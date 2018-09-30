package com.example.ertugrul.dovizim;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    TextView birim, usd, eur, gbp;
    SharePref sharePref;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        birim = findViewById(R.id.textView3);
        usd = findViewById(R.id.usdText);
        eur = findViewById(R.id.eurText);
        gbp = findViewById(R.id.gbpText);

        sharePref = new SharePref();
        birim.setText("Para Biriminiz: " + sharePref.getValue(context));
    }

    private class DownloadData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            URL url;
            HttpURLConnection httpURLConnection;

            try {
                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                int data = inputStreamReader.read();
                while (data > 0) {
                    char character = (char) data;
                    result += character;
                    data = inputStreamReader.read();
                }
                return result;

            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                String rates = jsonObject.getString("rates");
                JSONObject jsonObject1 = new JSONObject(rates);

                String USD = jsonObject1.getString("USD");
                String EUR = jsonObject1.getString("EUR");
                String GBP = jsonObject1.getString("GBP");
                String Birim = jsonObject1.getString(sharePref.getValue(context));

                double USDsonuc = Double.parseDouble(Birim)/Double.parseDouble(USD);
                double EURsonuc = Double.parseDouble(Birim)/Double.parseDouble(EUR);
                double GBPsonuc = Double.parseDouble(Birim)/Double.parseDouble(GBP);


                usd.setText(String.valueOf(USDsonuc));
                eur.setText(String.valueOf(EURsonuc));
                gbp.setText(String.valueOf(GBPsonuc));


            } catch (Exception e) {

            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.setting) {
            Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.setting, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStart() {
        if (sharePref.getValue(context) != null){

        }else{
            Toast.makeText(this, "Lütfen para biriminizi seçin", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
            startActivity(intent);
        }

        DownloadData downloadData = new DownloadData();
        try {
            String url = "http://data.fixer.io/api/latest?access_key=849509b1ec6ab80fd410313d8dc558ed&format=1";
            downloadData.execute(url);
        } catch (Exception e) {

        }

        super.onStart();
    }

    public void ceviri(View view) {
        Intent intent = new Intent(getApplicationContext(), Ceviri.class);
        startActivity(intent);
    }

    public void doviz(View view) {
        Intent intent = new Intent(getApplicationContext(), Doviz.class);
        startActivity(intent);
    }


}