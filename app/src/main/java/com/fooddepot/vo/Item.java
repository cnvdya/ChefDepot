package com.fooddepot.vo;

import java.util.Date;

/**
 * Created by ravisha on 4/26/18.
 */

public class Item {
    private  String name;
    private String category;
    private String description;
    private float price;
    private int quantity;
    private String photoPath;
    private String classification;
    private String itemId;
    private String dateStart;
    private String dateEnd;
    private String timeStart;
    private String timeEnd;
    private long reviews;
    private float avgRating;
    private String cookId;
    private String address;
    private double latitude;
    private double longitude;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    private String nickName;

    public String getDistaceCalc() {
        return distaceCalc;
    }

    public void setDistaceCalc(String distaceCalc) {
        this.distaceCalc = distaceCalc;
    }

    private String distaceCalc;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }



    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }



    public String getCookId() {
        return cookId;
    }

    public void setCookId(String cookId) {
        this.cookId = cookId;
    }


    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }


    public float getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(float avgRating) {
        this.avgRating = avgRating;
    }


    public Item(){

    }
    public Item(String name, String category, String description, float price, int quantity,
                String photoPath, String classification, String dateStart,String dateEnd,
                String timeStart,String timeEnd,long reviews, String cookId, float avgRating, String address) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.photoPath = photoPath;
        this.classification = classification;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.dateStart = dateStart;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.reviews = reviews;
        this.cookId = cookId;
        this.avgRating=avgRating;
        this.address=address;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public long getReviews() {
        return reviews;
    }

    public void setReviews(long reviews) {
        this.reviews = reviews;
    }


}
