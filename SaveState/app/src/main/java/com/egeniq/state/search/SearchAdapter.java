package com.egeniq.state.search;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.egeniq.state.R;

/**
 * Created by andrei on 24/11/16.
 */

public class SearchAdapter extends ArrayAdapter<SearchData> {

    public SearchAdapter(Context context) {
        super(context, R.layout.search_list_item, R.id.text1);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        SearchData searchData = getItem(position);

        TextView textView = (TextView)view.findViewById(R.id.text1);
        textView.setText(String.valueOf(searchData.getCount()));

        textView = (TextView)view.findViewById(R.id.text2);
        textView.setText(searchData.getTitle());

        textView = (TextView)view.findViewById(R.id.text3);
        textView.setText(searchData.getDescription());

        return view;
    }
}
