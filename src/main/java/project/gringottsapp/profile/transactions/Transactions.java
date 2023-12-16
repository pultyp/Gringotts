package project.gringottsapp.profile.transactions;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import project.gringottsapp.database.Const;
import project.gringottsapp.database.DataBaseHandler;
import project.gringottsapp.profile.profile_page.ProfileController;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class Transactions {
	 @FXML
	 private TextField phoneNumber_field;
	 @FXML
	 private TextField sum_field;
	 @FXML
	 private Button transaction_button;
	 Alert message = new Alert(Alert.AlertType.INFORMATION);
	 ProfileController pl = new ProfileController();
	 Transactions_DB transactionsDb = new Transactions_DB();
	 private int newBalance;
	 private int operationId;
	 private Timestamp date;
	 private int amount;
	 private String recipient;
	 private static int userId;
	 private boolean transactionCompleted;

	 public TextField getPhoneNumber_field() {
		  return phoneNumber_field;
	 } // допилить у получателя историю транзакций

	 public int getOperationId() {
		  return operationId;
	 }

	 public Timestamp getDate() {
		  return date;
	 }

	 public int getAmount() {
		  return amount;
	 }

	 public String getRecipient() {
		  return recipient;
	 }

	 public int getUserId() {
		  return userId;
	 }
	 public boolean getTransactionState() {return transactionCompleted;}
	 public Transactions(int operationId, Timestamp date, int amount, String recipient, int userId) {
		  this.operationId = operationId;
		  this.date = date;
		  this.amount = amount;
		  this.recipient = recipient;
		  this.userId = userId;
	 }
	 public Transactions (){}


	 @FXML
	 void initialize() {
		  transactionCompleted = false;
		  transaction_button.setOnAction(actionEvent1 -> {
				String phoneNumber = phoneNumber_field.getText().trim();
				int sum = Integer.parseInt(sum_field.getText());
				if (balanceChecker(pl.getId(), sum) && numberChecker(phoneNumber)) {
						  transaction(phoneNumber, sum);
						  newBalance = minusBalance(pl.getId(), sum);
						  message.setTitle("Информация о транзакции");
						  message.setHeaderText("Транзакция проведена успешно");
						  message.showAndWait();
						  transactionsDb.addTransaction(pl.getId(), sum, phoneNumber);
						  printTransactions(pl.getId());
						  transactionCompleted = true;
						  transaction_button.getScene().getWindow().hide();
				} else {
					 message.setTitle("Информация о транзакции");
					 message.setHeaderText("Операция не совершена");
					 message.setContentText("На балансе недостаточно средств");
					 message.showAndWait();
					 transaction_button.getScene().getWindow().hide();
				}
		  });
	 }

	 protected void printTransactions(int id) {
		  Transactions_DB transactionsDB = new Transactions_DB();
		  int limit = transactionsDB.getTransactionCountForUser(id);
		  limit = Math.min(5, limit);
		  List<Transactions> transactions = transactionsDB.getLastTransactionsForUser(id, limit);
	 }
	 public String handleWindowClose() {
		  return newBalance + " рублей";
	 }
	 private void transaction(String phoneNumber, int sum) {
		  DataBaseHandler dbHandler = new DataBaseHandler();

		  int balance = 0;
		  ResultSet userBlanace = getBalance(phoneNumber);
		  while(true) {
				try {
					 if (!userBlanace.next()) break;
				} catch (SQLException e) {
					 throw new RuntimeException(e);
				}
				try {
					 balance = userBlanace.getInt("balance");
				} catch (SQLException e) {
					 throw new RuntimeException(e);
				}
		  }
		  int newBalance = balance + sum;
		  String update = "UPDATE " + Const.USER_TABLE + " SET " + Const.USER_BALANCE + "=?" + " WHERE " +
					 Const.PHONE_NUMBER + "=?";

		  try {
				PreparedStatement prSt = dbHandler.getDbConnection().prepareStatement(update);
				prSt.setInt(1, newBalance);
				prSt.setString(2, phoneNumber);

				int rowsAffected = prSt.executeUpdate();


		  } catch (SQLException e) {
				throw new RuntimeException(e);
		  } catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
		  }
	 }
	 private boolean numberChecker(String phoneNumber) {
		  DataBaseHandler dbHandler = new DataBaseHandler();
		  String select = "SELECT COUNT(*) as count FROM " + Const.USER_TABLE + " WHERE " +
					 Const.PHONE_NUMBER + "=?";
		  try (PreparedStatement prSt = dbHandler.getDbConnection().prepareStatement(select)) {
				prSt.setString(1, phoneNumber);
				ResultSet resultSet = prSt.executeQuery();

				if (resultSet.next()) {
					 int count = resultSet.getInt("count");
					 return count == 1; // Если count больше 0, значит, номер получателя существует
				}
		  } catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace(); // Обработайте исключение в соответствии с вашими потребностями
		  }
		  return false;
	 }
	 private boolean balanceChecker(int id, int sum) {
		  DataBaseHandler dbHandler = new DataBaseHandler();
		  int balance = 0;
		  ResultSet userBlanace = getBalance(id);
		  while(true) {
				try {
					 if (!userBlanace.next()) break;
				} catch (SQLException e) {
					 throw new RuntimeException(e);
				}
				try {
					 balance = userBlanace.getInt("balance");
				} catch (SQLException e) {
					 throw new RuntimeException(e);
				}
		  }
		  if (balance >= sum) {
				return true;
		  } else {
				return false;
		  }
	 }
	 private ResultSet getBalance(String phoneNumber) {
		  DataBaseHandler dbHandler = new DataBaseHandler();
		  ResultSet resultSet = null;
		  String select = "SELECT " + Const.USER_BALANCE + " FROM " + Const.USER_TABLE + " WHERE " +
					 Const.PHONE_NUMBER + "=?";
		  try {
				PreparedStatement prSt = dbHandler.getDbConnection().prepareStatement(select);
				prSt.setString(1, phoneNumber);

				resultSet = prSt.executeQuery(); // получаем данные из БД с помощью этого метода
		  } catch (SQLException e) {
				throw new RuntimeException(e);
		  } catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
		  }

		  return resultSet;
	 }
	 private ResultSet getBalance(int id) {
		  DataBaseHandler dbHandler = new DataBaseHandler();
		  ResultSet resultSet = null;
		  String select = "SELECT " + Const.USER_BALANCE + " FROM " + Const.USER_TABLE + " WHERE " +
					 Const.USERS_ID + "=?";
		  try {
				PreparedStatement prSt = dbHandler.getDbConnection().prepareStatement(select);
				prSt.setInt(1, id);


				resultSet = prSt.executeQuery(); // получаем данные из БД с помощью этого метода
		  } catch (SQLException e) {
				throw new RuntimeException(e);
		  } catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
		  }

		  return resultSet;
	 }
	 private int minusBalance(int id, int sum) {
		  DataBaseHandler dbHandler = new DataBaseHandler();

		  int balance = 0;
		  ResultSet userBlanace = getBalance(id);
		  while(true) {
				try {
					 if (!userBlanace.next()) break;
				} catch (SQLException e) {
					 throw new RuntimeException(e);
				}
				try {
					 balance = userBlanace.getInt("balance");
				} catch (SQLException e) {
					 throw new RuntimeException(e);
				}
		  }

		  int newBalance = balance - sum;
		  String update = "UPDATE " + Const.USER_TABLE + " SET " + Const.USER_BALANCE + "=?" + " WHERE " +
					 Const.USERS_ID + "=?";

		  try {
				PreparedStatement prSt = dbHandler.getDbConnection().prepareStatement(update);
				prSt.setInt(1, newBalance);
				prSt.setInt(2, id);

				int rowsAffected = prSt.executeUpdate();

		  } catch (SQLException e) {
				throw new RuntimeException(e);
		  } catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
		  }
		  return newBalance;
	 }
}