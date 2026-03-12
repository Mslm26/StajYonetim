package com.ankarabt.stajyonetim.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "egitim_materyalleri")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class EgitimMateryali {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String baslik;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String link;

    @Column(length = 20)
    private String tur; // Dosyanın türü

    @Column(length = 20)
    private String zorlukSeviyesi; // kolay - orta - zor



    // Materyal kategorisi
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kategori_id", nullable = false)
    private Kategori kategori;

    // Materyali sisteme ekleyen kişi
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ekleyen_kullanici_id", nullable = false)
    private Kullanici ekleyen;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime olusturulmaTarihi;
}