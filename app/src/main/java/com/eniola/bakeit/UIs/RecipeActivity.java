package com.eniola.bakeit.UIs;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.eniola.bakeit.R;
import com.eniola.bakeit.data.APIClient;
import com.eniola.bakeit.data.APIService;
import com.eniola.bakeit.data.RecipeData;
import com.eniola.bakeit.data.RecipeDataInterface;
import com.eniola.bakeit.databinding.ActivityRecipeBinding;
import com.eniola.bakeit.models.OnRecipeSelectedListener;
import com.eniola.bakeit.models.RecipeModel;
import com.eniola.bakeit.utilities.APPConstant;
import com.eniola.bakeit.utilities.APPUtility;

import java.util.List;

public class RecipeActivity extends AppCompatActivity implements RecipeDataInterface.OnRecipeFetchedListener,
        OnRecipeSelectedListener {

    ActivityRecipeBinding activityRecipeBinding;
    APPUtility appUtility;
    Context mContext;
    APIService apiService;
    APIClient apiClient;
    RecipeData recipeData;
    private RecipeAdapter recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRecipeBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipe);

        appUtility = new APPUtility();
        mContext = RecipeActivity.this;
        apiClient = new APIClient();
        apiService = apiClient.getRetrofit(APPConstant.BASE_URL).create(APIService.class);
        recipeData = new RecipeData(apiService);

        if(appUtility.isInternetAvailable(mContext)){
            recipeData.getRecipes(this);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            activityRecipeBinding.gridRecyclerView.setLayoutManager(linearLayoutManager);
            activityRecipeBinding.gridRecyclerView.setHasFixedSize(true);
        } else {
            Toast.makeText(mContext, "Internet is not available", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRecipeSuccessful(List<RecipeModel> recipeModel) {
        Toast.makeText(mContext, "Recipes fetched successfully", Toast.LENGTH_LONG).show();
        recipeAdapter = new RecipeAdapter(recipeModel, this);
        recipeAdapter.notifyDataSetChanged();
        activityRecipeBinding.gridRecyclerView.setAdapter(recipeAdapter);
    }

    @Override
    public void onRecipeFailed(String errorMessage) {
        Toast.makeText(mContext, "Cannot fetch recipes", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRecipeSelected(RecipeModel recipeModel) {}
}
