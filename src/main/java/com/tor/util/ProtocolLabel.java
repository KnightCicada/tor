package com.tor.util;

import com.tor.domain.Flow;

import java.util.List;

public class ProtocolLabel {
    public static List<Flow> transform(List<Flow> flowList) {
        for (Flow f : flowList) {
            if ("6".equals(f.getProtocol())) {
                f.setProtocol("TCP");
            } else if ("17".equals(f.getProtocol())) {
                f.setProtocol("UDP");
            }
        }
        return flowList;
    }
}
