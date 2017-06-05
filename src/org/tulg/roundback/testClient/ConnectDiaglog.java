package org.tulg.roundback.testClient;

import org.tulg.roundback.client.ClientNetwork;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

class ConnectDiaglog extends JDialog {
    private JPanel contentPane;
    private JButton buttonConnect;
    private JButton buttonClose;
    private JButton btnPrefs;
    private JTextArea textArea1;
    private JTextField txtCommand;
    private JButton btnSend;
    private final TestCliConfig testCliConfig;
    private ClientNetwork clientNetwork;
    private boolean connected;
    private Thread recvThread;


    public ConnectDiaglog(TestCliConfig testCliConfig) {
        connected = false;
        this.testCliConfig = testCliConfig;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btnSend);

        buttonConnect.addActionListener(e -> onConnect());
        buttonClose.addActionListener(e -> onClose());
        btnPrefs.addActionListener(e -> onPrefs());
        btnSend.addActionListener(e -> onSend());

        resetThread();


        // call onClose() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onClose();
            }
        });

        // call onClose() on ESCAPE
        contentPane.registerKeyboardAction(e -> onClose(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void resetThread(){
        // set up a new thread to do the recv
        recvThread = new Thread(() -> {
            // need to work on this.
            if(connected) {
                while(connected) {
                    // if we ar econnected
                    try {
                        textArea1.append(clientNetwork.recvRaw() + "\n");
                    } catch (IOException e) {
                        connected = false;
                        buttonConnect.setText("Connect");
                        clientNetwork.disconnect();
                        clientNetwork = null;
                    } catch (InterruptedException e) {
                        connected = false;
                        buttonConnect.setText("Connect");
                        if(clientNetwork != null) {
                            clientNetwork.disconnect();
                            clientNetwork = null;
                        }
                    }
                }
            }
            System.out.println("Recieve thread exit.");
            resetThread();
        });
    }

    private void onSend() {
        // Send the entered command
        if(clientNetwork != null) {
            try {
                clientNetwork.sendRaw(txtCommand.getText());
                txtCommand.setText("");
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    private void onPrefs() {
        // add code here to open prefs window.
        PrefsDialog dialog = new PrefsDialog(testCliConfig);
        dialog.pack();
        dialog.setVisible(true);
    }

    private void onConnect() {
        // Connect to the master
        if(! connected ) {
            clientNetwork = new ClientNetwork();
            clientNetwork.setEncryption(testCliConfig.getEncrypted());
            clientNetwork.setEncryptionKey(testCliConfig.getEncryptionKey());
            clientNetwork.setServer(testCliConfig.getHostname(), testCliConfig.getPort());
            if (clientNetwork.connect()) {
                connected = true;
                buttonConnect.setText("Disconnect");
                contentPane.setPreferredSize(contentPane.getPreferredSize());
                recvThread.start();
            }
        } else {
            connected = false;
            buttonConnect.setText("Connect");
            clientNetwork.disconnect();
            clientNetwork = null;
            recvThread.interrupt();
        }

    }

    private void onClose() {
        // add your code here if necessary
        if(connected){
            connected = false;
            buttonConnect.setText("Connect");
            clientNetwork.disconnect();
            clientNetwork = null;
            recvThread.interrupt();

        }
        dispose();
    }

}
