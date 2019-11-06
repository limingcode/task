package com.skyedu.dao.impl;

import com.util.CommonUtil;
import com.util.HbmDAOUtil;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import java.util.List;
import java.util.Map;

/**
 * @Description xxx
 * @author: LiMing
 * @date: 2019/10/28  14:20
 * 权限控制实现dao
 */
@Repository
public class PermissonDao extends HbmDAOUtil {



    /**
     * 查询对应的班级列表
     * @param condition
     * @return
     */
    public List<Map<String, Object>> getMessageList(Map<String, Object> condition) {
        //获取
        String jname = (String) condition.get("jname");
        String dname = (String) condition.get("dname");

        String fsql = "SELECT b.mapping_Key,a.type,a.createTime,i.CityName,e.termName,d.name as dname,j.name as jname,g.name as gname,f.name as fname,h.name as hname,d.courseTime,d.courseTimeFlag,c.bookName,a.operationTime,a.operationPeople FROM Tk_rule_base_t a LEFT JOIN Tk_rule_mapping_t b ON a.typeId = b.typeId LEFT JOIN IM_Book c ON b.bookId = c.id LEFT JOIN Edu_Course d ON c.id = d.id LEFT JOIN Edu_Period e ON d.period = e.id LEFT JOIN Edu_Grade f ON d.grade = f.code LEFT JOIN Edu_Depa g ON d.depa = g.code LEFT JOIN Edu_Cate h ON d.cate = h.code LEFT JOIN Edu_City i ON c.cityId = i.CityId LEFT JOIN Edu_Teacher j ON  d.id=j.id WHERE a.type = 1";

        Query q1 = factory.getCurrentSession().createSQLQuery(fsql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        //查询分页信息

        return q1.list();
    }

    /**
     * 查询设置过阅读权限的学生进行分页显示 ，模糊查询
     *
     * @param condition
     * @return
     */

    public List<Map<String, Object>> getEveryList(Map<String, Object> condition) {
        String id = (String) condition.get("id");//学生姓名
        String name = (String) condition.get("name");//学生姓名

        String code = (String) condition.get("code");//学生编号

        int pageNo = (Integer) condition.get("pageNo");//分页码
        //对应学生阅读层次的数据
        String fsql = "select es.id,es.name,es.code,ib.bookName + ',' as bookName,rb.operationTime,rb.operationPeople from Tk_rule_base_t rb left join Tk_rule_mapping_t tm on rb.typeId = tm.typeId left join IM_Book ib on tm.bookId = ib.id left join Edu_Student es ON rb.typeId = es.id where  rb.type = 1 and rb.typeId = es.id";
        String fcon = "";
        //模糊查询
        if (!StringUtils.isEmpty(name)) {
            fcon = fcon + " and a.name like '%" + name + "%'";
        }
        if (!StringUtils.isEmpty(code)) {
            fcon = fcon + " and a.code='" + code + "'";
        }
        String sql = fsql + fcon + " order by rb.operationTime desc";
        Query q = factory.getCurrentSession().createSQLQuery(sql)
                .setFirstResult((pageNo - 1) * CommonUtil.QUESTIONSIZE)
                .setMaxResults(CommonUtil.QUESTIONSIZE)
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        //查询设置过阅读权限的学生人数  然后进行分页
        String sql1 = "select count(1) count from Tk_rule_mapping_t a  WHERE a.bookId  is not  null" + fcon;
        Query q1 = factory.getCurrentSession().createSQLQuery(sql1).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        int size = (Integer) ((Map<String, Object>) q1.uniqueResult()).get("count");
        int totalPage = size / CommonUtil.QUESTIONSIZE + 1;
        if (size % CommonUtil.QUESTIONSIZE == 0) {
            totalPage = totalPage - 1;
        }
        if (pageNo > totalPage) {
            pageNo = totalPage;
            condition.put("pageNo", pageNo);
        }
        condition.put("totalPage", totalPage);
        //将查询的对线转化成map集合
        //将map转化成list集合
        return q.list();


    }

    public int delStudent(int id) {
        String sql = "DELETE  FROM Tk_rule_mapping_t WHERE typeId= " + id;
        return factory.getCurrentSession().createQuery(sql).executeUpdate();


    }
}
