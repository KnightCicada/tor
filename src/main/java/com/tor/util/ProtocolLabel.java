package com.tor.util;

import com.tor.domain.Flow;
import com.tor.domain.MultiNum;

import java.util.List;

public class ProtocolLabel {
    public static void protocol(List<Flow> flowList) {
        for (Flow f : flowList) {
            if ("6".equals(f.getProtocol())) {
                f.setProtocol("TCP");
            } else if ("17".equals(f.getProtocol())) {
                f.setProtocol("UDP");
            }
        }
    }

    public static int protocolAndTorNum(List<Flow> flowList) {
        int torNum = 0;
        for (Flow f : flowList) {
            if ("TOR".equals(f.getLabel())) {
                torNum++;
            }
            if ("6".equals(f.getProtocol())) {
                f.setProtocol("TCP");
            } else if ("17".equals(f.getProtocol())) {
                f.setProtocol("UDP");
            }
        }
        return torNum;
    }

    public static MultiNum protocolAndMultiNum(List<Flow> flowList) {
        int video = 0;
        int mail = 0;
        int browsing = 0;
        int audio = 0;
        int other = 0;
        for (Flow f : flowList) {
            switch (f.getLabel()) {
                case "VIDEO":
                    video++;
                    break;
                case "MAIL":
                    mail++;
                    break;
                case "BROWSING":
                    browsing++;
                    break;
                case "AUDIO":
                    audio++;
                    break;
                case "OTHER":
                    audio++;
                    break;
                default:
            }
            if ("6".equals(f.getProtocol())) {
                f.setProtocol("TCP");
            } else if ("17".equals(f.getProtocol())) {
                f.setProtocol("UDP");
            }
        }
        return new MultiNum(video, mail, browsing, audio, other);
    }
}
