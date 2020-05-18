package com.habit.star.pojo.po;

import java.io.Serializable;
import java.util.List;

public class ShopDetailsBO implements Serializable {


    /**
     * id : 1
     * image : http://skip-rope.oss-cn-hangzhou.aliyuncs.com/commonImages/202004071022536824439.jpg
     * imageList : ["http://skip-rope.oss-cn-hangzhou.aliyuncs.com/commonImages/202004071022536824439.jpg","http://skip-rope.oss-cn-hangzhou.aliyuncs.com/commonImages/202004071027560948692.gif"]
     * name : 习惯星智能跳绳1
     * exchangeNum : 999
     * exchangeEnergy : 66
     * price : 69.0
     * content : 习惯星智能跳绳是一根配合习惯星app使用的智能跳绳，它可以帮助我们更好的进行科学跳绳训练，提高身体机能。
     * vailEnergy : 4388
     */

    private int id;
    private String image;
    private String name;
    private int exchangeNum;
    private int exchangeEnergy;
    private String price;
    private String content;
    private int vailEnergy;
    private List<String> imageList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getExchangeNum() {
        return exchangeNum;
    }

    public void setExchangeNum(int exchangeNum) {
        this.exchangeNum = exchangeNum;
    }

    public int getExchangeEnergy() {
        return exchangeEnergy;
    }

    public void setExchangeEnergy(int exchangeEnergy) {
        this.exchangeEnergy = exchangeEnergy;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getVailEnergy() {
        return vailEnergy;
    }

    public void setVailEnergy(int vailEnergy) {
        this.vailEnergy = vailEnergy;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }
}
