package com.ankarabt.stajyonetim.repository;

import com.ankarabt.stajyonetim.entity.Kurum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KurumRepository extends JpaRepository<Kurum, Long> {

    Optional<Kurum> findByVergiNo(String vergiNo);
    Optional<Kurum> findByEposta(String eposta);
}