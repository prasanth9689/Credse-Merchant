package in.sethiya.bizzbots.bfsi.finces.merchant.activity.contacts;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import in.sethiya.bizzbots.bfsi.finces.merchant.R;
import in.sethiya.bizzbots.bfsi.finces.merchant.databinding.ActivityContactsDetailsBinding;
import in.sethiya.bizzbots.bfsi.finces.merchant.helper.Utils;

public class ContactsDetailsActivity extends AppCompatActivity {
    private ActivityContactsDetailsBinding binding;
    private static final String TAG = "ContactsDetailsActivity";
    private final Context context= this;
    private String mPersonalMobileNo, mWhatsApp ="", mPersonalEmailId, mPrefLangCommun = "";
    private String mPrefCommunicationMode = "", mOTP;
    private String mResidenLandLine;
    private ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactsDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        list = new ArrayList<>();

        onClick();
        initSpinner();
    }

    private void initSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.language_array, R.layout.custom_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerPrefLang.setAdapter(adapter);
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

        binding.spinnerPrefLang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mPrefLangCommun = adapterView.getItemAtPosition(i).toString();
                if (!"Select".equals(mPrefLangCommun)){
                    binding.txtPrfLangNotValid.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.edOtpVerify.setVisibility(View.VISIBLE);
            }
        });

        binding.sms.setOnClickListener(v -> {
            CheckBox checkBox = (CheckBox) v;
            if (checkBox.isChecked()) {
                list.add("SMS");
            }else {
                binding.sms.setChecked(false);
                list.remove("SMS");
            }
        });

        binding.whatsapp.setOnClickListener(view -> {
            CheckBox checkBox = (CheckBox) view;
            if (checkBox.isChecked()) {
                list.add("WhatsApp");
            }else {
                binding.whatsapp.setChecked(false);
                list.remove("WhatsApp");
            }
        });

        binding.email.setOnClickListener(v -> {
            CheckBox checkBox = (CheckBox) v;
            if (checkBox.isChecked()) {
                list.add("Email");
            }else {
                binding.email.setChecked(false);
                list.remove("Email");
            }
        });

        binding.call.setOnClickListener(v -> {
            CheckBox checkBox = (CheckBox) v;
            if (checkBox.isChecked()) {
                list.add("Call");
            }else {
                binding.call.setChecked(false);
                list.remove("Call");
            }
        });

    }

    private void saveNow() {
        Set<String> s = new LinkedHashSet<>(list);
        String arrayList = s.toString();
      //  serviceIntent.putExtra("milk_supply_company", arrayList);
        Toast.makeText(context, "Valid", Toast.LENGTH_SHORT).show();
    }

    private boolean validateInputs() {
        mPersonalMobileNo = binding.edPersonalMobileNo.getText().toString().trim();
        mOTP = binding.edOtpVerify.getText().toString().trim();
        mResidenLandLine = binding.edResidentialLaNo.getText().toString();
        mPersonalEmailId = binding.edPersonalEmailId.getText().toString().trim();
        mPrefLangCommun = binding.spinnerPrefLang.getSelectedItem().toString();

        if (mPersonalMobileNo.isEmpty()) {
            binding.edPersonalMobileNo.setError(getString(R.string.enter_mobile_number));
            binding.edPersonalMobileNo.requestFocus();
            return false;
        }
        if (mWhatsApp.isEmpty()){
            Log.e(TAG, "Enter WhatsApp number");
        }
        if (mOTP.isEmpty()){
            binding.edOtpVerify.setError(getString(R.string.enter_otp));
            binding.edOtpVerify.requestFocus();
            return false;
        }
        if (mResidenLandLine.isEmpty()){
            binding.edResidentialLaNo.setError(getString(R.string.enter_residential_landline_number));
            binding.edResidentialLaNo.requestFocus();
            return false;
        }
        if (mPersonalEmailId.isEmpty()){
            binding.edPersonalEmailId.setError(getString(R.string.enter_email_id));
            binding.edPersonalEmailId.requestFocus();
            return false;
        }
        if ("Select".equals(mPrefLangCommun)){
            Utils.showMessageInSnackbar(context, getString(R.string.select_preferred_language));
            binding.txtPrfLangNotValid.setVisibility(View.VISIBLE);
            return false;
        }
        if (list.isEmpty()){
            Utils.showMessageInSnackbar(context, getString(R.string.select_preferred_communication_mode));
            return false;
        }
        return true;
    }
}