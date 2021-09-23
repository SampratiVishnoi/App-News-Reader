package com.example.newsreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {




    ListView mylistview;  ArrayList<String> newsTile; ArrayAdapter<String> arrayAdapter;String[] articleCon=new String[20];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mylistview =(ListView) findViewById(R.id.listView);

        newsTile = new ArrayList<String>();
        SharedPreferences sharedPreferences= this.getSharedPreferences("com.example.newsreader", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("list", String.valueOf(newsTile)).apply();

                DownloadTask task = new DownloadTask();
         arrayAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,newsTile );
        task.execute("https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty");
        Log.i("one","1");
    }



    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            Log.i("two","2");
            URL url;
            HttpURLConnection urlConnection = null;

            try {

                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                Log.i("three","3");

                int data = reader.read();
                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();

                }

                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);

            try {
                JSONArray arr = new JSONArray(result);
Log.i("result",result);
                for (int i = 0; i < 20; i++) {
                    String listId = arr.getString(i);

                    DownloadTask task = new DownloadTask();

                    String ress=task.execute("https://hacker-news.firebaseio.com/v0/item/"+listId+".json?print=pretty").get();
                    JSONObject jsonObject =new JSONObject(ress);

                    String webInfo= jsonObject.getString("title");
                  articleCon[i]=jsonObject.getString("url");
                    newsTile.add(webInfo);



                    mylistview.setAdapter(arrayAdapter);







                }





            }catch (JSONException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            mylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Log.i("open","7");
                    Intent intent=new Intent(MainActivity.this,ArticleActivity2.class);

                    intent.putExtra("url",articleCon[position]);
                    Log.i("poss",Integer.toString(position));
                    startActivity(intent);
                }
            });


        }



        }
    }
