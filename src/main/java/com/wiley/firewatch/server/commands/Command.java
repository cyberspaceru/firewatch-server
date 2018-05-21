package com.wiley.firewatch.server.commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wiley.firewatch.core.utils.ContentType;
import io.netty.handler.codec.http.HttpMethod;

import java.util.regex.Pattern;

public abstract class Command {
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public abstract Pattern getExpectedPattern();
    public abstract HttpMethod getExpectedHttpMethod();
    public abstract ContentType contentType();
    public abstract String execute(String content);
}
