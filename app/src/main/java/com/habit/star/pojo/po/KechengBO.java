package com.habit.star.pojo.po;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/5/1515:41
 * desc   : 课程的实体类
 * version: 1.0
 */
public class KechengBO {


    /**
     * id : 1
     * courseClassId : 1
     * url : null
     * image : http://skip-rope.oss-cn-hangzhou.aliyuncs.com/commonImages/202004021553022688191.png
     * title : 跳绳入门训练-侧甩
     * introduction : <html><body><p>初学者一定要从基础的动作开始练习。在入门阶段，不要过早地追求训练的时长伙食跳绳个数，应该首先修正错误的跳绳姿势，掌握持续跳绳的节奏。如果再过去跳绳中已经养成了错误的姿势，大家一定要重视对基础动作的修正。</p > < img src=\"http://skip-rope.oss-cn-hangzhou.aliyuncs.com/commonImages/202004241710244872627.png\" width=\"326\" height=\"146\" alt=\"\" style=\"border: 0px; vertical-align: bottom; display: block; margin: 0px auto; max-width: 100%; height: auto;\"/></p ></body></html>
     * age : 6—12
     * weight : 50—150
     * height : 50—180
     * createDate : 2020-03-12 00:00:00
     * courseNum : 4
     * isCollect : null
     */

    private int id;
    private int courseClassId;
    private String url;
    private String image;
    private String title;
    private String introduction;
    private String age;
    private String weight;
    private String height;
    private String createDate;
    private int courseNum;
    private String isCollect;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseClassId() {
        return courseClassId;
    }

    public void setCourseClassId(int courseClassId) {
        this.courseClassId = courseClassId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getCourseNum() {
        return courseNum;
    }

    public void setCourseNum(int courseNum) {
        this.courseNum = courseNum;
    }

    public String getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(String isCollect) {
        this.isCollect = isCollect;
    }
}
