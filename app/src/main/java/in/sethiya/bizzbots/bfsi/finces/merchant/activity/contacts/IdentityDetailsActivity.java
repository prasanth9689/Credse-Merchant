package in.sethiya.bizzbots.bfsi.finces.merchant.activity.contacts;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;

import in.sethiya.bizzbots.bfsi.finces.merchant.CameraX;
import in.sethiya.bizzbots.bfsi.finces.merchant.R;
import in.sethiya.bizzbots.bfsi.finces.merchant.activity.ImageViewActivity;
import in.sethiya.bizzbots.bfsi.finces.merchant.databinding.ActivityIdentityDetailsBinding;
import in.sethiya.bizzbots.bfsi.finces.merchant.helper.Utils;

public class IdentityDetailsActivity extends AppCompatActivity {
    private ActivityIdentityDetailsBinding binding;
    private final Context context = this;
    private final static String TAG = IdentityDetailsActivity.class.getSimpleName();
    private String mAadharNo, mPanNo, mVoterId, mPassportNo, mDrivingLicenseNo, mRationCardNo;
    private Bitmap bitmapAadharFront, bitmapAadharBack;
    private Bitmap bitmapPanFront,bitmapPanBack;

    private Bitmap bitmapVoterIdFront,bitmapVoterIdBack;
    private Bitmap bitmapPassportFront, bitmapPassportBack;

    private Bitmap bitmapDrivingLicenseFront, bitmapDrivingLicenseBack;
    private Bitmap bitmapRationCardFront, bitmapRationCardBack;
    private File file1, file2, file3, file4, file5, file6, file7, file8, file9, file10, file11, file12;

    private static final String AADHAR_FRONT_FILE_NAME = "AADFR_123.jpg";
    private static final String AADHAR_BACK_FILE_NAME = "AADBK_123.jpg";
    private static final String PAN_FRONT_FILE_NAME = "PANFR_123.jpg";
    private static final String PAN_BACK_FILE_NAME = "PANBK_123.jpg";

    private static final String VOTER_FRONT_FILE_NAME = "VOTERFR_123.jpg";
    private static final String VOTER_BACK_FILE_NAME = "VOTERBK_123.jpg";
    private static final String PASSPORT_FRONT_FILE_NAME = "PASSPORTFR_123.jpg";
    private static final String PASSPORT_BACK_FILE_NAME = "PASSPORTBK_123.jpg";

    private static final String DRIVING_LICENSE_FRONT_FILE_NAME = "DRVFR_123.jpg";
    private static final String DRIVING_LICENSE_BACK_FILE_NAME = "DRVBK_123.jpg";
    private static final String RATION_FRONT_FILE_NAME = "RATIONFR_123.jpg";
    private static final String RATION_BACK_FILE_NAME = "RATIONBK_123.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIdentityDetailsBinding.inflate(getLayoutInflater());
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

