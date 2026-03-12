package com.ankarabt.stajyonetim.service;

import com.ankarabt.stajyonetim.core.annotation.IslemLogu;
import com.ankarabt.stajyonetim.entity.Kategori;
import com.ankarabt.stajyonetim.repository.KategoriRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KategoriService {

    private final KategoriRepository kategoriRepository;

    public KategoriService(KategoriRepository kategoriRepository) {
        this.kategoriRepository = kategoriRepository;
    }

    private void kategoriValidasyonu(Kategori kategori, Long mevcutId) {
        if (kategori.getKategoriAdi() == null || kategori.getKategoriAdi().trim().isEmpty()) {
            throw new RuntimeException("Kategori adı boş bırakılamaz.");
        }

        //Lenght kontrol
        if (kategori.getKategoriAdi().length() > 100) {
            throw new RuntimeException("Kategori adı 100 karakteri geçemez.");
        }

        //Unique Kontorl
        Optional<Kategori> kategoriKontrol = kategoriRepository.findByKategoriAdi(kategori.getKategoriAdi());
        if (kategoriKontrol.isPresent() && !kategoriKontrol.get().getId().equals(mevcutId)) {
            throw new RuntimeException(kategori.getKategoriAdi() + "' adında bir kategori zaten mevcut.");
        }
    }

    //CREATE
    @IslemLogu(islemAdi = "YENİ MATERYAL KATEGORİSİ EKLENDİ")
    public Kategori kategoriEkle(Kategori kategori) {
        kategoriValidasyonu(kategori, null);
        return kategoriRepository.save(kategori);
    }

    //READ
    public List<Kategori> tumKategorileriGetir() {
        return kategoriRepository.findAll();
    }

    //UPDATE
    @IslemLogu(islemAdi = "MATERYAL KATEGORİSİ GÜNCELLENDİ")
    public Kategori kategoriGuncelle(Long id, Kategori guncelKategori) {
        Kategori mevcutKategori = kategoriRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Güncellenecek kategori bulunamadı!"));

        kategoriValidasyonu(guncelKategori, id);
        mevcutKategori.setKategoriAdi(guncelKategori.getKategoriAdi());

        return kategoriRepository.save(mevcutKategori);
    }

    //DELETE
    @IslemLogu(islemAdi = "MATERYAL KATEGORİSİ SİLİNDİ")
    public void kategoriSil(Long id) {
        kategoriRepository.deleteById(id);
    }
}