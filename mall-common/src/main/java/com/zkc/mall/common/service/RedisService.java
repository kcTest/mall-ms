package com.zkc.mall.common.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RedisService {
	
	void set(String key, Object value, long time);
	
	void set(String key, String value);
	
	Object get(String key);
	
	Boolean del(String key);
	
	Long del(List<String> keys);
	
	Boolean expire(String key, long time);
	
	Long getExpire(String key);
	
	Boolean hasKey(String key);
	
	Long incr(String key, long delta);
	
	Long decr(String key, long delta);
	
	Object hGet(String key, String hasKey);
	
	void hSet(String key, String hashKey, Object value);
	
	Boolean hSet(String key, String hasKey, Object value, long time);
	
	Map<Object, Object> hGetAll(String key);
	
	void hSetAll(String key, Map<Object, ?> map);
	
	Boolean hSetAll(String key, Map<Object, Object> map, long time);
	
	void hDelete(String key, Object... hashKey);
	
	Boolean hHasKey(String key, String hashKey);
	
	Long hIncr(String key, String hashKey, Long delta);
	
	Long hDecr(String key, String hashKey, Long delta);
	
	Set<Object> sMembers(String key);
	
	Long sAdd(String key, Object... values);
	
	Long sAdd(String key, long time, Object... values);
	
	Boolean sIsMember(String key, Object value);
	
	Long sSize(String key);
	
	Long sRemove(String key, Object... value);
	
	List<Object> lRange(String key, long start, long end);
	
	Long lSize(String key);
	
	Object lIndex(String key, long index);
	
	Long lPush(String key, Object value);
	
	Long lPush(String key, Object value, long time);
	
	Long lPushAll(String key, long time, Object... values);
	
	Long lRemove(String key, long count, Object value);
	
	
}
