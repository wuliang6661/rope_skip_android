package com.habit.star.pojo.po;

import java.io.Serializable;

public class HuodongBO implements Serializable {


    /**
     * id : 3
     * image : http://skip-rope.oss-cn-hangzhou.aliyuncs.com/commonImages/202004021603241597010.jpg
     * title : 浙江大学早操课外锻炼计划
     * content : <p><strong>1. 参与对象：</strong></p ><p><span style="color:#FF9900;">所有学生</span></p ><span style="color:#FF9900;">2. 1次跳绳大于等于400个，平均速度大于等于80BPM，记成功锻炼1次</span><br /><span style="color:#FF9900;">3. 每天最多锻炼4次，4次可连续完成，也可以分开完成</span><br /><p><strong>4. 计划实施时段：</strong></p ><p><span style="background-color:#FFFFFF;color:#FF9900;">2020/04/01</span><span style="background-color:#FFFFFF;color:#FF9900;">-2020/07/01</span></p ><span style="background-color:#FFFFFF;color:#FF9900;">5. 每次锻炼可以采用App内任何一种跳绳模式，主要跳绳数据满足上述第二条，即可记成功锻炼1次，锻炼需要使用习惯星智能跳绳</span>
     * timeBucket : 2020-04-01—2020-07-01
     */

    private int id;
    private String image;
    private String title;
    private String content;
    private String timeBucket;

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

    public String getTimeBucket() {
        return timeBucket;
    }

    public void setTimeBucket(String timeBucket) {
        this.timeBucket = timeBucket;
    }
}
