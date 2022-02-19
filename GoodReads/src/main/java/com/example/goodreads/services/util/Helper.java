package com.example.goodreads.services.util;

import org.apache.commons.validator.routines.EmailValidator;

public class Helper {
    public enum Visibility{EVERYONE('e'), FRIENDS('f'), NONE('n');

        public final char symbol;
        Visibility(char symbol){
            this.symbol = symbol;
        }

        public static boolean isValidVisibility(char symbol) {
            return (symbol == NONE.symbol ||
                    symbol == EVERYONE.symbol ||
                    symbol == FRIENDS.symbol);
        }
    }

    public static Boolean isValidEmail(String email) {
        if (charCounter(email) == 1) {
            return EmailValidator.getInstance()
                    .isValid(email);
        }
        return false;
    }

    public static Boolean isValidPassword(String password) {
        return (password.matches("(?=^.{8,}$)(?=.*\\d)(?=.*[!@#$%^&*]+)(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$"));
    }

    public static int charCounter(String text) {
        int counter = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '@') {
                counter++;
            }
        }
        return counter;
    }

}
