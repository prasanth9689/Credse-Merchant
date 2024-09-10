package in.sethiya.bizzbots.bfsi.finces.merchant.activity.contacts;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import in.sethiya.bizzbots.bfsi.finces.merchant.CameraX;
import in.sethiya.bizzbots.bfsi.finces.merchant.R;
import in.sethiya.bizzbots.bfsi.finces.merchant.activity.ImageViewActivity;
import in.sethiya.bizzbots.bfsi.finces.merchant.databinding.ActivityEduOccupDetailsBinding;
import in.sethiya.bizzbots.bfsi.finces.merchant.helper.Utils;
import in.sethiya.bizzbots.bfsi.finces.merchant.retrofit.APIClient;
import in.sethiya.bizzbots.bfsi.finces.merchant.retrofit.APIInterface;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EduOccupDetailsActivity extends AppCompatActivity {
    private ActivityEduOccupDetailsBinding binding;
    private final Context context = this;
    private static final String EDUCATION_FILE_NAME = "EDUPROOF_123.jpg";
    private File file;
    private Bitmap bitmap;
    private int occupationSelectedId;
    private int OCCUPATION_SALARY = 1;
    private int OCCUPATION_SELF_EMPLOYED = 2;
    private int OCCUPATION_OTHER = 3;
    private String mEducation, mOccupation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEduOccupDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        onClick();
        visibleGone();
        initSpinner();
    }

    private void visibleGone() {
        // Salaried with employed container
        binding.salariedEmployedWithContainer.setVisibility(GONE);
        binding.salariedEmployedWithSpinnerSelectionContainer.setVisibility(GONE);
        binding.salariedEmployedWithCompanyContainer.setVisibility(GONE);

        // For occupation self employed
        binding.selfEmployedSelectionContainer.setVisibility(GONE);
        binding.selfEmployedBusinessContainer.setVisibility(GONE);
        binding.selfEmployedProfessionContainer.setVisibility(GONE);

        binding.selfEmpBusinessOtherContainer.setVisibility(GONE);
        binding.selfEmpBusinessDescriptionContainer.setVisibility(GONE);

        // occupation profession
        binding.selfEmpProfessionOtherContainer.setVisibility(GONE);
        binding.selfEmpProfessionDescriptionContainer.setVisibility(GONE);

        // Income proof selection
        binding.incomeProofSelectionContainer.setVisibility(GONE);
        binding.incomeSrcOtherContainer.setVisibility(GONE);

        binding.incomeSrcOtherContainer2.setVisibility(GONE);
    }

    private void onClick() {
        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (validateInputs()) {
//                    saveNow();
//                }

                saveNow();
            }
        });

        binding.cardEducationProof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap1 = BitmapFactory.decodeFile(file.getAbsolutePath());
                if (bitmap1 != null){
                    imageView(EDUCATION_FILE_NAME, getString(R.string.education_proof));
                    return;
                }
                captureEvent();
            }
        });

        binding.checkboxSelfEmpProfessional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBox = (CheckBox) view;
                if (checkBox.isChecked()) {
                    // Main container
                    binding.selfEmployedProfessionContainer.setVisibility(VISIBLE);

                    // Visible description
                    binding.selfEmpProfessionDescriptionContainer.setVisibility(VISIBLE);

                    // Other spinner's
                    binding.selfEmpProfessionOtherContainer.setVisibility(GONE);

                    // To hide business container
                    binding.selfEmployedBusinessContainer.setVisibility(GONE);
                    binding.selfEmpBusinessOtherContainer.setVisibility(GONE);
                    binding.selfEmpBusinessDescriptionContainer.setVisibility(GONE);

                    binding.checkboxSelfEmpBusiness.setChecked(false);
                    binding.checkboxOthersBusiness.setChecked(false);
                    binding.checkboxManufacture.setChecked(false);
                    binding.checkboxTrade.setChecked(false);
                    binding.checkboxRetail.setChecked(false);
                    binding.checkboxService.setChecked(false);
                    binding.checkboxRd.setChecked(false);
                    binding.checkboxRecycling.setChecked(false);
                }else {
                    binding.checkboxSelfEmpProfessional.setChecked(false);
                    binding.selfEmployedProfessionContainer.setVisibility(GONE);
                    binding.selfEmployedBusinessContainer.setVisibility(GONE);
                }
            }
        });

        binding.checkboxSelfEmpBusiness.setOnClickListener(view -> {
            CheckBox checkBox = (CheckBox) view;
            if (checkBox.isChecked()) {
                binding.selfEmployedBusinessContainer.setVisibility(VISIBLE);
                binding.selfEmployedProfessionContainer.setVisibility(GONE);
                binding.checkboxSelfEmpProfessional.setChecked(false);
                binding.checkboxCaCs.setChecked(false);
                binding.checkboxDoctor.setChecked(false);
                binding.checkboxLawyer.setChecked(false);
                binding.checkboxArchitect.setChecked(false);
                binding.checkboxPlumber.setChecked(false);
                binding.checkboxElectrician.setChecked(false);
                binding.checkboxCarpenter.setChecked(false);
                binding.checkboxTailor.setChecked(false);
                binding.checkboxOthersProfession.setChecked(false);

                // As per documentation
                binding.selfEmpBusinessDescriptionContainer.setVisibility(VISIBLE);
            }else {
                binding.checkboxSelfEmpBusiness.setChecked(false);
                binding.selfEmployedBusinessContainer.setVisibility(GONE);
                binding.selfEmployedProfessionContainer.setVisibility(GONE);
            }

        });

