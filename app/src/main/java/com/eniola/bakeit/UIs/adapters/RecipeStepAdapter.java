package com.eniola.bakeit.UIs.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eniola.bakeit.databinding.ItemRecipeStepBinding;
import com.eniola.bakeit.models.OnRecipeStepInstructionClickedListener;
import com.eniola.bakeit.models.RecipeDescription;

import java.util.List;

public class RecipeStepAdapter  extends RecyclerView.Adapter<RecipeStepAdapter.RecipeStepViewHolder> {

    List<RecipeDescription> recipeDescriptionList;
    private OnRecipeStepInstructionClickedListener stepInstructionClickedListener;

    public RecipeStepAdapter(List<RecipeDescription> recipeDescriptions, OnRecipeStepInstructionClickedListener
                             recipeStepInstructionClickedListener){
        this.recipeDescriptionList = recipeDescriptions;
        this.stepInstructionClickedListener = recipeStepInstructionClickedListener;
    }

    @NonNull
    @Override
    public RecipeStepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemRecipeStepBinding recipeStepBinding = ItemRecipeStepBinding.inflate(layoutInflater,
                parent, false);
        return new RecipeStepViewHolder(recipeStepBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeStepViewHolder holder, int position) {
        final RecipeDescription recipeDescription = recipeDescriptionList.get(position);
        holder.bindDescriptionToView(recipeDescription, stepInstructionClickedListener);
    }

    @Override
    public int getItemCount() {
        return recipeDescriptionList.size();
    }

    public class RecipeStepViewHolder extends RecyclerView.ViewHolder{

        private ItemRecipeStepBinding itemRecipeStepBinding;

        public RecipeStepViewHolder(ItemRecipeStepBinding recipeStepBinding){
            super(recipeStepBinding.getRoot());
            this.itemRecipeStepBinding = recipeStepBinding;

        }

        private void bindDescriptionToView(final RecipeDescription recipeDescription,
                                           final OnRecipeStepInstructionClickedListener stepInstructionClickedListener){
            itemRecipeStepBinding.stepNumberTextView.setText(String.valueOf(recipeDescription.getId()));
            itemRecipeStepBinding.recipeShortDescriptionTextView.setText(recipeDescription.getShortDescription());
            itemRecipeStepBinding.recipeShortDescriptionTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    stepInstructionClickedListener.onRecipeStepInstructionClicked(recipeDescription);
                }
            });
        }
    }
}
