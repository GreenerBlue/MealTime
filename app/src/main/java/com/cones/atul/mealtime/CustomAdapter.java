package com.cones.atul.mealtime;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Collection;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<String> {

    Context c;
    List<String> items;

    public CustomAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        this.c=context;
        this.items=objects;
    }

    @Override
    public void addAll(@NonNull Collection<? extends String> collection) {
        super.addAll(collection);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
            convertView= LayoutInflater.from(c).inflate(R.layout.order_list_item, parent,false);
        }
        TextView name = convertView.findViewById(R.id.add_meal_name);
        name.setText(items.get(position));

        Button up=convertView.findViewById(R.id.add_meal_up);
        Button down=convertView.findViewById(R.id.add_meal_down);

        final EditText qty = convertView.findViewById(R.id.add_meal_qty);

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quan = Integer.parseInt(qty.getText().toString());
                if(quan<10)
                    qty.setText(String.valueOf(quan+1));
            }
        });

        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quan = Integer.parseInt(qty.getText().toString());
                if(quan>0)
                    qty.setText(String.valueOf(quan-1));
            }
        });

        return convertView;
    }
}
