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

import java.util.ArrayList;
import java.util.List;

public class contributions_type_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<contributions_type_model> contributions_type_models;
    private Context mContext;
    private String TAG ="contributionsAdapter";

    public contributions_type_adapter(List<contributions_type_model> contributions_type_models, Context mContext) {
        this.contributions_type_models = contributions_type_models;
        this.mContext = mContext;
    }



    private class contributionsView extends RecyclerView.ViewHolder {

        TextView contribution_type,contribution_type_id;


        public contributionsView(View itemView) {
            super(itemView);

            contribution_type =  itemView.findViewById(R.id.contribution_type);
            contribution_type_id =  itemView.findViewById(R.id.contribution_type_id);

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_contributions_type_adapter, parent,false);
        Log.e(TAG, "  view created ");
        return new contributionsView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        final contributions_type_model model =  contributions_type_models.get(position);

        ((contributionsView) holder).contribution_type.setText(model.getContribution_type());
        ((contributionsView) holder).contribution_type_id.setText(model.getContribution_type_id());


    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
