package com.ankarabt.stajyonetim.service;

import com.ankarabt.stajyonetim.core.annotation.IslemLogu;
import com.ankarabt.stajyonetim.entity.Kullanici;
import com.ankarabt.stajyonetim.entity.Proje;
import com.ankarabt.stajyonetim.repository.ProjeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjeService {

    private final ProjeRepository projeRepository;

    public ProjeService(ProjeRepository projeRepository) {
        this.projeRepository = projeRepository;
    }


    private void projeValidasyonu(Proje proje) {
        // 1. Proje Adı
        if (proje.getProjeAdi() == null || proje.getProjeAdi().trim().isEmpty()) {
            throw new RuntimeException("Proje adı boş bırakılamaz.");
        }
        if (proje.getProjeAdi().length() > 150) {
            throw new RuntimeException("Proje adı 150 karakteri geçemez.");
        }
        // 2. Kurum Bağlantısı
        if (proje.getKurum() == null || proje.getKurum().getId() == null) {
            throw new RuntimeException("Proje mutlaka bir kuruma ait olmalıdır.");
        }

        // 3. Mentör Bağlantısı Kontrolü
        if (proje.getOlusturan() == null || proje.getOlusturan().getId() == null) {
            throw new RuntimeException("Projeyi oluşturan kişi belirtilmelidir.");
        }

        // 4. Tarih Mantığı
        if (proje.getBaslangicTarihi() != null && proje.getBitisTarihi() != null) {
            if (proje.getBitisTarihi().isBefore(proje.getBaslangicTarihi())) {
                throw new RuntimeException("Projenin bitiş tarihi, başlangıç tarihinden önce olamaz.");
            }
        }
    }

    // CREATE
    @IslemLogu(islemAdi = "YENİ PROJE EKLENDİ")
    public Proje projeEkle(Proje proje) {
        projeValidasyonu(proje);
        return projeRepository.save(proje);
    }

    // READ
    public List<Proje> tumProjeleriGetir() {
        return projeRepository.findAll();
    }

    public Proje projeGetirById(Long id) {
        return projeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proje bulunamadı!"));
    }

    // UPDATE
    @IslemLogu(islemAdi = "PROJE GÜNCELLENDİ")
    public Proje projeGuncelle(Long id, Proje guncelProje) {
        Proje mevcutProje = projeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Güncellenecek proje bulunamadı!"));

        projeValidasyonu(guncelProje);

        mevcutProje.setProjeAdi(guncelProje.getProjeAdi());
        mevcutProje.setAciklama(guncelProje.getAciklama());
        mevcutProje.setBaslangicTarihi(guncelProje.getBaslangicTarihi());
        mevcutProje.setBitisTarihi(guncelProje.getBitisTarihi());
        mevcutProje.setAktifMi(guncelProje.isAktifMi());


        mevcutProje.setKurum(guncelProje.getKurum());
        mevcutProje.setOlusturan(guncelProje.getOlusturan());


        mevcutProje.setStajyerler(guncelProje.getStajyerler());

        return projeRepository.save(mevcutProje);
    }

    // DELETE
    @IslemLogu(islemAdi = "PROJE SİLİNDİ")
    public void projeSil(Long id) {
        projeRepository.deleteById(id);
    }

    //STAJYER EKLEME
    @IslemLogu(islemAdi = "PROJEYE STAJYER EKLENDİ")
    public Proje projeyeStajyerEkle(Long projeId, Kullanici stajyer) {
        Proje mevcutProje = projeRepository.findById(projeId)
                .orElseThrow(() -> new RuntimeException("Proje bulunamadı!"));

        // Aynı stajyer kontrolü
        if (mevcutProje.getStajyerler().contains(stajyer)) {
            throw new RuntimeException("Bu stajyer zaten bu projeye atanmış.");
        }

        mevcutProje.getStajyerler().add(stajyer);
        return projeRepository.save(mevcutProje);
    }
}