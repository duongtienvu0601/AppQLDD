package com.example.qldd.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.qldd.R;
import com.example.qldd.databinding.ActivityMainBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {
    TextView txtTen,txtSex, txtNgaySinh;
//    EditText edtDate;
    Button btnLogout,btnXoa,btnLuu;
    ImageView imgvatar;
    GoogleSignInAccount signInAccount;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference accRef = database.getReference("Account");
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        init();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Trở lại");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
//        setSupportActionBar(toolbar);
//        toolbar.setTitle("Thông tin của bạn");
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent main = new Intent(Profile.this, MainActivity.class);
//                startActivityForResult(main ,1);
//                finish();
//            }
//        });
        signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        DataProfile();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Deletedata();
            }
        });

    }
    private void Deletedata() {
        new AlertDialog.Builder( this)
                .setTitle(getString(R.string.app_name))
                .setMessage("Bạn có muốn Xóa Thông Tin của mình")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        accRef.child(signInAccount.getId()).child("hoTen").setValue(" ");
                        accRef.child(signInAccount.getId()).child("sex").setValue(" ");
                        accRef.child(signInAccount.getId()).child("date").setValue(" ");
                        Toast.makeText(Profile.this, "Xóa thành công", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
    private void init(){
        txtSex = findViewById(R.id.txtGioiTinh);
//        edtDate = (EditText) findViewById(R.id.edtDate);
        btnLogout = findViewById(R.id.btn_logout);
        txtTen = findViewById(R.id.txtName);
        txtNgaySinh = findViewById(R.id.txtNgaySinh);
        btnXoa=findViewById(R.id.btnxoa);
        imgvatar=findViewById(R.id.imAVT);

    }

    private void DataProfile() {

        accRef.child(signInAccount.getId()).child("sex").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String Sex = snapshot.getValue().toString();
                    txtSex.setText(Sex);
                }
                else {
                    txtSex.getText();
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        accRef.child(signInAccount.getId()).child("date").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String date= snapshot.getValue().toString();
                    txtNgaySinh.setText(date);}
                else {
                    txtNgaySinh.getText();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        accRef.child(signInAccount.getId()).child("hoTen").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String name = snapshot.getValue().toString();
                    txtTen.setText(name);}
                else {
                    txtTen.setText(signInAccount.getDisplayName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Uri photourl = signInAccount.getPhotoUrl();
        Glide.with(this).load(photourl).error(R.drawable.login).into(imgvatar);

    }

}