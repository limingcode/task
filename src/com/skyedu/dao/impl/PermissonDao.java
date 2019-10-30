package com.skyedu.dao.impl;

import com.skyedu.model.EduStudent;
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
 * @date: 2019/10/25  14:20
 * 权限控制实现dao
 *
 */
@Repository
public class PermissonDao  extends HbmDAOUtil {


    /**
     *
     * @param condition
     * @return
     */
    public List<Map<String, Object>> getMessageList(Map<String, Object> condition) {
        //获取
        String title = (String) condition.get("title");
        String grade = (String) condition.get("grade");
        String subject = (String) condition.get("subject");
        String cate = (String) condition.get("cate");
        int pageNo = (Integer) condition.get("pageNo");

        String fsql = "select wq.*,(select name from Edu_Teacher su where su.city=wq.city) teacherName,(select userName from Edu_Teacher su where su.oaId=wq.editor and su.oaId!=0) editorName,(select name from edu_cate ec where ec.code=wh.cate)cateName,(select name from edu_subject es where es.code=wh.subject)subjectName,(select name from edu_grade eg where eg.code=wh.grade)gradeName from IM_ wq left join WA_Hierarchy wh on wq.hierarchy = wh.id where wq.pId is null ";

        String fcon = "";
        if (!StringUtils.isEmpty(title)) {
            fcon = fcon + " and wq.brief like '%" + title + "%'";

        }

        if (!StringUtils.isEmpty(grade)) {
            fcon = fcon + " and wh.grade='" + grade + "'";
        }
        if (!StringUtils.isEmpty(subject)) {
            fcon = fcon + " and wh.subject='" + subject + "'";
        }

        if (!StringUtils.isEmpty(cate)) {
            fcon = fcon + " and wh.cate='" + cate + "'";
        }


        String sql = fsql + fcon + " order by wq.createDate desc";
        Query q = factory.getCurrentSession().createSQLQuery(sql)
                .setFirstResult((pageNo - 1) * CommonUtil.QUESTIONSIZE)
                .setMaxResults(CommonUtil.QUESTIONSIZE)
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        String sql1 = "select count(1)count from WA_Question wq left join WA_Hierarchy wh on wq.hierarchy = wh.id where wq.pId is null "+ fcon;
        Query q1 = factory.getCurrentSession().createSQLQuery(sql1).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);


        //查询分页信息
        int size = (Integer) ((Map<String,Object>)q1.uniqueResult()).get("count");
        int totalPage = size / CommonUtil.QUESTIONSIZE + 1;
        if (size % CommonUtil.QUESTIONSIZE == 0) {
            totalPage = totalPage - 1;
        }

        if (pageNo > totalPage) {
            pageNo = totalPage;
            condition.put("pageNo", pageNo);
        }
        condition.put("totalPage", totalPage);
        return q.list();
    }

    /**
     * 查询设置过阅读权限的学生进行分页显示 ，模糊查询
     * @param condition
     * @return
     */

    public  List<Map<String, Object>> getEveryList(Map<String, Object> condition){
        String name = (String) condition.get("name");//学生姓名

        String code = (String) condition.get("code");//学生编号

        int pageNo = (Integer) condition.get("pageNo");//分页码
        //对应学生阅读层次的数据
        String fsql = "SELECT a.id,a.name,a.code,b.levelCateId,b.operationTime,b.operationPeople FROM Edu_Student a,IM_Permission b WHERE a.permissonCode = b.levelCateId";
        String fcon = "";
        //模糊查询
        if (!StringUtils.isEmpty(name)) {
            fcon = fcon + " and a.name like '%" + name + "%'";
        }
        if (!StringUtils.isEmpty(code)) {

            fcon = fcon + " and a.code='" + code + "'";
        }
        String sql = fsql + fcon + " order by b.operationTime desc";
        Query q = factory.getCurrentSession().createSQLQuery(sql)
                .setFirstResult((pageNo - 1) * CommonUtil.QUESTIONSIZE)
                .setMaxResults(CommonUtil.QUESTIONSIZE)
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        //查询设置过阅读权限的学生人数  然后进行分页
        String sql1 = "select count(1) count from Edu_Student wq left join IM_Permission wh on wq.id = wh.id where wq.permissonCode is not null "+ fcon;
        Query q1 = factory.getCurrentSession().createSQLQuery(
                sql1).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        int size = (Integer) ((Map<String,Object>)q1.uniqueResult()).get("count");
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
}
