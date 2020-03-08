
package com.example.tedesk.activity;



import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.tedesk.R;
import com.example.tedesk.adapters.CategoryAdapter;
import com.example.tedesk.constans.AppConstants;
import com.example.tedesk.listeners.ListItemClickListener;
import com.example.tedesk.models.quiz.CategoryModel;
import com.example.tedesk.utilities.ActivityUtilities;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class MenuActivity extends BaseActivity {

    private static final int LAYOUT = R.layout.activity_menu;

    private Context context;
    private Activity activity;

    private DrawerLayout drawerLayout;
    private ViewPager viewPager;

    private ArrayList<CategoryModel> categoryList;
    private CategoryAdapter adapter = null;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Изменил цвет в style(v21) для AppTheme2 , ничего не изменилось (кроме цвета при scroll)
        setTheme(R.style.AppTheme2);
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        activity = MenuActivity.this;
        context = getApplicationContext();

        recyclerView = (RecyclerView) findViewById(R.id.rvContent);
        recyclerView.setLayoutManager(new GridLayoutManager(activity, 1, GridLayoutManager.VERTICAL, false));

        categoryList = new ArrayList<>();
        adapter = new CategoryAdapter(context, activity, categoryList);
        recyclerView.setAdapter(adapter);

        initToolbar(getString(R.string.app_name));
        initNavigationView();
        initListener();
        loadData();

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
                    case R.id.actionRemindingItem:
                        Toast.makeText(getApplicationContext(),"Reminding is selected",Toast.LENGTH_LONG).show();
                        break;
                    case R.id.actionFavouritesItem:
                        Toast.makeText(getApplicationContext(),"Favourites is selected",Toast.LENGTH_LONG).show();
                        break;
                    case R.id.actionSettingsItem:
                        Toast.makeText(getApplicationContext(),"Settings is selected",Toast.LENGTH_LONG).show();
                        break;
                    case R.id.actionReferenceItem:
                        Toast.makeText(getApplicationContext(),"Reference is selected",Toast.LENGTH_LONG).show();
                        break;
                }
                return false;
            }
        });
    }

    private void loadData() {
        loadJson();
    }

    private void loadJson() {
        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;
        try{
            br = new BufferedReader(new InputStreamReader(getAssets().open(AppConstants.CONTENT_FILE)));
            String temp;
            while ((temp = br.readLine()) != null)
                sb.append(temp);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        parseJson(sb.toString());
    }

    private void parseJson(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray jsonArray = jsonObject.getJSONArray(AppConstants.JSON_KEY_ITEMS);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);

                String categoryId = object.getString(AppConstants.JSON_KEY_CATEGORY_ID);
                String categoryName = object.getString(AppConstants.JSON_KEY_CATEGORY_NAME);

                categoryList.add(new CategoryModel(categoryId, categoryName));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter.notifyDataSetChanged();
    }

    private void initListener() {

        // recycler list item click listener
        adapter.setItemClickListener(new ListItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {

                CategoryModel model = categoryList.get(position);
                ActivityUtilities.getInstance().invokeCommonQuizActivity(activity, SelectionActivity.class, model.getCategoryId(), true);
            }
        });
    }
}
