Kullanılan Teknolojiler
Bu proje, modern web geliştirme standartları ve Java ekosistemi kullanılarak inşa edilmiştir:

Backend: Java & Spring Boot

Veritabanı: MySQL

ORM: Hibernate / Spring Data JPA

Bağımlılık Yönetimi: Maven

Not Alma ve Planlama: Proje sürecinde Notion kullanılmıştır.

Temel Özellikler
Etkinlik Yönetimi: Yeni etkinliklerin oluşturulması ve listelenmesi.

Gönüllü Katılımı: Kullanıcıların etkinliklere kayıt olabilmesi.

Dinamik Veri Yapısı: Veritabanı ile senkronize çalışan kullanıcı ve etkinlik yönetimi.
Kurulum ve Çalıştırma
Projeyi yerel bilgisayarınızda çalıştırmak için şu adımları izleyebilirsiniz:

1)Bu repoyu klonlayın: git clone https://github.com/kullanici-adin/volunteering-event-platform.git

2)src/main/resources/application.properties dosyasını oluşturun ve kendi MySQL veritabanı bilgilerinizi girin.

3)Proje ana dizininde terminali açın ve projeyi çalıştırın:
mvn spring-boot:run
4)Tarayıcınızdan http://localhost:8080 adresine giderek uygulamaya erişin.
