package com.eniola.bakeit.UIs.fragments;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eniola.bakeit.R;
import com.eniola.bakeit.databinding.FragmentRecipeInformationDescriptionBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeInformationDescriptionFragment extends Fragment {


    public RecipeInformationDescriptionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentRecipeInformationDescriptionBinding descriptionBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_recipe_information_description, container, false);
        View rootView = descriptionBinding.getRoot();
        return rootView;
    }

}
