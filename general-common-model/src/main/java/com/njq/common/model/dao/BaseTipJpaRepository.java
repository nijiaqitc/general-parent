package com.njq.common.model.dao;

import com.njq.common.model.po.BaseTip;
import com.njq.common.model.vo.LabelNameVO;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author: nijiaqi
 * @date: 2019/2/1
 */
@Repository
@Qualifier("baseTipJpaRepository")
public interface BaseTipJpaRepository extends JpaRepository<BaseTip, Long> {
    @Modifying
    @Query(value = "update BaseTip set num = num+1 where id = :id ")
    int updateForAddNum(@Param("id") Long id);
    
    
    @Query(value = "select t.tip_name name , count(tip_name) num from base_tip t left join base_tip_config c  on t.id = c.tip_id where c.title_id is not null GROUP BY tip_name")
    List<LabelNameVO> queryAllTip();
}
