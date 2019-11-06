package com.skyedu.dao.impl;

import com.skyedu.model.EduCity;
import com.skyedu.model.EduGrade;
import com.skyedu.model.EduPeriod;
import com.util.HbmDAOUtil;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description xxx
 * @author: LiMing
 * @date: 2019/11/6  9:54
 * dao层实现
 */
@Repository
public class GradeBaseRelateDao extends HbmDAOUtil {
    /**业务层实现*/
    public List<EduCity> getIiniTtermName() {
        String sql = "SELECT CityId,CityName FROM Edu_City";
        return factory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    }

    public List<EduPeriod> getInitPeriod() {
        String sql = "SELECT id,name,termName FROM Edu_Period";
        return factory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    }
}
