package com.eniola.bakeit.UIs.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eniola.bakeit.databinding.ItemRecipeIngredientBinding;
import com.eniola.bakeit.models.RecipeIngredient;

import java.util.List;

public class RecipeIngredientAdapter extends RecyclerView.Adapter<RecipeIngredientAdapter.RecipeIngredientViewHolder> {

    List<RecipeIngredient> recipeIngredientsList;

    public RecipeIngredientAdapter(List<RecipeIngredient> recipeIngredients){
        this.recipeIngredientsList = recipeIngredients;
    }

    @NonNull
    @Override
    public RecipeIngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemRecipeIngredientBinding recipeIngredientBinding = ItemRecipeIngredientBinding.inflate(
                layoutInflater, parent, false);
        return new RecipeIngredientViewHolder(recipeIngredientBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeIngredientViewHolder holder, int position) {
        final RecipeIngredient recipeIngredient = recipeIngredientsList.get(position);
        holder.bindIngredientDetailsToView(recipeIngredient);
    }

    @Override
    public int getItemCount() {
        return recipeIngredientsList.size();
    }

    public class RecipeIngredientViewHolder extends RecyclerView.ViewHolder{

        private final ItemRecipeIngredientBinding itemRecipeIngredientBinding;

        public RecipeIngredientViewHolder(ItemRecipeIngredientBinding recipeIngredientBinding){
            super(recipeIngredientBinding.getRoot());
            this.itemRecipeIngredientBinding = recipeIngredientBinding;
        }

        private void bindIngredientDetailsToView(final RecipeIngredient recipeIngredient){
            itemRecipeIngredientBinding.recipeQuantityValue.setText(recipeIngredient.getQuantity());
            itemRecipeIngredientBinding.recipeMeasureValue.setText(recipeIngredient.getMeasure());
            itemRecipeIngredientBinding.ingredientTextView.setText(recipeIngredient.getIngredient());
        }
    }
}

