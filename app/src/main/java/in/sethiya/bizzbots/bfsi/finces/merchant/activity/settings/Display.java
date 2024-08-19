package in.sethiya.bizzbots.bfsi.finces.merchant.activity.settings;

import static in.sethiya.bizzbots.bfsi.finces.merchant.AppConstants.SHARED_PREF;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.Objects;

import in.sethiya.bizzbots.bfsi.finces.merchant.R;
import in.sethiya.bizzbots.bfsi.finces.merchant.databinding.ActivityDisplayBinding;

public class Display extends AppCompatActivity {
    private ActivityDisplayBinding binding;
    private final Context context = this;
    private SharedPreferences.Editor editor;
    private Dialog themeDialog;
    private int selectedTheme;
    private boolean isDarkModeOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDisplayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sharedPreferences
                = getSharedPreferences(SHARED_PREF,
                MODE_PRIVATE);
        editor = sharedPreferences.edit();

        initTheme();
    }

    private void initTheme() {
        themeDialog = new Dialog(context);
        themeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        themeDialog.setContentView(R.layout.model_theme_select);
        Objects.requireNonNull(themeDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        themeDialog.setCancelable(true);
     //   themeDialog.show();

        RelativeLayout systemDefault = themeDialog.findViewById(R.id.system_default);
        RelativeLayout light = themeDialog.findViewById(R.id.light);
        RelativeLayout dark = themeDialog.findViewById(R.id.dark);
        TextView okButton = themeDialog.findViewById(R.id.ok);
        TextView cancelButton = themeDialog.findViewById(R.id.cancel);


        RadioButton systemDefaultRadioBtn = themeDialog.findViewById(R.id.system_def_radio_btn);
        RadioButton lightRadioBtn = themeDialog.findViewById(R.id.light_radio_btn);
        RadioButton darkRadioBn = themeDialog.findViewById(R.id.dark_radio_btn);
    //    systemDefault.setEnabled(true);

        darkRadioBn.setEnabled(true);
        lightRadioBtn.setEnabled(true);
        systemDefaultRadioBtn.setEnabled(true);
        okButton.setEnabled(true);
        cancelButton.setEnabled(true);

        systemDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTheme = 1;
                systemDefaultRadioBtn.setChecked(true);

                lightRadioBtn.setChecked(false);
                darkRadioBn.setChecked(false);
            }
        });

        light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTheme = 2;
                lightRadioBtn.setChecked(true);

                systemDefaultRadioBtn.setChecked(false);
                darkRadioBn.setChecked(false);
            }
        });

        dark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTheme = 3;
                darkRadioBn.setChecked(true);

                lightRadioBtn.setChecked(false);
                systemDefaultRadioBtn.setChecked(false);
            }
        });

        systemDefaultRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTheme = 1;
                systemDefaultRadioBtn.setChecked(true);

                lightRadioBtn.setChecked(false);
                darkRadioBn.setChecked(false);
            }
        });

        lightRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTheme = 2;
                lightRadioBtn.setChecked(true);

                systemDefaultRadioBtn.setChecked(false);
                darkRadioBn.setChecked(false);
            }
        });

        darkRadioBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTheme = 3;
                darkRadioBn.setChecked(true);

                lightRadioBtn.setChecked(false);
                systemDefaultRadioBtn.setChecked(false);
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (selectedTheme){
                    case 1:
                        enableSystemDefaultMode();
                        showToast("Selected system default theme");
                        break;

                    case 2:
                        enableLightMode();
                        showToast("Selected light theme");
                        break;

                    case 3:
                        enableDarkMode();
                        showToast("Selected dark theme");
                        break;

                    default:
                        showToast("Theme not selected!");
                        break;
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themeDialog.dismiss();
            }
        });

    }

    private void enableSystemDefaultMode() {
        AppCompatDelegate
                .setDefaultNightMode(
                        AppCompatDelegate
                                .MODE_NIGHT_FOLLOW_SYSTEM);

        // it will set isDarkModeOn
        // boolean to true
        editor.putBoolean("isDarkModeOn",
                true);
        editor.apply();
        themeDialog.dismiss();
    }

    private void enableLightMode() {
        AppCompatDelegate
                .setDefaultNightMode(
                        AppCompatDelegate
                                .MODE_NIGHT_NO);
        editor.putBoolean("isDarkModeOn",
                false);
        editor.apply();
        themeDialog.dismiss();
    }

    private void enableDarkMode() {


            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_YES);



            editor.putBoolean("isDarkModeOn",
                    true);
            editor.apply();

        themeDialog.dismiss();
    }

    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void onTheme(View view) {
        themeDialog.show();
    }
}