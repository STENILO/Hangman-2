package com.example.juste.hangedman_game_v2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private Button user;
    private Button anon;
    private EditText userName;
    private String name;
    private String langauge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Firebase.setAndroidContext(this);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        user = (Button) findViewById(R.id.UserButton);
        user.setOnClickListener(this);
        anon = (Button) findViewById(R.id.anonButton);
        anon.setOnClickListener(this);
        userName = (EditText) findViewById(R.id.Username);
        userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v == userName){
                    userName.setText("");
                }
            }
        });

        name = "ERROR";
        langauge = getIntent().getStringExtra("message");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void onClick(View v){
        if(v == user){
            name = userName.getText().toString();
            userName.setText("UserName Accepted, if you want to change it simply write it again");
        }else if (v == anon) {
            name = "Anon";
            userName.setText("User is Anonymous, if you want to change it simply write a name");
        }
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_playgame) {
            if(name.equals("ERROR")){
                userName.setText("Please choose to make a user or play Anon");
            }else if(langauge==null){
                Intent gameStart = new Intent(this, hangedmanGame.class);
                gameStart.putExtra("name", name);
                startActivity(gameStart);
            }else if(langauge != null){
                Intent gameStart = new Intent(this, hangedmanGame.class);
                gameStart.putExtra("name", name);
                gameStart.putExtra("langauge", langauge);
                startActivity(gameStart);
            }

        } else if (id == R.id.nav_highscore) {
            Intent stats = new Intent(this, StatsActivity.class);
            startActivity(stats);
        } else if (id == R.id.nav_language) {
            startActivity(new Intent(this, LanguageActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}