package in.eweblabs.careeradvance;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
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

import com.bumptech.glide.Glide;
import com.google.firebase.iid.FirebaseInstanceId;

import in.eweblabs.careeradvance.Account.ChangePassword;
import in.eweblabs.careeradvance.Account.ProfileScreen;
import in.eweblabs.careeradvance.Account.SignInScreen;
import in.eweblabs.careeradvance.Account.SignUpScreen;
import in.eweblabs.careeradvance.Account.UpdateProfile.UpdateProfileScreen;
import in.eweblabs.careeradvance.Account.UploadResumeScreen;
import in.eweblabs.careeradvance.Entity.UserInfo;
import in.eweblabs.careeradvance.Network.BaseNetwork;
import in.eweblabs.careeradvance.Search.AppliedJobFragment;
import in.eweblabs.careeradvance.Search.ChangeCountry;
import in.eweblabs.careeradvance.Search.JobDetailFragment;
import in.eweblabs.careeradvance.Search.SearchFragment;
import in.eweblabs.careeradvance.Search.SearchResultFragment;
import in.eweblabs.careeradvance.StaticData.StaticConstant;
import in.eweblabs.careeradvance.UI.AppRateDialog;
import in.eweblabs.careeradvance.UI.AppRater;
import in.eweblabs.careeradvance.UI.CircleImageView;
import in.eweblabs.careeradvance.UI.WebviewMessageDialog;
import in.eweblabs.careeradvance.Utils.Logger;
import in.eweblabs.careeradvance.fcm.RegisterToken;

