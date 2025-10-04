package com.sorokaandriy.springsecurityjwt.webtoken;

import io.jsonwebtoken.Jwts;
import jakarta.xml.bind.DatatypeConverter;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

public class JwtSecretMakerTest {

    //generate secret key in HS512 algorithm and convert into string
    @Test
    public void generateKey(){
        SecretKey secretKey = Jwts.SIG.HS512.key().build();
        String encodedKey = DatatypeConverter.printHexBinary(secretKey.getEncoded());
        System.out.printf("Key = [%s]",encodedKey);
    }
}
