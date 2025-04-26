    package com.ateam.jjimppong_back.common.entity;

    import jakarta.persistence.Entity;
    import jakarta.persistence.GeneratedValue;
    import jakarta.persistence.GenerationType;
    import jakarta.persistence.Id;
    import jakarta.persistence.Table;
    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;

    @Entity(name = "restaurant")
    @Table(name = "restaurant")
    @Getter
    @Setter
    @NoArgsConstructor  
    @AllArgsConstructor
    public class RestaurantEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer restaurantNumber;

        private String restaurantTitle;
        private String region;
        private String restaurantAddress;
    }
