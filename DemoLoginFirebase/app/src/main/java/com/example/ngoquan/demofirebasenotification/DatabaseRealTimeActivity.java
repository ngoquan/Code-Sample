package com.example.ngoquan.demofirebasenotification;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/*
* link tutorial: http://www.androidhive.info/2016/10/android-working-with-firebase-realtime-database/*/

public class DatabaseRealTimeActivity extends AppCompatActivity {

    private final String TAG = "DatabaseRealTimeActivity";
    private TextView tvDetails;
    private EditText edName, edEmail;
    private Button btnSave;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_realtime);
//        final ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayShowHomeEnabled(true);
//            actionBar.setDisplayShowTitleEnabled(true);
//            actionBar.setIcon(R.mipmap.ic_launcher);
//        }
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);





        initControlViews();




        mFirebaseInstance = FirebaseDatabase.getInstance();
//        Kiểm tra kết nối
        checkConnection();



//        get reference to 'user' node
        mFirebaseDatabase = mFirebaseInstance.getReference("users");
        // store app title to 'app_title' node
        mFirebaseInstance.getReference("app_title").setValue("Realtime Database");
//        Store app title to 'app_title' node
        mFirebaseInstance.getReference("app_title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "App title updated");
                String title = dataSnapshot.getValue(String.class);
                // update toolbar
//                if (actionBar != null)
//                    actionBar.setTitle(title);
                Log.d(TAG, "title: " + title);
                getSupportActionBar().setTitle(title);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // failed to write value
                Log.e(TAG, "Failed to write app title value.", databaseError.toException());
            }
        });

//        Save and Update the user
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edName.getText().toString().trim();
                String email = edEmail.getText().toString().trim();
//                Check for already exists userId
                if (TextUtils.isEmpty(userId)) {
                    createUser(name, email);
                } else {
                    updateUser(name, email);
                }
            }
        });

        toggleButton();

    }


//    Check user is connected / disconnected
    private void checkConnection() {
        DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean isConnected = dataSnapshot.getValue(Boolean.class);
                if (isConnected) {
                    Log.d(TAG, "Connected");
                } else {
                    Log.d(TAG, "Disconnected");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Listener was cancelled", databaseError.toException());
            }
        });
    }


//    Changing button text
    private void toggleButton() {
        if (TextUtils.isEmpty(userId))
            btnSave.setText("Save");
        else
            btnSave.setText("Update");
    }

    private void createUser(String name, String email) {
        //Todo
        // In real app this userId should be fetched
        // by implementing fireabse auth
        if (TextUtils.isEmpty(userId))
            userId = mFirebaseDatabase.push().getKey();

        Customer customer = new Customer(name, email);
        mFirebaseDatabase.child(userId).setValue(customer);
        addUserChangeListener();
    }

    private void addUserChangeListener() {
        // User data change listener
        mFirebaseDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Customer customer = dataSnapshot.getValue(Customer.class);
                // Check for null
                if (customer == null) {
                    Log.e(TAG, "User data is null");
                    return;
                }

                Log.d(TAG, "User data is changed: " + customer.name + ", " + customer.email);
                tvDetails.setText(customer.name + ", " + customer.email);
                // clear edit text
                edName.setText("");
                edEmail.setText("");
                toggleButton();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to wire data
                Log.e(TAG, "Failed to write data", databaseError.toException());
            }
        });
    }

    private void updateUser(String name, String email) {
        // updating the user via child node
        if (!TextUtils.isEmpty(name))
            mFirebaseDatabase.child(userId).child("name").setValue(name);
        if (!TextUtils.isEmpty(email))
            mFirebaseDatabase.child(userId).child("email").setValue(email);
    }

    private void initControlViews() {
        tvDetails = (TextView) findViewById(R.id.txt_user);
        edName = (EditText) findViewById(R.id.name);
        edEmail = (EditText) findViewById(R.id.email);
        btnSave = (Button) findViewById(R.id.btn_save);
    }



}
