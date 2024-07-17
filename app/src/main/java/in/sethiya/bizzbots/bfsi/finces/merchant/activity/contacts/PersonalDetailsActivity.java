package in.sethiya.bizzbots.bfsi.finces.merchant.activity.contacts;

import static android.view.View.GONE;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import in.sethiya.bizzbots.bfsi.finces.merchant.CameraX;
import in.sethiya.bizzbots.bfsi.finces.merchant.R;
import in.sethiya.bizzbots.bfsi.finces.merchant.activity.ImageViewActivity;
import in.sethiya.bizzbots.bfsi.finces.merchant.databinding.ActivityPersonalDetailsBinding;
import in.sethiya.bizzbots.bfsi.finces.merchant.helper.Utils;

public class PersonalDetailsActivity extends AppCompatActivity {
    private ActivityPersonalDetailsBinding binding;
    private static final String TAG = "PersonalDetailsActivity";
    private final Context context = this;
    Bitmap bitmap;
    private String mTitle, mFirstName, mSurName, mFatherName, mMotherName, mGender, mDOB;
    private String mPlaceOfBirth, mCitizenship, mMaritalStatus, mDOA, mSpouseName;
    private Calendar calendar;
    private int year, month, day;
    private File file;

    int datePickerId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPersonalDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        binding.dob.setFocusable(false);
        binding.doa.setFocusable(false);

        file = new File(getExternalFilesDir(null), "/credse/" + "PROFILE_123.jpg");

