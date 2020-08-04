package com.tor.service;

import com.tor.dao.BridgeDao;
import com.tor.domain.Bridge;
import com.tor.domain.IPEntity;
import com.tor.util.IPUtil;
import com.tor.vo.CountryCnt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class BridgeService {

    @Autowired
    private BridgeDao bridgeDao;

    public Integer insertBridges(List<Bridge> list) {
        return bridgeDao.insertBridges(list);
    }

    public List<Bridge> selectBridges() {
        return bridgeDao.selectBridges();
    }

    public List<CountryCnt> findByCountry() {
        return bridgeDao.findByCountry();
    }

}
