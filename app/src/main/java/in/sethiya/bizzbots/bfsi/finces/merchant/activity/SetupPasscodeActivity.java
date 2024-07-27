package in.sethiya.bizzbots.bfsi.finces.merchant.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import in.sethiya.bizzbots.bfsi.finces.merchant.R;
import in.sethiya.bizzbots.bfsi.finces.merchant.databinding.ActivitySetupPasscodeBinding;

public class SetupPasscodeActivity extends AppCompatActivity {
    private ActivitySetupPasscodeBinding binding;
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = ActivitySetupPasscodeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        binding.edPasscode.requestFocus();


        binding.getOtp.setOnClickListener(view -> {
            String mPassCode = binding.edPasscode.getText().toString();
            if (mPassCode.isEmpty()) {
                binding.edPasscode.setError(getString(R.string.enter_pass_code));
                binding.edPasscode.requestFocus();
                return;
            }
            if (mPassCode.length() < 4) {
                binding.edPasscode.setError(getString(R.string.enter_4_digits_pass_code));
                binding.edPasscode.requestFocus();
                return;
            }
            startActivity(new Intent(context, Home.class));
            finish();
        });
    }
}