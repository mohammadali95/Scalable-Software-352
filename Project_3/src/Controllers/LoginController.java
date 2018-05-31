package Controllers;




import java.io.IOException;
import java.sql.SQLException;

import Data.Database;
import LoginValidation.LoginValidation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginController {
    @FXML
    Button loginButton;

    @FXML
    ImageView img;

    @FXML
    TextField passwordField;

    public static int maxTries = 5;

    @FXML
    Label instructions;

    BadNews badNews;

    Stage stage = Main.getStage();

    Database database;

    LoginValidation validator;

    @FXML
    private void initialize() throws SQLException, ClassNotFoundException {
        validator = new LoginValidation();
        badNews = new BadNews();
        // if database exists,
        if (validator.passwordExists()) {
            database = new Database("Passwords");
            if (!database.canLogin()) {
                instructions.setTextFill(Color.RED);
                instructions.setText("Password manager locked until \n" + database.prettyPrintNextLoginTime());
            } else {
                instructions.setText("Enter your password below.");
                loginButton.setOnAction((event) -> {
                    try {
                        if (validator.validate(passwordField.getText()) && database.attemptedTries() <= maxTries) {
                            database.resetTries();
                            goToMain();
                        } else {
                            database.incAttemptedTries();
                            checkPasswordLimit();
                        }
                    } catch (SQLException | ClassNotFoundException | IOException e) {
                        e.printStackTrace();
                        badNews.display("Internal error. :(");
                    }
                });
            }
        } else {
            instructions.setText("Welcome to your password manager. \nEnter a password below to be used in future logins.");
            loginButton.setOnAction((event) -> {
                try {
                    setupDatabase();
                    goToMain();
                } catch (ClassNotFoundException|SQLException|IOException e) {
                    e.printStackTrace();
                    badNews.display("Internal error. :(");
                }
            });
        }
    }

    private void checkPasswordLimit() throws SQLException {
        int attemptedTries = database.attemptedTries();

        instructions.setTextFill(Color.RED);
        instructions.setText("Incorrect password. " + String.valueOf(maxTries - attemptedTries) + " tries remaining. ");
        if (attemptedTries >= maxTries) {
            database.triesMaxed();

            instructions.setText("Password manager locked until \n" + database.prettyPrintNextLoginTime());
        }
    }

    private void setupDatabase() throws SQLException, ClassNotFoundException {
        database = new Database("Passwords");
        database.createTables();
        String plainText = passwordField.getText();
        String hashedPassword = validator.hashPassword(passwordField.getText());
        database.setPlainTextMasterPassword(plainText);
        database.setMasterTable(hashedPassword);
        System.out.println(database.getHashedMasterPassword());
        System.out.println(hashedPassword);
    }


    @FXML
    private void goToMain() throws IOException{
        Parent parentLogin = FXMLLoader.load(getClass().getClassLoader().getResource("Resources/MainGUI.fxml"));
        Scene sceneLogin = new Scene(parentLogin, 850, 500);
        stage.setScene(sceneLogin);

        stage.show();
        PasswordTableController.setPlainTextMasterPassword(passwordField.getText());
    }

}
