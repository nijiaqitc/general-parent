package com.njq.common.model.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.njq.common.model.po.XsDocGeneralInfo;



@Repository
@Qualifier("xsDocGeneralInfoJpaRepository")
public interface XsDocGeneralInfoJpaRepository extends JpaRepository<XsDocGeneralInfo, Long>{

	@Modifying
    @Query(value = "update XsDocGeneralInfo set goodNum = goodNum+1 where id = :id ")
    int updateForAddGoodNum(@Param("id") Long id);
	
	
	@Modifying
    @Query(value = "update XsDocGeneralInfo set badNum = badNum+1 where id = :id ")
    int updateForAddBadNum(@Param("id") Long id);
	
	@Modifying
    @Query(value = "update XsDocGeneralInfo set fontNum = :num where titleId = :id ")
	int updateForAddFontNum(@Param("num") Integer num , @Param("id") Long id);
}
