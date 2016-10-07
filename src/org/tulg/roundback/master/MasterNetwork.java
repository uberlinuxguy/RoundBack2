package org.tulg.roundback.master;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by jasonw on 10/7/2016.
 */
public class MasterNetwork {
    ServerSocket socket;
    int port;
    private boolean quitting;

    public MasterNetwork(MasterConfig config){
        port=2377;

        port = Integer.parseInt(config.getPort(),10);
        if(port == 0) {
            port = 2377;
        }
        quitting=false;

    }

    public void listen(){
        try {
            socket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error: Unable to create socket on port " + port);
            System.exit(1);
        }
        //handleConnection(socket);
        Socket clientSock = null;
        while(!quitting) {
            try {
                clientSock = socket.accept();

                ExecutorService executor = Executors.newFixedThreadPool(5);
                Runnable clientThread = new MasterThread(clientSock);
                executor.execute(clientThread);
                System.out.println("Accepted connection: " +
                        clientSock.getInetAddress().getHostAddress() + ":" +
                        clientSock.getPort()
                );

            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error: socket error on accept()");
                System.exit(1);
            }
        }

        /*
        Socket clientSock = null;
        BufferedReader in = null;
        String inputLine = "";
        // listen until we quit
        while(!quitting) {
            try {
                clientSock = socket.accept();
                in = new BufferedReader(
                        new InputStreamReader(clientSock.getInputStream()));
                 inputLine = in.readLine();
                while (inputLine != "bye") {
                    inputLine = in.readLine();
                }
                if(inputLine.equals("bye")) {
                    quitting = true;
                }

            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error: socket error on accept()");
                System.exit(1);
            }
            System.out.println("Accepted connection: " +
                    clientSock.getInetAddress().getHostAddress() + ":" +
                    clientSock.getPort()
            );
            try {
                clientSock.close();
            } catch (IOException e) {

            }


        }*/

    }
}
