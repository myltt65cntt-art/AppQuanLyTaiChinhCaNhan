package ck.my65131996.spendwise;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private EditText edtEmail, edtPassword;
    private TextView txtForgotPassword;
    private Button btnLogin, btnRegister;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        txtForgotPassword = findViewById(R.id.txtForgotPassword);
        mAuth = FirebaseAuth.getInstance();
        // Quên mật khẩu
        txtForgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });
        // Chuyển sang đăng ký
        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
        // Đăng nhập
        btnLogin.setOnClickListener(v -> loginUser());
    }
    private void loginUser() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập Email và Mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(MainActivity.this, "Email hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}