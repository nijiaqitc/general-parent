package com.njq.common.model.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.njq.common.model.po.BaseTitleLoading;

/**
 * @author: nijiaqi
 * @date: 2019/2/1
 */
@Repository
@Qualifier("baseTitleLoadingJpaRepository")
public interface BaseTitleLoadingJpaRepository extends JpaRepository<BaseTitleLoading, Long> {
    @Modifying
    @Query(value = "update BaseTitleLoading set tryNum = tryNum+1 where id = :id ")
    int updateForAddNum(@Param("id") Long id);
    
    
    @Modifying
    @Query(value = "update BaseTitleLoading set loaded = '1'  where id = :id ")
    int updateForSuccess(@Param("id") Long id);
    
}
