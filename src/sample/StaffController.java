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
import javafx.stage.Stage;
import sample.DBConnection.ConnectionClass;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class StaffController implements Initializable {

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

    //////
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

    @FXML
    private Button btnLogout;

    @FXML
    private Label lblUser;

    @FXML
    private Label welcomeUser;

    private String username;
    public static String mailaddress;
    private ConnectionClass connectionClass = new ConnectionClass();
    private Connection connection = connectionClass.getConnection();
    private ObservableList<PurchaseList> data = FXCollections.observableArrayList();
    private ObservableList<CustomerList> data2 = FXCollections.observableArrayList();

    @FXML
    void exitAction(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void logOutAction(ActionEvent event) throws IOException {
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
            data2.add(new CustomerList(rs.getString(1),
                    rs.getString(2),
                    rs.getString(4),
                    rs.getInt(5),
                    rs.getString(6)));
            tableCustomerDetails.setItems(data2);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCellPurchaseTable();
        setCellCustomerTable();
        try {
            loadDataPurchaseTableDatabase();
            loadDataCustomerTableDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            String sql = "SELECT StaffName FROM StaffDetails WHERE  UserName = ('" + mailaddress + "')";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                username = resultSet.getString("StaffName");
                lblUser.setText(username);
                welcomeUser.setText(username);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
