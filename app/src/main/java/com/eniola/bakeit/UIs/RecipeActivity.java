package com.eniola.bakeit.UIs;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import com.eniola.bakeit.R;
import com.eniola.bakeit.UIs.adapters.RecipeAdapter;
import com.eniola.bakeit.UIs.fragments.AppErrorViewFragment;
import com.eniola.bakeit.UIs.fragments.AppLoadingViewFragment;
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
    FragmentTransaction fragmentTransaction;
    GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRecipeBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipe);

        appUtility = new APPUtility();
        mContext = RecipeActivity.this;
        apiClient = new APIClient();
        apiService = apiClient.getRetrofit(APPConstant.BASE_URL).create(APIService.class);
        recipeData = new RecipeData(apiService);
        setTitle(R.string.baking_time);

        if(appUtility.isInternetAvailable(mContext)){
            recipeData.getRecipes(this);
            if(findViewById(R.id.loadingViewCard) == null){
                showLoadingDialogFragment();
            }
            boolean isPhone = getResources().getBoolean(R.bool.is_phone);
            if(isPhone){
                gridLayoutManager = new GridLayoutManager(this,
                        1, GridLayoutManager.VERTICAL, false);
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE);
                gridLayoutManager = new GridLayoutManager(this,
                        3, GridLayoutManager.VERTICAL, false);
            }
            activityRecipeBinding.gridRecyclerView.setLayoutManager(gridLayoutManager);
            activityRecipeBinding.gridRecyclerView.setHasFixedSize(true);
        } else {
            showErrorDialogFragment("Please check your Internet Connection");
        }
    }

    @Override
    public void onRecipeSuccessful(List<RecipeModel> recipeModel) {
        removeDialogFragment(AppLoadingViewFragment.class.getName());
        recipeAdapter = new RecipeAdapter(recipeModel, this);
        recipeAdapter.notifyDataSetChanged();
        activityRecipeBinding.gridRecyclerView.setAdapter(recipeAdapter);
    }

    @Override
    public void onRecipeFailed(String errorMessage) {
        showErrorDialogFragment("Cannot fetch recipes");
        Toast.makeText(mContext, "Cannot fetch recipes", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRecipeSelected(RecipeModel recipeModel) {
        Intent intent = new Intent(mContext, RecipeInformationActivity.class);
        intent.putExtra("RECIPE", recipeModel);
        mContext.startActivity(intent);
    }

    //View Utility methods
    private void removeDialogFragment(String fragmentTag) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        DialogFragment dialogFragment = (DialogFragment) getSupportFragmentManager().findFragmentByTag(fragmentTag);
        if(dialogFragment != null){
            fragmentTransaction.
                    remove(dialogFragment).commit();
        }
    }

    private void showLoadingDialogFragment(){
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment loadingFragment = getSupportFragmentManager().findFragmentByTag(AppLoadingViewFragment.class.getName());
        if(loadingFragment != null){
            fragmentTransaction.remove(loadingFragment);
        }
        fragmentTransaction.addToBackStack(null);
        DialogFragment appLoadingViewFragment =
                AppLoadingViewFragment.newInstance("Loading");
        appLoadingViewFragment.show(fragmentTransaction, AppLoadingViewFragment.class.getName());
    }

    private void showErrorDialogFragment(String errorMessage){
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment errorFragment = getSupportFragmentManager().findFragmentByTag(AppErrorViewFragment.class.getName());
        if(errorFragment != null){
            fragmentTransaction.remove(errorFragment);
        }
        fragmentTransaction.addToBackStack(null);
        AppErrorViewFragment.newInstance(errorMessage).show(fragmentTransaction,
                AppErrorViewFragment.class.getName());
    }
}
