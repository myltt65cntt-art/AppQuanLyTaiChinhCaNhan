package ck.my65131996.spendwise;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {
    private EditText edtEmail;
    private Button btnSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        edtEmail = findViewById(R.id.edtEmailReset);
        btnSend = findViewById(R.id.btnSendReset);
        btnSend.setOnClickListener(v -> {
            String email = edtEmail.getText().toString().trim();
            if(email.isEmpty()){
                Toast.makeText(this, "Nhập email", Toast.LENGTH_SHORT).show();
                return;
            }
            // Gửi email đặt lại mật khẩu bằng Firebase Authentication
            FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            Toast.makeText(this, "Đã gửi email đặt lại mật khẩu", Toast.LENGTH_LONG).show();
                            finish();
                        }else{
                            // Hiển thị lỗi nếu gửi thất bại
                            Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_LONG
                            ).show();
                        }
                    });
        });
    }
}