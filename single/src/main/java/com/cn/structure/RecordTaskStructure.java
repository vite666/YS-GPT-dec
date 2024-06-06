package com.cn.structure;

import com.cn.dto.RecordTaskDto;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @BelongsProject: YS-GPT-dec
 * @BelongsPackage: com.cn.structure
 * @Author: 刘志威
 * @CreateTime: 2024-06-04  10:51
 * @Description: TODO
 * @Version: 1.0
 */
@Data
@Accessors(chain = true)
public class RecordTaskStructure {

    RecordTaskDto recordTaskDto;
}
