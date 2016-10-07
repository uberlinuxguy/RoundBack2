package org.tulg.roundback.master;

import java.util.prefs.Preferences;

/**
 * Created by jasonw on 9/24/2016.
 */
public class MasterConfig {
    Preferences preferences;
    String port;
    public MasterConfig(){
        preferences = Preferences.userRoot().node("org.tulg.roundback.master");
        port = preferences.get("port", "2377");



    }

    public String getPort() {
        return port;
    }
}
