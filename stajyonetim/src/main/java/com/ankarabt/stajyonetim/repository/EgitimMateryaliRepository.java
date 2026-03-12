package com.ankarabt.stajyonetim.repository;
import com.ankarabt.stajyonetim.entity.EgitimMateryali;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EgitimMateryaliRepository extends JpaRepository<EgitimMateryali, Long> {

}