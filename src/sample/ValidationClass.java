package sample;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ValidationClass {

    public static boolean isValidPassword(String input){
        return input.matches("[0-9],[a-z]") && input.length() == 10;
    }

    /*public static boolean isValidPassword(TextField tf){
        boolean b=false;
        String pattern="((?=.*\\d) (?=.*[a-z]) (?=.*[A-Z]) (?=.*[@#$%]).{6,15})";
        if(tf.getText().matches(pattern)) {
            b = true;

        }return b;
        }*/

    public static boolean isValidEmail(TextField tf){
        boolean b=false;
        String pattern="\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        if(tf.getText().matches(pattern))
            b=true;
        return b;
    }
    //overloading
    public static boolean isValidEmail(TextField tf, Label lb, String errorMessage) {
        boolean b=true;
        String msg=null;
        tf.getStyleClass().remove("error");
        if(!isValidEmail(tf)){

            b=false;
            msg=errorMessage;
            tf.getStyleClass().add("error");

        }
        lb.setText(msg);

        return b;
    }

    public static boolean isPasswordMatched(PasswordField tf1, PasswordField tf2){
        boolean b=false;
        if(tf1.getText().equals(tf2.getText()))
            b=true;
        return b;
    }

    public static boolean isPasswordMatched(PasswordField tf1,PasswordField tf2, Label lb, String errorMessage) {
        boolean b=true;
        String msg=null;
        tf2.getStyleClass().remove("error");
        if(!isPasswordMatched(tf1,tf2)){

            b=false;
            msg=errorMessage;
            tf2.getStyleClass().add("error");

        }
        lb.setText(msg);

        return b;
    }

    public static boolean isValidatePhone(String input){
        return input.charAt(0) == '0' && input.length() == 10 && input.matches("[0-9]+");
    }

}
