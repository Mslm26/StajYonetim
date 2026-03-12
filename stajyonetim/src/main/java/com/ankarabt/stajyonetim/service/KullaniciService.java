package com.ankarabt.stajyonetim.service;

import com.ankarabt.stajyonetim.core.annotation.IslemLogu;
import com.ankarabt.stajyonetim.entity.Kullanici;
import com.ankarabt.stajyonetim.repository.KullaniciRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
@RestController
@Service
@RequestMapping("/kullaniciService")
public class KullaniciService {

    private final KullaniciRepository kullaniciRepository;
    private final PasswordEncoder passwordEncoder;

    // RegEx Kalıpları
    private static final String EMAIL_PATTERN = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private static final String TELEFON_PATTERN = "^(\\+90|0)?[1-9][0-9]{9}$";

    public KullaniciService(KullaniciRepository kullaniciRepository, PasswordEncoder passwordEncoder) {
        this.kullaniciRepository = kullaniciRepository;
        this.passwordEncoder = passwordEncoder;
    }


    private void kullaniciValidasyonu(Kullanici kullanici, Long mevcutId) {
        // 1. Ad
        if (kullanici.getAd() == null || kullanici.getAd().trim().isEmpty()) {
            throw new RuntimeException("Ad boş bırakılamaz.");
        }
        if (kullanici.getAd().length() > 50) {
            throw new RuntimeException("Ad 50 karakteri geçemez.");
        }
        // 2. Soyad
        if (kullanici.getSoyad() == null || kullanici.getSoyad().trim().isEmpty()) {
            throw new RuntimeException("Soyad boş bırakılamaz.");
        }
        if (kullanici.getSoyad().length() > 50) {
            throw new RuntimeException("Soyad 50 karakteri geçemez.");
        }
        // 3. E-posta
        if (kullanici.getEposta() == null || kullanici.getEposta().trim().isEmpty()) {
            throw new RuntimeException("E-posta boş bırakılamaz.");
        }
        if (kullanici.getEposta().length() > 100) {
            throw new RuntimeException("E-posta 100 karakteri geçemez.");
        }
        if (!Pattern.matches(EMAIL_PATTERN, kullanici.getEposta())) {
            throw new RuntimeException("Geçersiz e-posta formatı.");
        }
        Optional<Kullanici> epostaKontrol = kullaniciRepository.findByEposta(kullanici.getEposta());
        if (epostaKontrol.isPresent() && !epostaKontrol.get().getId().equals(mevcutId)) {
            throw new RuntimeException("Bu e-posta adresi başka bir hesap tarafından kullanılıyor!");
        }
        // 4. Şifre
        if (kullanici.getSifre() == null || kullanici.getSifre().trim().isEmpty()) {
            throw new RuntimeException("Şifre boş bırakılamaz.");
        }
        // 5. Telefon
        if (kullanici.getTelefon() != null && !kullanici.getTelefon().trim().isEmpty()) {
            if (kullanici.getTelefon().length() > 20) {
                throw new RuntimeException("Telefon numarası 20 karakteri geçemez.");
            }
            if (!Pattern.matches(TELEFON_PATTERN, kullanici.getTelefon())) {
                throw new RuntimeException("Geçersiz telefon numarası. (Örn: 05XXXXXXXXX)");
            }
        }
    }

    @IslemLogu(islemAdi = "SİSTEME GİRİŞ YAPILDI")
    public Kullanici girisYap(String eposta, String sifre) {
        Kullanici kullanici = kullaniciRepository.findByEposta(eposta)
                .orElseThrow(() -> new RuntimeException("Bu e-posta adresine ait bir kullanıcı bulunamadı."));


        if (!passwordEncoder.matches(sifre, kullanici.getSifre())) {
            throw new RuntimeException("Hatalı şifre girdiniz!");
        }

        if (!kullanici.isAktifMi()) {
            throw new RuntimeException("Erişim Engellendi: Hesabınız pasif durumdadır. Lütfen kurum yöneticinizle iletişime geçin.");
        }

        return kullanici;
    }

    //CREATE
    @PostMapping
    @IslemLogu(islemAdi = "YENİ KULLANICI EKLENDİ")
    public Kullanici kullaniciEkle(@RequestBody Kullanici kullanici) {
       kullaniciValidasyonu(kullanici, null);

        String kriptoluSifre = passwordEncoder.encode(kullanici.getSifre());
        kullanici.setSifre(kriptoluSifre);

        return kullaniciRepository.save(kullanici);
    }

    //READ
    public List<Kullanici> tumKullanicilariGetir() {
        return kullaniciRepository.findAll();
    }

    public Kullanici kullaniciGetirById(Long id) {
        return kullaniciRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı!"));
    }

    //UPDATE
    @IslemLogu(islemAdi = "KULLANICI GÜNCELLENDİ")
    public Kullanici kullaniciGuncelle(Long id, Kullanici guncelKullanici) {
        Kullanici mevcutKullanici = kullaniciRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Güncellenecek kullanıcı bulunamadı!"));

        kullaniciValidasyonu(guncelKullanici, id);

        mevcutKullanici.setAd(guncelKullanici.getAd());
        mevcutKullanici.setSoyad(guncelKullanici.getSoyad());
        mevcutKullanici.setEposta(guncelKullanici.getEposta());
        mevcutKullanici.setSifre(guncelKullanici.getSifre()); //şifre hashlenerek güncellenecek
        mevcutKullanici.setTelefon(guncelKullanici.getTelefon());
        mevcutKullanici.setAktifMi(guncelKullanici.isAktifMi());


        mevcutKullanici.setKurum(guncelKullanici.getKurum());
        mevcutKullanici.setRol(guncelKullanici.getRol());
        mevcutKullanici.setMentor(guncelKullanici.getMentor());
        mevcutKullanici.setIlgiAlani(guncelKullanici.getIlgiAlani());

        return kullaniciRepository.save(mevcutKullanici);
    }

    //DELETE
    @IslemLogu(islemAdi = "KULLANICI SİLİNDİ")
    public void kullaniciSil(Long id) {
        kullaniciRepository.deleteById(id);
    }
}