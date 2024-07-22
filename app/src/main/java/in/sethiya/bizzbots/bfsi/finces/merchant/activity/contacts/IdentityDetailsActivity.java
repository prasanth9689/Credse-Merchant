package in.sethiya.bizzbots.bfsi.finces.merchant.activity.contacts;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import in.sethiya.bizzbots.bfsi.finces.merchant.R;
import in.sethiya.bizzbots.bfsi.finces.merchant.databinding.ActivityIdentityDetailsBinding;

public class IdentityDetailsActivity extends AppCompatActivity {
    private ActivityIdentityDetailsBinding binding;
    private final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIdentityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}