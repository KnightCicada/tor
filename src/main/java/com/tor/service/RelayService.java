package com.tor.service;

import com.tor.dao.RelayDao;
import com.tor.domain.Relay;
import com.tor.vo.CountryCnt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RelayService {
    @Autowired
    private RelayDao initDao;

    public List<Relay> findAll() {
        return initDao.findAll();
    }

    public List<CountryCnt> findByCountry() {
        return initDao.findByCountry();
    }

    public List<Relay> findTop10() {
        return initDao.findTop10();
    }
}
