package com.example.democlient.service;

import java.util.Objects;

public class Validator {
    private static String psswrd = "123456";

    public static Boolean checkForValidity(String username, String password){
        return Objects.equals(password, psswrd);
    }
}
