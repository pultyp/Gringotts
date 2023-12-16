package project.gringottsapp.registration;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import project.gringottsapp.*;
import project.gringottsapp.database.Const;
import project.gringottsapp.database.DataBaseHandler;
import project.gringottsapp.firstPage.HelloPage;
import project.gringottsapp.profile.profile_page.ProfileController;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LastStep extends PhoneNumber{
	 @FXML
	 private Button button_Next;

	 @FXML
	 private Label hello_Label;

	 @FXML
	 private TextField loginField;

	 @FXML
	 private PasswordField passwordField;

	 @FXML
	 private PasswordField passwordField_repeat;

	 @FXML
	 private Label registration_errorMessage;
	 @FXML
	 private Label registration_errorMessage_db;
	 @FXML
	 private Button back;

	 private static String firstName;
	 private static String lastName;
	 private static String phonenumber;
	 public void setFirstName(String firstName){
		  this.firstName = firstName;
	 }
	 public void setLastName(String lastName){
		  this.lastName = lastName;
	 }
	 public void setPhonenumber(String phonenumber) {
		  this.phonenumber = phonenumber;
	 }
	 DataBaseHandler dbHandler = new DataBaseHandler();
	 void initialize() {
		  button_Next.setOnAction(actionEvent -> {
				checkPassword();
				if (checkPassword() != null){
					 if (dbHandler.notExistInDB(Const.USER_LOGINS, loginField.getText().trim())) {
						  signUpNewUser(checkPassword()); // регистрация пользователя
						  loginUser(loginField.getText().trim(), passwordField.getText().trim());
					 } else {
						  registration_errorMessage_db.setVisible(true);
					 }
				}
		  });
		  back.setOnAction(actionEvent -> {
				back.getScene().getWindow().hide();
				sl.openNewScene("firstPage.fxml");
		  });
	 }
	 private String checkPassword() {
		  String password = null;
		  registration_errorMessage.setVisible(false);
		  if (passwordField.getText().equals(passwordField_repeat.getText())) {
				password = passwordField.getText();
		  } else {
				registration_errorMessage.setVisible(true);
		  }
		  return password;
	}
	 private void signUpNewUser(String password) {
		  DataBaseHandler dbHandler = new DataBaseHandler();

		  User user = new User(firstName, lastName, phonenumber, passwordField.getText().trim(), loginField.getText().trim());

		  dbHandler.signUpUser(user); // вызываем метод для занесения пользователя в БД с конкретными данными
	 }

	 private void loginUser(String loginText, String passwordText) {
		  DataBaseHandler dbHandler = new DataBaseHandler();
		  User user = new User();
		  user.setLogin(loginText);
		  user.setPassword(passwordText);
		  ResultSet result = dbHandler.getUser(user);

		  int counter = 0;
		  int balance = 0;
		  String firstName;
		  String lastName;
		  String fullName="";
		  try {
				while (result.next()) {
					 counter++;
					 balance = result.getInt("balance");
					 firstName = result.getNString("firstname");
					 lastName = result.getNString("lastname");
					 fullName = firstName + " " + lastName;
				}
				System.out.println(balance + " после получения из бд");
		  } catch (SQLException e) {
				e.printStackTrace();
		  }

		  if (counter == 1) {
				button_Next.getScene().getWindow().hide();
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/project/gringottsapp/profile.fxml"));
				try {
					 loader.load();
				} catch (IOException e) {
					 throw new RuntimeException(e);
				}
				ProfileController pc = loader.getController(); // берем контроллер для работы с полями fxml
				pc.setBalance(balance); // устанавливаем баланс профиля
				pc.setName(fullName);


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
}