package com.eniola.bakeit.UIs;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eniola.bakeit.data.models.RecipeModel;
import com.eniola.bakeit.data.networking.RecipeDataInterface;
import com.eniola.bakeit.databinding.ItemRecipeBinding;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>  {

    private RecipeDataInterface.OnRecipeFetchedListener recipeFetchedListener;
    private List<RecipeModel> recipeModelList;

    public RecipeAdapter(List<RecipeModel> recipeModel,
                         RecipeDataInterface.OnRecipeFetchedListener recipeFetchedListener){
        this.recipeModelList = recipeModel;
        this.recipeFetchedListener = recipeFetchedListener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemRecipeBinding itemRecipeBinding = ItemRecipeBinding.inflate(layoutInflater, parent, false);
        return new RecipeViewHolder(itemRecipeBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        final RecipeModel recipe = recipeModelList.get(position);
        holder.bindDataToView(recipe, recipeFetchedListener);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        private final ItemRecipeBinding itemRecipeBinding;

        private RecipeViewHolder(ItemRecipeBinding recipeBinding){
            super(recipeBinding.getRoot());
            this.itemRecipeBinding = recipeBinding;
        }

        private void bindDataToView(final RecipeModel recipeModel,
                                   RecipeDataInterface.OnRecipeFetchedListener recipeFetchedListener){
            String recipeName = recipeModel.getName();
            itemRecipeBinding.recipeNameTextView.setText(recipeName);

        }
    }
}
