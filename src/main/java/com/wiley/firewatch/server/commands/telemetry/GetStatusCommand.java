package com.wiley.firewatch.server.commands.telemetry;

import com.wiley.firewatch.core.FirewatchConnection;
import com.wiley.firewatch.core.utils.ContentType;
import com.wiley.firewatch.server.commands.Command;
import io.netty.handler.codec.http.HttpMethod;

import java.util.regex.Pattern;

public class GetStatusCommand extends Command {
    @Override
    public Pattern getExpectedPattern() {
        return Pattern.compile("status");
    }

    @Override
    public HttpMethod getExpectedHttpMethod() {
        return HttpMethod.GET;
    }

    @Override
    public ContentType contentType() {
        return ContentType.plain().utf8();
    }

    @Override
    public String execute(String content) {
        if (FirewatchConnection.isAvailable()) {
            return "available";
        }
        return "error";
    }
}
