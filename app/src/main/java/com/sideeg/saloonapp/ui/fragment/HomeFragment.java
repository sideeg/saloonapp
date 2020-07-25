package com.sideeg.saloonapp.ui.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sideeg.saloonapp.R;
import com.sideeg.saloonapp.ui.activites.NavActivity;

import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        FloatingActionButton fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Dialog dialog = new Dialog(rootView.getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.add_new_service);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);

            dialog.findViewById(R.id.cancel_button).setOnClickListener(v -> dialog.dismiss());
            dialog.show();
        });

        return rootView;
    }
}
