package com.ankarabt.stajyonetim.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "projeler")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Proje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String projeAdi;

    @Column(columnDefinition = "TEXT")
    private String aciklama;

    private LocalDate baslangicTarihi;

    private LocalDate bitisTarihi;

    @Column(nullable = false)
    private boolean aktifMi = true;


    // Bu projenin hangi kuruma ait olduğu
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kurum_id", nullable = false)
    private Kurum kurum;

    // Bu projeyi hangi mühendisin (mentörün) oluşturduğu
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "olusturan_id", nullable = false)
    private Kullanici olusturan;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime olusturulmaTarihi;

    // Bu projeye atanan stajyerler listesi
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "proje_stajyerler", // Ortak köprü tablosunun adı
            joinColumns = @JoinColumn(name = "proje_id"), // Projelerden gidecek id
            inverseJoinColumns = @JoinColumn(name = "stajyer_id") //Kullanicilardan gelecek ID
    )

    private List<Kullanici> stajyerler = new java.util.ArrayList<>();


}