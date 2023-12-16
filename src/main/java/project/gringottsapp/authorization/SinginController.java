package project.gringottsapp.authorization;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import project.gringottsapp.firstPage.HelloController;
import project.gringottsapp.User;
import project.gringottsapp.database.Const;
import project.gringottsapp.database.DataBaseHandler;
import project.gringottsapp.loader.SceneLoader;
import project.gringottsapp.profile.profile_page.ProfileController;

public class SinginController {
	 @FXML
	 private Label emptyField;
	 @FXML
	 private Label notInBD;
	 @FXML
	 private Label hello_Label;
	 @FXML
	 private TextField signin_LoginField;
	 @FXML
	 private PasswordField signin_passwordField;
	 @FXML
	 private Button signin_enterButton;
	 @FXML
	 private Button back;
	 SceneLoader sl = new SceneLoader();
	 DataBaseHandler dataBaseHandler = new DataBaseHandler();

	 @FXML
	 void initialize() {
		  signin_enterButton.setOnAction(actionEvent -> {
				String loginText = signin_LoginField.getText().trim();
				String passwordText = signin_passwordField.getText().trim();
				if (!loginText.equals("") && !passwordText.equals("")) { //проверка на то, что поля заполнены
					 if (!dataBaseHandler.notExistInDB(Const.USER_LOGINS, loginText)) { //проверяем, что пользователь с таким логином есть в бд
						  loginUser(loginText, passwordText);
					 } else {
						  notInBD.setVisible(true);
					 }
				} else {
					 emptyField.setVisible(true);
				}
		  });
		  back.setOnAction(actionEvent -> {
				back.getScene().getWindow().hide();
				sl.openNewScene("firstPage.fxml");
		  });
	 }

	 private void loginUser(String loginText, String passwordText) {
		  DataBaseHandler dbHandler = new DataBaseHandler();
		  User user = new User();
		  user.setLogin(loginText);
		  user.setPassword(passwordText);
		  ResultSet result = dbHandler.getUser(user);

		  int counter = 0;
		  int balance = 0;
		  int id = 0;
		  String firstName;
		  String lastName;
		  String fullName = "";
		  try {
				while (result.next()) {
					 counter++;
					 id = result.getInt("id");
					 balance = result.getInt("balance");
					 firstName = result.getNString("firstname");
					 lastName = result.getNString("lastname");
					 fullName = firstName + " " + lastName;
				}
		  } catch (SQLException e) {
				e.printStackTrace();
		  }

		  if (counter == 1) {
				signin_enterButton.getScene().getWindow().hide();
				FXMLLoader loader = sl.loadFXML("profile.fxml");
				ProfileController pc = loader.getController(); // берем контроллер для работы с полями fxml
				pc.setBalance(balance); // устанавливаем баланс профиля
				pc.setName(fullName);
				pc.setId(id);

				sl.openNewScene(loader);
		  }
	 }
}
