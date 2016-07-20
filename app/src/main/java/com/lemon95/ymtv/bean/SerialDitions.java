package com.lemon95.ymtv.bean;

import java.util.List;

/**
 * Created by WXT on 2016/7/20.
 * 电视剧详情
 */
public class SerialDitions {
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

    public SerialDitions.Data getData() {
        return Data;
    }

    public void setData(SerialDitions.Data data) {
        Data = data;
    }

    public static class Data {
        private String Id;
        private String SerialName;
        private String Title;
        private String TotalEpisodes;
        private String LastEpisode;
        private String VideoGenreIds;
        private String VideoGenres;
        private String VideoAreaId;
        private String VideoAreaName;
        private String VideoLanguageId;
        private String VideoLanguageName;
        private String Director;
        private String Starring;
        private String ReleaseYear;
        private String PicturePath;
        private String Description;
        private String Hot;
        private String Score;
        private String VIPLevel;
        private String Comments;
        private List<SerialEpisodes> SerialEpisodes;

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getSerialName() {
            return SerialName;
        }

        public void setSerialName(String serialName) {
            SerialName = serialName;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public String getTotalEpisodes() {
            return TotalEpisodes;
        }

        public void setTotalEpisodes(String totalEpisodes) {
            TotalEpisodes = totalEpisodes;
        }

        public String getLastEpisode() {
            return LastEpisode;
        }

        public void setLastEpisode(String lastEpisode) {
            LastEpisode = lastEpisode;
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

        public String getVIPLevel() {
            return VIPLevel;
        }

        public void setVIPLevel(String VIPLevel) {
            this.VIPLevel = VIPLevel;
        }

        public String getComments() {
            return Comments;
        }

        public void setComments(String comments) {
            Comments = comments;
        }

        public List<SerialDitions.Data.SerialEpisodes> getSerialEpisodes() {
            return SerialEpisodes;
        }

        public void setSerialEpisodes(List<SerialDitions.Data.SerialEpisodes> serialEpisodes) {
            SerialEpisodes = serialEpisodes;
        }

        public static class SerialEpisodes {
            private String Id;
            private String SerialId;
            private String TagName;
            private String SerialIndex;
            private String Origin;

            public String getId() {
                return Id;
            }

            public void setId(String id) {
                Id = id;
            }

            public String getSerialId() {
                return SerialId;
            }

            public void setSerialId(String serialId) {
                SerialId = serialId;
            }

            public String getTagName() {
                return TagName;
            }

            public void setTagName(String tagName) {
                TagName = tagName;
            }

            public String getSerialIndex() {
                return SerialIndex;
            }

            public void setSerialIndex(String serialIndex) {
                SerialIndex = serialIndex;
            }

            public String getOrigin() {
                return Origin;
            }

            public void setOrigin(String origin) {
                Origin = origin;
            }
        }
    }
}
