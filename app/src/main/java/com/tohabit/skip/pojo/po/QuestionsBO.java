package com.tohabit.skip.pojo.po;

import java.util.List;

public class QuestionsBO {


    /**
     * id : 7
     * userId : 1
     * userName : 陈阳☀️
     * questionAnswerClassId : 1
     * className : 汽车
     * headImage : http://skip-rope.oss-cn-hangzhou.aliyuncs.com/headImages/202005161528587847947.jpeg
     * title : 四月最后一天也要元气满满
     * content : 欲望从来不是幸福的源泉，而且一切痛苦的根。一个欲望满足了，马上会产生新的，就像我，满足了随便喝凉水的欲望，有会产生睡懒觉欲望，周而复始，无穷无尽。
     * image : http://skip-rope.oss-cn-hangzhou.aliyuncs.com/commonImages/202004300853383314528.png
     * imageList : null
     * commentNum : 25
     * friendDate : 2020-04-30
     * createDate : 2020-04-30
     */

    private int id;
    private int userId;
    private String userName;
    private int questionAnswerClassId;
    private String className;
    private String headImage;
    private String title;
    private String content;
    private String image;
    private List<String> imageList;
    private int commentNum;
    private String friendDate;
    private String createDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getQuestionAnswerClassId() {
        return questionAnswerClassId;
    }

    public void setQuestionAnswerClassId(int questionAnswerClassId) {
        this.questionAnswerClassId = questionAnswerClassId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public String getFriendDate() {
        return friendDate;
    }

    public void setFriendDate(String friendDate) {
        this.friendDate = friendDate;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
