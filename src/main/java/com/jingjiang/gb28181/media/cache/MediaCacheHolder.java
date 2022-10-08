package com.jingjiang.gb28181.media.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

public class MediaCacheHolder {

    /**
     * 过期缓存，10秒内会过期
     */
    private static final Cache<String, String> expiredCache = CacheBuilder.newBuilder().expireAfterAccess(10, TimeUnit.SECONDS).build();
    /**
     * 持久缓存
     */
    private static final Cache<String, String> localCache = CacheBuilder.newBuilder().build();

    public static Optional<Map.Entry<String, String>> get(String value) {
        Set<Map.Entry<String, String>> optional = localCache.asMap().entrySet();
        Optional<Map.Entry<String, String>> entryOptional = optional.stream().filter(kvEntry -> Objects.equals(kvEntry.getValue(), value)).findAny();
        return entryOptional;
    }

    public static ConcurrentMap<String, String> getAll() {
        return localCache.asMap();
    }

    public static void put(String key, String value) {
        expiredCache.put(key, value);
    }

    public static void remove(String key) {
        localCache.invalidate(key);
    }


    public static Boolean isExist(String value) {
        boolean expiredContainsValue = expiredCache.asMap().containsValue(value);
        boolean localContainsValue = localCache.asMap().containsValue(value);
        return expiredContainsValue || localContainsValue;
    }

    public static void persistentKey(String key) {
        // 当临时缓存中存在此 key 时，缓存进持久缓存
        if (expiredCache.asMap().containsKey(key)) {
            localCache.put(key, Objects.requireNonNull(expiredCache.getIfPresent(key)));
        }
    }
}
