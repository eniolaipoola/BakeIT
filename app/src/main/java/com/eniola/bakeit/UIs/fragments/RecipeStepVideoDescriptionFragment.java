package com.eniola.bakeit.UIs.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import com.eniola.bakeit.R;
import com.eniola.bakeit.databinding.FragmentRecipeStepVideoDescriptionBinding;
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

public class RecipeStepVideoDescriptionFragment extends Fragment implements ExoPlayer.EventListener {

    private String recipeName;
    private int currentStepId;
    private SimpleExoPlayer exoPlayer;
    private SimpleExoPlayerView exoPlayerView;
    private String recipeVideoUrl, description;
    private Context mContext;
    private RecipeModel recipeModel;
    private MediaSessionCompat mediaSessionCompat;
    private static final String TAG = "media_session_tag";
    private PlaybackStateCompat.Builder stateCompatBuilder;
    private RecipeDescription recipeDescription;
    private FragmentRecipeStepVideoDescriptionBinding videoDescriptionBinding;

    public RecipeStepVideoDescriptionFragment() {
        // Required empty public constructor
    }

    public static RecipeStepVideoDescriptionFragment newInstance(RecipeModel recipeModel, RecipeDescription recipeDescription){
        RecipeStepVideoDescriptionFragment recipeStepVideoDescriptionFragment = new RecipeStepVideoDescriptionFragment();
        Bundle args = new Bundle();
        args.putSerializable("RECIPE_MODEL", recipeModel);
        args.putSerializable("RECIPE_DESCRIPTION", recipeDescription);
        recipeStepVideoDescriptionFragment.setArguments(args);
        return recipeStepVideoDescriptionFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        videoDescriptionBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_recipe_step_video_description, container, false);
        View rootView = videoDescriptionBinding.getRoot();
        mContext = getActivity();

        //set up media session
        mediaSessionCompat = new MediaSessionCompat(mContext.getApplicationContext(), TAG);
        mediaSessionCompat.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mediaSessionCompat.setMediaButtonReceiver(null);
        stateCompatBuilder = new PlaybackStateCompat.Builder().setActions(PlaybackStateCompat.ACTION_PLAY |
                PlaybackStateCompat.ACTION_PLAY_PAUSE | PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
                | PlaybackStateCompat.ACTION_SKIP_TO_NEXT);
        mediaSessionCompat.setPlaybackState(stateCompatBuilder.build());
        mediaSessionCompat.setCallback(new MediaSessionCallback());
        mediaSessionCompat.setActive(true);
        exoPlayerView = rootView.findViewById(R.id.fragment_playerView);

        if(getArguments() != null) {
            recipeModel = (RecipeModel) getArguments().getSerializable("RECIPE_MODEL");
            recipeDescription = (RecipeDescription) getArguments().getSerializable("RECIPE_DESCRIPTION");
            recipeName = recipeModel.getName();
            if (recipeDescription != null) {
                currentStepId = recipeDescription.getId();
                recipeVideoUrl = recipeDescription.getVideoURL();
                description = recipeDescription.getDescription();
                videoDescriptionBinding.fragmentInstructionTextView.setText(description);
                releasePlayer();
                initiateMediaPlayer(Uri.parse(recipeVideoUrl));
            }
        }

        videoDescriptionBinding.nextStep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    currentStepId = currentStepId + 1;
                    getCurrentStepInstructions(currentStepId);
                }
        });

        videoDescriptionBinding.prevStep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    currentStepId = currentStepId - 1;
                    getCurrentStepInstructions(currentStepId);
                }
            });

        return rootView;
    }

    private void getCurrentStepInstructions(int currentStepId){
        List<RecipeDescription> recipeDescriptions = recipeModel.getRecipeDescriptionList();
        int recipeDescriptionSize = recipeDescriptions.size();
        if(currentStepId < recipeDescriptionSize){
            recipeVideoUrl = recipeDescriptions.get(currentStepId).getVideoURL();
            releasePlayer();
            initiateMediaPlayer(Uri.parse(recipeVideoUrl));
            videoDescriptionBinding.fragmentInstructionTextView.setText(recipeDescriptions.get(currentStepId).getDescription());
        }
    }

    /** Initialize media player*/
    private void initiateMediaPlayer(Uri mediaUri){
        if(exoPlayer == null){
            //create an exoPlayer instance
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector, loadControl);
            exoPlayerView.setPlayer(exoPlayer);
            //prepare the media source
            String userAgent = Util.getUserAgent(mContext, "Recipe Instruction Description");
            MediaSource videoSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(mContext, userAgent),
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
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if(playbackState == ExoPlayer.STATE_READY && playWhenReady){
            stateCompatBuilder.setState(PlaybackStateCompat.STATE_PLAYING, exoPlayer.getCurrentPosition(), 1f);
            mediaSessionCompat.setPlaybackState(stateCompatBuilder.build());

        } else if(playbackState == ExoPlayer.STATE_READY) {
            stateCompatBuilder.setState(PlaybackStateCompat.STATE_PAUSED, exoPlayer.getCurrentPosition(), 1f);
        }
        mediaSessionCompat.setPlaybackState(stateCompatBuilder.build());
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

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaSessionCompat.setActive(false);
    }
}
