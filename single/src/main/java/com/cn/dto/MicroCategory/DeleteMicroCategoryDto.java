package com.cn.dto.MicroCategory;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 删除预设词类目 DTO
 *
 * @author 时间海 @github dulaiduwang003
 * @version 1.0
 */
@Data
@Accessors(chain = true)
public class DeleteMicroCategoryDto implements Serializable {


    @NotNull(message = "删除标识不能为空")
    private Long microCategoryId;

}
