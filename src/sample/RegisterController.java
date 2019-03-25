package sample;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.DBConnection.ConnectionClass;
import javafx.scene.input.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;


public class RegisterController implements Initializable {

    @FXML
    private Button btnLogin;

    @FXML
    private TextField txtMailAdd;

    @FXML
    private TextField txtAddress;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnRegister;

    @FXML
    private ImageView picLoading;

    @FXML
    private Label labelEmail;

    @FXML
    private Label labelPassword;

    @FXML
    private TextField txtName;

    @FXML
    private PasswordField txtReEnterPassword;

    @FXML
    private RadioButton rdoMale;

    @FXML
    private RadioButton rdoFemale;

    @FXML
    private ToggleGroup genders;

    @FXML
    private TextField txtContactNo;

    @FXML
    private Label labelPhoneno;

    @FXML
    void loginAction(ActionEvent event) throws IOException {
        btnLogin.getScene().getWindow().hide();
        Stage login = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        login.setTitle("Login");
        login.setScene(new Scene(root, 700, 400));
        login.show();
        login.setResizable(false);
    }

    @FXML
    void registerAction(ActionEvent event) throws SQLException, IOException {


        labelPhoneno.setText(null);
        if (txtName.getText().isEmpty() || txtMailAdd.getText().isEmpty() || txtPassword.getText().isEmpty()
                || txtReEnterPassword.getText().isEmpty() || getGender().isEmpty() || txtContactNo.getText().isEmpty() || txtAddress.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Registration Failed");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the details");
            alert.showAndWait();
            picLoading.setVisible(false);
        } else {
            picLoading.setVisible(true);
            PauseTransition pt = new PauseTransition();
            pt.setDuration(Duration.minutes(1));
            pt.play();

            boolean isValidEmail = ValidationClass.isValidEmail(txtMailAdd, labelEmail, "* Invalid email!");
            boolean isPasswordMatched = ValidationClass.isPasswordMatched(txtPassword, txtReEnterPassword, labelPassword, "* Password \n  didn't match");
            boolean isValidPhone = ValidationClass.isValidatePhone(txtContactNo.getText());
          //  boolean pwValidate = ValidationClass.isValidPassword(txtPassword.getText());

            if (isValidEmail && isPasswordMatched && isValidPhone) {

                ConnectionClass connectionClass = new ConnectionClass();
                Connection connection = connectionClass.getConnection();

                //Add to Database
                String sql = "INSERT INTO UserDetails (Name, Email, Password, Gender, Phone, Address) VALUES ('" + txtName.getText()
                        + "','" + txtMailAdd.getText() + "','" + txtPassword.getText() + "','"
                        + getGender() + "','" + txtContactNo.getText() + "','" + txtAddress.getText() + "')";
                Statement statement = connection.createStatement();
                statement.executeUpdate(sql);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Registration Successful");
                alert.setHeaderText(null);
                alert.setContentText("Dear " + txtName.getText() + "! You have successfully registered your account.\nPlease login to purchase our products. Thankyou!");
                alert.showAndWait();
                picLoading.setVisible(false);

                btnRegister.getScene().getWindow().hide();
                Stage login = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
                login.setTitle("Login");
                login.setScene(new Scene(root, 700, 400));
                login.show();
                login.setResizable(false);
            }else if(!isValidPhone){
                labelPhoneno.setText("* Invalid \n   phone number");
            }
        }
    }

    public void numberOnlyAction(KeyEvent event) {
        char num = event.getCharacter().charAt(0);
        if(!(Character.isDigit(num))){
            event.consume();
        }
    }

    public String getGender(){
        String gen = "";
        if(rdoMale.isSelected()){
            gen = "Male";
        } else if(rdoFemale.isSelected()){
            gen = "Female";
        }
        return gen;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        picLoading.setVisible(false);
    }
}
