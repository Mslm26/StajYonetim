package com.ankarabt.stajyonetim.service;

import com.ankarabt.stajyonetim.core.annotation.IslemLogu;
import com.ankarabt.stajyonetim.entity.StajyerMateryalDurumu;
import com.ankarabt.stajyonetim.repository.StajyerMateryalDurumuRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StajyerMateryalDurumuService {

    private final StajyerMateryalDurumuRepository durumRepository;

    public StajyerMateryalDurumuService(StajyerMateryalDurumuRepository durumRepository) {
        this.durumRepository = durumRepository;
    }

    @IslemLogu(islemAdi = "STAJYERE YENİ EĞİTİM ATANDI")
    public StajyerMateryalDurumu atamaYap(StajyerMateryalDurumu durum) {
        if (durum.getStajyer() == null || durum.getMateryal() == null) {
            throw new RuntimeException("Atama işlemi için stajyer ve materyal seçilmesi zorunludur.");
        }

        // Aynı materyalin aynı kişiye atanması
        Optional<StajyerMateryalDurumu> mevcutAtama = durumRepository.findByStajyerIdAndMateryalId(
                durum.getStajyer().getId(), durum.getMateryal().getId());

        if (mevcutAtama.isPresent()) {
            throw new RuntimeException("Bu eğitim materyali zaten bu stajyere atanmış.");
        }

        durum.setTamamlandiMi(false);
        durum.setTamamlanmaTarihi(null);
        return durumRepository.save(durum);
    }

    public List<StajyerMateryalDurumu> tumAtamalariGetir() {
        return durumRepository.findAll();
    }


    @IslemLogu(islemAdi = "EĞİTİM MATERYALİ TAMAMLANDI")
    public StajyerMateryalDurumu materyaliTamamla(Long atamaId) {
        StajyerMateryalDurumu mevcutDurum = durumRepository.findById(atamaId)
                .orElseThrow(() -> new RuntimeException("Eğitim ataması bulunamadı."));

        if (mevcutDurum.isTamamlandiMi()) {
            throw new RuntimeException("Bu eğitim zaten tamamlanmış olarak görünüyor.");
        }

        mevcutDurum.setTamamlandiMi(true);
        mevcutDurum.setTamamlanmaTarihi(LocalDateTime.now());

        return durumRepository.save(mevcutDurum);
    }

    @IslemLogu(islemAdi = "EĞİTİM ATAMASI İPTAL EDİLDİ/SİLİNDİ")
    public void atamaSil(Long atamaId) {
        durumRepository.deleteById(atamaId);
    }
}