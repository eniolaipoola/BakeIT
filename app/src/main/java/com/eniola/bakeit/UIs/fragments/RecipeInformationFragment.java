package com.eniola.bakeit.UIs.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eniola.bakeit.R;
import com.eniola.bakeit.databinding.FragmentRecipeInfromationBinding;

public class RecipeInformationFragment extends Fragment {

    private OnRecipeStepClickedListener onRecipeStepClickedListener;
    GridLayoutManager gridLayoutManager;

    public RecipeInformationFragment() {}

    public static RecipeInformationFragment newInstance(String param1, String param2) {
        RecipeInformationFragment fragment = new RecipeInformationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentRecipeInfromationBinding recipeInfromationBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_recipe_infromation, container, false);
        View rootview = recipeInfromationBinding.getRoot();
       /* gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL,
                false);*/
       /* recipeInformationBinding.ingredientRecyclerView.setLayoutManager(gridLayoutManager);
        recipeInformationBinding.ingredientRecyclerView.setHasFixedSize(true);


        GridLayoutManager descriptionLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL,
                false);
        recipeInformationBinding.descriptionRecyclerView.setLayoutManager(descriptionLayoutManager);
        recipeInformationBinding.descriptionRecyclerView.setHasFixedSize(true);
*/

        return  rootview;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRecipeStepClickedListener) {
            onRecipeStepClickedListener = (OnRecipeStepClickedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onRecipeStepClickedListener = null;
    }

    public interface OnRecipeStepClickedListener {
        void onRecipeStepClicked();
    }
}
