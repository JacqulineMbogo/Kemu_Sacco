package com.example.kemu_sacco.Account;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kemu_sacco.R;
import com.example.kemu_sacco.Utility.SharedPreferenceActivity;

import java.util.List;

public class Kin_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Kin_Model> kin_models;
    SharedPreferenceActivity sharedPreferenceActivity;
    private Context mContext;
    private String TAG ="kinAdapter";

    public Kin_Adapter(List<Kin_Model> kin_models, Context mContext) {
        this.kin_models = kin_models;
        this.mContext = mContext;
    }

    private class kinItemView extends RecyclerView.ViewHolder {
        ImageView delete, edit;
        TextView name, phone, relation,id;


        public kinItemView(View itemView) {
            super(itemView);

            name= itemView.findViewById(R.id.name);
            phone= itemView.findViewById(R.id.phone);
            relation= itemView.findViewById(R.id.relation);
            id= itemView.findViewById(R.id.id);
            delete= itemView.findViewById(R.id.delete);
            edit= itemView.findViewById(R.id.edit);



        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_kin_item, parent,false);
        Log.e(TAG, "  view created ");


        this.sharedPreferenceActivity = new SharedPreferenceActivity(mContext);
        return new kinItemView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        final Kin_Model model =  kin_models.get(position);

        ((kinItemView) holder).name.setText("Name:"+ " " +model.getName());
        ((kinItemView) holder).phone.setText("Phone:"+ " " +model.getPhone());
        ((kinItemView) holder).id.setText("Id :"+ " " +model.getId());
        ((kinItemView) holder).relation.setText("Relation:"+ " " +model.getRelation());

    }

    @Override
    public int getItemCount() {

        return kin_models.size();
    }
}
