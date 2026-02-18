package com.elanur_sude.gonulluluk_proje;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.elanur_sude.gonulluluk_proje.model.Role;
import com.elanur_sude.gonulluluk_proje.model.User;
import com.elanur_sude.gonulluluk_proje.repository.UserRepository;

@SpringBootApplication
public class GonullulukProjeApplication {

    public static void main(String[] args) {
        SpringApplication.run(GonullulukProjeApplication.class, args);
    }

    // Uygulama ayağa kalkınca bir kez çalışır, admin yoksa ekler
   // import org.springframework.security.crypto.password.PasswordEncoder; ekli olduğunda

@Bean
CommandLineRunner seed(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    return args -> {
        userRepository.findByUsername("admin").ifPresentOrElse(u -> {
            String stored = u.getPassword();
            // Eğer mevcut şifre bcrypt ile hashlenmemişse (basit kontrol: "$2" ile başlıyor mu diye bak)
            if (stored == null || !stored.startsWith("$2")) {
                // Eğer stored doluysa onu hashle, değilse default "123456" ile oluştur
                String toHash = (stored == null || stored.isEmpty()) ? "123456" : stored;
                u.setPassword(passwordEncoder.encode(toHash));
                userRepository.save(u);
                System.out.println(">>> Seed: var olan adminin şifresi hashlenip güncellendi.");
            } else {
                System.out.println(">>> Seed: admin zaten hashed.");
            }
        }, () -> {
            User admin = new User(
                null,
                "admin",
                "admin@example.com",
                passwordEncoder.encode("123456"), // burası  hashli
                Role.ADMIN
            );
            userRepository.save(admin);
            System.out.println(">>> Seed: admin oluşturuldu (şifre hashlenmiş).");
        });
    };
}

}
