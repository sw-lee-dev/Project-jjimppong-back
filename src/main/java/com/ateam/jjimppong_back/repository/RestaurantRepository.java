package com.ateam.jjimppong_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import com.ateam.jjimppong_back.common.entity.RestaurantEntity;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Integer> {
    @Query("SELECT r FROM restaurant r WHERE r.admSectCode LIKE :admSectCode")
    List<RestaurantEntity> findByAdmSectCode(@Param("admSectCode") String admSectCode );
}
