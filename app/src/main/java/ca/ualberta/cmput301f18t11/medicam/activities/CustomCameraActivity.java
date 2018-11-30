package ca.ualberta.cmput301f18t11.medicam.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.graphics.Point;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.Drawable;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import ca.ualberta.cmput301f18t11.medicam.R;
import ca.ualberta.cmput301f18t11.medicam.controllers.ImageStorageController;
import ca.ualberta.cmput301f18t11.medicam.utils.CustomTextureView;

public class CustomCameraActivity extends Activity {
    //Refernce: https://github.com/googlesamples/android-Camera2Basic/blob/master/Application/src/main/java/com/example/android/camera2basic/Camera2BasicFragment.java

    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    //States for focus locking

    /**
     * Camera state: Showing camera preview.
     */
    private static final int STATE_PREVIEW = 0;

    /**
     * Camera state: Waiting for the focus to be locked.
     */
    private static final int STATE_WAIT_LOCK = 1;

    /**
     * Camera state: Waiting for the exposure to be precapture state.
     */
    private static final int STATE_WAITING_PRECAPTURE = 2;

    /**
     * Camera state: Waiting for the exposure state to be something other than precapture.
     */
    private static final int STATE_WAITING_NON_PRECAPTURE = 3;

    /**
     * Camera state: Picture was taken.
     */
    private static final int STATE_PICTURE_TAKEN = 4;

    /**
     * Max preview width that is guaranteed by Camera2 API
     */
    private static final int MAX_PREVIEW_WIDTH = 1920;

    /**
     * Max preview height that is guaranteed by Camera2 API
     */
    private static final int MAX_PREVIEW_HEIGHT = 1080;

    private int current_state;

    private Size mPreviewSize;
    private String mCameraId;
    private ImageView cameraOverlay;
    private ToggleButton overlayToggleButton;
    private TextureView mTextureView;
    private TextureView.SurfaceTextureListener mSurfaceTextureListener =
            new TextureView.SurfaceTextureListener() {
                @Override
                public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                    openCamera(width, height);
                }

