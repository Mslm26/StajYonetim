package com.ankarabt.stajyonetim.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "stajyer_materyal_durumlari")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class StajyerMateryalDurumu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean tamamlandiMi = false;

    private LocalDateTime tamamlanmaTarihi;


    // Hangi stajyer?
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stajyer_id", nullable = false)
    private Kullanici stajyer;

    // Hangi eğitim materyali?
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "materyal_id", nullable = false)
    private EgitimMateryali materyal;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime atamaTarihi; // Sistemin/akademisyenin/mentörün eğitimi atadığı tarih
}