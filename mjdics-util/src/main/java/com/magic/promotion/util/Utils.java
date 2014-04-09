package com.magic.promotion.util;




public class Utils {
	private static String digits = "0123456789abcdef";

	/**
	 * Return length many bytes of the passed in byte array as a hex string.
	 * 
	 * @param data
	 *            the bytes to be converted.
	 * @param length
	 *            the number of bytes in the data block to be converted.
	 * @return a hex representation of length bytes of data.
	 */
	public static String toHex(byte[] data, int length) {
		StringBuffer buf = new StringBuffer();

		for (int i = 0; i != length; i++) {
			int v = data[i] & 0xff;

			buf.append(digits.charAt(v >> 4));
			buf.append(digits.charAt(v & 0xf));
		}

		return buf.toString().toUpperCase();
	}

	/**
	 * Return the passed in byte array as a hex string.
	 * 
	 * @param data
	 *            the bytes to be converted.
	 * @return a hex representation of data.
	 */
	public static String toHex(byte[] data) {
		return toHex(data, data.length);
	}

	public static byte[] toByte(String str)throws Exception {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < str.length(); i += 2) {
			String tmp = str.substring(i, i + 2);
			buf.append((char) Integer.parseInt(tmp, 16));
		}
		String val= buf.toString();
		return val.getBytes("ISO-8859-1");
	}

	

}
