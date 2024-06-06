package com.cn.net;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSONObject;

import com.cn.common.ChatGptCommon;
import com.cn.common.structure.ChatGptStructure;
import com.cn.constant.ChatStatusConstant;
import com.cn.dto.DialogueParameterDto;
import com.cn.dto.RecordTaskDto;
import com.cn.enums.DialogueEnum;
import com.cn.exception.CloseException;
import com.cn.exceptions.MemberException;

import com.cn.service.UserService;
import com.cn.service.impl.ChatGptServiceFlux;
import com.cn.utils.ChatGptUtil;
import com.cn.utils.SpringContextUtil;
import com.cn.utils.StringUtils;
import com.cn.utils.UserUtils;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;

import static com.alibaba.fastjson.JSON.parseObject;


/**
 * GPT流对话
 *
 * @author 时间海 @github dulaiduwang003
 * @version 1.0
 */
@Slf4j
@ServerEndpoint(value = "/gpt/{token}", subprotocols = {"protocol"})
@SuppressWarnings("all")
@Service
public class ChatGptNet {

    private static ChatGptServiceFlux chatGptServiceFlux;

    private static UserService userService;

    private static ChatGptUtil chatGptUtil;
    private Session session;

    private int maxSize = 123 * 1024 * 1024;

    @OnOpen
    public void onOpen(final Session session, @PathParam("token") final String token) {
        //设置传输缓存流
        if (session.getMaxTextMessageBufferSize() <= 8192) {
            session.setMaxBinaryMessageBufferSize(maxSize);
            session.setMaxTextMessageBufferSize(maxSize);
        }
        try {
            assert session.getId() != null;
            //校验用户
            assert StpUtil.getLoginIdByToken(token) != null;
        } catch (Exception e) {
            log.warn("无法获取建立连接数据,已拒绝连接");
            return;
        }
        this.session = session;
        //手动注入
        if (chatGptServiceFlux == null) {
            chatGptServiceFlux = (ChatGptServiceFlux) SpringContextUtil.getBean("chatGptServiceFlux");
            chatGptUtil = (ChatGptUtil) SpringContextUtil.getBean("chatGptUtil");
            userService = (UserService) SpringContextUtil.getBean("userServiceImpl");
        }
    }

    /**
     * 建立数据连接
     *
     * @param message the message
     * @param token   the token
     * @param model   the model
     * @param env     the env
     */
    @OnMessage
    public void onMessage(final String parameter, @PathParam("token") final String token) {

        //转化请求
        final DialogueParameterDto dto = JSONObject.parseObject(parameter, DialogueParameterDto.class);
        ChatGptCommon.ModelObj modelObj;
        final DialogueParameterDto.Extra extra = parseObject(dto.getExtra(), DialogueParameterDto.Extra.class);
        modelObj = ChatGptCommon.pollGetKey(extra.getModelIndex(), false);

        ChatGptStructure.ImageRecognitionConfig config = ChatGptCommon.STRUCTURE.getImageRecognitionConfig();
        try {
            long promptTokens = chatGptUtil.get_tokens(dto); // 请求信息 tokens
            long completionTokens[] = {0}; // 补全信息 tokens
            final String[] completion = {""}; // 补全信息
            //校验用户会员额度（校验收费的对话模型和绘图模型）
            if ("RECOGNITION".equals(dto.getType()) || modelObj.getPromptPrice().compareTo(BigDecimal.ZERO) > 0 || modelObj.getPromptPrice().compareTo(BigDecimal.ZERO) > 0)
                UserUtils.membershipHasExpiredCharacter(UserUtils.getLoginIdByToken(token));

            //建立流数据
            final ChatGptServiceFlux.FluxOutcome action = chatGptServiceFlux.action(dto, DialogueEnum.fromString(dto.getType()));
            //返回流数据
            action.getFlux().doFinally(signal -> {
                        handleWebSocketCompletion();
                        completionTokens[0] = chatGptUtil.get_tokens(modelObj.getModelName(), completion[0]) - 3L;
                        if (StringUtils.notEmpty(completion[0])) {
                            chatGptServiceFlux.expenseRecord(new RecordTaskDto().setUserId(UserUtils.getLoginIdByToken(token))
                                    .setPromptTokens(promptTokens).setCompletionTokens(completionTokens[0])
                                    .setModelObj(modelObj));
                        }
                    })
                    .subscribe(data -> {
                        final String dataed = chatGptUtil.dataParsing(data);
                        try {
                            completion[0] += dataed;
                            this.session.getBasicRemote().sendText(dataed);
                        } catch (Exception e) {
                            //用户可能手动断开连接
                            throw new CloseException();
                        }
                    }, throwable -> {
                        //为 Close异常时 过滤
                        if (!(throwable instanceof CloseException)) {
                            sendErrorMessages(ChatStatusConstant.ERROR);
                        }
                    });

        } catch (MemberException e) {
            //会员过期
            sendErrorMessages(e.getMessage());
        } catch (Exception e) {
            sendErrorMessages(ChatStatusConstant.ERROR);
        }


    }

    /**
     * \
     * 发送错误消息
     *
     * @param msg
     */
    private void sendErrorMessages(final String msg) {
        try {
            this.session.getBasicRemote().sendText(msg);
            handleWebSocketCompletion();
        } catch (Exception e) {

        }
    }

    @OnClose
    public void handleWebSocketCompletion() {
        try {
            this.session.close();
        } catch (IOException e) {
            log.error("关闭 WebSocket 会话失败.", e);
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        log.warn("GPT websocket出现异常 原因:{}", throwable.getMessage());
        //打印堆栈
        //      throwable.printStackTrace();
    }
}
