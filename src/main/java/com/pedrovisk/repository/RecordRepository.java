package com.pedrovisk.repository;

import com.pedrovisk.model.RecordEntity;
import com.pedrovisk.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<RecordEntity, Long> {

    RecordEntity findTopByUserEntityOrderByDateDesc(UserEntity userEntity);

    List<RecordEntity> findAllByUserEntity(UserEntity userEntity);

}
