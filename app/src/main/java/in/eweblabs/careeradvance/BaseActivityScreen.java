package in.eweblabs.careeradvance;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import in.eweblabs.careeradvance.Account.ChangePassword;
import in.eweblabs.careeradvance.Account.UpdateProfile.UpdateProfileScreen;
import in.eweblabs.careeradvance.Account.ProfileScreen;
import in.eweblabs.careeradvance.Account.SignInScreen;
import in.eweblabs.careeradvance.Account.SignUpScreen;
import in.eweblabs.careeradvance.Account.UpdateProfile.UploadYourDetailedResume;
import in.eweblabs.careeradvance.Account.UpdateProfile.YourContactInformation;
import in.eweblabs.careeradvance.Account.UpdateProfile.YourCurrentEmploymentDetails;
import in.eweblabs.careeradvance.Entity.UserInfo;
import in.eweblabs.careeradvance.Search.ChangeCountry;
import in.eweblabs.careeradvance.Search.JobDetailFragment;
import in.eweblabs.careeradvance.Search.SearchFragment;
import in.eweblabs.careeradvance.Search.SearchResultFragment;
import in.eweblabs.careeradvance.UI.AppRateDialog;
import in.eweblabs.careeradvance.UI.AppRater;
import in.eweblabs.careeradvance.UI.CircleImageView;
import in.eweblabs.careeradvance.UI.WebviewMessageDialog;
import in.eweblabs.careeradvance.Utils.Logger;
import in.eweblabs.careeradvance.loader.ImageLoader;

