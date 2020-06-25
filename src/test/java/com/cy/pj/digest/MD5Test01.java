package com.cy.pj.digest;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

@SpringBootTest
public class MD5Test01 {
      /**
       *MD5算法：消息摘要(Message Digest)加密算法，其特点：
       *1)不可逆(只能加密不能解密-只能暴力破解)
       *2)相同内容加密结果也相同。
       */
	  @Test
	  void testMD501() {
		  String s1="123456";
		  String pwd=DigestUtils.md5DigestAsHex(s1.getBytes());
		  System.out.println(pwd);
		  pwd=DigestUtils.md5DigestAsHex(pwd.getBytes());
		  System.out.println(pwd);
	  }
	  
	  @Test
	  void testMD502() {//盐值加密
		  String s1="123456";
		  String salt=UUID.randomUUID().toString();
		  System.out.println("salt="+salt);
		  String pwd1=DigestUtils.md5DigestAsHex((s1+salt).getBytes());
		  System.out.println("pwd1="+pwd1);
		  String pwd2=DigestUtils.md5DigestAsHex((s1+salt).getBytes());
		  System.out.println("pwd2="+pwd2);
	  }
	  @Test
	  void testBase64() {
		 String s1="123456";
		 Encoder encoder=Base64.getEncoder();//获取加密对象
		 String s2=new String(encoder.encode(s1.getBytes()));
		 System.out.println("s2="+s2);
		 Decoder decoder=Base64.getDecoder();//获取解密对象
		 String s3=new String(decoder.decode(s2));
		 System.out.println("s3="+s3);
	  }
	  
}
