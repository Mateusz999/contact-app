package com.example.contacts;

import java.io.Serializable;

public class Contact implements Serializable {
    private String firstName;
    private String lastName;
    private String employerCompanyName;
    private String jobTitle;
    private String phoneNumber;
    private String emailAddress;

    public Contact(String firstName, String lastName, String employerCompanyName,
                   String jobTitle, String phoneNumber, String emailAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.employerCompanyName = employerCompanyName;
        this.jobTitle = jobTitle;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public String getEmployerCompanyName(){
        return employerCompanyName;
    }
    public String getJobTitle(){
        return jobTitle;
    }
    public String getPhoneNumber(){
        return phoneNumber;
    }
    public String getEmailAddress(){
        return emailAddress;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
