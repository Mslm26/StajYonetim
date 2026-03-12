package com.ankarabt.stajyonetim.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "kullanicilar")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Kullanici {




    @Id
    @SequenceGenerator(name = "kullanicilar_id_seq", sequenceName = "kullanicilar_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "kullanicilar_id_seq")
    private Long id;

    @Column(nullable = false, length = 50)
    private String ad;

    @Column(nullable = false, length = 50)
    private String soyad;

    @Column(nullable = false, unique = true, length = 100)
    private String eposta;

    @Column(nullable = false)
    private String sifre;

    @Column(length = 20)
    private String telefon;

    @Column(nullable = false)
    private boolean aktifMi = true;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kurum_id", nullable = true)
    private Kurum kurum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rol_id", nullable = true)
    private Rol rol;

    // 1. Mentör / Yönetici İlişkisi
    // Bir stajyerin bağlı olduğu mühendis de aslında bir Kullanıcıdır.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentor_id")
    private Kullanici mentor;


    // Stajyerin seçtiği ilgi alanı (Sadece stajyerler için dolu olacak)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kategori_id")
    private Kategori ilgiAlani;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime olusturulmaTarihi;
}