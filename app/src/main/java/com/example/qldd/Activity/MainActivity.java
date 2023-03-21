package com.example.qldd.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.qldd.Object.User;
import com.example.qldd.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    GoogleSignInAccount signInAccount;

    GoogleSignInClient mGoogleSignInClient;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference accRef = database.getReference("Account");
    DatabaseReference ageRef = database.getReference("Ages");
    Button btnPush;
    RelativeLayout ImageBtnKeKhai, ImageBtnNguyenLieu, ImageBtnMonAn, ImageBtnTuVan;
    TextView txtTen;
    ImageView imgHinh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        init();
        checkmail();
        showProfile();
//        btnPush.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                accRef.child(signInAccount.getId()).child("sex").addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if(!snapshot.exists()){
//                            Intent khaibao = new Intent(getApplicationContext(), KhaiBaoActivity.class);
//                            startActivityForResult(khaibao , 0);
//                        }
//                        else {
//                            Intent NL = new Intent(getApplicationContext(), MaterialActivity.class);
//                            startActivityForResult(NL, 0);
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//
//            }
//        });
        imgHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(getApplicationContext(), Profile.class) ;
                startActivityForResult(profile,0);
            }
        });
        ImageBtnKeKhai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent khaibao = new Intent(getApplicationContext(), KhaiBaoActivity.class);
                startActivityForResult(khaibao, 0);
            }
        });

        ImageBtnNguyenLieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nguyenlieu = new Intent(getApplicationContext(), MaterialActivity.class);
                startActivityForResult(nguyenlieu, 0);
            }
        });

        ImageBtnMonAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent monan = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivityForResult(monan, 0);
            }
        });
        ImageBtnTuVan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tuvan = new Intent(getApplicationContext(), TuVanActivity.class);
                startActivityForResult(tuvan, 0);
            }
        });
    }

    private void checkmail()  {
        accRef.orderByChild("user_id").equalTo(signInAccount.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()) {
                    User newAcc = new User();
                    newAcc.setUser_id(signInAccount.getId());
                    newAcc.setEmail(signInAccount.getEmail());
                    newAcc.setHoTen(signInAccount.getDisplayName());
                    accRef.child(signInAccount.getId()).setValue(newAcc);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void showProfile() {

        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if(signInAccount != null) {
            accRef.child(signInAccount.getId()).child("hoTen").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()) {
                        String name = snapshot.getValue().toString();
                        txtTen.setText(name);
                    }
                    else {
                        txtTen.setText(signInAccount.getDisplayName());
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            Uri photourl = signInAccount.getPhotoUrl();
            Glide.with(this).load(photourl).error(R.drawable.login).into(imgHinh);

        }

    }

    public void init() {
        ImageBtnKeKhai = (RelativeLayout) findViewById(R.id.image_btnkekhai);
        ImageBtnNguyenLieu = (RelativeLayout) findViewById(R.id.image_btnNguyenLieu);
        ImageBtnMonAn = (RelativeLayout) findViewById(R.id.image_btnCTNguyenLieu);
        ImageBtnTuVan = (RelativeLayout) findViewById(R.id.image_btntuvan);
        txtTen= findViewById(R.id.txtTenHienThi);
        imgHinh=findViewById(R.id.imgIamgePF);

    }
}