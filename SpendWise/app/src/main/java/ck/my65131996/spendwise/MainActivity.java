package ck.my65131996.spendwise;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import android.content.Intent;
public class MainActivity extends AppCompatActivity {

    EditText edtEmail, edtPassword;
    Button btnLogin, btnRegister;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);

        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        mAuth = FirebaseAuth.getInstance();

        // Đăng ký
        btnRegister.setOnClickListener(v -> {
            Intent intent =
                    new Intent(MainActivity.this,
                            RegisterActivity.class);

            startActivity(intent);
        });

        // Đăng nhập
        btnLogin.setOnClickListener(v -> loginUser());
    }

    private void registerUser() {

        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this,
                    "Vui lòng nhập Email và Mật khẩu",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        Toast.makeText(this,
                                "Đăng ký thành công",
                                Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(this,
                                task.getException().getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void loginUser() {

        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this,
                    "Vui lòng nhập Email và Mật khẩu",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        Toast.makeText(this,
                                "Đăng nhập thành công",
                                Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(this,
                                "Email hoặc mật khẩu không đúng",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}