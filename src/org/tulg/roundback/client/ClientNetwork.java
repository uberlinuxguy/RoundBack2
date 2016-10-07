package org.tulg.roundback.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by jasonw on 10/6/2016.
 */
public class ClientNetwork {
    String serverAddress;
    String serverPort;
    Socket socket;

    public ClientNetwork(){

    }

    public void setServer(String serverString){
        //  parse serverString
        if(serverString.equals("")){
            this.serverAddress="locahost";
            this.serverPort="2377";
            return;
        }
        int portSep = serverString.indexOf(':');
        serverAddress = serverString.substring(0, portSep);
        serverPort = serverString.substring(portSep+1);

    }

    public void setServer(String serverAddress, String serverPort){
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public boolean connect(){
        try {
            socket = new Socket(serverAddress,Integer.parseInt(serverPort));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error: Cannot connect to " + serverAddress + ":" + serverPort);
            return false;
        }
        return true;

    }
    public void disconnect(){
        try {
            PrintWriter out =
                    new PrintWriter(socket.getOutputStream(), true);
            out.println("bye");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
