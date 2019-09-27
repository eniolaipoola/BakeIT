package com.eniola.bakeit.UIs;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import com.eniola.bakeit.R;
import com.eniola.bakeit.UIs.adapters.RecipeIngredientAdapter;
import com.eniola.bakeit.UIs.adapters.RecipeStepAdapter;
import com.eniola.bakeit.UIs.fragments.RecipeInformationDescriptionFragment;
import com.eniola.bakeit.UIs.fragments.RecipeInformationFragment;
import com.eniola.bakeit.databinding.ActivityRecipeInformationBinding;
import com.eniola.bakeit.databinding.ActivityRecipeInformationTabletBinding;
import com.eniola.bakeit.models.OnRecipeStepInstructionClickedListener;
import com.eniola.bakeit.models.RecipeDescription;
import com.eniola.bakeit.models.RecipeIngredient;
import com.eniola.bakeit.models.RecipeModel;
import java.util.List;

public class RecipeInformationActivity extends AppCompatActivity implements OnRecipeStepInstructionClickedListener,
        RecipeInformationFragment.OnRecipeStepClickedListener {

    private RecipeIngredientAdapter recipeIngredientAdapter;
    private RecipeStepAdapter recipeStepAdapter;
    ActivityRecipeInformationBinding recipeInformationBinding;
    ActivityRecipeInformationTabletBinding recipeInformationTabletBinding;
    String recipeName;
    List<RecipeDescription> recipeDescriptions;
    RecipeModel recipeModel;
    GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isPhone = getResources().getBoolean(R.bool.is_phone);

        if(isPhone){
            recipeInformationBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_information);
            recipeInformationBinding.getRoot();
            getRecipeIngredient();

            gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL,
                    false);
            recipeInformationBinding.ingredientRecyclerView.setLayoutManager(gridLayoutManager);
            recipeInformationBinding.ingredientRecyclerView.setHasFixedSize(true);
            GridLayoutManager descriptionLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL,
                    false);
            recipeInformationBinding.descriptionRecyclerView.setLayoutManager(descriptionLayoutManager);
            recipeInformationBinding.descriptionRecyclerView.setHasFixedSize(true);

        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE);
            recipeInformationTabletBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_information_tablet);
            recipeInformationTabletBinding.getRoot();
            if(savedInstanceState != null){
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                RecipeInformationFragment informationFragment = new RecipeInformationFragment();
                fragmentTransaction.add(R.id.fragment_ingredient, informationFragment).commit();

                RecipeInformationDescriptionFragment descriptionFragment = new RecipeInformationDescriptionFragment();
                fragmentTransaction.add(R.id.fragment_step_description, descriptionFragment).commit();
            }
        }

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setTitle(recipeName);
    }

    public void getRecipeIngredient(){
        Intent intent = getIntent();
        if(intent != null){
            recipeModel = (RecipeModel) intent.getSerializableExtra("RECIPE");
            if(recipeModel != null){
                recipeName = recipeModel.getName();
                List<RecipeIngredient> recipeIngredients = recipeModel.getRecipeIngredientList();
                recipeIngredientAdapter = new RecipeIngredientAdapter(recipeIngredients);
                recipeInformationBinding.ingredientRecyclerView.setAdapter(recipeIngredientAdapter);
                recipeIngredientAdapter.notifyDataSetChanged();

                recipeDescriptions = recipeModel.getRecipeDescriptionList();
                recipeStepAdapter = new RecipeStepAdapter(recipeDescriptions, this);
                recipeInformationBinding.descriptionRecyclerView.setAdapter(recipeStepAdapter);
                recipeStepAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onRecipeStepInstructionClicked(RecipeDescription recipeDescription) {
        Intent intent = new Intent(this, RecipeInformationDescription.class);
        intent.putExtra("RECIPE_DESCRIPTION", recipeDescription);
        intent.putExtra("RECIPE_MODEL", recipeModel);
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

    @Override
    public void onRecipeStepClicked() {

    }
}
