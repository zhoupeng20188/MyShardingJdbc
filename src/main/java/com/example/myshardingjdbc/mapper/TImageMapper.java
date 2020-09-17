package com.example.myshardingjdbc.mapper;


import com.example.myshardingjdbc.entity.TImage;
import com.example.myshardingjdbc.entity.TImageExample;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TImageMapper {
    long countByExample(TImageExample example);

    int deleteByExample(TImageExample example);

    int deleteByPrimaryKey(Integer imageId);

    int insert(TImage record);

    int insertSelective(TImage record);

    List<TImage> selectByExample(TImageExample example);

    TImage selectByPrimaryKey(Long imageId);

    int updateByPrimaryKeySelective(TImage record);

    int updateByPrimaryKey(TImage record);

    @Select("select * from t_image where title like  CONCAT('%',#{title},'%') ")
    List<TImage> selectByTitle(String title);
    @Select("select * from t_image")
    List<TImage> selectAll();

    @Delete("delete from t_image")
    void delete();
}