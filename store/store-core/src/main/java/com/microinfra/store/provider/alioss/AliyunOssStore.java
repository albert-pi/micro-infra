package com.microinfra.store.provider.alioss;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson2.JSON;
import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.comm.Protocol;
import com.aliyun.oss.model.OSSObject;
import com.microinfra.commons.lang.SysException;
import com.microinfra.store.StoreException;
import com.microinfra.store.StoreConfig.ProviderConfigs;
import com.microinfra.store.provider.StoreProvider;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 适配阿里云OSS存储服务
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */

@Component
@Slf4j
public class AliyunOssStore implements StoreProvider {

	@Resource
	private ProviderConfigs providerConfigs;

	private AliyunOssConfig config;

	private OSS ossClient;

	@PostConstruct
	public void init() {
		if (providerConfigs.getProviders() == null || providerConfigs.getProviders().size() == 0) {
			throw new SysException("Aliyun OSS configs not provided");
		}

		Map<String, String> configs = providerConfigs.getProviders().get(this.getName());
		if (configs == null) {
			throw new SysException("Aliyun OSS configs not provided");
		}

		config = JSON.parseObject(JSON.toJSONString(configs), AliyunOssConfig.class);
		ClientBuilderConfiguration configuration = new ClientBuilderConfiguration();
		configuration.setProtocol(Protocol.HTTPS);

		ossClient = new OSSClientBuilder().build(config.getEndpoint(), config.getAccessId(), config.getAccessSecret(), configuration);
		// 更多参数可以使用ClientConfiguration来配置（比如重试次数，默认为3次）
	}

	public String getDefaultBucket() {
		return config.getDefaultBucket();
	}

	public void upload(String bucket, String key, InputStream ins) throws StoreException {
		try {
			ossClient.putObject(bucket, key, ins);
		} catch (OSSException | ClientException e) {
			log.error(String.format("文件上传到阿里云OSS错误, bucket: %s, key: %s", bucket, key), e);

			throw new StoreException(String.format("文件上传错误, bucket: %s", bucket));
		}
	}

	public String getSignedUrl(String bucket, String key) throws StoreException {
		try {
			// 设置URL过期时间为1小时。
			Date expiration = new Date(new Date().getTime() + 3600 * 1000);
			// 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
			URL url = ossClient.generatePresignedUrl(bucket, key, expiration);

			return url.toString();
		} catch (ClientException e) {
			log.error(String.format("获取阿里云OSS文件访问地址错误, bucket: %s, key: %s", bucket, key), e);

			throw new StoreException("获取文件访问地址错误");
		}
	}

	public String getPublicUrl(String bucket, String key) throws StoreException {
		return String.format("%s/%s", config.getDomain(), key);
	}

	public InputStream getInputStream(String bucket, String key) {
		OSSObject ossObject = ossClient.getObject(bucket, key);

		return ossObject.getObjectContent();
	}

	public void deleteStore(String bucket, String key) throws StoreException {
		try {
			ossClient.deleteObject(bucket, key);
		} catch (ClientException e) {
			throw new StoreException(String.format("从OSS删除文件出错, bucket: %s, key: %s. ", bucket, key), e);
		}
	}

	public String getName() {
		return "aliyunoss";
	}

	public String getTitle() {
		return "阿里云OSS";
	}

	@Override
	public boolean isDefault() {
		return config.isDefaultProvider();
	}

//	public static void main(String[] args) {
//		try {
//			ClientBuilderConfiguration configuration = new ClientBuilderConfiguration();
//			configuration.setProtocol(Protocol.HTTPS);
//
//			OSS ossClient = new OSSClientBuilder().build("oss-cn-shenzhen.aliyuncs.com", "",
//					"", configuration);
//
//			long expireTime = 300L;
//			long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
//			Date expiration = new Date(expireEndTime);
//
//			String dir = DateUtil.formatDate(DateUtil.getCurrentDateTime(), DateUtil.DEFAULT_DATE_FORMAT) + "/";
//
//			PolicyConditions policyConds = new PolicyConditions();
//			policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
//			policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);
//
//			String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
//			byte[] binaryData = postPolicy.getBytes("utf-8");
//			String encodedPolicy = BinaryUtil.toBase64String(binaryData);
//			String postSignature = ossClient.calculatePostSignature(postPolicy);
//
//			StringBuilder sbAttachedParams = new StringBuilder();
//
//			sbAttachedParams.append(
//					"application=5252793072304996473&item=height-weight&filename=${object}&size=${size}&mimeType=${mimeType}&height=${imageInfo.height}&width=${imageInfo.width}");
////			sbAttachedParams.append(
////					"filename=${object}&size=${size}&mimeType=${mimeType}&height=${imageInfo.height}&width=${imageInfo.width}");
//
//			JSONObject jasonCallback = new JSONObject();
//			jasonCallback.put("callbackUrl", "https://api.i-exam.com.cn/oss/notify");
//			jasonCallback.put("callbackBody", sbAttachedParams.toString());
//			jasonCallback.put("callbackBodyType", "application/x-www-form-urlencoded");
//			String base64CallbackBody = BinaryUtil.toBase64String(jasonCallback.toString().getBytes());
//
//			Map<String, String> policyMap = new LinkedHashMap<String, String>();
//			policyMap.put("accessid", "LTAI4G6Z52Vez49qcEnU6vs2");
//			policyMap.put("policy", encodedPolicy);
//			policyMap.put("signature", postSignature);
//			policyMap.put("dir", dir);
//			policyMap.put("host", "https://iexam.oss-cn-shenzhen.aliyuncs.com");
//			policyMap.put("expire", String.valueOf(expireEndTime / 1000));
//			policyMap.put("callback", base64CallbackBody);
//
//			System.out.println("accessid==================LTAI4G6Z52Vez49qcEnU6vs2");
//			System.out.println("policy====================" + encodedPolicy);
//			System.out.println("signature=================" + postSignature);
//			System.out.println("dir=======================" + dir);
//			System.out.println("host======================https://iexam.oss-cn-shenzhen.aliyuncs.com");
//			System.out.println("expire====================" + String.valueOf(expireEndTime / 1000));
//			System.out.println("callback==================" + base64CallbackBody);
//
//		} catch (Exception e) {
//			log.error("获取文件上传Policy出错。bucket：", e);
//
//		}
//	}
}