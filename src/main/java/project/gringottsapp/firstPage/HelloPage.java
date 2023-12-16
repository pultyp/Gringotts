package project.gringottsapp.firstPage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import project.gringottsapp.loader.SceneLoader;

import java.io.IOException;

public class HelloPage extends Application {
	 @Override
	 public void start(Stage stage){
		  SceneLoader sl = new SceneLoader();
		  sl.openNewScene("firstPage.fxml");
	 }
	 public static void main(String[] args) {
		  launch();
	 }
}