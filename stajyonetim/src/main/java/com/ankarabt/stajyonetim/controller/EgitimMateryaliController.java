package com.ankarabt.stajyonetim.controller;

import com.ankarabt.stajyonetim.entity.EgitimMateryali;
import com.ankarabt.stajyonetim.service.EgitimMateryaliService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materyaller")
public class EgitimMateryaliController {

    private final EgitimMateryaliService materyalService;

    public EgitimMateryaliController(EgitimMateryaliService materyalService) {
        this.materyalService = materyalService;
    }

    @PostMapping
    public ResponseEntity<EgitimMateryali> materyalEkle(@RequestBody EgitimMateryali materyal) {
        return ResponseEntity.ok(materyalService.materyalEkle(materyal));
    }

    @GetMapping
    public ResponseEntity<List<EgitimMateryali>> tumMateryalleriGetir() {
        return ResponseEntity.ok(materyalService.tumMateryalleriGetir());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EgitimMateryali> materyalGuncelle(@PathVariable Long id, @RequestBody EgitimMateryali guncelMateryal) {
        return ResponseEntity.ok(materyalService.materyalGuncelle(id, guncelMateryal));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> materyalSil(@PathVariable Long id) {
        materyalService.materyalSil(id);
        return ResponseEntity.noContent().build();
    }
}