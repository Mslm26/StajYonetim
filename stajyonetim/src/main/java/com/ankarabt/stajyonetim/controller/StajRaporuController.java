package com.ankarabt.stajyonetim.controller;

import com.ankarabt.stajyonetim.entity.StajRaporu;
import com.ankarabt.stajyonetim.service.StajRaporuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/raporlar")
public class StajRaporuController {

    private final StajRaporuService raporService;

    public StajRaporuController(StajRaporuService raporService) {
        this.raporService = raporService;
    }

    //CRUD
    @PostMapping
    public ResponseEntity<StajRaporu> raporEkle(@RequestBody StajRaporu rapor) {
        return ResponseEntity.ok(raporService.raporEkle(rapor));
    }

    @GetMapping
    public ResponseEntity<List<StajRaporu>> tumRaporlariGetir() {
        return ResponseEntity.ok(raporService.tumRaporlariGetir());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StajRaporu> raporGetirById(@PathVariable Long id) {
        return ResponseEntity.ok(raporService.raporGetirById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StajRaporu> raporGuncelle(@PathVariable Long id, @RequestBody StajRaporu guncelRapor) {
        return ResponseEntity.ok(raporService.raporGuncelle(id, guncelRapor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> raporSil(@PathVariable Long id) {
        raporService.raporSil(id);
        return ResponseEntity.noContent().build();
    }




    @PutMapping("/{id}/sirket-onayla")
    public ResponseEntity<StajRaporu> sirketOnayla(@PathVariable Long id) {
        return ResponseEntity.ok(raporService.sirketOnayla(id));
    }

    @PutMapping("/{id}/akademisyen-onayla")
    public ResponseEntity<StajRaporu> akademisyenOnayla(@PathVariable Long id) {
        return ResponseEntity.ok(raporService.akademisyenOnayla(id));
    }

    @PutMapping("/{id}/reddet")
    public ResponseEntity<StajRaporu> raporReddet(@PathVariable Long id, @RequestParam String retSebebi) {
        return ResponseEntity.ok(raporService.raporReddet(id, retSebebi));
    }

    @GetMapping("/ogrenci/{stajyerId}/defter")
    public ResponseEntity<List<StajRaporu>> onaylanmisStajDefteriniGetir(@PathVariable Long stajyerId) {
        return ResponseEntity.ok(raporService.onaylanmisStajDefteriniGetir(stajyerId));
    }
}