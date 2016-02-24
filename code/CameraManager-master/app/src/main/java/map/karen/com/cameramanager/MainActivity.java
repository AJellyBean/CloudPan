package map.karen.com.cameramanager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.soundcloud.android.crop.CropImageActivity;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mCamera;
    private TextView mAlbum;
    private ImageView mPicture;
    private Uri mCameraUri;
    private File mAlbumFile;
    private boolean isCamera = true;

    private static int CAMERA_REQUEST_CODE = 1;
    private static int GALLERY_REQUEST_CODE = 2;
    private static int CROP_REQUEST_CODE = 3;
    private ImageView mPicture2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCamera = (TextView) findViewById(R.id.camera);
        mAlbum = (TextView) findViewById(R.id.album);
        mPicture = (ImageView) findViewById(R.id.picture);
        mPicture2 = (ImageView) findViewById(R.id.picture2);

        mCamera.setOnClickListener(this);
        mAlbum.setOnClickListener(this);

        mCameraUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/camera.png"));
        mAlbumFile = new File(Environment.getExternalStorageDirectory() + "/album.png");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.camera:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mCameraUri);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
                break;
            case R.id.album:
                Intent albumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                albumIntent.setType("image/*");
                startActivityForResult(albumIntent, GALLERY_REQUEST_CODE);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
//            try {
//                startImageZoom(mCameraUri);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

//        if (requestCode == CROP_REQUEST_CODE && resultCode == RESULT_OK) {
//            try {
//                Bitmap bitmap = BitmapFactory.decodeStream(
//                        getContentResolver().openInputStream(data.getData()));
//                mPicture.setImageBitmap(bitmap);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

//        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
//            try {
//                Bitmap bitmap = BitmapFactory.decodeStream(
//                        getContentResolver().openInputStream(data.getData()));
//                FileOutputStream fos = new FileOutputStream(mAlbumFile);
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
//                fos.flush();
//                fos.close();
//                startImageZoom(Uri.fromFile(mAlbumFile));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }


        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            try {
                startImageZoom(true, mCameraUri, mCameraUri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (requestCode == CROP_REQUEST_CODE && resultCode == RESULT_OK) {
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(
                        getContentResolver().openInputStream(isCamera ? mCameraUri : Uri.fromFile(mAlbumFile)));
                mPicture.setImageBitmap(bitmap);
                mPicture2.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            try {
                startImageZoom(false, data.getData(), Uri.fromFile(mAlbumFile));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


//    private void startImageZoom(Uri uri) {
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setDataAndType(uri, "image/*");
//        intent.putExtra("crop", "true");
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
//        intent.putExtra("outputX", 500);
//        intent.putExtra("outputY", 500);
//        intent.putExtra("return-data", true);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
//        startActivityForResult(intent, CROP_REQUEST_CODE);
//    }


    private void startImageZoom(boolean isCamera, Uri source, Uri destination) {
        try {
            this.isCamera = isCamera;
            Intent intent = new Intent(this, CropImageActivity.class);
            intent.setDataAndType(source, "image/*");
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", 500);
            intent.putExtra("outputY", 500);
            intent.putExtra("return-data", true);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, destination);
            startActivityForResult(intent, CROP_REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
