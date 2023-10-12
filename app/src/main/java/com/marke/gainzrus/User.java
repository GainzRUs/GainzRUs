package com.marke.gainzrus;

import android.content.Context;
import android.content.SharedPreferences;

public class User {

        String userName, userAge, userWeight;

        // constructor
//        User(String userName, String userAge, String userWeight){
//            this.userName = userName;
//            this.userAge = userAge;
//            this.userWeight = userWeight;
//        }

        User(){}

        // to display user's name
        @Override
        public String toString() {
            return userName;
        }
}



