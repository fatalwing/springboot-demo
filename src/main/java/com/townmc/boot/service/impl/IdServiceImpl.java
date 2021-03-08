package com.townmc.boot.service.impl;

import com.townmc.boot.service.CacheService;
import com.townmc.utils.DateUtil;
import com.townmc.utils.NumberUtil;
import com.townmc.boot.service.IdService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.townmc.boot.constants.RedisKeyConstants.ID_CREATOR_KEY;

/**
 * 本id生成依赖redis
 * @author meng
 */
@Slf4j
@Service
public class IdServiceImpl implements IdService {

    private final CacheService cacheService;
    public IdServiceImpl(CacheService cacheService) {
        this.cacheService = cacheService;
    }


    @Override
    public String createNumId() {
        // 以当前秒当key，依靠缓存完成自增操作。当前秒数取2020年以来的值
        long time2020 = DateUtil.formatDate(DateUtil.FULL_TIME_PATTERN, "2020-01-01 00:00:00").getTime();
        long since2020 = System.currentTimeMillis() - time2020;

        String calcSec = String.valueOf(since2020/60000);

        long incr = cacheService.increment(ID_CREATOR_KEY + calcSec, 90);

        return calcSec + String.valueOf(incr);
    }

    @Override
    public String createId() {
        return NumberUtil.hex10To64(new BigDecimal(this.createNumId()));
    }

}
