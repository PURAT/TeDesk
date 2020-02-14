
package com.example.tedesk;



import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;


public class MenuActivity extends AppCompatActivity {

    private  static final int LAYOUT = R.layout.activity_menu;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ViewPager viewPager;

    private long backPressedMillis;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme2);
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        initToolbar();
        initNavigationView();
    }


    private void initToolbar() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });

    }

    private void initNavigationView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.view_navigation_open,R.string.view_navigation_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem){
                drawerLayout.closeDrawers();
                switch (menuItem.getItemId()){
                    case R.id.actionHomeItem:
                        Toast.makeText(getApplicationContext(),"Home is selected",Toast.LENGTH_LONG).show();
                        break;
                    case R.id.actionInformationItem:
                        Toast.makeText(getApplicationContext(),"Information is selected",Toast.LENGTH_LONG).show();
                        break;
                    case R.id.actionSettingsItem:
                        Toast.makeText(getApplicationContext(),"Settings is selected",Toast.LENGTH_LONG).show();
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (backPressedMillis + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(this, "Нажмите ещё раз, чтобы выйти", Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedMillis = System.currentTimeMillis();
    }

}
