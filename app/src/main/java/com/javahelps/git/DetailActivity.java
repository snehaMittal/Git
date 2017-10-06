package com.javahelps.git;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class DetailActivity extends AppCompatActivity implements DetailAsynTask.DetailsDownloadListener,NumberAsyncTask.NumberListener {

    String browse , repository_url ;
    ProgressBar progressBar;
    ImageView imageView;
    TextView name, repo, reponum, followers, followersnum, following, followingnum;
    Button browswer, repobutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        imageView = (ImageView) findViewById(R.id.imageView);
        repo = (TextView) findViewById(R.id.repos);
        reponum = (TextView) findViewById(R.id.reponum);
        followers = (TextView) findViewById(R.id.followers);
        followersnum = (TextView) findViewById(R.id.followersnum);
        following = (TextView) findViewById(R.id.following);
        followingnum = (TextView) findViewById(R.id.followingnum);
        name = (TextView) findViewById(R.id.textView4);
        browswer = (Button) findViewById(R.id.button4);
        repobutton = (Button) findViewById(R.id.button3);

        Intent intent = getIntent();
        String username = intent.getStringExtra(Constants.USERNAME);
        String url = "https://api.github.com/users/";
        String final_url = url + username;
        DetailAsynTask detailAsynTask = new DetailAsynTask(this);
        progressBar.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.GONE);
        reponum.setVisibility(View.GONE);
        repo.setVisibility(View.GONE);
        following.setVisibility(View.GONE);
        followersnum.setVisibility(View.GONE);
        followingnum.setVisibility(View.GONE);
        followers.setVisibility(View.GONE);
        browswer.setVisibility(View.GONE);
        name.setVisibility(View.GONE);
        repobutton.setVisibility(View.GONE);

        detailAsynTask.execute(final_url);

        browswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(browse); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        repobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this , Repositories.class);
                intent.putExtra(Constants.REPO_URL , repository_url);
                startActivity(intent);
            }
        });
    }

    Integer number;

    @Override
    public void onDownload(ArrayList<Details> courses) {

        for (Details item : courses) {
            Picasso.with(this).load(item.image_url).into(imageView);
            name.setText(item.login);
            browse = item.html_url;
            repository_url = item.repos_url ;
            followersnum.setText(item.followers_url.toString());
            followingnum.setText(item.following_url.toString());
            NumberAsyncTask num = new NumberAsyncTask(this);
            num.execute(item.repos_url);


        }
        progressBar.setVisibility(View.GONE);
        imageView.setVisibility(View.VISIBLE);
        reponum.setVisibility(View.VISIBLE);
        repo.setVisibility(View.VISIBLE);
        following.setVisibility(View.VISIBLE);
        followersnum.setVisibility(View.VISIBLE);
        followingnum.setVisibility(View.VISIBLE);
        followers.setVisibility(View.VISIBLE);
        browswer.setVisibility(View.VISIBLE);
        name.setVisibility(View.VISIBLE);
        repobutton.setVisibility(View.VISIBLE);
    }


    @Override
    public void onNumberReceiver(ArrayList<Integer> answer) {
        reponum.setText("" + answer.get(0).toString());
    }

}
