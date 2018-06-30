package com.maxcheung.controllers;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.maxcheung.datatable.service.ThirdPartyYoutubeLib;
import com.maxcheung.models.Video;

@RestController
@RequestMapping("youtube")
public class YouTubeRestController {

	private static final Logger LOG = LoggerFactory.getLogger(YouTubeRestController.class);

    @Autowired
    private ThirdPartyYoutubeLib  thirdPartyYoutubeLib;
    
    @RequestMapping(value = "/getVideo/{videoId}", method = RequestMethod.GET)
    @ResponseBody
    public Video getVideo(@PathVariable String videoId) {
    	LOG.info("Get getVideo");
		Video video = thirdPartyYoutubeLib.getVideo(videoId);
        return video;
    }
 

    @RequestMapping(value = "/popularVideos", method = RequestMethod.GET)
    @ResponseBody
    public HashMap<String, Video> popularVideos() {
    	LOG.info("Get popularVideos");
		return thirdPartyYoutubeLib.popularVideos();
    }


}