module project.gringottsapp {
	 requires javafx.controls;
	 requires javafx.fxml;

	 requires org.kordamp.bootstrapfx.core;
	 requires java.sql;
	 requires org.apache.httpcomponents.httpcore;
	 requires org.apache.httpcomponents.httpclient;
	 requires android.json;

	 opens project.gringottsapp to javafx.fxml;
	 exports project.gringottsapp;
	 exports project.gringottsapp.registration;
	 opens project.gringottsapp.registration to javafx.fxml;
	 exports project.gringottsapp.profile.profile_page;
	 opens project.gringottsapp.profile.profile_page to javafx.fxml;
	 exports project.gringottsapp.profile.transactions;
	 opens project.gringottsapp.profile.transactions to javafx.fxml;
	 exports project.gringottsapp.profile.api;
	 opens project.gringottsapp.profile.api to javafx.fxml;
	 exports project.gringottsapp.database;
	 opens project.gringottsapp.database to javafx.fxml;
	 exports project.gringottsapp.authorization;
	 opens project.gringottsapp.authorization to javafx.fxml;
	 exports project.gringottsapp.firstPage;
	 opens project.gringottsapp.firstPage to javafx.fxml;
}