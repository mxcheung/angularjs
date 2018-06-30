package com.maxcheung.datatable.service;

import java.util.HashMap;

import com.maxcheung.models.Video;

public interface ThirdPartyYoutubeLib {
    public HashMap<String, Video> popularVideos();

    public Video getVideo(String videoId);
}