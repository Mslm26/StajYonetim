package com.ankarabt.stajyonetim.controller;

import com.ankarabt.stajyonetim.entity.StajyerMateryalDurumu;
import com.ankarabt.stajyonetim.service.StajyerMateryalDurumuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/egitim-atamalari")
public class StajyerMateryalDurumuController {

    private final StajyerMateryalDurumuService durumService;

    public StajyerMateryalDurumuController(StajyerMateryalDurumuService durumService) {
        this.durumService = durumService;
    }

    @PostMapping
    public ResponseEntity<StajyerMateryalDurumu> atamaYap(@RequestBody StajyerMateryalDurumu durum) {
        return ResponseEntity.ok(durumService.atamaYap(durum));
    }

    @GetMapping
    public ResponseEntity<List<StajyerMateryalDurumu>> tumAtamalariGetir() {
        return ResponseEntity.ok(durumService.tumAtamalariGetir());
    }

    @PutMapping("/{id}/tamamla")
    public ResponseEntity<StajyerMateryalDurumu> materyaliTamamla(@PathVariable Long id) {
        return ResponseEntity.ok(durumService.materyaliTamamla(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> atamaSil(@PathVariable Long id) {
        durumService.atamaSil(id);
        return ResponseEntity.noContent().build();
    }
}