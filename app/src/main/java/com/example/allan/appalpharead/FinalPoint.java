package com.example.allan.appalpharead;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FinalPoint extends Activity {

    private TextView point;

    private Button btnTelaPrincipal;

    private Bundle bundle;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_point);

        context = getApplicationContext();

        bundle = getIntent().getExtras();
        Log.i("Words", bundle.getString("Point"));

        point = findViewById(R.id.Point);
        point.setText(bundle.getString("Point"));

        btnTelaPrincipal = findViewById(R.id.btnEnd);
        btnTelaPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), PaginaPrincipal.class);
                startActivity(it);
            }
        });

    }

}
