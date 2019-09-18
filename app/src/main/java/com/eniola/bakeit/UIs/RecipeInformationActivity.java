package com.eniola.bakeit.UIs;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import com.eniola.bakeit.R;
import com.eniola.bakeit.UIs.adapters.RecipeIngredientAdapter;
import com.eniola.bakeit.UIs.adapters.RecipeStepAdapter;
import com.eniola.bakeit.databinding.ActivityRecipeInformationBinding;
import com.eniola.bakeit.models.OnRecipeStepInstructionClickedListener;
import com.eniola.bakeit.models.RecipeDescription;
import com.eniola.bakeit.models.RecipeIngredient;
import com.eniola.bakeit.models.RecipeModel;

import java.util.List;

public class RecipeInformationActivity extends AppCompatActivity implements OnRecipeStepInstructionClickedListener {

    private RecipeIngredientAdapter recipeIngredientAdapter;
    private RecipeStepAdapter recipeStepAdapter;
    ActivityRecipeInformationBinding recipeInformationBinding;
    String recipeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recipeInformationBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_information);
        recipeInformationBinding.getRoot();

        getRecipeIngredient();
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setTitle(recipeName);

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
                recipeName = recipeModel.getName();
                List<RecipeIngredient> recipeIngredients = recipeModel.getRecipeIngredientList();
                recipeIngredientAdapter = new RecipeIngredientAdapter(recipeIngredients);
                recipeInformationBinding.ingredientRecyclerView.setAdapter(recipeIngredientAdapter);
                recipeIngredientAdapter.notifyDataSetChanged();

                List<RecipeDescription> recipeDescriptions = recipeModel.getRecipeDescriptionList();
                recipeStepAdapter = new RecipeStepAdapter(recipeDescriptions, this);
                recipeInformationBinding.descriptionRecyclerView.setAdapter(recipeStepAdapter);
                recipeStepAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onRecipeStepInstructionClicked(RecipeDescription recipeDescription) {
        Intent intent = new Intent(this, RecipeDescriptionActivity.class);
        intent.putExtra("RECIPE_DESCRIPTION", recipeDescription);
        this.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemSelected = item.getItemId();
        if (itemSelected == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
