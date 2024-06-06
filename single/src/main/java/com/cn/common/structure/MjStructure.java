package com.cn.common.structure;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @BelongsProject: YS-GPT-dec
 * @BelongsPackage: com.cn.common.structure
 * @Author: 刘志威
 * @CreateTime: 2024-05-28  16:06
 * @Description: TODO
 * @Version: 1.0
 */
@Data
@Accessors(chain = true)
public class MjStructure {
    /**
     * The Request url.
     */
    private String requestUrl;

    /**
     * The Key list.
     */
    private List<String> keyList;
}
