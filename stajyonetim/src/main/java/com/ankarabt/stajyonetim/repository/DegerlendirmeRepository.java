package com.ankarabt.stajyonetim.repository;
import com.ankarabt.stajyonetim.entity.Degerlendirme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DegerlendirmeRepository extends JpaRepository<Degerlendirme, Long> {

}