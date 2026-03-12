package com.ankarabt.stajyonetim.service;

import com.ankarabt.stajyonetim.core.annotation.IslemLogu;
import com.ankarabt.stajyonetim.entity.Kurum;
import com.ankarabt.stajyonetim.repository.KurumRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;


@Service
public class KurumService {

    private final KurumRepository kurumRepository;

    // RegEx Kalıpları
    private static final String EMAIL_PATTERN = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private static final String TELEFON_PATTERN = "^(\\+90|0)?[1-9][0-9]{9}$"; // Türkiye formatı
    private static final String VERGI_NO_PATTERN = "^[0-9]{10}$"; // Türkiye Vergi No

    public KurumService(KurumRepository kurumRepository) {
        this.kurumRepository = kurumRepository;
    }

    private void kurumValidasyon(Kurum kurum, Long mevcutId) {
        //1. Kurum Adı
        if (kurum.getKurumAdi() == null || kurum.getKurumAdi().trim().isEmpty()) {
            throw new RuntimeException("Kurum adı boş bırakılamaz");
        }
        if (kurum.getKurumAdi().length() > 100){
            throw new RuntimeException("Kurum adı 100 karakteri geçemez");
        }
        //2. Kurum Tipi
        if (kurum.getKurumTipi() == null || kurum.getKurumTipi().trim().isEmpty()) {
            throw new RuntimeException("Kurum tipi boş bırakılamaz");
        }
        //3. Vergi numarası kontrolü
        if(kurum.getVergiNo() != null && !kurum.getVergiNo().trim().isEmpty()){
            if (!Pattern.matches(VERGI_NO_PATTERN, kurum.getVergiNo())) {
                throw new RuntimeException("Vergi numarası 10 haneli rakamlardan  oluşmalıdır.");
            }
            Optional<Kurum> vergiNoKontrol = kurumRepository.findByVergiNo(kurum.getVergiNo());
            if (vergiNoKontrol.isPresent() && !vergiNoKontrol.get().getId().equals(mevcutId)) {
                throw new RuntimeException("Bu vergi numarası sistemde başka bir kuruma ait");
            }
        }
        //4. E-posta
        if (kurum.getEposta() != null && !kurum.getEposta().trim().isEmpty()) {
            if (!Pattern.matches(EMAIL_PATTERN, kurum.getEposta())) {
                throw new RuntimeException("Geçersiz e-posta formatı.");
            }
            Optional<Kurum> epostaKontrol = kurumRepository.findByEposta(kurum.getEposta());
            if (epostaKontrol.isPresent() && !epostaKontrol.get().getId().equals(mevcutId)) {
                throw new RuntimeException("Bu e-posta adresi başka bir kurum tarafından kullanılıyor.");
            }
        }
        //5. Telefon
        if (kurum.getTelefon() != null && !kurum.getTelefon().trim().isEmpty()) {
            if (!Pattern.matches(TELEFON_PATTERN, kurum.getTelefon())) {
                throw new RuntimeException("Validasyon Hatası: Geçersiz telefon numarası. (Örn: 05XXXXXXXXX)");
            }
        }
    }

    //CREATE
    @IslemLogu(islemAdi = "YENİ KURUM EKLENDİ")
    public Kurum kurumEkle(Kurum kurum){
        kurumValidasyon(kurum, null);
        return kurumRepository.save(kurum);
    }
    //READ
    public List<Kurum> tumKurumlariGetir(){
        return kurumRepository.findAll();
    }
    //UPDATE
    @IslemLogu(islemAdi = "KURUM GÜNCELLENDİ")
    public Kurum kurumGuncelle(Long id, Kurum guncelKurum){

        Kurum mevcutKurum = kurumRepository.findById(id).orElseThrow(() -> new RuntimeException("Kurum Bulunamadı."));

        if(guncelKurum.getVergiNo() != null && !guncelKurum.getVergiNo().equals(mevcutKurum.getVergiNo())){
            Optional<Kurum> vergiNoKontrol = kurumRepository.findByVergiNo(guncelKurum.getVergiNo());
            if (vergiNoKontrol.isPresent()){
                throw new RuntimeException("Bu vergi numarası ile kayıtlı başka  bir kurum var.");
            }
        }

        kurumValidasyon(guncelKurum, id);

        mevcutKurum.setKurumAdi(guncelKurum.getKurumAdi());
        mevcutKurum.setVergiNo(guncelKurum.getVergiNo());
        mevcutKurum.setKurumTipi(guncelKurum.getKurumTipi());
        mevcutKurum.setEposta(guncelKurum.getEposta());
        mevcutKurum.setTelefon(guncelKurum.getTelefon());
        mevcutKurum.setAdres(guncelKurum.getAdres());


        return kurumRepository.save(mevcutKurum);

    }
    //DELETE
    @IslemLogu(islemAdi = "KURUM SİLİNDİ")
    public void kurumSil(Long id){
        kurumRepository.deleteById(id);
    }
}
