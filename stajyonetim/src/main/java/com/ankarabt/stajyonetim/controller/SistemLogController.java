package com.ankarabt.stajyonetim.controller;

import com.ankarabt.stajyonetim.entity.SistemLog;
import com.ankarabt.stajyonetim.service.SistemLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loglar")
public class SistemLogController {

    private final SistemLogService sistemLogService;

    public SistemLogController(SistemLogService sistemLogService) {
        this.sistemLogService = sistemLogService;
    }

    @GetMapping
    public ResponseEntity<List<SistemLog>> tumLoglariGetir() {
        return ResponseEntity.ok(sistemLogService.tumLoglariGetir());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SistemLog> logGetirById(@PathVariable Long id) {
        return ResponseEntity.ok(sistemLogService.logGetirById(id));
    }
}