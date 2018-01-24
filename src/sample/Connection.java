package sample;

import com.jfoenix.controls.JFXTextField;
import java.io.*;
import java.net.Socket;

public class Connection {

//Jest to klasa ktora korzysta ze wzorca Singleton - wszystkie dane o polaczeniu z serwerem przechowujemy w obiekcie tej klasy
    private static Connection INSTANCE;
    private Connection() {
    }

    private String local_nickname;
    private Socket socket;

    public static Connection getInstance() {
        if (INSTANCE == null) INSTANCE = new Connection();
        return INSTANCE;

    }

    public String getNickname() {
        return local_nickname;
    }

    public Socket getSocket() {
        return socket;
    }

    //Aby utworzyc polaczenie podajemy IP,PORT i nick pobrany z textfieldow
    public boolean makeConnection(String IP, String port, String nickname) throws IOException {

        int local_port;
        local_port = Integer.parseInt(port);
        socket = new Socket(IP, local_port);
        local_nickname = nickname;

        if (socket.isConnected()) {
            System.out.println("Gniazdo utworzone pomyslnie!");
            return true;
        } else {
            System.out.println("Nie udalo sie polaczyc z serwerem");
            return false;
        }

    }
//Wysylamy wiadomosci podajac obiekt typu PrintWriter znajdujacy sie w kontrolerze
    public void sendMessage(JFXTextField textField, PrintWriter pw) throws IOException {
      //jak jestesmy polaczeni to wyslij nick i wiadomosc
        if (socket.isConnected()) {
            //jesli jest polaczenie to wyslij do serwera nick oraz tresc wiadomosci
            pw.println(local_nickname + " :  " + textField.getText());
            pw.flush();
        }

    }


}
