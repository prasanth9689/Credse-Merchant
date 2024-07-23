package in.sethiya.bizzbots.bfsi.finces.merchant.activity.contacts;

import static android.view.View.GONE;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import in.sethiya.bizzbots.bfsi.finces.merchant.helper.Utils;
import in.sethiya.bizzbots.bfsi.finces.merchant.R;
import in.sethiya.bizzbots.bfsi.finces.merchant.databinding.ActivityAddressDetailsBinding;

public class AddressDetailsActivity extends AppCompatActivity {
    private ActivityAddressDetailsBinding binding;
    private final Context context = this;
    private String mLivingType, mHouseType, mNameOfHome;
    private String mApartHouseNo, mApartLevelFloor, mApartBlockNo, mApartName;
    private String mRawHouse;
    private String mHoseNo;
    private String mRawPropertyName, mDoorNo, mPlotNo, mStreetName;
    private String mColonyName, mLandmark, mAreaName, mSubDistrict;
    private String mState, mDistrict, mPropertyType, mIsPermanentAddr = "", mIsCommunicationAddr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddressDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        onClick();
        initSpinnerArray();

     //   intiTesting();
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
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerLivingType.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.house_type_array, R.layout.custom_spinner);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerHouseType.setAdapter(adapter2);

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.floor_array, R.layout.custom_spinner);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerApartLevelFloor.setAdapter(adapter3);
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


        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void saveNow() {
        Toast.makeText(context, "Valid", Toast.LENGTH_SHORT).show();
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

//        mState = binding.spinnerState.getSelectedItem().toString();
//        mDistrict = binding.spinnerDistrict.getSelectedItem().toString();

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
//        if("Select".equals(mState)){
//            Utils.showMessageInSnackbar(context, getString(R.string.select_state));
//            binding.txtErrorFound.setVisibility(View.VISIBLE);
//            return false;
//        }
//        if("Select".equals(mDistrict)){
//            Utils.showMessageInSnackbar(context, getString(R.string.select_district));
//            binding.txtErrorFound.setVisibility(View.VISIBLE);
//            return false;
//        }
        if (mIsCommunicationAddr.isEmpty()){
            Utils.showMessageInSnackbar(context, getString(R.string.select_default_communication_address));
            binding.txtErrorFound.setVisibility(View.VISIBLE);
            return false;
        }
        return true;
    }
}