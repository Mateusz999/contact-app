package com.example.contacts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.contacts.Interfaces.IContactRemover;

public class ContactDetailActivity extends AppCompatActivity {





private Contact contact;
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
        TextView btnDelete = findViewById(R.id.btnDelete);

        int contactIndex = getIntent().getIntExtra("contactIndex", -1);
        contact = (Contact) getIntent().getSerializableExtra("contact");
        if (contact == null) {
            finish();
            return;
        }


        btnDelete.setOnClickListener( v ->{
            Intent result = new Intent();
            result.putExtra("deleteContact",contact);
            setResult(RESULT_OK,result);
            finish();
        });


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
                LayoutInflater inflater = LayoutInflater.from(ContactDetailActivity.this);
                View dialogView = inflater.inflate(R.layout.dialog_add_contact, null);

                EditText firstNameInput = dialogView.findViewById(R.id.inputFirstName);
                EditText lastNameInput = dialogView.findViewById(R.id.inputLastName);
                EditText employerCompanyNameInput = dialogView.findViewById(R.id.inputEmployerCompanyName);
                EditText jobTitleInput = dialogView.findViewById(R.id.inputJobTitle);
                EditText phoneNumberInput = dialogView.findViewById(R.id.inputPhoneNumber);
                EditText emailAddressInput = dialogView.findViewById(R.id.inputEmailAddress);

               
                firstNameInput.setText(contact.getFirstName());
                lastNameInput.setText(contact.getLastName());
                employerCompanyNameInput.setText(contact.getEmployerCompanyName());
                jobTitleInput.setText(contact.getJobTitle());
                phoneNumberInput.setText(contact.getPhoneNumber());
                emailAddressInput.setText(contact.getEmailAddress());

                new AlertDialog.Builder(ContactDetailActivity.this)
                        .setView(dialogView)
                        .setTitle("Edytuj Kontakt")
                        .setPositiveButton("Zapisz", (dialog, which) -> {
                            if (firstNameInput.getText().toString().isEmpty() ||
                                    phoneNumberInput.getText().toString().isEmpty()) {
                                Toast.makeText(this, "Imię i numer telefonu są wymagane", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            contact = ContactFactory.create(
                                    firstNameInput.getText().toString(),
                                    lastNameInput.getText().toString(),
                                    employerCompanyNameInput.getText().toString(),
                                    jobTitleInput.getText().toString(),
                                    phoneNumberInput.getText().toString(),
                                    emailAddressInput.getText().toString()
                            );

                            Intent result = new Intent();
                            result.putExtra("contactIndex", contactIndex);
                            result.putExtra("editedContact", contact);
                            setResult(RESULT_FIRST_USER, result);
                            finish();
                        })
                        .setNegativeButton("Anuluj", (dialog, which) -> dialog.dismiss())
                        .show();
            });



        btnCancel.setOnClickListener(v -> finish());
    }
}
