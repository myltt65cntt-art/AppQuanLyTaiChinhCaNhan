package ck.my65131996.spendwise;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import android.graphics.Color;
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

        View view =
                LayoutInflater.from(
                                parent.getContext())
                        .inflate(
                                R.layout.item_transaction,
                                parent,
                                false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position) {

        TransactionModel model =
                list.get(position);

        holder.txtCategory.setText(
                model.getCategory());

        if(model.isIncome()){

            holder.txtMoney.setText(
                    "+ " + model.getMoney() + " đ");

            holder.txtMoney.setTextColor(
                    Color.parseColor("#4CAF50"));

        }else{

            holder.txtMoney.setText(
                    "- " + model.getMoney() + " đ");

            holder.txtMoney.setTextColor(
                    Color.parseColor("#F44336"));
        }

        holder.txtDate.setText(
                model.getDate());

        holder.txtNote.setText(
                model.getNote());
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

            txtCategory =
                    itemView.findViewById(
                            R.id.txtCategory);

            txtMoney =
                    itemView.findViewById(
                            R.id.txtMoney);

            txtDate =
                    itemView.findViewById(
                            R.id.txtDate);

            txtNote =
                    itemView.findViewById(
                            R.id.txtNote);
        }
    }
}