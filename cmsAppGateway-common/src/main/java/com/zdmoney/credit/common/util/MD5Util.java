package com.zdmoney.credit.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;

public class MD5Util {

	private MD5Util() {

	}

	/**
	 * Returns a MessageDigest for the given <code>algorithm</code>.
	 * 
	 * @param algorithm
	 *            The MessageDigest algorithm name.
	 * @return An MD5 digest instance.
	 * @throws RuntimeException
	 *             when a {@link java.security.NoSuchAlgorithmException} is
	 *             caught
	 */
	static MessageDigest getDigest() {
		try {
			return MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Calculates the MD5 digest and returns the value as a 16 element
	 * <code>byte[]</code>.
	 * 
	 * @param data
	 *            Data to digest
	 * @return MD5 digest
	 */
	public static byte[] md5(byte[] data) {
		return getDigest().digest(data);
	}

	/**
	 * Calculates the MD5 digest and returns the value as a 16 element
	 * <code>byte[]</code>.
	 * 
	 * @param data
	 *            Data to digest
	 * @return MD5 digest
	 */
	public static byte[] md5(String data) {
		return md5(data.getBytes());
	}

	/**
	 * Calculates the MD5 digest and returns the value as a 32 character hex
	 * string.
	 * 
	 * @param data
	 *            Data to digest
	 * @return MD5 digest as a hex string
	 */
	public static String md5Hex(byte[] data) {
		return HexUtil.toHexString(md5(data));
	}

	/**
	 * Calculates the MD5 digest and returns the value as a 32 character hex
	 * string.
	 * 
	 * @param data
	 *            Data to digest
	 * @return MD5 digest as a hex string
	 * @throws UnsupportedEncodingException 
	 */
	public static String md5Hex(String data) throws UnsupportedEncodingException {
		return HexUtil.toHexString(md5(data.getBytes("utf-8")));
	}

	public static boolean validateSign(List<String> parameteres, String sign, String privateKey) throws UnsupportedEncodingException {
		boolean result = false;
		if (parameteres.isEmpty() || sign == null)
			return false;
		Iterator<String> i = parameteres.iterator();
		StringBuffer sb = new StringBuffer();
		while (i.hasNext()) {
			sb.append(i.next());
		}
		sb.append(privateKey);
		String signed = MD5Util.md5Hex(sb.toString());
		result = sign.equalsIgnoreCase(signed);
		return result;
	}
}
