package org.tulg.roundback.master;

/**
 * Created by jasonw on 9/24/2016.
 */
public class Main {
    public static void main(String[] args) {
        MasterConfig masterConfig = new MasterConfig();
        MasterNetwork masterNetwork = new MasterNetwork(masterConfig);
        masterNetwork.listen();

    }
}
