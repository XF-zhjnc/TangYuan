package io.github.xf_zhjnc.tangyuantv

import tv.danmaku.ijk.media.player.IMediaPlayer

/**
 * NAME: 柚子啊
 * DATE: 2021/1/5
 * DESC:
 */
interface TYVideoListener : IMediaPlayer.OnBufferingUpdateListener, IMediaPlayer.OnCompletionListener
                            , IMediaPlayer.OnPreparedListener, IMediaPlayer.OnInfoListener
                            , IMediaPlayer.OnVideoSizeChangedListener, IMediaPlayer.OnErrorListener
                            , IMediaPlayer.OnSeekCompleteListener {

}