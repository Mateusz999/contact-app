package com.example.contacts.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.contacts.Contact;
import com.example.contacts.ContactFactory;
import com.example.contacts.Interfaces.IContactWriter;
import com.example.contacts.R;

public  class DialogManager {
    public  void showAddContactDialog(Context context, IContactWriter writer, ArrayAdapter<Contact> adapter){
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_add_contact,null);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView)
                .setTitle("Dodaj Kontakt")
                .setPositiveButton("Zapisz",(dialog, which) -> {
                    EditText firstNameInput = dialogView.findViewById(R.id.inputFirstName);
                    EditText lastNameInput = dialogView.findViewById(R.id.inputLastName);
                    EditText emplerCompanyNameInput = dialogView.findViewById(R.id.inputEmployerCompanyName);
                    EditText jobTitleInput = dialogView.findViewById(R.id.inputJobTitle);
                    EditText phoneNumberInput = dialogView.findViewById(R.id.inputPhoneNumber);
                    EditText emailAddressInput = dialogView.findViewById(R.id.inputEmailAddress);

                    if (firstNameInput.getText().toString().isEmpty() || phoneNumberInput.getText().toString().isEmpty()) {
                        Toast.makeText(context, "Imię i numer telefonu są wymagane", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Contact newContact = null;
                    try{
                                newContact = ContactFactory.create(
                                firstNameInput.getText().toString(),
                                lastNameInput.getText().toString(),
                                emplerCompanyNameInput.getText().toString(),
                                jobTitleInput.getText().toString(),
                                phoneNumberInput.getText().toString(),
                                emailAddressInput.getText().toString()
                        );
                    } catch (Exception e) {
                        Toast.makeText(context,"Bład podczas tworznia kontaktu."+e.getMessage(),Toast.LENGTH_LONG).show();
                    }

                    if (newContact == null) {
                        Toast.makeText(context, "Nie udało się utworzyc kontaktu", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    writer.addContact(newContact);
                    writer.saveToFile(context);
                    adapter.notifyDataSetChanged();

                })
                .setNegativeButton("Anuluj", (dialog, which) -> dialog.dismiss())
                .show();
    }

}