public class BaseActivityScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = BaseActivityScreen.class.getSimpleName();
    ActionBarDrawerToggle toggle;
    public EditText edit_search_country;
    private  static SessionManager sSessionManager;
    private UserInfo mUserInfo ;
    private CircleImageView mProfileImage;
    private Bundle mFilterBundle ;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
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
        mProfileImage = (CircleImageView)headerView.findViewById(R.id.header_profile_img);

        toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close){

            @Override
            public void onDrawerClosed(View drawerView) {
                 super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                headerSettings(headerView);
                super.onDrawerOpened(drawerView);
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        ((LinearLayout)headerView.findViewById(R.id.navigation_header)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                if (mUserInfo != null && !TextUtils.isEmpty(mUserInfo.getUserEmail())) {
                    //onReplaceFragment(new ProfileScreen(), true);
                    onReplaceFragment(new ProfileScreen(), false);
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

        mUserInfo =  getSessionManager().getUserInfoFromShPref();
        ApplicationController.getInstance().setUserInfo(mUserInfo);
        headerSettings(headerView);

        //SEND REGISTRATIO TOKEN IF NOT SENT YET
        String localStoredToken = getSessionManager().getString(BaseNetwork.DEVICE_TOKEN);
        if(TextUtils.isEmpty(localStoredToken)){
            Logger.v(TAG,"token is empty");
            if(mUserInfo != null && !TextUtils.isEmpty(FirebaseInstanceId.getInstance().getToken())){
                RegisterToken.sendRegistrationTokenToServer(FirebaseInstanceId.getInstance().getToken(),this);
            }
        }
        //MARSHMALLOW SUPPORT
        mayRequestPermission();
    }

    public synchronized  SessionManager getSessionManager(){
        if(sSessionManager == null){
            sSessionManager = new SessionManager(getApplicationContext());
        }
        return sSessionManager ;
    }

    public Bundle getmFilterBundle() {
        return mFilterBundle;
    }

    public void setmFilterBundle(Bundle mFilterBundle) {
        this.mFilterBundle = mFilterBundle;
    }

    public UserInfo getmUserInfo() {
        return mUserInfo;
    }

    public void setmUserInfo(UserInfo mUserInfo) {
        this.mUserInfo = mUserInfo;
    }

    //ImageLoader imageLoader;
    private void headerSettings(View headerView) {

        TextView text_email_address = (TextView)headerView.findViewById(R.id.text_email_address);
        TextView txt_username = (TextView)headerView.findViewById(R.id.txt_username);
        //UserInfo userInfo = ApplicationController.getInstance().getUserInfo();

        if(mUserInfo != null && !TextUtils.isEmpty(mUserInfo.getUserEmail())) {
            text_email_address.setVisibility(View.VISIBLE);
            txt_username.setText(mUserInfo.getUserName());
            text_email_address.setText(mUserInfo.getUserEmail());
            //imageLoader =  new ImageLoader(this);
            if(!TextUtils.isEmpty(mUserInfo.getUserAvatar())){
              //  imageLoader.DisplayImage(mUserInfo.getUserAvatar(),mProfileImage);
                Glide.with(this)
                        .load(mUserInfo.getUserAvatar())
                      //  .load("https://chinmayala.org/sites/default/files/styles/slideshow/public/gurudev-kids.jpg?itok=BdnmB7j3")
                        .placeholder(R.drawable.ic_face_white_48dp) // can also be a drawable
                        .error(R.drawable.ic_face_white_48dp) // will be displayed if the image cannot be loaded
                       // .crossFade(2000)
                        .into(mProfileImage);
            }

        } else{
            mProfileImage.setImageResource(R.drawable.ic_face_white_48dp);
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
            else{
                onReplaceFragment(new SearchFragment(), false);
            }
        } else if(id == R.id.nav_my_profile){
            if(mUserInfo == null){
                SignInScreen signInScreen = new SignInScreen();
                Bundle bundle = new Bundle();
                bundle.putString("activity", StaticConstant.SIGN_IN);
                signInScreen.setArguments(bundle);
                onReplaceFragment(signInScreen, true);
            }else{
                onReplaceFragment(new ProfileScreen(), false);
            }

        }else if(id == R.id.nav_applied_jobs){
            if(mUserInfo == null){
                SignInScreen signInScreen = new SignInScreen();
                Bundle bundle = new Bundle();
                bundle.putString("activity", StaticConstant.APPLIED_JOB);
                signInScreen.setArguments(bundle);
                onReplaceFragment(signInScreen, true);
            }else{
                onReplaceFragment(new AppliedJobFragment(), false);
            }
        } else if (id == R.id.nav_post_your_resume) {
            if(mUserInfo == null){
                SignInScreen signInScreen = new SignInScreen();
                Bundle bundle = new Bundle();
                bundle.putString("activity", StaticConstant.UPLOAD_RESUME);
                signInScreen.setArguments(bundle);
                onReplaceFragment(signInScreen, true);
            }else{
                onReplaceFragment(new UploadResumeScreen(), false);
            }
        } else if (id == R.id.nav_change_country) {
            if(fragment instanceof ChangeCountry){}
            else
                onReplaceFragment(new ChangeCountry(),true);
        }/*  else if (id == R.id.nav_settings) {

        }*/ else if (id == R.id.nav_term_and_condition) {
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

    public void setToolbarInitialization(Fragment fragment){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if(fragment instanceof SearchFragment) {
            setTitle(getString(R.string.title_job_search));
            setDrawerVisibility(0);
            ((EditText)findViewById(R.id.edit_search_country)).setVisibility(View.GONE);
            navigationView.setCheckedItem(R.id.nav_job_search);
            navigationView.invalidate();
            invalidateOptionsMenu();
        }
        else if(fragment instanceof SignInScreen) {
            setTitle(getString(R.string.title_sign_in));
            setDrawerVisibility(1);
            ((EditText) findViewById(R.id.edit_search_country)).setVisibility(View.GONE);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            invalidateOptionsMenu();
        }
        else if(fragment instanceof SignUpScreen) {
            setTitle(getString(R.string.title_sign_out));
            setDrawerVisibility(1);
            ((EditText)findViewById(R.id.edit_search_country)).setVisibility(View.GONE);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            invalidateOptionsMenu();
        }
        else if(fragment instanceof ProfileScreen) {
            setTitle(getString(R.string.title_profile));
            setDrawerVisibility(1);
            ((EditText) findViewById(R.id.edit_search_country)).setVisibility(View.GONE);
            toggle.setDrawerIndicatorEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            invalidateOptionsMenu();
        }
        else if(fragment instanceof SignUpScreen) {
            setTitle(getString(R.string.forget_password));
            setDrawerVisibility(1);
            ((EditText)findViewById(R.id.edit_search_country)).setVisibility(View.GONE);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            invalidateOptionsMenu();
        }
        else if(fragment instanceof UpdateProfileScreen) {
            setTitle(getString(R.string.add_update_profile));
            setDrawerVisibility(1);
            ((EditText) findViewById(R.id.edit_search_country)).setVisibility(View.GONE);
            toggle.setDrawerIndicatorEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            invalidateOptionsMenu();
        }
        else if(fragment instanceof ChangePassword) {
            setTitle(getString(R.string.action_change_password));
            setDrawerVisibility(1);
            ((EditText) findViewById(R.id.edit_search_country)).setVisibility(View.GONE);
            toggle.setDrawerIndicatorEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            invalidateOptionsMenu();
        }
        else if(fragment instanceof ChangeCountry) {
            setTitle(getString(R.string.change_county));
            setDrawerVisibility(0);
            ((EditText)findViewById(R.id.edit_search_country)).setVisibility(View.VISIBLE);
            navigationView.setCheckedItem(R.id.nav_change_country);
            navigationView.invalidate();
            invalidateOptionsMenu();
        }
        else if(fragment instanceof UpdateProfileScreen) {
            setTitle(getString(R.string.title_profile));
            setDrawerVisibility(1);
            ((EditText) findViewById(R.id.edit_search_country)).setVisibility(View.GONE);
            toggle.setDrawerIndicatorEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            invalidateOptionsMenu();
        }else if(fragment instanceof SearchResultFragment) {
            setTitle(getString(R.string.title_job_for_you));
            setDrawerVisibility(1);
            ((EditText) findViewById(R.id.edit_search_country)).setVisibility(View.GONE);
            toggle.setDrawerIndicatorEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            invalidateOptionsMenu();
        }else if(fragment instanceof JobDetailFragment) {
            setTitle(getString(R.string.job_details));
            setDrawerVisibility(1);
            ((EditText) findViewById(R.id.edit_search_country)).setVisibility(View.GONE);
            toggle.setDrawerIndicatorEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            invalidateOptionsMenu();
        }else if(fragment instanceof  AppliedJobFragment){
            setTitle(getString(R.string.applied_jobs));
            setDrawerVisibility(1);
            ((EditText) findViewById(R.id.edit_search_country)).setVisibility(View.GONE);
            toggle.setDrawerIndicatorEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            invalidateOptionsMenu();
        }else if(fragment instanceof  UploadResumeScreen){
            setTitle(getString(R.string.update_download_resume));
            setDrawerVisibility(1);
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

    public void setDrawerVisibility(int status) {
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

    private final int REQUEST_WRITE_EXT_STORAGE = 3;

    private boolean mayRequestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(this, getString(R.string.permission_storage_ratinale)
                    , Toast.LENGTH_LONG).show();
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXT_STORAGE);
        } else {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXT_STORAGE);
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_WRITE_EXT_STORAGE) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //startLocationUpdates();
            } else {
                if (requestCode == REQUEST_WRITE_EXT_STORAGE) {
                    Toast.makeText(this, getString(R.string.permission_storage_denied)
                            , Toast.LENGTH_LONG).show();
                }

            }
        }
    }

}
