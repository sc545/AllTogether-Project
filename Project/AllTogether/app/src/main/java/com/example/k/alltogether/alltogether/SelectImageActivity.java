package com.example.k.alltogether.alltogether;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.k.alltogether.R;

import java.io.File;

/**
 * Created by K on 2015-11-19.
 */
public class SelectImageActivity extends Activity implements View.OnClickListener {
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_CAMERA = 2;

    private Uri mImageCaptureUri;

    ImageView ivBubble, ivBombBubble, ivFeverBubble;
    Button btnChangeBubble, btnChangeBombBubble, btnChangeFeverBubble, btnOk, btnCancel;

    int imgFlag;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(MainActivity.SIZE == 0)
            setContentView(R.layout.select_image_activity);
        else
            setContentView(R.layout.select_image_activity_tab);

        ivBubble = (ImageView) findViewById(R.id.ivBubble);
        ivBombBubble = (ImageView) findViewById(R.id.ivBombBubble);
        ivFeverBubble = (ImageView) findViewById(R.id.ivFeverBubble);
        btnChangeBubble = (Button) findViewById(R.id.btnChangeBubble);
        btnChangeBombBubble = (Button) findViewById(R.id.btnChangeBombBubble);
        btnChangeFeverBubble = (Button) findViewById(R.id.btnChangeFeverBubble);
        btnOk = (Button) findViewById(R.id.btnOkay);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        btnChangeBubble.setOnClickListener(this);
        btnChangeBombBubble.setOnClickListener(this);
        btnChangeFeverBubble.setOnClickListener(this);

        intent = new Intent(getApplicationContext(), GameStageActivity.class);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent2);
                finish();
            }
        });

    }

    private void getPhotoFromCamera() { // 카메라에서 이미지 가져오기
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 임시로 사용할 파일의 경로 생성
        String url = "tmp_"+String.valueOf(System.currentTimeMillis())+".png";
        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));

        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PICK_FROM_CAMERA);
    }

    private void getPhotoFromAlbum(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_OK){
            return;
        }

        switch (requestCode){
            case CROP_FROM_CAMERA:
                final Bundle extras = data.getExtras();

                if(extras != null){
                    Bitmap photo = extras.getParcelable("data");
//                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    if(photo != null) {
                        photo = getCircleBitmap(photo);

                        if (imgFlag == 0) {
                            ivBubble.setImageBitmap(photo);
                            intent.putExtra("imgBubble", photo);
                        }
                        if (imgFlag == 1) {
                            ivBombBubble.setImageBitmap(photo);
                            intent.putExtra("imgBombBubble", photo);
                        }
                        if (imgFlag == 2) {
                            ivFeverBubble.setImageBitmap(photo);
                            intent.putExtra("imgFeverBubble", photo);
                        }

                    }else
                        Toast.makeText(getApplicationContext(), "사진을 다시 정해주세요!!", Toast.LENGTH_SHORT).show();

                }

                File f = new File(mImageCaptureUri.getPath());
                if(f.exists()){
                    f.delete();
                }
                break;
            case PICK_FROM_ALBUM:
                mImageCaptureUri = data.getData();
            case PICK_FROM_CAMERA:
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri, "image/*");

                intent.putExtra("outputX", 90);
                intent.putExtra("outputY", 90);
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_FROM_CAMERA);
                break;
        }
    }

    @Override
    public void onClick(View v)
    {
        if(v.getId() == R.id.btnChangeBubble){
            imgFlag = 0;
        }
        if(v.getId() == R.id.btnChangeBombBubble){
            imgFlag = 1;
        }
        if(v.getId() == R.id.btnChangeFeverBubble){
            imgFlag = 2;
        }

        DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                getPhotoFromCamera();
            }
        };

        DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                getPhotoFromAlbum();
            }
        };

        DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        };

        new AlertDialog.Builder(this)
                .setTitle("업로드할 이미지 선택")
                .setPositiveButton("사진촬영", cameraListener)
                .setNeutralButton("앨범선택", albumListener)
                .setNegativeButton("취소", cancelListener)
                .show();
    }
    /*
        이미지를 원으로 크롭하는 메소드
     */
    public Bitmap getCircleBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        int size = (bitmap.getWidth()/2);
        canvas.drawCircle(size, size, size, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {// 뒤로가기 누를시
        }
        return false;
    }

}
