package project.gringottsapp.firstPage;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import project.gringottsapp.loader.SceneLoader;

public class HelloController {
	 @FXML
	 private Button hello_EnterButton;

	 @FXML
	 private Label hello_Label;

	 @FXML
	 private Button hello_RegistrationButton;
	 SceneLoader sl = new SceneLoader();

	 @FXML
	 void initialize() {
		  hello_EnterButton.setOnAction(ActionEvent -> {
				hello_EnterButton.getScene().getWindow().hide();
				sl.openNewScene("signin.fxml");
		  });

		  hello_RegistrationButton.setOnAction(ActionEvent -> {
				hello_RegistrationButton.getScene().getWindow().hide();
				sl.openNewScene("registration_fullname.fxml");
		  });
	 }
}
