package com.ankarabt.stajyonetim.repository;

import com.ankarabt.stajyonetim.entity.SistemLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SistemLogRepository extends JpaRepository<SistemLog, Long> {
    //İlerde belli kullanıcının loglarını getir burda olacak.
}