package in.sethiya.bizzbots.bfsi.finces.merchant.activity.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import in.sethiya.bizzbots.bfsi.finces.merchant.databinding.ActivityDeviceRegistrationBinding;

public class DeviceRegistrationActivity extends AppCompatActivity {
    private ActivityDeviceRegistrationBinding binding;
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeviceRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.submit.setOnClickListener(v -> {
            startActivity(new Intent(context, LoginMainActivity.class));
            finish();
        });

    }
}