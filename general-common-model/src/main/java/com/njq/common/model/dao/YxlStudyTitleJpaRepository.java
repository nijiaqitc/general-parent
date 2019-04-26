package com.njq.common.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.njq.common.model.po.YxlStudyTitle;
@Repository
@Qualifier("yxlStudyTitleJpaRepository")
public interface YxlStudyTitleJpaRepository extends JpaRepository<YxlStudyTitle, Long>{

	
	@Query(value = "from YxlStudyTitle where titleType = :ttype  order by rand() , id  ")
	List<YxlStudyTitle> queryQues(@Param("ttype") String ttype ,Pageable page);
	
}
