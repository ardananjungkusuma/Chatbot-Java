/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import backend.Answer;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
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
    private static String message = "";
    private static String chatBot;
    private static int portServer = 1239;
    public static void main(String[] args) {
        try {
            ss = new ServerSocket(portServer);
            s = ss.accept();
            Server server = new Server();
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
            System.out.println("Server Running On Port " + portServer);
            while (!message.equalsIgnoreCase("/quit")) {
                message = dis.readUTF();
                String kategori;
                Answer answer = new Answer();
                ArrayList<Answer> list = new Answer().filterAsk(message);
                if (message.equalsIgnoreCase("/quit")) {
                    System.out.println("Application Stopped");
                    System.exit(0);
                } else if (answer.filterAsk(message).isEmpty()) {
                    kategori = "Bot : gapaham xixixi";
                    System.out.println("Kamu : " + message + "\n" + chatBot + "\n");
                    dos.writeUTF(kategori);
                } else {
                    for (Answer ans : list) {
                        kategori = ans.getCategory();
                        chatBot = "Bot : " + answer.cariLur(kategori);
                        System.out.println("Kamu : " + message + "\n" + chatBot + "\n");
                        dos.writeUTF(chatBot);
                    }
                }
            }
        } catch (IOException e) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
