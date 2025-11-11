package com.example.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.contacts.Interfaces.IContactReader;
import com.example.contacts.Interfaces.IContactRemover;
import com.example.contacts.Interfaces.IContactWriter;
import com.example.contacts.Utils.DialogManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private DialogManager dialogManager;
    private ContactRepository repository;
    private IContactReader reader;
    private IContactWriter writer;
    private IContactRemover remover;
    private ArrayAdapter<Contact> adapter;
    private static String capitalize(String s) {
        if (s == null || s.isEmpty()) return s;
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    private final ActivityResultLauncher<Intent> detailLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result ->{
                if(result.getResultCode() == RESULT_OK){
                    Intent data = result.getData();
                    if(data !=null){
                        Contact deleted = (Contact) data.getSerializableExtra("deleteContact");
                        if(deleted !=null){
                            remover.removeContact(deleted);
                            writer.saveToFile(this);
                            adapter.notifyDataSetChanged();

                        }
                    }
                }
                if (result.getResultCode() == RESULT_FIRST_USER) {
                    Intent data = result.getData();
                    int contactIndex = data.getIntExtra("contactIndex", -1);
                    Contact edited = (Contact) data.getSerializableExtra("editedContact");
                    if (contactIndex != -1 && edited != null) {
                        reader.getContacts().set(contactIndex, edited); // podmiana kontaktu
                        writer.saveToFile(this);
                        adapter.notifyDataSetChanged();
                    }
                }

            });


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
        remover = repository;
        dialogManager = new DialogManager();

        reader.loadFromFile(this);

        ListView widokListy = (ListView) findViewById(R.id.listaKontaktow);
        adapter = new ArrayAdapter<Contact>(this, android.R.layout.simple_list_item_1,reader.getContacts());
        widokListy.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fabPlus);
        fab.setOnClickListener( v -> dialogManager.showAddContactDialog(this,writer,adapter));

        widokListy.setOnItemClickListener((parent, view, position, id)-> {
            Contact selected = adapter.getItem(position);
            Intent intent = new Intent(MainActivity.this, ContactDetailActivity.class);
            intent.putExtra("contact",selected);
            intent.putExtra("contactIndex", position);
            detailLauncher.launch(intent);

        });
    }
}