package com.boot.commons.test.sequence;

import com.boot.commons.test.DemoApplication;
import com.imfbp.boot.common.utils.sequence.CacheSequence;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class SequenceTest {


    @Resource(name = "redisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    @Resource(name = "cacheSequence")
    private CacheSequence cacheSequence;

    @Test
    public void test() {

        for (int i = 0; i < 200; i++) {
            String s = cacheSequence.get("seq.test21");
            System.out.println(s);
        }
        redisTemplate.delete("seq.test21");
    }
}