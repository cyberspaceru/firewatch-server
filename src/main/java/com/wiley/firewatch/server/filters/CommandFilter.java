package com.wiley.firewatch.server.filters;

import com.wiley.firewatch.core.utils.ContentType;
import com.wiley.firewatch.core.utils.MatchingType;
import com.wiley.firewatch.core.utils.ResponseFactory;
import com.wiley.firewatch.core.utils.StringMatcher;
import com.wiley.firewatch.server.FirewatchServer;
import com.wiley.firewatch.server.commands.Command;
import com.wiley.firewatch.server.commands.telemetry.GetStatusCommand;
import io.netty.handler.codec.http.*;
import net.lightbody.bmp.filters.RequestFilter;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.wiley.firewatch.server.FirewatchServer.instance;
import static com.wiley.firewatch.core.utils.ResponseFactory.createHttpResponse;
import static com.wiley.firewatch.core.utils.ResponseFactory.createHttpResponse;
import static io.netty.handler.codec.http.HttpResponseStatus.CONFLICT;
import static io.netty.handler.codec.http.HttpResponseStatus.NOT_FOUND;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;

public class CommandFilter implements RequestFilter {
    private static final List<Command> COMMANDS;
    private final String base;

    static {
        COMMANDS = new ArrayList<>();
        COMMANDS.add(new GetStatusCommand());
    }

    public CommandFilter(String base) {
        this.base = base;
    }

    @Override
    public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo info) {
        String url = info.getUrl();
        if (url.startsWith(instance().configuration().base())) {
            String significantUrl = url.replace(this.base, "");
            List<Command> matched = COMMANDS.stream()
                    .filter(x -> StringMatcher.match(significantUrl, MatchingType.EQUALS, x.getPattern().pattern()))
                    .collect(Collectors.toList());
            HttpVersion version = request.getProtocolVersion();
            if (matched.isEmpty()) {
                return createHttpResponse(this.base , version, NOT_FOUND);
            } else if (matched.size() > 1) {
                return createHttpResponse(this.base , version, CONFLICT);
            } else {
                Command command = matched.get(0);
                try {
                    String executionResult = command.execute(contents.getTextContents());
                    return createHttpResponse(this.base , version, OK, command.contentType(), executionResult);
                } catch (Exception throwable) {
                    return createHttpResponse(this.base , version, HttpResponseStatus.BAD_REQUEST,
                            ContentType.plain(), "Execution Error: " + throwable.getMessage());
                }
            }
        }
        return null;
    }
}
