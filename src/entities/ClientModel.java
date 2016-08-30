/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.PrintWriter;


public class ClientModel {
    private PrintWriter writer;
    private String nickName;
    private String realName;
    private String lastIp;
    
    
    public ClientModel(){
        
    }
    
    public ClientModel(PrintWriter writer
            , String nickName
            , String realName
            , String lastIP){
        this.writer = writer;
        this.nickName = nickName;
        this.realName = realName;
        this.lastIp = lastIP;
    }

    public PrintWriter getWriter() {
        return writer;
    }

    public String getNickName() {
        return nickName;
    }

    public String getRealName() {
        return realName;
    }

    public String getLastIp() {
        return lastIp;
    }

    public void setWriter(PrintWriter writer) {
        this.writer = writer;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public void setLastIp(String lastIp) {
        this.lastIp = lastIp;
    }
    
    
}
