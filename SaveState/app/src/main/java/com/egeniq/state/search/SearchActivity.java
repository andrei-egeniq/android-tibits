package com.egeniq.state.search;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.egeniq.state.R;
import com.egeniq.state.app.StateCache;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by andrei on 24/11/16.
 */

public class SearchActivity extends AppCompatActivity {

    private ArrayList<SearchData> mSearchDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mSearchDataList = new ArrayList<>();
        if(savedInstanceState == null) {
            //fresh launch of the activity
            fakeSearchData();
        } else {
            //activity recreation
            mSearchDataList = StateCache.pop(mSearchDataList.getClass());
        }

        showSearchData();
    }

    private void fakeSearchData() {
        Random random = new Random();
        int size = (int)(100+ Math.random() * 100);
        for(int i = 0; i < size; i++) {
            mSearchDataList.add(new SearchData(random));
        }
    }

    private void showSearchData() {
        ListView listView = (ListView)findViewById(R.id.list_view);
        SearchAdapter adapter = new SearchAdapter(SearchActivity.this);
        adapter.addAll(mSearchDataList);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //we might need the data later
        StateCache.push(mSearchDataList);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //done with the search data, clear it
        StateCache.pop(mSearchDataList.getClass());
    }
}
