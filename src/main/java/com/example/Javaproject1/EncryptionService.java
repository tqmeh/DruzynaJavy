package com.example.Javaproject1;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.stereotype.Component;

@Component
public class EncryptionService {

    private final TextEncryptor textEncryptor;

    public EncryptionService(@Value("${encryption.password}") String password,
                             @Value("${encryption.salt}") String salt) {
        this.textEncryptor = Encryptors.text(password,salt);
    }

    public String encrypt(String data) {
        return textEncryptor.encrypt(data);
    }

    public String decrypt(String encryptedData) {
        return textEncryptor.decrypt(encryptedData);
    }
}
