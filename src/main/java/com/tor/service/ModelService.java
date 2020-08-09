package com.tor.service;


import com.tor.dao.ModelDao;
import com.tor.domain.Model;
import com.tor.domain.Packet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelService {
    @Autowired
    private ModelDao modelDao;

    public int insertModel(Model model) {
        return modelDao.insertModel(model);
    }

    public List<Model> findAllModel() {
        return modelDao.findAllModel();
    }

    public List<Model> findAllModelNoMul() {
        return modelDao.findAllModelNoMul();
    }

    public List<Model> findAllModelDesc() {
        return modelDao.findAllModelDesc();
    }


    public int deleteModel(Integer id) {
        return modelDao.deleteModel(id);
    }

    public List<Model> findModelByKeyword(String modelName) {
        return modelDao.findModelByKeyword(modelName);
    }

    public Model findExactModelByName(String modelName) {
        return modelDao.findExactModelByName(modelName);
    }

    public Model findLastModel() {
        return modelDao.findLastModel();
    }

    public Model findModelById(Integer id) {
        return modelDao.findModelById(id);
    }
}
