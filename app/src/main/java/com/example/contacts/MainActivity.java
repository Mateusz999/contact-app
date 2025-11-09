package com.example.contacts;

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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
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

        String[] listaKontaktow = new String[]{"Grzegorz Antkowicz","Lukas Barczuk","Kacper Ciupik","Patryk Nastala",
                                                "Dominik Kozik","Przemek Grubiak","Marcel Splesnialy","Martin Schneider",
                                                "Kornel Odoj","Michal Jozkow","Kacper Florek","Karolina Wiercinska",
                                                "Mateusz Biernat","Jan Kowalski","Przemyslaw Kolodziejczyk","Marek Modrzejewski"};

        String[] jobTitles = {
                "Software Engineer", "Frontend Developer", "Backend Developer", "Full Stack Developer",
                "Mobile App Developer", "DevOps Engineer", "QA Tester", "Data Analyst",
                "Machine Learning Engineer", "Cloud Architect", "Product Manager", "UI/UX Designer",
                "Cybersecurity Specialist", "IT Support Specialist", "Technical Writer"
        };

        String[] companyNames = {
                "CodeNova", "ByteForge", "CloudSphere", "QuantumSoft", "PixelWorks", "DevNest",
                "NetFusion", "LogicLoop", "AppVibe", "SynapseTech", "CoreStack", "IntelliWare",
                "NexaBit", "GridLabs", "Orbitron"
        };
        Random rand = new Random();
        List<Contact> kontakty = new ArrayList<>();

        for (String pelneImie : listaKontaktow) {
            String[] czesci = pelneImie.split(" ", 2);
            String firstName = czesci.length > 0 ? czesci[0].toLowerCase() : "";
            String lastName = czesci.length > 1 ? czesci[1].toLowerCase() : "";

            String jobTitle = jobTitles[rand.nextInt(jobTitles.length)];
            String companyName = companyNames[rand.nextInt(companyNames.length)];

            // Losowy numer telefonu
            String phoneNumber = "+48 5" +
                    (rand.nextInt(9) + 1) + rand.nextInt(10) + rand.nextInt(10) + " " +
                    rand.nextInt(10) + rand.nextInt(10) + rand.nextInt(10) + " " +
                    rand.nextInt(10) + rand.nextInt(10) + rand.nextInt(10);

            // Adres e-mail
            String emailAddress = firstName + "." + lastName + "@" +
                    companyName.toLowerCase() + ".com";

            Contact kontakt = new Contact(
                    capitalize(firstName),
                    capitalize(lastName),
                    companyName,
                    jobTitle,
                    phoneNumber,
                    emailAddress
            );

            kontakty.add(kontakt);
        }


        ListView widokListy = (ListView) findViewById(R.id.listaKontaktow);
        ArrayAdapter<Contact> przejsciowka = new ArrayAdapter<Contact>(this, android.R.layout.simple_list_item_1,kontakty);
        widokListy.setAdapter(przejsciowka);


        FloatingActionButton fab = findViewById(R.id.fabPlus);

        fab.setOnClickListener( v -> {
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


                        Contact newContact = new Contact(
                                firstNameInput.getText().toString(),
                                lastNameInput.getText().toString(),
                                emplerCompanyNameInput.getText().toString(),
                                jobTitleInput.getText().toString(),
                                phoneNumberInput.getText().toString(),
                                emailAddressInput.getText().toString()
                        );
                        kontakty.add(newContact);
                    })
                    .setNegativeButton("Anuluj", (dialog, which) -> dialog.dismiss())
                    .show();
        });


    }
}