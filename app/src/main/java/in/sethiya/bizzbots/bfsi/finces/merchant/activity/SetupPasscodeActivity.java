package in.sethiya.bizzbots.bfsi.finces.merchant.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import in.sethiya.bizzbots.bfsi.finces.merchant.databinding.ActivitySetupPasscodeBinding;

public class SetupPasscodeActivity extends AppCompatActivity {
    private ActivitySetupPasscodeBinding binding;
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = ActivitySetupPasscodeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.getOtp.setOnClickListener(view -> {
//            String mobile = binding.mobile.getText().toString();
//            if (mobile.isEmpty()) {
//                binding.mobile.setError("Please enter mobile number");
//                binding.mobile.requestFocus();
//                return;
//            }
            startActivity(new Intent(context, Home.class));
            finish();
        });
    }
}