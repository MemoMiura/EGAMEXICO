package com.cursosant.insurance.common.entities;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by oscarzarco on 09/05/19.
 */

public class UserData implements Serializable {
    private String token;
    private String token_multicotizador;
    private String username;
    private String password;
    private String full_name;
    private String first_name;
    private String last_name;
    private String email;
    private String quote;

    private static final String DATA_FILE_NAME = "user.data";
    private static UserData singleton = new UserData();


    public static void saveData(Context context,String token, String token_multicotizador, String username,String password,
                                String full_name, String first_name, String last_name, String email, String quote) {
        singleton.token = token;
        singleton.username = username;
        singleton.token_multicotizador = token_multicotizador;
        singleton.password = password;
        singleton.full_name = full_name;
        singleton.first_name = first_name;
        singleton.last_name = last_name;
        singleton.email = email;
        singleton.quote = quote;

        try {
            FileOutputStream fos = context.openFileOutput(DATA_FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(singleton);
            os.close();
            fos.close();
        } catch (IOException e) {
            Log.e("Error","User info not saved"+ e.getMessage());
        }

    }

    public static void loadData(Context context){
        try {
            FileInputStream fis = context.openFileInput(DATA_FILE_NAME);
            ObjectInputStream is = null;
            is = new ObjectInputStream(fis);
            singleton= (UserData) is.readObject();
            is.close();
            fis.close();
        } catch (IOException e) {

        } catch (ClassNotFoundException e) {

        }


    }

    public static void clearData(Context context){
        context.deleteFile(DATA_FILE_NAME);
        singleton = new UserData();
    }

    public static String getName() {
        return singleton.first_name;
    }

    public static String getEmail() {
        return singleton.email;
    }

    public static String getPassword() {
        return singleton.password;
    }


    public  static String getToken() {
        return singleton.token;
    }

    public static String getToken_multicotizador() {
        return singleton.token_multicotizador;
    }

    public static String getUsername() {
        return singleton.username;
    }

    public static String getFull_name() {
        return singleton.full_name;
    }

    public static String getFirst_name(){ return  singleton.first_name;}

    public static String getLast_name() {
        return singleton.last_name;
    }

    public static String getQuote() {
        return singleton.quote;
    }

    private UserData(){

    }
}
