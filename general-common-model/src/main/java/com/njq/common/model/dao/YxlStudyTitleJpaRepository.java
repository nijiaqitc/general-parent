package com.njq.common.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.njq.common.model.po.YxlStudyTitle;
@Repository
@Qualifier("yxlStudyTitleJpaRepository")
public interface YxlStudyTitleJpaRepository extends JpaRepository<YxlStudyTitle, Long>{

	
	@Query(value = "select * from YxlStudyTitle where titleType = :ttype  order by rand() , id  Limit :limt ")
	List<YxlStudyTitle> queryQues(@Param("id") String ttype ,@Param("limt") Integer limt);
	
}
