package com.ankarabt.stajyonetim.controller;

import com.ankarabt.stajyonetim.entity.Rol;
import com.ankarabt.stajyonetim.service.RolService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roller")
public class RolController {

    private final RolService rolservice;

    public RolController(RolService rolservice) {
        this.rolservice = rolservice;
    }

    //CREATE
    @PostMapping
    public ResponseEntity<Rol> rolEkle(@RequestBody Rol rol) {
        return ResponseEntity.ok(rolservice.rolEkle(rol));
    }

    //READ
    @GetMapping
    public ResponseEntity<List<Rol>> tumRolleriGetir() {
        return ResponseEntity.ok(rolservice.tumRolleriGetir());
    }

    //UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Rol> rolGuncelle(@PathVariable Long id, @RequestBody Rol guncelRol) {
        return ResponseEntity.ok(rolservice.rolGuncelle(id, guncelRol));
    }

    //DELETE
    public ResponseEntity<Void> rolSil(@PathVariable Long id) {
        rolservice.rolSil(id);
        return ResponseEntity.noContent().build();
    }
}
