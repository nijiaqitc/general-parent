package com.njq.common.model.dao;

import com.njq.common.model.po.BaseTypeNum;
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
@Qualifier("baseTypeNumJpaRepository")
public interface BaseTypeNumJpaRepository extends JpaRepository<BaseTypeNum, Long> {

    @Modifying
    @Query(value = "update BaseTypeNum set num = num+1 where channel =:channel  and typeId = :typeId ")
    int updateForAddNum(@Param("channel") String channel, @Param("typeId") Long typeId);
}
