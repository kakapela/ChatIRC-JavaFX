package sample;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private JFXTextField nickname;

    @FXML
    private JFXTextField IP_address;

    @FXML
    private JFXTextField port_number;
    @FXML
    private AnchorPane mainPane;

    UsefulFunctions usefulFunctions = new UsefulFunctions();
    Connection connection = Connection.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usefulFunctions.menuDragged(mainPane);
    }


    @FXML
    public void exitProgram(MouseEvent event) {
        usefulFunctions.exitProgram(event);
    }

    //po kliknieciu w guzik sprawdzamy czy nasza metoda makeConnection zwraca true, jesli tak to jestesmy polaczeni
    @FXML
    public void startConnection() throws IOException {

        String ip = IP_address.getText();
        String port = port_number.getText();
        String nick = nickname.getText();
        if (connection.makeConnection(ip, port, nick)) {
            System.out.println("Polaczyles sie z serwerem. Twoj nick to " + connection.getNickname() + ".");
            System.out.println("Numer portu to " + port);
            Main.fadeTrans(mainPane);
            Main.changeScene("ChatView.fxml");


        } else {
            System.out.println("Nie udalo nam sie polaczyc :(");
        }
    }


}
