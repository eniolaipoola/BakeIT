package com.eniola.bakeit.UIs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.eniola.bakeit.R;
import com.eniola.bakeit.UIs.adapters.RecipeIngredientAdapter;
import com.eniola.bakeit.UIs.adapters.RecipeStepAdapter;
import com.eniola.bakeit.databinding.ActivityRecipeInformationBinding;
import com.eniola.bakeit.models.RecipeDescription;
import com.eniola.bakeit.models.RecipeIngredient;
import com.eniola.bakeit.models.RecipeModel;

import java.util.List;

public class RecipeInformationActivity extends AppCompatActivity {
    private RecipeIngredientAdapter recipeIngredientAdapter;
    private RecipeStepAdapter recipeStepAdapter;
    ActivityRecipeInformationBinding recipeInformationBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recipeInformationBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_information);
        recipeInformationBinding.getRoot();
        getRecipeIngredient();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL,
                false);
        recipeInformationBinding.ingredientRecyclerView.setLayoutManager(gridLayoutManager);
        recipeInformationBinding.ingredientRecyclerView.setHasFixedSize(true);

        GridLayoutManager descriptionLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL,
                false);
        recipeInformationBinding.descriptionRecyclerView.setLayoutManager(descriptionLayoutManager);
        recipeInformationBinding.descriptionRecyclerView.setHasFixedSize(true);
    }

    public void getRecipeIngredient(){
        Intent intent = getIntent();
        if(intent != null){
            RecipeModel recipeModel = (RecipeModel) intent.getSerializableExtra("RECIPE");
            if(recipeModel != null){
                List<RecipeIngredient> recipeIngredients = recipeModel.getRecipeIngredientList();
                Log.d("debug", recipeModel.getName()+ "recipe name" );
                Log.d("debug", recipeIngredients.size() + "recipe ingredient size is" );
                recipeIngredientAdapter = new RecipeIngredientAdapter(recipeIngredients);
                recipeInformationBinding.ingredientRecyclerView.setAdapter(recipeIngredientAdapter);
                recipeIngredientAdapter.notifyDataSetChanged();

                List<RecipeDescription> recipeDescriptions = recipeModel.getRecipeDescriptionList();
                recipeStepAdapter = new RecipeStepAdapter(recipeDescriptions);
                recipeInformationBinding.descriptionRecyclerView.setAdapter(recipeStepAdapter);
                recipeStepAdapter.notifyDataSetChanged();
            }
        }
    }
}
