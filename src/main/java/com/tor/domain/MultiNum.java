package com.tor.domain;

public class MultiNum {
    private int chat = 0;
    private int video = 0;
    private int voip = 0;
    private int p2p = 0;
    private int file = 0;
    private int mail = 0;
    private int browsing = 0;
    private int audio = 0;

    public MultiNum(int chat, int video, int voip, int p2p, int file, int mail, int browsing, int audio) {
        this.chat = chat;
        this.video = video;
        this.voip = voip;
        this.p2p = p2p;
        this.file = file;
        this.mail = mail;
        this.browsing = browsing;
        this.audio = audio;
    }

    public int getChat() {
        return chat;
    }

    public void setChat(int chat) {
        this.chat = chat;
    }

    public int getVideo() {
        return video;
    }

    public void setVideo(int video) {
        this.video = video;
    }

    public int getVoip() {
        return voip;
    }

    public void setVoip(int voip) {
        this.voip = voip;
    }

    public int getP2p() {
        return p2p;
    }

    public void setP2p(int p2p) {
        this.p2p = p2p;
    }

    public int getFile() {
        return file;
    }

    public void setFile(int file) {
        this.file = file;
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
}
