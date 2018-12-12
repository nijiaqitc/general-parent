package com.njq.common.base.dao.ddl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.njq.common.model.po.BaseUser;


@Repository
//@NoRepositoryBean
@Qualifier("repository")
public interface SqlJpaRepository  extends JpaRepository<BaseUser, Long >{

}
