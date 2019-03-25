package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import sample.DBConnection.ConnectionClass;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML
    private Button btnLogout;

    @FXML
    private TextField txtStaffName;

    @FXML
    private TextField txtStaffUserName;

    @FXML
    private TextField txtSearch;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private PasswordField txtReEnterPassword;

    @FXML
    private TextField txtContactNo;

    @FXML
    private Label labelPassword;

    @FXML
    private Label lblUserNameError;

    @FXML
    private Label lblSuccessModification;

    @FXML
    private Label labelPhoneno;

    @FXML
    private TextField txtId;

    @FXML
    private TableView<PurchaseList> tablePurchase;

    @FXML
    private TableColumn<?, ?> colCusName;

    @FXML
    private TableColumn<?, ?> colCode;

    @FXML
    private TableColumn<?, ?> colProductName;

    @FXML
    private TableColumn<?, ?> colSize;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colCost;

    ////
    @FXML
    private TableView<StaffAccountList> tableStaffAccount;

    @FXML
    private TableColumn<?, ?> colSID;

    @FXML
    private TableColumn<?, ?> colSName;

    @FXML
    private TableColumn<?, ?> colSUName;

    @FXML
    private TableColumn<?, ?> colSPass;

    @FXML
    private TableColumn<?, ?> colSNo;

    ////
    @FXML
    private TableView<CustomerList> tableCustomerDetails;

    @FXML
    private TableColumn<?, ?> colCustomerName;

    @FXML
    private TableColumn<?, ?> colCusMail;

    @FXML
    private TableColumn<?, ?> colCusGender;

    @FXML
    private TableColumn<?, ?> colCusPhone;

    @FXML
    private TableColumn<?, ?> colCusAddress;

    private ConnectionClass connectionClass = new ConnectionClass();
    private Connection connection = connectionClass.getConnection();
    private ObservableList<PurchaseList> data = FXCollections.observableArrayList();
    private ObservableList<StaffAccountList> data2 = FXCollections.observableArrayList();
    private ObservableList<CustomerList> data3 = FXCollections.observableArrayList();

    @FXML
    void addAccount(ActionEvent event) throws SQLException {
        labelPhoneno.setText(null);
        if (txtId.getText().isEmpty() || txtStaffName.getText().isEmpty() || txtStaffUserName.getText().isEmpty() || txtPassword.getText().isEmpty()
                || txtReEnterPassword.getText().isEmpty() || txtContactNo.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Registration Failed");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the details");
            alert.showAndWait();

        } else {
            lblUserNameError.setText(null);
            boolean isPasswordMatched = ValidationClass.isPasswordMatched(txtPassword, txtReEnterPassword, labelPassword, "* Password didn't match");
            boolean isValidPhone = ValidationClass.isValidatePhone(txtContactNo.getText());
            boolean correctUsername = txtStaffUserName.getText().startsWith("#");

            if (isPasswordMatched && isValidPhone && correctUsername) {

                ConnectionClass connectionClass = new ConnectionClass();
                Connection connection = connectionClass.getConnection();

                //Add Staff to the Database
                String sql = "INSERT INTO StaffDetails (ID, StaffName, UserName, Password, ContactNo) VALUES ('" + txtId.getText()
                        + "','" + txtStaffName.getText() + "','" + txtStaffUserName.getText() + "','"
                        + txtPassword.getText() + "','" + txtContactNo.getText() + "')";
                Statement statement = connection.createStatement();
                statement.executeUpdate(sql);
                setCellStaffTable();
                loadDataStaffTableDatabase();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Registration Successful");
                alert.setHeaderText(null);
                alert.setContentText("Dear Jeff! You have successfully added " + txtStaffName.getText() + " as a Staff.");
                lblSuccessModification.setText("Staff Successfully Added");
                alert.showAndWait();
                lblSuccessModification.setText(null);
                txtStaffName.setText(null);
                txtStaffUserName.setText(null);
                txtPassword.setText(null);
                txtReEnterPassword.setText(null);
                txtContactNo.setText(null);
            } else if (!isValidPhone) {
                labelPhoneno.setText("* Invalid phone number");
            } else if (!correctUsername) {
                lblUserNameError.setText("* The Staff Username \n  should start with #");
            }
        }
    }

    @FXML
    void modifyAccount(ActionEvent event) throws SQLException {
        labelPhoneno.setText(null);
        if (txtId.getText().isEmpty() || txtStaffName.getText().isEmpty() || txtStaffUserName.getText().isEmpty() || txtPassword.getText().isEmpty()
                || txtReEnterPassword.getText().isEmpty() || txtContactNo.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Modification Failed");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the details");
            alert.showAndWait();

        } else {
            boolean isPasswordMatched = ValidationClass.isPasswordMatched(txtPassword, txtReEnterPassword, labelPassword, "* Password didn't match");
            boolean isValidPhone = ValidationClass.isValidatePhone(txtContactNo.getText());
            boolean correctUsername = txtStaffUserName.getText().startsWith("#");

            if (isPasswordMatched && isValidPhone && correctUsername) {

                ConnectionClass connectionClass = new ConnectionClass();
                Connection connection = connectionClass.getConnection();

                //Modify Staff from the Database
                String sql = "UPDATE StaffDetails SET StaffName = '" + txtStaffName.getText() + "',UserName = '" + txtStaffUserName.getText()
                        + "',Password = '" + txtPassword.getText() + "',ContactNo = '" + txtContactNo.getText() + "'WHERE ID='" + txtId.getText() + "'";
                Statement statement = connection.createStatement();
                statement.executeUpdate(sql);
                setCellStaffTable();
                loadDataStaffTableDatabase();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Modification Successful");
                alert.setHeaderText(null);
                alert.setContentText("Dear Jeff! You have successfully modified " + txtStaffName.getText() + " details.");
                lblSuccessModification.setText("Account Successfully modified");
                alert.showAndWait();
                lblSuccessModification.setText(null);
                txtStaffName.setText(null);
                txtStaffUserName.setText(null);
                txtPassword.setText(null);
                txtReEnterPassword.setText(null);
                txtContactNo.setText(null);

            } else if (!isValidPhone) {
                labelPhoneno.setText("* Invalid phone number");
            } else if (!correctUsername) {
                lblUserNameError.setText("* The Staff Username should start with #");

            }
        }
    }

    @FXML
    void deleteAccount(ActionEvent event) throws SQLException {
        if (txtId.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Account Failed");
            alert.setHeaderText(null);
            alert.setContentText("Please enter the Staff ID and Click on Delete Staff button");
            alert.showAndWait();
        } else {
            ConnectionClass connectionClass = new ConnectionClass();
            Connection connection = connectionClass.getConnection();

            //Delete Staff from the Database
            String sql = "DELETE FROM StaffDetails WHERE ID ='" + txtId.getText() + "'";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            setCellStaffTable();
            loadDataStaffTableDatabase();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Staff Successfully Removed");
            alert.setHeaderText(null);
            alert.setContentText("Dear Jeff! You have successfully removed the Staff.");
            lblSuccessModification.setText("Staff Successfully removed");
            alert.showAndWait();
            lblSuccessModification.setText(null);
        }
    }

    @FXML
    void numberOnlyAction(KeyEvent event) {
        char num = event.getCharacter().charAt(0);
        if (!(Character.isDigit(num))) {
            event.consume();
        }
    }


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

    private void setCellPurchaseTable() {
        colCusName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
    }

    private void loadDataPurchaseTableDatabase() throws SQLException {
        String sql = "SELECT * FROM PurchaseDetails";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            data.add(new PurchaseList(rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getInt(5),
                    rs.getDouble(6)));
            tablePurchase.setItems(data);
        }
    }

    private void setCellStaffTable() {
        colSID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSUName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colSPass.setCellValueFactory(new PropertyValueFactory<>("password"));
        colSNo.setCellValueFactory(new PropertyValueFactory<>("contactNo"));
    }

    private void loadDataStaffTableDatabase() throws SQLException {
        data2.clear();
        String sql = "SELECT * FROM StaffDetails";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            data2.add(new StaffAccountList(rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5)));
            tableStaffAccount.setItems(data2);
        }
    }

    private void setCellCustomerTable() {
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCusMail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colCusGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colCusPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colCusAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
    }

    private void loadDataCustomerTableDatabase() throws SQLException {
        String sql = "SELECT * FROM UserDetails";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            data3.add(new CustomerList(rs.getString(1),
                    rs.getString(2),
                    rs.getString(4),
                    rs.getInt(5),
                    rs.getString(6)));
            tableCustomerDetails.setItems(data3);
        }
    }

    // sort the data using Selection sort method
    public ObservableList<PurchaseList> selectionSort(ObservableList<PurchaseList> ob) {
        for (int i = 0; i < ob.size() - 1; i++) {
            int index = i;
            for (int j = i + 1; j < ob.size(); j++) {

                if (ob.get(j).getCustomerName().compareTo(ob.get(index).getCustomerName()) < 0) {
                    index = j;//searching for lowest index
                }
            }
            PurchaseList smallerNumber = ob.get(index);
            ob.set(index, ob.get(i));
            ob.set(i, smallerNumber);

        }
        return ob;
    }

    // search the data using Binary search method
    ObservableList<PurchaseList> binarySearch(ObservableList o, String name) {
        ObservableList<PurchaseList> ls = o;
        ObservableList<PurchaseList> n = FXCollections.observableArrayList();
        int l = 0, r = ls.size() - 1;

        for (PurchaseList p : ls)
        {
            int m = l + (r - l) / 2;
            if (p.getCustomerName().equalsIgnoreCase(name)) {
                n.add(p);
            }
            if (p.getCustomerName().compareTo(name) < 0) {

                l = m + 1;
            }
            else
                r = m - 1;
        }
        return n;
    }

    @FXML
    void searchAction(ActionEvent event) {
        ObservableList o = selectionSort(data);
        o = binarySearch(o, txtSearch.getText());
        tablePurchase.setItems(null);
        tablePurchase.setItems(o);
    }

    @FXML
    void exitAction(ActionEvent event) {
        System.exit(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCellPurchaseTable();
        setCellStaffTable();
        setCellCustomerTable();
        try {
            loadDataPurchaseTableDatabase();
            loadDataStaffTableDatabase();
            loadDataCustomerTableDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