        onClick();
        initSpinnerArray();
        deletePreviousData();
    }

    private void deletePreviousData() {
        if (file.exists()) {
            if (file.delete()) {
                Log.i(TAG, "File deleted");
            } else {
                Log.i(TAG, "File not deleted!");
            }
        }
    }

    public void setDate()
    {
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id)
    {
        // TODO Auto-generated method stub
        if (id == 999)
        {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = (arg0, arg1, arg2, arg3) -> {
        // TODO Auto-generated method stub
        // arg1 = year
        // arg2 = month
        // arg3 = day
        showDate(arg1, arg2+1, arg3);
    };

    private void showDate(int year, int month, int day)
    {
        String selectedDate = day+"/"+month+"/"+year;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");
        Date date;
        try {
            date = dateFormat.parse(selectedDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        if (datePickerId == 0){
            binding.dob.setText(dateFormat.format(date));
        }else {
            binding.doa.setText(dateFormat.format(date));
        }
    }

    private void initSpinnerArray() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender, R.layout.custom_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerGender.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.countries_array, R.layout.custom_spinner);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerCitizenship.setAdapter(adapter1);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.marital_status, R.layout.custom_spinner);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.mStatus.setAdapter(adapter2);

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.title_array, R.layout.custom_spinner);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.title.setAdapter(adapter3);
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

        binding.dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate();
            }
        });

        binding.doa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerId = 1;
                setDate();
            }
        });

        binding.profileImg.setOnClickListener(v -> {
            Bitmap bitmap1 = BitmapFactory.decodeFile(file.getAbsolutePath());
            if (bitmap1 != null){
                imageView();
                return;
            }

              Intent intent = new Intent(context, CameraX.class);
              intent.putExtra("event_name", "Profile picture");
              intent.putExtra("camera_id", "1");
              startActivity(intent);

        });

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CameraX.class);
                intent.putExtra("event_name", "Profile picture");
                intent.putExtra("camera_id", "1");
                startActivity(intent);
            }
        });

        binding.title.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mTitle = binding.title.getSelectedItem().toString();

                if (!mTitle.equals("Select")){
                    binding.txtErrorFound.setVisibility(GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.firstName.addTextChangedListener(new TextWatcher() {
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

        binding.surname.addTextChangedListener(new TextWatcher() {
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

        binding.fatherName.addTextChangedListener(new TextWatcher() {
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

        binding.motherName.addTextChangedListener(new TextWatcher() {
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

        binding.spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mGender = binding.spinnerGender.getSelectedItem().toString();

                if (!mGender.equals("Select")){
                    binding.txtErrorFound.setVisibility(GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.dob.addTextChangedListener(new TextWatcher() {
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

        binding.plOfBirth.addTextChangedListener(new TextWatcher() {
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

        binding.spinnerCitizenship.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCitizenship = binding.spinnerCitizenship.getSelectedItem().toString();

                if (!mCitizenship.equals("Select")){
                    binding.txtErrorFound.setVisibility(GONE);
                    binding.txtCitizenNotValid.setVisibility(GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.mStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mMaritalStatus = binding.mStatus.getSelectedItem().toString();

                if (!mMaritalStatus.equals("Select")){
                    binding.txtErrorFound.setVisibility(GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.dob.addTextChangedListener(new TextWatcher() {
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

        binding.spouse.addTextChangedListener(new TextWatcher() {
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
    }

    private void saveNow() {
        Toast.makeText(context, "Valid", Toast.LENGTH_SHORT).show();
    }

    private boolean validateInputs() {
        mTitle = binding.title.getSelectedItem().toString();
        mFirstName = binding.firstName.getText().toString();
        mSurName = binding.surname.getText().toString();
        mFatherName = binding.fatherName.getText().toString();
        mMotherName = binding.motherName.getText().toString();
        mGender = binding.spinnerGender.getSelectedItem().toString();
        mDOB = binding.dob.getText().toString();
        mPlaceOfBirth = binding.plOfBirth.getText().toString();
        mCitizenship = binding.spinnerCitizenship.getSelectedItem().toString();
        mMaritalStatus = binding.mStatus.getSelectedItem().toString();
        mDOA = binding.doa.getText().toString();
        mSpouseName = binding.spouse.getText().toString();

        if (bitmap == null){
            Utils.showMessageInSnackbar(context, "Please take a picture");
            binding.txtErrorFound.setVisibility(View.VISIBLE);
            return false;
        }
        if ("Select".equals(mTitle)){
            Utils.showMessageInSnackbar(context, "Please select title");
            binding.title.requestFocus();
            binding.txtErrorFound.setVisibility(View.VISIBLE);
            return false;
        }
        if ("".equals(mFirstName)){
            binding.firstName.setError("Enter First Name");
            binding.firstName.requestFocus();
            binding.txtErrorFound.setVisibility(View.VISIBLE);
            return false;
        }
        if("".equals(mSurName)){
            binding.surname.setError("Enter Surname");
            binding.surname.requestFocus();
            binding.txtErrorFound.setVisibility(View.VISIBLE);
            return false;
        }
        if("".equals(mFatherName)) {
            binding.fatherName.setError("Enter Father Name");
            binding.fatherName.requestFocus();
            binding.txtErrorFound.setVisibility(View.VISIBLE);
            return false;
        }
        if("".equals(mMotherName)) {
            binding.motherName.setError("Enter Mother Name");
            binding.motherName.requestFocus();
            binding.txtErrorFound.setVisibility(View.VISIBLE);
            return false;
        }
        if("Select".equals(mGender)) {
            binding.spinnerGender.requestFocus();
            Utils.showMessageInSnackbar(context, "Please select gender");
            binding.txtErrorFound.setVisibility(View.VISIBLE);
            return false;
        }
        if("".equals(mDOB)) {
            Utils.showMessageInSnackbar(context, "Select Date of birth");
            binding.txtErrorFound.setVisibility(View.VISIBLE);
            return false;
        }
        if("".equals(mPlaceOfBirth)) {
            binding.plOfBirth.setError("Enter Place of Birth");
            binding.plOfBirth.requestFocus();
            binding.txtErrorFound.setVisibility(View.VISIBLE);
            return false;
        }
        if("Select".equals(mCitizenship)) {
            binding.spinnerCitizenship.getSelectedView().requestFocus();
            binding.txtCitizenNotValid.setVisibility(View.VISIBLE);
            binding.txtErrorFound.setVisibility(View.VISIBLE);
            return false;
        }
        if("Select".equals(mMaritalStatus)){
            binding.mStatus.getSelectedView().requestFocus();
            binding.txtErrorFound.setVisibility(View.VISIBLE);
            Utils.showMessageInSnackbar(context, "Select marital status");
            return false;
        }
        if("".equals(mDOA)) {
            binding.doa.setError("Enter DOA");
            binding.txtErrorFound.setVisibility(View.VISIBLE);
            return false;
        }
        if("".equals(mSpouseName)) {
            binding.spouse.setError("Enter Spouse Name");
            binding.spouse.requestFocus();
            binding.txtErrorFound.setVisibility(View.VISIBLE);
            return false;
        }
        return true;
    }

    private void imageView() {
        String imagePath = getExternalFilesDir("/").getPath() + "/" + "credse/" + "PROFILE_123.jpg";

        Intent intent = new Intent(context, ImageViewActivity.class);
        intent.putExtra("uri", imagePath);
        intent.putExtra("event_name", "Profile Picture");
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

        if (bitmap != null){

            binding.txtErrorFound.setVisibility(GONE);

            Glide
                    .with(context)
                    .load(bitmap)
                    .apply(RequestOptions.circleCropTransform())
                    .placeholder(R.drawable.placeholder_person)
                    .into(binding.profileImage);

        }
    }
}