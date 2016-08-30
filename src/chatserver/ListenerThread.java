/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chatserver;

import entities.ClientModel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;



class ListenerThread extends Thread{
    
    private BufferedReader selfReader;
    private PrintWriter selfWriter;
    private final ClientList clientList;
    private final Socket self;
    
    ListenerThread(Socket newClient, ClientList clientList) {
        self = newClient;
        try {
            InputStream in = newClient.getInputStream();
            selfReader = new BufferedReader(new InputStreamReader(in));
            selfWriter = new PrintWriter(new OutputStreamWriter(newClient.getOutputStream()));
        } catch (IOException ex) {
            Logger.getLogger(ListenerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.clientList = clientList;
        
        start();
    }
    
    @Override
    public void run(){
        String line = "";
        try {
            while( (line = selfReader.readLine()) != null){
                System.out.println("Message received from: " 
                        + self.getInetAddress().toString()
                        + "\nMessage: " + line);
                
                String parsedResponse[] = line.split("(?<=>)");
                switch(parsedResponse[0]){
                    case "<REGISTER>":
                        String welcomeMessage = "Welcome " + parsedResponse[1];
                        addClient(parsedResponse[1], parsedResponse[2], parsedResponse[3]);
                        //  Send friendslist
                        selfWriter.println(welcomeMessage);
                        selfWriter.flush();
                        break;
                    case "<PUBLIC>":
                        clientList.sendMessage(line);
                        break;
                    case "<PRIVATE>":
                        //  Get client to send to
                        ClientModel receiver = clientList.getClient(parsedResponse[2]);
                        sendPrivateMessage(parsedResponse[1], receiver, parsedResponse[3]);
                        break;
                    case "<LOGOUT>":
                        break;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ListenerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private synchronized void addClient(
            String nickName
            , String realName
            , String lastIp){
        try {
            clientList.addClient(self, nickName, realName, lastIp);
        } catch (IOException ex) {
            Logger.getLogger(ListenerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private synchronized void sendPrivateMessage(String sender, ClientModel receiver, String message) {
        String Message = 
                "<PRIVATE>" 
                + "<" + sender + ">"
                + "<" + receiver.getNickName() + ">" 
                + "<" + message + ">";
        receiver.getWriter().println(message);
    }

}
