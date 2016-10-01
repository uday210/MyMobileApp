package com.example.mr2.onlineloader;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ProductPurchase extends AppCompatActivity {

    
    private static final String TAG = "uday";
    String myjson;
    ProgressDialog pDialog;
    public CreateOrder myorder;
    ArrayList<CreateOrder> allorders = new ArrayList<>();

    String itemname, itemprice, itemid;
    TextView prname, prprice;
    EditText qty;
    String qt;
    Button Ordernow;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_purchase);

        Intent intent = getIntent();
        itemname = intent.getStringExtra("Product_Name");
        itemprice = intent.getStringExtra("Product_Price");
        itemid = intent.getStringExtra("product_id");
        prname = (TextView) findViewById(R.id.tvProname);
        prprice = (TextView) findViewById(R.id.tvProPrice);
        qty = (EditText) findViewById(R.id.numQty);
        prname.setText(itemname);
        prprice.setText(itemprice);


        Ordernow = (Button) findViewById(R.id.btOrder);


        Ordernow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qt = qty.getText().toString();
                //*Integer.parseInt(itemprice)
                //Toast.makeText(getApplicationContext(),"final price is "+Integer.parseInt(itemprice), Toast.LENGTH_SHORT).show();

                Log.i(TAG, qt);
                Log.i(TAG, itemprice);
                Log.i(TAG, itemid);


                //      Toast.makeText(getApplicationContext(),"final Qty  is "+qt, Toast.LENGTH_SHORT).show();
                //      Toast.makeText(getApplicationContext(),"final price is "+Float.parseFloat(itemprice)*Integer.parseInt(qt), Toast.LENGTH_SHORT).show();

                myorder = new CreateOrder(Float.parseFloat(itemprice) * Integer.parseInt(qt), Integer.parseInt(qt), itemid);
                allorders.add(myorder);
                allorders.add(myorder);
                Gson gson = new Gson();
                myjson = gson.toJson(allorders);

                Log.i(TAG, "VVVVVVVVVVV");
                Log.i(TAG,myjson );

                ProductPurchasenew aa = new ProductPurchasenew();
                aa.execute();
            }


        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ProductPurchase Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.mr2.onlineloader/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ProductPurchase Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.mr2.onlineloader/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


    public class ProductPurchasenew extends AsyncTask<String, Void, String> {

        String SaveOrder_load = "https://uday210new-developer-edition.ap2.force.com/services/apexrest/SaveOrder";
        public String lines = "";
        String type;

        @Override
        protected String doInBackground(String... params) {

            // String od = params[0];
            JSONObject jsonObj = new JSONObject();


            try {
                // adialog.setTitle("in try");
                Log.i(TAG, "In Detail try 123 4");
                //  Log.i(TAG,od);

                // adialog.setTitle("in try");
               jsonObj.put("orderid", itemid);
               // jsonObj.put("order_price", Float.parseFloat(itemprice) * Integer.parseInt(qt));
               // jsonObj.put("order_qty", qt);

                URL url = new URL(SaveOrder_load);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestProperty("Content-Type", "application/json");
                httpURLConnection.setRequestProperty("Accept", "application/json");
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream ostream = httpURLConnection.getOutputStream();
                OutputStreamWriter outputswriter = new OutputStreamWriter(ostream, "UTF-8");
               // outputswriter.write(jsonObj.toString());
                outputswriter.write(myjson);
                BufferedWriter bwriter = new BufferedWriter(outputswriter);
                bwriter.flush();
                bwriter.close();
                ostream.close();

                InputStream istream = httpURLConnection.getInputStream();
                BufferedReader breader = new BufferedReader(new InputStreamReader(istream, "iso-8859-1"));

                StringBuffer buffer = new StringBuffer();
                String line = "";


                while ((line = breader.readLine()) != null) {

                    buffer.append(line);
                    lines += line;
                }


                breader.close();
                istream.close();
                httpURLConnection.disconnect();

                return null;

            } catch (MalformedURLException e) {

                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();


            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            pDialog.dismiss();

            Intent in = new Intent(ProductPurchase.this, LeadDetails.class);
            //in.putExtra("Lead_id", myleadstrack.get(selecteditem));

            startActivity(in);


        }

        @Override
        protected  void onPreExecute(){

            super.onPreExecute();
            pDialog = new ProgressDialog(ProductPurchase.this);
            pDialog.setMessage("Placing Order Please Wait....");
            pDialog.show();

        }
    }
}
