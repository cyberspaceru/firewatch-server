package com.wiley.firewatch.server.commands.telemetry;

import com.wiley.firewatch.core.FirewatchConnection;
import com.wiley.firewatch.core.utils.ContentType;
import com.wiley.firewatch.server.commands.Command;
import com.wiley.firewatch.server.entities.RequestPreview;
import io.netty.handler.codec.http.HttpMethod;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.core.har.HarLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class GetPreviewCommand extends Command {
    @Override
    public Pattern getExpectedPattern() {
        return Pattern.compile("preview");
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
        List<RequestPreview> previews = new ArrayList<>();
        Har har = FirewatchConnection.proxyServer().getHar();
        Optional.ofNullable(har)
                .map(Har::getLog)
                .map(HarLog::getEntries)
                .ifPresent(x -> x.forEach(entry -> {
                    RequestPreview preview = new RequestPreview()
                            .url(entry.getRequest().getUrl())
                            .status(entry.getResponse().getStatus());
                    previews.add(preview);
                }));
        return GSON.toJson(previews);
    }
}
