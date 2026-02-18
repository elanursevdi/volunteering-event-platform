package com.elanur_sude.gonulluluk_proje.controller;

import com.elanur_sude.gonulluluk_proje.model.User;
import com.elanur_sude.gonulluluk_proje.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Yeni kullanıcı ekle (sadece admin)
    @PostMapping("/admin/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    // Kullanıcı kayıt (register)
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {//Postman'ın gönderdiği JSON User nesnesine dönüşür.
        if (userService.usernameExists(user.getUsername())) {//Daha önce aynı username kayıtlı mı kontrol edilir.
            return ResponseEntity.badRequest().body("Kullanıcı adı zaten mevcut.");
        }
        if (userService.emailExists(user.getEmail())) {//Email kontrolü yapılır.
            return ResponseEntity.badRequest().body("Email zaten kayıtlı.");
        }
        User saved = userService.register(user);//userService.register(user) çağrılır, DB’ye kaydedilir
        return ResponseEntity.ok(saved);//başarıyla kayıt edilen kullanıcıyı döner.
    }

    // Kullanıcı giriş (login)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        boolean success = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        if (!success) {
            return ResponseEntity.badRequest().body("Kullanıcı adı veya şifre hatalı.");
        }

        return userService.getUserByUsername(loginRequest.getUsername())
                .<ResponseEntity<?>>map(u -> ResponseEntity.ok(new SimpleUser(u.getId(), u.getUsername(), u.getRole().name())))
                .orElseGet(() -> ResponseEntity.badRequest().body("Kullanıcı bulunamadı."));
    }

    // Şifreyi döndürmeden basit kullanıcı yanıtı
    public static class SimpleUser {
        public Long id;
        public String username;
        public String role;
        public SimpleUser(Long id, String username, String role) {
            this.id = id;
            this.username = username;
            this.role = role;
        }
    }

    // Tüm kullanıcıları getir
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // ID ile kullanıcı getir
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Kullanıcıyı sil
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build(); // HTTP 204
    }
}
