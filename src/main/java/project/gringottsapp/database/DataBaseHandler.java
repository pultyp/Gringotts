package project.gringottsapp.database;

import project.gringottsapp.User;
import project.gringottsapp.database.Configs;
import project.gringottsapp.database.Const;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class DataBaseHandler extends Configs {
	 Connection dbConnection;

	 public Connection getDbConnection () throws
				ClassNotFoundException, SQLException {
		  Class.forName("com.mysql.cj.jdbc.Driver");
		String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;


		dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPassword);

		return dbConnection;
	 }

	 public void signUpUser (User user) {
		  String insert = "INSERT INTO " + Const.USER_TABLE + "(" + Const.FIRST_NAME + "," + Const.LAST_NAME + ","
					 + Const.USER_LOGINS + "," + Const.PASSWORDS + "," + Const.PHONE_NUMBER + ")" +
					 "VALUES(?,?,?,?,?)"; // SQL запрос для регистрации пользователя

		  try {
				PreparedStatement prSt = getDbConnection().prepareStatement(insert);
				prSt.setString(1, user.getFirstName());
				prSt.setString(2, user.getLastName());
				prSt.setString(3, user.getLogin());
				prSt.setString(4, user.getPassword());
				prSt.setString(5, user.getPhone_number());

				prSt.executeUpdate(); // вносим данные в БД
		  } catch (SQLException | ClassNotFoundException e) {
				throw new RuntimeException(e);
		  }


	 }
	 public ResultSet getUser(User user) {
		  ResultSet resultSet = null;

		  String select =  "SELECT * FROM " + Const.USER_TABLE + " WHERE " + Const.USER_LOGINS + "=? AND " +
					 Const.PASSWORDS + "=?";

		  try {
				PreparedStatement prSt = getDbConnection().prepareStatement(select);
				prSt.setString(1, user.getLogin());
				prSt.setString(2, user.getPassword());

				resultSet = prSt.executeQuery(); // получаем данные из БД с помощью этого метода
		  } catch (SQLException | ClassNotFoundException e) {
				throw new RuntimeException(e);
		  }
		  return resultSet;
	 }

	 public boolean notExistInDB(String fieldName, String valueToCheck) {
		  String query = "SELECT COUNT(*) FROM " + Const.USER_TABLE + " WHERE " + fieldName + " = ?";
		  try{
				 PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
				preparedStatement.setString(1, valueToCheck);
				ResultSet resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					 int count = resultSet.getInt(1);
					 return count == 0;
				}
		  } catch (SQLException e) {
				e.printStackTrace();
		  } catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
		  }
		  return false;
	 }
}
