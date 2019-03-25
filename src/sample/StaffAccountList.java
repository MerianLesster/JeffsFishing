package sample;

public class StaffAccountList {
    private String id;
    private String name;
    private String userName;
    private String password;
    private String contactNo;

    public StaffAccountList(String id, String name, String userName, String password, String contactNo) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.contactNo = contactNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }
}
