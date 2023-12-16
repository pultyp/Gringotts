package project.gringottsapp.profile.profile_page;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import project.gringottsapp.firstPage.HelloPage;
import project.gringottsapp.loader.SceneLoader;
import project.gringottsapp.profile.api.DollarCourse;
import project.gringottsapp.profile.transactions.Transactions;
import project.gringottsapp.profile.transactions.TransactionsHistory;

public class ProfileController {
	 @FXML
	 public Label profile_balance;
	 @FXML
	 private Label profile_name;
	 @FXML
	 private Button profile_transactions;
	 @FXML
	 private Button history;
	 @FXML
	 private Label dollar;
	 private static int id;
	 private int balanceToSet;
	 private String name;

	 public void setName(String name) {
		  this.name = name;
		  profile_name.setText(name);
	 }

	 public void setId(int id) {
		  this.id = id;
	 }

	 public void setBalance (int balance) {
		  balanceToSet = balance;
		  profile_balance.setText(balanceToSet + " рублей");
		  profile_name.setText(name);
	 }

	 public int getBalanceToSet() {
		  return balanceToSet;
	 }
	 public int getId() {return id;}
	 private boolean isBalanceSet = false;
	 SceneLoader sl = new SceneLoader();

	 @FXML
	 void initialize() {
		  if (!isBalanceSet) { // проверка на то, что условие выполнится только один раз
				int balance = getBalanceToSet(); // берем значение переменной, в которую мы положили баланс из бд
				profile_balance.setText(balance + " рублей");
				isBalanceSet = true; // ставим true, чтобы initialize не выполнился второй раз
				dollar.setText("1 Dollar"  + " = " + DollarCourse.getDollarCourse() + " RUB");

				profile_transactions.setOnAction(actionEvent -> {
					 FXMLLoader loader = sl.loadFXML("transactions.fxml");
					 Transactions transactions = loader.getController();
					 Parent root = loader.getRoot();
					 Stage stage = new Stage();
					 stage.setScene(new Scene(root));
					 Image icon = new Image(HelloPage.class.getResourceAsStream("/images/mainLogo.png"));
					 stage.getIcons().add(icon);
					 stage.centerOnScreen();
					 stage.setResizable(false);
					 stage.setOnHidden((WindowEvent) -> {
						  if (transactions.getTransactionState()) {
								profile_balance.setText(transactions.handleWindowClose()); //это было сложно, мягко говоря
						  }});
					 stage.show();
				});


				history.setOnAction(actionEvent -> {
					 FXMLLoader loader = sl.loadFXML("transactions_history.fxml");

					 TransactionsHistory transactionsHistoryController = loader.getController();
					 transactionsHistoryController.setListView(id); // Передайте необходимые параметры, если нужно

					 sl.openNewScene(loader);
				});
		  }
	 }
}