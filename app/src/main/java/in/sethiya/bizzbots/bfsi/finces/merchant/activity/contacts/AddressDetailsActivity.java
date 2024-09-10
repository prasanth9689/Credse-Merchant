package in.sethiya.bizzbots.bfsi.finces.merchant.activity.contacts;

import static android.view.View.GONE;
import static in.sethiya.bizzbots.bfsi.finces.merchant.AppConstants.MAS_GET_DISTRICTS;
import static in.sethiya.bizzbots.bfsi.finces.merchant.AppConstants.MAS_GET_STATES;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import in.sethiya.bizzbots.bfsi.finces.merchant.R;
import in.sethiya.bizzbots.bfsi.finces.merchant.adapter.SelectionAdapter;
import in.sethiya.bizzbots.bfsi.finces.merchant.databinding.ActivityAddressDetailsBinding;
import in.sethiya.bizzbots.bfsi.finces.merchant.helper.Utils;
import in.sethiya.bizzbots.bfsi.finces.merchant.model.States;
import in.sethiya.bizzbots.bfsi.finces.merchant.retrofit.APIClient;
import in.sethiya.bizzbots.bfsi.finces.merchant.retrofit.APIInterface;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressDetailsActivity extends AppCompatActivity implements SelectionAdapter.OnClickInterface {
    private ActivityAddressDetailsBinding binding;
    private final Context context = this;
    private String mLivingType, mHouseType, mNameOfHome;
    private String mApartHouseNo, mApartLevelFloor, mApartBlockNo, mApartName;
    private String mRawHouse;
    private String mHoseNo;
    private int mSelect = 0;
    private String mRawPropertyName, mDoorNo, mPlotNo, mStreetName;
    private String mColonyName, mLandmark, mAreaName, mSubDistrict;
    private String mState, mDistrict, mPropertyType, mIsPermanentAddr = "", mIsCommunicationAddr = "";
    private APIInterface apiInterface;
    private List<States> selectionsLists;
    private String mSelectedName;
    private String mSelectedCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddressDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        apiInterface = APIClient.getClient().create(APIInterface.class);
        selectionsLists = new ArrayList<>();

        binding.edState.setFocusable(false);
         binding.edDistrict.setFocusable(false);

        onClick();
        initSpinnerArray();
    }

    @Override
    public void onClickInterface(Intent intent) {
        String requestId = intent.getStringExtra("request_id");

        int mRequestId = Integer.parseInt(requestId);

        if (mRequestId == 0) {
            mSelectedName = intent.getStringExtra("selection_name");
            mSelectedCode = intent.getStringExtra("selection_code");
            binding.edState.setText(mSelectedName);
        }

        if (mRequestId == 1) {
            mSelectedName = intent.getStringExtra("selection_name");
            binding.edDistrict.setText(mSelectedName);
        }

        if (mSelect == 1){
            binding.scrollView1.setVisibility(View.VISIBLE);
            binding.title.setText("Agent creation");
            binding.selectionCon.setVisibility(View.GONE);
            mSelect = 0;
        }
    }

    private void intiTesting() {
        binding.spinnerLivingType.setSelection(1);
        binding.spinnerHouseType.setSelection(1);
        binding.edNameOfHome.setText("Test");
        binding.edApartHouseNo.setText("55");

        binding.spinnerApartLevelFloor.setSelection(1);
        binding.edApartBlockNo.setText("5");
        binding.edApartmentName.setText("Test");
        binding.edRawHouse.setText("Test");

        binding.edRawPropertyName.setText("Test");
        binding.edPlotNo.setText("Test");
        binding.edDoorNo.setText("Test");
        binding.edStreetName.setText("Test");

        binding.edColonyName.setText("Test");
        binding.edLandmarl.setText("Test");
        binding.edAreaNameLoca.setText("Test");
        binding.edSubDistrict.setText("Test");

    }

    private void initSpinnerArray() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.living_type_array, R.layout.custom_spinner);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        binding.spinnerLivingType.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.house_type_array, R.layout.custom_spinner);
        adapter2.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        binding.spinnerHouseType.setAdapter(adapter2);

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.floor_array, R.layout.custom_spinner);
        adapter3.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        binding.spinnerApartLevelFloor.setAdapter(adapter3);

        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this,
                R.array.property_type_array, R.layout.custom_spinner);
        adapter4.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        binding.spinnerPropertyType.setAdapter(adapter4);
    }

    private void onClick() {
        binding.save.setOnClickListener(view -> {
            if (validateInputs()) {
                saveNow();
            }
        });

        binding.spinnerLivingType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mLivingType = binding.spinnerLivingType.getSelectedItem().toString();

                if (!mLivingType.equals("Select")){
                    binding.txtErrorFound.setVisibility(GONE);
                    binding.txtLivingTyNotValid.setVisibility(GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.spinnerHouseType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mHouseType = binding.spinnerHouseType.getSelectedItem().toString();

                if (!mHouseType.equals("Select")){
                    binding.txtHouseTypeNotValid.setVisibility(GONE);
                    binding.txtErrorFound.setVisibility(GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.spinnerPropertyType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mPropertyType = binding.spinnerPropertyType.getSelectedItem().toString();

                if (!mHouseType.equals("Select")){
                    binding.txtPropertyTypeNotValid.setVisibility(GONE);
                    binding.txtErrorFound.setVisibility(GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        binding.edNameOfHome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.txtErrorFound.setVisibility(GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.edApartHouseNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.txtErrorFound.setVisibility(GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.spinnerApartLevelFloor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mApartLevelFloor = binding.spinnerApartLevelFloor.getSelectedItem().toString();

                if (!mApartLevelFloor.equals("Select")){
                    binding.txtLvlFloorNotValid.setVisibility(GONE);
                    binding.txtErrorFound.setVisibility(GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        binding.edApartBlockNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.txtErrorFound.setVisibility(GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.edApartmentName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.txtErrorFound.setVisibility(GONE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.edRawHouse.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.txtErrorFound.setVisibility(GONE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.edRawPropertyName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.txtErrorFound.setVisibility(GONE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        binding.edDoorNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.txtErrorFound.setVisibility(GONE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.edPlotNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.txtErrorFound.setVisibility(GONE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.edStreetName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.txtErrorFound.setVisibility(GONE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.edColonyName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.txtErrorFound.setVisibility(GONE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.edLandmarl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.txtErrorFound.setVisibility(GONE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        binding.edAreaNameLoca.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.txtErrorFound.setVisibility(GONE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.edSubDistrict.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.txtErrorFound.setVisibility(GONE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        binding.back.setOnClickListener(v -> finish());

        binding.edState.setOnClickListener(v -> {
            binding.title.setText("Select State");
            loadStates();
            binding.scrollView1.setVisibility(View.GONE);
            binding.selectionCon.setVisibility(View.VISIBLE);
            mSelect = 1;
        });

        binding.edState.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.txtErrorFound.setVisibility(View.GONE);
                binding.edState.setError(null);
                if (mSelectedCode != null && !mSelectedCode.isEmpty()){
                    loadDistricts(mSelectedCode);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        binding.edDistrict.setOnClickListener(v -> {
            binding.title.setText("Select District");
            //   loadDistricts(mSelectedCode);
            binding.scrollView1.setVisibility(View.GONE);
            binding.selectionCon.setVisibility(View.VISIBLE);
            mSelect = 1;

            String mState = binding.edState.getText().toString();

            if (mState.isEmpty()){
                binding.shimmerLayout.setVisibility(View.GONE);
                binding.stateNotSelectError.setVisibility(View.VISIBLE);
            }
        });

        binding.edDistrict.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.txtErrorFound.setVisibility(GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.defaultCurrent.setOnClickListener(v -> {
            binding.defaultPermanent.setChecked(false);
            mIsCommunicationAddr = "Current";
        });

        binding.defaultPermanent.setOnClickListener(v -> {
            binding.defaultCurrent.setChecked(false);
            mIsCommunicationAddr = "Permanent Address";
        });

    }

    private void loadDistricts(String mSelectedStateCode) {
        if (selectionsLists != null && !selectionsLists.isEmpty()){
            selectionsLists.clear();
        }

        Call<ResponseBody> call =
                apiInterface.getDistricts(MAS_GET_DISTRICTS, mSelectedStateCode);

       call.enqueue(new Callback<ResponseBody>() {
           @Override
           public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
               if (response.isSuccessful()){
                   binding.shimmerLayout.setVisibility(View.GONE);
                   String districtList = "";
                   try {
                       districtList = response.body().string();
                       JSONObject jsonObject = new JSONObject(districtList);
                       boolean mRecords = jsonObject.getBoolean("status");

                       if (mRecords){
                           JSONArray jsonArrayData = jsonObject.getJSONArray("data");
                           for (int i = 0; i < jsonArrayData.length(); i++) {
                               States states = new States();
                               JSONObject object = jsonArrayData.getJSONObject(i);
                               states.setSelectionCode(object.getString("Dist_code"));
                               states.setSelectionName(object.getString("Dist_name"));
                               selectionsLists.add(states);
                           }
                           LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                           linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                           binding.recyclerView.setLayoutManager(linearLayoutManager);
                           binding.recyclerView.setHasFixedSize(true);
                           binding.recyclerView.setItemViewCacheSize(20);
                           SelectionAdapter selectionAdapter = new SelectionAdapter(1, selectionsLists, context);
                           binding.recyclerView.setAdapter(selectionAdapter);
                           selectionAdapter.notifyDataSetChanged();
                       }
                   } catch (IOException | JSONException e) {
                       // throw new RuntimeException(e);
                       binding.shimmerLayout.setVisibility(View.GONE);
                       Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                   }
               }
           }

           @Override
           public void onFailure(Call<ResponseBody> call, Throwable t) {

           }
       });
    }

    private void saveNow() {

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        String currentTimeZone = new SimpleDateFormat("z", Locale.getDefault()).format(new Date());
        String timeZone = currentDate +" "+ currentTime +" "+ currentTimeZone;

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("axn", "addr_save")
                .addFormDataPart("live_type", mLivingType)
                .addFormDataPart("house_type", mHouseType)
                .addFormDataPart("name_of_home", mNameOfHome)
                .addFormDataPart("apart_house_no", mApartHouseNo)
                .addFormDataPart("apart_lel_floor", mApartLevelFloor)
                .addFormDataPart("apart_block_no", mApartBlockNo)
                .addFormDataPart("apart_name", mApartName)
                .addFormDataPart("raw_house", mRawHouse)
                .addFormDataPart("raw_property_name", mRawPropertyName)
                .addFormDataPart("door_no", mDoorNo)
                .addFormDataPart("plot_no", mPlotNo)
                .addFormDataPart("street_name", mStreetName)
                .addFormDataPart("colony_name", mColonyName)
                .addFormDataPart("landmark", mLandmark)
                .addFormDataPart("area_name", mAreaName)
                .addFormDataPart("sub_district", mSubDistrict)
                .addFormDataPart("state", mSubDistrict)
                .addFormDataPart("district", mSubDistrict)
                .addFormDataPart("property_type", mSubDistrict)
                .addFormDataPart("date", currentDate)
                .addFormDataPart("time", currentTime)
                .addFormDataPart("time_zone", currentTimeZone)
                .build();

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<ResponseBody> call = apiInterface.saveAddressDetails(requestBody);

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
                startActivity(new Intent(context, ContactsDetailsActivity.class));
                Toast.makeText(context, "Error!" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateInputs() {
        mLivingType = binding.spinnerLivingType.getSelectedItem().toString();
        mHouseType = binding.spinnerHouseType.getSelectedItem().toString();
        mNameOfHome = binding.edNameOfHome.getText().toString().trim();
        mApartHouseNo = binding.edApartHouseNo.getText().toString().trim();
        mApartLevelFloor = binding.spinnerApartLevelFloor.getSelectedItem().toString();
        mApartBlockNo = binding.edApartBlockNo.getText().toString().trim();
        mApartName = binding.edApartmentName.getText().toString().trim();
        mRawHouse = binding.edRawHouse.getText().toString().trim();
        mRawPropertyName = binding.edRawPropertyName.getText().toString().trim();
        mDoorNo = binding.edDoorNo.getText().toString().trim();
        mPlotNo = binding.edPlotNo.getText().toString().trim();
        mStreetName = binding.edStreetName.getText().toString().trim();
        mColonyName = binding.edColonyName.getText().toString().trim();
        mLandmark = binding.edLandmarl.getText().toString().trim();
        mAreaName = binding.edAreaNameLoca.getText().toString().trim();
        mSubDistrict = binding.edSubDistrict.getText().toString().trim();
        mState = binding.edState.getText().toString().trim();
        mDistrict = binding.edDistrict.getText().toString().trim();
        mPropertyType = binding.spinnerPropertyType.getSelectedItem().toString();

        if("Select".equals(mLivingType)){
            Utils.showMessageInSnackbar(context, getString(R.string.select_living_type));
            binding.txtLivingTyNotValid.setVisibility(View.VISIBLE);
            binding.txtErrorFound.setVisibility(View.VISIBLE);
            return false;
        }
        if("Select".equals(mHouseType)){
            Utils.showMessageInSnackbar(context, getString(R.string.select_house_type));
            binding.txtHouseTypeNotValid.setVisibility(View.VISIBLE);
            binding.txtErrorFound.setVisibility(View.VISIBLE);
            return false;
        }
          if(mNameOfHome.isEmpty()){
           binding.edNameOfHome.requestFocus();
           binding.edNameOfHome.setError(getString(R.string.enter_name_of_home));
           binding.txtErrorFound.setVisibility(View.VISIBLE);
           return false;
        }
        if (mApartHouseNo.isEmpty()){
            binding.edApartHouseNo.requestFocus();
            binding.edApartHouseNo.setError(getString(R.string.enter_apartment_house_no));
            binding.txtErrorFound.setVisibility(View.VISIBLE);
            return false;
        }
        if ("Select".equals(mApartLevelFloor)){
            Utils.showMessageInSnackbar(context, getString(R.string.select_floor));
            binding.txtLvlFloorNotValid.setVisibility(View.VISIBLE);
            binding.txtErrorFound.setVisibility(View.VISIBLE);
            return false;
        }
        if (mApartBlockNo.isEmpty()){
            binding.edApartBlockNo.requestFocus();
            binding.edApartBlockNo.setError(getString(R.string.enter_block_no));
            binding.txtErrorFound.setVisibility(View.VISIBLE);
            return false;
        }
        if (mApartName.isEmpty()){
            binding.edApartmentName.requestFocus();
            binding.edApartmentName.setError(getString(R.string.enter_apartment_name));
            binding.txtErrorFound.setVisibility(View.VISIBLE);
            return false;
        }
        if (mRawHouse.isEmpty()){
            binding.edRawHouse.requestFocus();
            binding.edRawHouse.setError(getString(R.string.enter_raw_hose));
            binding.txtErrorFound.setVisibility(View.VISIBLE);
            return false;
        }
        if (mRawPropertyName.isEmpty()){
            binding.edRawPropertyName.requestFocus();
            binding.edRawPropertyName.setError(getString(R.string.enter_raw_property_name));
            binding.txtErrorFound.setVisibility(View.VISIBLE);
            return false;
        }
        if (mDoorNo.isEmpty()){
            binding.edDoorNo.requestFocus();
            binding.edDoorNo.setError(getString(R.string.enter_door_no));
            binding.txtErrorFound.setVisibility(View.VISIBLE);
            return false;
        }
         if (mPlotNo.isEmpty()){
            binding.edPlotNo.requestFocus();
            binding.edPlotNo.setError(getString(R.string.enter_plot_no));
            binding.txtErrorFound.setVisibility(View.VISIBLE);
            return false;
        }
        if (mStreetName.isEmpty()){
            binding.edStreetName.requestFocus();
            binding.edStreetName.setError(getString(R.string.enter_street_no));
            binding.txtErrorFound.setVisibility(View.VISIBLE);
            return false;
        }
         if (mColonyName.isEmpty()){
            binding.edColonyName.requestFocus();
            binding.edColonyName.setError(getString(R.string.enter_colony_name));
            binding.txtErrorFound.setVisibility(View.VISIBLE);
            return false;
        }
         if (mLandmark.isEmpty()){
            binding.edLandmarl.requestFocus();
            binding.edLandmarl.setError(getString(R.string.enter_landmark));
            binding.txtErrorFound.setVisibility(View.VISIBLE);
            return false;
        }
         if (mAreaName.isEmpty()){
            binding.edAreaNameLoca.requestFocus();
            binding.edAreaNameLoca.setError(getString(R.string.enter_area_name));
            binding.txtErrorFound.setVisibility(View.VISIBLE);
            return false;
        }
         if (mSubDistrict.isEmpty()){
            binding.edSubDistrict.requestFocus();
            binding.edSubDistrict.setError(getString(R.string.enter_sub_district));
            binding.txtErrorFound.setVisibility(View.VISIBLE);
            return false;
        }
         if (mState.isEmpty()){
             binding.edState.requestFocus();
             binding.edState.setError(getString(R.string.select_state));
             binding.txtErrorFound.setVisibility(View.VISIBLE);
             return false;
         }
        if (mDistrict.isEmpty()){
            binding.edDistrict.requestFocus();
            binding.edDistrict.setError(getString(R.string.select_district));
            binding.txtErrorFound.setVisibility(View.VISIBLE);
            return false;
        }
        if ("Select".equals(mPropertyType)){
            Utils.showMessageInSnackbar(context, getString(R.string.select_property));
            binding.txtPropertyTypeNotValid.setVisibility(View.VISIBLE);
            binding.txtErrorFound.setVisibility(View.VISIBLE);
            return false;
        }
        if (mIsCommunicationAddr.isEmpty()){
            Utils.showMessageInSnackbar(context, getString(R.string.select_default_communication_address));
            binding.txtErrorFound.setVisibility(View.VISIBLE);
            return false;
        }
        return true;
    }

    private void loadStates() {
        Call<ResponseBody> call =
                apiInterface.getStates(MAS_GET_STATES);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    binding.shimmerLayout.setVisibility(View.GONE);
                    String stateList = "";

                    try {
                        stateList = response.body().string();
                        JSONObject jsonObject = new JSONObject(stateList);
                        boolean mRecords = jsonObject.getBoolean("status");

                        if (mRecords){
                            JSONArray jsonArrayData = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArrayData.length(); i++) {
                                States states = new States();
                                JSONObject object = jsonArrayData.getJSONObject(i);
                                states.setSelectionCode(object.getString("State_Code"));
                                states.setSelectionName(object.getString("StateName"));
                                selectionsLists.add(states);
                            }
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            binding.recyclerView.setLayoutManager(linearLayoutManager);
                            binding.recyclerView.setHasFixedSize(true);
                            binding.recyclerView.setItemViewCacheSize(20);
                            SelectionAdapter selectionAdapter = new SelectionAdapter(0,selectionsLists, context);
                            binding.recyclerView.setAdapter(selectionAdapter);
                            selectionAdapter.notifyDataSetChanged();
                        }
                    } catch (IOException | JSONException e) {
                        // throw new RuntimeException(e);
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
      //  super.onBackPressed();
        if (mSelect == 1) {
            binding.scrollView1.setVisibility(View.VISIBLE);
            binding.title.setText("Agent creation");
            binding.selectionCon.setVisibility(GONE);
            mSelect = 0;
            return;
        }
        finish();
    }
}