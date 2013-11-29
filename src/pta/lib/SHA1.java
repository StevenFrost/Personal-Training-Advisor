package pta.lib;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA1 Hashing algorithm static class, used for password hashing
 * 
 * @author Steven Frost
 */
public class SHA1 {
	public static String hash(String input) throws NoSuchAlgorithmException {
		MessageDigest mDigest = MessageDigest.getInstance("SHA1");
		byte[] result = mDigest.digest(input.getBytes());
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < result.length; i++) {
			sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
		}

		return sb.toString();
	}
}