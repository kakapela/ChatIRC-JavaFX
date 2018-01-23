package sample;

import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Window;


import java.io.IOException;
import java.io.PrintWriter;
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
    double initialX;
    double initialY;
    UsefulFunctions usefulFunctions = new UsefulFunctions();
    Connection connection = Connection.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            usefulFunctions.menuDragged(mainPane);
    }



    @FXML
  public void exitProgram(MouseEvent event){

        usefulFunctions.exitProgram(event);
    }

    @FXML
    public void startConnection() throws IOException {

      String ip = IP_address.getText();
      String port = port_number.getText();
      String nick = nickname.getText();
      if(connection.makeConnection(ip,port,nick)) {
          System.out.println("Polaczyles sie z serwerem. Twoj nick to " + connection.getNickname() +".");
          System.out.println("Numer portu to " + port);
          Main.fadeTrans(mainPane);
          Main.changeScene("ChatView.fxml");


      }
      else {
          System.out.println("Nie udalo nam sie polaczyc :(");
      }
    }



}
