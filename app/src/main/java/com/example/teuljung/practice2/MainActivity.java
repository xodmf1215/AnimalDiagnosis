package com.example.teuljung.practice2;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import com.example.teuljung.practice2.*;
import com.example.teuljung.practice2.drawer.MyNavigationDrawer;
import com.example.teuljung.practice2.testing.ImageAreasTestActivity;
import com.example.teuljung.practice2.testing.ImageMapTestActivity;
import com.example.teuljung.practice2.testing.SampleCoordinatorActivity;
import com.example.teuljung.practice2.testing.WebServiceDist;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
//public class MainActivity extends AppCompatActivity implements MyNavigationDrawer {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00bcd4")));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
            startActivity(new Intent(this, ModifyPetActivity.class));
            finish();
        } else if (id == R.id.Diagnosis) {
            startActivity(new Intent(this, DiagnosisActivity.class));
            finish();
        } else if (id == R.id.Shopping) {
            startActivity(new Intent(this, ShoppingActivity.class));
            finish();
        } else if (id == R.id.MyPage) {
            startActivity(new Intent(this, MypageActivity.class));
            finish();
        } else if (id == R.id.Logout) {
            // popup Dialogue, logout and go to mainActivity
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else if (id ==R.id.ToSample) {
            startActivity((new Intent(this, ImageAreasTestActivity.class)));
            finish();
        } else if(id==R.id.ToLoginHome) {
            startActivity((new Intent(this, SignHomeActivity.class)));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
