package org.tulg.roundback.gui;

import org.tulg.roundback.client.ClientNetwork;

import javax.swing.*;
import javax.swing.text.Position;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.*;
import java.util.prefs.Preferences;

/**
 * Created by jasonw on 9/24/2016.
 */
public class MainWindow extends JFrame {

    private JPanel panel;
    private JTree tree1;
    private JMenuBar menuBar;
    private ClientNetwork clientNetwork;
    private Preferences preferences;
    private PreferencesWindow preferencesWindow;
    private boolean isConnected;
    JMenuItem connItem;


    public MainWindow() {
        isConnected = false;

        //setBounds(-1, -1, 300, 200);
        DefaultMutableTreeNode top =
                new DefaultMutableTreeNode("...");
        DefaultTreeModel model = (DefaultTreeModel) tree1.getModel();
        /*
        top.add(new DefaultMutableTreeNode("Test 1"));

        DefaultMutableTreeNode category = new DefaultMutableTreeNode("Test 2");
        category.add(new DefaultMutableTreeNode("Test 3"));
        top.add(category);
        */

        clientNetwork = new ClientNetwork();
        this.preferences = Preferences.userRoot().node("org.tulg.roundback.gui");
        preferencesWindow = new PreferencesWindow(this);


        // TODO: load preferences.
        loadAndCheckPrefs();

        // TODO: Attempt to connect to the master server.
        isConnected = clientNetwork.connect();


        model.setRoot(top);

        GuiTreeRenderer guiTreeRenderer = new GuiTreeRenderer();
        tree1.setCellRenderer(guiTreeRenderer);


        this.setTitle("Round Back");

        this.setJMenuBar(buildMainWindowMenu());
        this.add(panel);


        setBounds(-1, -1, 800, 600);
        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                System.exit(0); // An Exit Listener
            }
        });


    }

    public Preferences getPreferences() {
        return preferences;
    }

    private JMenuBar buildMainWindowMenu() {

        menuBar = new JMenuBar();
        // File Menu
        JMenu fileMenu = new JMenu("File");
        {
            JMenuItem prefsItem = new JMenuItem("Preferences");
            prefsItem.setAccelerator(KeyStroke.getKeyStroke("p"));
            prefsItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //open the prefs window.
                    PreferencesWindow dialog = preferencesWindow;

                    dialog.pack();
                    dialog.setVisible(true);
                    // reload all the prefs
                    loadAndCheckPrefs();
                    if(isConnected){
                        clientNetwork.disconnect();
                        isConnected=false;
                        connItem.setText("Connect");
                    }
                }
            });
            fileMenu.add(prefsItem);

            fileMenu.addSeparator();

            String connectedMenu="Disconnect";
            if(!isConnected) {
                connectedMenu="Connect";

            }
            connItem = new JMenuItem(connectedMenu);
            connItem.setAccelerator(KeyStroke.getKeyStroke("c"));
            connItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO: Disconnect or connect.
                    if(isConnected) {
                        clientNetwork.disconnect();
                        isConnected=false;
                        connItem.setText("Connect");
                    } else {
                        clientNetwork.connect();
                        isConnected=true;
                        connItem.setText("Disconnect");
                    }
                }
            });
            fileMenu.add(connItem);

            fileMenu.addSeparator();

            JMenuItem closeItem = new JMenuItem("Exit");
            closeItem.setAccelerator(KeyStroke.getKeyStroke("x"));
            closeItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(2);
                }
            });
            fileMenu.add(closeItem);

        }

        menuBar.add(fileMenu);



        return menuBar;
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    private void loadAndCheckPrefs(){
        clientNetwork.setServer(preferences.get("masterServer",""));

    }

    @Override
    public void paint(Graphics g) {

        super.paint(g);
    }
}
