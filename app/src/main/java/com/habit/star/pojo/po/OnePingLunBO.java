package com.habit.star.pojo.po;

import java.util.List;

public class OnePingLunBO {


    /**
     * id : 19
     * userId : 1
     * userName : 陈阳☀️
     * headImage : http://skip-rope.oss-cn-hangzhou.aliyuncs.com/headImages/202005162344241617945.png
     * image : http://skip-rope.oss-cn-hangzhou.aliyuncs.com/commonImages/202005012211490197894.png,http://skip-rope.oss-cn-hangzhou.aliyuncs.com/commonImages/202005012211490669076.png
     * imageList : ["http://skip-rope.oss-cn-hangzhou.aliyuncs.com/commonImages/202005012211490197894.png","http://skip-rope.oss-cn-hangzhou.aliyuncs.com/commonImages/202005012211490669076.png"]
     * commentNum : 22
     * content : Comment test content - 2
     * friendDate : 2020-05-01
     * createDate : 2020-05-01
     * twoComment : {"id":47,"userId":1,"userName":"陈阳☀️","headImage":"http://skip-rope.oss-cn-hangzhou.aliyuncs.com/headImages/202005162344241617945.png","toUserName":"陈阳☀️","content":"世界和你","friendDate":"昨天11:04","createDate":"2020-05-16","oneCommentId":19}
     */

    private int id;
    private int userId;
    private String userName;
    private String headImage;
    private String image;
    private int commentNum;
    private String content;
    private String friendDate;
    private String createDate;
    private TwoPingLunBO twoComment;
    private List<String> imageList;

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

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public TwoPingLunBO getTwoComment() {
        return twoComment;
    }

    public void setTwoComment(TwoPingLunBO twoComment) {
        this.twoComment = twoComment;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

}
