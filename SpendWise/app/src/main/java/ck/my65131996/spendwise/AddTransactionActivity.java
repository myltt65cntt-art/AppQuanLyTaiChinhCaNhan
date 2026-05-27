package ck.my65131996.spendwise;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AddTransactionActivity extends AppCompatActivity {

    EditText edtMoney, edtNote;

    Button btnSave,
            btnExpense,
            btnIncome;

    LinearLayout layoutFood,
            layoutShopping,
            layoutOther,
            layoutDate;

    TextView txtDate;

    String selectedCategory = "";

    String selectedDate = "";

    boolean isIncome = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_transaction);

        // FIND VIEW

        edtMoney = findViewById(R.id.edtMoney);

        edtNote = findViewById(R.id.edtNote);

        btnSave = findViewById(R.id.btnSave);

        btnExpense = findViewById(R.id.btnExpense);

        btnIncome = findViewById(R.id.btnIncome);

        txtDate = findViewById(R.id.txtDate);

        layoutDate = findViewById(R.id.layoutDate);

        layoutFood = findViewById(R.id.layoutFood);

        layoutShopping =
                findViewById(R.id.layoutShopping);

        layoutOther =
                findViewById(R.id.layoutOther);

        // TAB CHI TIÊU

        btnExpense.setOnClickListener(v -> {

            isIncome = false;

            btnExpense.setAlpha(1f);

            btnIncome.setAlpha(0.5f);

            btnSave.setText("Lưu chi tiêu");

        });

        // TAB THU NHẬP

        btnIncome.setOnClickListener(v -> {

            isIncome = true;

            btnIncome.setAlpha(1f);

            btnExpense.setAlpha(0.5f);

            btnSave.setText("Lưu thu nhập");

        });

        // DANH MỤC

        layoutFood.setOnClickListener(v -> {

            if (isIncome) {

                selectedCategory =
                        "📈 Kinh doanh";

            } else {

                selectedCategory =
                        "🍜 Ăn uống";
            }

            selectCategory(layoutFood);
        });

        layoutShopping.setOnClickListener(v -> {

            if (isIncome) {

                selectedCategory =
                        "💼 Lương";

            } else {

                selectedCategory =
                        "🛍️ Mua sắm";
            }

            selectCategory(layoutShopping);
        });

        layoutOther.setOnClickListener(v -> {

            if (isIncome) {

                selectedCategory =
                        "🏅 Thưởng";

            } else {

                selectedCategory =
                        "✨ Khác";
            }

            selectCategory(layoutOther);
        });

        // CHỌN NGÀY

        layoutDate.setOnClickListener(v -> {

            Calendar calendar =
                    Calendar.getInstance();

            int year =
                    calendar.get(Calendar.YEAR);

            int month =
                    calendar.get(Calendar.MONTH);

            int day =
                    calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog =
                    new DatePickerDialog(
                            this,
                            (view, y, m, d) -> {

                                selectedDate =
                                        d + "/" +
                                                (m + 1) +
                                                "/" + y;

                                txtDate.setText(
                                        selectedDate);

                            },
                            year,
                            month,
                            day
                    );

            dialog.show();

        });

        // SAVE

        btnSave.setOnClickListener(v -> {

            saveTransaction();

        });
    }

    // CHỌN CARD

    private void selectCategory(
            LinearLayout selectedLayout) {

        layoutFood.setBackgroundColor(
                Color.WHITE);

        layoutShopping.setBackgroundColor(
                Color.WHITE);

        layoutOther.setBackgroundColor(
                Color.WHITE);

        selectedLayout.setBackgroundColor(
                Color.parseColor("#C8E6C9"));
    }

    // SAVE

    private void saveTransaction() {

        String money =
                edtMoney.getText()
                        .toString()
                        .trim();

        String note =
                edtNote.getText()
                        .toString()
                        .trim();

        if (money.isEmpty()) {

            Toast.makeText(
                    this,
                    "Nhập số tiền",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        if (selectedCategory.isEmpty()) {

            Toast.makeText(
                    this,
                    "Chọn danh mục",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        Intent intent = new Intent();

        intent.putExtra(
                "category",
                selectedCategory);

        intent.putExtra(
                "money",
                money);

        intent.putExtra(
                "note",
                note);

        intent.putExtra(
                "date",
                selectedDate);

        intent.putExtra(
                "isIncome",
                isIncome);

        setResult(
                RESULT_OK,
                intent);

        Toast.makeText(
                this,
                "Lưu thành công 🌱",
                Toast.LENGTH_SHORT
        ).show();

        finish();
    }
}