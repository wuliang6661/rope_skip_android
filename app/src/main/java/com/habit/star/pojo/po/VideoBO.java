package com.habit.star.pojo.po;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/5/1516:50
 * desc   :
 * version: 1.0
 */
public class VideoBO {


    /**
     * courseId : 1
     * image : http://skip-rope.oss-cn-hangzhou.aliyuncs.com/commonImages/202004021603241597010.jpg
     * title : 侧甩直摇
     * url : http://skip-rope.oss-cn-hangzhou.aliyuncs.com/commonImages/202004021617034564772.mp4
     * length : 16
     * videoLength : 16秒
     * isLearn : 1
     * id : 3
     */

    private int courseId;
    private String image;
    private String title;
    private String url;
    private int length;
    private String videoLength;
    private int isLearn;
    private int id;

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getVideoLength() {
        return videoLength;
    }

    public void setVideoLength(String videoLength) {
        this.videoLength = videoLength;
    }

    public int getIsLearn() {
        return isLearn;
    }

    public void setIsLearn(int isLearn) {
        this.isLearn = isLearn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
