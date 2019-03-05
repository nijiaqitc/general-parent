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

	@Query(value = " select new com.njq.common.model.po.BaseTitleGrab(g.id,g.docId,g.title,g.typeId,g.starTab,g.createDate,g.isParent) from BaseTitleGrab g left join BaseTipConfig c on c.titleId = g.id where c.tipId = :tipId order by g.starTab desc , g.createDate desc")
    List<BaseTitleGrab> queryByTipId(@Param("tipId") Long tipId);
}
