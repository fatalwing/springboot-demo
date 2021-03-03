package com.townmc.boot.service.impl;

import com.townmc.utils.DateUtil;
import com.townmc.utils.NumberUtil;
import com.townmc.boot.dao.RedisDao;
import com.townmc.boot.service.IdService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 本id生成依赖redis
 * @author meng
 */
@Slf4j
@Service
public class IdServiceImpl implements IdService {
    private static final String REDIS_ID_KEY = "com:townmc:boot:idkey:";

    private final RedisDao redisDao;
    public IdServiceImpl(RedisDao redisDao) {
        this.redisDao = redisDao;
    }


    @Override
    public String createNumId() {
        // 已当前秒当key，依靠redis完成自增操作。当前秒数取2020年以来的值
        long time2020 = DateUtil.formatDate(DateUtil.FULL_TIME_PATTERN, "2020-01-01 00:00:00").getTime();
        long since2020 = System.currentTimeMillis() - time2020;

        String calcSec = String.valueOf(since2020/60000);

        long incr = redisDao.increment(calcSec, 90);

        return calcSec + String.valueOf(incr);
    }

    @Override
    public String createId() {
        return NumberUtil.hex10To64(new BigDecimal(this.createNumId()));
    }

}
