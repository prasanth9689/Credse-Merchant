package in.sethiya.bizzbots.bfsi.finces.merchant.activity.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import in.sethiya.bizzbots.bfsi.finces.merchant.R;
import in.sethiya.bizzbots.bfsi.finces.merchant.databinding.ActivityAadhaarVerificationBinding;

public class AadhaarVerificationActivity extends AppCompatActivity {
    private ActivityAadhaarVerificationBinding binding;
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAadhaarVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.submit.setOnClickListener(v -> {
            startActivity(new Intent(context, ContactDetailsActivity.class));
            finish();
        });

        binding.back.setOnClickListener(v -> finish());
    }
}