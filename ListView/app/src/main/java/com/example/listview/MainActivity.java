package com.example.listview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DBHelper dbHelper;
    ArrayList<String> contactList;
    ArrayAdapter<String> adapter;
    ListView listViewContacts;
    private int selectedPosition = ListView.INVALID_POSITION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnEdit = findViewById(R.id.btnEdit);
        Button btnDelete = findViewById(R.id.btnDelete);
        Button btnSearch = findViewById(R.id.btnSearch);
        listViewContacts = findViewById(R.id.listViewContacts);

        updateContactList();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDialog();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editSelectedContact();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSelectedContact();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSearchDialog();
            }
        });

        listViewContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPosition = position;
                Toast.makeText(MainActivity.this, "Selected: " + contactList.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showAddDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_contact, null);
        dialogBuilder.setView(dialogView);

        EditText editTextName = dialogView.findViewById(R.id.editTextName);
        EditText editTextPhoneNumber = dialogView.findViewById(R.id.editTextPhoneNumber);
        Button btnSave = dialogView.findViewById(R.id.btnSave);

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String phoneNumber = editTextPhoneNumber.getText().toString();

                if (!name.isEmpty() && !phoneNumber.isEmpty()) {
                    dbHelper.addContact(name, phoneNumber);
                    updateContactList();
                    alertDialog.dismiss();
                } else {
                    Toast.makeText(MainActivity.this, "Name and Phone Number cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void editSelectedContact() {
        if (selectedPosition != ListView.INVALID_POSITION) {
            String selectedContact = contactList.get(selectedPosition);
            String[] parts = selectedContact.split(" - ");
            String oldName = parts[0];
            String oldPhoneNumber = parts[1];

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_contact, null);
            dialogBuilder.setView(dialogView);

            EditText editTextName = dialogView.findViewById(R.id.editTextName);
            EditText editTextPhoneNumber = dialogView.findViewById(R.id.editTextPhoneNumber);
            Button btnSave = dialogView.findViewById(R.id.btnSave);

            editTextName.setText(oldName);
            editTextPhoneNumber.setText(oldPhoneNumber);

            AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.show();

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String newName = editTextName.getText().toString();
                    String newPhoneNumber = editTextPhoneNumber.getText().toString();

                    if (!newName.isEmpty() && !newPhoneNumber.isEmpty()) {
                        dbHelper.updateContact(oldName, oldPhoneNumber, newName, newPhoneNumber);
                        updateContactList();
                        alertDialog.dismiss();
                    } else {
                        Toast.makeText(MainActivity.this, "Name and Phone Number cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, "No contact selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteSelectedContact() {
        if (selectedPosition != ListView.INVALID_POSITION) {
            String selectedContact = contactList.get(selectedPosition);
            String[] parts = selectedContact.split(" - ");
            String name = parts[0];
            String phoneNumber = parts[1];

            dbHelper.deleteContact(name, phoneNumber);
            updateContactList();
        } else {
            Toast.makeText(this, "No contact selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void showSearchDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_search_contact, null);
        dialogBuilder.setView(dialogView);

        EditText editTextSearch = dialogView.findViewById(R.id.editTextSearch);
        Button btnSearch = dialogView.findViewById(R.id.btnSearch);

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = editTextSearch.getText().toString();
                ArrayList<String> searchResults = dbHelper.searchContacts(searchText);
                if (searchResults.isEmpty()) {
                    Toast.makeText(MainActivity.this, "No contacts found", Toast.LENGTH_SHORT).show();
                } else {
                    updateContactList(searchResults);
                    alertDialog.dismiss();
                }
            }
        });
    }

    private void updateContactList() {
        contactList = dbHelper.getAllContacts();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactList);
        listViewContacts.setAdapter(adapter);
    }

    private void updateContactList(ArrayList<String> contacts) {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contacts);
        listViewContacts.setAdapter(adapter);
    }
}
