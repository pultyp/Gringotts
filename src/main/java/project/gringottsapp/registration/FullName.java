package project.gringottsapp.registration;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import project.gringottsapp.firstPage.HelloController;
import project.gringottsapp.loader.SceneLoader;

public class FullName {
	 @FXML
	 private Label hello_Label;
	 @FXML
	 private TextField registration_FirstName;
	 @FXML
	 private TextField registration_LastName;
	 @FXML
	 private Button button_Next;
	 @FXML
	 private Label errorMessage;
	 @FXML
	 private Button back;
	 private static String firstName;
	 private static String lastName;

	 SceneLoader sl = new SceneLoader();
	 @FXML
	 void initialize() {
		  button_Next.setOnAction(actionEvent -> {
				if (!registration_FirstName.getText().isEmpty() && !registration_LastName.getText().isEmpty()) {
					 firstName = registration_FirstName.getText().trim();
					 lastName = registration_LastName.getText().trim();
					 if (checkName(firstName) && checkName(lastName)) {
						  LastStep ls = new LastStep();
						  firstName = beatufy(firstName);
						  lastName = beatufy(lastName);
						  ls.setFirstName(firstName);
						  ls.setLastName(lastName);
						  button_Next.getScene().getWindow().hide();
						  sl.openNewScene("registration_phone_number.fxml");
					 } else {
						  errorMessage.setVisible(true);
					 }
				}
		  });
		  back.setOnAction(actionEvent -> {
				back.getScene().getWindow().hide();
				sl.openNewScene("firstPage.fxml");
		  });
	 }
	 private boolean checkName(String name) {
		  return name.matches("[а-яА-Я]+");
	 }
	 private String beatufy(String name) {
		  StringBuilder sb = new StringBuilder(name.toLowerCase());
		  sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
		  String newString = sb.toString();
		  return newString;
	 }
}
