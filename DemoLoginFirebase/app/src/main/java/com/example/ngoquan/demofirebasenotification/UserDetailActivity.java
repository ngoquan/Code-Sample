package com.example.ngoquan.demofirebasenotification;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UserDetailActivity extends AppCompatActivity {

    private final String TAG = "UserDetailActivity";
    private Firebase myFirebaseRef;
    private FirebaseAuth mAuth;
    private TextView name;
    private TextView welcomeText;
    private Button changeButton;
    private Button revertButton;
    // To hold Facebook profile picture
    private ImageView profilePicture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setIcon(R.mipmap.ic_launcher);
        } else {

        }
        myFirebaseRef = new Firebase("https://fir-pushnotification-513de.firebaseio.com/users/");
        mAuth = FirebaseAuth.getInstance();

        initControlView();

        //Get the uid for the currently logged in User from intent data passed to this activity
        String uid = getIntent().getExtras().getString("user_id");
        //Get the imageUrl  for the currently logged in User from intent data passed to this activity
        String imageUrl = getIntent().getExtras().getString("profile_picture");

        if (uid != null && !uid.equals("")) {
            Log.d(TAG, "uid: " + uid);
            name.setText(uid);
        } else {
            Log.d(TAG, "Chưa lấy được id.");
        }

        if (imageUrl != null && !imageUrl.equals("")) {
            Log.d(TAG, "imageUrl: " + imageUrl);
        } else {
            Log.d(TAG, "Chưa lấy được link ảnh.");
        }


        if (imageUrl != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                new ImageLoadTask(imageUrl, profilePicture).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } else {
                new ImageLoadTask(imageUrl, profilePicture).execute();
            }
        } else {
            Log.d(TAG, "Không có ảnh đại diện");
        }


        if (uid != null && !uid.equals("")) {
            //Referring to the name of the User who has logged in currently and adding a valueChangeListener
            myFirebaseRef.child(uid).child("name").addValueEventListener(new ValueEventListener() {
                //onDataChange is called every time the name of the User changes in your Firebase Database
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //Inside onDataChange we can get the data as an Object from the dataSnapshot
                    //getValue returns an Object. We can specify the type by passing the type expected as a parameter
                    String data = dataSnapshot.getValue(String.class);
                    name.setText("Hello " + data);
                }

                //onCancelled is called in case of any error
                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    Toast.makeText(getApplicationContext(), "" + firebaseError.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }

        //A firebase reference to the welcomeText can be created in following ways :
        // You can use this :
        //Firebase myAnotherFirebaseRefForWelcomeText=new Firebase("https://androidbashfirebaseupdat-bd094.firebaseio.com/welcomeText");*/
        //OR as shown below
        if (uid != null && !uid.equals("")) {
            myFirebaseRef.child(uid).child("email").addValueEventListener(new ValueEventListener() {
                //onDataChange is called every time the data changes in your Firebase Database
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //Inside onDataChange we can get the data as an Object from the dataSnapshot
                    //getValue returns an Object. We can specify the type by passing the type expected as a parameter
                    String data = dataSnapshot.getValue(String.class);
                    welcomeText.setText(data);
                }

                //onCancelled is called in case of any error
                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    Toast.makeText(getApplicationContext(), "" + firebaseError.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }

        //onClicking changeButton to work with realtime database of firebase.
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserDetailActivity.this, DatabaseRealTimeActivity.class));
            }
        });
//
//        //onClicking revertButton the value of the welcomeText in the Firebase database gets changed
//        revertButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                myFirebaseRef.child("welcomeText").setValue("Welcome to Learning @ AndroidBash");
//            }
//        });




    }

    private void initControlView() {
        name = (TextView) findViewById(R.id.text_view_name);
        welcomeText = (TextView) findViewById(R.id.text_view_welcome);
        changeButton = (Button) findViewById(R.id.button_change);
//        revertButton = (Button) findViewById(R.id.button_revert);
        profilePicture = (ImageView) findViewById(R.id.profile_picture);
    }

    public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private ImageView imageView;

        public ImageLoadTask(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            imageView.setImageBitmap(result);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            mAuth.signOut();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
