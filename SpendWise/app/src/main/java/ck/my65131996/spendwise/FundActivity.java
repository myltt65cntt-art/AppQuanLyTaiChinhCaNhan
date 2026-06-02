package ck.my65131996.spendwise;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.content.Intent;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class FundActivity extends AppCompatActivity {
    EditText edtFundName,
            edtTarget;
    Button btnCreateFund;
    RecyclerView recyclerFunds;
    ArrayList<FundModel> fundList;
    FundAdapter adapter;
    FirebaseAuth mAuth;
    DatabaseReference fundRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Khởi tạo giao diện màn hình Quản lý hũ tiết kiệm
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund);
        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setSelectedItemId(R.id.navigation_feature);
        bottomNavigation.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.navigation_home){
                startActivity(new Intent(FundActivity.this, HomeActivity.class));
                finish();
                return true;
            }
            if(item.getItemId() == R.id.navigation_feature){
                return true;
            }
            if(item.getItemId() == R.id.navigation_profile){
                startActivity(new Intent(FundActivity.this, ProfileActivity.class));
                finish();
                return true;
            }
            return false;
        });
        edtFundName = findViewById(R.id.edtFundName);
        edtTarget = findViewById(R.id.edtTarget);
        btnCreateFund = findViewById(R.id.btnCreateFund);
        recyclerFunds = findViewById(R.id.recyclerFunds);
        recyclerFunds.setLayoutManager(new LinearLayoutManager(this));
        // Khởi tạo Adapter cho RecyclerView
        fundList = new ArrayList<>();
        adapter = new FundAdapter(this, fundList);
        recyclerFunds.setAdapter(adapter);
        // Kết nối Firebase Authentication
        mAuth = FirebaseAuth.getInstance();
        fundRef = FirebaseDatabase.getInstance(
                "https://spendwise-8253b-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("funds");
        btnCreateFund.setOnClickListener(v -> {
            createFund();

        });
        // Tải danh sách hũ từ Firebase
        loadFunds();
    }
    private void createFund() {
        // Lấy tên hũ và số tiền mục tiêu
        String name = edtFundName.getText().toString().trim();
        String target = edtTarget.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            edtFundName.setError("Nhập tên hũ");
            return;
        }
        // Kiểm tra dữ liệu nhập vào
        if (TextUtils.isEmpty(target)) {
            edtTarget.setError("Nhập mục tiêu");
            return;
        }
        // Lấy UID người dùng hiện tại
        String uid = mAuth.getCurrentUser().getUid();
        String fundId = fundRef.child(uid).push().getKey();
        // Tạo đối tượng hũ tiết kiệm
        FundModel model = new FundModel();
        model.setFundId(fundId);
        model.setName(name);
        model.setTarget(target);
        model.setCurrent("0");
        // Lưu dữ liệu lên Firebase
        fundRef.child(uid)
                .child(fundId)
                .setValue(model)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Tạo hũ thành công", Toast.LENGTH_SHORT).show();
                    edtFundName.setText("");
                    edtTarget.setText("");

                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();

                });
    }
    private void loadFunds() {
        String uid = mAuth.getCurrentUser().getUid();
        // Lắng nghe dữ liệu hũ tiết kiệm từ Firebase
        fundRef.child(uid).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(
                                    @NonNull DataSnapshot snapshot) {
                                fundList.clear();
                                for (DataSnapshot data : snapshot.getChildren()) {
                                    FundModel model = data.getValue(FundModel.class);
                                    fundList.add(model);
                                }
                                adapter.notifyDataSetChanged();
                            }
                            @Override
                            public void onCancelled(
                                    @NonNull DatabaseError error) {

                            }
                        });
    }
}