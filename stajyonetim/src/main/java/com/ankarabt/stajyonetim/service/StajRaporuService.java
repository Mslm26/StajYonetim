package com.ankarabt.stajyonetim.service;

import com.ankarabt.stajyonetim.core.annotation.IslemLogu;
import com.ankarabt.stajyonetim.entity.StajRaporu;
import com.ankarabt.stajyonetim.repository.StajRaporuRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StajRaporuService {

    private final StajRaporuRepository stajRaporuRepository;

    public StajRaporuService(StajRaporuRepository stajRaporuRepository) {
        this.stajRaporuRepository = stajRaporuRepository;
    }

    private void raporValidasyonu(StajRaporu rapor) {
        // 1. Tarih
        if (rapor.getTarih() == null) {
            throw new RuntimeException("Rapor tarihi (ilgili hafta) boş bırakılamaz.");
        }
        if (rapor.getTarih().isAfter(LocalDate.now())) {
            throw new RuntimeException("Gelecek bir tarih için staj raporu yazılamaz.");
        }

        // 2. Yapılan İş
        if (rapor.getYapilanIs() == null || rapor.getYapilanIs().trim().isEmpty()) {
            throw new RuntimeException("Yapılan iş detayları boş bırakılamaz.");
        }

        // 3. Stajyer Bağımlılığı
        if (rapor.getStajyer() == null || rapor.getStajyer().getId() == null) {
            throw new RuntimeException("Rapor mutlaka bir stajyere ait olmalıdır.");
        }

        // 4. Ret Sebebi Kontrolü
        if (rapor.getRetSebebi() != null && rapor.getRetSebebi().length() > 250) {
            throw new RuntimeException("Ret sebebi açıklaması 250 karakteri geçemez.");
        }
    }

    //CREATE
    @IslemLogu(islemAdi = "YENİ STAJ RAPORU EKLENDİ")
    public StajRaporu raporEkle(StajRaporu rapor) {
        raporValidasyonu(rapor);
        rapor.setSirketOnayladiMi(false);
        rapor.setAkademisyenOnayladiMi(false);
        return stajRaporuRepository.save(rapor);
    }

    // READ
    public List<StajRaporu> tumRaporlariGetir() {
        return stajRaporuRepository.findAll();
    }

    public StajRaporu raporGetirById(Long id) {
        return stajRaporuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rapor bulunamadı!"));
    }

    //UPDATE
    @IslemLogu(islemAdi = "STAJ RAPORU GÜNCELLENDİ")
    public StajRaporu raporGuncelle(Long id, StajRaporu guncelRapor) {
        StajRaporu mevcutRapor = stajRaporuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Güncellenecek rapor bulunamadı!"));

        if (mevcutRapor.isSirketOnayladiMi() || mevcutRapor.isAkademisyenOnayladiMi()) {
            throw new RuntimeException("Şirket veya akademisyen tarafından onaylanmış bir rapor üzerinde değişiklik yapılamaz!");
        }

        raporValidasyonu(guncelRapor);

        mevcutRapor.setTarih(guncelRapor.getTarih());
        mevcutRapor.setYapilanIs(guncelRapor.getYapilanIs());

        return stajRaporuRepository.save(mevcutRapor);
    }

    //DELETE
    @IslemLogu(islemAdi = "STAJ RAPORU SİLİNDİ")
    public void raporSil(Long id) {
        StajRaporu mevcutRapor = stajRaporuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Silinecek rapor bulunamadı!"));

        if (mevcutRapor.isSirketOnayladiMi() || mevcutRapor.isAkademisyenOnayladiMi()) {
            throw new RuntimeException("Onaylanmış bir staj raporu silinemez!");
        }

        stajRaporuRepository.deleteById(id);
    }


    //ONAYLI STAJ DEFTERİ ÇIKTISI ALMA
    public List<StajRaporu> onaylanmisStajDefteriniGetir(Long stajyerId) {
        if (stajyerId == null) {
            throw new RuntimeException("Stajyer ID boş olamaz.");
        }

        List<StajRaporu> onayliDefter = stajRaporuRepository
                .findByStajyerIdAndSirketOnayladiMiTrueAndAkademisyenOnayladiMiTrue(stajyerId);

        if (onayliDefter.isEmpty()) {
            throw new RuntimeException("Bu stajyere ait henüz tamamen onaylanmış bir staj raporu bulunmuyor.");
        }

        return onayliDefter;
    }



    //ONAY/RET METOTLARI

    //Mentör Onayı
    @IslemLogu(islemAdi = "RAPOR ŞİRKET TARAFINDAN ONAYLANDI")
    public StajRaporu sirketOnayla(Long id) {
        StajRaporu rapor = stajRaporuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rapor bulunamadı!"));

        rapor.setSirketOnayladiMi(true);
        rapor.setRetSebebi(null);
        return stajRaporuRepository.save(rapor);
    }

    //Akademisyen Onayı
    @IslemLogu(islemAdi = "RAPOR AKADEMİSYEN TARAFINDAN ONAYLANDI")
    public StajRaporu akademisyenOnayla(Long id) {
        StajRaporu rapor = stajRaporuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rapor bulunamadı!"));

        //Mentör onaylamadan hoca onaylayamaz
        if (!rapor.isSirketOnayladiMi()) {
            throw new RuntimeException("Şirket tarafından onaylanmamış bir rapor akademisyen tarafından onaylanamaz.");
        }

        rapor.setAkademisyenOnayladiMi(true);
        rapor.setRetSebebi(null);
        return stajRaporuRepository.save(rapor);
    }

    @IslemLogu(islemAdi = "RAPOR REDDEDİLDİ (REVİZE İSTENDİ)")
    public StajRaporu raporReddet(Long id, String retSebebi) {
        StajRaporu rapor = stajRaporuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rapor bulunamadı!"));

        if (retSebebi == null || retSebebi.trim().isEmpty()) {
            throw new RuntimeException("Reddedilen raporlar için ret sebebi mutlaka belirtilmelidir.");
        }
        if (retSebebi.length() > 250) {
            throw new RuntimeException("Ret sebebi 250 karakteri geçemez.");
        }

        rapor.setSirketOnayladiMi(false);
        rapor.setAkademisyenOnayladiMi(false);
        rapor.setRetSebebi(retSebebi);

        return stajRaporuRepository.save(rapor);
    }
}