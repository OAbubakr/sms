/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

/**
 *
 * @author omari
 */
public class UserHolder {
    private static String userName;
    private static String password;

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        UserHolder.userName = userName;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        UserHolder.password = password;
    }
    
}
