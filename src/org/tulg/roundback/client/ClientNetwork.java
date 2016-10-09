package org.tulg.roundback.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

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
    public ArrayList<String> getHosts(){
        ArrayList<String> tmpArray = new ArrayList<>();
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // send the master server out request
            out.println("list hosts");
            out.flush();
            // wait for reply...
            int timer=0;
            while(!in.ready()){
                try {
                    Thread.sleep(1000);
                    timer++;
                    if(timer > 15){
                        break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.err.println("Error: Interrupted while waiting for server.");
                    System.exit(1);
                }
            }
            if(in.ready()) {
                String tmpString = in.readLine();
                while (tmpString != "") {
                    tmpArray.add(tmpString);
                    if(in.ready()) {
                        tmpString = in.readLine();
                    } else {
                        tmpString = "";
                    }
                }
            } else {
                System.err.println("Error: Timeout waiting for list hosts from seerver");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return tmpArray;
    }

}
