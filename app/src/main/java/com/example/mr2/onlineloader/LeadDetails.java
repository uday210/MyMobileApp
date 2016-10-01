package com.example.mr2.onlineloader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
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

public class LeadDetails extends Activity {
    ArrayList<productandprice> myproducts= new ArrayList<productandprice>();
    ArrayList<String> myproductnames= new ArrayList<String>();
    private static final String TAG = "uday";
    private ListView lproductslview;

    ImageView img;
    Bitmap bitmap;
    ProgressDialog pDialog;
    String imageurl ="https://uday210new-developer-edition.ap2.force.com/servlet/servlet.ImageServer?id=";
    String urlend = "&oid=00D280000018e2U";
    String imageid;
String scanid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead_details);
        img = (ImageView)findViewById(R.id.leadimage);

        lproductslview = (ListView)findViewById(R.id.lvProducts);
        Intent intent = getIntent();
        scanid = intent.getStringExtra("Lead_id");

        //

         LeadDetailsview  bw = new LeadDetailsview();
         bw.execute(scanid);

    }



    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LeadDetails.this);
            pDialog.setMessage("Loading Lead Details ....");
            pDialog.show();

        }
        protected Bitmap doInBackground(String... args) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream)new URL(args[0]).getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {

            if(image != null){
                img.setImageBitmap(image);
                pDialog.dismiss();

            }else{

                pDialog.dismiss();
                Toast.makeText(LeadDetails.this, "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();

            }
        }

    }



    public class LeadDetailsview  extends AsyncTask<String,Void,String> {

        String leads_load = "https://uday210new-developer-edition.ap2.force.com/services/apexrest/getAccDetails";
        public String lines = "";
        @Override
        protected String doInBackground(String... params) {


            String passedleadis = params[0];
            JSONObject jsonObj = new JSONObject();

            try {
                Log.i(TAG,"In Detail try");
                // adialog.setTitle("in try");
                jsonObj.put("accname", passedleadis);
                URL url = new URL(leads_load);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestProperty("Content-Type", "application/json");
                httpURLConnection.setRequestProperty("Accept", "application/json");
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream ostream = httpURLConnection.getOutputStream();
                OutputStreamWriter outputswriter = new OutputStreamWriter(ostream, "UTF-8");
                outputswriter.write(jsonObj.toString());
                BufferedWriter bwriter = new BufferedWriter(outputswriter);
                //String post_data = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode("aa", "UTF-8");
               // String post_data  = url;
               // bwriter.write(post_data);
                bwriter.flush();
                bwriter.close();
                ostream.close();

                InputStream istream = httpURLConnection.getInputStream();
                BufferedReader breader = new BufferedReader(new InputStreamReader(istream, "iso-8859-1"));

                StringBuffer buffer = new StringBuffer();
                String line = "";
                String line1 = "";

                while ((line = breader.readLine()) != null) {

                    buffer.append(line);
                    lines += line;
                    Log.i(TAG,line);
                }

                String finalJson = buffer.toString();

                JSONArray ParentJsonArray = new JSONArray(finalJson);
                int a  =ParentJsonArray.length();
                for (int i=0;i<a;i++)
                {
                    JSONObject eachobj = ParentJsonArray.getJSONObject(i);
                    String name = eachobj.getString("Name");
                    String logo = eachobj.getString("logo__c");
                    JSONObject products = eachobj.getJSONObject("Products__r");
                    JSONArray Allproduct = products.getJSONArray("records");

                    for(int k=0;k<Allproduct.length();k++)
                    {
                        JSONObject eachpro = Allproduct.getJSONObject(k);
                        String proid = eachpro.getString("Name");
                        String proname = eachpro.getString("Name__c");
                        String proprice = eachpro.getString("Price__c");
                        productandprice pr = new productandprice(proid,proname,proprice);
                        myproducts.add(pr);
                        myproductnames.add(proname);

                    }

                    imageid = logo;
                    Log.i(TAG,name);
                    Log.i(TAG,"XXXXXX");
                    Log.i(TAG,myproducts.toString());
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
            String imageurl1 = imageurl+imageid+urlend;
            new LoadImage().execute(imageurl1);

            ArrayAdapter adapter  = new ArrayAdapter(LeadDetails.this,android.R.layout.simple_list_item_1,myproductnames);
            lproductslview.setAdapter(adapter);
            lproductslview.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                 //   Toast.makeText(getApplicationContext(),myproductnames.get(position)+" postiotn is "+position,Toast.LENGTH_SHORT).show();
                   productandprice selproduct = myproducts.get(position);


                    Toast.makeText(getApplicationContext(),selproduct.name+" product price  is "+selproduct.Price,Toast.LENGTH_SHORT).show();

                    Intent in = new Intent(LeadDetails.this, ProductPurchase.class);
                    in.putExtra("Product_Name", selproduct.name);
                    in.putExtra("Product_Price", selproduct.Price);
                    in.putExtra("product_id",selproduct.Proid);

                    startActivity(in);

                }
            });






        }



    }


}