package com.citrus.suzaku;
//
// Created by $USER_NAME on 2017/05/17

public class TagLibHelper
{
    static{
        System.loadLibrary("taglib_wrapper");
    }

    private long fileRefHandle;

    private long tagHandle;
    private long tagsHandle;

    public native void setFile(String path_);
    public native void dumpTags();

    public native String getTitle();
    public native String getTitleSort();
    public native String getArtist();
    public native String getArtistSort();
    public native String getAlbum();
    public native String getAlbumSort();
    public native String getAlbumArtist();
    public native String getAlbumArtistSort();
    public native String getGenre();
    public native String getComposer();
    public native int getYear();
    public native String getLyrics();
    public native String getComment();
    public native String getGroup();
    public native int getTrackNumber();
    public native int getDiscNumber();
    public native boolean getCompilation();
/*
    public native int getLength();
    public native int getBitrate();
    public native int getSampleRate();
    public native int getChannel();
*/
    public native byte[] getArtwork();

    public native void release();
}
