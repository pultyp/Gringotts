package project.gringottsapp.loader;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import project.gringottsapp.firstPage.HelloPage;

import java.io.IOException;

public class SceneLoader {
	 public FXMLLoader loadFXML(String fileName) { // загружаем fxml
		  FXMLLoader loader = new FXMLLoader();
		  loader.setLocation(getClass().getResource("/project/gringottsapp/" + fileName));
		  try {
				 loader.load();
		  } catch (IOException e) {
				throw new RuntimeException(e);
		  }
		  return loader;
	 }

	 public void openNewScene(String fileName) {
		  FXMLLoader loader = loadFXML(fileName);
		  openNewScene(loader);
	 }

	 public void openNewScene(FXMLLoader loader) { // метод для случаев, когда необходимо отдельно работать с loader'ом
		  Parent root = loader.getRoot();
		  Stage stage = new Stage();
		  stage.setScene(new Scene(root));
		  Image icon = new Image(HelloPage.class.getResourceAsStream("/images/mainLogo.png"));
		  stage.getIcons().add(icon);
		  stage.centerOnScreen();
		  stage.setResizable(false);
		  stage.show();
	 }
}
