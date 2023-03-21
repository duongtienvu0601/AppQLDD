package com.example.qldd.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.qldd.Adapter.MarterialAdapter;
import com.example.qldd.Object.Material;
import com.example.qldd.Object.PhieuDiCho;
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

import java.util.ArrayList;
import java.util.List;

public class GioHangActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference maRef = database.getReference("Material");
    DatabaseReference marketRef;
    ImageView imgGG;
    GoogleSignInAccount signInAccount;
    RecyclerView nguyenLieuRv;
    MarterialAdapter nguyenLieuApdater;
  static  List<Material> materialList = new ArrayList<>();
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        marketRef = database.getReference("Account/" + signInAccount.getId() +"/PhieuDiCho");
        nguyenLieuRv =(RecyclerView) findViewById(R.id.VoHangRV);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        nguyenLieuApdater = new MarterialAdapter(materialList, this);
        nguyenLieuRv.setAdapter(nguyenLieuApdater);
        nguyenLieuRv.setLayoutManager(linearLayoutManager);
        imgGG = findViewById(R.id.imageView3);

        marketRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    PhieuDiCho phieuDiCho = dataSnapshot.getValue(PhieuDiCho.class);
                    maRef.child(phieuDiCho.getId_nguyenLieu()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            materialList.add(snapshot.getValue(Material.class));
                            System.out.println(materialList);
                            nguyenLieuApdater.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        imgGG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://monkey.edu.vn/ba-me-can-biet/gia-dinh/dinh-duong-gia-dinh/bang-thanh-phan-dinh-duong"));
                startActivity(intent);
            }
        });
    }

    protected void onResume() {
        super.onResume();
        nguyenLieuApdater.notifyDataSetChanged();
    }
}