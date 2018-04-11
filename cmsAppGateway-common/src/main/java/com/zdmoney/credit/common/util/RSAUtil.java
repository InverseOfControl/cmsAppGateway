package com.zdmoney.credit.common.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.stereotype.Component;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.zdmoney.credit.config.SystemProperties;

/**
 * RSA 操作工具类
 * 
 * @author 00237071
 *
 */
@Component
public class RSAUtil {

	// 指定加密算法为RSA
	private static final String ALGORITHM = "RSA";
	// 密钥长度，用来初始化
	private static final int KEYSIZE = 1024;

	/**
	 * 生成密钥公钥对
	 * 
	 */
	// public static void generateKeyPair() {
	//
	// ObjectOutputStream oos1 = null, oos2 = null;
	//
	// try {
	// KeyParamBean keyParamBean = (KeyParamBean)
	// SpringContextHelper.getBean("keyParamBean");
	//
	// // 为RSA算法创建一个KeyPairGenerator对象
	// KeyPairGenerator keyPairGenerator =
	// KeyPairGenerator.getInstance(ALGORITHM);
	// // RSA算法要求有一个可信任的随机数源
	// SecureRandom secrand = SecureRandom.getInstance("SHA1PRNG", "SUN");
	// secrand.setSeed(keyParamBean.getKeyGeneratorRandom().getBytes()); //
	// 初始化随机产生器
	//
	// // 利用上面的随机数据源初始化这个KeyPairGenerator对象
	// keyPairGenerator.initialize(KEYSIZE, secrand);
	//
	// // 生成密匙对
	// KeyPair keyPair = keyPairGenerator.generateKeyPair();
	//
	// // 得到公钥
	// Key publicKey = keyPair.getPublic();
	//
	// // 得到私钥
	// Key privateKey = keyPair.getPrivate();
	//
	// // 用对象流将生成的密钥写入文件
	// oos1 = new ObjectOutputStream(new
	// FileOutputStream(keyParamBean.getKeyPath()
	// + keyParamBean.getPublicKeyFileName()));
	// oos2 = new ObjectOutputStream(new
	// FileOutputStream(keyParamBean.getKeyPath()
	// + keyParamBean.getPrivateKeyFileName()));
	// oos1.writeObject(publicKey);
	// oos2.writeObject(privateKey);
	// } catch (Exception e) {
	// log.error("生成公钥密钥对失败", e);
	// } finally {
	// try {
	// if (oos1 != null) {
	// oos1.close();
	// oos1 = null;
	// }
	// if (oos2 != null) {
	// oos2.close();
	// oos2 = null;
	// }
	// } catch (IOException e) {
	// log.error("生成公钥密钥【关闭输出流失败】", e);
	// }
	// }
	// }

