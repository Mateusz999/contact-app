package com.example.contacts;

public class ContactFactory {
    public static Contact create(String firstName, String lastName, String company, String jobTitle,
                                 String phone, String email){

        return new Contact(
                capitalize(firstName),
                capitalize(lastName),
                company,
                jobTitle,
                phone,
                email
        );
    }

    private static String capitalize(String s) {
        if (s == null || s.isEmpty()) return s;
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
