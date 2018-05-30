package LoginValidation;
import Data.Database;

import java.io.File;
import java.sql.SQLException;


public class LoginValidation {

    private Database database;
    private int numTries = 6;



    public Boolean validate(String passwordAttempt) throws SQLException, ClassNotFoundException {
        database = new Database("Passwords");
        String correctHashedPassword = database.getHashedMasterPassword();
        System.out.println(correctHashedPassword);
        return BCrypt.checkpw(passwordAttempt, correctHashedPassword);
    }


    public static String hashPassword(String password) throws SQLException {
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(password, salt);
    }

    public static Boolean passwordExists() throws SQLException, ClassNotFoundException {
        File file = new File("Passwords");
        return file.exists();
    }

//    public void resetPassword(String oldPassword, String newPassword) throws SQLException {
//        // TODO drop old password from database
//        setUpPassword(newPassword);
//    }



}
