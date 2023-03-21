package com.example.qldd.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.qldd.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TuVanActivity extends AppCompatActivity {

    TextView textView;
    GoogleSignInAccount signInAccount;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference accRef = database.getReference("Account");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tu_van1);
        signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        textView = (TextView) findViewById(R.id.txtTuVan);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] to = {"appleghost1101@gmail.com"};
                String[] cc = {"appleghost1101@gmail.com"};
                sendMail(to, cc, "Nhận xét", "Gửi đánh giá sản phẩm");
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Trở lại");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
    private void sendMail(String[] emailAddress, String[] carbonCopies, String subject, String message ){
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        String[] to = emailAddress;
        String[] cc = carbonCopies;
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
        emailIntent.putExtra(Intent.EXTRA_CC, cc);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);
        emailIntent.setType("message/rfc822");
        startActivity(Intent.createChooser(emailIntent, "Email"));

    }
}