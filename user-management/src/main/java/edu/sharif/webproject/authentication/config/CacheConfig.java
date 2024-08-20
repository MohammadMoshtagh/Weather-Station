package edu.sharif.webproject.authentication.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    private final String countriesCacheName = "CountriesCache";
    private final String countryNamesCacheName = "CountryNameCache";
    private final String weatherCacheName = "WeatherCache";

    @Bean
    public Caffeine<Object, Object> caffeineConfig() {
        return Caffeine.newBuilder().expireAfterAccess(60, TimeUnit.MINUTES);
    }

    @Bean
    public CacheManager cacheManager(Caffeine<Object, Object> caffeine) {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(caffeine);
        cacheManager.setCacheNames(Arrays.asList(countriesCacheName, countryNamesCacheName, weatherCacheName));
        return cacheManager;
    }

    @Bean
    public CaffeineCache CountriesCache(Caffeine<Object, Object> caffeine) {
        return new CaffeineCache(countriesCacheName, caffeine.build());
    }

    @Bean
    public CaffeineCache CountryNameCache(Caffeine<Object, Object> caffeine) {
        return new CaffeineCache(countryNamesCacheName, caffeine.build());
    }

    @Bean
    public CaffeineCache WeatherCache(Caffeine<Object, Object> caffeine) {
        return new CaffeineCache(weatherCacheName, caffeine.build());
    }
}


