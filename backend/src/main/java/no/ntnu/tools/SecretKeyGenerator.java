package no.ntnu.tools;

import java.security.SecureRandom;
import java.util.Base64;


/**
 * The SecretKeyGenerator class is a utility class that generates a key.
 * The generated key is a Base64 string which can be used as a JWT secret key.
 * The generated key should be kept secret and used for
 * signing and verifying JWT tokens in your application.
 * <p>
 *  Written with assistance from AI
 */
public class SecretKeyGenerator {
  /**
   * The main method for the SecretKeyGenerator class.
   *
   * @param args an array of command-line arguments for the application
   */
  public static void main(String[] args) {
    SecureRandom secureRandom = new SecureRandom();
    byte[] token = new byte[32];
    secureRandom.nextBytes(token);
    String secretKey = Base64.getEncoder().encodeToString(token);
    System.out.println(secretKey);
  }
}