package com.microinfra.store.provider;

import java.io.InputStream;

import com.microinfra.store.StoreException;

/**
 * <p>
 * 第三方存储服务适配接口
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */
public interface StoreProvider {

	/**
	 * 获取默认的bucket
	 * 
	 * @return
	 */
	public String getDefaultBucket();

	/**
	 * 上传存储数据
	 * 
	 * @param bucket
	 * @param key    存储key
	 * @param ins
	 * @throws StoreException
	 */
	public void upload(String bucket, String key, InputStream ins) throws StoreException;

	/**
	 * 获取签名的访问URL（存储在私有存储空间内，签名URL为临时访问地址)
	 * 
	 * @param bucket
	 * @param key    存储key
	 * @return
	 * @throws StoreException
	 */
	public String getSignedUrl(String bucket, String key) throws StoreException;

	/**
	 * 获取公的访问URL
	 * 
	 * @param bucket
	 * @param key    存储key
	 * @return
	 * @throws StoreException
	 */
	public String getPublicUrl(String bucket, String key) throws StoreException;

	/**
	 * 以输入流形式返回文件内容（服务器端使用）
	 * 
	 * @param bucket
	 * @param key    存储key
	 * @return
	 * @throws StoreException
	 */
	public InputStream getInputStream(String bucket, String key) throws StoreException;

	/**
	 * 删除存储
	 * 
	 * @param bucket
	 * @param key    存储key
	 * @return
	 * @throws StoreException
	 */
	public void deleteStore(String bucket, String key) throws StoreException;

	/**
	 * 获取存储服务提供商代码名称
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * 获取存储服务提供商显示名称
	 * 
	 * @return
	 */
	public String getTitle();

	/**
	 * 是否为默认存储服务提供商
	 * 
	 * @return
	 */
	public boolean isDefault();

}