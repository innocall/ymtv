package com.lemon95.ymtv.bean;

/**
 * Created by wuxiaotie on 16/7/23.
 */
public class MovieSources {
    private String MovieId;
    private String OriginId;
    private String SourceUrl;
    private String RealSource;

    public String getMovieId() {
        return MovieId;
    }

    public void setMovieId(String movieId) {
        MovieId = movieId;
    }

    public String getOriginId() {
        return OriginId;
    }

    public void setOriginId(String originId) {
        OriginId = originId;
    }

    public String getSourceUrl() {
        return SourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        SourceUrl = sourceUrl;
    }

    public String getRealSource() {
        return RealSource;
    }

    public void setRealSource(String realSource) {
        RealSource = realSource;
    }
}
