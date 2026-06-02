package ck.my65131996.spendwise;

import android.app.AlertDialog;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import java.util.ArrayList;
public class FundAdapter
        extends RecyclerView.Adapter<FundAdapter.ViewHolder> {
    ArrayList<FundModel> list;
    public FundAdapter(FundActivity context, ArrayList<FundModel> list) {
        this.list = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fund, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position) {
        FundModel model = list.get(position);
        holder.txtFundName.setText(model.getName());
        holder.txtFundMoney.setText(model.getCurrent() + " / " + model.getTarget() + " đ");
        try {
            int current = Integer.parseInt(model.getCurrent());
            int target = Integer.parseInt(model.getTarget());
            if(target > 0){int percent = current * 100 / target;
                holder.progressFund.setProgress(percent);
            }
        } catch (Exception ignored) {
        }
        // XÓA
        holder.btnDelete.setOnClickListener(v -> {
            long currentMoney = 0;
            try {

                currentMoney = Long.parseLong(model.getCurrent());
                    } catch (Exception ignored) {
                    }
                    if(currentMoney > 0){
                        saveFundTransaction(currentMoney, true);
                    }
                    FirebaseDatabase.getInstance(
                            "https://spendwise-8253b-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("funds")
                            .child(FirebaseAuth.getInstance().getUid())
                            .child(model.getFundId()).removeValue();
                });
        // GÓP
        holder.btnDeposit.setOnClickListener(v -> {showMoneyDialog(holder, model, true);
                });

        // RÚT
        holder.btnWithdraw.setOnClickListener(v -> {showMoneyDialog(holder, model, false);
                });
    }
    private void showMoneyDialog(
            ViewHolder holder,
            FundModel model,
            boolean isDeposit) {
        EditText edt = new EditText(holder.itemView.getContext());
        new AlertDialog.Builder(holder.itemView.getContext()).setTitle(isDeposit ? "Góp quỹ" : "Rút quỹ")
                .setView(edt).setPositiveButton("OK", (dialog, which) -> {
                    try {
                        long amount = Long.parseLong(edt.getText().toString());
                        long current = Long.parseLong(model.getCurrent());
                        if(isDeposit){
                            current += amount;
                            saveFundTransaction(amount, false);
                        }else{
                            if(amount > current){
                                Toast.makeText(holder.itemView.getContext(), "Số dư hũ không đủ", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    current -= amount;
                                    saveFundTransaction(amount, true);
                                }
                        FirebaseDatabase.getInstance(
                                "https://spendwise-8253b-default-rtdb.asia-southeast1.firebasedatabase.app/")
                                        .getReference("funds").child(FirebaseAuth.getInstance().getUid())
                                        .child(model.getFundId()).child("current").setValue(String.valueOf(current));
                            } catch (Exception ignored) {
                            }

                        })
                .show();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    static class ViewHolder
            extends RecyclerView.ViewHolder {
        TextView txtFundName,
                txtFundMoney;
        ProgressBar progressFund;
        Button btnDeposit,
                btnWithdraw,
                btnDelete;
        public ViewHolder(
                @NonNull View itemView) {
            super(itemView);
            txtFundName = itemView.findViewById(R.id.txtFundName);

            txtFundMoney = itemView.findViewById(R.id.txtFundMoney);

            progressFund = itemView.findViewById(R.id.progressFund);

            btnDeposit = itemView.findViewById(R.id.btnDeposit);

            btnWithdraw = itemView.findViewById(R.id.btnWithdraw);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
    private void saveFundTransaction(
            long amount,
            boolean isIncome){
        String uid = FirebaseAuth.getInstance().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance(
                "https://spendwise-8253b-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("transactions");
        String transactionId = ref.child(uid).push().getKey();
        TransactionModel model = new TransactionModel();
        model.setTransactionId(transactionId);
        model.setMoney(String.valueOf(amount));
        model.setIsIncome(isIncome);
        if(isIncome){
            model.setCategory("🏦 Hoàn quỹ");
        }else{
            model.setCategory("🏦 Góp quỹ");
        }
        model.setDate(new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date()));
        model.setNote("");
        model.setTimestamp(System.currentTimeMillis());
        ref.child(uid).child(transactionId).setValue(model);
    }
}
