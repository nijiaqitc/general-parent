package com.njq.common.model.dao;

import com.njq.common.model.po.BaseUser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("baseUserJpaRepository")
public interface BaseUserJpaRepository extends JpaRepository<BaseUser, Long > {

}