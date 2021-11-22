import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 暗号化/復号化処理サンプル
 * 
 * @author zuckersystems
 */
public class Test {

  // アルゴリズム/ブロックモード/パディング方式
  private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
  // 暗号化＆復号化で使用する鍵
  private static final String ENCRYPT_KEY = "TestEncryptKey01";
  // 初期ベクトル
  private static final String INIT_VECTOR = "TestInitVector01";

  private final IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes());
  private final SecretKeySpec key = new SecretKeySpec(ENCRYPT_KEY.getBytes(), "AES");

  public static void main(String[] args) throws Exception {
    Test test = new Test();
    String ret = test.encryptToken(args[0]);
    System.out.println("暗号化後：" + ret);
    String ret2 = test.decryptToken(ret);
    System.out.println("復号化後：" + ret2);
  }

  /** コンストラクタ */
  public Test() {

  }

  /**
   * 暗号化処理
   */
  private String encryptToken(String token) throws Exception {

    Cipher encrypter = Cipher.getInstance(ALGORITHM);
    encrypter.init(Cipher.ENCRYPT_MODE, this.key, this.iv);
    byte[] byteToken = encrypter.doFinal(token.getBytes());

    return new String(Base64.getEncoder().encode(byteToken));
  }

  /**
   * 復号化処理
   */
  private String decryptToken(String encryptedToken) throws Exception {

    Cipher decrypter = Cipher.getInstance(ALGORITHM);
    decrypter.init(Cipher.DECRYPT_MODE, this.key, this.iv);
    byte[] byteToken = Base64.getDecoder().decode(encryptedToken);

    return new String(decrypter.doFinal(byteToken));
  }

}