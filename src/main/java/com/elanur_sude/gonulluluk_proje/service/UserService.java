package com.elanur_sude.gonulluluk_proje.service;

import com.elanur_sude.gonulluluk_proje.model.Role;
import com.elanur_sude.gonulluluk_proje.model.User;
import com.elanur_sude.gonulluluk_proje.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // iş mantığı katmanı (business logic)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository; // DB erişimi
    private final PasswordEncoder passwordEncoder; // şifreleme için

    // 🔹 Admin kullanıcı oluşturma (manuel kayıt)
    public User createUser(User user) {
        // Şifre her zaman hashlenmiş olmalı
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Eğer rol boşsa varsayılanı ADMIN yapıyoruz
        if (user.getRole() == null) {
            user.setRole(Role.ADMIN);
        }

        return userRepository.save(user);
    }

    // 🔹 Tüm kullanıcıları getir
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // 🔹 ID ile kullanıcı getir
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // 🔹 Username ile kullanıcı getir
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // 🔹 Kullanıcı sil
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // 🔹 Username kontrolü (kayıt sırasında çakışma olmaması için)
    public boolean usernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    // 🔹 Email kontrolü (kayıt sırasında çakışma olmaması için)
    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    // 🔹 Normal kullanıcı kayıt (register)
    public User register(User user) {
        // Eğer rol seçilmemişse otomatik USER atanır
        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }

        // Şifre hashlenip DB'ye kaydedilir
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // 🔹 Kullanıcı login (şifre kontrolü)
    public boolean login(String username, String rawPassword) {
        if (username == null || rawPassword == null) {
            return false; // null kontrolü ile 500 hatası önlenir
        }

        return userRepository.findByUsername(username)
                .map(user -> passwordEncoder.matches(rawPassword, user.getPassword()))
                .orElse(false);
    }
}
