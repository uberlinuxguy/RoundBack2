package org.tulg.roundback.gui;

import javafx.application.Application;
import javafx.stage.Stage;

import javax.swing.*;
import java.applet.Applet;

/**
 * Created by jasonw on 9/24/2016.
 */
public class Main {
    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }


        // write your code here
        MainWindow mainWindow = new MainWindow();
        mainWindow.setVisible(true);
        mainWindow.toFront();


    }
}
