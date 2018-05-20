package com.wiley.firewatch.server.commands;

import com.wiley.firewatch.core.utils.ContentType;
import io.netty.handler.codec.http.HttpMethod;

import java.util.regex.Pattern;

public abstract class Command {
    public abstract Pattern getPattern();
    public abstract HttpMethod getHttpMethod();
    public abstract ContentType contentType();
    public abstract String execute(String content);
}
