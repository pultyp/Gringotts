package project.gringottsapp.registration;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import project.gringottsapp.database.Const;
import project.gringottsapp.database.DataBaseHandler;
import project.gringottsapp.loader.SceneLoader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNumber extends FullName {
	 @FXML
	 private Button button_Next;
	 @FXML
	 private Label hello_Label;
	 @FXML
	 private TextField phone_number;
	 @FXML
	 private Label errorMessage;
	 @FXML
	 private Label errorMessage_db;
	 @FXML
	 private Button back;
	 private static String  phoneNumber;


	 void initialize() {
		  button_Next.setOnAction(actionEvent -> {
				if (!phone_number.getText().isEmpty() && numberChecker(phone_number.getText().trim())) {
					 DataBaseHandler dbHandler = new DataBaseHandler();
					 if (dbHandler.notExistInDB(Const.PHONE_NUMBER, phone_number.getText().trim())) {
						  phoneNumber = phone_number.getText().trim();
						  LastStep ls = new LastStep();
						  ls.setPhonenumber(phoneNumber);
						  SceneLoader sl = new SceneLoader();
						  button_Next.getScene().getWindow().hide();
						  sl.openNewScene("registration_last_step.fxml");
					 } else {
						  errorMessage_db.setVisible(true);
					 }
				} else {
					 errorMessage.setVisible(true);
				}
		  });
		  back.setOnAction(actionEvent -> {
				back.getScene().getWindow().hide();
				sl.openNewScene("firstPage.fxml");
		  });
	}

	private boolean numberChecker(String number) {
		 // Проверка номера телефона с использованием регулярного выражения
		 String regex = "^8\\d{10}$";
		 Pattern pattern = Pattern.compile(regex);
		 Matcher matcher = pattern.matcher(number);
		 return matcher.matches();
	}
}

