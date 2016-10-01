package com.example.mr2.onlineloader;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class Loadleads extends Activity {
    public   String  luser;
    ArrayList<Attendees> myleads= new ArrayList<Attendees>();
    ArrayList<String> myleadstrack = new ArrayList<String>();
    private static final String TAG = "uday";
    private String file = "myLeaddata";
    private ListView lvview;
    Button exportbtn,loadmorebtn;
    FileWriter writer;
    File root = Environment.getExternalStorageDirectory();
    File gpxfile = new File(root, "mydata.csv");

    Uri URI = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadleads);

        Intent intent = getIntent();

       luser = intent.getStringExtra("user_id");
        lvview = (ListView)findViewById(R.id.lvLeads);


        Log.i(TAG,"main  ");
        Log.i(TAG,luser);

        String type="load_lead";
        loadleads  bw = new loadleads();
       bw.execute(luser,"load");

        exportbtn = (Button)findViewById(R.id.expo);
        loadmorebtn = (Button)findViewById(R.id.load);


        loadmorebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    Toast.makeText(Loadleads.this, "Loading More Leads..", Toast.LENGTH_SHORT).show();

                loadleads  bw = new loadleads();
                bw.execute(luser,"loadmore");



            }


        });


        exportbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                try {
                    writer = new FileWriter(gpxfile);
                    writeCsvHeader("Scan Ids","track fields");

                    for(int i=0;i<myleads.size();i++) {
                     //   writeCsvData(myleads.get(i),myleadstrack.get(i));

                    }


                    writer.flush();
                    writer.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }





                BufferedReader input = null;

                String pathname= Environment.getExternalStorageDirectory().getAbsolutePath();
                String filename="/mydata.csv";
                File file=new File(pathname, filename);



                int leadscount =  myleads.size();
                    String emailmsg = "there are total of : " + leadscount;
                    Log.i("Send email", "");
                    String[] TO = {"appattachment@1qmfdl1tn2vq7w4ue30gdtmw22venej0kt0uzehdqhzsb7dyw9.28-18e2ueaq.ap2.apex.salesforce.com","udaykiran.uday@gmail.com"};
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);

                    //   emailIntent.setData(Uri.parse("mailto:"));
                    emailIntent.setType("text/plain");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                //    emailIntent.putExtra(Intent.EXTRA_CC, CC);
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Expost Leads ");
                    emailIntent.putExtra(Intent.EXTRA_TEXT,emailmsg );
                   emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));


                try {
                        //startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                    startActivity(emailIntent);
                        finish();
                        //    Log.i("Finished sending email...", "");
                    }
                    catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(Loadleads.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                    }
                }


        });

    }

    private void writeCsvHeader(String h1,String h2) throws IOException {
        String line = String.format("%s,%s\n", h1,h2);
        writer.write(line);
    }


    private void writeCsvData(String d,String e) throws IOException {
        String line = String.format("%s,%s\n", d,e);
        writer.write(line);
    }





    public class loadleads extends AsyncTask<String,Void,String>{

        String leads_load = "https://uday210new-developer-edition.ap2.force.com/services/apexrest/AttendeeData";
        String leads_load_more =  "https://uday210new-developer-edition.ap2.force.com/services/apexrest/getAccDetailsMore";
public String lines = "";
        String type;
        @Override
        protected String doInBackground(String... params) {


            String userid_scan = params[0];
            type= params[1];

            if(type.equals("load")) {

                try {
                    // adialog.setTitle("in try");
                    URL url = new URL(leads_load);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    OutputStream ostream = httpURLConnection.getOutputStream();
                    BufferedWriter bwriter = new BufferedWriter(new OutputStreamWriter(ostream, "UTF-8"));
                    String post_data = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(userid_scan, "UTF-8");

                    bwriter.write(post_data);
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
                    }

                    String finalJson = buffer.toString();

                    JSONArray ParentJsonArray = new JSONArray(finalJson);
                    int a = ParentJsonArray.length();
                    Log.i(TAG, String.valueOf(a));
                    for (int i = 0; i < a; i++) {
                        JSONObject eachobj = ParentJsonArray.getJSONObject(i);
                        String scanideacg = eachobj.getString("Name");
                        String trackfields = eachobj.getString("DevicceId__c");
                       // JSONObject accdetails  =  new JSONObject(scanideacg);
                       // String indacc = accdetails.getString("name");

                        Log.i(TAG, scanideacg);
                        myleads.add(new Attendees(scanideacg,trackfields));
                        myleadstrack.add(trackfields);
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
            }else if (type.equals("loadmore")){

                String lastleadis = myleadstrack.get(myleads.size()-1);
                Log.i(TAG,lastleadis);
                JSONObject jsonObj = new JSONObject();

                try {
                    Log.i(TAG,"In Detail try");
                    // adialog.setTitle("in try");
                    jsonObj.put("accname", lastleadis);
                    Log.i(TAG,jsonObj.toString());
                    URL url = new URL(leads_load_more);
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
                    //String post_data = URLEncoder.encode("last_id", "UTF-8") + "=" + URLEncoder.encode(lastleadis, "UTF-8");

                    //bwriter.write(post_data);
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
                    }

                    String finalJson = buffer.toString();

                    JSONArray ParentJsonArray = new JSONArray(finalJson);
                    int a = ParentJsonArray.length();
                    Log.i(TAG, String.valueOf(a));
                    for (int i = 0; i < a; i++) {
                        JSONObject eachobj = ParentJsonArray.getJSONObject(i);
                        String scanideacg = eachobj.getString("name");
                        String trackfields = eachobj.getString("DevicceId__c");
                        // JSONObject accdetails  =  new JSONObject(scanideacg);
                        // String indacc = accdetails.getString("name");

                        Log.i(TAG, scanideacg);
                        myleads.add(new Attendees(scanideacg,trackfields));
                        myleadstrack.add(trackfields);
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

            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
           super.onPostExecute(result);
            Log.i(TAG,"rsult is");
            Log.i(TAG,lines);

            ArrayAdapter adapter  = new ArrayAdapter(Loadleads.this,android.R.layout.simple_list_item_1,myleads);
            lvview.setAdapter(adapter);
            TextView tvLeadCount  = (TextView)findViewById(R.id.leadcount);
            tvLeadCount.setText("Total Leads "+myleads.size());
            lvview.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    final int selecteditem = position;
                    AlertDialog alertDialog1 = new AlertDialog.Builder(Loadleads.this).create();


                }
            });





        }



    }


}

