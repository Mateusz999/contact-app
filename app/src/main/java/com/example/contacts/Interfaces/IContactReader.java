package com.example.contacts.Interfaces;

import android.content.Context;

import com.example.contacts.Contact;

import java.util.List;

public interface IContactReader {
    //void addContact(Contact contact);
    List<Contact> getContacts();
    //void save(Context context);
    void loadFromFile(Context context);


}
