package in.sethiya.bizzbots.bfsi.finces.merchant.activity.contacts;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import in.sethiya.bizzbots.bfsi.finces.merchant.R;
import in.sethiya.bizzbots.bfsi.finces.merchant.databinding.ActivityRelCommDetailsBinding;
import in.sethiya.bizzbots.bfsi.finces.merchant.helper.Utils;

public class RelCommDetailsActivity extends AppCompatActivity {
    private ActivityRelCommDetailsBinding binding;
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        initSpinner();
        onClick();
    }

    private void onClick() {
        binding.save.setOnClickListener(view -> {
            if (validateInputs()) {
                saveNow();
            }
        });
    }

    private void saveNow() {
        Toast.makeText(context, "Valid", Toast.LENGTH_SHORT).show();
    }

    private boolean validateInputs() {
        String mReligion = binding.spinnerRrPract.getSelectedItem().toString();

        if ("Select".equals(mReligion)){
            Utils.showMessageInSnackbar(context, getString(R.string.select_religion_practice));
            return false;
        }
        return true;
    }

    private void initSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.rl_pract_array, R.layout.custom_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerRrPract.setAdapter(adapter);
    }
}