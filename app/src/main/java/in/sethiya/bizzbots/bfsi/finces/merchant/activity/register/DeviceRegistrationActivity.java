package in.sethiya.bizzbots.bfsi.finces.merchant.activity.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import in.sethiya.bizzbots.bfsi.finces.merchant.R;
import in.sethiya.bizzbots.bfsi.finces.merchant.databinding.ActivityDeviceRegistrationBinding;

public class DeviceRegistrationActivity extends AppCompatActivity {
    private ActivityDeviceRegistrationBinding binding;
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeviceRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        binding.edAccessToken.requestFocus();

        binding.submit.setOnClickListener(v -> {
            String mDeviceToken = binding.edAccessToken.getText().toString().trim();

            if (mDeviceToken.isEmpty()) {
                binding.edAccessToken.setError(getString(R.string.enter_access_token));
                binding.edAccessToken.requestFocus();
                return;
            }

            startActivity(new Intent(context, LoginMainActivity.class));
            finish();
        });

    }
}