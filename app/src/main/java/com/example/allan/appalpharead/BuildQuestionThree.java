package com.example.allan.appalpharead;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Activity;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.allan.appalpharead.models.Data;

import java.io.ByteArrayOutputStream;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BuildQuestionThree extends Activity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private Button btnAvancar, btnCamera, btnCancel;
    private EditText txtName;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_question_three);

        final Bundle b = getIntent().getExtras();

        btnAvancar = findViewById(R.id.btnAvancar);
        btnCamera = findViewById(R.id.btnCamera);
        image = findViewById(R.id.imageView);
        txtName = findViewById(R.id.txtName);
        btnCancel = findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(BuildQuestionThree.this);
                alert.setTitle("Tem certeza de que deseja sair?");
                alert
                        .setMessage("Todo o progresso nesta construção de prova será perdido.")
                        .setIcon(R.drawable.notification)
                        .setPositiveButton("Sair", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                startActivity(new Intent(BuildQuestionThree.this, PaginaPrincipal.class));
                                finish();
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {}
                        });
                android.app.AlertDialog alertDialog = alert.create();
                alertDialog.show();

            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getApplicationContext().getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
            }
        });

        btnAvancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(txtName.getText().toString())) {
                    Intent it = new Intent(getApplicationContext(), BuildQuestionFour.class);

                    it.putExtra("word1", b.getString("word1"));
                    it.putExtra("word2", b.getString("word3"));
                    it.putExtra("word3", b.getString("word2"));
                    it.putExtra("word", b.getString("word"));
                    //it.putExtra("picture", image.getImageMatrix());
                    it.putExtra("name", txtName.getText().toString());

                    startActivity(it);
                }else{
                    Toast.makeText(getApplicationContext(), "Favor preencher todos os campos!", Toast.LENGTH_LONG).show();
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

            this.imageRecognition(encoded);

            image.setImageBitmap(imageBitmap);
        }
    }

    protected void imageRecognition(String base64){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.100.6:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PythonService api = retrofit.create(PythonService.class);

        String json = "{\"image\": "+ base64 +"\"}";

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
