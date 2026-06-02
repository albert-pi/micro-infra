package com.microinfra.store.provider;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.microinfra.commons.bean.DataOption;
import com.microinfra.framework.ApplicationContextHelper;

/**
 * <p>
 * 存储服务提供商辅助类
 * </p>
 *
 * @author Albert
 * @since 1.0.0
 */

public class StoreProviderHelper {

	private static final Map<String, StoreProvider> PROVIDER_MAP = new HashMap<>();

	static {
		Map<String, StoreProvider> storeProviders = ApplicationContextHelper.getBeans(StoreProvider.class);
		storeProviders.entrySet().forEach(entry -> {
			PROVIDER_MAP.put(entry.getValue().getName(), entry.getValue());
		});
	}

	/**
	 * 根据名称获取指定的存储服务提供商
	 * 
	 * @param providerName
	 * @return
	 */
	public static StoreProvider getProvider(String name) {
		return PROVIDER_MAP.get(name);
	}

	/**
	 * 获得所有支持的存储服务提供商
	 * 
	 * @return
	 */
	public static Collection<StoreProvider> getProviders() {
		return PROVIDER_MAP.values();
	}

	/**
	 * 获得所有的存储服务提供商列表选项
	 * 
	 * @return
	 */
	public static List<DataOption> getProviderOptions() {
		return PROVIDER_MAP.values().stream().map(provider -> new DataOption(provider.getName(), provider.getTitle())).collect(Collectors.toList());
	}

	/**
	 * 获取默认的存储服务提供商
	 * 
	 * @return
	 */
	public static StoreProvider getDefaultProvider() {
		return PROVIDER_MAP.values().stream().filter(provider -> provider.isDefault()).findFirst().get();
	}

}