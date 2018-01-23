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
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

/*Co do naprawy?
1. opisac lepiej serwer
2. co z datą?
3. opisać jave
4. obsługa błędów?
* */

public class ChatController implements Initializable {
    @FXML
    private Label labelForNick;


    @FXML
    private JFXTextField textField;



    @FXML
    private AnchorPane mainPane;
    @FXML
    private JFXTextArea chatTextArea;

    Connection connection= Connection.getInstance();
    UsefulFunctions usefulFunctions = new UsefulFunctions();

    Thread thread = new Thread(){
        public void run(){
            try {
                // 启动线程时先将用户名发送
                // envoyer le nom du client quand on lance le Thread
                PrintWriter pwr = new PrintWriter(connection.getSocket().getOutputStream(), true);
                pwr.println("  "+connection.getNickname()+" jest online!");

                // 用套接口输入流创建一个Reader以读取其中数据
                // créer un objet Reader en utilisant le InputStream du socket pour récupérer les données dedans
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getSocket().getInputStream()));

                while (true) {
                    // 一直等到套接口有新信息可以接收
                    // attendre jusqu'à ce qu'il y a le nouveau message à recevoir dans le socket
                    while(!connection.getSocket().isClosed() && connection.getSocket().getInputStream().available()<=0)
                        ;

                    if(connection.getSocket().isClosed()){
                        System.out.println("The client thread has been closed");
                        break;
                    }

                    // 打印日期和信息内容
                    // afficher la date et le message
                    Date date = new Date();
                    DateFormat df = DateFormat.getTimeInstance(DateFormat.LONG, Locale.FRANCE);
                    chatTextArea.appendText("\n"+df.format(date)+ ":\n");

                    chatTextArea.appendText(" "+ br.readLine() + '\n');
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

        //connection.receiveMessage(chatTextArea);
        thread.start();


    }
    @FXML
    public void send() throws IOException {
        PrintWriter pw = new PrintWriter(connection.getSocket().getOutputStream(), true);
        try {
            connection.sendMessage(textField,pw);
            chatTextArea.appendText("\n Ja:  "+ textField.getText() + "\n");
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
