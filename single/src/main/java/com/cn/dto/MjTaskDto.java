package com.cn.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @BelongsProject: YS-GPT-dec
 * @BelongsPackage: com.cn.dto
 * @Author: 刘志威
 * @CreateTime: 2024-05-27  23:47
 * @Description: Mj 绘图
 * @Version: 1.0
 */
@Data
@Accessors(chain = true)
@SuppressWarnings("all")
public class MjTaskDto {

    private String images;

    @NotBlank(message = "提示词不能为空")
    private String prompt;
}
