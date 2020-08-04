package com.tor.service;


import com.tor.dao.ModelDao;
import com.tor.domain.Model;
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

    public List<Model> findAllModelDesc() {
        return modelDao.findAllModelDesc();
    }

    public int updateModel(Model model) {
        return modelDao.updateModel(model);
    }

    public int deleteModel(Integer id) {
        return modelDao.deleteModel(id);
    }

    public List<Model> findModelByName(String modelName) {
        return modelDao.findModelByName(modelName);
    }

    public Model findExactModelByName(String modelName) {
        return modelDao.findExactModelByName(modelName);
    }

    public Model findLastModel() {
        return modelDao.findLastModel();
    }
}
