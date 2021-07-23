package com.townmc.boot.dao.impl;

import com.townmc.boot.dao.SqlDao;
import com.townmc.boot.domain.enums.Err;
import com.townmc.boot.domain.po.Demo;
import com.townmc.boot.utils.BrokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Map;

/**
 * @author meng
 */
@Repository
public class SqlDaoImpl implements SqlDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Demo findOne(String id) {
        Map<String, Object> re = jdbcTemplate.queryForMap("select * from app where id=?", new Object[]{id});
        if(null == re) {
            throw new BrokenException(Err.NULL_OBJECT);
        }
        Demo demo = new Demo();
        demo.setId(id);
        demo.setDateCreated((Date)re.get("date_created"));
        demo.setLastUpdated((Date)re.get("last_updated"));
        demo.setDeleted((Integer)re.get("deleted"));
        return demo;
    }
}
