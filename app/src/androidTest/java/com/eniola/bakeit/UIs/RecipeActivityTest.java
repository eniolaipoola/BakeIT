package com.eniola.bakeit.UIs;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.eniola.bakeit.R;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class RecipeActivityTest {

    public static final String RECIPE_NAME = "Nutella Pie";

    @Rule public ActivityTestRule<RecipeActivity> mActivityTestRule =
            new ActivityTestRule<>(RecipeActivity.class);

    @Test
    public void testForRecipeViewClick(){
        onView(withId(R.id.grid_recycler_view))
                .inRoot(RootMatchers.withDecorView(Matchers.is(mActivityTestRule.getActivity().getWindow().getDecorView()))).perform(
                        RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    @Test
    public void testThatRecipeRecyclerViewIsScrollable(){
        //launchers recipe activity and scrolls to the last item on the view
        RecyclerView recyclerView = mActivityTestRule.getActivity().findViewById(R.id.recipe_item_view);
        int itemCount = recyclerView.getAdapter().getItemCount();

        onView(withId(R.id.recipe_item_view)).inRoot(RootMatchers.withDecorView(
                Matchers.is(mActivityTestRule.getActivity().getWindow().getDecorView())))
                .perform(RecyclerViewActions.scrollToPosition(itemCount-1));

    }

    @Test
    public void testThatRecipeItemViewIsDisplayed(){
        onView(withId(R.id.grid_recycler_view)).inRoot(RootMatchers.withDecorView(
                Matchers.is(mActivityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(atPosition(1, Matchers.allOf(withId(R.id.recipe_item_view), isDisplayed()))));
    }

}