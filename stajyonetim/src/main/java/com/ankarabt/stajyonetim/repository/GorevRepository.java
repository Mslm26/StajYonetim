package com.ankarabt.stajyonetim.repository;

import com.ankarabt.stajyonetim.entity.Gorev;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GorevRepository extends JpaRepository<Gorev, Long> {
}