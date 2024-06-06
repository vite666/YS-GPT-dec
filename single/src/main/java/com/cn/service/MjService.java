package com.cn.service;

import com.cn.dto.MjTaskDto;


/**
 * @BelongsProject: YS-GPT-dec
 * @BelongsPackage: com.cn.service
 * @Author: 刘志威
 * @CreateTime: 2024-05-27  23:52
 * @Description: TODO
 * @Version: 1.0
 */
public interface MjService {

    /**
     * 添加 Mj 绘图任务
     * @param dto
     * @return
     */
    String addMjDrawingTask(final MjTaskDto dto);

}
