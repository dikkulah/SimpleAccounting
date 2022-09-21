# SimpleAccounting

Ft Teknoloji Practium Case

Dil:Java Spring Boot

Kullanılacak Teknolojiiler:Postgre SQL, Swagger

Kullanıcı Ekranları:

Kullanıcı Giriş Ekranı: Kullanıcının bilgileri ile giriş yaptığı bölümdür. Sistemde yeni kullanıcı ekleme gibi bir yapı
vakit alacağı için gerekli görülmemiştir. Birden fazla olacak şekilde daha önceden kaydettiğiniz kullanıcı bilgilerini
kullanabilirsiniz.

Hesap Bilgileri Ekranı: TL, Dolar, Euro ve altın cinsinden hesap durumlarının görüntülendiği ekrandır. Bu ve bundan
sonraki ekranlara sadece 'authenticated' olan hesabın erişme yetkisi olmalıdır. ++++

Hesap Detayı Ekranı:Seçilen hesabın (TL, Dolar vs.) son hesap durumunu, hesap hareketlerini içeren bölümdür. Varsayılan
olarak son 5 işlemi ekrana getirmelidir. Getirilen bir kayıt şu değerleri içermelidir.

Zaman, Miktar (Çıkış işlemi ise eksi görülmeli), Açıklama (Alım satım işlemleri sonucunda bu bölüme otomatik bir içerik
girilecektir.)

Alım Satım Ekranı: Kullanıcının hesabında var olan para veya altın ile diğer cinslerdeki para birimlerinden alım veya
satım yapabildiği bölümdür. Satış sadece hesaplarında satmak istediği cinsten bir değer var ise gerçekleşmelidir.
Kullanıcı işlem yapmak istediği hesabı ve karşılığında alıp satacağı para-altın birimini seçtiğinde müşteri önünde
random olarak yaratılacak (piyasa fiyatları ile makul olan değerler gösterin lütfen) alış satış fiyatı gösterilecek.
Miktar ve işlem tipine göre yapılan işlem sonucu kullanıcı hesaplarına alış veya satış olarak yansıtılacaktır. Açıklama
olarak da alış yapılan hesaba Alış [Alınan Miktar] @ [Alım Fiyatı], satış yapılan hesaba Satış [Satılan Miktar]
@ [Satış Fiyatı] olarak yansıtılacaktır.

Backend İle ilgili Beklentiler:

2 ayrı servis halinde sistem tasarlanmalıdır.
Login, Hesap bilgi ve detaylarının sorgulandığı api'lerin çağrıldığı bölüm Hesap Servisi, Alış-Satış yapılan servis
Exchange Servisi olarak adlandırılacaktır.
Bir kullanıcı hesap servisi
üzerinden diğer tüm bilgilere erişebileceği authentication token'ini almalı, hesap servisi bu token'i saklamalı ve
Exchange servisi, token'i hesap servisi üzerinden doğrulamalıdır.

Hesap servisi ve exchange servisi arasındaki iletişimde apikey-api secret yapısı kurulmalıdır.

Exchange Servisi alım satım fiyatı gibi detayları sabit bir değer olarak kullanıcıya gönderilsin. Alım satım işlem
isteğini hesap servisine yönlendirip , hesaplarda yeterli miktar var ise işlemi gerçekleştirmelidir. Hesabın aynı anda
başka bir işlem gerçekleştirmesi ihtimaline karşı bu yapı bir transaction şeklinde gerçekleştirilmelidir.

Hesap Servisi üzerinde alım satımı kontrol eden bölüm için lütfen JUNIT üzerinde basit bri unit test yazınız. 
