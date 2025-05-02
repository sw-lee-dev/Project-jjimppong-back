package com.ateam.jjimppong_back.service;

import org.springframework.stereotype.Service;
import java.util.List;
import com.ateam.jjimppong_back.common.entity.RestaurantEntity;
import com.ateam.jjimppong_back.common.util.RegionCodeLoader;
import com.ateam.jjimppong_back.repository.RestaurantRepository;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RegionCodeLoader regionCodeLoader;

    public RestaurantService(RestaurantRepository restaurantRepository, RegionCodeLoader regionCodeLoader) {
        this.restaurantRepository = restaurantRepository;
        this.regionCodeLoader = regionCodeLoader;
    }

    public List<RestaurantEntity> findByAdmSectCode(String regionOrAdmSectCode) {
        String targetAdmSectCode = parseAdmSectCodeOrName(regionOrAdmSectCode);
        if (targetAdmSectCode == null) {
            throw new IllegalArgumentException("Invalid region code or name " + regionOrAdmSectCode);
        }
        return restaurantRepository.findByAdmSectCode(targetAdmSectCode);
    }

    private String parseAdmSectCodeOrName(String regionOrAdmSectCode) {
        if (regionCodeLoader.containsAdmSectCode(regionOrAdmSectCode)) {
            return regionOrAdmSectCode;
        }

        return regionCodeLoader.getAdmSectCodeByRegionName(regionOrAdmSectCode);
    }
}
