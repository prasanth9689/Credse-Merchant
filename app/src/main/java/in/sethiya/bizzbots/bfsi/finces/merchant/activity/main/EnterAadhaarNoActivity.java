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
import in.sethiya.bizzbots.bfsi.finces.merchant.databinding.ActivityEnterAadhaarNoBinding;

public class EnterAadhaarNoActivity extends AppCompatActivity {
    private ActivityEnterAadhaarNoBinding binding;
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEnterAadhaarNoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        binding.edAadharNo.requestFocus();

        binding.sendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mAadharNo = binding.edAadharNo.getText().toString();
                if (mAadharNo.isEmpty()) {
                    binding.edAadharNo.setError(getString(R.string.enter_aadhar_no));
                    binding.edAadharNo.requestFocus();
                    return;
                }
                if (mAadharNo.length() < 12) {
                    binding.edAadharNo.setError(getString(R.string.enter_12_digits_aadhar_no));
                    binding.edAadharNo.requestFocus();
                    return;
                }

                sendOtp(mAadharNo);
            }
        });

        binding.back.setOnClickListener(v -> finish());
    }

    private void sendOtp(String aadharNumber) {

    }
}