                @Override
                public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
                    //configureTransform(width,height);
                }

                @Override
                public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                    return false;
                }

                @Override
                public void onSurfaceTextureUpdated(SurfaceTexture surface) {

                }
            };
    private CameraDevice mCameraDevice;
    private CameraDevice.StateCallback mCameraDeviceStateCallback =
            new CameraDevice.StateCallback() {
                @Override
                public void onOpened(CameraDevice camera) {
                    mCameraDevice = camera;
                    createCameraPreviewSesh();
                    Toast.makeText(getApplicationContext(), "Camera has been opened.", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onDisconnected(CameraDevice camera) {
                    camera.close();
                    mCameraDevice = null;
                }

                @Override
                public void onError(CameraDevice camera, int error) {
                    camera.close();
                    mCameraDevice = null;
                    finish();
                }
            };
    private CaptureRequest mPreviewCaptureRequest;
    private CaptureRequest.Builder mPreviewCaptureRequestBuilder;
    private CameraCaptureSession mCameraCaptureSession;
    private CameraCaptureSession.CaptureCallback mSessionCaptureCallback =
            new CameraCaptureSession.CaptureCallback() {

                private void process(CaptureResult result){
                    switch (current_state){
                        case STATE_WAIT_LOCK:
                            Integer afState = result.get(CaptureResult.CONTROL_AF_STATE);
                            if(afState == CaptureRequest.CONTROL_AF_STATE_FOCUSED_LOCKED){
                                captureStillImage();
                            }
                            break;
                        case STATE_PREVIEW:
                            //Do nothing, the camera is working.
                            break;
                    }

                }

                @Override
                public void onCaptureStarted(CameraCaptureSession session, CaptureRequest request, long timestamp, long frameNumber) {
                    super.onCaptureStarted(session, request, timestamp, frameNumber);
                }

                @Override
                public void onCaptureProgressed(CameraCaptureSession session, CaptureRequest request, CaptureResult partialResult) {
                    super.onCaptureProgressed(session, request, partialResult);

                    process(partialResult);
                }

                @Override
                public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) {
                    super.onCaptureCompleted(session, request, result);

                    process(result);
                }

                @Override
                public void onCaptureFailed(CameraCaptureSession session, CaptureRequest request, CaptureFailure failure) {
                    super.onCaptureFailed(session, request, failure);

                    Toast.makeText(getApplicationContext(), "Focus Lock Failed", Toast.LENGTH_SHORT).show();
                }
            };
    private HandlerThread mBackgroundThread;
    private Handler mBackgroundHandler;
    //TODO:Prompt user for image storage location. Or create one on build up.
    private File userImageFd;
    private ImageReader mImageReader;
    private final ImageReader.OnImageAvailableListener mOnImageAvailableListener =
            new ImageReader.OnImageAvailableListener() {
                @Override
                public void onImageAvailable(ImageReader reader) {
                    mBackgroundHandler.post(new ImageStorageController(reader.acquireNextImage(),userImageFd));
                }
            };


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_camera);

        //TODO:Pull most recent photo for this record from gallery and display as overlay
        cameraOverlay = findViewById(R.id.camera_overlay);
        cameraOverlay.setImageResource(R.drawable.ic_muscles_arm_svgrepo_com);
        cameraOverlay.setAlpha(0.25f);

        //TODO: check the necessity of these declarations
        final int maxMemorySize = (int) Runtime.getRuntime().maxMemory() / 1024;
        final int cacheSize = maxMemorySize / 10;

        mTextureView = (CustomTextureView) findViewById(R.id.camera_texture_view);
    }

    @Override
    public void onResume() {
        super.onResume();

        openBackgroundThread();

        if (mTextureView.isAvailable()) {
            openCamera(mTextureView.getWidth(), mTextureView.getHeight());
        } else {
            mTextureView.setSurfaceTextureListener(mSurfaceTextureListener);
        }
    }

    @Override
    public void onPause(){

        closeCamera();
        closeBackgroundThread();

        super.onPause();
    }

    private void setCameraDimension(int width, int height) {

        CameraManager camManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            for (String cameraId : camManager.getCameraIdList()) {
                CameraCharacteristics camCharacteristics = camManager.getCameraCharacteristics(cameraId);

                //TODO: Allow front-facing camera.
                if (camCharacteristics.get(CameraCharacteristics.LENS_FACING) ==
                        CameraCharacteristics.LENS_FACING_FRONT) {
                    continue;
                }

                StreamConfigurationMap scmap = camCharacteristics.get(
                        CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);

                //Something broke, probably camera hardware doesn't exist somehow.
                if(scmap == null){ continue; }

                Size largestImageSize = Collections.max(
                        Arrays.asList(scmap.getOutputSizes(ImageFormat.JPEG)),
                        new CompareByArea()
                );
                mImageReader = ImageReader.newInstance(largestImageSize.getWidth(),
                        largestImageSize.getHeight(),
                        ImageFormat.JPEG, //Set image format.
                        1);
                mImageReader.setOnImageAvailableListener(mOnImageAvailableListener, mBackgroundHandler);

                //TODO:check device orientation to orient photo in storage

                Point displayDimensions = new Point();
                getWindowManager().getDefaultDisplay().getSize(displayDimensions);

                mPreviewSize = getPreferredPreviewSize(scmap.getOutputSizes(SurfaceTexture.class),
                                            width,
                                            height,
                                            displayDimensions.x,
                                            displayDimensions.y,
                                            largestImageSize);


                mCameraId = cameraId;
                return;
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public void capturePhoto(View view) {
        try {
            userImageFd = createImageFilepath();
        }catch (IOException e ){
            e.printStackTrace();
        }
        lockFocus();
    }

    //TODO:Move this into ImageStorageController as a pre-storage check if exist, else create.
    File createImageFilepath() throws IOException{

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());


        File directory = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                + File.separator + "medicam");
        File parentDir = directory.getParentFile();

        if(!directory.exists()) {
            if(!directory.isDirectory()) {
                directory.mkdirs();
            }
        }

        return File.createTempFile(String.format("IMG_%s", timeStamp), ".jpg", directory );
    }

    private Size getPreferredPreviewSize(Size[] mapSizes, int texWidth, int texHeight,
                                         int maxWidth, int maxHeight, Size aspectRatio) {

        List<Size> collectorSizes = new ArrayList<Size>();
        List<Size> tooSmall = new ArrayList<Size>();
        int w = aspectRatio.getWidth();
        int h = aspectRatio.getHeight();

        //ensuring that the height and width are actually the correct dimensions
        for (Size option : mapSizes) {
            if (texWidth > texHeight) {
                if (option.getWidth() <= maxWidth && option.getHeight() <= maxHeight
                        && option.getHeight() == option.getWidth() * h / w) {
                    if (option.getWidth() >= texWidth &&
                            option.getHeight() >= texHeight) {
                        collectorSizes.add(option);
                    } else {
                        tooSmall.add(option);
                    }
                }
            }
        }

        if(collectorSizes.size() > 0) {
            return  Collections.min(collectorSizes,new CompareByArea());
        } else if (tooSmall.size() > 0){
            return Collections.max(tooSmall, new CompareByArea());
        } else {
            //We a usable size which as a serious problem
            Log.d("SET PREVIEW DIMENSIONS", "COULD NOT FIND SUITABLE DIMENSIONS.");
            return mapSizes[0];
        }

//            } else {
//                if (option.getWidth() > height &&
//                        option.getHeight() > width) {
//                    collectorSizes.add(option);
//                }
//            }
//        }
//        if (collectorSizes.size() > 0) {
//            return Collections.min(collectorSizes, new Comparator<Size>() {
//                @Override
//                public int compare(Size lhs, Size rhs) {
//                    return Long.signum(lhs.getWidth() * lhs.getHeight() - rhs.getWidth() * rhs.getHeight());
//                }
//            });
//        }
//        return mapSizes[0];
    }

    static class CompareByArea implements Comparator<Size> {

        @Override
        public int compare(Size lhs, Size rhs) {
            return Long.signum((long) lhs.getWidth() * lhs.getHeight() -
                    (long) rhs.getWidth() * rhs.getHeight());
        }
    }

    private void openCamera(int width, int height) {

        setCameraDimension(width, height);
        //configureTransform(width,height);

        CameraManager camManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            camManager.openCamera(mCameraId, mCameraDeviceStateCallback, mBackgroundHandler);
        }catch (CameraAccessException e){
            e.printStackTrace();
        }
    }

    private void closeCamera() {

            if (mCameraCaptureSession != null) {
                mCameraCaptureSession.close();
                mCameraCaptureSession = null;
            }
            if (mCameraDevice != null) {
                mCameraDevice.close();
                mCameraDevice = null;
            }
            if (mImageReader != null) {
                mImageReader.close();
                mImageReader = null;
            }
    }

    private void createCameraPreviewSesh() {
        try{
            SurfaceTexture surfaceTexture = mTextureView.getSurfaceTexture();
            surfaceTexture.setDefaultBufferSize(mPreviewSize.getWidth(), mPreviewSize.getHeight());
            Surface previewSurface = new Surface(surfaceTexture);
            mPreviewCaptureRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            mPreviewCaptureRequestBuilder.addTarget(previewSurface);
            mCameraDevice.createCaptureSession(Arrays.asList(previewSurface, mImageReader.getSurface()), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(CameraCaptureSession session) {
                    if(mCameraDevice == null){
                        //Something went wrong here
                        //The camera never opened
                        return;
                    }
                    try {
                        mPreviewCaptureRequest = mPreviewCaptureRequestBuilder.build();
                        mCameraCaptureSession = session;
                        mCameraCaptureSession.setRepeatingRequest(
                                mPreviewCaptureRequest,
                                mSessionCaptureCallback,
                                mBackgroundHandler
                        );

                    } catch (CameraAccessException e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onConfigureFailed(CameraCaptureSession session) {
                    Toast.makeText(getApplicationContext(), "Something went wrong with create camera session", Toast.LENGTH_SHORT).show();
                }
            }, null);
        }catch (CameraAccessException e){
            e.printStackTrace();
        }
    }

    public void toggleCameraOverlay(View view){
        boolean isChecked = ((ToggleButton) view).isChecked();

        if (isChecked) {
            cameraOverlay.setVisibility(View.VISIBLE);
        } else {
            cameraOverlay.setVisibility(View.INVISIBLE);
        }
    }

    private void openBackgroundThread() {
        mBackgroundThread = new HandlerThread("Camera2 background thread");
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
    }

    private void closeBackgroundThread() {

        //Clean up background threads
        mBackgroundThread.quitSafely();
        try{
            mBackgroundThread.join();
            mBackgroundThread = null;
            mBackgroundHandler = null;
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    private void unlockFocus(){
        try {
            current_state = STATE_PREVIEW;
            mPreviewCaptureRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER,
                    CaptureRequest.CONTROL_AF_TRIGGER_CANCEL);
            mCameraCaptureSession.capture(mPreviewCaptureRequestBuilder.build(),
                    mSessionCaptureCallback, mBackgroundHandler);
        }catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void lockFocus(){
        try {
            current_state = STATE_WAIT_LOCK;
            mPreviewCaptureRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER,
                    CaptureRequest.CONTROL_AF_TRIGGER_START);
            mCameraCaptureSession.capture(mPreviewCaptureRequestBuilder.build(),
                    mSessionCaptureCallback, mBackgroundHandler);
        }catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void captureStillImage(){
        try{
        CaptureRequest.Builder captureStillBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
        captureStillBuilder.addTarget(mImageReader.getSurface());

        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        captureStillBuilder.set(CaptureRequest.JPEG_ORIENTATION,
                ORIENTATIONS.get(rotation));
        CameraCaptureSession.CaptureCallback captureCallback =
                new CameraCaptureSession.CaptureCallback() {
                    @Override
                    public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) {
                        super.onCaptureCompleted(session, request, result);

                        Toast.makeText(getApplicationContext(), "Image Captured", Toast.LENGTH_SHORT).show();
                        unlockFocus();
                    }
                };

        mCameraCaptureSession.capture(
                captureStillBuilder.build(), captureCallback, null );

        } catch (CameraAccessException e){
            e.printStackTrace();
        }
    }
}

