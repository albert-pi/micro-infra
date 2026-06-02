package com.microinfra.passport.service;

import static com.microinfra.commons.ignite.IgniteConfigurer.TRANSIENT_REGION;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.expiry.ExpiryPolicy;
import javax.imageio.ImageIO;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.CacheRebalanceMode;
import org.apache.ignite.cache.CacheWriteSynchronizationMode;
import org.apache.ignite.cache.PartitionLossPolicy;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.util.FastByteArrayOutputStream;

import com.google.common.base.CaseFormat;
import com.microinfra.commons.lang.SysException;
import com.microinfra.passport.PassportConfig.AppConfig;
import com.microinfra.passport.PassportConfig.KaptchaConfig;
import com.microinfra.passport.model.CaptchaInfo;
import com.microinfra.passport.security.KaptchaProducer;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.IdUtil;

/**
 * <p>
 * 图形验证码管理
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
@Service
public class CaptchaService {

	@Resource
	private AppConfig appConfig;

	@Resource
	private Ignite ignite;

	@Resource
	private KaptchaConfig kaptchaConfig;

	@Resource
	private KaptchaProducer kaptchaProducer;

	private IgniteCache<String, String> captchaCache;

	@PostConstruct
	public void init() {
		CacheConfiguration<String, String> captchaCacheCfg = new CacheConfiguration<String, String>() //
				.setSqlSchema(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, appConfig.getName())) //
				.setName("Captcha") //
				.setCacheMode(CacheMode.REPLICATED) //
				.setDataRegionName(TRANSIENT_REGION) //
				.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL) //
				.setWriteSynchronizationMode(CacheWriteSynchronizationMode.FULL_SYNC) //
				.setBackups(1) //
				.setEagerTtl(true) //
				.setPartitionLossPolicy(PartitionLossPolicy.READ_ONLY_SAFE) //
				.setRebalanceMode(CacheRebalanceMode.ASYNC) //
				.setIndexedTypes(new Class[] { String.class, String.class });
		this.captchaCache = ignite.getOrCreateCache(captchaCacheCfg);
	}

	public CaptchaInfo generateCaptcha() {
		String captchaText = kaptchaProducer.createText();
		String captchaStr = captchaText.substring(0, captchaText.lastIndexOf("@"));
		String captchaCode = captchaText.substring(captchaText.lastIndexOf("@") + 1);
		BufferedImage image = kaptchaProducer.createImage(captchaStr);

		try {
			String captchaKey = IdUtil.simpleUUID();
			FastByteArrayOutputStream os = new FastByteArrayOutputStream();
			ImageIO.write(image, "jpg", os);

			ExpiryPolicy kaptchaExpiryPolicy = new CreatedExpiryPolicy(new Duration(SECONDS, kaptchaConfig.getValidity()));
			captchaCache.withExpiryPolicy(kaptchaExpiryPolicy).put(captchaKey, captchaCode);

			return new CaptchaInfo(captchaKey, "data:image/jpg;base64," + Base64.encode(os.toByteArray()));
		} catch (IOException e) {
			throw new SysException("生成验证码错误", e);
		}
	}

	public String pollCaptcha(String uid) {
		return captchaCache.getAndRemove(uid);
	}

}
