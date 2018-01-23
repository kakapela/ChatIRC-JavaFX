package sample;

import java.io.IOException;
import java.net.Socket;

public class Connection {


    private static Connection INSTANCE;
    private Connection(){}
    private String local_nickname;


    public static Connection getInstance(){
        if(INSTANCE==null) INSTANCE=new Connection();
        return INSTANCE;

    }

    public String getNickname() {
        return local_nickname;
    }

    public boolean makeConnection(String IP, String port, String nickname) throws IOException {

        int local_port;
        local_port = Integer.parseInt(port);
        Socket socket= new Socket(IP,local_port);
        local_nickname= nickname;

        if(socket.isConnected()){
            System.out.println("Gniazdo utworzone pomyslnie!");
            return true;
        }
        else {
            System.out.println("Nie udalo sie polaczyc z serwerem");
            return false;
        }

    }
}
