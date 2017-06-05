package org.tulg.roundback.storage;


import java.util.prefs.Preferences;

/**
 * Created by jasonw on 9/24/2016.
 */
class StorageConfig {
    private Preferences storagePrefs;
    private String port;
    private int maxThreads;
    private int minDataPort;
    private int maxDataPort;


    public Preferences getStoragePrefs() {
        return storagePrefs;
    }

    public String getPort() {
        return port;
    }

    public StorageConfig () {
        storagePrefs = Preferences.userRoot().node("org.tulg.roundback.storage");

        port = storagePrefs.get("port", "2278");
        maxThreads = storagePrefs.getInt("maxThreads", 2);
        minDataPort = storagePrefs.getInt("minDataPort", 50000);
        maxDataPort = storagePrefs.getInt("maxDataPort", 51000);


    }

    public void printConfig(){
        System.out.println("port: " + port);
        System.out.println("maxThreads: " + maxThreads);
        System.out.println("minDataPort: " + minDataPort);
        System.out.println("maxDataPort: " + maxDataPort);


    }

    public void save() {
        //  save the current config to prefs
        storagePrefs.put("port", port);
        storagePrefs.putInt("maxThreads", maxThreads);
        storagePrefs.putInt("minDataPort", minDataPort);
        storagePrefs.putInt("maxDataPort", maxDataPort);

    }

    public void setStoragePrefs(Preferences storagePrefs) {
        this.storagePrefs = storagePrefs;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public int getMaxThreads() {
        return maxThreads;
    }

    public void setMaxThreads(int maxThreads) {
        this.maxThreads = maxThreads;
    }

    public int getMinDataPort() {
        return minDataPort;
    }

    public void setMinDataPort(int minDataPort) {
        this.minDataPort = minDataPort;
    }

    public int getMaxDataPort() {
        return maxDataPort;
    }

    public void setMaxDataPort(int maxDataPort) {
        this.maxDataPort = maxDataPort;
    }

}
