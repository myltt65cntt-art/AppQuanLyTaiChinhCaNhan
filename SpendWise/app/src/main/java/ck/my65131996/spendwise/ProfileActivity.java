package ck.my65131996.spendwise;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ProfileActivity
        extends AppCompatActivity {

    TextView txtName,
            txtEmail,
            txtIncome,
            txtExpense,
            txtTransaction;

    Button btnEdit,
            btnLogout;

    ImageView imgAvatar;

    FirebaseAuth mAuth;

    DatabaseReference userRef;

    StorageReference storageReference;

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
                        .getInstance()
                        .getReference("users");

        storageReference =
                FirebaseStorage
                        .getInstance()
                        .getReference("avatars");

        // VIEW

        txtName =
                findViewById(R.id.txtName);

        txtEmail =
                findViewById(R.id.txtEmail);

        txtIncome =
                findViewById(R.id.txtIncome);

        txtExpense =
                findViewById(R.id.txtExpense);

        txtTransaction =
                findViewById(R.id.txtTransaction);

        btnEdit =
                findViewById(R.id.btnEdit);

        btnLogout =
                findViewById(R.id.btnLogout);

        imgAvatar = findViewById(R.id.imgAvatar);

        // USER DATA

        if (mAuth.getCurrentUser() != null) {

            String uid =
                    mAuth.getCurrentUser()
                            .getUid();

            txtEmail.setText(

                    mAuth.getCurrentUser()
                            .getEmail()
            );

            // LOAD USER INFO

            userRef.child(uid)

                    .get()

                    .addOnSuccessListener(snapshot -> {

                        if (snapshot.exists()) {

                            String name =
                                    snapshot.child("name")
                                            .getValue(String.class);

                            String avatar =
                                    snapshot.child("avatar")
                                            .getValue(String.class);

                            String income =
                                    snapshot.child("income")
                                            .getValue(String.class);

                            String expense =
                                    snapshot.child("expense")
                                            .getValue(String.class);

                            String transaction =
                                    snapshot.child("transaction")
                                            .getValue(String.class);

                            // NAME

                            if (name != null) {

                                txtName.setText(name);

                            } else {

                                txtName.setText(
                                        "SpendWise User 🌸");
                            }

                            // INCOME

                            if (income != null) {

                                txtIncome.setText(income);

                            } else {

                                txtIncome.setText("+0đ");
                            }

                            // EXPENSE

                            if (expense != null) {

                                txtExpense.setText(expense);

                            } else {

                                txtExpense.setText("-0đ");
                            }

                            // TRANSACTION

                            if (transaction != null) {

                                txtTransaction.setText(transaction);

                            } else {

                                txtTransaction.setText("0");
                            }

                            // AVATAR

                            if (avatar != null) {

                                Glide.with(this)

                                        .load(avatar)

                                        .into(imgAvatar);
                            }
                        }
                    });
        }

        // CLICK AVATAR

        imgAvatar.setOnClickListener(v -> {

            Intent intent =
                    new Intent(Intent.ACTION_PICK);

            intent.setType("image/*");

            startActivityForResult(intent, 100);

        });

        // EDIT

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

    // UPLOAD AVATAR

    @Override
    protected void onActivityResult(
            int requestCode,
            int resultCode,
            @Nullable Intent data) {

        super.onActivityResult(
                requestCode,
                resultCode,
                data);

        if (requestCode == 100
                && resultCode == RESULT_OK
                && data != null) {

            Uri imageUri =
                    data.getData();

            String uid =
                    mAuth.getCurrentUser()
                            .getUid();

            storageReference

                    .child(uid + ".jpg")

                    .putFile(imageUri)

                    .addOnSuccessListener(taskSnapshot -> {

                        storageReference

                                .child(uid + ".jpg")

                                .getDownloadUrl()

                                .addOnSuccessListener(uri -> {

                                    String imageLink =
                                            uri.toString();

                                    // SAVE LINK DATABASE

                                    userRef.child(uid)

                                            .child("avatar")

                                            .setValue(imageLink);

                                    // SHOW IMAGE

                                    Glide.with(this)

                                            .load(imageLink)

                                            .into(imgAvatar);

                                    Toast.makeText(
                                            this,
                                            "Đổi avatar thành công 🌸",
                                            Toast.LENGTH_SHORT
                                    ).show();

                                });

                    })

                    .addOnFailureListener(e -> {

                        Toast.makeText(
                                this,
                                "Upload thất bại",
                                Toast.LENGTH_SHORT
                        ).show();

                    });
        }
    }
}