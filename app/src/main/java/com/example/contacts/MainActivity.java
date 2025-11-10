package com.example.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.contacts.Interfaces.IContactReader;
import com.example.contacts.Interfaces.IContactWriter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ContactRepository repository;
    private IContactReader reader;
    private IContactWriter writer;
    private ArrayAdapter<Contact> adapter;
    private static String capitalize(String s) {
        if (s == null || s.isEmpty()) return s;
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        repository = new ContactRepository();
        reader = repository;
        writer = repository;

        reader.loadFromFile(this);


        ListView widokListy = (ListView) findViewById(R.id.listaKontaktow);
        adapter = new ArrayAdapter<Contact>(this, android.R.layout.simple_list_item_1,reader.getContacts());
        widokListy.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fabPlus);
        fab.setOnClickListener( v -> showAddContactDialog());


        widokListy.setOnItemClickListener((parent, view, position, id)-> {
            Contact selected = adapter.getItem(position);
            Intent intent = new Intent(MainActivity.this, ContactDetailActivity.class);
            intent.putExtra("contact",selected);
            startActivity(intent);

        });




    }

    private void showAddContactDialog(){
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        View dialogView = inflater.inflate(R.layout.dialog_add_contact,null);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(dialogView)
                .setTitle("Dodaj Kontakt")
                .setPositiveButton("Zapisz",(dialog, which) -> {
                    EditText firstNameInput = dialogView.findViewById(R.id.inputFirstName);
                    EditText lastNameInput = dialogView.findViewById(R.id.inputLastName);
                    EditText emplerCompanyNameInput = dialogView.findViewById(R.id.inputEmployerCompanyName);
                    EditText jobTitleInput = dialogView.findViewById(R.id.inputJobTitle);
                    EditText phoneNumberInput = dialogView.findViewById(R.id.inputPhoneNumber);
                    EditText emailAddressInput = dialogView.findViewById(R.id.inputEmailAddress);


                    Contact newContact = ContactFactory.create(
                            firstNameInput.getText().toString(),
                            lastNameInput.getText().toString(),
                            emplerCompanyNameInput.getText().toString(),
                            jobTitleInput.getText().toString(),
                            phoneNumberInput.getText().toString(),
                            emailAddressInput.getText().toString()

                    );
                    writer.addContact(newContact);
                    writer.saveToFile(this);
                    adapter.notifyDataSetChanged();


                })
                .setNegativeButton("Anuluj", (dialog, which) -> dialog.dismiss())
                .show();
    }
}