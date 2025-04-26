package com.ateam.jjimppong_back.common.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "popupStore")
@Table(name = "popup_store")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PopupStoreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer popupNumber;
    
    private String popupTitle;
    private String region;
    private LocalDate popupStartDate;
    private LocalDate popupEndDate;
    private String popupImage;

}
