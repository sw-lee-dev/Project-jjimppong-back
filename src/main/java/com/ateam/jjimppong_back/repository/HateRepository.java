package com.ateam.jjimppong_back.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ateam.jjimppong_back.common.entity.HateEntity;
import com.ateam.jjimppong_back.common.entity.pk.HatePK;

import jakarta.transaction.Transactional;

@Repository
public interface HateRepository extends JpaRepository<HateEntity, HatePK> {
    HateEntity findByUserIdAndBoardNumber(String userId, Integer boardNumber);
    List<HateEntity> findByBoardNumber(Integer boardNumber);

    @Transactional
    void deleteByBoardNumber(Integer boardNumber);
}
