package in.sethiya.bizzbots.bfsi.finces.merchant.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import java.util.Locale;

import in.sethiya.bizzbots.bfsi.finces.merchant.R;
import in.sethiya.bizzbots.bfsi.finces.merchant.databinding.ActivityHomeBinding;
import in.sethiya.bizzbots.bfsi.finces.merchant.helper.LocaleHelper;
import in.sethiya.bizzbots.bfsi.finces.merchant.helper.session.SessionHandler;
import in.sethiya.bizzbots.bfsi.finces.merchant.helper.session.User;

public class Home extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private SessionHandler session;
    private Context context = this;
    private final static String TAG = "Home";
    private String currentLanguage = "en", currentLang;
    private Locale myLocale;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        currentLanguage = getIntent().getStringExtra(currentLang);

        session = new SessionHandler(getApplicationContext());
        user = session.getUserDetails();

        if(session.isLoggedIn()){
            binding.navDrawerLayout.userName.setText(user.getName());
            binding.navDrawerLayout.userName.setText(user.getName());

            binding.toolbar.usernameLayout.setVisibility(View.VISIBLE);
            binding.toolbar.loginTextLayout.setVisibility(View.INVISIBLE);
            // Drawer
            binding.navDrawerLayout.loginBtn.setVisibility(View.INVISIBLE);
        }else{
            binding.toolbar.loginTextLayout.setVisibility(View.VISIBLE);
            binding.toolbar.usernameLayout.setVisibility(View.INVISIBLE);

            // Drawer
            binding.navDrawerLayout.userName.setVisibility(View.INVISIBLE);
            binding.navDrawerLayout.usernamePlaceHolder.setVisibility(View.INVISIBLE);
            binding.navDrawerLayout.loginBtn.setVisibility(View.VISIBLE);
        }
    }

    public void ClickMenu(View view){
        openDrawer(binding.drawerLayout);
    }

    private static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view){
        closeDrawer(binding.drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickHome(View view){
        recreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }
}