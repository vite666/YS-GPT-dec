package com.cn.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cn.dto.GptModel.AddGptModelDto;
import com.cn.dto.GptModel.DeleteGptModelDto;
import com.cn.dto.GptModel.UpdateGptModelDto;
import com.cn.entity.TsGptModel;
import com.cn.vo.GptModelPageVo;

/**
 * gpt模型配置业务
 *
 * @author 时间海 @github dulaiduwang003
 * @version 1.0
 */
public interface GptModelConfigService {


    /**
     * 获取gpt模型 分页获取
     *
     * @param pageNum the page num
     * @param prompt  the prompt
     * @return the gpt key page
     */
    IPage<GptModelPageVo> getGptModelPage(final int pageNum, final String prompt);

    /**
     * 通过模型名字获取单个gpt模型
     * @param modelName
     * @return
     */
    TsGptModel getGptModelByGptModelName(final String modelName);

    /**
     * 通过模型 I的 获取 gpt 模型
     * @param gptModelId
     * @return
     */
    TsGptModel getGptModelByGptModelId(final Long gptModelId);

    /**
     * 新增模型
     *
     * @param dto the dto
     */
    void addGptModel(final AddGptModelDto dto);


    /**
     * 修改模型
     *
     * @param dto the dto
     */
    void updateGptModel(final UpdateGptModelDto dto);


    /**
     * 删除模型
     *
     * @param dto the dto
     */
    void deleteGptModel(final DeleteGptModelDto dto);



}
