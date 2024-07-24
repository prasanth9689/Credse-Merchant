package in.sethiya.bizzbots.bfsi.finces.merchant.activity.contacts;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

import in.sethiya.bizzbots.bfsi.finces.merchant.CameraX;
import in.sethiya.bizzbots.bfsi.finces.merchant.R;
import in.sethiya.bizzbots.bfsi.finces.merchant.activity.ImageViewActivity;
import in.sethiya.bizzbots.bfsi.finces.merchant.databinding.ActivityEduOccupDetailsBinding;

public class EduOccupDetailsActivity extends AppCompatActivity {
    private ActivityEduOccupDetailsBinding binding;
    private final Context context = this;
    private static final String EDUCATION_FILE_NAME = "EDUPROOF_123.jpg";
    private File file;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEduOccupDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        onClick();
        initSpinner();
    }

    private void onClick() {
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
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerEducation.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.occupation_array, R.layout.custom_spinner);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerOccupation.setAdapter(adapter1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        file = new File(getExternalFilesDir(null), "/credse/" + EDUCATION_FILE_NAME);
        bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        if (file != null){
            binding.txtEducationProof.setVisibility(View.GONE);
            binding.imageEducationProof.setImageBitmap(bitmap);
            binding.reTakeEduProof.setVisibility(View.VISIBLE);
        }
    }
}