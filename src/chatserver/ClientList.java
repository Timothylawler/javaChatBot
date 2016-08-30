/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chatserver;

import entities.ClientModel;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;


class ClientList extends ArrayList<ClientModel>{
    
    public ClientList(){
        
    }
    
    public void addClient(Socket newClient, String nickName, String realName, String lastIp) 
            throws IOException{
        OutputStream output = newClient.getOutputStream();
        PrintWriter printer = new PrintWriter(
            new OutputStreamWriter(output));
        ClientModel client = new ClientModel(printer, nickName, realName, lastIp);
        
        add(client);
    }

    void sendMessage(String line) {
        for(ClientModel client: this){
            client.getWriter().println(line);
            client.getWriter().flush();
        }
    }
    
    public ClientModel getClient(String nickName){
        for(ClientModel client: this){
            if(client.getNickName().equals(nickName))
                return client;
        }
        return null;
    }

}
