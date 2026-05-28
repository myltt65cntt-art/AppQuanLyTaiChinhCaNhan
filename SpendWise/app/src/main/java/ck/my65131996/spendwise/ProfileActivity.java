package ck.my65131996.spendwise;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity
        extends AppCompatActivity {

    TextView txtName,
            txtEmail,
            txtAddress;

    Button btnEdit,
            btnLogout;

    ImageView imgAvatar,
            imgCover;

    FirebaseAuth mAuth;

    DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_profile);

        // FIREBASE

        mAuth =
                FirebaseAuth.getInstance();

        userRef =
                FirebaseDatabase

                        .getInstance(
                                "https://spendwise-8253b-default-rtdb.asia-southeast1.firebasedatabase.app/"
                        )

                        .getReference("users");

        // VIEW

        txtName =
                findViewById(R.id.txtName);

        txtEmail =
                findViewById(R.id.txtEmail);

        txtAddress =
                findViewById(R.id.txtAddress);

        btnEdit =
                findViewById(R.id.btnEdit);

        btnLogout =
                findViewById(R.id.btnLogout);

        imgAvatar =
                findViewById(R.id.imgAvatar);

        imgCover =
                findViewById(R.id.imgCover);

        // USER DATA

        if (mAuth.getCurrentUser() != null) {

            String uid =
                    mAuth.getCurrentUser()
                            .getUid();

            // EMAIL AUTH

            txtEmail.setText(

                    mAuth.getCurrentUser()
                            .getEmail()
            );

            // LOAD DATABASE

            userRef.child(uid)

                    .get()

                    .addOnSuccessListener(snapshot -> {

                        if (snapshot.exists()) {

                            String name =
                                    snapshot.child("name")
                                            .getValue(String.class);

                            String address =
                                    snapshot.child("address")
                                            .getValue(String.class);

                            String avatar =
                                    snapshot.child("avatar")
                                            .getValue(String.class);

                            String cover =
                                    snapshot.child("cover")
                                            .getValue(String.class);

                            // NAME

                            if (name != null) {

                                txtName.setText(name);

                            } else {

                                txtName.setText(
                                        "SpendWise User");
                            }

                            // ADDRESS

                            if (address != null) {

                                txtAddress.setText(
                                        "📍 " + address);

                            } else {

                                txtAddress.setText(
                                        "📍 Chưa cập nhật");
                            }

                            // AVATAR

                            if (avatar != null) {

                                Glide.with(this)

                                        .load(avatar)

                                        .into(imgAvatar);
                            }

                            // COVER

                            if (cover != null) {

                                Glide.with(this)

                                        .load(cover)

                                        .into(imgCover);
                            }
                        }
                    });
        }

        // EDIT PROFILE

        btnEdit.setOnClickListener(v -> {

            startActivity(

                    new Intent(
                            this,
                            EditProfileActivity.class
                    )
            );
        });

        // LOGOUT

        btnLogout.setOnClickListener(v -> {

            mAuth.signOut();

            startActivity(

                    new Intent(
                            this,
                            MainActivity.class
                    )
            );

            finish();
        });
    }

    // LOAD LẠI KHI QUAY VỀ

    @Override
    protected void onResume() {

        super.onResume();

        if (mAuth.getCurrentUser() != null) {

            String uid =
                    mAuth.getCurrentUser()
                            .getUid();

            userRef.child(uid)

                    .get()

                    .addOnSuccessListener(snapshot -> {

                        if (snapshot.exists()) {

                            String name =
                                    snapshot.child("name")
                                            .getValue(String.class);

                            String address =
                                    snapshot.child("address")
                                            .getValue(String.class);

                            String avatar =
                                    snapshot.child("avatar")
                                            .getValue(String.class);

                            String cover =
                                    snapshot.child("cover")
                                            .getValue(String.class);

                            if (name != null) {

                                txtName.setText(name);
                            }

                            if (address != null) {

                                txtAddress.setText(
                                        "📍 " + address);
                            }

                            if (avatar != null) {

                                Glide.with(this)

                                        .load(avatar)

                                        .into(imgAvatar);
                            }

                            if (cover != null) {

                                Glide.with(this)

                                        .load(cover)

                                        .into(imgCover);
                            }
                        }
                    });
        }
    }
}