package com.njq.common.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.njq.common.model.po.BaseTitleGrab;

@Repository
@Qualifier("baseTitleGrabJpaRepository")
public interface BaseTitleGrabJpaRepository extends JpaRepository<BaseTitleGrab, Long>{

	@Query(value = " select new com.njq.common.model.po.BaseTitleGrab(g.id,g.docId,g.title,g.typeId,g.starTab,g.createDate,g.isParent) from BaseTitleGrab g left join BaseTipConfig c on c.titleId = g.id where c.tipId = :tipId order by g.starTab desc , g.title desc")
    List<BaseTitleGrab> queryByTipId(@Param("tipId") Long tipId);
	
	@Query(value ="select g.* from base_title_grab g left join base_tip_config c on g.id = c.title_id left join base_tip p on p.id = c.tip_id where p.tip_name = :tipName" ,nativeQuery = true )
	List<BaseTitleGrab> queryByTipName(@Param("tipName") String tipName);
}
