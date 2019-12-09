package com.example.kemu_sacco.Withdrawals;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kemu_sacco.Contributions.contributions_adapter;
import com.example.kemu_sacco.Contributions.contributions_model;
import com.example.kemu_sacco.R;

import java.util.List;

public class withdrawal_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<withdrawals_model> withdrawals_models;
    private Context mContext;
    private String TAG ="withdrawalAdapter";

    public withdrawal_adapter(List<withdrawals_model> withdrawals_models, Context mContext) {
        this.withdrawals_models = withdrawals_models;
        this.mContext = mContext;
    }


    private class withdrawalsView extends RecyclerView.ViewHolder {

        TextView id, amount, date;


        public withdrawalsView(View itemView) {
            super(itemView);

            id =  itemView.findViewById(R.id.id);
            amount =  itemView.findViewById(R.id.amount);
            date =  itemView.findViewById(R.id.date);

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_withdrawal_adapter, parent,false);
        Log.e(TAG, "  view created ");
        return new withdrawalsView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        final withdrawals_model model =  withdrawals_models.get(position);

        ((withdrawal_adapter.withdrawalsView) holder).id.setText(model.getWithdrawal_id());
        ((withdrawalsView) holder).amount.setText(model.getWithdrawal_amount());
        ((withdrawalsView) holder).date.setText(model.getWithdrawal_date());
    }

    @Override
    public int getItemCount() {
        return withdrawals_models.size();
    }
}
