package project.gringottsapp.profile.transactions;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import project.gringottsapp.profile.transactions.Transactions;
import project.gringottsapp.profile.transactions.Transactions_DB;

import java.util.List;

public class TransactionsHistory {
	 @FXML
	 private Label hello_Label;

	 @FXML
	 private ListView listView;
	 Transactions transactions = new Transactions();

	 @FXML
	 void initialize() {
		  setListView(transactions.getUserId());
	 }

	 public void setListView(int id) {
		  Transactions_DB transactionsDB = new Transactions_DB();
		  int limit = transactionsDB.getTransactionCountForUser(id);
		  limit = Math.min(5, limit); // устанавливаем количество транзакций, отображаемых для пользователя
		  List<Transactions> transactions = transactionsDB.getLastTransactionsForUser(id, limit);

		  ObservableList<String> transactionStrings = FXCollections.observableArrayList();
		  for (Transactions transaction : transactions) {
				String transactionString = "ID транзакции: " + transaction.getOperationId() +
						  "\nДата транзакции: " + transaction.getDate() +
						  "\nСумма перевода: " + transaction.getAmount() +
						  "\nПолучатель: " + transaction.getRecipient();
				transactionStrings.add(transactionString);
		  }

		  // transactionListView - это ваш ListView в FXML
		  listView.setItems(transactionStrings);
	 }
}
