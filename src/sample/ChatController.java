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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
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

    Connection connection = Connection.getInstance();
    UsefulFunctions usefulFunctions = new UsefulFunctions();

    //Tworzymy watek do obslugi odbierania wiadomosci z serwera
    Thread thread = new Thread() {
        public void run() {
            try {

                //za pomoca Klasy PrintWriter wysylamy do klientow dane o tym czy ktos sie zalogowal do czatu
                PrintWriter pwr = new PrintWriter(connection.getSocket().getOutputStream(), true);
                pwr.println("\t\t\t\t\t" + connection.getNickname() + " jest online!");

                //tym bedziemy odczytywac dane
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getSocket().getInputStream()));

                while (true) {
                    //jezeli jestesmy polaczeni ale nie mamy co wczytac to nic nie rob
                    while (!connection.getSocket().isClosed() && connection.getSocket().getInputStream().available() <= 0)
                        ;

                    if (connection.getSocket().isClosed()) {
                        System.out.println("Watek zostal zamkniety");
                        break;
                    }

                    //za pomoca klasy date pobieramy sobie aktualna date i wyswietlamy ja
                    Date date = new Date();
                    DateFormat df = new SimpleDateFormat("MMM dd, yyyy h:mm a", new Locale("pl", "PL"));
                    chatTextArea.appendText("\n" + df.format(date) + ":\n");

                    //to co odebralismy z BufferReader to zalaczamy na koncu text area
                    chatTextArea.appendText(" " + br.readLine() + '\n');

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    };


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.fadeTrans(mainPane);
        usefulFunctions.menuDragged(mainPane);

        //ustaw nicka
        labelForNick.setText("Witaj " + connection.getNickname() + " ! Zacznij czatować już teraz!");

        //rozpocznij watek odbierania wiadomosci przez klientow
        thread.start();

    }

    @FXML
    public void send() throws IOException {
//tworzymy PrintWriter do wyslania danych
        PrintWriter pw = new PrintWriter(connection.getSocket().getOutputStream(), true);
        try {
            connection.sendMessage(textField, pw);
            chatTextArea.appendText("\n Ja:  " + textField.getText() + "\n");
            textField.setText("");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void disconnect(MouseEvent event) throws IOException {
        Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION, "Confirm", ButtonType.OK, ButtonType.CANCEL);
        Window owner = ((Node) event.getTarget()).getScene().getWindow();
        exitAlert.setContentText("Czy napewno chcesz się rozłączyć??");
        exitAlert.initModality(Modality.APPLICATION_MODAL);
        exitAlert.initOwner(owner);
        exitAlert.showAndWait();

        if (exitAlert.getResult() == ButtonType.OK) {
            connection.getSocket().close();
            Main.changeScene("sample.fxml");
        } else {
            exitAlert.close();
        }
    }


    @FXML
    public void exitProgram(MouseEvent event) {
        usefulFunctions.exitProgram(event);

    }

}
