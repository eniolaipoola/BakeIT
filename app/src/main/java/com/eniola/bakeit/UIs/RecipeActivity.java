package com.eniola.bakeit.UIs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.GridLayout;
import android.widget.Toast;

import com.eniola.bakeit.R;
import com.eniola.bakeit.data.models.RecipeModel;
import com.eniola.bakeit.data.networking.APIClient;
import com.eniola.bakeit.data.networking.APIService;
import com.eniola.bakeit.data.networking.RecipeData;
import com.eniola.bakeit.data.networking.RecipeDataInterface;
import com.eniola.bakeit.databinding.ActivityRecipeBinding;
import com.eniola.bakeit.utilities.APPConstant;
import com.eniola.bakeit.utilities.APPUtility;

public class RecipeActivity extends AppCompatActivity implements RecipeDataInterface.OnRecipeFetchedListener {
    ActivityRecipeBinding activityRecipeBinding;
    APPUtility appUtility;
    Context mContext;
    APIService apiService;
    APIClient apiClient;
    RecipeData recipeData;
    private RecipeAdapter recipeAdapter;
    RecyclerView recyclerView;

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
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, calculateNumberOfColumns(this),
                    GridLayoutManager.HORIZONTAL, false);
            activityRecipeBinding.gridRecyclerView.setLayoutManager(gridLayoutManager);
            activityRecipeBinding.gridRecyclerView.setHasFixedSize(true);
        } else {
            Toast.makeText(mContext, "Internet is not available", Toast.LENGTH_LONG).show();
        }
    }

    public static int calculateNumberOfColumns(Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 200;
        int noOfColumns = (int) (dpWidth / scalingFactor);

        if(noOfColumns < 2){
            noOfColumns = 2;
        }
        return  noOfColumns;
    }

    @Override
    public void onRecipeSuccessful(RecipeModel recipeModel) {

        Toast.makeText(mContext, "Recipes fetched successfully", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRecipeFailed(String errorMessage) {
        Toast.makeText(mContext, "Cannot fetch recipes", Toast.LENGTH_LONG).show();
    }
}
