package com.ateam.jjimppong_back.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ateam.jjimppong_back.common.entity.PopupStoreEntity;

public interface PopupStoreRepository extends JpaRepository<PopupStoreEntity, Integer> {
    List<PopupStoreEntity> findByAdmSectCode(String admSectCode);

}
