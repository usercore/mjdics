package com.magic.promotion.util;


public class ByteUtil {
	private static final char[] HEX_CHARS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
			'e', 'f', };

	public static String intToIp(int i) {
		return ((i >> 24) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + (i & 0xFF);
	}

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
		char buf[] = new char[data.length * 2];
		for (int i = 0, x = 0; i < data.length; i++) {
			buf[x++] = HEX_CHARS[(data[i] >>> 4) & 0xf];
			buf[x++] = HEX_CHARS[data[i] & 0xf];
		}
		return new String(buf);
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

	public static byte[] toByte(String str) throws Exception {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < str.length(); i += 2) {
			String tmp = str.substring(i, i + 2);
			buf.append((char) Integer.parseInt(tmp, 16));
		}
		String val = buf.toString();
		return val.getBytes("ISO-8859-1");
	}

	public static byte[] toByte(int vint) {
		byte[] array = new byte[4];
		array[0] = (byte) (vint >>> 24);
		array[1] = (byte) (vint >>> 16);
		array[2] = (byte) (vint >>> 8);
		array[3] = (byte) (vint >>> 0);
		return array;

	}

	public static byte[] transToUnicode(String str) {
		char c;
		int intAsc;
		byte[] temp = new byte[str.length() * 2];
		byte[] result = null;
		int index = 0;
		for (int i = 0; i < str.length(); i++) {
			c = str.charAt(i);
			intAsc = (int) c;
			if (intAsc > 128) {
				temp[index] = (byte) ((str.charAt(i) >> 8) & 0xFF);
				index++;
				temp[index] = (byte) (str.charAt(i) & 0xFF);
				index++;
			} else {
				temp[index] = (byte) (str.charAt(i) & 0xFF);
				index++;
			}
		}
		result = new byte[index];
		for (int i = 0; i < index; i++) {
			result[i] = temp[i];
		}
		return result;
	}

	/**
	 * 将二进制转换成16进制
	 * 
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 将16进制转换为二进制
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}
}
