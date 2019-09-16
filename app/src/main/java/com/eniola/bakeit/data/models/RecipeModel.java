package com.eniola.bakeit.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeModel {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("ingredients")
    @Expose
    private List<RecipeIngredient> recipeIngredientList;

    @SerializedName("steps")
    @Expose
    private List<RecipeDescription> recipeDescriptionList;

    @SerializedName("servings")
    @Expose
    private String servings;

    @SerializedName("image")
    @Expose
    private String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RecipeIngredient> getRecipeIngredientList() {
        return recipeIngredientList;
    }

    public void setRecipeIngredientList(List<RecipeIngredient> recipeIngredientList) {
        this.recipeIngredientList = recipeIngredientList;
    }

    public List<RecipeDescription> getRecipeDescriptionList() {
        return recipeDescriptionList;
    }

    public void setRecipeDescriptionList(List<RecipeDescription> recipeDescriptionList) {
        this.recipeDescriptionList = recipeDescriptionList;
    }

    public String getServings() {
        return servings;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
