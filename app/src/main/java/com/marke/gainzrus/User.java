package com.marke.gainzrus;

import android.content.Context;
import android.content.SharedPreferences;

public class User {

        private String userName, userHeight, userWeight, userBMI;

        User(){
                setUserName("");
                setUserWeight("0");
                setUserHeight("0");
                setUserBMI("0");
        }

        User(String user, String height, String weight, String BMI) {
                setUserName(user);
                setUserWeight(weight);
                setUserHeight(height);
                setUserBMI(BMI);
        }

        public String getUserName() {
                return userName;
        }

        public String getUserWeight() {
                return userWeight;
        }

        public String getUserHeight() {
                return userHeight;
        }

        public String getUserBMI() {
                return userBMI;
        }

        public void setUserName(String name) {
                this.userName = name;
        }

        public void setUserHeight(String h) {
                this.userHeight = h;
        }

        public void setUserWeight(String w) {
                this.userWeight = w;
        }

        public void setUserBMI(String bmi) {
                this.userBMI = bmi;
        }
        public void setAll(String user, String height, String weight, String BMI){
                setUserName(user);
                setUserWeight(height);
                setUserHeight(weight);
                setUserBMI(BMI);
        }

}



