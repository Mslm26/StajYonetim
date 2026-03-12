package com.ankarabt.stajyonetim.repository;

import com.ankarabt.stajyonetim.entity.SistemLog;
import com.ankarabt.stajyonetim.entity.StajRaporu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StajRaporuRepository extends JpaRepository<StajRaporu,Long> {

    List<StajRaporu> findByStajyerIdAndSirketOnayladiMiTrueAndAkademisyenOnayladiMiTrue(Long stajyerId);

}
