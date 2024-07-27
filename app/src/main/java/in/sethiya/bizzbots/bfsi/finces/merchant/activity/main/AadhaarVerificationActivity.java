package in.sethiya.bizzbots.bfsi.finces.merchant.activity.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import in.sethiya.bizzbots.bfsi.finces.merchant.R;
import in.sethiya.bizzbots.bfsi.finces.merchant.activity.contacts.PersonalDetailsActivity;
import in.sethiya.bizzbots.bfsi.finces.merchant.databinding.ActivityAadhaarVerificationBinding;

public class AadhaarVerificationActivity extends AppCompatActivity {
    private ActivityAadhaarVerificationBinding binding;
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAadhaarVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        binding.edAadharOtp.requestFocus();

        binding.submit.setOnClickListener(v -> {
            String mPassCode = binding.edAadharOtp.getText().toString();
            if (mPassCode.isEmpty()) {
                binding.edAadharOtp.setError(getString(R.string.enter_otp));
                binding.edAadharOtp.requestFocus();
                return;
            }
            if (mPassCode.length() < 4) {
                binding.edAadharOtp.setError(getString(R.string.enter_4_digits_aadhar_otp));
                binding.edAadharOtp.requestFocus();
                return;
            }
            startActivity(new Intent(context, PersonalDetailsActivity.class));
        });

        binding.back.setOnClickListener(v -> finish());
    }
}