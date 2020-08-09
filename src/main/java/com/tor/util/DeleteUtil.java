package com.tor.util;

import com.tor.domain.Model;
import com.tor.domain.Packet;
import com.tor.exception.GlobalException;
import com.tor.result.CodeMsg;

import java.io.File;

public class DeleteUtil {
    public static boolean deletePacket(Packet packet) {
        String packetPath = packet.getPacketPath();
        String csvPath = packet.getCsvPath();
        delete(packetPath);
        delete(csvPath);
        return true;
    }

    public static boolean deleteModel(Model model) {
        String modelPath = model.getModelPath();
        String InfoPath = model.getModelInfo();
        String featurePath = model.getFeaturePath();
        delete(modelPath);
        delete(InfoPath);
        delete(featurePath);
        return true;
    }

    private static boolean delete(String path) {
        File file = new File(path);
        if (!file.exists()) {
            throw new GlobalException(CodeMsg.FILE_NOT_EXIST);
        } else {
            file.delete();
        }
        return true;
    }
}
