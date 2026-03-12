package com.ankarabt.stajyonetim.repository;
import com.ankarabt.stajyonetim.entity.Kategori;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KategoriRepository extends JpaRepository<Kategori, Long> {
    Optional<Kategori> findByKategoriAdi(String kategoriAdi);
}