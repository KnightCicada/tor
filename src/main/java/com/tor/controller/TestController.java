package com.tor.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tor.domain.Flow;
import com.tor.domain.Model;
import com.tor.domain.MultiNum;
import com.tor.domain.Packet;
import com.tor.result.CodeMsg;
import com.tor.result.Const;
import com.tor.result.Result;
import com.tor.service.ModelService;
import com.tor.service.PacketService;
import com.tor.service.TestService;
import com.tor.util.PropertiesUtil;
import com.tor.util.ProtocolLabel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    TestService testService;
    @Autowired
    private PacketService packetService;
    @Autowired
    private ModelService modelService;

    private Model model = new Model();


    @RequestMapping(method = RequestMethod.GET)
    public String findAll(ModelMap map, @RequestParam(required = false, defaultValue = "1", value = "pn") Integer pn, @RequestParam(required = false, defaultValue = "1", value = "pn1") Integer pn1) {
        PageHelper.startPage(pn1, 6);
        List<Model> modelList = modelService.findAllModelNoMul();
        map.addAttribute("modelList", modelList);
        PageInfo<Model> modelPage = new PageInfo<>(modelList);
        map.addAttribute("modelPage", modelPage);

        PageHelper.startPage(pn, 6);
        List<Packet> packetList = packetService.findAllTestPacket();
        map.addAttribute("packetList", packetList);
        PageInfo<Packet> packetPage = new PageInfo<>(packetList);
        map.addAttribute("packetPage", packetPage);
        return Const.TEST_PAGE;
    }

    /**
     * @param testCsvPath:测试集对应csv文件路径
     * @param modelname：模型名称
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/result", method = RequestMethod.GET)
    public String test(@RequestParam("testFile") String testCsvPath, @RequestParam("modelName") String modelname, ModelMap modelMap) throws Exception {
        if (testCsvPath == null) {
            modelMap.addAttribute("result", Result.error(CodeMsg.NULL_DATA));
            return "ErrorPage";
        }
        //对testCsvPath进行处理得到测试文件名称
        String testFileName = testCsvPath.substring(testCsvPath.lastIndexOf("/")).replace("/", "");
        model = modelService.findExactModelByName(modelname);
        if (model == null) {
            modelMap.addAttribute("result", Result.error(CodeMsg.NULL_DATA));
            return "ErrorPage";
        } else {
            String modelPath = model.getModelPath();//.model
            String featurePath = model.getFeaturePath();//Feature.txt
            //调用测试算法，得到一个表，表示测试结果。
            List<Flow> resultList = testService.getModelClassifyList(testFileName, testCsvPath, modelPath, featurePath);
            if (resultList.size() == 0) {
                modelMap.addAttribute("result", Result.error(CodeMsg.TEST_ERROR));
                return "ErrorPage";
            }
            try {
                Packet packet = packetService.findPacketByExactPath(testCsvPath);
                packet.setType("已判别test");
                packetService.updateType(packet);
            } catch (Exception e) {
                log.info(e.getMessage());
            }
            int torNum = 0;
            int totalNum = resultList.size();
            torNum = ProtocolLabel.protocolAndTorNum(resultList);
            int nontorNum = totalNum - torNum;


            model = modelService.findExactModelByName("multiRandomForest.model");
            String multimodelPath = model.getModelPath();//.model
            String multiFeaturePath = model.getFeaturePath();//Feature.txt
            String multiFileName = "multiTmp" + testFileName;
            String multiFilePath = PropertiesUtil.getPcapCsvPath() + multiFileName;
            //调用测试算法，得到一个表，表示测试结果。
            List<Flow> multiResultList = testService.getModelClassifyListMulti(multiFileName, multiFilePath, multimodelPath, multiFeaturePath);
            MultiNum multiNum = ProtocolLabel.protocolAndMultiNum(multiResultList);

            modelMap.addAttribute("MultiTotal", multiResultList.size());
            modelMap.addAttribute("video", multiNum.getVideo());
            modelMap.addAttribute("mail", multiNum.getMail());
            modelMap.addAttribute("browsing", multiNum.getBrowsing());
            modelMap.addAttribute("audio", multiNum.getAudio());
            modelMap.addAttribute("other", multiNum.getOther());
            modelMap.addAttribute("resultList", resultList);
            modelMap.addAttribute("multiResultList", multiResultList);


            modelMap.addAttribute("totalNum", totalNum);
            modelMap.addAttribute("torNum", torNum);
            modelMap.addAttribute("nontorNum", nontorNum);
            modelMap.addAttribute("resultList", resultList);
            return Const.TEST_RESULT_PAGE;
        }
    }

    /**
     * @param testCsvPath
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/multiResult", method = RequestMethod.GET)
    public String testMulti(@RequestParam("testFile") String testCsvPath, ModelMap modelMap) throws Exception {
        if (testCsvPath == null) {
            modelMap.addAttribute("result", Result.error(CodeMsg.NULL_DATA));
            return Const.CLASSIFY_PAGE;
        }
        //对testCsvPath进行处理，得到测试文件名字
        String testFileName = testCsvPath.substring(testCsvPath.lastIndexOf("/")).replace("/", "");
        //选取提供的多分类模型
        model = modelService.findLastModel();
        if (model == null) {
            modelMap.addAttribute("result", Result.error(CodeMsg.NULL_DATA));
            return Const.CLASSIFY_PAGE;
        } else {
            String modelPath = model.getModelPath();//.model
            String featurePath = model.getFeaturePath();//Feature.txt
            //调用测试算法，得到一个表，表示测试结果。
            List<Flow> resultList = testService.getModelClassifyListMulti(testFileName, testCsvPath, modelPath, featurePath);
            modelMap.addAttribute("resultList", resultList);
            return Const.TEST_RESULT_PAGE;
        }
    }
}
