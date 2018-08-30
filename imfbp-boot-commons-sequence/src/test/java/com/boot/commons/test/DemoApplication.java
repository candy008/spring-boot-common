package com.boot.commons.test;

import com.imfbp.boot.common.utils.sequence.CacheSequence;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@SpringBootApplication
@ComponentScan(basePackages={"com.imfbp.boot.common"})
public class DemoApplication {

//	@Bean
//	public RedisConnectionFactory jedisConnectionFactory(){
//
//		JedisPoolConfig poolConfig=new JedisPoolConfig();
//		poolConfig.setMaxIdle(5);
//		poolConfig.setMinIdle(1);
//		poolConfig.setTestOnBorrow(true);
//		poolConfig.setTestOnReturn(true);
//		poolConfig.setTestWhileIdle(true);
//		poolConfig.setNumTestsPerEvictionRun(10);
//		poolConfig.setTimeBetweenEvictionRunsMillis(60000);
//		//哨兵节点host、port设置，可设置多个哨兵，只需要链式新增 .sentinel(sentinelhost, sentinelport)
//		RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration() .master("session-cluster")
//				.sentinel("test.sentinel1.imfbp.com",26379)
//				.sentinel("test.sentinel2.imfbp.com",26379)
//				.sentinel("test.sentinel3.imfbp.com",26379);
//		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(sentinelConfig,poolConfig);
//
//		return jedisConnectionFactory;
//	}

	@Bean(name = "redisTemplate")
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory jedisConnectionFactory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate();
		redisTemplate.setConnectionFactory(jedisConnectionFactory);
		RedisSerializer<String> stringRedisSerializer = new StringRedisSerializer();//Long类型不可以会出现异常信息;
		redisTemplate.setKeySerializer(stringRedisSerializer);
		return redisTemplate;
	}

	@Bean(name = "cacheSequence")
	public CacheSequence cacheSequence( RedisTemplate<String, Object> redisTemplate) {
		CacheSequence cacheSequence = new CacheSequence();
		cacheSequence.setRedisTemplate(redisTemplate);
		cacheSequence.setHexNum(10);
		return cacheSequence;
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
