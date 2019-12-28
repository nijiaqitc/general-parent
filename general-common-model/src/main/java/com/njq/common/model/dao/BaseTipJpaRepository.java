package com.njq.common.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.njq.common.model.po.BaseTip;

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
    
    
    @Query(value = "select t.id, t.tip_name as name,IFNULL(n.num,0) as totalNum from base_tip t left join  ( select c.tip_id id , count(c.tip_id) num from base_tip_config c group by c.tip_id ) n on t.id = n.id ORDER BY n.num desc",nativeQuery=true)
    List<Object[]> queryAllTip();
    
    @Query(value = "select t.tip_name as tipName from base_tip t left join  ( select tip_id tipId, count(*) total from base_tip_config GROUP BY tip_id  order by total desc limit 0 ,10 ) c on c.tipId = t.id where c.total >0 order by c.total desc " , nativeQuery = true)
    List<String> queryTopTip();
}
