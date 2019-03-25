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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.DBConnection.ConnectionClass;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


public class LoginController implements Initializable {

    @FXML
    private Button btnLogin;

    @FXML
    public TextField txtUserMail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private ImageView picLoading;

    @FXML
    private Label txtMessageLabel;

    @FXML
    void loginAction(ActionEvent event) throws SQLException, IOException {

        picLoading.setVisible(true);
        PauseTransition pt = new PauseTransition();
        pt.setDuration(Duration.seconds(20));
        pt.play();

        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        Statement statement = connection.createStatement();

        if (txtUserMail.getText().equals("Jeff") && txtPassword.getText().equals("Jeff123")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Admin Login Successful");
            alert.setHeaderText(null);
            alert.setContentText("Welcome " + txtUserMail.getText() + "!");
            txtMessageLabel.setText("  Login Successful");
            txtMessageLabel.setTextFill(Color.GREEN);
            alert.showAndWait();

            btnLogin.getScene().getWindow().hide();
            Stage register = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("adminPage.fxml"));
            register.setTitle("Administrator");
            register.setScene(new Scene(root, 910, 900));
            register.show();
            register.setResizable(false);
        } else if (txtUserMail.getText().startsWith("#")) {
            //Retrieve from Database
            String sql = "SELECT UserName,Password FROM StaffDetails WHERE  UserName = ('" + txtUserMail.getText() + "') && Password = ('" + txtPassword.getText() + "')";
            ResultSet resultSet = statement.executeQuery(sql);
            int count = 0;
            while (resultSet.next()) {
                count = count + 1;
            }
            if (count == 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Staff Login Successful");
                alert.setHeaderText(null);
                alert.setContentText("Welcome " + txtUserMail.getText() + "!");
                txtMessageLabel.setText("  Login Successful");
                txtMessageLabel.setTextFill(Color.GREEN);
                alert.showAndWait();
                StaffController.mailaddress = txtUserMail.getText();

                btnLogin.getScene().getWindow().hide();
                Stage register = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("staffPage.fxml"));
                register.setTitle("Our Products");
                register.setScene(new Scene(root, 910, 900));
                register.show();
                register.setResizable(false);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setHeaderText(null);
                alert.setContentText("Invalid Username and Password! Please try again.");
                alert.showAndWait();
                picLoading.setVisible(false);
                txtMessageLabel.setText("Login Unsuccessful");
            }
        } else {
            //Retrieve from Database
            String sql = "SELECT Email,Password FROM UserDetails WHERE  Email = ('" + txtUserMail.getText() + "') && Password = ('" + txtPassword.getText() + "')";
            ResultSet resultSet = statement.executeQuery(sql);
            int count = 0;
            while (resultSet.next()) {
                count = count + 1;
            }
            if (count == 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("User Login Successful");
                alert.setHeaderText(null);
                alert.setContentText("Welcome " + txtUserMail.getText() + "!");
                txtMessageLabel.setText("  Login Successful");
                txtMessageLabel.setTextFill(Color.GREEN);
                alert.showAndWait();
                ProductController.mailaddress = txtUserMail.getText();
                EmailClass.userMail = txtUserMail.getText();

                btnLogin.getScene().getWindow().hide();
                Stage register = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("product.fxml"));
                register.setTitle("Our Products");
                register.setScene(new Scene(root, 900, 813));
                register.show();
                register.setResizable(false);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setHeaderText(null);
                alert.setContentText("Invalid Username and Password! Please try again.");
                alert.showAndWait();
                picLoading.setVisible(false);
                txtMessageLabel.setText("Login Unsuccessful");
            }
        }

    }

    @FXML
    void registerAction(ActionEvent event) throws IOException {
        btnLogin.getScene().getWindow().hide();
        Stage register = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));
        register.setTitle("Register Account");
        register.setScene(new Scene(root, 701, 652));
        register.show();
        register.setResizable(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        picLoading.setVisible(false);
    }

    public void viewPassword(ActionEvent actionEvent) {
        //String show = txtPassword.getText().toString();
        //txtPassword.setText(show);
    }
}
