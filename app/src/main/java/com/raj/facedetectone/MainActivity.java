package com.raj.facedetectone;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;

public class MainActivity extends AppCompatActivity {

    ImageView img;
    Button btnProgress;
    //Canvas canvas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        img=(ImageView)findViewById(R.id.img);
        btnProgress=(Button)findViewById(R.id.btnProgress);

        final Bitmap bitmapFactory=BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.imgfacone);
        img.setImageBitmap(bitmapFactory);


        final Paint paint=new Paint();
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);



        final Bitmap bitmap=Bitmap.createBitmap(bitmapFactory.getWidth(),bitmapFactory.getHeight(),Bitmap.Config.RGB_565);

        final Canvas canvas=new Canvas(bitmap);
        canvas.drawBitmap(bitmapFactory,0,0,null);



        btnProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseVisionFaceDetectorOptions highAccuracyOpts =
                        new FirebaseVisionFaceDetectorOptions.Builder()
                                .setPerformanceMode(FirebaseVisionFaceDetectorOptions.ACCURATE)
                                .setLandmarkMode(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
                                .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
                                .build();


                FaceDetector highAccuracyOpts1 =
                        new FaceDetector.Builder(getApplicationContext())
                                .setTrackingEnabled(false)
                                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                                .setMode(FaceDetector.FAST_MODE)
                                 .build();



                if(!highAccuracyOpts1.isOperational()){
                    Toast.makeText(getApplicationContext(),"Not Support",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Support",Toast.LENGTH_LONG).show();
                }


                Frame frame=new Frame.Builder().setBitmap(bitmapFactory).build();
                SparseArray<Face> faceSparseArray=highAccuracyOpts1.detect(frame);

                canvas.drawBitmap(bitmap,0,0,null);
                for(int i=0;i<faceSparseArray.size();i++){
                    Face face=faceSparseArray.valueAt(i);
                    float x1=face.getPosition().x;
                    float y1=face.getPosition().y;

                    float x2=x1+face.getWidth();
                    float y2=y1+face.getHeight();


                    RectF mRectF=new RectF(x1,y1,x2,y2);

                    canvas.drawRoundRect(mRectF,2,2,paint);

                }

               img.setImageDrawable(new BitmapDrawable(getResources(),bitmap));




            }
        });



    }
}
