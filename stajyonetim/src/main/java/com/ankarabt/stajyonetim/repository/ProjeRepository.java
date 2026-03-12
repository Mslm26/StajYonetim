package com.ankarabt.stajyonetim.repository;

import com.ankarabt.stajyonetim.entity.Proje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjeRepository extends JpaRepository<Proje, Long> {
}