package in.sethiya.bizzbots.bfsi.finces.merchant.activity.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONObject;

import in.sethiya.bizzbots.bfsi.finces.merchant.R;
import in.sethiya.bizzbots.bfsi.finces.merchant.activity.Home;
import in.sethiya.bizzbots.bfsi.finces.merchant.databinding.ActivityLogin2Binding;
import in.sethiya.bizzbots.bfsi.finces.merchant.retrofit.APIClient;
import in.sethiya.bizzbots.bfsi.finces.merchant.retrofit.APIInterface;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity2 extends AppCompatActivity {
    private ActivityLogin2Binding binding;
    private static final String TAG = "LoginActivity2";
    private final Context context = this;
    private String mMobile, mPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLogin2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        onClick();
    }

    private void onClick() {
        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMobile = binding.mobile.getText().toString().trim();
                mPassword = binding.password.getText().toString().trim();

                if ("".equals(mMobile) || mMobile.isEmpty()) {
                    binding.mobile.setError("Enter mobile no");
                    binding.mobile.requestFocus();
                    return;
                }
                if (mMobile.length() < 10) {
                    binding.mobile.setError(getString(R.string.mobile_no_must_10_digits));
                    binding.mobile.requestFocus();
                    return;
                }
                if (mPassword.isEmpty()) {
                    binding.password.setError("Enter mobile no");
                    binding.password.requestFocus();
                    return;
                }
                loginNow();
            }
        });

        binding.mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.errorCon.setVisibility(View.GONE);
                binding.error.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.errorCon.setVisibility(View.GONE);
                binding.error.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void loginNow() {
        showProgress();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("acc", "login")
                .addFormDataPart("mobile", mMobile)
                .addFormDataPart("password", mPassword)
                .build();

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<ResponseBody> call = apiInterface.login(requestBody);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        try {
                            dismissProgress();
                            String res =response.body().string();
                            JSONArray jsonArray = new JSONArray(res);
                            for (int i = 0; i<jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String status =jsonObject.getString("status");
                                if (status.equals("1")){
                                    Intent intent = new Intent(context, Home.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                    return;
                                }
                                String message = jsonObject.getString("message");
                                binding.errorCon.setVisibility(View.VISIBLE);
                                binding.error.setText(message);
                            }
                        } catch (Exception e) {
                            if (e.getMessage() != null) {
                                Log.e(TAG, e.getMessage());
                            }
                            showErrorToast();
                        }
                    }
                }else {
                    Log.e(TAG, "Retrofit null response!");
                    showErrorToast();
                    dismissProgress();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                if (t.getMessage() != null){
                    Log.e(TAG, t.getMessage());
                }
                showErrorToast();
            }
        });
    }

    private void showErrorToast() {
        dismissProgress();
        binding.errorCon.setVisibility(View.VISIBLE);
        binding.error.setText("Unable to login! try again.");
    }

    private void showProgress(){
        binding.loginText.setVisibility(View.GONE);
        binding.progressBar2.setVisibility(View.VISIBLE);
        binding.login.setEnabled(false);
        binding.login.setFocusable(false);
    }

    private void dismissProgress(){
        binding.loginText.setVisibility(View.VISIBLE);
        binding.progressBar2.setVisibility(View.GONE);
        binding.login.setEnabled(true);
        binding.login.setFocusable(true);
    }
}