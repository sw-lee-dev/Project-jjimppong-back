package com.ateam.jjimppong_back.common.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import com.fasterxml.jackson.core.type.TypeReference;

@Component
public class RegionCodeLoader {
    
    private final Map<String, String> regionCodeMap = new HashMap<>();
    private final Map<String, String> admSectCodeToRegionNameMap = new HashMap<>();

    @PostConstruct
    public void loadRegionCodes() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream is = getClass().getClassLoader().getResourceAsStream("regionCodes.json");

            List<Map<String, Object>> regionList = objectMapper.readValue(is, new TypeReference<>() {});

            for (Map<String, Object> region : regionList) {
                String regionName = ((String) region.get("regionName")).trim().toLowerCase();
                String admSectCode = region.get("ADM_SECT_C").toString();
                regionCodeMap.put(regionName, admSectCode);
                admSectCodeToRegionNameMap.put(admSectCode, regionName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getAdmSectCodeByRegionName(String regionName) {
        String key = regionName.trim().toLowerCase();
        String admSectCodeStr = regionCodeMap.get(key);
        if( admSectCodeStr == null ) {
            System.out.println("No matching ADM_SECT_CODE found for region: " + regionName);
        }
        return admSectCodeStr;
    }
    public String getRegionNameByAdmSectCode(String admSectCode) {
        return admSectCodeToRegionNameMap.get(admSectCode);
    }
    
    public boolean containsAdmSectCode(String admSectCode) {
        return admSectCodeToRegionNameMap.containsKey(admSectCode);
    }
}
