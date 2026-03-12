package com.ankarabt.stajyonetim.core.aop;

import com.ankarabt.stajyonetim.core.annotation.IslemLogu;
import com.ankarabt.stajyonetim.service.SistemLogService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class SistemLogAspect {

    private final SistemLogService sistemLogService;

    public SistemLogAspect(SistemLogService sistemLogService) {
        this.sistemLogService = sistemLogService;
    }

    // @IslemLogu anotasyonuna sahip bir metot hata vermeden (başarıyla) tamamlandığında burası tetiklenir
    @AfterReturning(value = "@annotation(islemLogu)")
    public void logYakalayici(JoinPoint joinPoint, IslemLogu islemLogu) {

        String islem = islemLogu.islemAdi();

        // Çalışan metod detaya gidiyo
        String metodAdi = joinPoint.getSignature().getName();
        String detay = metodAdi + " işlemi başarıyla gerçekleştirildi.";

        // 3. HTTP İsteği üzerinden işlemi yapanın IP adresini yakalıyoz
        String ipAdresi = "Bilinmiyor";
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            ipAdresi = request.getRemoteAddr();
        }
        //BUNU ANLAMADIM




        // Servisi çağırıp veritabanına kaydediyoruz
        sistemLogService.hizliLogOlustur(islem, detay, ipAdresi, null);
    }
}