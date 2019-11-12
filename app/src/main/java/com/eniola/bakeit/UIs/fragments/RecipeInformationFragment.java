package com.eniola.bakeit.UIs.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import com.eniola.bakeit.R;
import com.eniola.bakeit.UIs.adapters.RecipeIngredientAdapter;
import com.eniola.bakeit.UIs.adapters.RecipeStepAdapter;
import com.eniola.bakeit.databinding.FragmentRecipeInfromationBinding;
import com.eniola.bakeit.models.OnRecipeStepInstructionClickedListener;
import com.eniola.bakeit.models.RecipeDescription;
import com.eniola.bakeit.models.RecipeIngredient;
import com.eniola.bakeit.models.RecipeModel;
import com.eniola.bakeit.utilities.APPConstant;
import java.util.List;

public class RecipeInformationFragment extends Fragment implements OnRecipeStepInstructionClickedListener {

    GridLayoutManager gridLayoutManager;
    GridLayoutManager descriptionLayoutManager;
    FragmentRecipeInfromationBinding recipeInformationBinding;
    private RecipeIngredientAdapter recipeIngredientAdapter;
    private RecipeStepAdapter recipeStepAdapter;
    private RecipeModel currentRecipe;
    private List<RecipeIngredient> recipeIngredients;
    private List<RecipeDescription> recipeDescriptions;

    public RecipeInformationFragment() {}

    public static RecipeInformationFragment newInstance(RecipeModel recipeModel) {
        RecipeInformationFragment fragment = new RecipeInformationFragment();
        Bundle args = new Bundle();
        args.putSerializable("RECIPE_MODEL", recipeModel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        recipeInformationBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_recipe_infromation, container, false);
        View rootview = recipeInformationBinding.getRoot();
        if(getArguments() != null){
            currentRecipe =  (RecipeModel) getArguments().getSerializable("RECIPE_MODEL");
            if(currentRecipe != null){
                String recipeName = currentRecipe.getName();
                getActivity().setTitle(recipeName);
                recipeIngredients = currentRecipe.getRecipeIngredientList();
                recipeDescriptions = currentRecipe.getRecipeDescriptionList();

                recipeIngredientAdapter = new RecipeIngredientAdapter(recipeIngredients);
                recipeInformationBinding.fragmentIngredientRecyclerview.setAdapter(recipeIngredientAdapter);
                recipeIngredientAdapter.notifyDataSetChanged();

                recipeStepAdapter = new RecipeStepAdapter(recipeDescriptions, this);
                recipeInformationBinding.fragmentStepDescriptionRecyclerView.setAdapter(recipeStepAdapter);
                recipeStepAdapter.notifyDataSetChanged();

            } else {
                Log.d(APPConstant.DEBUG_TAG, "looks like it didn't pass bundle so recipe name is empty " );
            }
        }

        gridLayoutManager = new GridLayoutManager(this.getContext(), 1, GridLayoutManager.HORIZONTAL,
                false);
        recipeInformationBinding.fragmentIngredientRecyclerview.setLayoutManager(gridLayoutManager);
        recipeInformationBinding.fragmentIngredientRecyclerview.setHasFixedSize(true);

        descriptionLayoutManager = new GridLayoutManager(this.getContext(), 1, GridLayoutManager.VERTICAL,
                false);
        recipeInformationBinding.fragmentStepDescriptionRecyclerView.setLayoutManager(descriptionLayoutManager);
        recipeInformationBinding.fragmentStepDescriptionRecyclerView.setHasFixedSize(true);
        return  rootview;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onRecipeStepInstructionClicked(RecipeDescription recipeDescription) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        RecipeStepVideoDescriptionFragment stepVideoDescriptionFragment =
                RecipeStepVideoDescriptionFragment.newInstance(currentRecipe, recipeDescription);
        fragmentTransaction.replace(R.id.fragment_step_description_page, stepVideoDescriptionFragment);
        fragmentTransaction.commit();
    }
}
