package in.sethiya.bizzbots.bfsi.finces.merchant.activity.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import in.sethiya.bizzbots.bfsi.finces.merchant.R;
import in.sethiya.bizzbots.bfsi.finces.merchant.activity.SetupPasscodeActivity;
import in.sethiya.bizzbots.bfsi.finces.merchant.databinding.ActivityLoginMainBinding;

public class LoginMainActivity extends AppCompatActivity {
    private ActivityLoginMainBinding binding;
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        binding.edPasscode.requestFocus();

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mPassCode = binding.edPasscode.getText().toString().trim();

                if (mPassCode.isEmpty()) {
                    binding.edPasscode.setError(getString(R.string.enter_passcode));
                    binding.edPasscode.requestFocus();
                    return;
                }

                startActivity(new Intent(context, SetupPasscodeActivity.class));
                finish();
            }
        });
    }
}