# SimpleAccounting
Ft Teknoloji Practium Case
# Frontend - Vaadin Framework ile kodlandı.
- Frontend uygulaması çalışmaz ise mvn komutu çalıştırılmalı.
```maven
mvn clean package
```
- Aynı zamanda staj yaptığım için modelmapper ve dto yapısını atladım.
- ufuk 123456 hazır kullanıcı - içerisinde işlemler mevcut
- mehmet 123456 hazır kullanıcı
- Account servisinin otomatik olarak hazır verileri ekleyen data.sql i mevcut proje ayağa kalkarken çalışıyor, tekrar ayağa kaldırılırken hata verirse application.yml de yazan işlem yapılmalı.

# Docker ile veritabanlarını ayağa kaldırma
```
docker-compose up -d
```
- http://localhost:8080/swagger-ui/index.html Account Swagger UI - Bearer Token authentication
- http://localhost:8081/swagger-ui/index.html Exchange Swagger UI - ApiKey authentication
- Frontend http://localhost:8085
- Hesap Servisi http://localhost:8080
- Exchange Servisi http://localhost:8081

![img.png](image/img.png)
# Uygulama ekranları
- login page
- ![img_1.png](image/img_1.png)
- Account page
- ![img_2.png](image/img_2.png)
- Account Activities page
- ![img_3.png](image/img_3.png)
- Exchange page
- ![img_4.png](image/img_4.png)