        // Ration card back
        binding.cardRdBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap1 = BitmapFactory.decodeFile(file12.getAbsolutePath());
                if (bitmap1 != null){
                    imageView(RATION_BACK_FILE_NAME, getString(R.string.ration_card_back));
                    return;
                }
                captureEvent(12);
            }
        });

        binding.reTakeRdBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureEvent(12);
            }
        });

        // Ration card front
        binding.cardRdFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap1 = BitmapFactory.decodeFile(file11.getAbsolutePath());
                if (bitmap1 != null){
                    imageView(RATION_FRONT_FILE_NAME, getString(R.string.ration_card_front));
                    return;
                }
                captureEvent(11);
            }
        });

        binding.reTakeRdFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureEvent(11);
            }
        });

        // Driving License back side
        binding.cardDrivingLicenseBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap1 = BitmapFactory.decodeFile(file10.getAbsolutePath());
                if (bitmap1 != null){
                    imageView(DRIVING_LICENSE_BACK_FILE_NAME, getString(R.string.driving_license_back));
                    return;
                }
                captureEvent(10);
            }
        });

        binding.reTakeDrivingLicenseBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureEvent(10);
            }
        });

        // Driving License front side
        binding.cardDrivingLicenseFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap1 = BitmapFactory.decodeFile(file9.getAbsolutePath());
                if (bitmap1 != null){
                    imageView(DRIVING_LICENSE_FRONT_FILE_NAME, getString(R.string.driving_license_front));
                    return;
                }
                captureEvent(9);
            }
        });

        binding.reTakeDrivingLicenseFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureEvent(9);
            }
        });

        // Passport back side
        binding.cardPassportBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap1 = BitmapFactory.decodeFile(file8.getAbsolutePath());
                if (bitmap1 != null){
                    imageView(PASSPORT_BACK_FILE_NAME, getString(R.string.passport_back));
                    return;
                }
                captureEvent(8);
            }
        });

        binding.reTakePassportBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureEvent(8);
            }
        });

        // Passport front side
        binding.cardPassportFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap1 = BitmapFactory.decodeFile(file7.getAbsolutePath());
                if (bitmap1 != null){
                    imageView(PASSPORT_FRONT_FILE_NAME, getString(R.string.passport_front));
                    return;
                }
                captureEvent(7);
            }
        });

        binding.reTakePassportFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureEvent(7);
            }
        });

        // Voter back side
        binding.cardVoterBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap1 = BitmapFactory.decodeFile(file6.getAbsolutePath());
                if (bitmap1 != null){
                    imageView(VOTER_BACK_FILE_NAME, getString(R.string.voter_id_back));
                    return;
                }
                captureEvent(6);
            }
        });

        binding.reTakeVoterBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureEvent(6);
            }
        });

        // Voter front side
        binding.cardVoterFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap1 = BitmapFactory.decodeFile(file5.getAbsolutePath());
                if (bitmap1 != null){
                    imageView(VOTER_FRONT_FILE_NAME, getString(R.string.voter_id_front));
                    return;
                }
                captureEvent(5);
            }
        });

        binding.reTakeVoterFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureEvent(5);
            }
        });

        // Pan back side
        binding.cardPanBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap1 = BitmapFactory.decodeFile(file4.getAbsolutePath());
                if (bitmap1 != null){
                    imageView(PAN_BACK_FILE_NAME, getString(R.string.pan_back));
                    return;
                }
                captureEvent(4);
            }
        });

        binding.reTakePanBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureEvent(4);
            }
        });

        // Pan front side
        binding.cardPanFront.setOnClickListener(view -> {
            Bitmap bitmap1 = BitmapFactory.decodeFile(file3.getAbsolutePath());
            if (bitmap1 != null){
                imageView(PAN_FRONT_FILE_NAME, getString(R.string.pan_front));
                return;
            }
            captureEvent(3);
        });

        binding.reTakePanFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureEvent(3);
            }
        });

        // Adhar back side
        binding.cardAadharBack.setOnClickListener(view -> {

            Bitmap bitmap1 = BitmapFactory.decodeFile(file2.getAbsolutePath());
            if (bitmap1 != null){
                imageView(AADHAR_BACK_FILE_NAME, getString(R.string.aadhaar_back));
                return;
            }
            captureEvent(2);
        });

        binding.reTakeAadharBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureEvent(2);
            }
        });

        // Adhar front side
        binding.cardAadharFront.setOnClickListener(view -> {

            Bitmap bitmap1 = BitmapFactory.decodeFile(file1.getAbsolutePath());
            if (bitmap1 != null){
                imageView(AADHAR_FRONT_FILE_NAME, getString(R.string.aadhaar_front));
                return;
            }
            captureEvent(1);
        });

        binding.reTakeAadharFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureEvent(1);
            }
        });

    }

    private void captureEvent(int i) {
        Intent intent = new Intent(context, CameraX.class);

        switch (i){

            case 12:
                intent.putExtra("event_name", getString(R.string.capture_ration_card_back));
                intent.putExtra("camera_id", "13");
                startActivity(intent);
                break;

            case 11:
                intent.putExtra("event_name", getString(R.string.capture_ration_card_front));
                intent.putExtra("camera_id", "12");
                startActivity(intent);
                break;

            case 10:
                intent.putExtra("event_name", getString(R.string.capture_driving_license_back));
                intent.putExtra("camera_id", "11");
                startActivity(intent);
                break;

            case 9:
                intent.putExtra("event_name", getString(R.string.capture_driving_license_front));
                intent.putExtra("camera_id", "10");
                startActivity(intent);
                break;

            case 8:
                intent.putExtra("event_name", getString(R.string.capture_passport_back));
                intent.putExtra("camera_id", "9");
                startActivity(intent);
                break;

            case 7:
                intent.putExtra("event_name", getString(R.string.capture_passport_front));
                intent.putExtra("camera_id", "8");
                startActivity(intent);
                break;

            case 6:
                intent.putExtra("event_name", getString(R.string.capture_voter_id_back));
                intent.putExtra("camera_id", "7");
                startActivity(intent);
                break;

            case 5:
                intent.putExtra("event_name", getString(R.string.capture_voter_id_front));
                intent.putExtra("camera_id", "6");
                startActivity(intent);
                break;

            case 4:
                intent.putExtra("event_name", getString(R.string.capture_pan_back));
                intent.putExtra("camera_id", "5");
                startActivity(intent);
                break;

            case 3:
                intent.putExtra("event_name", getString(R.string.capture_pan_front));
                intent.putExtra("camera_id", "4");
                startActivity(intent);
                break;

            case 2:
                intent.putExtra("event_name", getString(R.string.capture_aadhar_back));
                intent.putExtra("camera_id", "3");
                startActivity(intent);
                break;

            case 1:
                intent.putExtra("event_name", getString(R.string.capture_aadhar_front));
                intent.putExtra("camera_id", "2");
                startActivity(intent);
                break;

        }
    }

    private void imageView(String fileName, String eventName) {
        String imagePath = getExternalFilesDir("/").getPath() + "/" + "credse/" + fileName;

        Intent intent = new Intent(context, ImageViewActivity.class);
        intent.putExtra("uri", imagePath);
        intent.putExtra("event_name", eventName);
        startActivity(intent);
    }

    private void saveNow() {
        Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
    }

    private boolean validateInputs() {
        mAadharNo = binding.edAadharNo.getText().toString().trim();
        mPanNo = binding.edPanCard.getText().toString().trim();
        mVoterId = binding.edVoterId.getText().toString().trim();
        mPassportNo = binding.edPassport.getText().toString().trim();
        mDrivingLicenseNo = binding.edDrivingLicense.getText().toString().trim();
        mRationCardNo = binding.edRationCard.getText().toString().trim();

        // Aadhar
        if (mAadharNo.isEmpty()) {
            binding.edAadharNo.setError(getString(R.string.enter_aadhar_no));
            binding.edAadharNo.requestFocus();
            return false;
        }
        if (bitmapAadharFront == null) {
           Utils.showMessageInSnackbar(context, getString(R.string.capture_aadhar_front));
           return false;
        }
        if (bitmapAadharBack == null) {
            Utils.showMessageInSnackbar(context, getString(R.string.capture_aadhar_back));
            return false;
        }

        // Pan
        if (mPanNo.isEmpty()) {
            binding.edPanCard.setError(getString(R.string.enter_pan_no));
            binding.edPanCard.requestFocus();
            return false;
        }
        if (bitmapPanFront == null) {
            Utils.showMessageInSnackbar(context, getString(R.string.capture_pan_front));
            return false;
        }
        if (bitmapPanBack == null) {
            Utils.showMessageInSnackbar(context, getString(R.string.capture_pan_back));
            return false;
        }

        // Voter
        if (mVoterId.isEmpty()) {
            binding.edVoterId.setError(getString(R.string.enter_voter_id));
            binding.edVoterId.requestFocus();
            return false;
        }
        if (bitmapVoterIdFront == null) {
            Utils.showMessageInSnackbar(context, getString(R.string.capture_voter_id_front));
            return false;
        }
        if (bitmapVoterIdBack == null) {
            Utils.showMessageInSnackbar(context, getString(R.string.capture_voter_id_back));
            return false;
        }

        // Passport
        if (mPassportNo.isEmpty()) {
            binding.edPassport.setError(getString(R.string.enter_passport_no));
            binding.edPassport.requestFocus();
            return false;
        }
        if (bitmapPassportFront == null) {
            Utils.showMessageInSnackbar(context, getString(R.string.capture_passport_front));
            return false;
        }
        if (bitmapPassportBack == null) {
            Utils.showMessageInSnackbar(context, getString(R.string.capture_passport_back));
            return false;
        }

        // Driving License
        if (mDrivingLicenseNo.isEmpty()) {
            binding.edDrivingLicense.setError(getString(R.string.enter_driving_license_no));
            binding.edDrivingLicense.requestFocus();
            return false;
        }
        if (bitmapDrivingLicenseFront == null) {
            Utils.showMessageInSnackbar(context, getString(R.string.capture_driving_license_front));
            return false;
        }
        if (bitmapDrivingLicenseBack == null) {
            Utils.showMessageInSnackbar(context, getString(R.string.capture_driving_license_back));
            return false;
        }

        // Ration card
        if (mRationCardNo.isEmpty()) {
            binding.edRationCard.setError(getString(R.string.enter_ration_card_no));
            binding.edRationCard.requestFocus();
            return false;
        }
        if (bitmapRationCardFront == null) {
            Utils.showMessageInSnackbar(context, getString(R.string.capture_driving_license_front));
            return false;
        }
        if (bitmapRationCardBack == null) {
            Utils.showMessageInSnackbar(context, getString(R.string.capture_driving_license_back));
            return false;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Ration card back side
        file12 = new File(getExternalFilesDir(null), "/credse/" + RATION_BACK_FILE_NAME);
        bitmapRationCardBack = BitmapFactory.decodeFile(file12.getAbsolutePath());
        if (bitmapRationCardBack != null){
            binding.txtRdCapBk.setVisibility(View.GONE);
            binding.imageRdBack.setImageBitmap(bitmapRationCardBack);
            binding.reTakeRdBack.setVisibility(View.VISIBLE);
        }

        // Ration card front side
        file11 = new File(getExternalFilesDir(null), "/credse/" + RATION_FRONT_FILE_NAME);
        bitmapRationCardFront = BitmapFactory.decodeFile(file11.getAbsolutePath());
        if (bitmapRationCardFront != null){
            binding.txtRdCapFr.setVisibility(View.GONE);
            binding.imageRdFront.setImageBitmap(bitmapRationCardFront);
            binding.reTakeRdFront.setVisibility(View.VISIBLE);
        }

        // Driving license back side
        file10 = new File(getExternalFilesDir(null), "/credse/" + DRIVING_LICENSE_BACK_FILE_NAME);
        bitmapDrivingLicenseBack = BitmapFactory.decodeFile(file10.getAbsolutePath());
        if (bitmapDrivingLicenseBack != null){
            binding.txtDrivingLicenseCapBk.setVisibility(View.GONE);
            binding.imageDrivingLicenseBack.setImageBitmap(bitmapDrivingLicenseBack);
            binding.reTakeDrivingLicenseBack.setVisibility(View.VISIBLE);
        }

        // Driving license front side
        file9 = new File(getExternalFilesDir(null), "/credse/" + DRIVING_LICENSE_FRONT_FILE_NAME);
        bitmapDrivingLicenseFront = BitmapFactory.decodeFile(file9.getAbsolutePath());
        if (bitmapDrivingLicenseFront != null){
            binding.txtDrivingLicenseCapFr.setVisibility(View.GONE);
            binding.imageDrivingLicenseFront.setImageBitmap(bitmapDrivingLicenseFront);
            binding.reTakeDrivingLicenseFront.setVisibility(View.VISIBLE);
        }

        // Passport back side
        file8 = new File(getExternalFilesDir(null), "/credse/" + PASSPORT_BACK_FILE_NAME);
        bitmapPassportBack = BitmapFactory.decodeFile(file8.getAbsolutePath());
        if (bitmapPassportBack != null){
            binding.txtPassportCapBk.setVisibility(View.GONE);
            binding.imagePassportBack.setImageBitmap(bitmapPassportBack);
            binding.reTakePassportBack.setVisibility(View.VISIBLE);
        }

        // Passport front side
        file7 = new File(getExternalFilesDir(null), "/credse/" + PASSPORT_FRONT_FILE_NAME);
        bitmapPassportFront = BitmapFactory.decodeFile(file7.getAbsolutePath());
        if (bitmapPassportFront != null){
            binding.txtPassportCapFr.setVisibility(View.GONE);
            binding.imagePassportFront.setImageBitmap(bitmapPassportFront);
            binding.reTakePassportFront.setVisibility(View.VISIBLE);
        }

        // Voter id back side
        file6 = new File(getExternalFilesDir(null), "/credse/" + VOTER_BACK_FILE_NAME);
        bitmapVoterIdBack = BitmapFactory.decodeFile(file6.getAbsolutePath());
        if (bitmapVoterIdBack != null){
            binding.txtVoterCapBk.setVisibility(View.GONE);
            binding.imageVoterBack.setImageBitmap(bitmapVoterIdBack);
            binding.reTakeVoterBack.setVisibility(View.VISIBLE);
        }

        // Voter id front side
        file5 = new File(getExternalFilesDir(null), "/credse/" + VOTER_FRONT_FILE_NAME);
        bitmapVoterIdFront = BitmapFactory.decodeFile(file5.getAbsolutePath());
        if (bitmapVoterIdFront != null){
            binding.txtVoterCapFr.setVisibility(View.GONE);
            binding.imageVoterFront.setImageBitmap(bitmapVoterIdFront);
            binding.reTakeVoterFront.setVisibility(View.VISIBLE);
        }


        // PAN back side
        file4 = new File(getExternalFilesDir(null), "/credse/" + PAN_BACK_FILE_NAME);
        bitmapPanBack = BitmapFactory.decodeFile(file4.getAbsolutePath());
        if (bitmapPanBack != null){
            binding.txtPanCapBk.setVisibility(View.GONE);
            binding.imagePanBack.setImageBitmap(bitmapPanBack);
            binding.reTakePanBack.setVisibility(View.VISIBLE);
        }

        // PAN front side
        file3 = new File(getExternalFilesDir(null), "/credse/" + PAN_FRONT_FILE_NAME);
        bitmapPanFront = BitmapFactory.decodeFile(file3.getAbsolutePath());
        if (bitmapPanFront != null){
            binding.txtPanCapFr.setVisibility(View.GONE);
            binding.imagePanFront.setImageBitmap(bitmapPanFront);
            binding.reTakePanFront.setVisibility(View.VISIBLE);
        }

        // Aadhar back side
        file2 = new File(getExternalFilesDir(null), "/credse/" + AADHAR_BACK_FILE_NAME);
        bitmapAadharBack = BitmapFactory.decodeFile(file2.getAbsolutePath());
        if (bitmapAadharBack != null){
            binding.txtAadharCapBk.setVisibility(View.GONE);
            binding.imageAadharBack.setImageBitmap(bitmapAadharBack);
            binding.reTakeAadharBack.setVisibility(View.VISIBLE);
        }

        // Aadhar front side
        file1 = new File(getExternalFilesDir(null), "/credse/" + AADHAR_FRONT_FILE_NAME);
        bitmapAadharFront = BitmapFactory.decodeFile(file1.getAbsolutePath());
        if (bitmapAadharFront != null){
            binding.txtAadharCapFr.setVisibility(View.GONE);
            binding.imageAadharFront.setImageBitmap(bitmapAadharFront);
            binding.reTakeAadharFront.setVisibility(View.VISIBLE);
        }
    }
}