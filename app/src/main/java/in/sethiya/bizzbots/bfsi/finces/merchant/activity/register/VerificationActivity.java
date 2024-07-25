package in.sethiya.bizzbots.bfsi.finces.merchant.activity.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import in.sethiya.bizzbots.bfsi.finces.merchant.databinding.ActivityVerificationBinding;

public class VerificationActivity extends AppCompatActivity {
    private ActivityVerificationBinding binding;
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        settimer();

        onClick();
    }

    private void onClick() {

        binding.textView4.setOnClickListener(v -> {
            startActivity(new Intent(context, DeviceRegistrationActivity.class));
            finish();
        });

        binding.reSend.setOnClickListener(v -> {
            binding.reSend.setVisibility(View.GONE);
            binding.textTimer.setVisibility(View.VISIBLE);
            settimer();
        });

    }

    public void settimer() {
        binding.reSend.setVisibility(View.GONE);
        binding.textTimer.setVisibility(View.VISIBLE);
        new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                long remainedSecs = millisUntilFinished / 1000;
                binding.textTimer.setText("" + (remainedSecs / 60) + ":" + (remainedSecs % 60));
            }
            public void onFinish() {

                binding.reSend.setVisibility(View.VISIBLE);
                binding.textTimer.setVisibility(View.GONE);
            }
        }.start();
    }
}