package com.lemon95.ymtv.bean;

import java.util.List;

/**
 * Created by WXT on 2016/7/18.
 * 电影详情获取数据
 */
public class Movie {
    private String ReturnCode;
    private String ReturnMsg;
    private Data Data;

    public String getReturnCode() {
        return ReturnCode;
    }

    public void setReturnCode(String returnCode) {
        ReturnCode = returnCode;
    }

    public String getReturnMsg() {
        return ReturnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        ReturnMsg = returnMsg;
    }

    public Movie.Data getData() {
        return Data;
    }

    public void setData(Movie.Data data) {
        Data = data;
    }

    public static class Data{
        private String Id;
        private String MovieName;
        private String VideoGenreIds;
        private String VideoGenres;
        private String VideoAreaId;
        private String VideoAreaName;
        private String VideoLanguageId;
        private String VideoLanguageName;
        private String Director;
        private String Starring;
        private String Duration;
        private String ReleaseYear;
        private String PicturePath;
        private String Description;
        private String VIPLevel;
        private String Hot;
        private String Score;
        private String CommentCount;
        private String Enable;
        private String AddTime;
        private List<MovieSources> MovieSources;

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getMovieName() {
            return MovieName;
        }

        public void setMovieName(String movieName) {
            MovieName = movieName;
        }

        public String getVideoGenreIds() {
            return VideoGenreIds;
        }

        public void setVideoGenreIds(String videoGenreIds) {
            VideoGenreIds = videoGenreIds;
        }

        public String getVideoGenres() {
            return VideoGenres;
        }

        public void setVideoGenres(String videoGenres) {
            VideoGenres = videoGenres;
        }

        public String getVideoAreaId() {
            return VideoAreaId;
        }

        public void setVideoAreaId(String videoAreaId) {
            VideoAreaId = videoAreaId;
        }

        public String getVideoAreaName() {
            return VideoAreaName;
        }

        public void setVideoAreaName(String videoAreaName) {
            VideoAreaName = videoAreaName;
        }

        public String getVideoLanguageId() {
            return VideoLanguageId;
        }

        public void setVideoLanguageId(String videoLanguageId) {
            VideoLanguageId = videoLanguageId;
        }

        public String getVideoLanguageName() {
            return VideoLanguageName;
        }

        public void setVideoLanguageName(String videoLanguageName) {
            VideoLanguageName = videoLanguageName;
        }

        public String getDirector() {
            return Director;
        }

        public void setDirector(String director) {
            Director = director;
        }

        public String getStarring() {
            return Starring;
        }

        public void setStarring(String starring) {
            Starring = starring;
        }

        public String getDuration() {
            return Duration;
        }

        public void setDuration(String duration) {
            Duration = duration;
        }

        public String getReleaseYear() {
            return ReleaseYear;
        }

        public void setReleaseYear(String releaseYear) {
            ReleaseYear = releaseYear;
        }

        public String getPicturePath() {
            return PicturePath;
        }

        public void setPicturePath(String picturePath) {
            PicturePath = picturePath;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String description) {
            Description = description;
        }

        public String getVIPLevel() {
            return VIPLevel;
        }

        public void setVIPLevel(String VIPLevel) {
            this.VIPLevel = VIPLevel;
        }

        public String getHot() {
            return Hot;
        }

        public void setHot(String hot) {
            Hot = hot;
        }

        public String getScore() {
            return Score;
        }

        public void setScore(String score) {
            Score = score;
        }

        public String getCommentCount() {
            return CommentCount;
        }

        public void setCommentCount(String commentCount) {
            CommentCount = commentCount;
        }

        public String getEnable() {
            return Enable;
        }

        public void setEnable(String enable) {
            Enable = enable;
        }

        public String getAddTime() {
            return AddTime;
        }

        public void setAddTime(String addTime) {
            AddTime = addTime;
        }

        public List<Movie.Data.MovieSources> getMovieSources() {
            return MovieSources;
        }

        public void setMovieSources(List<Movie.Data.MovieSources> movieSources) {
            MovieSources = movieSources;
        }

        public static class MovieSources {
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

    }

}
