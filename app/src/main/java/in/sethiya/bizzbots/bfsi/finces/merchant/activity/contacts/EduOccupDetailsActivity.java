package in.sethiya.bizzbots.bfsi.finces.merchant.activity.contacts;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import in.sethiya.bizzbots.bfsi.finces.merchant.R;
import in.sethiya.bizzbots.bfsi.finces.merchant.databinding.ActivityEduOccupDetailsBinding;

public class EduOccupDetailsActivity extends AppCompatActivity {
    private ActivityEduOccupDetailsBinding binding;
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEduOccupDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}