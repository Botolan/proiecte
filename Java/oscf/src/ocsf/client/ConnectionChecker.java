package ocsf.client;


import client.ChatClient;

public class ConnectionChecker extends Thread {

    private ChatClient client;

    public ConnectionChecker(ChatClient client){
        this.client = client;
    }

    public void run(){
        while(true){
            if(!client.isConnected()){
                client.quit();
                break;
            }
        }
    }
}
