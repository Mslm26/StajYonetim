# Staj Yönetim Sistemi - REST API

Bu proje, üniversite öğrencilerinin staj süreçlerini, kurum değerlendirmelerini ve rapor onay mekanizmalarını dijitalleştirmek amacıyla geliştirilmiş modern bir 
RESTful arka uç (backend) hizmetidir. Sistem, kurumsal düzeyde güvenlik ve modüler bir mimari gözetilerek tasarlanmıştır.

## Kullanılan Teknolojiler
* **Dil & Çatı:** Java, Spring Boot 3.x
* **Güvenlik:** Spring Security, JWT (JSON Web Token), BCrypt Şifreleme
* **Veritabanı:** PostgreSQL, Spring Data JPA (Hibernate)
* **API Belgelendirme:** Swagger / OpenAPI

## Öne Çıkan Özellikler (Mimari)
* **Durumsuz (Stateless) Kimlik Doğrulama:** JWT kullanılarak sunucu belleği yorulmadan güvenli oturum yönetimi sağlanmıştır.
* **Rol Bazlı Erişim Kontrolü (RBAC):** Sistemde Admin, Kurum Yöneticisi, Akademisyen, Mentör ve Stajyer olmak üzere 5 farklı rol bulunur.
  Kritik API uçları (örn: Kurum ekleme, Rapor onaylama) `hasAnyAuthority` kalkanlarıyla yetkisiz erişime kapatılmıştır.
* **Global Hata Yönetimi (Exception Handling):** `@RestControllerAdvice` kullanılarak sistemdeki tüm iş kuralı ve format ihlalleri yakalanır.
  İstemciye (Frontend) anlamsız sunucu hataları (500) yerine standartlaştırılmış, şık ve okunabilir JSON geri bildirimleri (Feedback) sunulur.
* **Katmanlı Mimari:** Controller, Service ve Repository katmanları birbirinden izole edilerek temiz kod (Clean Code) prensiplerine uygun geliştirilmiştir.
