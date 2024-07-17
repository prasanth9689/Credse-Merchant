package in.sethiya.bizzbots.bfsi.finces.merchant.activity.contacts;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import in.sethiya.bizzbots.bfsi.finces.merchant.helper.Utils;
import in.sethiya.bizzbots.bfsi.finces.merchant.R;
import in.sethiya.bizzbots.bfsi.finces.merchant.databinding.ActivityAddressDetailsBinding;

public class AddressDetailsActivity extends AppCompatActivity {
    private ActivityAddressDetailsBinding binding;
    private final Context context = this;
    private String mLivingType, mHouseType, mNameOfHome;
    private String mApartHouseNo, mApartLevelFloor, mApartBlockNo, mApartName;
    private String mHoseNo, mPropertyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddressDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        onClick();
    }

    private void onClick() {
        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInputs()) {
                    saveNow();
                }
            }
        });
    }

    private void saveNow() {

    }

    private boolean validateInputs() {
        mLivingType = binding.spinnerLivingType.getSelectedItem().toString();
        mHouseType = binding.spinnerHouseType.getSelectedItem().toString();
        mNameOfHome = binding.edNameOfHome.getText().toString();
        mApartHouseNo = binding.edApartHouseNo.getText().toString();

        if("Select".equals(mLivingType)){
            Utils.showMessageInSnackbar(context, "Please select living type");
            binding.txtErrorFound.setVisibility(View.VISIBLE);
            return false;
        }

        if("Select".equals(mHouseType)){
            Utils.showMessageInSnackbar(context, "Please select house type");
            binding.txtErrorFound.setVisibility(View.VISIBLE);
            return false;
        }
        if(mNameOfHome.isEmpty()){
           binding.edNameOfHome.requestFocus();
           binding.edNameOfHome.setError("Please enter name of home");
           binding.txtErrorFound.setVisibility(View.VISIBLE);
           return false;
        }
        if (mApartHouseNo.isEmpty()){
            binding.edApartHouseNo.requestFocus();
            binding.edApartHouseNo.setError("Please enter apart house no");
            binding.txtErrorFound.setVisibility(View.VISIBLE);
            return false;
        }
        return true;
    }
}