package com.ankarabt.stajyonetim.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "kurumlar")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Kurum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String kurumAdi;

    @Column(nullable = false, length = 20)
    private String kurumTipi; // Şirket - Üni

    @Column(length = 50, unique = true)
    private String vergiNo;

    @Column(length = 100)
    private String eposta;

    @Column(length = 20)
    private String telefon;

    @Column(columnDefinition = "TEXT")
    private String adres;

    @Column(nullable = false)
    private boolean aktifMi = true;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime olusturulmaTarihi;

}