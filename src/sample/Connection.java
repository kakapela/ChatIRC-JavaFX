package sample;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;

import java.io.*;
import java.net.Socket;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class Connection {


    private static Connection INSTANCE;
    private Connection(){}
    private String local_nickname;
    private Socket socket;
    /*
    PrintWriter pw;
    BufferedWriter bw;
    OutputStreamWriter osw;
    OutputStream os;
*/
    DataOutputStream dos;
    public static Connection getInstance(){
        if(INSTANCE==null) INSTANCE=new Connection();
        return INSTANCE;

    }

    public String getNickname() {
        return local_nickname;
    }

    public Socket getSocket() {
        return socket;
    }

    public boolean makeConnection(String IP, String port, String nickname) throws IOException {

        int local_port;
        local_port = Integer.parseInt(port);
        socket= new Socket(IP,local_port);
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

    public void sendMessage(JFXTextField textField, PrintWriter pw) throws IOException {
        if(socket.isConnected()){
            pw.println(local_nickname + " :  " + textField.getText());
            pw.flush();
        }

    }


}
