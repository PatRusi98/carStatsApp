package com.rusinak.carstat.ui.nastavenia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.rusinak.carstat.R;
import com.rusinak.carstat.ui.gallery.GalleryViewModel;

public class NastaveniaFragment extends Fragment {
    private NastaveniaViewModel nastaveniaViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        nastaveniaViewModel =
                new ViewModelProvider(this).get(NastaveniaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_nastavenia, container, false);
        //final TextView textView = root.findViewById(R.id.text_nastavenia);
        nastaveniaViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        return root;
    }
}
