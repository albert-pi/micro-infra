package com.microinfra.passport.security;

import static com.google.code.kaptcha.Constants.KAPTCHA_BACKGROUND_CLR_FROM;
import static com.google.code.kaptcha.Constants.KAPTCHA_BACKGROUND_CLR_TO;
import static com.google.code.kaptcha.Constants.KAPTCHA_BACKGROUND_IMPL;
import static com.google.code.kaptcha.Constants.KAPTCHA_BORDER;
import static com.google.code.kaptcha.Constants.KAPTCHA_BORDER_COLOR;
import static com.google.code.kaptcha.Constants.KAPTCHA_IMAGE_HEIGHT;
import static com.google.code.kaptcha.Constants.KAPTCHA_IMAGE_WIDTH;
import static com.google.code.kaptcha.Constants.KAPTCHA_NOISE_COLOR;
import static com.google.code.kaptcha.Constants.KAPTCHA_NOISE_IMPL;
import static com.google.code.kaptcha.Constants.KAPTCHA_OBSCURIFICATOR_IMPL;
import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_CONFIG_KEY;
import static com.google.code.kaptcha.Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH;
import static com.google.code.kaptcha.Constants.KAPTCHA_TEXTPRODUCER_CHAR_SPACE;
import static com.google.code.kaptcha.Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR;
import static com.google.code.kaptcha.Constants.KAPTCHA_TEXTPRODUCER_FONT_NAMES;
import static com.google.code.kaptcha.Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE;
import static com.google.code.kaptcha.Constants.KAPTCHA_TEXTPRODUCER_IMPL;

import java.util.Properties;

import org.springframework.stereotype.Component;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

@Component
public class KaptchaProducer extends DefaultKaptcha implements Producer {

	public KaptchaProducer() {
		Properties properties = new Properties();
		// 是否有边框 默认为true 我们可以自己设置yes，no
		properties.setProperty(KAPTCHA_BORDER, "yes");
		// 边框颜色 默认为Color.BLACK
		properties.setProperty(KAPTCHA_BORDER_COLOR, "217,217,217");
		// 验证码文本字符颜色 默认为Color.BLACK
		properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_COLOR, "black");
		// 验证码图片宽度 默认为200
		properties.setProperty(KAPTCHA_IMAGE_WIDTH, "160");
		// 验证码图片高度 默认为50
		properties.setProperty(KAPTCHA_IMAGE_HEIGHT, "60");
		// 验证码文本字符大小 默认为40
		properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_SIZE, "35");
		// KAPTCHA_SESSION_KEY
		properties.setProperty(KAPTCHA_SESSION_CONFIG_KEY, "kaptchaCodeMath");
		// 验证码文本生成器
		properties.setProperty(KAPTCHA_TEXTPRODUCER_IMPL, "com.zhongzhi.passport.security.KaptchaTextCreator");
		// 验证码文本字符间距 默认为2
		properties.setProperty(KAPTCHA_TEXTPRODUCER_CHAR_SPACE, "3");
		// 验证码文本字符长度 默认为5
		properties.setProperty(KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, "6");
		// 验证码文本字体样式 默认为new Font("Arial", 1, fontSize), new Font("Courier", 1, fontSize)
		properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_NAMES, "宋体,楷体,微软雅黑");
		// 验证码噪点颜色 默认为Color.BLACK
		properties.setProperty(KAPTCHA_NOISE_COLOR, "246,247,249");
		// 背景渐变色
		properties.setProperty(KAPTCHA_BACKGROUND_IMPL, "com.google.code.kaptcha.impl.DefaultBackground");
		properties.setProperty(KAPTCHA_BACKGROUND_CLR_FROM, "246,247,249");
		properties.setProperty(KAPTCHA_BACKGROUND_CLR_TO, "246,247,249");
		// 干扰实现类
		properties.setProperty(KAPTCHA_NOISE_IMPL, "com.google.code.kaptcha.impl.NoNoise");
		// 图片样式 水纹com.google.code.kaptcha.impl.WaterRipple 鱼眼com.google.code.kaptcha.impl.FishEyeGimpy 阴影com.google.code.kaptcha.impl.ShadowGimpy
		properties.setProperty(KAPTCHA_OBSCURIFICATOR_IMPL, "com.google.code.kaptcha.impl.ShadowGimpy");

		Config config = new Config(properties);
		setConfig(config);
	}

}
