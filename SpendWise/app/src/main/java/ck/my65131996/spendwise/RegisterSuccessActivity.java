package ck.my65131996.spendwise;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_success);

        Button btnGoLogin = findViewById(R.id.btnGoLogin);

        btnGoLogin.setOnClickListener(v -> {
            Intent intent =
                    new Intent(RegisterSuccessActivity.this,
                            MainActivity.class);

            startActivity(intent);
            finish();
        });
    }
}