package sample;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;


public class Main extends Application {


    static Stage stage;


    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();

    }

    public static void changeScene(String sceneName) throws IOException {
        //metoda która będzie zmieniać sceny
        //przyjmuje nazwe pliku fxml czyli naszej sceny jaka chcemy przyjac

        Parent root = FXMLLoader.load(Main.class.getResource(sceneName)); //wyjatek
        stage.setScene(new Scene(root));

    }

    public static void fadeTrans(AnchorPane e) {

        FadeTransition x = new FadeTransition(new Duration(1500), e);
        x.setFromValue(0);
        x.setToValue(100);
        x.setCycleCount(1);
        x.setInterpolator(Interpolator.LINEAR);
        x.play();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
