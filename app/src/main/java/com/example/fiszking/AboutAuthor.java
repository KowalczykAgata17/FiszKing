package com.example.fiszking;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class AboutAuthor extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String GITHUB_URL = "https://github.com/KowalczykAgata17";

    // TODO: Rename and change types of parameters

    public AboutAuthor() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_author, container, false);
        Button gh = view.findViewById(R.id.gh);
        gh.setOnClickListener(this::redirectToGH);
        return view;
    }

    public void redirectToGH(View view){
        openLink(GITHUB_URL);
    }

    private void openLink(String link){
        Uri uri = Uri.parse(link);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
