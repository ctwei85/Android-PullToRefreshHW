package com.example.lianchuang_hw.pulltorefreshdemo;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.Arrays;
import java.util.LinkedList;

public class MainActivity extends Activity {

    private String[] mStrings = {"Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
            "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
            "Allgauer Emmentaler", "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
            "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
            "Allgauer Emmentaler"};
    private LinkedList<String>mListItems;
    private PullToRefreshListView mPullRefreshListView;
    private ArrayAdapter<String>mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPullRefreshListView =(PullToRefreshListView)findViewById(R.id.pull_refresh_list);
        mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getApplicationContext(),System.currentTimeMillis(),DateUtils.FORMAT_SHOW_TIME|DateUtils.FORMAT_SHOW_DATE|DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                new GetDataTask().execute();
            }
        });

        mListItems = new LinkedList<String>();
        mListItems.addAll(Arrays.asList(mStrings));
        mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mListItems);

        //方法一
        //mPullRefreshListView.setAdapter(mAdapter);
        //方法二
        ListView actualListView = mPullRefreshListView.getRefreshableView();
        actualListView.setAdapter(mAdapter);
    }

    private class GetDataTask extends AsyncTask<Void,Void,String>{
        //后台处理部分
        @Override
        protected String doInBackground(Void... params){
            try{
                Thread.sleep(1000);
            }catch (InterruptedException e){

            }
            String str = "Added after refresh... I add";
            return str;
        }

        @Override
        protected void onPostExecute(String result){
            mListItems.addFirst(result);

            mAdapter.notifyDataSetChanged();
            mPullRefreshListView.onRefreshComplete();
            super.onPostExecute(result);
        }
    }


}
