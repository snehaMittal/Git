package com.javahelps.git;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Sneha on 03-10-2017.
 */

public class RepoAsyncTask extends AsyncTask<String , Void , ArrayList<String>> {

    RepoDownloadListener repoDownloadListener ;

    public RepoAsyncTask(RepoDownloadListener repoDownloadListener) {
        this.repoDownloadListener = repoDownloadListener;
    }

    @Override
    protected ArrayList<String> doInBackground(String... params) {
        ArrayList<String> detail = null;
        String urlString = params[0];
        try {
            URL url = new URL(urlString);   //convert string to url
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();  //to form the connection
            urlConnection.setRequestMethod("GET");// we wont data from server that is why "get"
            Log.i("Course response", "Connection started: ");
            urlConnection.connect();         //cant be called on main thread so we have to use asyncTask() for multithreading
            Log.i("Courses Response:","Connection Complete");

            InputStream inputStream = urlConnection.getInputStream();      //api gives the input stream
            Scanner scanner = new Scanner(inputStream);
            String response = "" ;
            while (scanner.hasNext()){
                response += scanner.next();
            }
            Log.i("Courses Response:",response);
             detail = parseDetails(response);
            return detail ;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return detail;
    }

    ArrayList<String> parseDetails(String response) throws JSONException{
        ArrayList<String> info = new ArrayList<>();

        JSONArray item = new JSONArray(response);
        if (item != null){
            for (int i=0 ; i<item.length() ; i++){
                JSONObject repos = item.getJSONObject(i);
                String name = repos.getString("name");
                info.add(name);
            }
        }
        return info ;
    }

    @Override
    protected void onPostExecute(ArrayList<String> list) {
        repoDownloadListener.onDownloadRepo(list);
    }

    public interface RepoDownloadListener{
        void onDownloadRepo(ArrayList<String> courses);
    }
}
