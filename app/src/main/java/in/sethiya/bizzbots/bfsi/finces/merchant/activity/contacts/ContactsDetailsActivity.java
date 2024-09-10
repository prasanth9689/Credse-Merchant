package in.sethiya.bizzbots.bfsi.finces.merchant.activity.contacts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

import in.sethiya.bizzbots.bfsi.finces.merchant.R;
import in.sethiya.bizzbots.bfsi.finces.merchant.databinding.ActivityContactsDetailsBinding;
import in.sethiya.bizzbots.bfsi.finces.merchant.helper.Utils;
import in.sethiya.bizzbots.bfsi.finces.merchant.retrofit.APIClient;
import in.sethiya.bizzbots.bfsi.finces.merchant.retrofit.APIInterface;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactsDetailsActivity extends AppCompatActivity {
    private ActivityContactsDetailsBinding binding;
    private static final String TAG = "ContactsDetailsActivity";
    private final Context context= this;
    private String mPersonalMobileNo, mWhatsApp ="", mPersonalEmailId, mPrefLangCommun = "";
    private String mPrefCommunicationMode = "", mOTP;
    private String mResidenLandLine;
    private ArrayList<String> list;
    private boolean isOtpVerified = false;

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
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        binding.spinnerPrefLang.setAdapter(adapter);
    }

    private void onClick() {
        binding.save.setOnClickListener(view -> {
            if (validateInputs()) {
                saveNow();
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

        binding.back.setOnClickListener(view -> finish());

        binding.verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.edOtpVerify.setVisibility(View.VISIBLE);
                binding.verify.setText("Verify");

                mOTP = binding.edOtpVerify.getText().toString();

                if (mOTP.isEmpty()){
                    binding.edOtpVerify.setError(getString(R.string.enter_otp));
                    binding.edOtpVerify.requestFocus();
                    return;
                }
                if (mOTP.length() < 6) {
                    binding.edOtpVerify.setError(getString(R.string.enter_6_digits_of_otp));
                    binding.edOtpVerify.requestFocus();
                    return;
                }
                isOtpVerified = true;
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                binding.edResidentialLaNo.requestFocus();
            }
        });

    }

    private void saveNow() {
        Set<String> s = new LinkedHashSet<>(list);
        String arrayList = s.toString();

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        String currentTimeZone = new SimpleDateFormat("z", Locale.getDefault()).format(new Date());
        String timeZone = currentDate +" "+ currentTime +" "+ currentTimeZone;

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("axn", "save_cont_details")
                .addFormDataPart("p_mobile", mPersonalMobileNo)
                .addFormDataPart("res_landline_no", mResidenLandLine)
                .addFormDataPart("p_email", mPersonalEmailId)
                .addFormDataPart("pref_lang", mPrefLangCommun)
                .addFormDataPart("date", currentDate)
                .addFormDataPart("time", currentTime)
                .addFormDataPart("time_zone", currentTimeZone)
                .build();

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<ResponseBody> call = apiInterface.saveContactDetails(requestBody);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    if (response.body() != null) {

                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                startActivity(new Intent(context, IdentityDetailsActivity.class));
                Toast.makeText(context, "Error!" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


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
        if (mPersonalMobileNo.length() < 10) {
            binding.edPersonalMobileNo.setError(getString(R.string.mobile_no_must_10_digits));
            binding.edPersonalMobileNo.requestFocus();
            return false;
        }
        if (mWhatsApp.isEmpty()){
            Log.e(TAG, "Enter WhatsApp number");
        }

        if (isOtpVerified){
            if (mResidenLandLine.isEmpty()){
                binding.edResidentialLaNo.setError(getString(R.string.enter_residential_landline_number));
                binding.edResidentialLaNo.requestFocus();
                return false;
            }
            if (mResidenLandLine.length() < 10) {
                binding.edResidentialLaNo.setError(getString(R.string.enter_10_digits_of_landline_no));
                binding.edResidentialLaNo.requestFocus();
                return false;
            }
            if (mPersonalEmailId.isEmpty()){
                binding.edPersonalEmailId.setError(getString(R.string.enter_email_id));
                binding.edPersonalEmailId.requestFocus();
                return false;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(mPersonalEmailId).matches()){
                binding.edPersonalEmailId.setError("Please enter valid email id");
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
        }else {
            Utils.showMessageInSnackbar(context, "Please Verify OTP");
            return false;
        }
        return true;
    }
}