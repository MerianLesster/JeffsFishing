package sample;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.util.Duration;
import sample.DBConnection.ConnectionClass;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProductController implements Initializable {

    @FXML
    private ChoiceBox<String> cbSelect1;

    @FXML
    private ChoiceBox<String> cbSelect2;

    @FXML
    private ChoiceBox<String> cbSelect3;

    @FXML
    private Label lblnewsLetters;

    @FXML
    private Label lblSuccessModification;

    @FXML
    private TextField txtQty1;

    @FXML
    private TextArea txtSummary1;

    @FXML
    private TextField txtQty2;

    @FXML
    private TextArea txtEnquire;

    @FXML
    private TextArea txtSummary2;

    @FXML
    private TextField txtQty3;

    @FXML
    private TextArea txtSummary3;

    @FXML
    private Label labelEmail;

    @FXML
    private Label labelPassword;

    @FXML
    private Label labelPhoneno;

    @FXML
    private Label lblUser;

    @FXML
    private Label lblPurchases;

    @FXML
    private Label welcomeUser;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtMailAdd;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private PasswordField txtReEnterPassword;

    @FXML
    private RadioButton rdoMale;

    @FXML
    private ToggleGroup genders;

    @FXML
    private RadioButton rdoFemale;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContactNo;

    @FXML
    private Button btnModifyAccount;

    @FXML
    private Button btnLogout;

    public static String mailaddress;
    String username;
    private ConnectionClass connectionClass = new ConnectionClass();
    private Connection connection = connectionClass.getConnection();

    @FXML
    public void logOutAction(ActionEvent actionEvent) throws IOException {
        btnLogout.getScene().getWindow().hide();
        Stage register = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        register.setTitle("Login");
        register.setScene(new Scene(root, 700, 400));
        register.show();
        register.setResizable(false);
    }

    @FXML
    void modifyAccount(ActionEvent event) throws SQLException {
        labelPhoneno.setText(null);
        if (txtName.getText().isEmpty() || txtMailAdd.getText().isEmpty() || txtPassword.getText().isEmpty()
                || txtReEnterPassword.getText().isEmpty() || getGender().isEmpty() || txtContactNo.getText().isEmpty() || txtAddress.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the details");
            alert.showAndWait();
        } else {
            PauseTransition pt = new PauseTransition();
            pt.setDuration(Duration.minutes(1));
            pt.play();

            boolean isValidEmail = ValidationClass.isValidEmail(txtMailAdd, labelEmail, "* Invalid email!");
            boolean isPasswordMatched = ValidationClass.isPasswordMatched(txtPassword, txtReEnterPassword, labelPassword, "* Password didn't match");
            boolean isValidPhone = ValidationClass.isValidatePhone(txtContactNo.getText());

            if (isValidEmail && isPasswordMatched && isValidPhone) {

                //Modify to UserDetails table in the Database
                String sql = "UPDATE UserDetails SET Email = '" + txtMailAdd.getText() + "',Password = '" + txtPassword.getText()
                        + "',Gender = '" + getGender() + "',Phone = '" + txtContactNo.getText() + "',Address = '" + txtAddress.getText() + "'where Name='" + txtName.getText() + "'";
                Statement statement = connection.createStatement();
                statement.executeUpdate(sql);
                lblSuccessModification.setText("Account Successfully modified");

                txtMailAdd.setText(null);
                txtPassword.setText(null);
                txtReEnterPassword.setText(null);
                txtContactNo.setText(null);
                txtAddress.setText(null);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Account Successfully Modified");
                alert.setHeaderText(null);
                alert.setContentText("Dear " + txtName.getText() + "! You have successfully modified your account.");
                alert.showAndWait();

            } else if (!isValidPhone) {
                labelPhoneno.setText("* Invalid phone number");
            }
        }
    }

    @FXML
    void sendEnquireAction(ActionEvent event) {
        if(txtEnquire.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Enquire made");
            alert.setHeaderText(null);
            alert.setContentText("Please fill the enquire field");
            alert.showAndWait();
        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Enquire Sent");
            alert.setHeaderText(null);
            alert.setContentText("Your enquire has been sent successfully");
            alert.showAndWait();

            String messageText = txtEnquire.getText();
            EmailClass.sendEmail("JFS Enquire", messageText);
            txtEnquire.setText(null);
        }
    }

    @FXML
    void clickMailAction(MouseEvent event) {
        lblSuccessModification.setText(null);
    }

    private String getGender() {
        String gen = "";
        if (rdoMale.isSelected()) {
            gen = "Male";
        } else if (rdoFemale.isSelected()) {
            gen = "Female";
        }
        return gen;
    }

    @FXML
    void fishingLineAction(ActionEvent event) throws SQLException {

        if (txtQty1.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter the quantity for the product Fishing Line");
            alert.showAndWait();
        } else {
            String productCode = "FL", productName = "Fishing Line";
            int qty = Integer.parseInt(txtQty1.getText());
            int size = Integer.parseInt(cbSelect1.getValue().substring(0, 3));
            double cost = 2.5;
            double total = qty * size * cost;
            txtSummary1.setText("Product: " + productName + "\nQuantity: " + txtQty1.getText() + "\nSize: " + cbSelect1.getValue() + "\nTotal amount: Rs." + total);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Alert");
            alert.setHeaderText(null);
            alert.setContentText("Do you want to purchase this product?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                //Add to Database
                String sql = "INSERT INTO PurchaseDetails (CustomerName, ProductCode, ProductName, Size, Quantity, Cost) VALUES ('" + txtName.getText()
                        + "','" + productCode + "','" + productName + "','"
                        + cbSelect1.getValue() + "','" + qty + "','" + total + "')";
                Statement statement = connection.createStatement();
                statement.executeUpdate(sql);

                noOfPurchases();
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Purchase Successful");
                alert.setHeaderText(null);
                alert.setContentText("Dear " + txtName.getText() + "! You have successfully purchased your product. The Invoice will be sent to your email.\n" +
                        "Thank you for purchasing!");
                alert.showAndWait();
                txtSummary1.setText(null);

                EmailClass.userName = txtName.getText();
                EmailClass.productDesc = productName;
                EmailClass.productCode = productCode;
                EmailClass.productSize = cbSelect1.getValue();
                EmailClass.productQty = qty;
                EmailClass.cost = cost;
                EmailClass.total = total;
                String invoice = EmailClass.invoiceDetails();
                EmailClass.sendEmail("Invoice Letter", invoice);

            } else {
                txtSummary1.setText(null);
            }
        }
    }

    @FXML
    void sinkersAction(ActionEvent event) throws SQLException {

        if (txtQty2.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter the quantity for the product Sinkers");
            alert.showAndWait();
        } else {
            String productCode = "SK", productName = "Sinkers";
            int qty = Integer.parseInt(txtQty2.getText());
            int size = Integer.parseInt(cbSelect2.getValue().substring(0, 2));
            int cost = 10;
            double total = qty * size * cost;
            txtSummary2.setText("Product: " + productName + "\nQuantity: " + txtQty2.getText() + "\nSize: " + cbSelect2.getValue() + "\nTotal amount: Rs." + total);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Alert");
            alert.setHeaderText(null);
            alert.setContentText("Do you want to purchase this product?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                //Add to Database
                String sql = "INSERT INTO PurchaseDetails (CustomerName, ProductCode, ProductName, Size, Quantity, Cost) VALUES ('" + txtName.getText()
                        + "','" + productCode + "','" + productName + "','"
                        + cbSelect2.getValue() + "','" + qty + "','" + total + "')";
                Statement statement = connection.createStatement();
                statement.executeUpdate(sql);

                noOfPurchases();
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Purchase Successful");
                alert.setHeaderText(null);
                alert.setContentText("Dear " + txtName.getText() + "! You have successfully purchased your product. The Invoice will be sent to your email.\n" +
                        "Thank you for purchasing!");
                alert.showAndWait();
                txtSummary2.setText(null);

                EmailClass.userName = txtName.getText();
                EmailClass.productDesc = productName;
                EmailClass.productCode = "\t" + productCode;
                EmailClass.productSize = cbSelect2.getValue();
                EmailClass.productQty = qty;
                EmailClass.cost = cost;
                EmailClass.total = total;
                String invoice = EmailClass.invoiceDetails();
                EmailClass.sendEmail("Invoice Letter", invoice);

            } else {
                txtSummary2.setText(null);
            }
        }
    }

    @FXML
    void swivelsAction(ActionEvent event) throws SQLException {
        String productCode = "SW", productName = "Swivels";
        int size = 0;
        if (cbSelect3.getValue().equals("Small")) {
            size = 250;
        } else if (cbSelect3.getValue().equals("Medium")) {
            size = 500;
        } else if (cbSelect3.getValue().equals("Large")) {
            size = 750;
        }

        if (txtQty3.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter the quantity for the product" + productName);
            alert.showAndWait();
        } else {
            int qty = Integer.parseInt(txtQty3.getText());
            double total = qty * size;
            txtSummary3.setText("Product:  " + productName + "\nQuantity: " + txtQty3.getText() + "\nSize: " + cbSelect3.getValue() + "\nTotal amount: Rs." + total);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Alert");
            alert.setHeaderText(null);
            alert.setContentText("Do you want to purchase this product?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                //Add to Database
                String sql = "INSERT INTO PurchaseDetails (CustomerName, ProductCode, ProductName, Size, Quantity, Cost) VALUES ('" + txtName.getText()
                        + "','" + productCode + "','" + productName + "','"
                        + cbSelect3.getValue() + "','" + qty + "','" + total + "')";
                Statement statement = connection.createStatement();
                statement.executeUpdate(sql);

                noOfPurchases();
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Purchase Successful");
                alert.setHeaderText(null);
                alert.setContentText("Dear " + txtName.getText() + "! You have successfully purchased your product. The Invoice will be sent to your email.\n" +
                        "Thank you for purchasing!");
                alert.showAndWait();
                txtSummary3.setText(null);

                EmailClass.userName = txtName.getText();
                EmailClass.productDesc = productName;
                EmailClass.productCode = "\t" + productCode;
                EmailClass.productSize = cbSelect3.getValue();
                EmailClass.productQty = qty;
                EmailClass.cost = size;
                EmailClass.total = total;
                String invoice = EmailClass.invoiceDetails();
                EmailClass.sendEmail("Invoice Letter", invoice);

            } else {
                txtSummary3.setText(null);
            }
        }
    }

    @FXML
    void numberOnlyAction(KeyEvent event) {
        char num = event.getCharacter().charAt(0);
        if (!(Character.isDigit(num))) {
            event.consume();
        }
    }

    private void noOfPurchases(){
        try {
            String sql2 = "SELECT COUNT(*) FROM PurchaseDetails WHERE  CustomerName = ('" + username + "')";
            Statement statement = connection.createStatement();
            ResultSet resultSet2 = statement.executeQuery(sql2);
            while (resultSet2.next()) {
                String count = resultSet2.getString(1);
                lblPurchases.setText(count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void newsLetterAction(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Signin For News Letters");
        dialog.setHeaderText(null);
        dialog.setContentText("Please enter your Email: ");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            lblnewsLetters.setText("NewsLetters - Signed-in");
        }else {
            lblnewsLetters.setText(null);
        }
    }

    @FXML
    void exitAction(ActionEvent event) {
        System.exit(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            String sql = "SELECT Name FROM UserDetails WHERE  Email = ('" + mailaddress + "')";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                username = resultSet.getString("name");
                lblUser.setText(username);
                welcomeUser.setText(username);
                txtName.setText(username);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        noOfPurchases();

        // Add elements to Fishing Line choice box
        cbSelect1.getItems().addAll("100m", "200m", "300m");
        cbSelect1.setValue("100m");
        // Add elements to Sinkers choice box
        cbSelect2.getItems().addAll("30g", "50g", "80g");
        cbSelect2.setValue("30g");
        // Add elements to Swivels choice box
        cbSelect3.getItems().addAll("Small", "Medium", "Large");
        cbSelect3.setValue("Small");
    }
}
