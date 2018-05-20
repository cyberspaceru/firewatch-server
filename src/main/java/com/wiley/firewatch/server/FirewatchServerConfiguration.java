package com.wiley.firewatch.server;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;

@Sources("classpath:configuration.properties")
public interface FirewatchServerConfiguration extends Config {
    @Key("base")
    @DefaultValue("http://127.0.0.1")
    String base();

    @Key("include-port")
    @DefaultValue("true")
    boolean includePort();

    @Key("name")
    @DefaultValue("firewatch-server")
    String name();
}
