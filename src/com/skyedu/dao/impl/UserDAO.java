package com.skyedu.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.skyedu.model.AppBugLog;
import com.skyedu.model.StudentLoginLog;
import com.util.HbmDAOUtil;

@Repository
public class UserDAO extends HbmDAOUtil{

    /**
     * 获取学生信息
     * @param username`
     * @param password
     * @return
     */
    public Map<String, Object> getStudent(String username, String password){
        String sql = "select top 1 a.* from Edu_Student a where (a.code = '"+ username + "' or a.name='"+ username + "') and (a.fatherTel ='"+ password +"' or a.motherTel = '"+ password +"') and a.status>-1 order by a.id desc";
        Query q = factory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return (Map<String, Object>) q.uniqueResult();
    }
    public Map<String, Object> getWjStudent(String username, String password){
        String sql = "select top 1 studentid   as id,\n" +
                "             studentcode as code,\n" +
                "             studentname as name,\n" +
                "             case when sex='男' then 0 when sex='女' then 1 else 2 end         as sex,\n" +
                "             school      as school,\n" +
                "             address     as address,\n" +
                "             region      as region,\n" +
                "             fathername  as father,\n" +
                "             fathertel   as fatherTel,\n" +
                "             mothername  as mother,\n" +
                "             mothertel   as motherTel,\n" +
                "             status      as status,\n" +
                "             description as description,\n" +
                "             logstamp    as logStamp,\n" +
                "             "+1+"   as tag,\n" +
                "             cityid      as cityId\n" +
                "from Wj_Edu_Student\n" +
                "where (StudentName = '"+username+"' or StudentCode = '"+username+"')\n" +
                "  and (FatherTel = '"+password+"' or MotherTel = '"+password+"')\n" +
                "  and Status > -1\n" +
                "order by studentid desc";
        Query q = factory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return (Map<String, Object>) q.uniqueResult();
    }
    public Map<String, Object> getWjStudent(int studentId){
        String sql = "select top 1 studentid   as id,\n" +
                "             studentcode as code,\n" +
                "             studentname as name,\n" +
                "             case when sex='男' then 0 when sex='女' then 1 else 2 end          as sex,\n" +
                "             school      as school,\n" +
                "             address     as address,\n" +
                "             region      as region,\n" +
                "             fathername  as father,\n" +
                "             fathertel   as fatherTel,\n" +
                "             mothername  as mother,\n" +
                "             mothertel   as motherTel,\n" +
                "             status      as status,\n" +
                "             description as description,\n" +
                "             logstamp    as logStamp,\n" +
                "             "+1+"   as tag,\n" +
                "             cityid      as cityId\n" +
                "from Wj_Edu_Student\n" +
                "where  studentid="+studentId+
                "  and Status > -1\n" +
                "order by studentid desc";
        Query q = factory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return (Map<String, Object>) q.uniqueResult();
    }
    public Map<String, Object> getStudentInfo(String username, String password){
        String sql = "select top 1 a.id,\n" +
                "             a.code,\n" +
                "             a.name,\n" +
                "             a.school,\n" +
                "             a.address,\n" +
                "             a.father,\n" +
                "             a.fatherTel,\n" +
                "             a.mother,\n" +
                "             a.motherTel,\n" +
                "             a.status,\n" +
                "             a.description,\n" +
                "             a.logStamp,\n" +
                "             a.img,\n" +
                "             a.tip,\n" +
                "             a.gradeCode,\n" +
                "             a.cityId,\n" +
                "             a.region,\n" +
                "             (case when a.sex = '男' then 0 else case when a.sex = '女' then 1 else 2 end end) as sex\n" +
                "from Edu_Student a where (a.code = '"+ username + "' or a.name='"+ username + "') and (a.fatherTel ='"+ password +"' or a.motherTel = '"+ password +"') and a.status >-1 order by a.id desc";
        Query q = factory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return (Map<String, Object>) q.uniqueResult();
    }

    /** 我的头像 */
    public void changImg(int studentId, String imgUrl, int cityId) {
        if (cityId == 1){
            String sql = "update Edu_Student set img='" + imgUrl + "' where id="
                    + studentId;
            factory.getCurrentSession().createSQLQuery(sql).executeUpdate();
            sql = "update Daemon..Edu_Student set img='" + imgUrl + "' where id="
                    + studentId;
            factory.getCurrentSession().createSQLQuery(sql).executeUpdate();
        } else {
            String sql = "update Task_Hz..Edu_Student set img='" + imgUrl + "' where id="
                    + studentId;
            factory.getCurrentSession().createSQLQuery(sql).executeUpdate();
            sql = "update Daemon_Hz..Edu_Student set img='" + imgUrl + "' where id="
                    + studentId;
            factory.getCurrentSession().createSQLQuery(sql).executeUpdate();
        }
    }

    /** 引导页 */
    public void changTip(int studentId, int tip) {
        String sql = "update Edu_Student set tip=" + tip + " where id="
                + studentId;
        factory.getCurrentSession().createSQLQuery(sql).executeUpdate();
    }

    /** 学生信息 */
    public Map<String,Object> getStudent(int studentId,int cityId) {
        String sql="";
        if (cityId==1){
            sql = "select * from Edu_Student where id="
                    + studentId;
        }else {
            sql = "select * from task..Edu_Student where id="
                    + studentId;
        }

        return (Map<String, Object>) factory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).uniqueResult();
    }
    public Map<String,Object> getStudent(int studentId) {

        String sql="select top 1 id,\n" +
                "             code,\n" +
                "             name,\n" +
                "             school,\n" +
                "             address,\n" +
                "             father,\n" +
                "             fatherTel,\n" +
                "             mother,\n" +
                "             motherTel,\n" +
                "             status,\n" +
                "             description,\n" +
                "             logStamp,\n" +
                "             img,\n" +
                "             tip,\n" +
                "             gradeCode,\n" +
                "             cityId,\n" +
                "             region,\n" +
                "             (case when sex = '男' then 0 else case when sex = '女' then 1 else 2 end end) as sex\n" +
                "from Edu_Student\n" +
                "where id = " +studentId+
                "  and status != -1";
        return (Map<String, Object>) factory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).uniqueResult();
    }
    public void saveStudentLoginLog(StudentLoginLog studentLoginLog){
        factory.getCurrentSession().saveOrUpdate(studentLoginLog);
    }

    public StudentLoginLog getStudentLoginLog(int studentId){
        String sql = "from StudentLoginLog where studentId="
                + studentId;
        Query q = factory.getCurrentSession().createQuery(sql);
        return (StudentLoginLog) q.uniqueResult();
    }

    public List<Map<String,Object>> getOaId(String username){
        String sql = "select * from sys_user where username = '"+username+"'";
        Query q = factory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return q.list();
    }

    public void setDelRecord(int oaId,String name,String content,int type){
        String sql = "insert into WA_DelRecord (oaId,name,content,type,createDate) values ("+oaId+",'"+name+"','"+content+"',"+type+",getdate())";
        factory.getCurrentSession().createSQLQuery(sql).executeUpdate();
    }

    public void saveAppBugLog(AppBugLog appBugLog) {
        factory.getCurrentSession().save(appBugLog);
    }
}
