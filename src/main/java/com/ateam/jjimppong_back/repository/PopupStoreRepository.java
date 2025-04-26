package com.ateam.jjimppong_back.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ateam.jjimppong_back.common.entity.PopupStoreEntity;

public interface PopupStoreRepository extends JpaRepository<PopupStoreEntity, Integer> {
    @Query("SELECT p FROM popupStore p WHERE p.region = region")
    List<PopupStoreEntity> findByRegion(@Param("region") String region);
}
