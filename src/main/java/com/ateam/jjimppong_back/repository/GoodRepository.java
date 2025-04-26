package com.ateam.jjimppong_back.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ateam.jjimppong_back.common.entity.GoodEntity;
import com.ateam.jjimppong_back.common.entity.pk.GoodPK;

import jakarta.transaction.Transactional;

@Repository
public interface GoodRepository extends JpaRepository<GoodEntity, GoodPK> {
    GoodEntity findByUserIdAndBoardNumber(String userId, Integer boardNumber);
    List<GoodEntity> findByBoardNumber(Integer boardNumber);

    @Transactional
    void deleteByBoardNumber(Integer boardNumber);
}
