package com.ankarabt.stajyonetim.service;

import com.ankarabt.stajyonetim.core.annotation.IslemLogu;
import com.ankarabt.stajyonetim.entity.Degerlendirme;
import com.ankarabt.stajyonetim.repository.DegerlendirmeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DegerlendirmeService {

    private final DegerlendirmeRepository degerlendirmeRepository;

    public DegerlendirmeService(DegerlendirmeRepository degerlendirmeRepository) {
        this.degerlendirmeRepository = degerlendirmeRepository;
    }

    private void degerlendirmeValidasyonu(Degerlendirme degerlendirme) {
        //Puan Kontrolü
        if (degerlendirme.getPuan() == null || degerlendirme.getPuan() < 0 || degerlendirme.getPuan() > 100) {
            throw new RuntimeException("Değerlendirme puanı 0 ile 100 arasında olmalıdır.");
        }

        //Görüş
        if (degerlendirme.getGorus() == null || degerlendirme.getGorus().trim().isEmpty()) {
            throw new RuntimeException("Değerlendirme görüşü boş bırakılamaz.");
        }

        // Tür Kontrolü
        if (degerlendirme.getTur() == null || degerlendirme.getTur().trim().isEmpty()) {
            throw new RuntimeException("Değerlendirme türü boş bırakılamaz.");
        }
        if (degerlendirme.getTur().length() > 20) {
            throw new RuntimeException("Değerlendirme türü 20 karakteri geçemez.");
        }

        // İlişki
        if (degerlendirme.getStajyer() == null || degerlendirme.getDegerlendiren() == null) {
            throw new RuntimeException("Değerlendirme stajyer ve değerlendiren kişiden bağımsız olamaz.");
        }
    }

    //CREATE
    @IslemLogu(islemAdi = "YENİ DEĞERLENDİRME EKLENDİ")
    public Degerlendirme degerlendirmeEkle(Degerlendirme degerlendirme) {
        degerlendirmeValidasyonu(degerlendirme);
        return degerlendirmeRepository.save(degerlendirme);
    }

    //READ
    public List<Degerlendirme> tumDegerlendirmeleriGetir() {
        return degerlendirmeRepository.findAll();
    }

    //UPDATE
    @IslemLogu(islemAdi = "DEĞERLENDİRME GÜNCELLENDİ")
    public Degerlendirme degerlendirmeGuncelle(Long id, Degerlendirme guncelDegerlendirme) {
        Degerlendirme mevcutDegerlendirme = degerlendirmeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Değerlendirme bulunamadı!"));

        degerlendirmeValidasyonu(guncelDegerlendirme);
        mevcutDegerlendirme.setPuan(guncelDegerlendirme.getPuan());
        mevcutDegerlendirme.setGorus(guncelDegerlendirme.getGorus());
        mevcutDegerlendirme.setTur(guncelDegerlendirme.getTur());

        return degerlendirmeRepository.save(mevcutDegerlendirme);
    }

    //DELETE
    @IslemLogu(islemAdi = "DEĞERLENDİRME SİLİNDİ")
    public void degerlendirmeSil(Long id) {
        degerlendirmeRepository.deleteById(id);
    }
}