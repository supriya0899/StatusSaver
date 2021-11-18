package com.example.statussaver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.statussaver.Adapters.PagerAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.paperdb.Paper;

public class DrawerActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNav;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Paper.init(DrawerActivity.this); // initialize of paper library
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);


        toolbar = (Toolbar) findViewById(R.id.toolBarMainActivity);
        tabLayout = (TabLayout) findViewById(R.id.tabsLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mToggle);
        mToggle.syncState();


        ButterKnife.bind(this);
      setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Status Saver:(WhatsApp)");
      // getSupportActionBar().setHomeButtonEnabled(true);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      //  getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_save);
        //setupTool();

        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

        mNav = (NavigationView) findViewById(R.id.nav_menu);
        mNav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                if(menuItem.getItemId() == R.id.Bug)
                {
                    Intent intent = new Intent(DrawerActivity.this,LoadWebViewActivity.class);
                    intent.putExtra("website-link","https://docs.google.com/forms/d/e/1FAIpQLSeuYY_NksnFhgbjSzunro0S0ku-2WWIPqkWlJ6x4tnZ_8j6pw/viewform");
                    startActivity(intent);
                }
                if(menuItem.getItemId() == R.id.rate)
                {
                    Intent intent = new Intent(DrawerActivity.this,LoadWebViewActivity.class);
                    startActivity(intent);
                }
                if(menuItem.getItemId() == R.id.share)
                {
                    Intent wIntent = new Intent(Intent.ACTION_SEND);
                    wIntent.setType("text/plain");
                    String shareBody="Download Status Saver for WhatsApp  through the link give below and download stories & status updates of your contact's in one click"
                            +"\n"+"\"https://play.google.com/store/apps/details?id=comm.anish.anish.freechat\""; // app link to be mentioned here after publishing.
                    String sharesub="WhatsApp Status Saver";
                    wIntent.putExtra(Intent.EXTRA_SUBJECT, shareBody);
                    wIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                    startActivity(Intent.createChooser(wIntent, "Share Using"));

                }
                if(menuItem.getItemId() == R.id.privacy_policy)
                {
                    Intent intent = new Intent(DrawerActivity.this,LoadWebViewActivity.class);
                    intent.putExtra("website-link","https://sites.google.com/view/pro-status-saver-whatsapp/home");
                    startActivity(intent);
                }
                return false;
            }
        });



    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mToggle.syncState();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        mToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
