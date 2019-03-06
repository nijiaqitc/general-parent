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
    
    
    @Query(value = "select new com.njq.common.model.vo.LabelNameVO(t.id as id, t.tipName as name , count(t.tipName) as totalNum ) from BaseTip t left join BaseTipConfig c  on t.id = c.tipId where c.titleId is not null GROUP BY t.tipName")
    List<LabelNameVO> queryAllTip();
    
    @Query(value = "select t.tip_name as tipName from base_tip t left join  ( select tip_id tipId, count(*) total from base_tip_config GROUP BY tip_id  order by total desc limit 0 ,10 ) c on c.tipId = t.id where c.total >0 order by c.total desc " , nativeQuery = true)
    List<String> queryTopTip();
}
