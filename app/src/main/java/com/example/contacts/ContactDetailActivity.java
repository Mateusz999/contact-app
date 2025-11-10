package com.example.contacts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ContactDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_contact_details);


        TextView name = findViewById(R.id.contactName);
        TextView job = findViewById(R.id.contactJob);
        TextView phone = findViewById(R.id.contactPhone);
        TextView email = findViewById(R.id.contactEmail);
        ImageButton btnCall = findViewById(R.id.btnCall);
        Button btnEdit = findViewById(R.id.btnEdit);
        Button btnCancel = findViewById(R.id.btnCancel);


        Contact contact = (Contact) getIntent().getSerializableExtra("contact");
        if (contact == null) {
            finish();
            return;
        }
        btnCall.setOnClickListener(v -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + contact.getPhoneNumber()));
            startActivity(callIntent);
        });

        name.setText(contact.getFirstName() + " " + contact.getLastName());
        job.setText(contact.getJobTitle() + " @ " + contact.getEmployerCompanyName());
        phone.setText("📞 " + contact.getPhoneNumber());
        email.setText("✉️ " + contact.getEmailAddress());

//
        btnEdit.setOnClickListener(v -> {
            // okno edycji do dodania
        });

        btnCancel.setOnClickListener(v -> finish());
    }
}
