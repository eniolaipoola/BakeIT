package com.eniola.bakeit.UIs;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.eniola.bakeit.R;
import com.eniola.bakeit.databinding.ActivityRecipeDescriptionBinding;
import com.eniola.bakeit.models.RecipeDescription;
import com.eniola.bakeit.models.RecipeModel;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import java.util.List;

public class RecipeInformationDescription extends AppCompatActivity {

    ActivityRecipeDescriptionBinding recipeDescriptionBinding;
    int currentStepId;
    RecipeDescription recipeDescription;
    RecipeModel recipeModel;
    String recipeName;
    private SimpleExoPlayer exoPlayer;
    private SimpleExoPlayerView exoPlayerView;
    String recipeVideoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recipeDescriptionBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_description);
        recipeDescriptionBinding.getRoot();
        Intent intent = getIntent();
        exoPlayerView = (SimpleExoPlayerView) findViewById(R.id.playerView);
        if(intent != null){
            recipeDescription =
                    (RecipeDescription) intent.getSerializableExtra("RECIPE_DESCRIPTION");
            recipeModel = (RecipeModel) intent.getSerializableExtra("RECIPE_MODEL");
            recipeName = recipeModel.getName();
        }
        getRecipeInstruction();

        recipeDescriptionBinding.nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentStepId = currentStepId + 1;
                getCurrentStepInstructions(currentStepId);
            }
        });

        recipeDescriptionBinding.prevStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentStepId = currentStepId - 1;
                getCurrentStepInstructions(currentStepId);
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setTitle(recipeName);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemSelected = item.getItemId();
        if (itemSelected == android.R.id.home) {
            onBackPressed();
            releasePlayer();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getRecipeInstruction(){
        if(recipeDescription != null){
            currentStepId = recipeDescription.getId();
            recipeVideoUrl = recipeDescription.getVideoURL();
            recipeDescriptionBinding.recipeInstructionTextView.setText(recipeDescription.getDescription());
            initiateMediaPlayer(Uri.parse(recipeVideoUrl));
        }
    }

    public void getCurrentStepInstructions(int currentStepId){
        List<RecipeDescription> recipeDescriptions = recipeModel.getRecipeDescriptionList();
        recipeVideoUrl = recipeDescriptions.get(currentStepId).getVideoURL();
        Log.d("debug", "video url from method is " + recipeVideoUrl);
        Log.d("debug", "current text is " + currentStepId);
        int recipeDescriptionSize = recipeDescriptions.size();
        if(currentStepId < recipeDescriptionSize){
            releasePlayer();
            initiateMediaPlayer(Uri.parse(recipeVideoUrl));
            recipeDescriptionBinding.recipeInstructionTextView.setText(recipeDescriptions.get(currentStepId).getDescription());
        }
    }

    /** Initialize media player*/
    private void initiateMediaPlayer(Uri mediaUri){
        if(exoPlayer == null){
            //create an exoPlayer instance
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);
            exoPlayerView.setPlayer(exoPlayer);
            //prepare the media source
            String userAgent = Util.getUserAgent(this, "Recipe Instruction Description");
            MediaSource videoSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(this, userAgent),
                    new DefaultExtractorsFactory(), null, null);
            exoPlayer.prepare(videoSource);
            exoPlayer.setPlayWhenReady(true);

        }
    }

    /** Release media player*/
    private void releasePlayer(){
        if(exoPlayer != null){
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        releasePlayer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        releasePlayer();
    }
}
