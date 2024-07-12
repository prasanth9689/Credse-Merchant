package in.sethiya.bizzbots.bfsi.finces.merchant.activity.contacts;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

import in.sethiya.bizzbots.bfsi.finces.merchant.CameraX;
import in.sethiya.bizzbots.bfsi.finces.merchant.R;
import in.sethiya.bizzbots.bfsi.finces.merchant.activity.ImageViewActivity;
import in.sethiya.bizzbots.bfsi.finces.merchant.databinding.ActivityPersonalDetailsBinding;

public class PersonalDetailsActivity extends AppCompatActivity {
    private ActivityPersonalDetailsBinding binding;
    private final Context context = this;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPersonalDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        onClick();
    }

    private void onClick() {
        binding.profileImg.setOnClickListener(v -> {
            File file = new File(getExternalFilesDir(null), "/credse/" + "PROFILE_123.jpg");
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
        File fileBreed = new File(getExternalFilesDir(null), "/credse/" + "PROFILE_123.jpg");
        Bitmap bitmap = BitmapFactory.decodeFile(fileBreed.getAbsolutePath());

        if (bitmap != null){
            Glide
                    .with(context)
                    .load(bitmap)
                    .apply(RequestOptions.circleCropTransform())
                    .placeholder(R.drawable.placeholder_person)
                    .into(binding.profileImage);

        }
    }
}