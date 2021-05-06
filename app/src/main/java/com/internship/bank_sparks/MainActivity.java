package com.internship.bank_sparks;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    ImageView customer, transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        customer = findViewById(R.id.customer);
        transaction = findViewById(R.id.Transactions);
        customer.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, user_list.class)));
        transaction.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, history_list.class)));
    }
}