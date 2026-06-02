package ck.my65131996.spendwise;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddTransactionActivity extends AppCompatActivity {
    EditText edtMoney, edtNote;
    Button btnSave, btnExpense, btnIncome;
    String transactionId;
    boolean isEdit = false;
    LinearLayout layoutFood,
            layoutShopping,
            layoutTransport,
            layoutEntertainment,
            layoutOther,
            layoutDate;
    TextView txtDate;
    TextView txtFood;
    TextView txtShopping;
    TextView txtTransport;
    TextView txtEntertainment;
    TextView txtOther;
    TextView txtFoodIcon;
    TextView txtShoppingIcon;
    TextView txtTransportIcon;
    TextView txtEntertainmentIcon;
    TextView txtOtherIcon;
    String selectedCategory = "";
    String selectedDate = "";
    boolean isIncome = false;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        // FIREBASE
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://spendwise-8253b-default-rtdb.asia-southeast1.firebasedatabase.app/");
        databaseReference = database.getReference("transactions");
        // VIEW
        edtMoney = findViewById(R.id.edtMoney);
        edtNote = findViewById(R.id.edtNote);
        btnSave = findViewById(R.id.btnSave);
        btnExpense = findViewById(R.id.btnExpense);
        btnIncome = findViewById(R.id.btnIncome);
        txtDate = findViewById(R.id.txtDate);
        layoutDate = findViewById(R.id.layoutDate);
        layoutFood = findViewById(R.id.layoutFood);
        layoutShopping = findViewById(R.id.layoutShopping);
        layoutOther = findViewById(R.id.layoutOther);
        layoutTransport = findViewById(R.id.layoutTransport);
        layoutEntertainment = findViewById(R.id.layoutEntertainment);
        txtFood = findViewById(R.id.txtFood);
        txtShopping = findViewById(R.id.txtShopping);
        txtTransport = findViewById(R.id.txtTransport);
        txtEntertainment = findViewById(R.id.txtEntertainment);
        txtOther = findViewById(R.id.txtOther);
        txtFoodIcon = findViewById(R.id.txtFoodIcon);
        txtShoppingIcon = findViewById(R.id.txtShoppingIcon);
        txtTransportIcon = findViewById(R.id.txtTransportIcon);
        txtEntertainmentIcon = findViewById(R.id.txtEntertainmentIcon);
        txtOtherIcon = findViewById(R.id.txtOtherIcon);
        // DEFAULT
        transactionId = getIntent().getStringExtra("transactionId");
        if(transactionId != null){
            isEdit = true;
            btnSave.setText("Cập nhật");
            loadTransaction();
        }
        btnExpense.setAlpha(1f);
        btnIncome.setAlpha(0.5f);
        btnSave.setText("Lưu chi tiêu");
        // BUTTON CHI TIÊU
        btnExpense.setOnClickListener(v -> {
            isIncome = false;
            btnExpense.setAlpha(1f);
            btnIncome.setAlpha(0.5f);
            btnSave.setText("Lưu chi tiêu");
            txtFood.setText("🍜 Ăn uống");
            txtShopping.setText("🛍️ Mua sắm");
            txtTransport.setText("🚕 Đi lại");
            txtEntertainment.setText("🎮 Giải trí");
            txtOther.setText("✨ Khác");
            txtFoodIcon.setText("🍜");
            txtShoppingIcon.setText("🛍️");
            txtTransportIcon.setText("🚕");
            txtEntertainmentIcon.setText("🎮");
            txtOtherIcon.setText("✨");
        });
        // BUTTON THU NHẬP
        btnIncome.setOnClickListener(v -> {
            isIncome = true;
            btnIncome.setAlpha(1f);
            btnExpense.setAlpha(0.5f);
            btnSave.setText("Lưu thu nhập");
            txtFood.setText("Lương");
            txtShopping.setText("Thưởng");
            txtTransport.setText("Kinh doanh");
            txtEntertainment.setText("Làm thêm");
            txtOther.setText("Khác");
            txtFoodIcon.setText("💼");
            txtShoppingIcon.setText("🏅");
            txtTransportIcon.setText("📈");
            txtEntertainmentIcon.setText("💵");
            txtOtherIcon.setText("✨");
        });
        // CATEGORY
        layoutFood.setOnClickListener(v ->
        {
            selectedCategory = isIncome ? "💼 Lương" : "🍜 Ăn uống";
            selectCategory(layoutFood);
        });
        layoutShopping.setOnClickListener(v ->
        {
            selectedCategory = isIncome ? "🏅 Thưởng" : "🛍️ Mua sắm";
            selectCategory(layoutShopping);
        });
        layoutTransport.setOnClickListener(v ->
        {
            selectedCategory = isIncome ? "📈 Kinh doanh" : "🚕 Đi lại";
            selectCategory(layoutTransport);
        });
        layoutEntertainment.setOnClickListener(v ->
        {
            selectedCategory = isIncome ? "💵 Làm thêm" : "🎮 Giải trí";
            selectCategory(layoutEntertainment);
        });
        layoutOther.setOnClickListener(v ->
        {
            selectedCategory = "✨ Khác";
            selectCategory(layoutOther);
        });
        // CHỌN NGÀY
        layoutDate.setOnClickListener(v ->
        {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(AddTransactionActivity.this, (view, y, m, d) ->
            {
                selectedDate = d + "/" + (m + 1) + "/" + y;
                txtDate.setText(selectedDate);
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
    // SELECT CATEGORY
    private void loadTransaction() {
        FirebaseDatabase.getInstance("https://spendwise-8253b-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("transactions")
                .child(FirebaseAuth.getInstance().getUid())
                .child(transactionId)
                .get()
                .addOnSuccessListener(snapshot -> {
                    TransactionModel model = snapshot.getValue(TransactionModel.class);
                    if(model == null) return;
                    edtMoney.setText(model.getMoney());
                    edtNote.setText(model.getNote());
                    txtDate.setText(model.getDate());
                    selectedDate = model.getDate();
                    selectedCategory = model.getCategory();
                    isIncome = model.isIncome();
                });
    }
    private void selectCategory(LinearLayout selectedLayout) {
        layoutFood.setBackgroundColor(Color.WHITE);
        layoutShopping.setBackgroundColor(Color.WHITE);
        layoutTransport.setBackgroundColor(Color.WHITE);
        layoutEntertainment.setBackgroundColor(Color.WHITE);
        layoutOther.setBackgroundColor(Color.WHITE);
        selectedLayout.setBackgroundColor(
                Color.parseColor("#C8E6C9")
        );
    }
    // SAVE TRANSACTION
    private void saveTransaction() {
        String money = edtMoney.getText().toString().trim();
        String note = edtNote.getText().toString().trim();
        // VALIDATE
        if (money.isEmpty()) {
            Toast.makeText(this, "Nhập số tiền", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectedCategory.isEmpty()) {
            Toast.makeText(this, "Chọn danh mục", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectedDate.isEmpty()) {
            Toast.makeText(this, "Chọn ngày", Toast.LENGTH_SHORT).show();
            return;
        }
        // CHECK LOGIN
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {Toast.makeText(this, "Chưa đăng nhập Firebase", Toast.LENGTH_LONG).show();
            return;
        }
        String uid = user.getUid();
        // CREATE NEW NODE
        DatabaseReference newRef;
        if(isEdit){
            newRef = databaseReference.child(uid).child(transactionId);
        }else{
            newRef = databaseReference.child(uid).push();
            transactionId = newRef.getKey();
        }
        if (transactionId == null) {
            Toast.makeText(this, "Không tạo được ID", Toast.LENGTH_LONG).show();
            return;
        }
        // MAP DATA
        Map<String, Object> map = new HashMap<>();
        map.put("transactionId", transactionId);
        map.put("money", money);
        map.put("note", note);
        map.put("category", selectedCategory);
        map.put("date", selectedDate);
        map.put("isIncome", isIncome);
        map.put("timestamp", System.currentTimeMillis());
        // UI LOADING
        btnSave.setEnabled(false);
        btnSave.setText("Đang lưu...");
        Log.d("FIREBASE", "Bắt đầu lưu");
        // SAVE FIREBASE
        newRef.setValue(map).addOnSuccessListener(unused -> {
                    Log.d("FIREBASE", "Lưu thành công");
                    btnSave.setEnabled(true);
                    btnSave.setText("Đã lưu ✓");
                    Toast.makeText(AddTransactionActivity.this, "Lưu thành công ☁️", Toast.LENGTH_LONG
                    ).show();
                    Intent intent = new Intent();
                    intent.putExtra("money", money);
                    intent.putExtra("note", note);
                    intent.putExtra("category", selectedCategory);
                    intent.putExtra("date", selectedDate);
                    intent.putExtra("isIncome", isIncome);
                    setResult(RESULT_OK, intent);
                    finish();
                })
                .addOnFailureListener(e -> {
                    Log.e("FIREBASE_ERROR", e.getMessage());
                    btnSave.setEnabled(true);
                    btnSave.setText("Lưu lại");
                    Toast.makeText(AddTransactionActivity.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }
}