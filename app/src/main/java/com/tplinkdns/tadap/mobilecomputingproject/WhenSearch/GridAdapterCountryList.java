package com.tplinkdns.tadap.mobilecomputingproject.WhenSearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tplinkdns.tadap.mobilecomputingproject.R;
import com.tplinkdns.tadap.mobilecomputingproject.WhenSearch.CountryList;

import java.util.ArrayList;

public class GridAdapterCountryList extends BaseAdapter {

    Context context;

    ArrayList<CountryList> arrayList;

    public GridAdapterCountryList(Context context, ArrayList<CountryList> arrayList){
        this.context=context;
        this.arrayList=arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.country_list_grid,viewGroup,false);
        }

        TextView textView = view.findViewById(R.id.cityName);

        textView.setText(arrayList.get(i).cityName);

        return view;
    }
}

