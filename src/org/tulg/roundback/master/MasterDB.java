package org.tulg.roundback.master;

import org.sqlite.JDBC;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

/**
 * Created by jasonw on 10/9/2016.
 *
 * MasterDB - interface to the main database.
 *
 */
public class MasterDB extends JDBC {
    Connection dbConn;
    Statement stmt;
    boolean isOpen;
    String dbPath;

    public MasterDB (){
        isOpen=false;
        dbPath=System.getProperty("user.home") + File.separator + ".roundback" + File.separator + "RoundBack.db";
        if (!Files.isDirectory(Paths.get(System.getProperty("user.home") + File.separator + ".roundback"))) {
            if(!Files.exists(Paths.get(System.getProperty("user.home") + File.separator + ".roundback" ))){
                // no directory, try to create it.
                File dataDir = new File(System.getProperty("user.home") + File.separator + ".roundback");
                dataDir.mkdir();
            } else {
                System.err.println("Error: Unable to create database directory: " +
                        System.getProperty("user.home") + File.separator + ".roundback");
                System.exit(4);
            }
        }



    }

    public boolean open(){
        try {
            dbConn = DriverManager.getConnection("jdbc:sqlite:"+dbPath);
        } catch (SQLException e) {
            e.printStackTrace();
            isOpen = false;
            dbConn=null;
            return false;
        }
        isOpen = true;
        return true;
    }

    public void close(){
        if(dbConn!=null){
            try {
                dbConn.close();
            } catch (SQLException e) {

            }
            dbConn=null;
            isOpen=false;
        }

    }

    public ResultSet getHosts(){
        if(!isOpen){
            open();
        }
        ResultSet rs=null;
        try {
            stmt = dbConn.createStatement();
            rs = stmt.executeQuery("SELECT id,hostname FROM hosts;");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rs;


    }
}
