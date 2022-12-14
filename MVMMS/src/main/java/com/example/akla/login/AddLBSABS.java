package com.example.akla.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

public class AddLBSABS extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lbsabs);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.apphome) {
            Intent intent = new Intent(AddLBSABS.this, MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_addLine) {
            Intent intent = new Intent(AddLBSABS.this, AddLine.class);
            startActivity(intent);

        } else if (id == R.id.nav_addGantry) {
            Intent intent = new Intent(AddLBSABS.this, AddGantry.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSupport) {
            Intent intent = new Intent(AddLBSABS.this, AddSupport.class);
            startActivity(intent);


        }else if (id == R.id.nav_addTowerMaintainance) {
            Intent intent = new Intent(AddLBSABS.this, TM2.class);
            startActivity(intent);


        }else if (id == R.id.nav_addEquipment) {
            Intent intent = new Intent(AddLBSABS.this, AddEquipment.class);
            startActivity(intent);


        } else if (id == R.id.nav_Login) {
            Intent intent = new Intent(AddLBSABS.this, Login.class);
            startActivity(intent);

        } else if (id == R.id.nearby) {
            Intent intent = new Intent(AddLBSABS.this, GetNearByTower.class);
            startActivity(intent);

        } else if (id == R.id.nav_addGantry) {
            Intent intent = new Intent(AddLBSABS.this, AddGantry.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_addAutoRecloser) {
            Intent intent = new Intent(AddLBSABS.this, AddAutoRecloser.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_addLBSABS) {
            Intent intent = new Intent(AddLBSABS.this, AddLBSABS.class);
            startActivity(intent);

        }


        else if (id == R.id.nav_addFeeder) {
            Intent intent = new Intent(AddLBSABS.this, AddFeeder.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_addPoles) {
            Intent intent = new Intent(AddLBSABS.this, AddPoles.class);
            startActivity(intent);

        } else if (id == R.id.nav_addTowers) {
            Intent intent = new Intent(AddLBSABS.this, AddLBSABS.class);
            startActivity(intent);
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {

    }
}
