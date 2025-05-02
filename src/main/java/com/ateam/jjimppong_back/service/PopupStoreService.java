package com.ateam.jjimppong_back.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ateam.jjimppong_back.common.entity.PopupStoreEntity;
import com.ateam.jjimppong_back.common.util.RegionCodeLoader;
import com.ateam.jjimppong_back.repository.PopupStoreRepository;

@Service
public class PopupStoreService {

    private final PopupStoreRepository popupStoreRepository;
    private final RegionCodeLoader regionCodeLoader;

    public PopupStoreService(PopupStoreRepository popupStoreRepository, RegionCodeLoader regionCodeLoader) {
        this.popupStoreRepository = popupStoreRepository;
        this.regionCodeLoader = regionCodeLoader;
    }

    public List<PopupStoreEntity> findByAdmSectCode(String regionOrAdmSectCode) {
        String targetAdmSectCode = parseAdmSectCodeOrName(regionOrAdmSectCode);
        if (targetAdmSectCode == null) {
            throw new IllegalArgumentException("Invalid region code or name: " + regionOrAdmSectCode);
        }

        return popupStoreRepository.findByAdmSectCode(targetAdmSectCode);
    }

    private String parseAdmSectCodeOrName(String regionOrAdmSectCode) {
        if (regionCodeLoader.containsAdmSectCode(regionOrAdmSectCode)) {
            return regionOrAdmSectCode;
        }
        return regionCodeLoader.getAdmSectCodeByRegionName(regionOrAdmSectCode);
    }
}