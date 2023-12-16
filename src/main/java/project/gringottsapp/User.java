package project.gringottsapp;

public class User {
	 private String firstName;
	 private String lastName;
	 private String phone_number;
	 private String password;
	 private String login;
	 public User(String firstName, String lastName, String phone_number,String password, String login) {
		  this.firstName = firstName;
		  this.lastName = lastName;
		  this.phone_number = phone_number;
		  this.password = password;
		  this.login = login;
	 }

	 public User() {
	 }

	 public String getFirstName() {
		  return firstName;
	 }
	 public String getLastName() {
		  return lastName;
	 }
	 public String getPhone_number() {
		  return phone_number;
	 }
	 public String getPassword() {
		  return password;
	 }
	 public void setPassword(String password) {
		  this.password = password;
	 }
	 public String getLogin() {
		  return login;
	 }
	 public void setLogin(String login) {
		  this.login = login;
	 }
}
