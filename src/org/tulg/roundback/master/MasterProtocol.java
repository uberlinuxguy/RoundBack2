package org.tulg.roundback.master;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by jasonw on 10/9/2016.
 *
 * MasterProtocol - Used to process the incoming/outgoing data
 *      to/from the master server.
 */
public class MasterProtocol {

    Socket clientSock;
    MasterDB db;

    public MasterProtocol (Socket clientSock){
        this.clientSock = clientSock;
        db = new MasterDB();
        db.open();
        db.close();

    }

    public boolean process(String inputLine){
        try {
            PrintStream out = new PrintStream(clientSock.getOutputStream(),true);
            // Process incoming commands.
            if (inputLine.equals("bye")) {
                System.out.println("Connection to: " + clientSock.getInetAddress().getHostAddress() + " closed.");
                return false;
            }
            // list command
            if (inputLine.substring(0, 4).compareToIgnoreCase("list") == 0) {
                if (inputLine.substring(5, 10).compareToIgnoreCase("hosts") == 0) {
                    System.out.println("Sending host list to " + clientSock.getInetAddress().getHostAddress());
                    // TODO: Generate a list of all the hosts available.

                    // TODO: For now, some test stuff.
                    ResultSet rs = db.getHosts();
                    while (rs.next()){
                        out.println("host " + rs.getInt("id") + " " + rs.getString("hostname"));
                    }

                    db.close();
                    out.flush();
                }
            }
        } catch (IndexOutOfBoundsException e) {

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }




}
