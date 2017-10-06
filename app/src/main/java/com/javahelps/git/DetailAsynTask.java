package com.javahelps.git;

import android.os.AsyncTask;
import android.speech.tts.Voice;
import android.util.Log;

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
 * Created by Sneha on 02-10-2017.
 */

public class DetailAsynTask extends AsyncTask<String , Void , ArrayList<Details>> {

    DetailsDownloadListener detailsDownloadListener ;

    public DetailAsynTask(DetailsDownloadListener detailsDownloadListener) {
        this.detailsDownloadListener = detailsDownloadListener;
    }

    @Override
    protected ArrayList<Details> doInBackground(String... params) {
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
            ArrayList<Details> detail = parseDetails(response);
            return detail ;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Details> parseDetails(String response) throws JSONException{
        ArrayList<Details> info = new ArrayList<>() ;

        JSONObject items = new JSONObject(response) ;
        String image_url = items.getString("avatar_url");
        String login = items.getString("login");
        String html_url = items.getString("html_url") ;
        Integer followers_url = items.getInt("followers") ;
        Integer following_url = items.getInt("following") ;
        String repos_url= items.getString("repos_url") ;

        Details details = new Details(image_url , login , html_url , followers_url , following_url , repos_url);
        info.add(details);
        return info ;
    }

    @Override
    protected void onPostExecute(ArrayList<Details> detailses) {
        detailsDownloadListener.onDownload(detailses);
    }

    public interface DetailsDownloadListener{
        void onDownload(ArrayList<Details> courses);
    }
}
