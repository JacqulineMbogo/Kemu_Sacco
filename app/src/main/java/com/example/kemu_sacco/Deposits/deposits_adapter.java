package com.example.kemu_sacco.Deposits;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kemu_sacco.R;

import java.util.List;

public class deposits_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<deposit_model> deposit_models;
    private Context mContext;
    private String TAG ="loansAdapter";


    public deposits_adapter(List<deposit_model> deposit_models, Context mContext) {
        this.deposit_models = deposit_models;
        this.mContext = mContext;
    }


    private class depositsView extends RecyclerView.ViewHolder {

        TextView deposit_id,amount,month,status;
        CardView depositcard;


        public depositsView(View itemView) {
            super(itemView);

            depositcard = itemView.findViewById(R.id.depositcard);
            deposit_id=  itemView.findViewById(R.id.deposit_id);
            amount = itemView.findViewById(R.id.amount);
            month = itemView.findViewById(R.id.month);



        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_deposits_adapter, parent,false);
        Log.e(TAG, "  view created ");
        return new depositsView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        final deposit_model model =  deposit_models.get(position);

        ((depositsView) holder).deposit_id.setText("Deposit  Id: " + " " + model.getId());

        ((depositsView) holder).amount.setText("Amount: " + " " + model.getAmount());

        ((depositsView) holder).month.setText("For The Month: " + " " + model.getDate());

    }

    @Override
    public int getItemCount() {

        return deposit_models.size();
    }
}
