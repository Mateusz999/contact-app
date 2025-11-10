package com.example.contacts;

import android.content.Context;

import com.example.contacts.Interfaces.IContactReader;
import com.example.contacts.Interfaces.IContactRemover;
import com.example.contacts.Interfaces.IContactWriter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ContactRepository implements IContactWriter, IContactReader, IContactRemover, Serializable {
    private List<Contact> contacts= new ArrayList<>();
    private static final String FILE_NAME = "contacts.dat";
    private static ContactRepository instance;

    private ContactRepository() { }

    public static ContactRepository getInstance(){
        if(instance == null)
            instance = new ContactRepository();
        return instance;
    }



    @Override
    public void addContact(Contact contact){
        contacts.add(contact);
    }
    @Override
    public List<Contact> getContacts(){
        return contacts;
    }
    @Override
    public void removeContact(Contact contact){
        contacts.remove(contact);
    }
    @Override
    public void saveToFile(Context context){
        try(FileOutputStream fos = context.openFileOutput(FILE_NAME,Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos)
        ) {
            oos.writeObject(contacts);

        } catch (FileNotFoundException e) {
            e.printStackTrace();


        } catch (IOException e) {
            e.printStackTrace();
            contacts.clear();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void loadFromFile(Context context){
        try(FileInputStream fis = context.openFileInput(FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fis)){
            List<Contact> loaded = (List<Contact>) ois.readObject();
            contacts.clear();
            contacts.addAll(loaded);

        } catch (IOException | ClassNotFoundException e) {
            contacts.clear();

        }
    }

}
