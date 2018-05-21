package com.wiley.firewatch.server;

import com.wiley.firewatch.core.FirewatchConnection;
import com.wiley.firewatch.server.exceptions.FirewatchServerException;
import com.wiley.firewatch.server.filters.CommandFilter;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.aeonbits.owner.ConfigFactory;

import static com.wiley.firewatch.core.FirewatchConnection.proxyServer;

@Slf4j
@Accessors(fluent = true)
public class FirewatchServer {
    private static FirewatchServer instance;
    @Getter
    private final FirewatchServerConfiguration configuration;

    private FirewatchServer() {
        this.configuration = ConfigFactory.create(FirewatchServerConfiguration.class);
        if (!FirewatchConnection.create()) {
            throw new FirewatchServerException();
        }
        else {
            String base = this.configuration.base();
            if (this.configuration.includePort()) {
                base += ":" + proxyServer().getPort();
            }
            if (!base.endsWith("/")) {
                base += "/";
            }
            proxyServer().addRequestFilter(new CommandFilter(base));
            proxyServer().newHar();
        }
    }

    public static FirewatchServer instance() {
        if (FirewatchServer.instance == null) {
            FirewatchServer.instance = new FirewatchServer();
        }
        return FirewatchServer.instance;
    }
}
