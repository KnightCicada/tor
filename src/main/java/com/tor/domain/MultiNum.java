package com.tor.domain;

public class MultiNum {
    private int video;
    private int mail;
    private int browsing;
    private int audio;
    private int other;


    public MultiNum(int video, int mail, int browsing, int audio, int other) {
        this.video = video;
        this.mail = mail;
        this.browsing = browsing;
        this.audio = audio;
        this.other = other;
    }


    public int getVideo() {
        return video;
    }

    public void setVideo(int video) {
        this.video = video;
    }

    public int getMail() {
        return mail;
    }

    public void setMail(int mail) {
        this.mail = mail;
    }

    public int getBrowsing() {
        return browsing;
    }

    public void setBrowsing(int browsing) {
        this.browsing = browsing;
    }

    public int getAudio() {
        return audio;
    }

    public void setAudio(int audio) {
        this.audio = audio;
    }

    public int getOther() {
        return other;
    }

    public void setOther(int other) {
        this.other = other;
    }


}
