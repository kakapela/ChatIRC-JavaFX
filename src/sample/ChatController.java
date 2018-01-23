package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Window;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatController implements Initializable {

    UsefulFunctions usefulFunctions = new UsefulFunctions();

    @FXML
    private AnchorPane mainPane;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
   Main.fadeTrans(mainPane);
        usefulFunctions.menuDragged(mainPane);
    }


    @FXML
    public void exitProgram(MouseEvent event) {
      usefulFunctions.exitProgram(event);
    }

}
