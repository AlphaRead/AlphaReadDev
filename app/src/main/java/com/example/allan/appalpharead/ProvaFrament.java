package com.example.allan.appalpharead;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.Manifest;

public class ProvaFrament extends Fragment {

    private Button btnSearch, btnBuild;

    private Context context;

    private static final int REQUEST_CODE = 100;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_prova, container, false);

        context = view.getContext();

        btnBuild = view.findViewById(R.id.btnBuild);
        btnSearch = view.findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermissionFromDevice()){
                    Intent it = new Intent(getContext(), SearchExam.class);
                    startActivity(it);
                } else {
                    requestPermissions(new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.RECORD_AUDIO
                    }, REQUEST_CODE);
                }
            }
        });

        btnBuild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(view.getContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CODE);
                } else {
                    Intent it = new Intent(getContext(), BuildQuestionOne.class);
                    startActivity(it);
                }
            }
        });

        return view;
    }

    private boolean checkPermissionFromDevice() {
        int write_external = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int recording = ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO);
        return write_external == PackageManager.PERMISSION_GRANTED &&
                recording == PackageManager.PERMISSION_GRANTED;
    }

}
