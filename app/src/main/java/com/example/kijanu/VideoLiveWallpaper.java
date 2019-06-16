/**
 * Based on code from https://github.com/Cyunrei/Video-Live-Wallpaper
 */

package com.example.kijanu;

import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.SurfaceHolder;

import java.io.IOException;


public class VideoLiveWallpaper extends WallpaperService {
    public Engine onCreateEngine() {
        return new VideoEngine();
    }

    public static final String TAG = "VideoLiveWallpaper";

    public static void setToWallPaper(Context context) {
        final Intent intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
        intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                new ComponentName(context, VideoLiveWallpaper.class));
        context.startActivity(intent);
    }

    class VideoEngine extends Engine {
        VideoEngine() {
            Log.w(TAG, "VideoEngine");
        }
        private MediaPlayer mMediaPlayer;

        @Override
        public void onVisibilityChanged(boolean visible) {
//            Log.w(TAG, "onVisibilityChanged visible:" + visible);
            if (visible) {
                mMediaPlayer.start();
            } else {
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            }
        }

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
//            Log.w(TAG, "onSurfaceCreated" + holder.toString());
            super.onSurfaceCreated(holder);

            mMediaPlayer = new MediaPlayer();
//            mView = new VideoSurfaceView(VideoLiveWallpaper.this, mMediaPlayer, holder);
            mMediaPlayer.setSurface(holder.getSurface());
            try {
                Uri introURI = Uri.parse("android.resource://com.example.kijanu/" + R.raw.kijanu);
//                Log.w(TAG, "introURI" + introURI.toString());
                mMediaPlayer.setDataSource(VideoLiveWallpaper.this, introURI);
                mMediaPlayer.setLooping(false);
                mMediaPlayer.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
                mMediaPlayer.setVolume(0, 0);
                mMediaPlayer.prepare();
                mMediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}