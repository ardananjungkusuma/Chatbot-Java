/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SARK-29
 */
public class Server {
    private static DataInputStream dis;
    private static DataOutputStream dos;
    private static ServerSocket ss;
    private static Socket s;
    
    private String jawabanBot;

    public String getJawabanBot() {
        return jawabanBot;
    }

    public void setJawabanBot(String jawabanBot) {
        this.jawabanBot = jawabanBot;
    }
    
    public static void main(String[] args) {
        try {
            ss = new ServerSocket(1239);
            s = ss.accept();
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
        } catch (IOException e) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
