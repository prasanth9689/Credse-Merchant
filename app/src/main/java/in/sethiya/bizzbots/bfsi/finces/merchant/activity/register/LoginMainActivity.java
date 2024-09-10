package in.sethiya.bizzbots.bfsi.finces.merchant.activity.register;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import in.sethiya.bizzbots.bfsi.finces.merchant.R;
import in.sethiya.bizzbots.bfsi.finces.merchant.activity.SetupPasscodeActivity;
import in.sethiya.bizzbots.bfsi.finces.merchant.databinding.ActivityLoginMainBinding;

public class LoginMainActivity extends AppCompatActivity {
    private ActivityLoginMainBinding binding;
    private final Context context = this;
    private static int CODE_AUTHENTICATION_VERIFICATION = 241;

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

                KeyguardManager km = (KeyguardManager)getSystemService(KEYGUARD_SERVICE);
                if(km.isKeyguardSecure()) {

                    Intent i = km.createConfirmDeviceCredentialIntent("Authentication required", "password");
                    startActivityForResult(i, CODE_AUTHENTICATION_VERIFICATION);
                }else{
                    startActivity(new Intent(context, SetupPasscodeActivity.class));
                    finish();
                }


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode==CODE_AUTHENTICATION_VERIFICATION)
        {
            startActivity(new Intent(context, SetupPasscodeActivity.class));
            finish();
            Toast.makeText(this, "Success: Verified", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Failure: Unable to verify user's identity", Toast.LENGTH_SHORT).show();
        }
    }
}