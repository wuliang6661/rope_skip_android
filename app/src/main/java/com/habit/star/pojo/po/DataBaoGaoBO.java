package com.habit.star.pojo.po;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/5/2615:18
 * desc   :
 * version: 1.0
 */
public class DataBaoGaoBO {


    /**
     * title : 2020年05月第4周周报
     * image : http://skip-rope.oss-cn-hangzhou.aliyuncs.com/headImages/202005182043156867682.png
     * content : 最新的跳绳周报已生成,快来查看吧
     * createDate : 2020/05/23
     */

    private String title;
    private String image;
    private String content;
    private String createDate;
    private int id;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
