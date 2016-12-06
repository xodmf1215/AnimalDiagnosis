package com.example.teuljung.practice2.testing;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;

import org.json.JSONObject;
import org.json.JSONException;
import org.apache.http.params.*;

import java.lang.Object.*;
import java.net.HttpURLConnection;

import com.example.teuljung.practice2.*;
import com.example.teuljung.practice2.drawer.MyNavigationDrawer;

public class WebServiceDist extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //public class MainActivity extends AppCompatActivity implements MyNavigationDrawer {
    EditText eTxt_Sx,eTxt_Sy,eTxt_Sz,eTxt_Tx,eTxt_Ty,eTxt_Tz,eTxt_Px,eTxt_Py,eTxt_Pz;
    TextView txt1,txt2,txt3;
    Button btn1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_webservice_dist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setLayout();

        btn1.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                HttpTask task = new HttpTask();
                task.execute();
            }
        });
    }

    private class HttpTask extends AsyncTask<Void, String, Integer> { //순서
        JSONObject responseJSON = null;
        JSONObject jobj = new JSONObject();
        protected void onPreExecute() { //background 실행전 준비
            super.onPreExecute();
            if (!isOnline()) {
                txt3.setText("인터넷 연결이 안됨");
                return;
            }
            try {
                jobj.put("S_x", eTxt_Sx.getText());
                jobj.put("S_y", eTxt_Sy.getText());
                jobj.put("S_z", eTxt_Sz.getText());

                jobj.put("T_x", eTxt_Tx.getText());
                jobj.put("T_y", eTxt_Ty.getText());
                jobj.put("T_z", eTxt_Tz.getText());

                jobj.put("P_x", eTxt_Px.getText());
                jobj.put("P_y", eTxt_Py.getText());
                jobj.put("P_z", eTxt_Pz.getText());
            }catch(Exception e) {

            }
        }
        protected Integer doInBackground(Void ... params) { //background에서 작업할 내용
            try {
                String url = "http://106.242.109.54:3000"; //http 안써주면 URL(url)생성에서 exception 발생 listening중이 아니면 con.getOutputStream이 오래걸림
                //String url = "http://www.google.com";
                OutputStream os = null;
                InputStream is = null;
                ByteArrayOutputStream baos = null;
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Cache-Control", "no-cache");
                con.setRequestProperty("Content-Type", "application/json");// 타입설정(application/json) 형식으로 전송 (Request Body 전달시 application/json로 서버에 전달.)
                con.setRequestProperty("Accept", "application/json; charset=UTF-8");// 서버 Response Data를 JSON 형식의 타입으로 요청.
                con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.75 Safari/537.36");
                con.setDoOutput(true); // OutputStream으로 POST 데이터를 넘겨주겠다는 옵션.
                con.setDoInput(true);
                //publishProgress(con.toString());
                os = con.getOutputStream();

                //publishProgress(jobj.toString());

                os.write(jobj.toString().getBytes("UTF-8")); //os.writeByte(jobj.toString()); Request Body에 Data 셋팅
                os.flush(); // Request Body에 Data 입력.
                os.close();
                //publishProgress(os.toString());
                String response;
                int responseCode = con.getResponseCode(); // 실제 서버로 Request 요청 하는 부분. (응답 코드를 받는다. 200 성공, 나머지 에러) 연결시도는 getOutputStream에서도 하는듯
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    publishProgress("3");
                    is = con.getInputStream();
                    baos = new ByteArrayOutputStream();
                    byte[] byteBuffer = new byte[1024];
                    byte[] byteData = null;
                    int nLength = 0;
                    while ((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                        baos.write(byteBuffer, 0, nLength);
                    }
                    byteData = baos.toByteArray();
                    response = new String(byteData);

                    responseJSON.getJSONObject(response);
                    publishProgress("4");
                }
            }catch (Exception e) {

            }
            return 1;
        }
        protected void onProgressUpdate(String... values) { //doInBackground 메서드 중간중간에 UI스레드에게 일처리 맡길 상황일때, publishProgress()호출하면된다.
            super.onProgressUpdate(values);
            txt3.setText("여기까지왔다"+values[0]);
        }
        protected void onPostExecute(Integer res) { //background가 끝나고 리턴값을 넘겨준다. 이 값을 해당 메소드가 매개변수로 받은 후 UI스레드에 일처리 시킬 떄 사용한다.
            super.onPostExecute(res);
            try {
                //Boolean result = (Boolean) responseJSON.get("result");
                String minDIst = (String) responseJSON.get("minDist");
                String minmaxDist = (String) responseJSON.get("minmaxDist");
                txt1.setText(minDIst);
                txt2.setText(minmaxDist);
            } catch(Exception e) {

            }
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        //hmm
        if (id == R.id.AddPet) {
            // Handle the camera action
            //startActivity(new Intent(this, ModifyPetActivity.class));
            startActivity(new Intent(this, SignHomeActivity.class));
        } else if (id == R.id.Diagnosis) {
            startActivity(new Intent(this, DiagnosisActivity.class));
        } else if (id == R.id.Shopping) {
            startActivity(new Intent(this, ShoppingActivity.class));
        } else if (id == R.id.MyPage) {
            startActivity(new Intent(this, MypageActivity.class));
        } else if (id == R.id.Logout) {
            // popup Dialogue, logout and go to mainActivity
            startActivity(new Intent(this, MainActivity.class));
        } else if (id == R.id.ToSample) {
            startActivity((new Intent(this, WebServiceDist.class)));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void setLayout() {
        eTxt_Sx = (EditText) findViewById(R.id.S_x);
        eTxt_Sy = (EditText) findViewById(R.id.S_y);
        eTxt_Sz = (EditText) findViewById(R.id.S_z);
        eTxt_Tx = (EditText) findViewById(R.id.T_x);
        eTxt_Ty = (EditText) findViewById(R.id.T_y);
        eTxt_Tz = (EditText) findViewById(R.id.T_z);
        eTxt_Px = (EditText) findViewById(R.id.P_x);
        eTxt_Py = (EditText) findViewById(R.id.P_y);
        eTxt_Pz = (EditText) findViewById(R.id.P_z);
        txt1 = (TextView) findViewById(R.id.minDistance);
        txt2 = (TextView) findViewById(R.id.minmaxDistance);
        txt3 = (TextView) findViewById(R.id.status);
        btn1 = (Button) findViewById(R.id.button_get_distance);
    }
}