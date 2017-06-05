package org.tulg.roundback.testClient;



/**
 * Created by jasonw on 9/24/2016.
 */
class Main {
    public static void main(String[] args) {
        TestCliConfig testCliConfig = new TestCliConfig();
        //TestCliCommandLine testCliCommandLine = new TestCliCommandLine(args, testCliConfig);

        ConnectDiaglog dialog = new ConnectDiaglog(testCliConfig);
        dialog.pack();
        dialog.setVisible(true);

    }
}
