package com.skyedu.dao.impl;

import com.skyedu.model.EduCity;
import com.skyedu.model.EduPeriod;
import com.util.HbmDAOUtil;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Description xxx
 * @author: LiMing
 * @date: 2019/11/3  15:56
 * 基础的编辑设置数据查询
 */
@Repository
public class BasePermissionDao extends HbmDAOUtil {

    /**
     * 查询年级列表
     * @return
     */
    public List<Map<String, Object>> gradeList() {

        return factory
                .getCurrentSession()
                .createSQLQuery(
                        "select * from Edu_Grade eg where eg.code in ('A01','A02','A03','A04','A05','A06')")
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    }

    /**
     * 查询对应的地区城市列表
     * @return
     */
    public List<Map<String, Object>> getCity() {

        return factory.getCurrentSession().createSQLQuery("SELECT * from Edu_City").setResultTransformer(
                Transformers.ALIAS_TO_ENTITY_MAP
        ).list();
    }

    /**
     * 获取科目
     * @return
     */
    public List<Map<String, Object>> subjectList() {
        return factory.getCurrentSession().createSQLQuery("SELECT * from Edu_Subject WHERE code='A01' ").setResultTransformer(
                Transformers.ALIAS_TO_ENTITY_MAP
        ).list();
    }

}

