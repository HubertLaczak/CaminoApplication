package com.example.hlaczak.caminoapplication.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.example.hlaczak.caminoapplication.DuringPlanning.OneCategoryBackpack;
import com.example.hlaczak.caminoapplication.R;
import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends ArrayAdapter<String> {

    private List<String> ItemsList = new ArrayList<>();
    private Context context;


    public ListViewAdapter(List<String> Fruits, Context context) {
        super(context, R.layout.backpacks_items_layout, Fruits);
        this.ItemsList = Fruits;
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView,  @NonNull ViewGroup parent) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View row = inflater.inflate(R.layout.backpacks_items_layout,parent, false);
        TextView item_name = row.findViewById(R.id.item_name);
        item_name.setText(ItemsList.get(position));
        CheckBox checkBox = row.findViewById(R.id.checkBox);
        checkBox.setTag(position);
        if(OneCategoryBackpack.isActionMode){
            checkBox.setVisibility(View.VISIBLE);
        } else {
            checkBox.setVisibility(View.GONE);
        }

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int position = (int) buttonView.getTag();
                if(OneCategoryBackpack.UserSelection.contains(ItemsList.get(position))){
                    OneCategoryBackpack.UserSelection.remove(ItemsList.get(position));
                } else {
                    OneCategoryBackpack.UserSelection.add(ItemsList.get(position));
                }

                OneCategoryBackpack.actionMode.setTitle(OneCategoryBackpack.UserSelection.size() + " items selected...");
            }
        });

        return row;
    }

    public void removeItems(List<String> items){
        for (String item: items){
            ItemsList.remove(item);
        }
        notifyDataSetChanged();
    }
}
