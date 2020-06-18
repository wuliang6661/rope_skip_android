package com.tohabit.skip.pojo.po;

public class MusicBeatBO {


    /**
     * musicId : 13
     * musicName : fafas
     * musicUrl : http://skip-rope.oss-cn-hangzhou.aliyuncs.com/common/202005211624033631393.mp3
     * beatId : 1
     * beat : 10
     * beatUrl : http://skip-rope.oss-cn-hangzhou.aliyuncs.com/common/202005252118500362456.mp3
     */

    private int musicId;
    private String musicName;
    private String musicUrl;
    private int beatId;
    private int beat;
    private String beatUrl;

    public int getMusicId() {
        return musicId;
    }

    public void setMusicId(int musicId) {
        this.musicId = musicId;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getMusicUrl() {
        return musicUrl;
    }

    public void setMusicUrl(String musicUrl) {
        this.musicUrl = musicUrl;
    }

    public int getBeatId() {
        return beatId;
    }

    public void setBeatId(int beatId) {
        this.beatId = beatId;
    }

    public int getBeat() {
        return beat;
    }

    public void setBeat(int beat) {
        this.beat = beat;
    }

    public String getBeatUrl() {
        return beatUrl;
    }

    public void setBeatUrl(String beatUrl) {
        this.beatUrl = beatUrl;
    }
}
