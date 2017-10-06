package com.javahelps.git;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class Repositories extends AppCompatActivity implements RepoAsyncTask.RepoDownloadListener{

    String url ;
    ArrayList<String> list = new ArrayList<>() ;
    static ListView listview ;
    Repo_Adapter adapter ;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repositories);

        listview = (ListView)findViewById(R.id.listview);
        progressBar = (ProgressBar)findViewById(R.id.repo_progress);
        adapter = new Repo_Adapter(this , list );
        listview.setAdapter(adapter);

        Intent intent = getIntent();
        url = intent.getStringExtra(Constants.REPO_URL);
        RepoAsyncTask asyncTask = new RepoAsyncTask(this);//create an object of async task for multithreading

        progressBar.setVisibility(View.VISIBLE);
        listview.setVisibility(View.GONE);
        asyncTask.execute(url);
    }

    @Override
    public void onDownloadRepo(ArrayList<String> courses) {
        list.clear();
        for (String s :courses){
            list.add(s);
        }
        progressBar.setVisibility(View.GONE);
        listview.setVisibility(View.VISIBLE);
        adapter.notifyDataSetChanged();
    }
}
