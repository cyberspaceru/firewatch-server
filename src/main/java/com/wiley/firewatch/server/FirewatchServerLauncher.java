package com.wiley.firewatch.server;

import com.wiley.firewatch.core.FirewatchConnection;

public class FirewatchServerLauncher {

    public static void host() {
        FirewatchServer.instance();
        while (FirewatchConnection.isAvailable()) {
            // Until server connection is available.
        }
    }

}
