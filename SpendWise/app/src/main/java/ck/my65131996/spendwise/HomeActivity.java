package ck.my65131996.spendwise;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    Button btnAddTransaction;

    TextView txtRecent,
            txtBalance,
            txtExpense,
            txtIncome;

    int balance = 5500000;

    int expense = 0;

    int income = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        // FIND VIEW

        btnAddTransaction =
                findViewById(R.id.btnAddTransaction);

        txtRecent =
                findViewById(R.id.txtRecent);

        txtBalance =
                findViewById(R.id.txtBalance);

        txtExpense =
                findViewById(R.id.txtExpense);

        txtIncome =
                findViewById(R.id.txtIncome);

        // BUTTON ADD TRANSACTION

        btnAddTransaction.setOnClickListener(v -> {

            Intent intent =
                    new Intent(HomeActivity.this,
                            AddTransactionActivity.class);

            startActivityForResult(intent, 1);

        });

        // UPDATE UI

        updateUI();
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {

        super.onActivityResult(
                requestCode,
                resultCode,
                data);

        if (requestCode == 1 &&
                resultCode == Activity.RESULT_OK) {

            String category =
                    data.getStringExtra(
                            "category");

            String moneyString =
                    data.getStringExtra(
                            "money");

            String note =
                    data.getStringExtra(
                            "note");

            String date =
                    data.getStringExtra(
                            "date");

            boolean isIncome =
                    data.getBooleanExtra(
                            "isIncome",
                            false);

            int money =
                    Integer.parseInt(
                            moneyString);

            // THU NHẬP

            if (isIncome) {

                income += money;

                balance += money;
            }

            // CHI TIÊU

            else {

                expense += money;

                balance -= money;
            }

            // UPDATE UI

            updateUI();

            // RECENT TRANSACTION

            txtRecent.setText(

                    category + "\n\n" +

                            money + " đ\n\n" +

                            note + "\n\n📅 " +

                            date
            );
        }
    }

    private void updateUI() {

        txtBalance.setText(
                balance + " đ");

        txtExpense.setText(
                "-" + expense + " đ");

        txtIncome.setText(
                "+" + income + " đ");
    }
}