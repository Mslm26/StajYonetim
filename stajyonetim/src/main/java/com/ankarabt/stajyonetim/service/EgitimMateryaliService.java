package com.ankarabt.stajyonetim.service;

import com.ankarabt.stajyonetim.core.annotation.IslemLogu;
import com.ankarabt.stajyonetim.entity.EgitimMateryali;
import com.ankarabt.stajyonetim.repository.EgitimMateryaliRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EgitimMateryaliService {

    private final EgitimMateryaliRepository egitimMateryaliRepository;

    public EgitimMateryaliService(EgitimMateryaliRepository egitimMateryaliRepository) {
        this.egitimMateryaliRepository = egitimMateryaliRepository;
    }

    private void materyalValidasyonu(EgitimMateryali materyal) {
        if (materyal.getBaslik() == null || materyal.getBaslik().trim().isEmpty()) {
            throw new RuntimeException("Materyal başlığı boş bırakılamaz.");
        }
        if (materyal.getBaslik().length() > 200) {
            throw new RuntimeException("Materyal başlığı 200 karakteri geçemez.");
        }
        if (materyal.getLink() == null || materyal.getLink().trim().isEmpty()) {
            throw new RuntimeException("Materyal bağlantı linki boş bırakılamaz.");
        }
        if (materyal.getKategori() == null || materyal.getKategori().getId() == null) {
            throw new RuntimeException("Materyal mutlaka bir kategoriye bağlı olmalıdır.");
        }
        if (materyal.getEkleyen() == null || materyal.getEkleyen().getId() == null) {
            throw new RuntimeException("Materyali ekleyen kişi belirtilmelidir.");
        }


        if (materyal.getEkleyen().getRol() != null) {
            String rolAdi = materyal.getEkleyen().getRol().getRolAdi();
            if (!rolAdi.equals("ROLE_MENTOR") && !rolAdi.equals("ROLE_AKADEMISYEN")) {
                throw new RuntimeException("Sadece Mentör veya Akademisyen rolündeki kullanıcılar sisteme eğitim materyali ekleyebilir.");
            }
        }
    }

    //CREATE
    @IslemLogu(islemAdi = "YENİ EĞİTİM MATERYALİ EKLENDİ")
    public EgitimMateryali materyalEkle(EgitimMateryali materyal) {
        materyalValidasyonu(materyal);
        return egitimMateryaliRepository.save(materyal);
    }

    //READ
    public List<EgitimMateryali> tumMateryalleriGetir() {
        return egitimMateryaliRepository.findAll();
    }

    //UPDATE
    @IslemLogu(islemAdi = "EĞİTİM MATERYALİ GÜNCELLENDİ")
    public EgitimMateryali materyalGuncelle(Long id, EgitimMateryali guncelMateryal) {
        EgitimMateryali mevcutMateryal = egitimMateryaliRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Güncellenecek materyal bulunamadı!"));

        materyalValidasyonu(guncelMateryal);
        mevcutMateryal.setBaslik(guncelMateryal.getBaslik());
        mevcutMateryal.setLink(guncelMateryal.getLink());
        mevcutMateryal.setTur(guncelMateryal.getTur());
        mevcutMateryal.setZorlukSeviyesi(guncelMateryal.getZorlukSeviyesi());
        mevcutMateryal.setKategori(guncelMateryal.getKategori());

        return egitimMateryaliRepository.save(mevcutMateryal);
    }

    //DELETE
    @IslemLogu(islemAdi = "EĞİTİM MATERYALİ SİLİNDİ")
    public void materyalSil(Long id) {
        egitimMateryaliRepository.deleteById(id);
    }
}