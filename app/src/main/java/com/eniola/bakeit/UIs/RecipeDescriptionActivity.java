package com.eniola.bakeit.UIs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eniola.bakeit.R;
import com.eniola.bakeit.databinding.ActivityRecipeDescriptionBinding;
import com.eniola.bakeit.models.RecipeDescription;

public class RecipeDescriptionActivity extends AppCompatActivity {

    ActivityRecipeDescriptionBinding recipeDescriptionBinding;
    int currenStepId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recipeDescriptionBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_description);
        recipeDescriptionBinding.getRoot();
        getRecipeInstruction();
    }

    public void getRecipeInstruction(){
        Intent intent = getIntent();
        if(intent != null){
            RecipeDescription recipeDescription =
                    (RecipeDescription) intent.getSerializableExtra("RECIPE_DESCRIPTION");
            if(recipeDescription != null){
                currenStepId = recipeDescription.getId();
                recipeDescriptionBinding.recipeInstructionTextView.setText(recipeDescription.getDescription());
                recipeDescriptionBinding.navigationPrevStep.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

                recipeDescriptionBinding.navigationNextStep.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }
        }
    }

    public void getNextStepInstruction(){

    }

    public void getPreviousStepInstruction(){

    }
}
