package com.ankarabt.stajyonetim.service;

import com.ankarabt.stajyonetim.core.annotation.IslemLogu;
import com.ankarabt.stajyonetim.entity.Gorev;
import com.ankarabt.stajyonetim.entity.Kullanici;
import com.ankarabt.stajyonetim.repository.GorevRepository;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class GorevService {

    private final GorevRepository gorevRepository;

    public GorevService(GorevRepository gorevRepository) {
        this.gorevRepository = gorevRepository;
    }

    private void gorevValidasyonu(Gorev gorev) {
        // 1. Başlık
        if (gorev.getBaslik() == null || gorev.getBaslik().trim().isEmpty()) {
            throw new RuntimeException("Görev başlığı boş bırakılamaz.");
        }
        if (gorev.getBaslik().length() > 150) {
            throw new RuntimeException("Görev başlığı 150 karakteri geçemez.");
        }

        // 2. Tarih ve Zaman
        if (gorev.getBaslangicTarihi() != null) {
            if (gorev.getBitisTarihi() != null && gorev.getBitisTarihi().isBefore(gorev.getBaslangicTarihi())) {
                throw new RuntimeException("Görevin bitiş tarihi, başlangıç tarihinden önce olamaz.");
            }
            if (gorev.getTeslimatTarihi() != null && gorev.getTeslimatTarihi().isBefore(gorev.getBaslangicTarihi())) {
                throw new RuntimeException("Teslimat tarihi, başlangıç tarihinden önce olamaz.");
            }
        }
    }

    // CREATE
    @IslemLogu(islemAdi = "YENİ GÖREV EKLENDİ")
    public Gorev gorevEkle(Gorev gorev) {
        gorevValidasyonu(gorev);
        gorev.setDurum("YAPILACAK");
        return gorevRepository.save(gorev);
    }

    // READ
    public List<Gorev> tumGorevleriGetir() {
        return gorevRepository.findAll();
    }

    // UPDATE
    @IslemLogu(islemAdi = "GÖREV GÜNCELLENDİ")
    public Gorev gorevGuncelle(Long id, Gorev guncelGorev) {
        Gorev mevcutGorev = gorevRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Güncellenecek görev bulunamadı!"));

        gorevValidasyonu(guncelGorev);

        mevcutGorev.setBaslik(guncelGorev.getBaslik());
        mevcutGorev.setAciklama(guncelGorev.getAciklama());
        mevcutGorev.setBaslangicTarihi(guncelGorev.getBaslangicTarihi());
        mevcutGorev.setBitisTarihi(guncelGorev.getBitisTarihi());
        mevcutGorev.setTeslimatTarihi(guncelGorev.getTeslimatTarihi());
        mevcutGorev.setMentorNotu(guncelGorev.getMentorNotu());
        mevcutGorev.setProje(guncelGorev.getProje());

        return gorevRepository.save(mevcutGorev);
    }

    // DELETE
    @IslemLogu(islemAdi = "GÖREV SİLİNDİ")
    public void gorevSil(Long id) {
        gorevRepository.deleteById(id);
    }

    // DURUM METOTLARI
    // 1. Görev Atama
    @IslemLogu(islemAdi = "YENİ GÖREV ATANDI")
    public Gorev gorevAta(Long gorevId, Kullanici stajyer) {
        Gorev mevcutGorev = gorevRepository.findById(gorevId)
                .orElseThrow(() -> new RuntimeException("Görev bulunamadı!"));

        mevcutGorev.setAtanan(stajyer);
        mevcutGorev.setDurum("YAPILACAK");
        return gorevRepository.save(mevcutGorev);
    }

    // 2. Görevi Başlatma
    @IslemLogu(islemAdi = "GÖREV BAŞLATILDI")
    public Gorev gorevBaslat(Long gorevId) {
        Gorev mevcutGorev = gorevRepository.findById(gorevId)
                .orElseThrow(() -> new RuntimeException("Hata: Görev bulunamadı!"));


        if (!mevcutGorev.getDurum().equals("YAPILACAK")) {
            throw new RuntimeException("Sadece 'YAPILACAK' durumundaki görevler başlatılabilir.");
        }

        mevcutGorev.setDurum("SÜRÜYOR");
        return gorevRepository.save(mevcutGorev);
    }

    // 3. Görevi Tamamlama
    @IslemLogu(islemAdi = "GÖREV TAMAMLANDI")
    public Gorev gorevTamamla(Long gorevId) {
        Gorev mevcutGorev = gorevRepository.findById(gorevId)
                .orElseThrow(() -> new RuntimeException("Görev bulunamadı!"));


        if (!mevcutGorev.getDurum().equals("SÜRÜYOR")) {
            throw new RuntimeException("Sadece 'SÜRÜYOR' durumundaki görevler tamamlanabilir.");
        }

        mevcutGorev.setDurum("TAMAMLANDI");
        return gorevRepository.save(mevcutGorev);
    }
}