package org.tulg.roundback.master;

import java.io.*;
import java.net.Socket;

/**
 * Created by jasonw on 10/7/2016.
 */
public class MasterThread implements Runnable {
    Socket clientSock;
    boolean quitting;
    MasterProtocol mProto;
    public MasterThread(Socket clientSock){
        this.clientSock = clientSock;
        quitting = false;
        mProto = new MasterProtocol(clientSock);
    }
    @Override
    public void run() {

        BufferedReader in = null;
        PrintStream out;
        String inputLine = "";
        // listen until we quit
        while(!quitting) {
            try {

                in = new BufferedReader(
                        new InputStreamReader(clientSock.getInputStream()));

                if(in.ready()){
                    inputLine = in.readLine();
                    if(!mProto.process(inputLine)){
                        quitting=true;
                    }
                }
            } catch (IOException e) {
            }


        }

    }
}
