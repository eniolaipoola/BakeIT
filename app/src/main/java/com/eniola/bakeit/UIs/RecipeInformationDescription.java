package com.eniola.bakeit.UIs;

import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.databinding.DataBindingUtil;
import androidx.media.session.MediaButtonReceiver;
import com.eniola.bakeit.R;
import com.eniola.bakeit.databinding.ActivityRecipeDescriptionBinding;
import com.eniola.bakeit.models.RecipeDescription;
import com.eniola.bakeit.models.RecipeModel;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayer;
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

public class RecipeInformationDescription extends AppCompatActivity implements ExoPlayer.EventListener {

    ActivityRecipeDescriptionBinding recipeDescriptionBinding;
    int currentStepId;
    RecipeDescription recipeDescription;
    RecipeModel recipeModel;
    String recipeName;
    private SimpleExoPlayer exoPlayer;
    private SimpleExoPlayerView exoPlayerView;
    String recipeVideoUrl;
    MediaSessionCompat mediaSessionCompat;
    private static final String TAG = "media_session_tag";
    PlaybackStateCompat.Builder stateCompatBuilder;
    private NotificationManager mNotificationManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recipeDescriptionBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_description);
        recipeDescriptionBinding.getRoot();

        //set up media session
        mediaSessionCompat = new MediaSessionCompat(this, TAG);
        mediaSessionCompat.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mediaSessionCompat.setMediaButtonReceiver(null);
        stateCompatBuilder = new PlaybackStateCompat.Builder().setActions(PlaybackStateCompat.ACTION_PLAY |
                PlaybackStateCompat.ACTION_PLAY_PAUSE | PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
                | PlaybackStateCompat.ACTION_SKIP_TO_NEXT);
        mediaSessionCompat.setPlaybackState(stateCompatBuilder.build());
        mediaSessionCompat.setCallback(new MediaSessionCallback());
        mediaSessionCompat.setActive(true);



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
        int recipeDescriptionSize = recipeDescriptions.size();
        if(currentStepId < recipeDescriptionSize){
            recipeVideoUrl = recipeDescriptions.get(currentStepId).getVideoURL();
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
            //mNotificationManger.cancelAll();
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaSessionCompat.setActive(false);
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if(playbackState == ExoPlayer.STATE_READY && playWhenReady){
            //We are playing
            Toast.makeText(this, "We are playing", Toast.LENGTH_LONG).show();
            stateCompatBuilder.setState(PlaybackStateCompat.STATE_PLAYING, exoPlayer.getCurrentPosition(), 1f);
            mediaSessionCompat.setPlaybackState(stateCompatBuilder.build());

        } else if(playbackState == ExoPlayer.STATE_READY) {
            //We are paused
            Toast.makeText(this, "We are paused", Toast.LENGTH_LONG).show();
            stateCompatBuilder.setState(PlaybackStateCompat.STATE_PAUSED, exoPlayer.getCurrentPosition(), 1f);
        }
        mediaSessionCompat.setPlaybackState(stateCompatBuilder.build());
        //showNotification(stateCompatBuilder.build());
    }

    private class MediaSessionCallback extends MediaSessionCompat.Callback {

        @Override
        public void onPlay() {
            super.onPlay();
        }

        @Override
        public void onPause() {
            super.onPause();
        }

        @Override
        public void onSkipToPrevious() {
            super.onSkipToPrevious();
        }

        @Override
        public void onSkipToNext() {
            super.onSkipToNext();
        }
    }

    private void showNotification(PlaybackStateCompat stateCompat){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        int icon;
        String play_pause;
        if(stateCompat.getState() == PlaybackStateCompat.STATE_PLAYING){
            icon = R.drawable.exo_controls_pause;
            play_pause = getString(R.string.exo_controls_play_description);
        } else {
            icon = R.drawable.exo_controls_pause;
            play_pause = getString(R.string.exo_controls_pause_description);
        }

        NotificationCompat.Action playPauseAction = new NotificationCompat.Action(icon, play_pause,
                MediaButtonReceiver.buildMediaButtonPendingIntent(this, PlaybackStateCompat.ACTION_PLAY_PAUSE));

        NotificationCompat.Action restartAction = new NotificationCompat.Action
                (R.drawable.exo_icon_previous, getString(R.string.exo_controls_repeat_one_description),
                        MediaButtonReceiver.buildMediaButtonPendingIntent(this, PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS));

        builder.setContentTitle(getString(R.string.ingredient))
                .setContentText(getString(R.string.app_name))
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .addAction(playPauseAction)
                .addAction(restartAction)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                .setMediaSession(mediaSessionCompat.getSessionToken())
                .setShowActionsInCompactView(0,1));

        mNotificationManger = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotificationManger.notify(0, builder.build());


    }
}
