package in.sethiya.bizzbots.bfsi.finces.merchant.activity.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import in.sethiya.bizzbots.bfsi.finces.merchant.activity.SetupPasscodeActivity;
import in.sethiya.bizzbots.bfsi.finces.merchant.databinding.ActivityLoginMainBinding;

public class LoginMainActivity extends AppCompatActivity {
    private ActivityLoginMainBinding binding;
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, SetupPasscodeActivity.class));
                finish();
            }
        });
    }
}