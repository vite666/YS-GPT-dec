package com.cn.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cn.common.ChatGptCommon;
import com.cn.dto.DialogueParameterDto;
import com.cn.model.ChatGptImageRecognitionModel;
import com.knuddels.jtokkit.api.Encoding;
import com.unfbx.chatgpt.entity.chat.Message;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

import static com.alibaba.fastjson.JSON.parseArray;
import static com.alibaba.fastjson.JSON.parseObject;
import static com.unfbx.chatgpt.utils.TikTokensUtil.getEncoding;
import static com.unfbx.chatgpt.utils.TikTokensUtil.tokens;

/**
 * gpt相关工具类
 *
 * @author 时间海 @github dulaiduwang003
 * @version 1.0
 */
@Component
@SuppressWarnings("all")
public class ChatGptUtil {

    /**
     * 解析CHATGPT数据体 flux
     *
     * @param data the data
     * @return the boolean
     */
    public String dataParsing(final String data) {
        if (JSON.isValidObject(data)) {
            JSONObject jsonObject = JSONObject.parseObject(data);
            JSONArray choices = jsonObject.getJSONArray("choices");
            if (choices != null && !choices.isEmpty()) {
                final JSONObject dataSet = choices.getJSONObject(0);
                if (dataSet.containsKey("delta")) {
                    JSONObject delta = dataSet.getJSONObject("delta");
                    if (delta.containsKey("content")) {
                        return delta.getString("content");
                    }
                } else {
                    final JSONObject message = dataSet.getJSONObject("message");
                    return message.getString("content");
                }
            }
        }
        return "";
    }

    /**
     * 文本敏感词检查
     *
     * @param data the data
     * @return boolean
     */
    public boolean isSusceptible(final String data) {

        String processedInput = data.toUpperCase().replaceAll("\\s+", "");
        Pattern pattern = Pattern.compile("chat");
        return pattern.matcher(processedInput).find();
    }

    /**
     * 计算 tokens
     * @param dto
     * @return
     */
    public Long get_tokens(DialogueParameterDto dto) {
        ChatGptCommon.ModelObj modelObj;
        //获取额外参数
        final DialogueParameterDto.Extra extra = parseObject(dto.getExtra(), DialogueParameterDto.Extra.class);

        //获取数据集
        final List<Message> messages = parseArray(dto.getMessages(), com.unfbx.chatgpt.entity.chat.Message.class);
        if ("RECOGNITION".equals(dto.getType())) {
            return get_tokens_img(dto);
        }
        //获取当前指向模型
        modelObj = ChatGptCommon.pollGetKey(extra.getModelIndex(), false);

        Encoding encoding = getEncoding(modelObj.getModelName());
        if (encoding == null) {
            encoding = getEncoding("gpt-4-32k");
        }
        Long request_tokens = 3l;
        for (Message msg : messages) {
            request_tokens += 3;
            request_tokens += tokens(encoding, msg.getContent());
            request_tokens += tokens(encoding, msg.getRole());
        }

        return request_tokens;
    }

    // 带图片的 token 计费, 默认 请求tokens = 500
    public long get_tokens_img(DialogueParameterDto dto) {
        final List<ChatGptImageRecognitionModel.Messages> messages = parseArray(dto.getMessages(), ChatGptImageRecognitionModel.Messages.class);
        Message message = new Message(messages.get(0).getContent().get(0).toString());

        Encoding encoding = getEncoding("gpt-4-32k");

        long request_tokens = 500;
        request_tokens += 3;
        request_tokens += tokens(encoding, message.getContent());
        request_tokens += tokens(encoding, message.getRole());

        return request_tokens;
    }

    public long get_tokens(String model_name, String conent) {


        Encoding encoding = getEncoding(model_name);
        if (encoding == null) {
            encoding = getEncoding("gpt-4-32k");
        }
        int request_tokens = 3 + tokens(encoding, conent);

        return request_tokens;
    }

}
