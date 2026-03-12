package com.ankarabt.stajyonetim.repository;

import com.ankarabt.stajyonetim.entity.Kullanici;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KullaniciRepository extends JpaRepository<Kullanici, Long> {

    // 1. Kayıt olurken e-postanın var olup olmadığını kontrol etmek için
    Optional<Kullanici> findByEposta(String eposta);

    // 2. Giriş yaparken e-posta ve şifre eşleşmesini kontrol etmek için
    Optional<Kullanici> findByEpostaAndSifre(String eposta, String sifre);
}