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
        int chat = 0;
        int video = 0;
        int voip = 0;
        int p2p = 0;
        int file = 0;
        int mail = 0;
        int browsing = 0;
        int audio = 0;
        for (Flow f : flowList) {
            switch (f.getLabel()) {
                case "CHAT":
                    chat++;
                    break;
                case "VIDEO":
                    video++;
                    break;
                case "VOIP":
                    voip++;
                    break;
                case "P2P":
                    p2p++;
                    break;
                case "FILE-TRANSFER":
                    file++;
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
                default:
            }
            if ("6".equals(f.getProtocol())) {
                f.setProtocol("TCP");
            } else if ("17".equals(f.getProtocol())) {
                f.setProtocol("UDP");
            }
        }
        return new MultiNum(chat, video, voip, p2p, file, mail, browsing, audio);
    }
}
