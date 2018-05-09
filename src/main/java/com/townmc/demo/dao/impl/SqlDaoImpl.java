package com.townmc.demo.dao.impl;

import com.townmc.demo.dao.SqlDao;
import com.townmc.demo.domain.po.Demo;
import com.townmc.demo.utils.LogicException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;
import java.util.Map;

@Repository
public class SqlDaoImpl implements SqlDao {
    @Autowired private JdbcTemplate jdbcTemplate;

    @Override
    public Demo findOne(String demoId) {
        Map<String, Object> re = jdbcTemplate.queryForMap("select * from demo where demo_id=?", new Object[]{demoId});
        if(null == re) {
            throw new LogicException("object_not_exists", "查找的对象不存在");
        }
        Demo demo = new Demo();
        demo.setDemoId(demoId);
        demo.setDemoName((String)re.get("demo_name"));
        demo.setStatus(Demo.DemoStatus.valueOf((String)re.get("status")));
        demo.setDateCreated((Date)re.get("date_created"));
        demo.setLastUpdated((Date)re.get("last_updated"));
        demo.setDeleted((Integer)re.get("deleted"));
        return demo;
    }
}
