package com.tor.dao;

import com.tor.domain.Model;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component("ModelDao")
public interface ModelDao {
    @Insert("insert into model (modelName,modelPath,modelInfo,featurePath) values(#{modelName},#{modelPath},#{modelInfo},#{featurePath})")
    int insertModel(Model model);

    @Select("select * from model")
    List<Model> findAllModel();

    @Select("select * from model where modelName != 'multiRandomForest.model'")
    List<Model> findAllModelNoMul();

    @Select("select * from model ORDER BY id DESC")
    List<Model> findAllModelDesc();

    @Update("update model set featurePath=#{featurePath} where modelName=#{modelName}")
    int updateModel(Model model);

    @Delete("delete from model where id=#{id}")
    int deleteModel(Integer id);

    @Select("SELECT * FROM model WHERE modelName like '%${value}%'")
    List<Model> findModelByKeyword(String keyword);

    @Select("SELECT * FROM model WHERE modelName=#{modelName} LIMIT 1")
    Model findExactModelByName(String modelName);

    @Select("select * from model where modelName = 'train(9039)RandomForest.model' ")
    Model findLastModel();

    @Select("select * from model ORDER BY id DESC LIMIT 5")
    List<Model> showModel();

    @Select("select * from model WHERE id=#{id}")
    Model findModelById(Integer id);
}
