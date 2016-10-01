package com.example.mr2.onlineloader;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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
import java.net.URLEncoder;

/**
 * Created by MR2 on 6/1/2016.
 */
public class backroundWorker extends AsyncTask<String,Void,String> {

    Context context;
    AlertDialog adialog;
    String type;
    String loggeduserid;
    private static final String TAG = "uday";

    backroundWorker(Context ctx){
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
       type = params[0];

        String loin_url = "http://semicolonend.com/login.php";
        String register_url = "http://semicolonend.com/register.php";
        String lead_insert = "http://semicolonend.com/leadinsert.php";
        String leads_load = "http://semicolonend.com/load_leads.php";
        if(type.equals("Login")){
            loggeduserid = params[1];
            String uname = params[1];
            String pass = params[2];

            try {
               // adialog.setTitle("in try");
                URL url = new URL(loin_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream ostream = httpURLConnection.getOutputStream();
                BufferedWriter bwriter = new BufferedWriter(new OutputStreamWriter(ostream,"UTF-8"));
                String post_data = URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(uname,"UTF-8")+"&"
                        +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(pass,"UTF-8");

                bwriter.write(post_data);
                bwriter.flush();
                bwriter.close();
                ostream.close();

                InputStream istream = httpURLConnection.getInputStream();
                BufferedReader breader = new BufferedReader(new InputStreamReader(istream,"iso-8859-1"));

                String result="";
                String line="";

                while ((line = breader.readLine() )!= null){
                    result+=line;
                }
                breader.close();
                istream.close();
                httpURLConnection.disconnect();

                return result;

            } catch (MalformedURLException e) {

                e.printStackTrace();
                adialog.setTitle("in first catch");
            } catch (IOException e) {
                e.printStackTrace();

                adialog.setTitle("in second catch");
            }


        }



        //reg logic

       else  if(type.equals("register")){

            String name = params[1];
            String sname = params[2];
            String age = params[3];
            String u_name = params[4];
            String pass = params[5];

            try {
                // adialog.setTitle("in try");
                URL url = new URL(register_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream ostream = httpURLConnection.getOutputStream();
                BufferedWriter bwriter = new BufferedWriter(new OutputStreamWriter(ostream,"UTF-8"));
                String post_data = URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"
                        +URLEncoder.encode("surname","UTF-8")+"="+URLEncoder.encode(sname,"UTF-8")+"&"
                        +URLEncoder.encode("age","UTF-8")+"="+URLEncoder.encode(age,"UTF-8")+"&"
                        +URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(u_name,"UTF-8")+"&"
                        +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(pass,"UTF-8");

                bwriter.write(post_data);
                bwriter.flush();
                bwriter.close();
                ostream.close();

                InputStream istream = httpURLConnection.getInputStream();
                BufferedReader breader = new BufferedReader(new InputStreamReader(istream,"iso-8859-1"));

                String result="";
                String line="";

                while ((line = breader.readLine() )!= null){
                    result+=line;
                }
                breader.close();
                istream.close();
                httpURLConnection.disconnect();

                return result;

            } catch (MalformedURLException e) {

                e.printStackTrace();
                adialog.setTitle("in first catch");
            } catch (IOException e) {
                e.printStackTrace();

                adialog.setTitle("in second catch");
            }


        }

        //Lead Insert logic

        else  if(type.equals("scan")){


            String scanid = params[1];
            String userid = params[2];
            Log.i(TAG,userid);
            try {
                // adialog.setTitle("in try "+userid);
                URL url = new URL(lead_insert);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream ostream = httpURLConnection.getOutputStream();
                BufferedWriter bwriter = new BufferedWriter(new OutputStreamWriter(ostream,"UTF-8"));
                String post_data = URLEncoder.encode("user_id","UTF-8")+"="+URLEncoder.encode(userid,"UTF-8")+"&"
                        +URLEncoder.encode("scanid","UTF-8")+"="+URLEncoder.encode(scanid,"UTF-8");

                bwriter.write(post_data);
                bwriter.flush();
                bwriter.close();
                ostream.close();

                InputStream istream = httpURLConnection.getInputStream();
                BufferedReader breader = new BufferedReader(new InputStreamReader(istream,"iso-8859-1"));

                String result="";
                String line="";

                while ((line = breader.readLine() )!= null){
                    result+=line;
                }
                breader.close();
                istream.close();
                httpURLConnection.disconnect();

                return result;

            } catch (MalformedURLException e) {

                e.printStackTrace();
                adialog.setTitle("in first catch");
            } catch (IOException e) {
                e.printStackTrace();

                adialog.setTitle("in second catch");
            }






        }

        //Lead all leads  logic

        else  if(type.equals("load_lead")) {


            String userid_scan = params[1];

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

                String result = "";
                String line = "";

                while ((line = breader.readLine()) != null) {
                    result += line;
                }
                breader.close();
                istream.close();
                httpURLConnection.disconnect();

                return result;

            } catch (MalformedURLException e) {

                e.printStackTrace();
                adialog.setTitle("in first catch");
            } catch (IOException e) {
                e.printStackTrace();

                adialog.setTitle("in second catch");
            }
        }

        return null;
    }

    @Override
    protected void onPreExecute(){
        adialog = new AlertDialog.Builder(context).create();

    }

    @Override
    protected void onPostExecute(String result){

        if(type.equals("Login"))
        {
            adialog.setMessage(result);
            adialog.show();

          //  adialog.setTitle("Login Status");
        }else if(type.equals("register")){
            adialog.setMessage(result);
            adialog.show();
            //adialog.setTitle("Sign Up  Status");
        }else  if(type.equals("scan")){
           // adialog.setTitle("Lead Insertion Status");

            Toast.makeText(context,"Lead Insertion Status "+result,Toast.LENGTH_SHORT).show();
        }


        if( result.contains("Welcome") && type.equals("Login")) {
           // Intent in = new Intent(context, loggedUser.class);
           // in.putExtra("user_name", result);
           // context.startActivity(in);

            Intent in = new Intent(context, QrcodeReader.class);
            in.putExtra("user_id", loggeduserid);
            context.startActivity(in);

        }


    }

    @Override
    protected void onProgressUpdate(Void... values){
        super.onProgressUpdate(values);
    }
}

