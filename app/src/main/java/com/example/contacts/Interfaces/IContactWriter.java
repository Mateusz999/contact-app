package com.example.contacts.Interfaces;

import android.content.Context;

import com.example.contacts.Contact;

import java.util.List;

public interface IContactWriter {

    void addContact(Contact contact);
    void saveToFile(Context context);
}
