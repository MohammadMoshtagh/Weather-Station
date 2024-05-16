package edu.sharif.webproject.config;

import java.util.concurrent.TimeUnit;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Arrays;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.github.benmanes.caffeine.cache.Caffeine;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public Caffeine<Object, Object> caffeineConfig() {
        return Caffeine.newBuilder();
    }

    @Bean
    public CacheManager cacheManager(Caffeine<Object, Object> caffeine) {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(caffeine);
        cacheManager.setCacheNames(Arrays.asList("CountriesCache", "CountryNameCache", "WeatherCache"));
        cacheManager.setCacheSpecification("expireAfterWrite=1h");
        return cacheManager;
    }

    @Bean
    public CaffeineCache CountriesCache(Caffeine<Object, Object> caffeine) {
        return new CaffeineCache("CountriesCache", caffeine.build());
    }

    @Bean
    public CaffeineCache CountryNameCache(Caffeine<Object, Object> caffeine) {
        return new CaffeineCache("CountryNameCache", caffeine.build());
    }

    @Bean
    public CaffeineCache WeatherCache(Caffeine<Object, Object> caffeine) {
        return new CaffeineCache("WeatherCache", caffeine.build());
    }
}


