package com.cn.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @BelongsProject: YS-GPT-dec
 * @BelongsPackage: com.cn.model
 * @Author: 刘志威
 * @CreateTime: 2024-05-28  11:26
 * @Description: TODO
 * @Version: 1.0
 */
@Data
@Accessors(chain = true)
@SuppressWarnings("all")
public class MjModel {

    /**
     * The Init images.
     */
    private List<String> base64Array;

    /**
     * The Prompt.
     */
    private String prompt;




}
