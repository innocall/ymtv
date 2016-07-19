package com.lemon95.ymtv.bean;

/**
 * Created by WXT on 2016/7/19.
 * 查询条件
 */
public class QueryConditions {

    String areaId = "0";
    String genreId = "0";
    String groupId = "0";
    String chargeMethod = "0";
    String vipLevel = "1";
    String year = "0";
    String type;
    String name;

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getGenreId() {
        return genreId;
    }

    public void setGenreId(String genreId) {
        this.genreId = genreId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getChargeMethod() {
        return chargeMethod;
    }

    public void setChargeMethod(String chargeMethod) {
        this.chargeMethod = chargeMethod;
    }

    public String getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(String vipLevel) {
        this.vipLevel = vipLevel;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
