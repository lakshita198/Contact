package com.example.contact;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import java.util.*;
import android.graphics.Color;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton fab;
    DBHelper db;
    List<Contact> list;
    ContactAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.fab);

        db = new DBHelper(this);
        list = new ArrayList<>();

        loadData();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ContactAdapter(list, db);
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(v -> showAddDialog());
    }

    void loadData() {
        Cursor res = db.getAllData();
        list.clear();

        while (res.moveToNext()) {
            list.add(new Contact(res.getString(1), res.getString(2)));
        }
    }

    void showAddDialog() {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(16,16,16,16);

        // Name Row
        LinearLayout nameLayout = new LinearLayout(this);
        nameLayout.setOrientation(LinearLayout.HORIZONTAL);

        TextView nameLabel = new TextView(this);
        nameLabel.setText("Name: ");
        nameLabel.setTextSize(16);
        nameLabel.setTextColor(Color.BLACK);

        EditText nameInput = new EditText(this);
        nameInput.setLayoutParams(new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1
        ));

        nameLayout.addView(nameLabel);
        nameLayout.addView(nameInput);

        // Mobile Row
        LinearLayout mobileLayout = new LinearLayout(this);
        mobileLayout.setOrientation(LinearLayout.HORIZONTAL);
        mobileLayout.setPadding(0,8,0,0);

        TextView mobileLabel = new TextView(this);
        mobileLabel.setText("Mobile No: ");
        mobileLabel.setTextSize(16);
        mobileLabel.setTextColor(Color.BLACK);

        EditText mobileInput = new EditText(this);
        mobileInput.setLayoutParams(new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1
        ));

        mobileLayout.addView(mobileLabel);
        mobileLayout.addView(mobileInput);

        layout.addView(nameLayout);
        layout.addView(mobileLayout);

        new MaterialAlertDialogBuilder(this)
                .setTitle("Add Contact")
                .setView(layout)
                .setPositiveButton("Add", (d, i) -> {
                    String name = nameInput.getText().toString().trim();
                    String mobile = mobileInput.getText().toString().trim();
                    if(!name.isEmpty() && !mobile.isEmpty()) {
                        db.insertData(name, mobile);
                        loadData();
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, "Enter all fields", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
