package ck.my65131996.spendwise;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity
        extends AppCompatActivity {

    EditText edtName,
            edtEmail,
            edtAddress;

    Button btnSave;

    ImageView imgAvatar,
            imgCover,
            btnChangeAvatar,
            btnChangeCover;

    FirebaseAuth mAuth;

    DatabaseReference userRef;

    String API_KEY =
            "6355d825209d7d7238bfd6a00ffe405b";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_edit_profile);

        edtName =
                findViewById(R.id.edtName);

        edtEmail =
                findViewById(R.id.edtEmail);

        edtAddress =
                findViewById(R.id.edtAddress);

        btnSave =
                findViewById(R.id.btnSave);

        imgAvatar =
                findViewById(R.id.imgAvatar);

        imgCover =
                findViewById(R.id.imgCover);

        btnChangeAvatar =
                findViewById(R.id.btnChangeAvatar);

        btnChangeCover =
                findViewById(R.id.btnChangeCover);

        mAuth =
                FirebaseAuth.getInstance();

        userRef =
                FirebaseDatabase

                        .getInstance(
                                "https://spendwise-8253b-default-rtdb.asia-southeast1.firebasedatabase.app/"
                        )

                        .getReference("users");

        loadUserData();

        btnChangeAvatar.setOnClickListener(v -> {

            chooseImage(100);
        });

        btnChangeCover.setOnClickListener(v -> {

            chooseImage(200);
        });

        btnSave.setOnClickListener(v -> {

            saveProfile();
        });
    }

    private void chooseImage(int code) {

        Intent intent =
                new Intent(
                        Intent.ACTION_PICK);

        intent.setType("image/*");

        startActivityForResult(
                intent,
                code);
    }

    private void loadUserData() {

        String uid =
                mAuth.getCurrentUser()
                        .getUid();

        userRef.child(uid)

                .get()

                .addOnSuccessListener(snapshot -> {

                    if (snapshot.exists()) {

                        edtName.setText(

                                snapshot.child("name")
                                        .getValue(String.class)
                        );

                        edtEmail.setText(

                                snapshot.child("email")
                                        .getValue(String.class)
                        );

                        edtAddress.setText(

                                snapshot.child("address")
                                        .getValue(String.class)
                        );

                        String avatar =
                                snapshot.child("avatar")
                                        .getValue(String.class);

                        String cover =
                                snapshot.child("cover")
                                        .getValue(String.class);

                        Glide.with(this)
                                .load(avatar)
                                .into(imgAvatar);

                        Glide.with(this)
                                .load(cover)
                                .into(imgCover);
                    }
                });
    }

    private void saveProfile() {

        String uid =
                mAuth.getCurrentUser()
                        .getUid();

        HashMap<String, Object> map =
                new HashMap<>();

        map.put(
                "name",
                edtName.getText().toString());

        map.put(
                "email",
                edtEmail.getText().toString());

        map.put(
                "address",
                edtAddress.getText().toString());

        userRef.child(uid)

                .updateChildren(map)

                .addOnSuccessListener(unused -> {

                    Toast.makeText(
                            this,
                            "Lưu thành công 😄",
                            Toast.LENGTH_SHORT
                    ).show();

                    finish();
                });
    }

    @Override
    protected void onActivityResult(
            int requestCode,
            int resultCode,
            @Nullable Intent data) {

        super.onActivityResult(
                requestCode,
                resultCode,
                data);

        if (resultCode == RESULT_OK
                && data != null) {

            Uri imageUri =
                    data.getData();

            if (requestCode == 100) {

                imgAvatar.setImageURI(imageUri);

                uploadImage(
                        imageUri,
                        "avatar");
            }

            if (requestCode == 200) {

                imgCover.setImageURI(imageUri);

                uploadImage(
                        imageUri,
                        "cover");
            }
        }
    }

    private void uploadImage(
            Uri imageUri,
            String type
    ) {

        try {

            InputStream inputStream =
                    getContentResolver()
                            .openInputStream(imageUri);

            byte[] imageBytes =
                    new byte[inputStream.available()];

            inputStream.read(imageBytes);

            RequestBody requestFile =

                    RequestBody.create(

                            MediaType.parse(
                                    "image/*"
                            ),

                            imageBytes
                    );

            MultipartBody.Part body =

                    MultipartBody.Part.createFormData(

                            "image",

                            "image.jpg",

                            requestFile
                    );

            ImgBBService service =

                    RetrofitClient

                            .getClient()

                            .create(
                                    ImgBBService.class
                            );

            service.uploadImage(
                            API_KEY,
                            body
                    )

                    .enqueue(

                            new Callback<ResponseBody>() {

                                @Override
                                public void onResponse(

                                        Call<ResponseBody> call,

                                        Response<ResponseBody> response
                                ) {

                                    try {

                                        String json =
                                                response.body()
                                                        .string();

                                        JSONObject object =
                                                new JSONObject(json);

                                        String imageUrl =

                                                object

                                                        .getJSONObject("data")

                                                        .getString("url");

                                        String uid =
                                                mAuth.getCurrentUser()
                                                        .getUid();

                                        userRef.child(uid)

                                                .child(type)

                                                .setValue(imageUrl);

                                    }

                                    catch (Exception e) {

                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(

                                        Call<ResponseBody> call,

                                        Throwable t
                                ) {

                                    Toast.makeText(

                                            EditProfileActivity.this,

                                            t.getMessage(),

                                            Toast.LENGTH_LONG

                                    ).show();
                                }
                            });

        }

        catch (Exception e) {

            e.printStackTrace();
        }
    }
}