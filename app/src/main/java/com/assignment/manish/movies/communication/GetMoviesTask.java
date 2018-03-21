package com.assignment.manish.movies.communication;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetMoviesTask extends AsyncTask<String,Void,String>
{
    private final String identifier;
    private IObserver observer;
    private Context context;

    public GetMoviesTask(IObserver observer, Context context, String identifier) {
        this.observer = observer;
        this.context = context;
        this.identifier = identifier;
    }

    @Override
    protected String doInBackground(String... strings)
    {
        String responseData = "";
        URL obj = null;
        try {
            obj = new URL(strings[0]);

        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        responseData = response.toString();
        } catch (MalformedURLException e) {
            Log.e("ERROR",e.getMessage());
        }
        catch (IOException e)
        {
            Log.e("ERROR",e.getMessage());
        }

        return responseData;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (observer!=null) {
            observer.onObserverCallBack(identifier,s);
        }
    }
}
