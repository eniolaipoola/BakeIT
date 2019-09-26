package com.eniola.bakeit.UIs.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.eniola.bakeit.R;
import com.eniola.bakeit.databinding.FragmentErrorPageBinding;

public class AppErrorViewFragment extends DialogFragment {

    public AppErrorViewFragment(){}

    public static AppErrorViewFragment newInstance(String errorMessage) {
        AppErrorViewFragment fragment = new AppErrorViewFragment();
        Bundle args = new Bundle();
        args.putString("message", errorMessage);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentErrorPageBinding fragmentErrorPageBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_error_page, container, false);
        View rootView = fragmentErrorPageBinding.getRoot();

        String message = null;
        if (getArguments() != null) {
            message = getArguments().getString("message");
        }
        fragmentErrorPageBinding.errorMessage.setText(message);
        fragmentErrorPageBinding.retryText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity = getActivity();
        if(context instanceof Activity){
            activity = (Activity) context;
        }
    }
}
