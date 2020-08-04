package com.tor.domain;

public class ResultCal {
    int totalNum;
    int torNum;
    int nontorNum;

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getTorNum() {
        return torNum;
    }

    public void setTorNum(int torNum) {
        this.torNum = torNum;
    }

    public int getNontorNum() {
        return nontorNum;
    }

    public void setNontorNum(int nontorNum) {
        this.nontorNum = nontorNum;
    }

    @Override
    public String toString() {
        return "ResultCal{" +
                "totalNum=" + totalNum +
                ", torNum=" + torNum +
                ", nontorNum=" + nontorNum +
                '}';
    }
}
