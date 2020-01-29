package com.eniola.bakeit.UIs.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.eniola.bakeit.databinding.ItemRecipeBinding;
import com.eniola.bakeit.models.OnRecipeSelectedListener;
import com.eniola.bakeit.models.RecipeModel;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>  {

    private OnRecipeSelectedListener recipeSelectedListener;
    private List<RecipeModel> recipeModelList;

    public RecipeAdapter(List<RecipeModel> recipeModel,
                         OnRecipeSelectedListener recipeSelectedListener){
        this.recipeModelList = recipeModel;
        this.recipeSelectedListener = recipeSelectedListener;
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
        holder.bindDataToView(recipe, recipeSelectedListener);
    }

    @Override
    public int getItemCount() {
        return recipeModelList.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        private final ItemRecipeBinding itemRecipeBinding;

        private RecipeViewHolder(ItemRecipeBinding recipeBinding){
            super(recipeBinding.getRoot());
            this.itemRecipeBinding = recipeBinding;
        }

        private void bindDataToView(final RecipeModel recipeModel,
                                   final OnRecipeSelectedListener recipeSelectedListener){
            String recipeName = recipeModel.getName();
            String recipeServings = recipeModel.getServings();
            itemRecipeBinding.recipeNameTextView.setText(recipeName);
            itemRecipeBinding.recipeImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recipeSelectedListener.onRecipeSelected(recipeModel);
                }
            });
        }
    }
}
