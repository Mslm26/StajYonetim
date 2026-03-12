package com.ankarabt.stajyonetim.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "staj_raporlari")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class StajRaporu {


    // BURDAKİ SÜTUNLAR STAJ DEFTERİ FORMATINA GÖRE DÜZELTİLECEK



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate tarih; // İlgili haftanın

    @Column(nullable = false, columnDefinition = "TEXT")
    private String yapilanIs;




    // 1. Aşama: Şirket Onayı
    @Column(nullable = false)
    private boolean sirketOnayladiMi = false;

    // 2. Aşama: Akademisyen Onayı
    @Column(nullable = false)
    private boolean akademisyenOnayladiMi = false;

    // Reddedilirse neden reddedildiği (Revize isteği)
    @Column(length = 250)
    private String retSebebi;




    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stajyer_id", nullable = false)
    private Kullanici stajyer;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime olusturulmaTarihi;
}