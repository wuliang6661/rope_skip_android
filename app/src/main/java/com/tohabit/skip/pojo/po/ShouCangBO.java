package com.tohabit.skip.pojo.po;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/5/1416:09
 * desc   :
 * version: 1.0
 */
public class ShouCangBO {


    /**
     * id : 1
     * title : 跳绳入门训练-侧甩
     * image : http://skip-rope.oss-cn-hangzhou.aliyuncs.com/commonImages/202004021553022688191.png
     * age : 6—12
     * weight : 50—150
     * height : 50—180
     * courseNum : 4
     * releaseName : null
     * releaseDate : null
     */

    private int id;
    private String title;
    private String image;
    private String age;
    private String weight;
    private String height;
    private int courseNum;
    private String releaseName;
    private String releaseDate;
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public int getCourseNum() {
        return courseNum;
    }

    public void setCourseNum(int courseNum) {
        this.courseNum = courseNum;
    }

    public String getReleaseName() {
        return releaseName;
    }

    public void setReleaseName(String releaseName) {
        this.releaseName = releaseName;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
