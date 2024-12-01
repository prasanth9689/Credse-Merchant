package in.sethiya.bizzbots.bfsi.finces.merchant.activity;

import static in.sethiya.bizzbots.bfsi.finces.merchant.AppConstants.SHARED_PREF;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import in.sethiya.bizzbots.bfsi.finces.merchant.R;
import in.sethiya.bizzbots.bfsi.finces.merchant.activity.contacts.ContactsDetailsActivity;
import in.sethiya.bizzbots.bfsi.finces.merchant.activity.contacts.EduOccupDetailsActivity;
import in.sethiya.bizzbots.bfsi.finces.merchant.activity.contacts.IdentityDetailsActivity;
import in.sethiya.bizzbots.bfsi.finces.merchant.activity.contacts.RelCommDetailsActivity;
import in.sethiya.bizzbots.bfsi.finces.merchant.activity.main.EnterAadhaarNoActivity;
import in.sethiya.bizzbots.bfsi.finces.merchant.activity.main.MyReviewActivity;
import in.sethiya.bizzbots.bfsi.finces.merchant.activity.main.RegisteredOffenceActivity;
import in.sethiya.bizzbots.bfsi.finces.merchant.activity.navigation_drawer.WalletStatementActivity;
import in.sethiya.bizzbots.bfsi.finces.merchant.activity.register.LoginActivity2;
import in.sethiya.bizzbots.bfsi.finces.merchant.activity.register.LoginMainActivity;
import in.sethiya.bizzbots.bfsi.finces.merchant.activity.settings.Display;
import in.sethiya.bizzbots.bfsi.finces.merchant.activity.settings.Settings;
import in.sethiya.bizzbots.bfsi.finces.merchant.databinding.ActivityEduOccupDetailsBinding;
import in.sethiya.bizzbots.bfsi.finces.merchant.databinding.ActivityEnterAadhaarNoBinding;
import in.sethiya.bizzbots.bfsi.finces.merchant.databinding.ActivitySplashBinding;
import in.sethiya.bizzbots.bfsi.finces.merchant.helper.session.SessionHandler;
import in.sethiya.bizzbots.bfsi.finces.merchant.payments.PaymentsOptionsActivity;
import in.sethiya.bizzbots.bfsi.finces.merchant.payments.UpiEnterPaymentActivity;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    private ActivitySplashBinding binding;
    private static final String SHARED_PREFE_ID = "mypref";
    private static final String KEY_PREFE_GET_STARTED = "get_started";
    private static final String KEY_PREFE_LANG = "lang";
    private SharedPreferences sp;
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);

        /*
         * Normal init app
         *
         * Splash -> Language selection -> Get started -> Register mobile -> Verification -> Device registration -> Login -> Setup passcode -> Home
         */
    //    normalInitApp();

        /*
         *  Bypass init application
         *
         *  Splash -> Login -> Setup passcode -> Home
         */
   //     byPassInitApp();

        testInitApp();
    }

    private void testInitApp() {
        startActivity(new Intent(context, EnterAadhaarNoActivity.class));
        finish();
    }

    private void byPassInitApp() {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(context, LoginActivity2.class);
       startActivity(intent);
       finish();

        },1500);
    }

    private void normalInitApp() {
        initTheme();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sp = getSharedPreferences(SHARED_PREFE_ID, MODE_PRIVATE);
        String status = sp.getString(KEY_PREFE_GET_STARTED, "");

        SessionHandler session = new SessionHandler(getApplicationContext());

        Handler handler = new Handler();
        handler.postDelayed(() -> {

            String LANG_ID = sp.getString(KEY_PREFE_LANG, String.valueOf(3));
            if (LANG_ID.equals("1")){
                if (status.equals("true")){
                    if (session.isLoggedIn()) {
                        Intent intent = new Intent(context, Home.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        startActivity(new Intent(context, Home.class));
                        //  finish();
                    }
                }else {
                    Intent intent = new Intent(context, GetStartedActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }else{
                Intent intent = new Intent(context, ChooseLanguageActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        },1500);
    }

    private void initTheme() {
        SharedPreferences sharedPreferences
                = getSharedPreferences(SHARED_PREF,
                MODE_PRIVATE);

        final boolean isDarkModeOn
                = sharedPreferences.getBoolean("isDarkModeOn", false);

        Toast.makeText(context, String.valueOf(isDarkModeOn), Toast.LENGTH_SHORT).show();

        if (isDarkModeOn) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES);
        }
        else {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}