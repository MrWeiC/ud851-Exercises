/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.recyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NumberAdapter.NumberItemListClickListener{
    private String TAG = "Test";

    private static final int NUM_LIST_ITEMS = 100;

    private static final int SELECTION_MODE_ENABLE = 0;
    private static final int SELECTION_MODE_DISABLE = 1;

    private NumberAdapter mAdapter;
    private RecyclerView mNumbersList;
    private List<NumberItem> mList  = new ArrayList<>();

    private int mMode = SELECTION_MODE_DISABLE;
    private boolean mSelectionStatus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mNumbersList = (RecyclerView)findViewById(R.id.rv_numbers);
        mNumbersList.setLayoutManager(layoutManager);
        mNumbersList.setHasFixedSize(true);
        mAdapter = new NumberAdapter(this,this);
        mNumbersList.setAdapter(mAdapter);
        //Init Data
        for (int i = 0; i < NUM_LIST_ITEMS; i++) {
            NumberItem numberItem = new NumberItem(i);
            mList.add(numberItem);
            mAdapter.notifyAdapter(mList, false);
        }
        Log.i(TAG, "onCreate: ");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_select:
                updateSelectionMode();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void updateSelectionMode(){
        mMode = mMode == SELECTION_MODE_ENABLE ? SELECTION_MODE_DISABLE : SELECTION_MODE_ENABLE;
        if(mMode == SELECTION_MODE_ENABLE){
            mSelectionStatus = true;
        }else{
            mSelectionStatus = false;

        }
        mAdapter.setEditMode(mMode);
    }



    @Override
    public void onListItemClick(int pos, List<NumberItem> numberItemList) {
        if(mSelectionStatus){
        NumberItem mNumberItem = numberItemList.get(pos);
        boolean isSelect = mNumberItem.isSelected();
            if (!isSelect) {
                mNumberItem.setSelected(true);
            } else {
                mNumberItem.setSelected(false);
            }
        }
        mAdapter.notifyDataSetChanged();
    }
}
