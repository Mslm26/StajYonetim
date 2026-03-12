package com.ankarabt.stajyonetim.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "degerlendirmeler")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Degerlendirme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer puan;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String gorus;

    @Column(nullable = false, length = 20)
    private String tur; // Mentör/Akademisyen



    // Hangi Stajyer
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stajyer_id", nullable = false)
    private Kullanici stajyer;

    // Mühendis/Hoca
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "degerlendiren_id", nullable = false)
    private Kullanici degerlendiren;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime degerlendirmeTarihi;
}