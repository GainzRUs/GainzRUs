package com.marke.gainzrus;

public class User {
    public class Dish {

        String userName, userAge, userWeight;
        String exercise, reps, sets;
        int price;


        void User(String title, String describe, int price){
            this.title = title;
            this.describe = describe;
            this.price = price;
        }

        @Override
        public String toString() {
            return title;
        }
    }


}
