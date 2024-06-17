package in.sethiya.bizzbots.bfsi.finces.merchant.activity.register;

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
import in.sethiya.bizzbots.bfsi.finces.merchant.databinding.ActivityRegisterMobileBinding;

public class RegisterMobileActivity extends AppCompatActivity {
    private ActivityRegisterMobileBinding binding;
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterMobileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.getOtp.setOnClickListener(v -> {
            startActivity(new Intent(context, VerificationActivity.class));
            finish();
        });
    }
}