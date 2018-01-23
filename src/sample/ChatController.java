package sample;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Window;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatController implements Initializable {
    @FXML
    private Label labelForNick;


    @FXML
    private JFXTextField textField;



    @FXML
    private AnchorPane mainPane;
    @FXML
    private JFXTextArea chatTextArea;
    ClientReceive clientReceive = new ClientReceive();
    Connection connection= Connection.getInstance();
    UsefulFunctions usefulFunctions = new UsefulFunctions();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
   Main.fadeTrans(mainPane);
        usefulFunctions.menuDragged(mainPane);

        //ustaw nicka
        labelForNick.setText("Witaj " + connection.getNickname() + " ! Zacznij czatować już teraz!");

    }
    @FXML
    public void send() throws IOException {
        PrintWriter pw = new PrintWriter(connection.getSocket().getOutputStream(), true);
        try {
            connection.sendMessage(textField,pw);
            textField.setText("");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @FXML
    public void exitProgram(MouseEvent event) {
      usefulFunctions.exitProgram(event);
    }

}
