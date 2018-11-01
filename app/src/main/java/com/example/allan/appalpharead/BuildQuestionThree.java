package com.example.allan.appalpharead;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Activity;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.allan.appalpharead.models.Data;
import com.example.allan.appalpharead.models.DicionarioOnline;
import com.google.android.gms.common.api.Api;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BuildQuestionThree extends Activity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private Button btnAvancar;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_question_three);

        btnAvancar = findViewById(R.id.btnCamera);
        image = findViewById(R.id.imageView);

        btnAvancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getApplicationContext().getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();

            String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

            Log.i("Base64", encoded);

            this.imageRecognition(encoded);

            image.setImageBitmap(imageBitmap);
        }
    }

    protected void imageRecognition(String base64){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.27.2.137:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PythonService api = retrofit.create(PythonService.class);

        String json = "{\"data\": \"bbb\"}";

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);

        api.postImage(requestBody).enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                try{
                    Log.i("Base64", response.body().toString());
                }catch(Exception e){
                    Log.i("Base64", "error");
                }
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
            }
        });

    }

}
