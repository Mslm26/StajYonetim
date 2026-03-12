package com.ankarabt.stajyonetim.controller;


import com.ankarabt.stajyonetim.entity.Kategori;
import com.ankarabt.stajyonetim.service.KategoriService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kategoriler")
public class KategoriController {

    private final KategoriService kategoriService;

    public KategoriController(KategoriService kategoriService) {
        this.kategoriService = kategoriService;
    }

    //CREATE
    @PostMapping
    public ResponseEntity<Kategori> kategoriEkle (@RequestBody Kategori kategori) {
        return ResponseEntity.ok(kategoriService.kategoriEkle(kategori));
    }

    //READ
    @GetMapping
    public ResponseEntity<List<Kategori>> tumKategorileriGetir() {
        return ResponseEntity.ok(kategoriService.tumKategorileriGetir());
    }

    //UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Kategori> kategoriGuncelle(@PathVariable Long id, @RequestBody Kategori guncelKategori) {
        return ResponseEntity.ok(kategoriService.kategoriGuncelle(id, guncelKategori));
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> kategoriSil(@PathVariable Long id) {
        kategoriService.kategoriSil(id);
        return ResponseEntity.noContent().build();
    }
}
