package com.vanguard.weather.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "WEATHER")
public class Weather {

    @Id
    @Column(name = "UUID")
    @GeneratedValue
    private UUID uuid;

    @Column(name = "API_KEY", nullable = false)
    private String apiKey;

    @Column(name = "EXECUTION_DATE")
    private LocalDateTime executionDate;

    @Column(name = "LON")
    private Double longitude;

    @Column(name = "LAT")
    private Double latitude;

    @Column(name = "WEATHER_ID")
    private Integer weatherId;

    @Column(name = "WEATHER_MAIN")
    private String weatherMain;

    @Column(name = "WEATHER_DESCRIPTION")
    private String weatherDescription;

    @Column(name = "WEATHER_ICON")
    private String weatherIcon;

    @Column(name = "BASE")
    private String base;

    @Column(name = "MAIN_TEMP")
    private Double mainTemp;

    @Column(name = "MAIN_FEELS_LIKE")
    private Double mainFeelsLike;

    @Column(name = "MAIN_TEMP_MIN")
    private Double mainTempMin;

    @Column(name = "MAIN_TEMP_MAX")
    private Double mainTempMax;

    @Column(name = "MAIN_PRESSURE")
    private Integer mainPressure;

    @Column(name = "MAINHUMIDITY")
    private Integer mainHumidity;

    @Column(name = "VISIBILITY")
    private Integer visibility;

    @Column(name = "WIND_SPEED")
    private Double windSpeed;

    @Column(name = "WIND_DEG")
    private Integer windDeg;

    @Column(name = "CLOUDS_ALL")
    private Integer cloudsAll;

    @Column(name = "DT")
    private Integer dt;

    @Column(name = "SYS_TYPE")
    private Integer sysType;

    @Column(name = "SYS_ID")
    private Integer sysId;

    @Column(name = "SYS_COUNTRY")
    private String sysCountry;

    @Column(name = "SYS_SUNRISE")
    private Integer sysSunrise;

    @Column(name = "SYS_SUNSET")
    private Integer sysSunset;

    @Column(name = "TIMEZONE")
    private Integer timezone;

    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "COD")
    private Integer cod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "API_KEY", insertable = false, updatable = false)
    private Subscription subscription;

}
