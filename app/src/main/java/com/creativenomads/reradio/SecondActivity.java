package com.creativenomads.reradio;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class SecondActivity extends AppCompatActivity {

    private ExoPlayer exoPlayer;
    private ExoPlayer.EventListener eventListener = new ExoPlayer.EventListener() {
        @Override
        public void onTimelineChanged(Timeline timeline, Object manifest) {
            Log.i(TAG,"onTimelineChanged");
        }

        @Override
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
            Log.i(TAG,"onTracksChanged");
                for (int i = 0; i < trackGroups.length; i++) {
                    TrackGroup trackGroup = trackGroups.get(i);
                    for (int j = 0; j < trackGroup.length; j++) {
                        Metadata trackMetadata = trackGroup.getFormat(j).metadata;
                        if (trackMetadata != null) {
                            Log.i(TAG,"onTracksChanged  we found metadata");
                        }
                    }
                }



        }

        @Override
        public void onLoadingChanged(boolean isLoading) {
            Log.i(TAG,"onLoadingChanged");
        }

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            Log.i(TAG,"onPlayerStateChanged: playWhenReady = "+String.valueOf(playWhenReady)
                    +" playbackState = "+playbackState);
            switch (playbackState){
                case ExoPlayer.STATE_ENDED:
                    Log.i(TAG,"Playback ended!");
                    //Stop playback and return to start position
                    setPlayPause(true);
//                    exoPlayer.seekTo(0);
                    break;
                case ExoPlayer.STATE_READY:
//                    Log.i(TAG,"ExoPlayer ready! pos: "+exoPlayer.getCurrentPosition()
//                            +" max: "+stringForTime((int)exoPlayer.getDuration()));
//                    setProgress();
                    break;
                case ExoPlayer.STATE_BUFFERING:
                    Log.i(TAG,"Playback buffering!");
                    break;
                case ExoPlayer.STATE_IDLE:
                    Log.i(TAG,"ExoPlayer idle!");
                    break;
            }
        }

        @Override
        public void onPlayerError(ExoPlaybackException error) {
            Log.i(TAG,"onPlaybackError: "+error.getMessage());
        }

        @Override
        public void onPositionDiscontinuity() {
            Log.i(TAG,"onPositionDiscontinuity");
        }
    };
    private SeekBar seekPlayerProgress;
    private Handler handler;
    private ImageButton btnPlay;
    private TextView txtCurrentTime, txtEndTime;
    private boolean isPlaying = false;

    private static final String TAG = "SecondActivity";

    ImageView selectedImage;
    TextView tv;
    String link_bundle = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide();
        setContentView(R.layout.activity_second);
        selectedImage = findViewById(R.id.selectedImage);  // init a ImageView
        tv = findViewById(R.id.textView);  //
        Intent intent = getIntent(); // get Intent which we set from Previous Activity
        Bundle bundle = intent.getExtras();
        selectedImage.setImageResource(bundle.getInt("image")); // get image from Intent and set it in ImageView
        tv.setText(bundle.getString("c_name"));


        link_bundle =bundle.getString("c_link");
        ///

        prepareExoPlayerFromURL(Uri.parse(link_bundle));
        ///
    }
//

    @Override
    public void onStart() {
        super.onStart();

        if ((Util.SDK_INT <= 21 || exoPlayer == null)) {
            prepareExoPlayerFromURL(Uri.parse(link_bundle));
                Intent intent = new Intent(SecondActivity.this, MyService.class);
                intent.setAction(MyService.ACTION_START_FOREGROUND_SERVICE);
                startService(intent);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if ((Util.SDK_INT <= 23 || exoPlayer == null)) {
//            prepareExoPlayerFromURL(Uri.parse(link_bundle));
//            setPlayPause(true);
        }
    }
    @Override
    public void onPause() {
        super.onPause();
//        setPlayPause(false);
    }

    @Override
    public void onStop() {
        super.onStop();

        if (Util.SDK_INT > 23) {
//            exoPlayer.release();
//            exoPlayer = null;
        }
    }



    /**
     * Prepares exoplayer for audio playback from a remote URL audiofile. Should work with most
     * popular audiofile types (.mp3, .m4a,...)
     * @param uri Provide a Uri in a form of Uri.parse("http://blabla.bleble.com/blublu.mp3)
     */

    private void prepareExoPlayerFromURL(Uri uri){

        TrackSelector trackSelector = new DefaultTrackSelector();

        LoadControl loadControl = new DefaultLoadControl();

        exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);

        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "exoplayer2example"), null);
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        MediaSource audioSource = new ExtractorMediaSource(uri, dataSourceFactory, extractorsFactory, null, null);
        exoPlayer.addListener(eventListener);

        exoPlayer.prepare(audioSource);
        final Holder globalVariable = (Holder) getApplicationContext();
        globalVariable.setExoPlayer(exoPlayer);

        Intent intent2 = new Intent(SecondActivity.this, MyService.class);
        intent2.setAction(MyService.ACTION_START_FOREGROUND_SERVICE);
        startService(intent2);


        initMediaControls();
        setPlayPause(true);
    }

    private void initMediaControls() {
        initPlayButton();
    }

    private void initPlayButton() {
        btnPlay = (ImageButton) findViewById(R.id.btnplay);
        btnPlay.requestFocus();
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPlayPause(!isPlaying);
            }
        });
    }

    /**
     * Starts or stops playback. Also takes care of the Play/Pause button toggling
     * @param play True if playback should be started
     */
    private void setPlayPause(boolean play){
        isPlaying = play;
        exoPlayer.setPlayWhenReady(play);
        if(!isPlaying){
            btnPlay.setImageResource(R.mipmap.play);
        }else{
//            setProgress();
            btnPlay.setImageResource(R.mipmap.pause);
        }
    }




}