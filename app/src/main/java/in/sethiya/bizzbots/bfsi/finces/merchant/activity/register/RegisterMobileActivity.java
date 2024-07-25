package in.sethiya.bizzbots.bfsi.finces.merchant.activity.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

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

            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            binding.edMobile.requestFocus();

        onClick();
    }

    private void onClick() {
        binding.getOtp.setOnClickListener(v -> {

            String mMobileNo = binding.edMobile.getText().toString().trim();

            if (mMobileNo.isEmpty()) {
                binding.edMobile.setError(getString(R.string.enter_mobile_no));
                binding.edMobile.requestFocus();
                return;
            }
            if (mMobileNo.length() < 10) {
                binding.edMobile.setError(getString(R.string.mobile_no_must_10_digits));
                binding.edMobile.requestFocus();
                return;
            }
            startActivity(new Intent(context, VerificationActivity.class));
            finish();
        });
    }

}