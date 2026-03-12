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
@Table(name = "gorevler")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Gorev {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String baslik;

    @Column(columnDefinition = "TEXT")
    private String aciklama;

    // yapılacak, sürüyor, tamamlandı
    @Column(nullable = false, length = 20)
    private String durum = "YAPILACAK";

    private LocalDate baslangicTarihi;
    private LocalDate bitisTarihi;

    private LocalDate teslimatTarihi;

    @Column(columnDefinition = "TEXT")
    private String mentorNotu;




    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proje_id", nullable = false)
    private Proje proje;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atanan_kullanici_id")
    private Kullanici atanan;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime olusturulmaTarihi;
}