package database;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.security.Security;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * <p> EncryptionHelper. </p>
 * 
 * <p> Description: Encryption and decryption used for article bodies.</p>
 * 
 * @author Eyan Martucci
 * 
 * @version 1.00		TODO
 *  
 */

public class EncryptionHelper {
	
	byte[] keyBytes = new byte[] {
            0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
            0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f,
            0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17 };
	
	private static String BOUNCY_CASTLE_PROVIDER_IDENTIFIER = "BC";	
	private Cipher cipher;
	private SecretKey key = new SecretKeySpec(keyBytes, "AES");
	private static int IV_SIZE = 16;
	
	public EncryptionHelper() throws Exception {
		Security.addProvider(new BouncyCastleProvider());
		cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", BOUNCY_CASTLE_PROVIDER_IDENTIFIER);		
	}
	
	public byte[] encrypt(byte[] plainText, byte[] initializationVector) throws Exception {
		cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(initializationVector));
		return cipher.doFinal(plainText);
	}
	
	public byte[] decrypt(byte[] cipherText, byte[] initializationVector) throws Exception {
		cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(initializationVector));
		return cipher.doFinal(cipherText);
	}
	
	
	
	
	
	public static char[] toCharArray(byte[] bytes) {		
        CharBuffer charBuffer = Charset.defaultCharset().decode(ByteBuffer.wrap(bytes));
        return Arrays.copyOf(charBuffer.array(), charBuffer.limit());
	}
	
	static byte[] toByteArray(char[] chars) {		
        ByteBuffer byteBuffer = Charset.defaultCharset().encode(CharBuffer.wrap(chars));
        return Arrays.copyOf(byteBuffer.array(), byteBuffer.limit());
	}
		
	public static byte[] getInitializationVector(char[] text) {
		char iv[] = new char[IV_SIZE];
		
		int textPointer = 0;
		int ivPointer = 0;
		while(ivPointer < IV_SIZE) {
			iv[ivPointer++] = text[textPointer++ % text.length];
		}
		
		return toByteArray(iv);
	}
	
	public static void printCharArray(char[] chars) {
		for(char c : chars) {
			System.out.print(c);
		}
	}
}
