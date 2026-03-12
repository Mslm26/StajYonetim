package com.ankarabt.stajyonetim.controller;

import com.ankarabt.stajyonetim.core.security.JwtService;
import com.ankarabt.stajyonetim.entity.Kullanici;
import com.ankarabt.stajyonetim.service.KullaniciService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final KullaniciService kullaniciService;
    private final JwtService jwtService;

    public AuthController(KullaniciService kullaniciService, JwtService jwtService) {
        this.kullaniciService = kullaniciService;
        this.jwtService = jwtService;
    }

    // URL: POST http://localhost:8080/api/auth/kayit-ol
    @PostMapping("/kayit-ol")
    public ResponseEntity<Map<String, String>> kayitOl(@RequestBody Kullanici kullanici) {
        kullanici.setAktifMi(true);
        kullaniciService.kullaniciEkle(kullanici);

        Map<String, String> response = new HashMap<>();
        response.put("mesaj", "Kayıt başarıyla oluşturuldu. Lütfen giriş yapınız.");
        return ResponseEntity.ok(response);
    }

    // URL: POST http://localhost:8080/api/auth/giris-yap
    @PostMapping("/giris-yap")
    public ResponseEntity<Map<String, Object>> girisYap(@RequestBody Map<String, String> loginIstegi) {
        String eposta = loginIstegi.get("eposta");
        String sifre = loginIstegi.get("sifre");

        Kullanici girenKullanici = kullaniciService.girisYap(eposta, sifre);

        Map<String, Object> response = new HashMap<>();

        if (girenKullanici.getKurum() != null && girenKullanici.getRol() != null) {

            String rolAdi = girenKullanici.getRol().getRolAdi();
            String token = jwtService.tokenOlustur(eposta, rolAdi);

            response.put("token", token);
            response.put("yonlendirme", "/panel");
        } else {
            // Kurumu/Rolü yoka token verme basvuruya yolla
            response.put("yonlendirme", "/basvuru");
            response.put("mesaj", "Lütfen önce CV ve kurum başvurunuzu tamamlayın.");
        }

        return ResponseEntity.ok(response);
    }
}