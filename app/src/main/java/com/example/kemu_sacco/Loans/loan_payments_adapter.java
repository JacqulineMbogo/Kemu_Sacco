package com.example.kemu_sacco.Loans;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kemu_sacco.R;

import java.util.List;



public class loan_payments_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<loan_payment_model> loanpayments_models;
    private Context mContext;
    private String TAG ="loanpaymentsAdapter";

    public loan_payments_adapter(List<loan_payment_model> loanpayments_models, Context mContext) {
        this.loanpayments_models = loanpayments_models;
        this.mContext = mContext;
    }

    private class paymentsView extends RecyclerView.ViewHolder {

        TextView payment_id, amount, payment_date;


        public paymentsView(View itemView) {
            super(itemView);

            payment_id =  itemView.findViewById(R.id.payment_id);
            amount =  itemView.findViewById(R.id.amount);
            payment_date =  itemView.findViewById(R.id.contribution_date);

        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_loan_payments_adapter, parent,false);
        Log.e(TAG, "  view created ");
        return new paymentsView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        final  loan_payment_model model =  loanpayments_models.get(position);

        ((paymentsView) holder).payment_id.setText(model.getPayment_id());
        ((paymentsView) holder).amount.setText(model.getPayment_amount());
        ((paymentsView) holder).payment_date.setText(model.getCreate_date());


    }

    @Override
    public int getItemCount() {

        return loanpayments_models.size();
    }
}
