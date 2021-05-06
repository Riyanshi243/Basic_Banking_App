package com.internship.bank_sparks;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class sendtouser extends AppCompatActivity {

    List<Model> modelList_sendtouser = new ArrayList<>();
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    CustomAdapter_sendToUser adapter;

    String phonenumber, name, currentamount, transferamount, remainingamount;
    String selectuser_phonenumber, selectuser_name, selectuser_balance, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendtouser);

        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy, hh:mm a");
        date = simpleDateFormat.format(calendar.getTime());

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            phonenumber = bundle.getString("phonenumber");
            name = bundle.getString("name");
            currentamount = bundle.getString("currentamount");
            transferamount = bundle.getString("transferamount");
            showData(phonenumber);
        }
    }

    private void showData(String phonenumber) {
        modelList_sendtouser.clear();
        // Log.d("DEMO",phonenumber);
        Cursor cursor = new DatabaseHelper(this).readselectuserdata(phonenumber);
        while (cursor.moveToNext()) {
            String balancefromdb = cursor.getString(2);
            Double balance = Double.parseDouble(balancefromdb);

            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setGroupingUsed(true);
            nf.setMaximumFractionDigits(2);
            nf.setMinimumFractionDigits(2);
            String price = nf.format(balance);

            Model model = new Model(cursor.getString(0), cursor.getString(1), price);
            modelList_sendtouser.add(model);
        }

        adapter = new CustomAdapter_sendToUser(sendtouser.this, modelList_sendtouser);
        mRecyclerView.setAdapter(adapter);
    }

    public void selectuser(int position) {
        selectuser_phonenumber = modelList_sendtouser.get(position).getPhoneNo();
        Cursor cursor = new DatabaseHelper(this).readparticulardata(selectuser_phonenumber);
        while (cursor.moveToNext()) {
            selectuser_name = cursor.getString(1);
            selectuser_balance = cursor.getString(2);
            double Dselectuser_balance = Double.parseDouble(selectuser_balance);
            double Dselectuser_transferamount = Double.parseDouble(transferamount);
            double Dselectuser_remainingamount = Dselectuser_balance + Dselectuser_transferamount;

            new DatabaseHelper(this).insertTransferData(date, name, selectuser_name, transferamount, "Success");
            new DatabaseHelper(this).updateAmount(selectuser_phonenumber, Double.toString(Dselectuser_remainingamount));
            calculateAmount();
            Toast.makeText(this, "Transaction is done Successfully!", Toast.LENGTH_LONG).show();
            Intent intent=new Intent(sendtouser.this, user_list.class);
            startActivity(intent);

            finish();
        }
    }

    private void calculateAmount() {
        Double Dcurrentamount = Double.parseDouble(currentamount);
        Double Dtransferamount = Double.parseDouble(transferamount);
        Double Dremainingamount = Dcurrentamount - Dtransferamount;
        remainingamount = Dremainingamount.toString();
        new DatabaseHelper(this).updateAmount(phonenumber, remainingamount);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder_exitbutton = new AlertDialog.Builder(sendtouser.this);
        builder_exitbutton.setTitle("Do you want to cancel the transaction?").setCancelable(false)
                .setPositiveButton("YES", (dialogInterface, i) -> {
                    new DatabaseHelper(sendtouser.this).insertTransferData(date, name, "Not selected", transferamount, "Failed");
                    Toast.makeText(sendtouser.this, "Transaction Cancelled!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(sendtouser.this, user_list.class));
                    finish();
                }).setNegativeButton("NO", null);
        AlertDialog alertexit = builder_exitbutton.create();
        alertexit.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem search = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText = newText.toLowerCase();
                ArrayList<Model> newList = new ArrayList<>();
                for (Model model : modelList_sendtouser) {
                    String name = model.getName().toLowerCase();
                    if (name.contains(newText)) {
                        newList.add(model);
                    }
                }
                adapter.setFilter(newList);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