//        binding.checkboxSelfEmpProfessional.setOnClickListener(view -> {
//            CheckBox checkBox = (CheckBox) view;
//            if (checkBox.isChecked()) {
//                binding.selfEmployedProfessionContainer.setVisibility(View.VISIBLE);
//                binding.selfEmployedBusinessContainer.setVisibility(GONE);
//                binding.checkboxSelfEmpBusiness.setChecked(false);
//            }else {
//                binding.checkboxSelfEmpProfessional.setChecked(false);
//                binding.selfEmployedBusinessContainer.setVisibility(GONE);
//                binding.selfEmployedProfessionContainer.setVisibility(GONE);
//            }
//
//        });

        binding.checkboxOthersProfession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBox = (CheckBox) view;

                if (checkBox.isChecked()) {
                    Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
                    binding.selfEmpProfessionOtherContainer.setVisibility(VISIBLE);
                    binding.selfEmpProfessionDescriptionContainer.setVisibility(View.GONE);

                    binding.checkboxCaCs.setChecked(false);
                    binding.checkboxDoctor.setChecked(false);
                    binding.checkboxLawyer.setChecked(false);
                    binding.checkboxArchitect.setChecked(false);
                    binding.checkboxPlumber.setChecked(false);
                    binding.checkboxElectrician.setChecked(false);
                    binding.checkboxCarpenter.setChecked(false);
                    binding.checkboxTailor.setChecked(false);
                }else {
                    Toast.makeText(context, " De Clicked", Toast.LENGTH_SHORT).show();
                    binding.checkboxOthersProfession.setChecked(false);
                    binding.selfEmpBusinessOtherContainer.setVisibility(GONE);
                    binding.selfEmpProfessionOtherContainer.setVisibility(GONE);
                }
            }
        });

        binding.checkboxCaCs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBox = (CheckBox) view;

                if (checkBox.isChecked()) {
                    binding.selfEmpProfessionDescriptionContainer.setVisibility(VISIBLE);
                    binding.selfEmpProfessionOtherContainer.setVisibility(View.GONE);
                    binding.checkboxOthersProfession.setChecked(false);
                }else {
                    binding.checkboxCaCs.setChecked(false);
                    binding.selfEmpProfessionDescriptionContainer.setVisibility(View.GONE);
                    binding.selfEmpProfessionOtherContainer.setVisibility(View.GONE);
                }
            }
        });

        binding.checkboxDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBox = (CheckBox) view;

                if (checkBox.isChecked()) {
                    binding.selfEmpProfessionDescriptionContainer.setVisibility(VISIBLE);
                    binding.selfEmpProfessionOtherContainer.setVisibility(View.GONE);
                    binding.checkboxOthersProfession.setChecked(false);
                }else {
                    binding.checkboxDoctor.setChecked(false);
                    binding.selfEmpProfessionDescriptionContainer.setVisibility(View.GONE);
                    binding.selfEmpProfessionOtherContainer.setVisibility(View.GONE);
                }
            }
        });

        binding.checkboxLawyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBox = (CheckBox) view;

                if (checkBox.isChecked()) {
                    binding.selfEmpProfessionDescriptionContainer.setVisibility(VISIBLE);
                    binding.selfEmpProfessionOtherContainer.setVisibility(View.GONE);
                    binding.checkboxOthersProfession.setChecked(false);
                }else {
                    binding.checkboxLawyer.setChecked(false);
                    binding.selfEmpProfessionDescriptionContainer.setVisibility(View.GONE);
                    binding.selfEmpProfessionOtherContainer.setVisibility(View.GONE);
                }
            }
        });

        binding.checkboxArchitect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBox = (CheckBox) view;

                if (checkBox.isChecked()) {
                    binding.selfEmpProfessionDescriptionContainer.setVisibility(VISIBLE);
                    binding.selfEmpProfessionOtherContainer.setVisibility(View.GONE);
                    binding.checkboxOthersProfession.setChecked(false);
                }else {
                    binding.checkboxArchitect.setChecked(false);
                    binding.selfEmpProfessionDescriptionContainer.setVisibility(View.GONE);
                    binding.selfEmpProfessionOtherContainer.setVisibility(View.GONE);
                }
            }
        });

        binding.checkboxPlumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBox = (CheckBox) view;

                if (checkBox.isChecked()) {
                    binding.selfEmpProfessionDescriptionContainer.setVisibility(VISIBLE);
                    binding.selfEmpProfessionOtherContainer.setVisibility(View.GONE);
                    binding.checkboxOthersProfession.setChecked(false);
                }else {
                    binding.checkboxPlumber.setChecked(false);
                    binding.selfEmpProfessionDescriptionContainer.setVisibility(View.GONE);
                    binding.selfEmpProfessionOtherContainer.setVisibility(View.GONE);
                }
            }
        });

        binding.checkboxElectrician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBox = (CheckBox) view;

                if (checkBox.isChecked()) {
                    binding.selfEmpProfessionDescriptionContainer.setVisibility(VISIBLE);
                    binding.selfEmpProfessionOtherContainer.setVisibility(View.GONE);
                    binding.checkboxOthersProfession.setChecked(false);
                }else {
                    binding.checkboxElectrician.setChecked(false);
                    binding.selfEmpProfessionDescriptionContainer.setVisibility(View.GONE);
                    binding.selfEmpProfessionOtherContainer.setVisibility(View.GONE);
                }
            }
        });

        binding.checkboxCarpenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBox = (CheckBox) view;

                if (checkBox.isChecked()) {
                    binding.selfEmpProfessionDescriptionContainer.setVisibility(VISIBLE);
                    binding.selfEmpProfessionOtherContainer.setVisibility(View.GONE);
                    binding.checkboxOthersProfession.setChecked(false);
                }else {
                    binding.checkboxCarpenter.setChecked(false);
                    binding.selfEmpProfessionDescriptionContainer.setVisibility(View.GONE);
                    binding.selfEmpProfessionOtherContainer.setVisibility(View.GONE);
                }
            }
        });

        binding.checkboxTailor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBox = (CheckBox) view;

                if (checkBox.isChecked()) {
                    binding.selfEmpProfessionDescriptionContainer.setVisibility(VISIBLE);
                    binding.selfEmpProfessionOtherContainer.setVisibility(View.GONE);
                    binding.checkboxOthersProfession.setChecked(false);
                }else {
                    binding.checkboxTailor.setChecked(false);
                    binding.selfEmpProfessionDescriptionContainer.setVisibility(View.GONE);
                    binding.selfEmpProfessionOtherContainer.setVisibility(View.GONE);
                }
            }
        });

        binding.checkboxOthersBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBox = (CheckBox) view;

                if (checkBox.isChecked()) {
                    binding.selfEmpBusinessDescriptionContainer.setVisibility(GONE);
                    binding.selfEmpBusinessOtherContainer.setVisibility(VISIBLE);

                    binding.checkboxManufacture.setChecked(false);
                    binding.checkboxTrade.setChecked(false);
                    binding.checkboxRetail.setChecked(false);
                    binding.checkboxService.setChecked(false);
                    binding.checkboxRd.setChecked(false);
                    binding.checkboxRecycling.setChecked(false);
                }else {
                    binding.checkboxOthersBusiness.setChecked(false);
                    binding.selfEmpBusinessOtherContainer.setVisibility(GONE);
                }

            }
        });

        binding.checkboxManufacture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBox = (CheckBox) view;

                if (checkBox.isChecked()) {
                    binding.selfEmpBusinessDescriptionContainer.setVisibility(VISIBLE);
                    binding.selfEmpBusinessOtherContainer.setVisibility(View.GONE);
                    binding.checkboxOthersBusiness.setChecked(false);
                }else {
                    binding.checkboxManufacture.setChecked(false);
                    binding.selfEmpBusinessOtherContainer.setVisibility(View.GONE);
                }
            }
        });

        binding.checkboxTrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBox = (CheckBox) view;

                if (checkBox.isChecked()) {
                    binding.selfEmpBusinessDescriptionContainer.setVisibility(VISIBLE);
                    binding.selfEmpBusinessOtherContainer.setVisibility(View.GONE);
                    binding.checkboxOthersBusiness.setChecked(false);
                }else {
                    binding.checkboxTrade.setChecked(false);
                    binding.selfEmpBusinessOtherContainer.setVisibility(View.GONE);
                }
            }
        });

        binding.checkboxRetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBox = (CheckBox) view;

                if (checkBox.isChecked()) {
                    binding.selfEmpBusinessDescriptionContainer.setVisibility(VISIBLE);
                    binding.selfEmpBusinessOtherContainer.setVisibility(View.GONE);
                    binding.checkboxOthersBusiness.setChecked(false);
                }else {
                    binding.checkboxRetail.setChecked(false);
                    binding.selfEmpBusinessOtherContainer.setVisibility(View.GONE);
                }
            }
        });

        binding.checkboxService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBox = (CheckBox) view;

                if (checkBox.isChecked()) {
                    binding.selfEmpBusinessDescriptionContainer.setVisibility(VISIBLE);
                    binding.selfEmpBusinessOtherContainer.setVisibility(View.GONE);
                    binding.checkboxOthersBusiness.setChecked(false);
                }else {
                    binding.checkboxService.setChecked(false);
                    binding.selfEmpBusinessOtherContainer.setVisibility(View.GONE);
                }
            }
        });

        binding.checkboxRd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBox = (CheckBox) view;

                if (checkBox.isChecked()) {
                    binding.selfEmpBusinessDescriptionContainer.setVisibility(VISIBLE);
                    binding.selfEmpBusinessOtherContainer.setVisibility(View.GONE);
                    binding.checkboxOthersBusiness.setChecked(false);
                }else {
                    binding.checkboxRd.setChecked(false);
                    binding.selfEmpBusinessOtherContainer.setVisibility(View.GONE);
                }
            }
        });

        binding.checkboxRecycling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBox = (CheckBox) view;

                if (checkBox.isChecked()) {
                    binding.selfEmpBusinessDescriptionContainer.setVisibility(VISIBLE);
                    binding.selfEmpBusinessOtherContainer.setVisibility(View.GONE);
                    binding.checkboxOthersBusiness.setChecked(false);
                }else {
                    binding.checkboxRecycling.setChecked(false);
                    binding.selfEmpBusinessOtherContainer.setVisibility(View.GONE);
                }
            }
        });


        binding.reTakeEduProof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureEvent();
            }
        });

        binding.spinnerOccupation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               // Toast.makeText(context, String.valueOf(i), Toast.LENGTH_SHORT).show();

                switch (i){
                    /*
                       1 -  Salaried
                     */
                    case 1:
                        occupationSelectedId = 1;
                        Toast.makeText(context, "Salaried", Toast.LENGTH_SHORT).show();
                        binding.salariedEmployedWithContainer.setVisibility(VISIBLE);
                        binding.salariedEmployedWithSpinnerSelectionContainer.setVisibility(VISIBLE);
                        binding.salariedEmployedWithCompanyContainer.setVisibility(VISIBLE);

                        // To hide self employed container's
                        binding.selfEmployedSelectionContainer.setVisibility(GONE);
                        binding.selfEmployedBusinessContainer.setVisibility(GONE);
                        binding.selfEmployedProfessionContainer.setVisibility(GONE);
                        break;

                    case 2:
                        occupationSelectedId = 2;
                        binding.selfEmployedSelectionContainer.setVisibility(VISIBLE);

                        binding.salariedEmployedWithContainer.setVisibility(GONE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.salariedEmployedWithSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 9:
                        binding.salariedEmployedWithOtherContainer.setVisibility(VISIBLE);
                        binding.salariedEmployedWithCompanyContainer.setVisibility(GONE);
                        break;

                    case 8:

                    case 7:

                    case 6:

                    case 5:

                    case 4:

                    case 3:

                    case 2:

                    case 1:
                        binding.salariedEmployedWithCompanyContainer.setVisibility(VISIBLE);
                        binding.salariedEmployedWithOtherContainer.setVisibility(GONE);
                        break;

                    case 0:
                        binding.salariedEmployedWithOtherContainer.setVisibility(GONE);
                        binding.salariedEmployedWithCompanyContainer.setVisibility(GONE);
                        break;

                        default:

                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.checkboxSrcIncomeBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBox = (CheckBox) view;

                if (checkBox.isChecked()) {
                    binding.incomeProofSelectionContainer.setVisibility(VISIBLE);

                    binding.incomeSrcOtherContainer.setVisibility(View.GONE);
                    binding.checkboxSrcIncomeOthers.setChecked(false);
                }else {
                    binding.checkboxSrcIncomeBusiness.setChecked(false);
                    binding.checkboxSrcIncomeOthers.setChecked(false);

                    binding.incomeSrcOtherContainer.setVisibility(GONE);
                }
            }
        });

        binding.checkboxSrcIncomeSalary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBox = (CheckBox) view;

                if (checkBox.isChecked()) {
                    binding.incomeProofSelectionContainer.setVisibility(VISIBLE);

                    binding.incomeSrcOtherContainer.setVisibility(View.GONE);
                    binding.checkboxSrcIncomeOthers.setChecked(false);
                } else {
                    binding.checkboxSrcIncomeSalary.setChecked(false);
                    binding.checkboxSrcIncomeOthers.setChecked(false);

                    binding.incomeSrcOtherContainer.setVisibility(GONE);
                }
            }
        });

        binding.checkboxSrcIncomeInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBox = (CheckBox) view;

                if (checkBox.isChecked()) {
                    binding.incomeProofSelectionContainer.setVisibility(VISIBLE);

                    binding.incomeSrcOtherContainer.setVisibility(View.GONE);
                    binding.checkboxSrcIncomeOthers.setChecked(false);
                } else {
                    binding.checkboxSrcIncomeInterest.setChecked(false);
                    binding.checkboxSrcIncomeOthers.setChecked(false);
                    binding.incomeSrcOtherContainer.setVisibility(GONE);
                }
            }
        });

        binding.checkboxSrcIncomeRoyality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBox = (CheckBox) view;

                if (checkBox.isChecked()) {
                    binding.incomeProofSelectionContainer.setVisibility(VISIBLE);

                    binding.incomeSrcOtherContainer.setVisibility(View.GONE);
                    binding.checkboxSrcIncomeOthers.setChecked(false);
                }else {
                    binding.checkboxSrcIncomeRoyality.setChecked(false);
                    binding.checkboxSrcIncomeOthers.setChecked(false);

                    binding.incomeSrcOtherContainer.setVisibility(GONE);
                }
            }
        });

        binding.checkboxSrcIncomeInvestments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBox = (CheckBox) view;

                if (checkBox.isChecked()) {
                    binding.incomeProofSelectionContainer.setVisibility(VISIBLE);

                    binding.incomeSrcOtherContainer.setVisibility(View.GONE);
                    binding.checkboxSrcIncomeOthers.setChecked(false);
                }else {
                    binding.checkboxSrcIncomeInvestments.setChecked(false);
                    binding.checkboxSrcIncomeOthers.setChecked(false);

                    binding.incomeSrcOtherContainer.setVisibility(GONE);
                }
            }
        });

        binding.checkboxSrcIncomePension.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBox = (CheckBox) view;

                if (checkBox.isChecked()) {
                    binding.incomeProofSelectionContainer.setVisibility(VISIBLE);

                    binding.incomeSrcOtherContainer.setVisibility(View.GONE);
                    binding.checkboxSrcIncomeOthers.setChecked(false);
                }else {
                    binding.checkboxSrcIncomePension.setChecked(false);
                    binding.checkboxSrcIncomeOthers.setChecked(false);

                    binding.incomeSrcOtherContainer.setVisibility(GONE);
                }
            }
        });

        binding.checkboxSrcIncomeOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBox = (CheckBox) view;

                if (checkBox.isChecked()) {
                    binding.incomeSrcOtherContainer.setVisibility(VISIBLE);

                    binding.incomeProofSelectionContainer.setVisibility(View.GONE);

                    binding.checkboxSrcIncomeBusiness.setChecked(false);
                    binding.checkboxSrcIncomeSalary.setChecked(false);
                    binding.checkboxSrcIncomeInterest.setChecked(false);
                    binding.checkboxSrcIncomeRoyality.setChecked(false);
                    binding.checkboxSrcIncomeInvestments.setChecked(false);
                    binding.checkboxSrcIncomePension.setChecked(false);
                }else {
                    binding.checkboxSrcIncomeOthers.setChecked(false);
                    binding.incomeSrcOtherContainer.setVisibility(GONE);
                }
            }
        });

        binding.spinnerIncomeProof.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 7){
                    binding.incomeSrcOtherContainer2.setVisibility(VISIBLE);
                }else {
                    binding.incomeSrcOtherContainer2.setVisibility(GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.back.setOnClickListener(view -> finish());
    }

    private void saveNow() {
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        String currentTimeZone = new SimpleDateFormat("z", Locale.getDefault()).format(new Date());
        String timeZone = currentDate +" "+ currentTime +" "+ currentTimeZone;

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("axn", "save_edu")
                .addFormDataPart("education", mEducation)
                .addFormDataPart("occupation", mOccupation)
                .addFormDataPart("date", currentDate)
                .addFormDataPart("time", currentTime)
                .addFormDataPart("time_zone", currentTimeZone)
                .build();

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<ResponseBody> call = apiInterface.saveEduOccupation(requestBody);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    if (response.body() != null) {

                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                startActivity(new Intent(context, RelCommDetailsActivity.class));
                Toast.makeText(context, "Error!" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateInputs() {
         mEducation = binding.spinnerEducation.getSelectedItem().toString();
         mOccupation = binding.spinnerOccupation.getSelectedItem().toString();

        if("Select".equals(mEducation)){
            Utils.showMessageInSnackbar(context, "Select education");
            return false;
        }
        if (bitmap == null){
            Utils.showMessageInSnackbar(context, "Capture education proof");
            return false;
        }
        if ("Select".equals(mOccupation)){
            Utils.showMessageInSnackbar(context, "Select occupation");
            return false;
        }
        if (occupationSelectedId == 1){

            String mValue = binding.salariedEmployedWithSpinner.getSelectedItem().toString();

            if ("Others".equals(mValue)){

               if (binding.salariedEmployedWithOtherEd.getText().toString().isEmpty()){
                   binding.salariedEmployedWithOtherEd.requestFocus();
                   Utils.showMessageInSnackbar(context, "Please specify others");
                   return false;
               }

            }else {
                if ("Select".equals(binding.salariedEmployedWithSpinner.getSelectedItem().toString())){
                    Utils.showMessageInSnackbar(context, "Select employed with");
                    return false;
                }

                if(binding.salariedEmployedWithCompanyEd.getText().toString().isEmpty()){
                    binding.salariedEmployedWithCompanyEd.requestFocus();
                    Utils.showMessageInSnackbar(context, "Enter company name");
                    return false;
                }
            }
        }
        return true;
    }

    private void captureEvent() {
            Intent intent = new Intent(context, CameraX.class);
            intent.putExtra("event_name", getString(R.string.capture_education_proof));
            intent.putExtra("camera_id", "14");
            startActivity(intent);
    }

    private void imageView(String educationFileName, String eventName) {
        String imagePath = getExternalFilesDir("/").getPath() + "/" + "credse/" + educationFileName;

        Intent intent = new Intent(context, ImageViewActivity.class);
        intent.putExtra("uri", imagePath);
        intent.putExtra("event_name", eventName);
        startActivity(intent);
    }

    private void initSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.education_array, R.layout.custom_spinner);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        binding.spinnerEducation.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.occupation_array, R.layout.custom_spinner);
        adapter1.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        binding.spinnerOccupation.setAdapter(adapter1);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.employed_with, R.layout.custom_spinner);
        adapter2.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        binding.salariedEmployedWithSpinner.setAdapter(adapter2);

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.income_from_array, R.layout.custom_spinner);
        adapter3.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        binding.spinnerAnIncomeFrom.setAdapter(adapter3);

        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this,
                R.array.income_to_array, R.layout.custom_spinner);
        adapter4.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        binding.spinnerAnIncomeTo.setAdapter(adapter4);

        ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(this,
                R.array.income_proof_array, R.layout.custom_spinner);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerIncomeProof.setAdapter(adapter5);
    }

    @Override
    protected void onResume() {
        super.onResume();
        file = new File(getExternalFilesDir(null), "/credse/" + EDUCATION_FILE_NAME);
        bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        if (bitmap != null){
            binding.txtEducationProof.setVisibility(GONE);
            binding.imageEducationProof.setImageBitmap(bitmap);
            binding.reTakeEduProof.setVisibility(VISIBLE);
        }
    }
}