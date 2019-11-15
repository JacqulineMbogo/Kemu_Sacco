package com.example.kemu_sacco;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class Spinner_Adapter extends ArrayAdapter<String>
{
    Context context;
    String[] code;
    String[] name;

    public Spinner_Adapter(Context context, String[] code, String[] name)
    {
        super(context, R.layout.activity_contributions_type_adapter);

        this.context        =   context;
        this.code         =   code;
        this.name   =   name;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view   =   convertView;
        ViewHolder viewHolder;

        if(view==null)
        {
            LayoutInflater layoutInflater   =   (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view    =   layoutInflater.inflate(R.layout.activity_contributions_type_adapter, null, false);
            viewHolder  =   new ViewHolder(view);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder  =   (ViewHolder) view.getTag();
        }

        viewHolder.code.setText(code[position]);
        viewHolder.description.setText(name[position]);

        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        return getView(position, convertView, parent);
    }

    class ViewHolder
    {
        TextView code;
        TextView description;

        public ViewHolder(View view)
        {
            code    =   view.findViewById(R.id.contribution_type_id);
            description =   view.findViewById(R.id.text);
        }
    }
}