public class BaseActivityScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ActionBarDrawerToggle toggle;
    public EditText edit_search_country;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        final View headerView = (View) LayoutInflater.from(this).inflate(R.layout.nav_header_splash_screen, null);
        navigationView.addHeaderView(headerView);

        toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close){

            @Override
            public void onDrawerClosed(View drawerView) {
                 super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                HeaderSettings(headerView);
                super.onDrawerOpened(drawerView);
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        ((LinearLayout)headerView.findViewById(R.id.navigation_header)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                if (ApplicationController.getInstance().getUserInfo() != null &&
                        !TextUtils.isEmpty(ApplicationController.getInstance().getUserInfo().getUserEmail())) {
                    onReplaceFragment(new ProfileScreen(), true);
                } else {
                    onReplaceFragment(new SignInScreen(), true);
                }
            }
        });


        onReplaceFragment(new SearchFragment(), false);
        AppRater.app_launched(this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container);
                if (currentFragment instanceof SearchFragment || currentFragment instanceof ChangeCountry) {
                    drawer.openDrawer(GravityCompat.START);
                } else {
                    onBackPressed();
                }
            }
        });
        edit_search_country = (EditText) findViewById(R.id.edit_search_country);

    }

    private void HeaderSettings(View headerView) {

        TextView text_email_address = (TextView)headerView.findViewById(R.id.text_email_address);
        TextView txt_username = (TextView)headerView.findViewById(R.id.txt_username);
        UserInfo userInfo = ApplicationController.getInstance().getUserInfo();
        if(userInfo!=null && !TextUtils.isEmpty(userInfo.getUserEmail()))
        {

            text_email_address.setVisibility(View.VISIBLE);
            txt_username.setText(userInfo.getUserName());
            text_email_address.setText(userInfo.getUserEmail());
            ImageLoader imageLoader =  new ImageLoader(this);
            if(!TextUtils.isEmpty(userInfo.getUserAvatar()))
            imageLoader.DisplayImage(userInfo.getUserAvatar(), ((CircleImageView)headerView.findViewById(R.id.profile_img)));

        }
        else{
            text_email_address.setVisibility(View.INVISIBLE);
            txt_username.setText(getString(R.string.title_sign_in_sign_up));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Fragment fragment  = getSupportFragmentManager().findFragmentById(R.id.container);
        if(fragment instanceof SearchFragment)
            getMenuInflater().inflate(R.menu.recent_search, menu);
        else if(fragment instanceof ProfileScreen)
            getMenuInflater().inflate(R.menu.change_password_menu, menu);


        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Logger.d("cek", "home selected");
        switch (item.getItemId()) {
            case android.R.id.home:
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container);
                if (currentFragment instanceof SearchFragment) {
                    if (toggle.onOptionsItemSelected(item))
                        return true;
                } else {
                    onBackPressed();
                    return true;
                }
                break;
            case R.id.action_recent:
                Toast.makeText(getApplicationContext(),"No Recent Search",Toast.LENGTH_LONG).show();
                break;
            case R.id.action_change_password:
                onReplaceFragment(new ChangePassword(),true);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    WebviewMessageDialog webviewMessageDialog = null;
    WebviewMessageDialog webviewAboutusMessageDialog = null;
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.container);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        int id = item.getItemId();
        if (id == R.id.nav_job_search) {
            if(fragment instanceof SearchFragment){
            }
            else
                onReplaceFragment(new SearchFragment(), false);
        }
        else if (id == R.id.nav_post_your_resume) {

        } else if (id == R.id.nav_change_country) {
            if(fragment instanceof ChangeCountry){}
            else
                onReplaceFragment(new ChangeCountry(),true);
        }  else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_term_and_condition) {
            if(webviewMessageDialog==null)
                webviewMessageDialog =new WebviewMessageDialog(this);
            if(webviewMessageDialog!=null && !webviewMessageDialog.isShowing())
            {
                webviewMessageDialog.show();
                webviewMessageDialog.setTitle(getString(R.string.term_and_condition));
                webviewMessageDialog.setWebViewContent("file:///android_asset/term_and_condition.html");
            }

        } else if (id == R.id.nav_rate_us) {
            SharedPreferences prefs = getSharedPreferences("apprater", 0);
            SharedPreferences.Editor editor = prefs.edit();
            AppRateDialog appRateDialog =  new AppRateDialog(this,editor);
            appRateDialog.show();
        }
        else if (id == R.id.nav_about_us) {
            if(webviewAboutusMessageDialog==null)
                webviewAboutusMessageDialog =new WebviewMessageDialog(this);
            if(webviewAboutusMessageDialog!=null && !webviewAboutusMessageDialog.isShowing())
            {
                webviewAboutusMessageDialog.show();
                webviewAboutusMessageDialog.setTitle(getString(R.string.about_us));
                webviewAboutusMessageDialog.setWebViewContent("file:///android_asset/about_us.html");
            }
        }
        else if(id == R.id.nav_promote_this_app){
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, String.format(getString(R.string.app_play_store_link), getPackageName()));
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
        }

        return true;
    }

    public void onReplaceFragment(Fragment fragment, boolean flag) {
        FragmentManager fragmentmanager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentmanager.beginTransaction();
        String backStateName = fragment.getClass().getName();
        if (flag) {
            String s = fragmentmanager.getClass().getName();
            if (!fragmentmanager.popBackStackImmediate(s, 0)) {
                fragmentTransaction.replace(R.id.container, fragment, backStateName);
                fragmentTransaction.addToBackStack(s);
                fragmentTransaction.commit();
            }
            return;
        } else {
            fragmentTransaction.replace(R.id.container, fragment, backStateName);
            fragmentTransaction.commit();
            return;
        }
    }

    public void SetToolbarInitialization(Fragment fragment){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if(fragment instanceof SearchFragment) {
            setTitle(getString(R.string.title_job_search));
            SetDrawerVisibility(0);
            ((EditText)findViewById(R.id.edit_search_country)).setVisibility(View.GONE);
            navigationView.setCheckedItem(R.id.nav_job_search);
            navigationView.invalidate();
            invalidateOptionsMenu();
        }
        else if(fragment instanceof SignInScreen) {
            setTitle(getString(R.string.title_sign_in));
            SetDrawerVisibility(1);
            ((EditText) findViewById(R.id.edit_search_country)).setVisibility(View.GONE);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            invalidateOptionsMenu();
        }
        else if(fragment instanceof SignUpScreen) {
            setTitle(getString(R.string.title_sign_out));
            SetDrawerVisibility(1);
            ((EditText)findViewById(R.id.edit_search_country)).setVisibility(View.GONE);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            invalidateOptionsMenu();
        }
        else if(fragment instanceof ProfileScreen) {
            setTitle(getString(R.string.title_profile));
            SetDrawerVisibility(1);
            ((EditText) findViewById(R.id.edit_search_country)).setVisibility(View.GONE);
            toggle.setDrawerIndicatorEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            invalidateOptionsMenu();
        }
        else if(fragment instanceof SignUpScreen) {
            setTitle(getString(R.string.forget_password));
            SetDrawerVisibility(1);
            ((EditText)findViewById(R.id.edit_search_country)).setVisibility(View.GONE);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            invalidateOptionsMenu();
        }
        else if(fragment instanceof UpdateProfileScreen) {
            setTitle(getString(R.string.add_update_profile));
            SetDrawerVisibility(1);
            ((EditText) findViewById(R.id.edit_search_country)).setVisibility(View.GONE);
            toggle.setDrawerIndicatorEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            invalidateOptionsMenu();
        }
        else if(fragment instanceof ChangePassword) {
            setTitle(getString(R.string.action_change_password));
            SetDrawerVisibility(1);
            ((EditText) findViewById(R.id.edit_search_country)).setVisibility(View.GONE);
            toggle.setDrawerIndicatorEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            invalidateOptionsMenu();
        }
        else if(fragment instanceof ChangeCountry) {
            setTitle(getString(R.string.change_county));
            SetDrawerVisibility(0);
            ((EditText)findViewById(R.id.edit_search_country)).setVisibility(View.VISIBLE);
            navigationView.setCheckedItem(R.id.nav_change_country);
            navigationView.invalidate();
            invalidateOptionsMenu();
        }
        else if(fragment instanceof UpdateProfileScreen) {
            setTitle(getString(R.string.title_profile));
            SetDrawerVisibility(1);
            ((EditText) findViewById(R.id.edit_search_country)).setVisibility(View.GONE);
            toggle.setDrawerIndicatorEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            invalidateOptionsMenu();
        }else if(fragment instanceof SearchResultFragment) {
            setTitle(getString(R.string.title_job_for_you));
            SetDrawerVisibility(1);
            ((EditText) findViewById(R.id.edit_search_country)).setVisibility(View.GONE);
            toggle.setDrawerIndicatorEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            invalidateOptionsMenu();
        }else if(fragment instanceof JobDetailFragment) {
            setTitle(getString(R.string.job_details));
            SetDrawerVisibility(1);
            ((EditText) findViewById(R.id.edit_search_country)).setVisibility(View.GONE);
            toggle.setDrawerIndicatorEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            invalidateOptionsMenu();
        }


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = fm.findFragmentById(R.id.container);
            if(fragment instanceof UpdateProfileScreen){
                if(((UpdateProfileScreen)fragment).getCurrentViewPager()==0)
                {
                    fm.popBackStack();
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                }
                else
                    ((UpdateProfileScreen)fragment).setCurrentViewPager(((UpdateProfileScreen)fragment).getCurrentViewPager()-1);

            }
            else if (fm.getBackStackEntryCount() > 0) {
                fm.popBackStack();
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            } else {
                if (fragment instanceof SearchFragment) {
                    super.onBackPressed();
                } else
                    onReplaceFragment(new SearchFragment(),false);
            }

        }

    }

    public void SetDrawerVisibility(int status) {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (status == 0) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, navigationView);
            toggle.onDrawerStateChanged(DrawerLayout.LOCK_MODE_UNLOCKED);
            toggle.setDrawerIndicatorEnabled(true);

            toggle.syncState();
        } else if (status == 1) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, navigationView);
            toggle.onDrawerStateChanged(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            toggle.setDrawerIndicatorEnabled(false);
            toggle.syncState();
        }
    }

}
