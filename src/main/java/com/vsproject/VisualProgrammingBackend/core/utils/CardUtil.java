package com.vsproject.VisualProgrammingBackend.core.utils;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class CardUtil {

    private static final int NFC_TAG_LENGTH = 16;
    private static final SecureRandom random = new SecureRandom();

    public String generateRandomNfcTag() {
        // Create a byte array of the desired length
        byte[] bytes = new byte[NFC_TAG_LENGTH / 2]; // 2 hex characters per byte
        random.nextBytes(bytes);

        // Convert the byte array to a hexadecimal string
        StringBuilder sb = new StringBuilder(NFC_TAG_LENGTH);
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }

        return sb.toString();
    }

}
