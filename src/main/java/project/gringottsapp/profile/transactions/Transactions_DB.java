package project.gringottsapp.profile.transactions;

import project.gringottsapp.database.DataBaseHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Transactions_DB {
	 private final String tableName = "transactions_history";
	 DataBaseHandler dbHandler = new DataBaseHandler();

	 public List<Transactions> getLastTransactionsForUser(int userId, int limit) {
		  List<Transactions> transactions = new ArrayList<>();

		  String query = "SELECT * FROM " + tableName + " WHERE user_id = ? ORDER BY date DESC LIMIT ?";

		  try (PreparedStatement preparedStatement = dbHandler.getDbConnection().prepareStatement(query)) {
				preparedStatement.setInt(1, userId);
				preparedStatement.setInt(2, limit);

				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					 while (resultSet.next()) {
						  int operationId = resultSet.getInt("operation_id");
						  Timestamp date = resultSet.getTimestamp("date");
						  int amount = resultSet.getInt("amount");
						  String recipient = resultSet.getString("recipient");

						  Transactions transaction = new Transactions(operationId, date, amount, recipient, userId);
						  transactions.add(transaction);
					 }
				}
		  } catch (SQLException e) {
				e.printStackTrace(); // Обработка исключений
		  } catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
		  }

		  return transactions;
	 }

	 public void addTransaction(int userId, int amount, String recipient) {
		  String query = "INSERT INTO transactions_history (date, amount, recipient, user_id) VALUES (NOW(), ?, ?, ?)";
		  try (PreparedStatement preparedStatement = dbHandler.getDbConnection().prepareStatement(query)) {
				preparedStatement.setInt(1, amount);
				preparedStatement.setString(2, recipient);
				preparedStatement.setInt(3, userId);

				preparedStatement.executeUpdate();
		  } catch (SQLException e) {
				throw new RuntimeException(e);
		  } catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
		  }
	 }

	 // Метод для получения общего числа транзакций пользователя
	 public int getTransactionCountForUser(int userId) {
		  int count = 0;

		  String query = "SELECT COUNT(*) FROM "+ tableName +" WHERE user_id = ?";
		  try (PreparedStatement preparedStatement = dbHandler.getDbConnection().prepareStatement(query)) {
				preparedStatement.setInt(1, userId);

				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					 if (resultSet.next()) {
						  count = resultSet.getInt(1);
					 }
				}
		  } catch (SQLException e) {
				e.printStackTrace(); // Обработка ошибок в вашем стиле
		  } catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
		  }

		  return count;
	 }
}
