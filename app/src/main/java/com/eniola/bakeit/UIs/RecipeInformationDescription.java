package com.eniola.bakeit.UIs;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.eniola.bakeit.R;
import com.eniola.bakeit.databinding.ActivityRecipeDescriptionBinding;
import com.eniola.bakeit.models.RecipeDescription;
import com.eniola.bakeit.models.RecipeModel;
import java.util.List;

public class RecipeInformationDescription extends AppCompatActivity {

    ActivityRecipeDescriptionBinding recipeDescriptionBinding;
    int currentStepId;
    RecipeDescription recipeDescription;
    RecipeModel recipeModel;
    String recipeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recipeDescriptionBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_description);
        recipeDescriptionBinding.getRoot();
        Intent intent = getIntent();
        if(intent != null){
            recipeDescription =
                    (RecipeDescription) intent.getSerializableExtra("RECIPE_DESCRIPTION");
            recipeModel = (RecipeModel) intent.getSerializableExtra("RECIPE_MODEL");
            recipeName = recipeModel.getName();
        }

        getRecipeInstruction();
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setTitle(recipeName);
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

    public void getRecipeInstruction(){
        if(recipeDescription != null){
            currentStepId = recipeDescription.getId();
            recipeDescriptionBinding.recipeInstructionTextView.setText(recipeDescription.getDescription());
            recipeDescriptionBinding.nextStep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    currentStepId = currentStepId + 1;
                    getCurrentStepInstructions(currentStepId);

                }
            });

            recipeDescriptionBinding.prevStep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    currentStepId = currentStepId - 1;
                    getCurrentStepInstructions(currentStepId);
                }
            });
        }
    }

    public void getCurrentStepInstructions(int currentStepId){
        List<RecipeDescription> recipeDescriptions = recipeModel.getRecipeDescriptionList();
        int recipeDescriptionSize = recipeDescriptions.size();
        if(currentStepId < recipeDescriptionSize){
            recipeDescriptionBinding.recipeInstructionTextView.setText(recipeDescriptions.get(currentStepId).getDescription());
        }
    }
}
