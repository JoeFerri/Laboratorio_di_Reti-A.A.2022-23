/**
 * @author Giuseppe Ferri
 */
package assignment_6.security.crypto;

import java.nio.CharBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.stream.Collectors;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import external.com.baeldung.aes.AESUtil;


/**
 * Classe di utilità per la gestione sicura dei dati.
 */
public final class SecurityUtils {
  

  private SecurityUtils () {
    ;
  }


  /**
   * Cripta una stringa utilizzando una password.
   * @param plainText stringa da criptare
   */
  public static final String encryptPasswordBased (String plainText, String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException,
                                                                                              InvalidKeyException, NoSuchPaddingException,
                                                                                                InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {
    IvParameterSpec ivParameterSpec = SecurityUtils.generateIv();
    SecretKey key = AESUtil.getKeyFromPassword(password,salt);
    String cipherText = AESUtil.encryptPasswordBased(plainText,key,ivParameterSpec);
    return cipherText;
  }

  /**
   * Cripta una stringa utilizzando una password.
   * @param plainText stringa da criptare
   */
  public static final String encryptPasswordBased (String plainText, String password, char[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException,
                                                                                              InvalidKeyException, NoSuchPaddingException,
                                                                                                InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {
    
    String ssalt = CharBuffer.wrap(salt).chars()
                              .mapToObj(intValue -> String.valueOf((char) intValue))
                                .collect(Collectors.joining(""));
    return encryptPasswordBased(plainText,password,ssalt);
  }

  /**
   * Decripta una stringa utilizzando una password.
   * @param cipherText stringa da decriptare
   */
  public static final String dencryptPasswordBased (String cipherText, String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException,
                                                                                              InvalidKeyException, NoSuchPaddingException,
                                                                                                InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {
    IvParameterSpec ivParameterSpec = SecurityUtils.generateIv();
    SecretKey key = AESUtil.getKeyFromPassword(password,salt);
    String plainText = AESUtil.decryptPasswordBased(cipherText,key,ivParameterSpec);
    return plainText;
  }

  /**
   * Decripta una stringa utilizzando una password.
   * @param cipherText stringa da decriptare
   */
  public static final String dencryptPasswordBased (String cipherText, String password, char[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException,
                                                                                              InvalidKeyException, NoSuchPaddingException,
                                                                                                InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {
    
    String ssalt = CharBuffer.wrap(salt).chars()
                              .mapToObj(intValue -> String.valueOf((char) intValue))
                                .collect(Collectors.joining(""));
    return dencryptPasswordBased(cipherText,password,ssalt);
  }

  /**
   * Il progetto usa questo metodo in alternativa al metodo {@link assignment_6.security.crypto.SecurityUtils#generateIv()}.
   * Il problema è nell'uso interno di tale metodo di {@code SecureRandom()} che genera ad ogni
   * nuovo avvio del programma una chiave diversa, non permettendo la lettura dei dati
   * salvati nell'avvio precedente.
   * <p>
   * NON E' una soluzione, ma piuttosto uno stratagemma per un software giocattolo che "finge"
   * di utilizzare un metodo di offuscamento dei dati nel database (anche questo giocattolo).
   * <p>
   * 
   * If you try to decrypt PKCS5-padded data with the wrong key,
   * and then unpad it (which is done by the Cipher class automatically),
   * you most likely will get the BadPaddingException (with probably of slightly less than 255/256, around 99.61%),
   * because the padding has a special structure which is validated during unpad and very few keys would produce a valid padding.
   * <p>
   * So, if you get this exception, catch it and treat it as "wrong key".
   * <p>
   * This also can happen when you provide a wrong password,
   * which then is used to get the key from a keystore,
   * or which is converted into a key using a key generation function.
   * <p>
   * Of course, bad padding can also happen if your data is corrupted in transport.
   * <p>
   * That said, there are some security remarks about your scheme:
   * <p>
   * For password-based encryption, you should use a SecretKeyFactory and PBEKeySpec instead of using a SecureRandom with KeyGenerator. The reason is that the SecureRandom could be a different algorithm on each Java implementation, giving you a different key. The SecretKeyFactory does the key derivation in a defined manner (and a manner which is deemed secure, if you select the right algorithm).
   * 
   * @see https://stackoverflow.com/questions/8049872/given-final-block-not-properly-padded
   * @see https://www.delftstack.com/howto/java/java.io.streamcorruptedexception-invalid-stream-header/
   * @return
   */
  public static IvParameterSpec generateIv() {
    byte[] iv = new byte[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
    // byte[] iv = new byte[16];
    // new SecureRandom().nextBytes(iv);
    return new IvParameterSpec(iv);
}
}
