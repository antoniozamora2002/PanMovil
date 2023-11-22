package com.example.panmovil_panitasproject.conexionApi;

import android.util.Base64;

public class ConexionApi {

    public static final String URL_BASE = "https://panaderia.informaticapp.com/";
    public static String AUTH = "Basic YTJhYTA3YWRmaGRmcmV4ZmhnZGZoZGZlcnR0Z2VJMzNOeC5PUUxobnU5eVBnbEJjQVJDMFgydnU5RUtxOm8yYW8wN29kZmhkZnJleGZoZ2RmaGRmZXJ0dGdleUY1TXJBdUh4eDZKYjdZR0VPMWE4UjFWYlFad2VnVw==";
    public static String username;
    public static String password;

    public static String desencriptarAuth(){

        String creds = String.format("%s:%s", username, password);
        String authConEspacio = Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
        String AuthSinEspacios = authConEspacio.replaceAll("\\s+", "");
        return "Basic " + AuthSinEspacios;

    }

}
