package com.whf.music.model.reponse;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author whf
 * @date 2023/4/11
 */
@Data
public class TreeResponse implements Serializable {

    private String value;

    private String label;

    private List<TreeResponse> children;
}
