package com.ankarabt.stajyonetim.service;

import com.ankarabt.stajyonetim.core.annotation.IslemLogu;
import com.ankarabt.stajyonetim.entity.Rol;
import com.ankarabt.stajyonetim.repository.RolRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolService {

    private final RolRepository rolRepository;

    public RolService(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    private void rolValidasyonu(Rol rol, Long mevcutId) {
        if (rol.getRolAdi() == null || rol.getRolAdi().trim().isEmpty()) {
            throw new RuntimeException("Rol adı boş bırakılamaz.");
        }

        // OTOMASYON
        String formatliRolAdi = rol.getRolAdi().trim().toUpperCase();
        if (!formatliRolAdi.startsWith("ROLE_")) {
            formatliRolAdi = "ROLE_" + formatliRolAdi;
        }
        rol.setRolAdi(formatliRolAdi);


        if (rol.getRolAdi().length() > 50) {
            throw new RuntimeException("Rol adı ('ROLE_' dahil) 50 karakteri geçemez.");
        }

        //Unique Kontrol
        Optional<Rol> rolKontrol = rolRepository.findByRolAdi(rol.getRolAdi());
        if (rolKontrol.isPresent() && !rolKontrol.get().getId().equals(mevcutId)) {
            throw new RuntimeException(rol.getRolAdi() + " adında bir rol zaten mevcut.");
        }
    }

    @IslemLogu(islemAdi = "YENİ SİSTEM ROLÜ EKLENDİ")
    public Rol rolEkle(Rol rol) {
        rolValidasyonu(rol, null);
        return rolRepository.save(rol);
    }

    public List<Rol> tumRolleriGetir() {
        return rolRepository.findAll();
    }

    @IslemLogu(islemAdi = "SİSTEM ROLÜ GÜNCELLENDİ")
    public Rol rolGuncelle(Long id, Rol guncelRol) {
        Rol mevcutRol = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Güncellenecek rol bulunamadı!"));

        rolValidasyonu(guncelRol, id);
        mevcutRol.setRolAdi(guncelRol.getRolAdi());

        return rolRepository.save(mevcutRol);
    }

    @IslemLogu(islemAdi = "SİSTEM ROLÜ SİLİNDİ")
    public void rolSil(Long id) {
        rolRepository.deleteById(id);
    }
}