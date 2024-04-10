package no.ntnu.courses.coursesapi.api.user;

import java.util.Base64;
import java.security.SecureRandom;

public class SecretKeyGenerator {
  public static void main(String[] args) {
    SecureRandom secureRandom = new SecureRandom(); //threadsafe
    byte[] token = new byte[32]; //256 bit
    secureRandom.nextBytes(token);
    String secretKey = Base64.getEncoder().encodeToString(token);
    System.out.println(secretKey);
  }
}