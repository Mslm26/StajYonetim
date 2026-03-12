package com.ankarabt.stajyonetim.controller;


import com.ankarabt.stajyonetim.entity.Kullanici;
import com.ankarabt.stajyonetim.entity.Kurum;
import com.ankarabt.stajyonetim.service.KurumService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kurumlar")
public class KurumController {

    private final KurumService kurumService;

    public KurumController(KurumService kurumService) {
        this.kurumService = kurumService;
    }

    //CREATE
    @PostMapping
    public ResponseEntity<Kurum> kurumEkle(@RequestBody Kurum kurum) {
        Kurum kaydedilenKurum = kurumService.kurumEkle(kurum);
        return ResponseEntity.ok(kaydedilenKurum);
    }

    //READ
    @GetMapping
    public ResponseEntity<List<Kurum>> tumKurumlariGetir() {
        List<Kurum> kurumlar = kurumService.tumKurumlariGetir();
        return ResponseEntity.ok(kurumlar);
    }

    //UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Kurum> kurumGuncelle(@PathVariable Long id, @RequestBody Kurum guncelKurum) {
        Kurum guncellenenKurum = kurumService.kurumGuncelle(id, guncelKurum);
        return ResponseEntity.ok(guncellenenKurum);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> kurumSil(@PathVariable Long id) {
        kurumService.kurumSil(id);
        return ResponseEntity.noContent().build();
    }


}



















