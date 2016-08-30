/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 *
 * @author Timothy
 */
public class ChatServer {
    public static void main(String[] args) throws IOException {
        ServerSocket server;
        
        server = new ServerSocket(50000);
        
        ClientList clientList = new ClientList();
        while(true){
            Socket newClient = server.accept();
            System.out.println(
                    "New connection: " 
                    + newClient.getInetAddress().toString());
            
            ListenerThread listener = new ListenerThread(newClient, clientList);
        }
    }
    
}
