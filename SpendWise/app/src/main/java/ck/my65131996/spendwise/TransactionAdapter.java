package ck.my65131996.spendwise;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import android.graphics.Color;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
public class TransactionAdapter
        extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    ArrayList<TransactionModel> list;

    public TransactionAdapter(
            HomeActivity context,
            ArrayList<TransactionModel> list) {

        this.list = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position) {
        TransactionModel model = list.get(position);
// CLICK = SỬA
        holder.itemView.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(holder.itemView.getContext(), AddTransactionActivity.class);
            intent.putExtra("transactionId", model.getTransactionId());
            holder.itemView.getContext().startActivity(intent);
        });
// LONG CLICK = XÓA
        holder.itemView.setOnLongClickListener(v -> {
            if(model.getCategory().equals("🏦 Góp quỹ")
                    || model.getCategory().equals("🏦 Rút quỹ")
                    || model.getCategory().equals("🏦 Hoàn quỹ")){
                android.widget.Toast.makeText(holder.itemView.getContext(), "Không thể xóa giao dịch quỹ", android.widget.Toast.LENGTH_SHORT
                ).show();
                return true;
            }
            new android.app.AlertDialog.Builder(holder.itemView.getContext())
                    .setTitle("Xóa giao dịch")
                    .setMessage("Bạn có muốn xóa giao dịch này không?")
                    .setPositiveButton("Xóa",
                            (dialog, which) -> {
                                FirebaseDatabase.getInstance(
                                        "https://spendwise-8253b-default-rtdb.asia-southeast1.firebasedatabase.app/"
                                        )
                                        .getReference("transactions")
                                        .child(FirebaseAuth.getInstance().getUid()).child(model.getTransactionId())
                                        .removeValue().addOnSuccessListener(unused -> {
                                            android.widget.Toast.makeText(holder.itemView.getContext(), "Xóa thành công", android.widget.Toast.LENGTH_SHORT).show();
                                        })
                                        .addOnFailureListener(e -> {
                                            android.widget.Toast.makeText(holder.itemView.getContext(), e.getMessage(), android.widget.Toast.LENGTH_LONG).show();

                                        });

                            })
                    .setNegativeButton("Hủy", null)
                    .show();
            return true;
        });
        holder.txtCategory.setText(model.getCategory());
        if(model.isIncome()){
            holder.txtMoney.setText("+ " + model.getMoney() + " đ");
            holder.txtMoney.setTextColor(Color.parseColor("#4CAF50"));

        }else{
            holder.txtMoney.setText("- " + model.getMoney() + " đ");
            holder.txtMoney.setTextColor(Color.parseColor("#F44336"));
        }
        holder.txtDate.setText(model.getDate());
        holder.txtNote.setText(model.getNote());
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    static class ViewHolder
            extends RecyclerView.ViewHolder {
        TextView txtCategory,
                txtMoney,
                txtDate,
                txtNote;
        public ViewHolder(
                @NonNull View itemView) {

            super(itemView);
            txtCategory = itemView.findViewById(R.id.txtCategory);
            txtMoney = itemView.findViewById(R.id.txtMoney);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtNote = itemView.findViewById(R.id.txtNote);
        }
    }
}