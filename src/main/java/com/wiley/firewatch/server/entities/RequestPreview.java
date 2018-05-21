package com.wiley.firewatch.server.entities;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class RequestPreview {
    private String url;
    private Integer status;
    private String type;
}
