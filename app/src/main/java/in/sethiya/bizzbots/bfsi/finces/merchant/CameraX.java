package in.sethiya.bizzbots.bfsi.finces.merchant;

import android.content.Context;
import android.os.Bundle;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.AspectRatio;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
//noinspection ExifInterface
import android.media.ExifInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.google.common.util.concurrent.ListenableFuture;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import in.sethiya.bizzbots.bfsi.finces.merchant.databinding.ActivityCameraXBinding;
import in.sethiya.bizzbots.bfsi.finces.merchant.helper.Utils;

public class CameraX extends AppCompatActivity {
    private ActivityCameraXBinding binding;
    private final Context context = this;
    private Camera camera;
    private String imageName, DIR;
    private File file;
    private Bitmap bitmap;
    public static final String APP_DATA = "/credse";
    int cameraFacing = CameraSelector.LENS_FACING_FRONT;
    private static final String PROFILE_FILE_NAME = "PROFILE_123.jpg";
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
    private static final String EDUCATION_FILE_NAME = "EDUPROOF_123.jpg";

    private final ActivityResultLauncher<String> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean result) {
            if (result){
                // startCamera(cameraFacing);
                startCamera(1);
            }
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCameraXBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        cameraPermission();
        DIR = getExternalFilesDir("/").getPath() + "/" + "credse/";
        createDirectory();
        onClick();

        // Load event name
        String eventName = getIntent().getStringExtra("event_name");

        if (eventName != null){
            binding.eventName.setText(eventName);
        }
    }

    private void onClick() {
        binding.back.setOnClickListener(v -> finish());
        binding.buttonSwitchCam.setOnClickListener(v -> {
            if (cameraFacing == CameraSelector.LENS_FACING_BACK) {
                cameraFacing = CameraSelector.LENS_FACING_FRONT;
            } else {
                cameraFacing = CameraSelector.LENS_FACING_BACK;
            }
            startCamera(cameraFacing);
        });

        binding.cameraxRightControls.setOnClickListener(v -> {
            startCamera(cameraFacing);
            binding.imageView.setVisibility(View.GONE);
            binding.cameraxRightControls.setVisibility(View.GONE);

            binding.cameraxLeftControls.setVisibility(View.VISIBLE);
            binding.cameraPreview.setVisibility(View.VISIBLE);
            binding.cameraxClickLayout.setVisibility(View.VISIBLE);
            binding.captureButton.setVisibility(View.VISIBLE);
        });
    }

    private void  createDirectory() {
        File dir = getExternalFilesDir(APP_DATA);
        if(!dir.exists()) {
            if (!dir.mkdir()) {
                Toast.makeText(getApplicationContext(), "The folder " + dir.getPath() + "was not created", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void cameraPermission() {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            activityResultLauncher.launch(Manifest.permission.CAMERA);
        } else {
            startCamera(cameraFacing);
        }
    }

    public void startCamera(int cameraFacing) {
        int aspectRatio = aspectRatio(binding.cameraPreview.getWidth(), binding.cameraPreview.getHeight());
        ListenableFuture<ProcessCameraProvider> listenableFuture = ProcessCameraProvider.getInstance(this);

        listenableFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = (ProcessCameraProvider) listenableFuture.get();

                Preview preview = new Preview.Builder().setTargetAspectRatio(aspectRatio).build();

                androidx.camera.core.ImageCapture imageCapture = new androidx.camera.core.ImageCapture.Builder()
                        .setCaptureMode(androidx.camera.core.ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                        .setTargetRotation(getWindowManager()
                                .getDefaultDisplay().getRotation()).build();

                CameraSelector cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(cameraFacing).build();

                cameraProvider.unbindAll();

                camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture);

                binding.captureButton.setOnClickListener(view -> {
                    takePicture(imageCapture);
                });

                binding.buttonFlash.setOnClickListener(view -> setFlashIcon(camera));

                preview.setSurfaceProvider(binding.cameraPreview.getSurfaceProvider());
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    public void takePicture(androidx.camera.core.ImageCapture imageCapture) {

//        String userInfo = "MyPrefs";
//        UserDetails = getSharedPreferences(userInfo, Context.MODE_PRIVATE);
//        String SF_Code = UserDetails.getString("Sfcode","");

//        long tsLong = System.currentTimeMillis() / 1000;
//        capturedImageName = SF_Code +"_"+Long.toString(tsLong) + ".jpg";

//        file = new File(DIR, capturedImageName);

//        long tsLong = System.currentTimeMillis() / 1000;
//        imageName = tsLong + ".jpg";
//        file = new File(DIR, imageName);

        String cameraEventId = getIntent().getStringExtra("camera_id");

        switch (Integer.parseInt(cameraEventId)){
            case 14:
                imageName = EDUCATION_FILE_NAME;
                file = new File(DIR, imageName);
                break;

            case 13:
                imageName = RATION_BACK_FILE_NAME;
                file = new File(DIR, imageName);
                break;

            case 12:
                imageName = RATION_FRONT_FILE_NAME;
                file = new File(DIR, imageName);
                break;

            case 11:
                imageName = DRIVING_LICENSE_BACK_FILE_NAME;
                file = new File(DIR, imageName);
                break;

            case 10:
                imageName = DRIVING_LICENSE_FRONT_FILE_NAME ;
                file = new File(DIR, imageName);
                break;

            case 9:
                imageName = PASSPORT_BACK_FILE_NAME;
                file = new File(DIR, imageName);
                break;

            case 8:
                imageName = PASSPORT_FRONT_FILE_NAME;
                file = new File(DIR, imageName);
                break;

            case 7:
                imageName = VOTER_BACK_FILE_NAME;
                file = new File(DIR, imageName);
                break;

            case 6:
                imageName = VOTER_FRONT_FILE_NAME;
                file = new File(DIR, imageName);
                break;

            case 5:
                imageName = PAN_BACK_FILE_NAME;
                file = new File(DIR, imageName);
                break;

            case 4:
                imageName = PAN_FRONT_FILE_NAME;
                file = new File(DIR, imageName);
                break;

            case 3:
                imageName = AADHAR_BACK_FILE_NAME;
                file = new File(DIR, imageName);
                break;

                case 2:
                imageName = AADHAR_FRONT_FILE_NAME;
                file = new File(DIR, imageName);
                break;

            case 1:
                imageName = PROFILE_FILE_NAME;
                file = new File(DIR, imageName);
                break;

            default:
                Utils.showMessageInSnackbar(context, "Empty camera id!");
                break;
        }

        androidx.camera.core.ImageCapture.OutputFileOptions outputFileOptions = new androidx.camera.core.ImageCapture.OutputFileOptions.Builder(file).build();
        imageCapture.takePicture(outputFileOptions, Executors.newCachedThreadPool(), new androidx.camera.core.ImageCapture.OnImageSavedCallback() {
            @Override
            public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                runOnUiThread(() -> {
                    file = new File(DIR, imageName);
                    bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

                    // automatic screen orientation require Android 13 above API 33
                    // using Exif method
                    ExifInterface exif = null;
                    try {
                        exif = new ExifInterface(file.getPath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_UNDEFINED);
                    Bitmap bmRotated = rotateBitmap(bitmap, orientation);

                    binding.imageView.setImageBitmap(bmRotated);
                    binding.cameraxLeftControls.setVisibility(View.GONE);
                    binding.cameraxClickLayout.setVisibility(View.GONE);

                    binding.imageView.setVisibility(View.VISIBLE);;
                    binding.cameraxRightControls.setVisibility(View.VISIBLE);
                    binding.cameraPreview.setVisibility(View.INVISIBLE);
                });
                startCamera(cameraFacing);
            }

            @Override
            public void onError(@NonNull ImageCaptureException exception) {
                runOnUiThread(() -> Log.e("image_capture", "Captured image save error :" + exception.getMessage()));
                startCamera(cameraFacing);
            }
        });
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {
        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotated;
        }
        catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }

    private int aspectRatio(int width, int height) {
        double previewRatio = (double) Math.max(width, height) / Math.min(width, height);
        if (Math.abs(previewRatio - 4.0 / 3.0) <= Math.abs(previewRatio - 16.0 / 9.0)) {
            return AspectRatio.RATIO_4_3;
        }
        return AspectRatio.RATIO_16_9;
    }

    private void setFlashIcon(Camera camera) {
        if (camera.getCameraInfo().hasFlashUnit()) {
            if (camera.getCameraInfo().getTorchState().getValue() == 0) {
                camera.getCameraControl().enableTorch(true);
                binding.buttonFlash.setImageResource(R.drawable.camera_flash);
            } else {
                camera.getCameraControl().enableTorch(false);
                binding.buttonFlash.setImageResource(R.drawable.camera_flash);
            }
        } else {
            runOnUiThread(() -> Toast.makeText(context, "Flash is not available currently", Toast.LENGTH_SHORT).show());
        }
    }
}