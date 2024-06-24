package in.sethiya.bizzbots.bfsi.finces.merchant.activity;

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
import in.sethiya.bizzbots.bfsi.finces.merchant.databinding.ActivityLoginBinding;
import in.sethiya.bizzbots.bfsi.finces.merchant.helper.session.SessionHandler;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = ActivityLoginBinding.inflate(getLayoutInflater());
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