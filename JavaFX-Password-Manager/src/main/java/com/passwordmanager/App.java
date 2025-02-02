package com.passwordmanager;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
/**
 * Hello world!
 *
 */
public class App extends Application
{

	// simulate db storage
	private static String salt = PasswordUtils.generateSalt();
	private static String storedHashedPassword = PasswordUtils.hashPassword("admin", salt);
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		// UI Elements
        Label titleLabel = new Label("Enter Master Password:");
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");
        Label feedbackLabel = new Label("");

        
        loginButton.setOnAction(e->{
        	String passwordInput = passwordField.getText();
        	if (authenticate(passwordInput)) {
        		primaryStage.setScene(getMainScene());
        	} else {
        		feedbackLabel.setText("the password entered is invalid, please try again");
        	}
        });
        
        // layout
        VBox layout = new VBox(10, titleLabel, passwordField,  feedbackLabel, loginButton);
        layout.setAlignment(Pos.CENTER);
        
        // scene
        Scene loginScene = new Scene(layout, 300, 200);
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Password Manager - Login");
        primaryStage.show();
	}
	
	
    private boolean authenticate(String password) {
        return PasswordUtils.validatePassword(password, storedHashedPassword, salt);
    }
	
    // Placeholder for Main Screen (After Login)
    private Scene getMainScene() {
        Label welcomeLabel = new Label("Welcome to Password Manager!");
        VBox layout = new VBox(10, welcomeLabel);
        layout.setAlignment(Pos.CENTER);
        return new Scene(layout, 400, 300);
    }
	
	public static void main( String[] args )
    {
        launch();
    }
}
