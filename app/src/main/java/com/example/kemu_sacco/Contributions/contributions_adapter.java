package com.example.kemu_sacco.Contributions;

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

public class contributions_adapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<contributions_model> contributions_models;
    private Context mContext;
    private String TAG ="contributionsAdapter";


    public contributions_adapter (Context context, List<contributions_model> contributionModels){
        this.contributions_models = contributionModels;
        this.mContext = context;

    }

    private class contributionsView extends RecyclerView.ViewHolder {

        TextView contribution_type, amount, contribution_date,contribution_id;


        public contributionsView(View itemView) {
            super(itemView);

            contribution_type =  itemView.findViewById(R.id.contribution_type);
            amount =  itemView.findViewById(R.id.amount);
            contribution_date =  itemView.findViewById(R.id.contribution_date);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_contributions_adapter, parent,false);
        Log.e(TAG, "  view created ");
        return new contributionsView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        final contributions_model model =  contributions_models.get(position);

        ((contributionsView) holder).contribution_type.setText(model.getContribution_type_id());
        ((contributionsView) holder).amount.setText(model.getAmount());
        ((contributionsView) holder).contribution_date.setText(model.getContribution_date());



    }

    @Override
    public int getItemCount() {
        // return 0;
        return contributions_models.size();
    }
}
