package ck.my65131996.spendwise;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

public class HomeActivity
        extends AppCompatActivity {

    Button btnAddTransaction;
    RecyclerView recyclerTransaction;

    ArrayList<TransactionModel> transactionList;

    TransactionAdapter adapter;
    TextView txtBalance,
            txtExpense,
            txtIncome;

    int balance = 0;

    int expense = 0;

    int income = 0;

    FirebaseAuth mAuth;

    DatabaseReference databaseReference;

    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        // VIEW

        btnAddTransaction =
                findViewById(R.id.btnAddTransaction);

        recyclerTransaction =
                findViewById(R.id.recyclerTransaction);

        transactionList =
                new ArrayList<>();

        adapter =
                new TransactionAdapter(
                        this,
                        transactionList);

        recyclerTransaction.setLayoutManager(
                new LinearLayoutManager(this));

        recyclerTransaction.setAdapter(adapter);

        txtBalance =
                findViewById(R.id.txtBalance);

        txtExpense =
                findViewById(R.id.txtExpense);

        txtIncome =
                findViewById(R.id.txtIncome);

        bottomNavigation =
                findViewById(R.id.bottomNavigation);

        // FIREBASE

        mAuth =
                FirebaseAuth.getInstance();

        FirebaseDatabase database =
                FirebaseDatabase.getInstance(
                        "https://spendwise-8253b-default-rtdb.asia-southeast1.firebasedatabase.app/"
                );

        databaseReference =
                database.getReference("transactions");

        // BUTTON ADD

        btnAddTransaction.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            HomeActivity.this,
                            AddTransactionActivity.class
                    );

            startActivity(intent);
        });

        // MENU DƯỚI

        bottomNavigation.setSelectedItemId(
                R.id.navigation_home);

        bottomNavigation.setOnItemSelectedListener(item -> {

            // HOME

            if (item.getItemId()
                    == R.id.navigation_home) {

                return true;
            }

            // CHỨC NĂNG

            else if (item.getItemId()
                    == R.id.navigation_feature) {

                startActivity(

                        new Intent(
                                HomeActivity.this,
                                FundActivity.class
                        )
                );

                return true;
            }
            // PROFILE

            else if (item.getItemId()
                    == R.id.navigation_profile) {

                startActivity(

                        new Intent(
                                HomeActivity.this,
                                ProfileActivity.class
                        )
                );

                return true;
            }

            return false;
        });

        // LOAD DATA

        loadData();
    }

    private void loadData() {

        FirebaseUser user =
                mAuth.getCurrentUser();

        if (user == null) {

            Toast.makeText(
                    this,
                    "Chưa đăng nhập",
                    Toast.LENGTH_LONG
            ).show();

            return;
        }

        String uid =
                user.getUid();

        databaseReference

                .child(uid)

                .addValueEventListener(
                        new ValueEventListener() {

                            @Override
                            public void onDataChange(
                                    @NonNull DataSnapshot snapshot) {

                                balance = 0;
                                income = 0;
                                expense = 0;

                                transactionList.clear();

                                for (DataSnapshot data : snapshot.getChildren()) {

                                    TransactionModel model =
                                            data.getValue(
                                                    TransactionModel.class);

                                    if (model == null) {
                                        continue;
                                    }

                                    Boolean incomeValue =
                                            data.child("isIncome")
                                                    .getValue(Boolean.class);

                                    boolean isIncome =
                                            incomeValue != null
                                                    && incomeValue;

                                    model.setIncome(isIncome);

                                    model.setTransactionId(
                                            data.getKey());

                                    transactionList.add(model);

                                    int money = 0;

                                    try {
                                        money = Integer.parseInt(
                                                model.getMoney());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    if (isIncome) {

                                        balance += money;

                                        if(!model.getCategory().equals("🏦 Rút quỹ")
                                                && !model.getCategory().equals("🏦 Hoàn quỹ")){

                                            income += money;
                                        }

                                    } else {

                                        expense += money;
                                        balance -= money;
                                    }
                                }

                                adapter.notifyDataSetChanged();

                                updateUI();
                            }

                            @Override
                            public void onCancelled(
                                    @NonNull DatabaseError error) {

                                Toast.makeText(
                                        HomeActivity.this,
                                        error.getMessage(),
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        });
    }

    private void updateUI() {

        txtBalance.setText(
                String.format("%,d đ", balance));

        txtIncome.setText(
                "+" + String.format("%,d", income) + " đ");

        txtExpense.setText(
                "-" + String.format("%,d", expense) + " đ");
    }
}