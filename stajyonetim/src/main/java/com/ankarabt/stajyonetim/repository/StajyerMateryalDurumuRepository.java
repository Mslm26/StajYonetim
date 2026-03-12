package com.ankarabt.stajyonetim.repository;

import com.ankarabt.stajyonetim.entity.StajyerMateryalDurumu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StajyerMateryalDurumuRepository extends JpaRepository<StajyerMateryalDurumu,Long> {

    Optional<StajyerMateryalDurumu> findByStajyerIdAndMateryalId(Long stajyerId, Long materyalId);
}
