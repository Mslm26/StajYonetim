package com.ankarabt.stajyonetim.controller;

import com.ankarabt.stajyonetim.entity.Kurum;
import com.ankarabt.stajyonetim.repository.KurumRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {


    @GetMapping(value = {"/", "/giris"})
    public String girisSayfasi() {
        return "login";
    }


    @GetMapping("/panel")
    public String anaPanel() {
        return "dashboard";
    }


}