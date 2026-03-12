package com.ankarabt.stajyonetim.controller;

import com.ankarabt.stajyonetim.entity.Kullanici;
import com.ankarabt.stajyonetim.service.KullaniciService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/kullanicilar")
public class KullaniciController {

    private final KullaniciService kullaniciService;

    public KullaniciController(KullaniciService kullaniciService) {
        this.kullaniciService = kullaniciService;
    }

    //LOGIN
    @PostMapping("/giris")
    public ResponseEntity<Kullanici> girisYap(@RequestBody Map<String, String> kimlikBilgileri) {
        String eposta = kimlikBilgileri.get("eposta");
        String sifre = kimlikBilgileri.get("sifre");

        Kullanici girenKullanici = kullaniciService.girisYap(eposta, sifre);
        return ResponseEntity.ok(girenKullanici);
    }

    //CREATE
    @PostMapping
    public ResponseEntity<Kullanici> kullaniciEkle(@RequestBody Kullanici kullanici) {
        return ResponseEntity.ok(kullaniciService.kullaniciEkle(kullanici));
    }

    //READ TÜM KULLANICILAR
    @GetMapping
    public ResponseEntity<List<Kullanici>> tumKullanicilariGetir() {
        return ResponseEntity.ok(kullaniciService.tumKullanicilariGetir());
    }

    // READ TEK KULLANICI
    @GetMapping("/{id}")
    public ResponseEntity<Kullanici> kullaniciGetirById(@PathVariable Long id) {
        return ResponseEntity.ok(kullaniciService.kullaniciGetirById(id));
    }

    //UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Kullanici> kullaniciGuncelle(@PathVariable Long id, @RequestBody Kullanici guncelKullanici) {
        return ResponseEntity.ok(kullaniciService.kullaniciGuncelle(id, guncelKullanici));
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> kullaniciSil(@PathVariable Long id) {
        kullaniciService.kullaniciSil(id);
        return ResponseEntity.noContent().build();
    }
}