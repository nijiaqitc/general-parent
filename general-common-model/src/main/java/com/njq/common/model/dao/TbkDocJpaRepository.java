package com.njq.common.model.dao;

import com.njq.common.model.po.TbkDoc;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author: nijiaqi
 * @date: 2019/2/20
 */
@Repository
@Qualifier("tbkDocJpaRepository")
public interface TbkDocJpaRepository extends JpaRepository<TbkDoc, Long> {

    @Modifying
    @Query(value = "update TbkDoc set readnums = readnums+1 where id = :id ")
    int updateForAddNum(@Param("id") Long id);

}
