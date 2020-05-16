package com.habit.star.pojo.po;

public class ShopBO {


    /**
     * id : 1
     * image : http://skip-rope.oss-cn-hangzhou.aliyuncs.com/commonImages/202004071022536824439.jpg
     * name : 习惯星智能跳绳1
     * exchangeEnergy : 66
     * price : 69.0
     */

    private int id;
    private String image;
    private String name;
    private int exchangeEnergy;
    private double price;

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

    public int getExchangeEnergy() {
        return exchangeEnergy;
    }

    public void setExchangeEnergy(int exchangeEnergy) {
        this.exchangeEnergy = exchangeEnergy;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
