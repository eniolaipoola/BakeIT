package com.eniola.bakeit.UIs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.eniola.bakeit.R;
import com.eniola.bakeit.databinding.ActivityRecipeDescriptionBinding;
import com.eniola.bakeit.models.RecipeDescription;
import com.eniola.bakeit.models.RecipeModel;

import java.util.List;

public class RecipeDescriptionActivity extends AppCompatActivity {

    ActivityRecipeDescriptionBinding recipeDescriptionBinding;
    int currentStepId;
    RecipeDescription recipeDescription;
    RecipeModel recipeModel;

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
        }
        getRecipeInstruction();
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
        recipeDescriptionBinding.recipeInstructionTextView.setText(recipeDescriptions.get(currentStepId).getDescription());
    }
}
