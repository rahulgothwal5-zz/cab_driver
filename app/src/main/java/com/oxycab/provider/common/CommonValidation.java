package com.oxycab.provider.common;

import java.util.regex.Pattern;

public class CommonValidation {
    public static Boolean Validation(String Values){
        return Values == null || Values.equalsIgnoreCase("null") || Values.isEmpty();
    }
    public static boolean isValidPhone(String phone) {
        if (phone.length()<10){
            return false;
        }
        phone=phone.substring(phone.toString().length() - 10);
        boolean check;
        if(!Pattern.matches("[a-zA-Z]+", phone)) {
            check = phone.length() < 6 || phone.length() > 13;
        } else {
            check=true;
        }
        return check;
    }

    public static boolean isValidEmail(String email) {
        boolean check;
        if(!Pattern.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+", email)) {
            check =false;
        } else {
            check=true;
        }
        return check;
    }
}
