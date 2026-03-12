package com.ankarabt.stajyonetim.service;

import com.ankarabt.stajyonetim.entity.Kullanici;
import com.ankarabt.stajyonetim.entity.SistemLog;
import com.ankarabt.stajyonetim.repository.SistemLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SistemLogService {

    private final SistemLogRepository sistemLogRepository;

    public SistemLogService(SistemLogRepository sistemLogRepository) {
        this.sistemLogRepository = sistemLogRepository;
    }

    private void logValidasyonu(SistemLog log) {
        if (log.getIslem() == null || log.getIslem().trim().isEmpty()) {
            throw new RuntimeException("Log işlemi boş bırakılamaz.");
        }
        if (log.getIslem().length() > 100) {
            throw new RuntimeException("Log işlemi 100 karakteri geçemez.");
        }
        if (log.getIpAdresi() != null && log.getIpAdresi().length() > 50) {
            throw new RuntimeException("IP adresi 50 karakteri geçemez.");
        }
    }

    //CREATE
    public SistemLog logEkle(SistemLog log) {
        logValidasyonu(log);
        return sistemLogRepository.save(log);
    }

    // AOP metodu
    public void hizliLogOlustur(String islem, String detay, String ipAdresi, Kullanici kullanici) {
        SistemLog log = new SistemLog();
        log.setIslem(islem);
        log.setDetay(detay);
        log.setIpAdresi(ipAdresi);
        log.setKullanici(kullanici);

        logValidasyonu(log);
        sistemLogRepository.save(log);
    }

    //READ
    public List<SistemLog> tumLoglariGetir() {
        return sistemLogRepository.findAll();
    }

    public SistemLog logGetirById(Long id) {
        return sistemLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hata: Log kaydı bulunamadı!"));
    }

}