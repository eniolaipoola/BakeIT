package com.eniola.bakeit.UIs;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
            recipeDescriptionBinding.navigationPrevStep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("debug", "current step is " + currentStepId );
                    currentStepId = currentStepId++;
                    getCurrentStenInstructions(currentStepId);
                    Log.d("debug", "current step is " + currentStepId);
                }
            });

            recipeDescriptionBinding.navigationNextStep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("debug", "current step is " + currentStepId );
                    currentStepId = currentStepId--;
                    getCurrentStenInstructions(currentStepId);
                    Log.d("debug", "current step is " + currentStepId );
                }
            });
        }
    }

    public void getCurrentStenInstructions(int currentStepId){
        int nextStepId = currentStepId + 1;
        List<RecipeDescription> recipeDescriptions = recipeModel.getRecipeDescriptionList();
        recipeDescriptionBinding.recipeInstructionTextView.setText(recipeDescriptions.get(nextStepId).getDescription());
    }
}
