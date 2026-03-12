package com.ankarabt.stajyonetim.controller;

import com.ankarabt.stajyonetim.entity.Degerlendirme;
import com.ankarabt.stajyonetim.service.DegerlendirmeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/degerlendirmeler")
public class DegerlendirmeController {

    private final DegerlendirmeService degerlendirmeService;

    public DegerlendirmeController(DegerlendirmeService degerlendirmeService) {
        this.degerlendirmeService = degerlendirmeService;
    }

    @PostMapping
    public ResponseEntity<Degerlendirme> degerlendirmeEkle(@RequestBody Degerlendirme degerlendirme) {
        return ResponseEntity.ok(degerlendirmeService.degerlendirmeEkle(degerlendirme));
    }

    @GetMapping
    public ResponseEntity<List<Degerlendirme>> tumDegerlendirmeleriGetir() {
        return ResponseEntity.ok(degerlendirmeService.tumDegerlendirmeleriGetir());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Degerlendirme> degerlendirmeGuncelle(@PathVariable Long id, @RequestBody Degerlendirme guncelDegerlendirme) {
        return ResponseEntity.ok(degerlendirmeService.degerlendirmeGuncelle(id, guncelDegerlendirme));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> degerlendirmeSil(@PathVariable Long id) {
        degerlendirmeService.degerlendirmeSil(id);
        return ResponseEntity.noContent().build();
    }
}