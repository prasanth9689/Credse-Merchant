package in.sethiya.bizzbots.bfsi.finces.merchant.payments;

import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import in.sethiya.bizzbots.bfsi.finces.merchant.R;
import in.sethiya.bizzbots.bfsi.finces.merchant.databinding.ActivityUpiEnterPaymentBinding;

public class UpiEnterPaymentActivity extends AppCompatActivity {
    ActivityUpiEnterPaymentBinding binding;
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpiEnterPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.edAmount.setEnabled(true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        binding.edAmount.requestFocus();
        binding.edAmount.setFocusable(true);
    }
}