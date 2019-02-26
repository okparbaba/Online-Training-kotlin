package com.greenhacker.greenhackeronlinetraining.caches

interface CacheUtils {
    companion object {
        //cache manager file names
        val POPULAR = "popular.json"
        val LATEST = "latest.json"
        val TOP_WATCHED = "top_watched.json"
        val SEARCH = "search.json"
        val RELATED = "related.json"
        val CHANNEL_VIDEOS = "channel_video.json"
    }
}