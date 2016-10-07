package org.tulg.roundback.master;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by jasonw on 10/7/2016.
 */
public class MasterThread implements Runnable {
    Socket clientSock;
    boolean quitting;
    public MasterThread(Socket clientSock){
        this.clientSock = clientSock;
        quitting = false;
    }
    @Override
    public void run() {

        BufferedReader in = null;
        String inputLine = "";
        // listen until we quit
        while(!quitting) {
            try {

                in = new BufferedReader(
                        new InputStreamReader(clientSock.getInputStream()));
                inputLine = in.readLine();
                while (!inputLine.equals("bye")) {
                    inputLine = in.readLine();
                }
                if(inputLine.equals("bye")) {
                    quitting = true;
                    System.out.println("Connection to: " + clientSock.getInetAddress().getHostAddress() + " closed.");
                }

            } catch (IOException e) {
            }


        }

    }
}