	/**
	 * 加密方法
	 * 
	 * @param source
	 *            源数据
	 * @param publicKeyPath
	 *            公钥文件路径
	 * @return
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws InvalidKeySpecException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	public static String rsaEncrypt(String source, String publicKeyPath) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException,
			InvalidKeySpecException, IOException, ClassNotFoundException {
		ObjectInputStream ois = null;
		try {
			SystemProperties systemProperties = (SystemProperties) SpringContextUtil.getBean("systemProperties");
			String publicFile = systemProperties.getRsaPublicFile();
			// 将文件中的公钥对象读出
			ois = new ObjectInputStream(new FileInputStream(publicFile));
			Key publicKey = (Key) ois.readObject();

			// String pubkeyvalue =
			// "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALKSFrzUMM5SsO0mVHfosvAmmen0JpDYxNX7uz1gvbXZ"
			// + "rlHhaJxLFepvYrVVaHytXJq/wr/+61IbMbYdrrRfe3sCAwEAAQ==";//
			// 这是GenerateKeyPair输出的公钥编码
			//
			// X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(new
			// BASE64Decoder().decodeBuffer(pubkeyvalue));
			// KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			// PublicKey publicKey = keyFactory.generatePublic(bobPubKeySpec);

			// 得到Cipher对象来实现对源数据的RSA加密
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] b = source.getBytes();
			// 执行加密操作
			byte[] b1 = cipher.doFinal(b);
			BASE64Encoder encoder = new BASE64Encoder();
			return encoder.encodeBuffer(b1);
		} finally {
			try {
				if (ois != null) {
					ois.close();
					ois = null;
				}
			} catch (IOException e) {
				// log.error("RSA 加密失败【关闭输入流失败】", e);
			}
		}
	}

	/**
	 * 解密算法
	 * 
	 * @param source
	 *            密文
	 * @param privateKeyPath
	 *            私钥文件路径
	 * @return
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws IOException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws InvalidKeySpecException
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	public static String rsaDecrypt(String source, String privateKeyPath) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException,
			InvalidKeySpecException, ClassNotFoundException {
		Key privateKey;
		ObjectInputStream ois = null;
		try {
			SystemProperties systemProperties = (SystemProperties) SpringContextUtil.getBean("systemProperties");
			String privateFile = systemProperties.getRsaPrivateFile();
			// 将文件中的私钥对象读出
			ois = new ObjectInputStream(new FileInputStream(privateFile));
			privateKey = (Key) ois.readObject();

			// String prikeyvalue =
			// "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAspIWvNQwzlKw7SZUd+iy8CaZ6fQm"
			// +
			// "kNjE1fu7PWC9tdmuUeFonEsV6m9itVVofK1cmr/Cv/7rUhsxth2utF97ewIDAQABAkEAi9X8+kgM"
			// +
			// "JDX7GOrwMKpIHemMk/m+jaVClvM3UDxGs3UAbWtmeew7e8UyuYay56DiKxQksRsBRRzcc7Ol96AS"
			// +
			// "uQIhAOmzLxl3fA2qfd+NYJ7tKY5qvnhwbAtLOxKUEDYfPeS/AiEAw5w1UKiZRbHOUCNVwtHk4IT2"
			// +
			// "c7jC/ApBv4GWGtKgLEUCICKq/B+xA+wbEJUrKdwpnnKMDzlapAyA2oCFCmqc89eNAiB0YWwXITiQ"
			// +
			// "DsoBRASydM8IBhPbpgHckv2rQx5btxU1QQIgCdT1oLaROiRQ6VzTQ52LrmzQaGmW5xOx8r1tJZe/"
			// + "0Uk=";// 这是GenerateKeyPair输出的私钥编码
			//
			// PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(new
			// BASE64Decoder().decodeBuffer(prikeyvalue));
			// KeyFactory keyf = KeyFactory.getInstance("RSA");
			// privateKey = keyf.generatePrivate(priPKCS8);

			// 得到Cipher对象对已用公钥加密的数据进行RSA解密
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] b1 = decoder.decodeBuffer(source);

			// 执行解密操作
			byte[] b = cipher.doFinal(b1);
			return new String(b);
		} finally {
			try {
				if (ois != null) {
					ois.close();
					ois = null;
				}
			} catch (IOException e) {
				// log.error("RSA 解密【关闭输入流失败】", e);
			}
		}
	}

	public PublicKey getPubKey(String pubKey) {
		PublicKey publicKey = null;
		try {

			java.security.spec.X509EncodedKeySpec bobPubKeySpec = new java.security.spec.X509EncodedKeySpec(
					new BASE64Decoder().decodeBuffer(pubKey));
			// RSA对称加密算法
			java.security.KeyFactory keyFactory;
			keyFactory = java.security.KeyFactory.getInstance("RSA");
			// 取公钥匙对象
			publicKey = keyFactory.generatePublic(bobPubKeySpec);
		} catch (Exception e) {
			// log.error("获得公钥失败", e);
		}
		return publicKey;
	}

	/**
	 * 字符串生成私钥对象 实例化私钥
	 * 
	 * @return
	 */
	public PrivateKey getPrivateKey(String priKey) {
		PrivateKey privateKey = null;
		PKCS8EncodedKeySpec priPKCS8;
		try {
			priPKCS8 = new PKCS8EncodedKeySpec(new BASE64Decoder().decodeBuffer(priKey));
			KeyFactory keyf = KeyFactory.getInstance("RSA");
			privateKey = keyf.generatePrivate(priPKCS8);
		} catch (Exception e) {
			// log.error("获得私钥失败", e);
		}
		return privateKey;
	}

	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException,
			IOException, ClassNotFoundException {

		// generateKeyPair();

		String source = System.currentTimeMillis() + "";// 要加密的字符串
		System.out.println("准备用公钥加密的字符串为：" + source);

		String cryptograph = rsaEncrypt(source, "");// 生成的密文
		System.out.print("用公钥加密后的结果为:" + cryptograph);
		System.out.println();

		String target = rsaDecrypt(cryptograph, "");// 解密密文
		System.out.println("用私钥解密后的字符串为：" + target);
		System.out.println();

		// // 用于生成 xxtea key 的常量
		// String key4key = "KWOJT23434LT3JT";
		// // 该 key 用于 加密 业务参数
		// String key = "戴元成";
		// System.out.println("key = " + key);
		//
		// String paramSource =
		// "{\"reqParam\":{\"mobile\":\"13482772194\",\"cvType\":\"1\"},\"reqHeadParam\":{},\"sn\":\"戴元成\"}";
		// String paramAfterXXtea = XXTeaUtil.xxteaEncrypt(paramSource, key);
		// System.out.println("paramAfterXXtea = " + paramAfterXXtea);
		//
		// String paramKey = XXTeaUtil.xxteaEncrypt(key, key4key);
		// String rsaKey = RSAUtil.rsaEncrypt(paramKey);
		// System.out.println("rsaKey = " + rsaKey);
		//
		// String afterKey = XXTeaUtil.xxteaDecrypt(RSAUtil.rsaDecrypt(rsaKey),
		// key4key);
		// System.out.println("afterKey = " + afterKey);
		// String functionId = "400001";
		//
		// String md5Source = functionId
		// + "|"
		// +
		// "1a289f04f20320bd5360a74d153c67087f834af7bd05b11773e686fbfaad9f1781f65e7503320dbb9d4496eaa38a7998ec67aa5c55bc1b8f209da6f3c68d8c90e2c73b8dc7d2f58e65501838d62392ff"
		// + "|"
		// +
		// "k5A0cFiuEgIwEldteRildd14VNmlkl01r5GW3WE/CY7Zp4eVlpG+h5ItpJkr2s4K+2KFHZNZjysCAJfRtBI15fp3TghJlsY2WSrdCnkGvqMk9UmsZM9ocwMUjJvKFqnQOcHEgMYZZ1rVDl1/LJaTqmY3R+7Br5tIEF/BelXgZiU="
		// + "|" + "damuzhiapp951753";
		// // String sign = MD5.MD5Encode(md5Source);
		// // System.out.println("sign = " + sign);
	}
}
