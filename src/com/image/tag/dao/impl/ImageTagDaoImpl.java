package com.image.tag.dao.impl;

import java.io.Serializable;
import java.util.*;

import com.google.gson.Gson;
import com.skyedu.model.book.BookAudioRecordModel;
import com.skyedu.model.book.SendBookJsonRecordModel;
import com.skyedu.model.common.PageModel;
import com.skyedu.model.user.ShareBookModel;
import com.util.StringUtil;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.aspectj.weaver.ast.Var;
import org.hibernate.mapping.Join;
import org.springframework.stereotype.Repository;

import com.image.tag.dao.ImageTagDao;
import com.image.tag.model.ImBook;
import com.image.tag.utils.ImageTagSendFileUtil;
import com.util.CommonUtil;

@Repository
public class ImageTagDaoImpl extends ImageTagBaseDao implements ImageTagDao {
    @Override
    public int saveOrUpdateObj(Object book) {
        Serializable obj = saveOrUpdate((Serializable) book);
        return ((ImBook) obj).getId();
    }
    @Override
    public Map<String, Object> checkVersion(int type) {
        String sql = "select * from tk_version_t where type=" + type;
        return getMapUseSql(sql);
    }

    @Override
    public PageModel getMyWorks(int studentId, PageModel pageModel) {
        String sql="select ibk.bookName as bookName,\n" +
                "       ibk.pop as pop,\n" +
                "       tsBt.valid as valid,\n" +
                "       tsBt.createTime as createTime,\n" +
                "       tsBt.type as type,\n" +
                "       tsBt.studentId as studentId,\n" +
                "       tsBt.typeId as typeId,\n" +
                "       tsBt.shareBookId as shareBookId,\n" +
                "       tsBt.bookId as bookId\n" +
                "from tk_shareBook_t tsBt\n" +
                "       left join IM_Book ibk on tsBt.bookId = ibk.id\n" +
                "where tsBt.studentId = "+studentId;
        PageModel<Map<String, Object>> mapPageModel = new PageModel<>();
        mapPageModel.setDataList(getListUseSql(sql,pageModel.getCurrPage(),pageModel.getPageSize()));
        return  mapPageModel;
    }

    @Override
    public void shareBook(ShareBookModel shareBookModel) {
        saveOrUpdate(shareBookModel);
    }

    @Override
    public void saveBookAudioRecordModel(BookAudioRecordModel BookAudioRecordModel) {
        saveOrUpdate(BookAudioRecordModel);
    }

    @Override
    public  Map<String, Object> getShareAudioInfo(int studentId, int graphicalId,int isShare ) {
        return  getMapUseSql("select top 1* from Tk_Book_AudioRecord_t where graphicalId=" + graphicalId+" and studentId="+studentId+" and valid=1 and isShare="+isShare);
    }

    @Override
    public Map<String, Object> getShareBookInfo(int bookId, int studentId) {
        String sql ="select * from tk_shareBook_t where bookId="+bookId+" and studentId="+studentId;
        return getMapUseSql(sql);
    }

    @Override
    public List<Map<String, Object>> getShareAudioInfoList(Integer studentId, Integer bookId, Integer isShare,Integer tag) {
        String sql ="select *from Tk_Book_AudioRecord_t where studentId="+studentId;
        if(!org.springframework.util.StringUtils.isEmpty(bookId)){
            sql+=" and bookId="+bookId;
        }
        if(!org.springframework.util.StringUtils.isEmpty(isShare)){
            sql+=" and isShare="+isShare;
        }
        if(!org.springframework.util.StringUtils.isEmpty(tag)){
            sql+=" and tag="+tag;
        }
        return getListUseSql(sql);
    }

    @Override
    public BookAudioRecordModel getShareAudioInfo(int bookAudioRecordId) {
        return (BookAudioRecordModel) find(BookAudioRecordModel.class, bookAudioRecordId);
    }

    @Override
	public ImBook getBook(int id) {
		return (ImBook) find(ImBook.class, id);
	}

    @Override
    public SendBookJsonRecordModel getSendBookJsonRecordModel(int id) {
        return (SendBookJsonRecordModel) find(SendBookJsonRecordModel.class, id);
    }

    @Override
	public Map<String, Object> getLesson(int lessonId) {
		String sql = "select * from IM_LESSON where id =" + lessonId;
		return getMapUseSql(sql);
	}
	
	@Override
	public void updateLessonPop(String id, String pop){
		String sql = "update IM_LESSON set lessonPop = '"+ pop +"' where id in ("+ id +")";
		updateUseSql(sql);
	}

    @Override
    public void addLesson(String josn) {
        JSONArray aa=JSONArray.fromObject(josn);
        System.out.println(aa);
        for (int i=0;aa.size()>i;i++){
            String bookName=aa.getJSONObject(i).get("bookName").toString();
                    String desc=aa.getJSONObject(i).get("desc").toString().replaceAll("'"," ");
            String bb="lessonPop/"+bookName+".jpg";
            String sql="insert into IM_LESSON\n" +
                    "  (fid, name, description, lessonpop,orderNo)\n" +
                    "values (182,'"+bookName+"','"+desc+"','"+bb+"',"+(i+1)+")" ;
            System.out.println(sql);
            System.out.println(i);
            factory.getCurrentSession().createSQLQuery(sql).executeUpdate();

        }
    }

    @Override
	public List<Map<String, Object>> getLessonList(String bookId) {
		String sql = "select * from IM_Lesson where fId = "+ bookId +" order by orderNo"; 
		return getListUseSql(sql);
	}
	
	@Override
	public List<Map<String, Object>> getLessonList(int fid) {
		String sql = "select * from IM_Lesson where fId >= "+ fid +" order by orderNo"; 
		return getListUseSql(sql);
	}

	@Override
	public Map<String, Object> getStudent(int studentId) {
		String sql = "select id, code, name, sex, school, address from edu_student where id = "+ studentId;
		return getMapUseSql(sql);
	}

	@Override
	public ImBook getBook(String gradeCode, String subjectCode, int cateCode, String bookName) {
		String hql = "from ImFileSendRecord if where if.gradeCode = '" + gradeCode +"' and if.subjectCode='"+ subjectCode +"' "
						+ "and if.cateCode = "+ cateCode +" and if.bookName = '"+ bookName +"'";
		return (ImBook) getObjUseHql(hql);
	}

	@Override
	public int saveBook(ImBook book) {
		Serializable obj = saveOrUpdate(book);
		return ((ImBook) obj).getId();
	}

    @Override
    public int saveSendBookJsonRecordModel(SendBookJsonRecordModel book) {
        Serializable obj = saveOrUpdate(book);
        return ((SendBookJsonRecordModel) obj).getSendBookJsonRecordId();
    }

    @Override
	public void updateBookStatus(int id, byte status) {
		String sql = "update Im_Book set status = '"+ status +"', updateTime = getDate() "
				+ "where id = '"+ id +"' ";
		updateUseSql(sql);
	}
	@Override
	public void updateBookStatus(String gradeCode, String subjectCode, int cateCode, String bookName, byte status) {
		String sql = "update IM_Book set status = '"+ status +"', updateTime = getDate() "
				+ "where gradeCode = '"+ gradeCode +"' and subjectCode='"+ subjectCode +"' and cateCode = "+ cateCode +" and bookName = '"+ bookName +"'";
		updateUseSql(sql);
	}
	
	@Override
	public List<Map<String, Object>> getSendFailBook() {
		String sql = "select * from IM_Book where status = "+ ImageTagSendFileUtil.FILE_SEND_STATUS_UNSEND +" and sendNum < 10";
		return getListUseSql(sql);
	}

	@Override
	public List<Map<String, Object>> getCateAndStatus(String subject, String grade, String period, String level) {
		if (StringUtils.isNotEmpty(grade) && StringUtils.isNotEmpty(level)) {
			String sql = "select im.*, (select count(*) from IM_Lesson where fId = im.id) as lessonCount "
					+ "from IM_Book im "
					+ "where im.gradeCode = '"+ grade +"' and subjectCode = '"+ subject +"' and cateCode = "+ level
					+ " order by im.cateCode, im.bookName";
			return getListUseSql(sql);
		}
		if (StringUtils.isNotEmpty(grade)) {
			String sql = "select im.*, ec.name, (select count(*) from IM_Lesson where fId = im.id) as lessonCount "
					+ "from IM_Book im inner join edu_cate ec on im.cateCode = ec.code "
					+ "where im.gradeCode = '"+ grade +"' and subjectCode = '"+ subject +"' "
					+ "order by im.cateCode, im.bookName";
			return getListUseSql(sql);
		} 
		String sql = "select im.*, (select count(*) from IM_Lesson where fId = im.id) as lessonCount "
				+ "from IM_Book im "
				+ "where im.cateCode = '"+ level +"' and subjectCode = '"+ subject +"' "
				+ "order by im.id";
		return getListUseSql(sql);
	}
	
	@Override
	public List<Map<String, Object>> getBookTypeList() {
		String sql = "select distinct im.bookName, im.cateCode, ec.name from IM_Book im inner join edu_cate ec on im.cateCode = ec.code";
		return getListUseSql(sql);
	}

	@Override
	public List<Map<String, Object>> getBookList(int studentId) {
		String sql = "select distinct fsr.id,fsr.bookName,fsr.pop, fsr.sendtime, fsr.zipSize from Edu_Course co inner join Edu_StudentClass cls on cls.course=co.id "+ 
				"inner join IM_Book fsr on co.subject=fsr.subjectCode and co.grade=fsr.gradeCode and co.cate=fsr.cateCode "+
				"where co.status<>-1 and cls.status=1 and fsr.status=2 and student="+ studentId;
		return getListUseSql(sql);
	}

    /**
     * @see  ImageTagDaoImpl#getNewBookList(int, int, Integer, int)
     * @param studentId
     * @param cityId
     * @return
     */
	@Deprecated
	@Override
	public List<Map<String, Object>> getBookList(int studentId, int cityId) {
		if (cityId == 2) { //杭州地区
//		      2018 秋季规则
//            String sql = "select * from (" +
//                        "select fsr.id,fsr.bookName,fsr.pop,fsr.sendtime,fsr.cateCode,fsr.zipSize,(case fsr.status when 2 then 1 else 0 end) isReady, (case when t.grade is not null then 1 else 0 end) isBelongtoClass, fsr.md5Code,"+
//                        "    '"+CommonUtil.APPFILEURL+"/upload/imageTag/zip/76/'+ convert(varchar,fsr.id) + '/'+ replace(replace(fsr.bookName, ' ', '_'), '朗文', 'langwen')+'.zip' downloadUrl "+
//                        "from im_book fsr left join  (     "+
//                        "	select distinct case when co.cate = 21 then 'A01' when co.cate = 22 then 'A02' else co.grade end grade, co.subject, co.cate " +
//                        "   from Edu_Course co inner join Edu_StudentClass esc on co.id = esc.course left join Edu_Curriculum cu on co.currId = cu.id "+
//                        "	where co.period >= 100 and (cu.courseType is null or cu.courseType = 1) and co.status<>-1 and esc.status in (1,3) and esc.student = "+ studentId +" and subject = 'A01'"+
//                        "    ) t on fsr.subjectCode = t.subject and charIndex(','+ t.grade +',', ','+ fsr.displayGrade +',') > 0 and charIndex(','+ t.cate +',', ','+ fsr.displayCate +',') > 0  "+
//                        "where fsr.showStatus = 1 and fsr.cateCode between 100 and 200 " +
//                    " union " +
//                        "select fsr.id,fsr.bookName,fsr.pop,fsr.sendtime,fsr.cateCode,fsr.zipSize,(case fsr.status when 2 then 1 else 0 end) isReady, (case when t.grade is not null then 1 else 0 end) isBelongtoClass, fsr.md5Code," +
//                        "  '"+CommonUtil.APPFILEURL+"/upload/imageTag/zip/76/'+ convert(varchar,fsr.id) + '/'+ replace(replace(fsr.bookName, ' ', '_'), '朗文', 'langwen')+'.zip' downloadUrl " +
//                        "from im_book fsr left join  ( " +
//                        "    select distinct co.subject, case when co.cate = 21 then 'A01' when co.cate = 22 then 'A02' else co.grade end grade, " +
//                        "       case when co.cate in (3, 21, 22) or co.cate = 7 and co.grade = 'A04' then 2 else -1 end cate " +
//                        "    from Edu_Course co inner join Edu_StudentClass esc on co.id = esc.course left join Edu_Curriculum cu on co.currId = cu.id left join Edu_Period ep on ep.id = co.period " +
//                        "    where co.period >= 100 and ep.termName like '%秋季' and (cu.courseType is null or cu.courseType = 1) and co.status<>-1 and esc.status in (1,3) and esc.student = "+ studentId +" and subject = 'A01' " +
//                        ") t on fsr.subjectCode = t.subject and t.grade=fsr.gradeCode and t.cate = fsr.cateCode " +
//                        "  where fsr.id between 7 and 10) tt order by tt.id";
            String sql ="select * from ("+
                        "        select fsr.id,fsr.bookName,fsr.pop,fsr.sendtime,fsr.cateCode,fsr.zipSize,(case when fsr.status = 2 then 1 else 0 end) isReady, "+
                        "                (case when t.grade is not null then 1 else 0 end) isBelongtoClass, fsr.md5Code,    "+
                        "                '"+CommonUtil.APPFILEURL+"/upload/imageTag/zip/76/'+ convert(varchar,fsr.id) + '/'+ replace(replace(fsr.bookName, ' ', '_'), '朗文', 'langwen')+'.zip' downloadUrl "+
                        "        from im_book fsr left join  (     	"+
                        "                select distinct case when co.cate = 21 then 'A01' when co.cate = 22 then 'A02' else co.grade end grade, co.subject, "+
                        "                        cast(case when co.cate = 9 and co.grade = 'A01' and co.period = 106  then 7 else co.cate end as varchar) cate  "+
                        "                from Edu_Course co inner join Edu_StudentClass esc on co.id = esc.course left join Edu_Curriculum cu on co.currId = cu.id 	"+
                        "                where co.period > 100 and (cu.courseType is null or cu.courseType = 1) and co.status<>-1 and esc.status in (1,3) and esc.student = "+ studentId +" and subject = 'A01'    "+
                        "             ) t on fsr.subjectCode = t.subject and charIndex(','+ t.grade +',', ','+ fsr.displayGrade +',') > 0 and charIndex(','+ t.cate +',', ','+ fsr.displayCate +',') > 0  "+
                        "        where fsr.showStatus = 1 and fsr.cateCode between 100 and 200  "+
                        "     union "+
                        "        select fsr.id, fsr.bookName,fsr.pop,fsr.sendtime,2 cateCode,fsr.zipSize,(case when fsr.status = 2 then 1 else 0 end) isReady, "+
                        "                (case when t.grade is not null then 1 else 0 end) isBelongtoClass, fsr.md5Code,  "+
                        "                '"+CommonUtil.APPFILEURL+"/upload/imageTag/zip/76/'+ convert(varchar,fsr.id) + '/'+ replace(replace(fsr.bookName, ' ', '_'), '朗文', 'langwen')+'.zip' downloadUrl "+
                        "        from im_book fsr left join  (     "+
                        "                select distinct co.subject, case when co.cate = 21 then 'A01' when co.cate = 22 then 'A02' else co.grade end grade,"+
                        "                        case when co.cate in (3, 21, 22) or co.grade = 'A04' and co.cate = 7 then 2 else co.cate end cate    "+
                        "                from Edu_Course co inner join Edu_StudentClass esc on co.id = esc.course left join Edu_Curriculum cu on co.currId = cu.id"+
                        "                where co.period > 100 and (cu.courseType is null or cu.courseType = 1) "+
                        "                        and co.status<>-1 and esc.status in (1,3) and esc.student = "+ studentId +" and subject = 'A01' "+
                        "                ) t on fsr.subjectCode = t.subject and t.grade=fsr.gradeCode  and t.cate=fsr.cateCode "+
                        "        where fsr.id in (97, 98, 99, 100, 109, 110, 111) "+
                        "  ) tt order by tt.id ";
			return getListUseSql(sql);
		}
        String sql =  "select distinct fsr.id,fsr.bookName,fsr.pop,fsr.sendtime,fsr.cateCode,fsr.zipSize,(case when fsr.status = 2 then 1 else 0 end) isReady,"
					+ "		(case when a.grade is not null or "+studentId+" = 29674 then 1 else 0 end) isBelongtoClass, fsr.md5Code, "
					+ "		'"+CommonUtil.APPFILEURL+"/upload/imageTag/zip/76/'+ convert(varchar,fsr.id) + '/'+ replace(replace(fsr.bookName, ' ', '_'), '朗文', 'langwen')+'.zip' downloadUrl "
					+ "from im_book fsr left join ("
					+ " 	select distinct co.grade, co.subject, case when co.cate = 3 or co.cate = 9 then '2,'+co.cate else co.cate end cate, case when p.termName like '%秋季' then 'A' when p.termName like '%春季' then 'B' end type " 
					+ " 	from Edu_Course co inner join Edu_StudentClass cls on cls.course=co.id left join Edu_Period p on co.period=p.id " 
					+ " 	where co.status<>-1 and cls.status in (1,3) and (p.termName like '%秋季' or p.termName like '%春季') and student = "+ studentId
                + ") a on a.subject=fsr.subjectCode and a.grade=fsr.gradeCode and charIndex(','+ fsr.cateCode +',', ','+ a.cate +',') > 0 and fsr.bookName like '%'+a.type "
                + " where fsr.cateCode < 100  and fsr.id not in (24,114) "
		        + " union "
			        + "select fsr.id,fsr.bookName,fsr.pop,fsr.sendtime,fsr.cateCode,fsr.zipSize,(case when fsr.status = 2 then 1 else 0 end) isReady, " +
			        "        (case when t.grade is not null or "+studentId+" = 29674 then 1 else 0 end) isBelongtoClass, fsr.md5Code, " +
			        "       '"+CommonUtil.APPFILEURL+"/upload/imageTag/zip/76/'+ convert(varchar,fsr.id) + '/'+ replace(replace(fsr.bookName, ' ', '_'), '朗文', 'langwen')+'.zip' downloadUrl " + 
			        "from im_book fsr left join  (" +
                    "    select distinct co.subject, co.cate,"+
                    "	case when co.grade = 'A01' and (co.cate = 7 or (co.cate = 9 and (ep.termName like '%寒假' or ep.termName like '%春季'))) then 'A02' "+
                    "	     when co.grade = 'A02' and co.cate in (15,1) and (ep.termName like '%寒假' or ep.termName like '%春季') then 'A04'"+
                    "	     when co.grade = 'A02' and co.cate in (9,7) and (ep.termName like '%寒假' or ep.termName like '%春季') then 'A03'"+
                    "	     when co.grade = 'A02' and co.cate in (15,1,7) and (ep.termName like '%暑假' or ep.termName like '%秋季') then 'A03'"+
                    "	     when co.grade = 'A03' and co.cate in (15,1) and (ep.termName like '%寒假' or ep.termName like '%春季') then 'A05'"+
                    "	     when co.grade = 'A03' and co.cate in (9,3,7) and (ep.termName like '%寒假' or ep.termName like '%春季') then 'A04'"+
                    "	     when co.grade = 'A03' and co.cate in (15,1,7,3) and (ep.termName like '%暑假' or ep.termName like '%秋季') then 'A04'"+
                    "	     when co.grade = 'A04' and co.cate = 9 and (ep.termName like '%暑假' or ep.termName like '%秋季') then 'A03'"+
                    "	     when co.grade = 'A04' and co.cate in (15,1,7,3) and (ep.termName like '%寒假' or ep.termName like '%春季') then 'A05'"+
                    "	     when co.grade = 'A05' and (ep.termName like '%暑假' or ep.termName like '%秋季') then 'A04' else co.grade end grade"+
                    "    from Edu_Course co inner join Edu_StudentClass esc on co.id = esc.course left join Edu_Period ep on ep.id = co.period"+
                    "    where co.period >= 100 and co.status<>-1 and esc.status in (1,3) and esc.student = "+ studentId +
                    ") t on fsr.subjectCode = t.subject and t.grade = fsr.gradeCode and charIndex(','+ t.cate +',', ','+ fsr.displayCate +',') > 0"+
                "where fsr.cateCode > 200 order by fsr.id ";
		// 2019-01-17 号之前规则
//        String sql =  "select distinct fsr.id,fsr.bookName,fsr.pop,fsr.sendtime,fsr.cateCode,fsr.zipSize,(case fsr.status when 2 then 1 else 0 end) isReady,"
//                + "		(case when a.grade is not null then 1 else 0 end) isBelongtoClass, fsr.md5Code, "
//                + "		'"+CommonUtil.APPFILEURL+"/upload/imageTag/zip/76/'+ convert(varchar,fsr.id) + '/'+ replace(replace(fsr.bookName, ' ', '_'), '朗文', 'langwen')+'.zip' downloadUrl "
//                + "from im_book fsr left join ("
//                + " 	select distinct co.grade, co.subject, case when co.cate = 3 or co.cate = 9 then '2,'+co.cate else co.cate end cate, case when p.termName like '%秋季' then 'A' when p.termName like '%春季' then 'B' end type "
//                + " 	from Edu_Course co inner join Edu_StudentClass cls on cls.course=co.id left join Edu_Period p on co.period=p.id "
//                + " 	where co.status<>-1 and cls.status in (1,3) and (p.termName like '%秋季' or p.termName like '%春季') and student = "+ studentId
//                + ") a on a.subject=fsr.subjectCode and a.grade=fsr.gradeCode and charIndex(','+ fsr.cateCode +',', ','+ a.cate +',') > 0 and fsr.bookName like '%'+a.type "
//                + " where fsr.cateCode < 100 "
//                + " union "
//                + "select fsr.id,fsr.bookName,fsr.pop,fsr.sendtime,fsr.cateCode,fsr.zipSize,(case fsr.status when 2 then 1 else 0 end) isReady, " +
//                "        (case when t.grade is not null or "+studentId+" = 29674 then 1 else 0 end) isBelongtoClass, fsr.md5Code, " +
//                "       '"+CommonUtil.APPFILEURL+"/upload/imageTag/zip/76/'+ convert(varchar,fsr.id) + '/'+ replace(replace(fsr.bookName, ' ', '_'), '朗文', 'langwen')+'.zip' downloadUrl " +
//                "from im_book fsr left join  (" +
//                "     select distinct co.grade, co.subject, co.cate from Edu_Course co inner join Edu_StudentClass esc on co.id = esc.course " +
//                "     where co.period >= 100 and co.status<>-1 and esc.status in (1,3) and esc.student = " + studentId +
//                ") t on fsr.subjectCode = t.subject and charIndex(','+ t.grade +',', ','+ fsr.displayGrade +',') > 0 and charIndex(','+ t.cate +',', ','+ fsr.displayCate +',') > 0 " +
////			        "where fsr.cateCode > 200 and fsr.gradeCode <> 'A05' and fsr.id <> 148 order by fsr.id ";
//                "where fsr.cateCode > 200 order by fsr.id ";
		return getListUseSql(sql);
	}
	private static final  String  beseUrl="http://seesfile.skyedu99.com";
	//private static final  String  beseUrl="http://192.168.40.9:8989";
	private String getSqlSz(int studentId,Integer cateCode) {

        String sql = " select *\n" +
                "from (select distinct fsr.id,\n" +
                "                fsr.bookName,\n" +
                "                fsr.pop,\n" +
                "               isnull((select top 1 TsBJRt.createTime from Tk_sendBookJsonRecord_t TsBJRt where TsBJRt.bookId=fsr.id order by TsBJRt.createTime desc),fsr.sendtime)  as modifyJsonTime,\n" +
                "                fsr.sendtime,\n" +
                "                fsr.cateCode,\n" +
                "                fsr.zipSize,\n" +
                "                (case when fsr.status = 2 then 1 else 0 end)                       isReady,\n" +
                "                (case when a.type is not null or " + studentId + " = 29674 or " + studentId + "=269901  then 1 else 0 end) isBelongtoClass,\n" +
                "                fsr.md5Code,\n" +
                "'"+beseUrl+ "/upload/imageTag/zip/76/' + convert(varchar, fsr.id) + '/' +\n" +
                "                replace(replace(fsr.bookName, ' ', '_'), '朗文', 'langwen') + '.zip' downloadUrl\n" +
                "from im_book fsr\n" +
                "       left join (select\n" +
                "                    case\n" +
                "                      --一年级\n" +
                "                      when co.grade = 'A01' and co.cate = 7 and\n" +
                "                           (ep.termName like '%暑假' or ep.termName like '%秋季') then 'LEAP-1A'\n" +
                "                      when co.grade = 'A01' and co.cate = 7 and\n" +
                "                           (ep.termName like '%寒假' or ep.termName like '%春季') then 'LEAP-1B'\n" +
                "                      when co.grade = 'A01' and co.cate = 15 and\n" +
                "                           (ep.termName like '%暑假' or ep.termName like '%秋季') then 'PLE2-1A'\n" +
                "                      when co.grade = 'A01' and co.cate = 15 and\n" +
                "                           (ep.termName like '%寒假' or ep.termName like '%春季') then 'PLE2-1B'\n" +
                "                      --二年级\n" +
                "                      when co.grade = 'A02' and co.cate = 3 and\n" +
                "                           (ep.termName like '%暑假' or ep.termName like '%秋季') then 'LEW-2A'\n" +
                "                      when co.grade = 'A02' and co.cate = 3 and\n" +
                "                           (ep.termName like '%寒假' or ep.termName like '%春季') then 'LEW-2B'\n" +
                "                      when co.grade = 'A02' and co.cate = 7 and\n" +
                "                           (ep.termName like '%暑假' or ep.termName like '%秋季') then 'LEAP-2A'\n" +
                "                      when co.grade = 'A02' and co.cate = 7 and\n" +
                "                           (ep.termName like '%寒假' or ep.termName like '%春季') then 'LEAP-2B'\n" +
                "                      when co.grade = 'A02' and co.cate in (1, 15) and\n" +
                "                           (ep.termName like '%暑假' or ep.termName like '%秋季') then 'PLE2-2A'\n" +
                "                      when co.grade = 'A02' and co.cate in (1, 15) and\n" +
                "                           (ep.termName like '%寒假' or ep.termName like '%春季') then 'PLE2-2B'\n" +
                "                      --三年级\n" +
                "                      when co.grade = 'A03' and co.cate = 3 and\n" +
                "                           (ep.termName like '%暑假' or ep.termName like '%秋季') then 'LEW-3A'\n" +
                "                      when co.grade = 'A03' and co.cate = 3 and\n" +
                "                           (ep.termName like '%寒假' or ep.termName like '%春季') then 'LEW-3B'\n" +
                "                      when co.grade = 'A03' and co.cate = 7 and\n" +
                "                           (ep.termName like '%暑假' or ep.termName like '%秋季') then 'LEAP-3A'\n" +
                "                      when co.grade = 'A03' and co.cate = 7 and\n" +
                "                           (ep.termName like '%寒假' or ep.termName like '%春季') then 'LEAP-3B'\n" +
                "                      when co.grade = 'A03' and co.cate in (1, 15) and\n" +
                "                           (ep.termName like '%暑假' or ep.termName like '%秋季') then 'PLE2-3A'\n" +
                "                      when co.grade = 'A03' and co.cate in (1, 15) and\n" +
                "                           (ep.termName like '%寒假' or ep.termName like '%春季') then 'PLE2-3B'\n" +
                "                      -- 四年级\n" +
                "                      when co.grade = 'A04' and co.cate = 3 and\n" +
                "                           (ep.termName like '%暑假' or ep.termName like '%秋季') then 'LEW-4A'\n" +
                "                      when co.grade = 'A04' and co.cate = 3 and\n" +
                "                           (ep.termName like '%寒假' or ep.termName like '%春季') then 'LEW-4B'\n" +
                "                      when co.grade = 'A04' and co.cate = 7 and\n" +
                "                           (ep.termName like '%暑假' or ep.termName like '%秋季') then 'LEAP-4A'\n" +
                "                      when co.grade = 'A04' and co.cate = 7 and\n" +
                "                           (ep.termName like '%寒假' or ep.termName like '%春季') then 'LEAP-4B'\n" +
                "                      when co.grade = 'A04' and co.cate in (1, 15) and\n" +
                "                           (ep.termName like '%暑假' or ep.termName like '%秋季') then 'PLE2-4A'\n" +
                "                      when co.grade = 'A04' and co.cate in (1, 15) and\n" +
                "                           (ep.termName like '%寒假' or ep.termName like '%春季') then 'PLE2-4B'\n" +
                "                      -- 五年级\n" +
                "                      when co.grade = 'A05' and co.cate = 3 and\n" +
                "                           (ep.termName like '%暑假' or ep.termName like '%秋季') then 'LEW-5A'\n" +
                "                      when co.grade = 'A05' and co.cate = 3 and\n" +
                "                           (ep.termName like '%寒假' or ep.termName like '%春季') then 'LEW-5B'\n" +
                "                      when co.grade = 'A05' and co.cate = 7 and\n" +
                "                           (ep.termName like '%暑假' or ep.termName like '%秋季') then 'LEAP-5A'\n" +
                "                      when co.grade = 'A05' and co.cate = 7 and\n" +
                "                           (ep.termName like '%寒假' or ep.termName like '%春季') then 'LEAP-5B'\n" +
                "                      when co.grade = 'A05' and co.cate in (1, 15) and\n" +
                "                           (ep.termName like '%暑假' or ep.termName like '%秋季') then 'PLE2-5A'\n" +
                "                      when co.grade = 'A05' and co.cate in (1, 15) and\n" +
                "                           (ep.termName like '%寒假' or ep.termName like '%春季') then 'PLE2-5B'\n" +
                "                      else null end as type\n" +
                "                  from Edu_Course co\n" +
                "                         inner join Edu_StudentClass cls on cls.course = co.id\n" +
                "                         left join Edu_Period ep on co.period = ep.id\n" +
                "                  where co.status <> -1\n" +
                "                    and cls.status in (1, 3)\n" +
                "                    and student = " + studentId + "\n" +
                "                    and co.subject = 'A01'\n" +
                "                    and co.cate <> 9) a on fsr.bookName = a.type\n" +
                "where fsr.cateCode < 100 and fsr.id not in (24, 114) ";
        if (!org.springframework.util.StringUtils.isEmpty(cateCode)) {
            sql += " and fsr.cateCode=" + cateCode + "\n";
        }
        //迫于产品
        if(studentId==29674){
            sql +="union select  fsr.id,\n" +
                    "        fsr.bookName,\n" +
                    "        fsr.pop,\n" +
                    "        isnull((select top 1 TsBJRt.createTime from Tk_sendBookJsonRecord_t TsBJRt where TsBJRt.bookId=fsr.id order by TsBJRt.createTime desc),fsr.sendtime) as modifyJsonTime,\n" +
                    "        fsr.sendtime,\n" +
                    "        fsr.cateCode,\n" +
                    "        fsr.zipSize,\n" +
                    "        1                      isReady,\n" +
                    "        1 isBelongtoClass,\n" +
                    "        fsr.md5Code,\n" +
                    "        '"+ beseUrl+"/upload/imageTag/zip/76/' + convert(varchar, fsr.id) + '/' +\n" +
                    "        replace(replace(fsr.bookName, ' ', '_'), '朗文', 'langwen') + '.zip' downloadUrl\n" +
                    "from IM_Book fsr\n" +
                    "where fsr.id in (198,\n" +
                    "             199,\n" +
                    "             200\n" +
                    "  )";
        }
        sql += "union\n" +
                "select fsr.id,\n" +
                "       fsr.bookName,\n" +
                "       fsr.pop,\n" +
                "       isnull((select top 1 TsBJRt.createTime from Tk_sendBookJsonRecord_t TsBJRt where TsBJRt.bookId=fsr.id order by TsBJRt.createTime desc),fsr.sendtime) as modifyJsonTime,\n" +
                "       fsr.sendtime,\n" +
                "       fsr.cateCode,\n" +
                "       fsr.zipSize,\n" +
                "       (case when fsr.status = 2 then 1 else 0 end)                       isReady,\n" +
                "       (case when t.grade is not null or " + studentId + " = 29674 or " + studentId + "=269901  then 1 else 0 end) isBelongtoClass,\n" +
                "       fsr.md5Code,\n" +
                "'"+ beseUrl+"/upload/imageTag/zip/76/' + convert(varchar, fsr.id) + '/' +\n" +
                "       replace(replace(fsr.bookName, ' ', '_'), '朗文', 'langwen') + '.zip' downloadUrl\n" +
                "from im_book fsr\n" +
                "       left join (select distinct co.subject,\n" +
                "                                  case\n" +
                "                                    --一年级\n" +
                "                                    when co.grade = 'A01' and (ep.termName like '%暑假' or ep.termName like '%秋季') and\n" +
                "                                         (co.cate = 9) then 'A01'\n" +
                "                                    when co.grade = 'A01' and co.cate = 7 and\n" +
                "                                         (ep.termName like '%寒假' or ep.termName like '%春季') then 'A01'\n" +
                "                                    when co.grade = 'A01' and co.cate = 9 and\n" +
                "                                         (ep.termName like '%寒假' or ep.termName like '%春季') then 'A02'\n" +
                "                                    when co.grade = 'A01' and co.cate = 7                       then 'A02'\n" +
                "                                    when co.grade = 'A01' and co.cate = 15 and\n" +
                "                                         (ep.termName like '%寒假' or ep.termName like '%春季') then 'A04'\n" +
                "                                    when co.grade = 'A01' and co.cate = 15 and\n" +
                "                                         (ep.termName like '%暑假' or ep.termName like '%秋季') then 'A03'\n" +
                "                                    --二年级\n" +
                "                                    when co.grade = 'A02' and co.cate =9 and\n" +
                "                                         (ep.termName like '%暑假' or ep.termName like '%秋季') then 'A02'\n" +
                "                                    when co.grade = 'A02' and co.cate in (3, 7) and\n" +
                "                                         (ep.termName like '%暑假' or ep.termName like '%秋季') then 'A03'\n" +
                "                                    when co.grade = 'A02' and co.cate in (9, 3, 7) and\n" +
                "                                         (ep.termName like '%寒假' or ep.termName like '%春季') then 'A03'\n" +
                "                                    when co.grade = 'A02' and co.cate = 1 then 'A03'\n" +
                "                                    when co.grade = 'A02' and co.cate = 15 and\n" +
                "                                         (ep.termName like '%寒假' or ep.termName like '%春季') then 'A03'\n" +
                "                                    when co.grade = 'A02' and co.cate = 15 and\n" +
                "                                         (ep.termName like '%暑假' or ep.termName like '%秋季') then 'A04'\n" +
                "\n" +
                "\n" +
                "                                    -- 三年级\n" +
                "                                    when co.grade = 'A03' and co.cate =9 and\n" +
                "                                         (ep.termName like '%暑假' or ep.termName like '%秋季') then 'A03'\n" +
                "                                    when co.grade = 'A03' and co.cate =9  and\n" +
                "                                         (ep.termName like '%寒假' or ep.termName like '%春季') then 'A04'\n" +
                "                                    when co.grade = 'A03' and co.cate in (1,7,3)                then 'A04'\n" +
                "                                    when co.grade = 'A03' and co.cate = 15 and\n" +
                "                                         (ep.termName like '%暑假' or ep.termName like '%秋季') then 'A04'\n" +
                "                                    when co.grade = 'A03' and co.cate = 15 and\n" +
                "                                         (ep.termName like '%寒假' or ep.termName like '%春季') then 'A05'\n" +
                "\n" +
                "\n" +
                "                                    --四年级\n" +
                "                                    when co.grade = 'A04' and co.cate = 9 and\n" +
                "                                         (ep.termName like '%暑假' or ep.termName like '%秋季') then 'A03'\n" +
                "                                    when co.grade = 'A04' and co.cate = 9 and\n" +
                "                                         (ep.termName like '%寒假' or ep.termName like '%春季') then 'A04'\n" +
                "                                    when co.grade = 'A04' and co.cate in (1,3,7)                then 'A05'\n" +
                "                                    when co.grade = 'A04' and co.cate = 15 and\n" +
                "                                         (ep.termName like '%暑假' or ep.termName like '%秋季') then 'A05'\n" +
                "                                    when co.grade = 'A04' and co.cate = 15 and\n" +
                "                                         (ep.termName like '%寒假' or ep.termName like '%春季') then 'A06'\n" +
                "\n" +
                "\n" +
                "                                    --五年级\n" +
                "                                    when co.grade = 'A05' and co.cate = 9 and\n" +
                "                                         (ep.termName like '%暑假' or ep.termName like '%秋季') then 'A04'\n" +
                "                                    when co.grade = 'A05' and co.cate = 9 and\n" +
                "                                         (ep.termName like '%寒假' or ep.termName like '%春季') then 'A05'\n" +
                "\n" +
                "                                    when co.grade = 'A05' and co.cate in (7, 3) and\n" +
                "                                         (ep.termName like '%暑假' or ep.termName like '%秋季') then 'A05'\n" +
                "                                    when co.grade = 'A05' and co.cate in (7, 3) and\n" +
                "                                         (ep.termName like '%寒假' or ep.termName like '%春季') then 'A06'\n" +
                "                                    when co.grade = 'A05' and co.cate in (1, 15) then 'A06'\n" +
                "                                    else co.grade end grade,\n" +

                "                                       case when co.grade = 'A02' and co.cate =9 and\n" +
                "                                            (ep.termName like '%暑假' or ep.termName like '%秋季') then 129\n" +
                "                                           when co.grade in ('A03','A04') and co.cate = 9 and\n" +
                "                                                (ep.termName like '%暑假' or ep.termName like '%秋季') then 134" +

                "                                    else 0 end  id " +
                "                 from Edu_Course co\n" +
                "                         inner join Edu_StudentClass esc on co.id = esc.course\n" +
                "                         left join Edu_Curriculum ec on co.currId=ec.id\n" +
                "                         left join Edu_Period ep on ep.id = co.period\n" +
                "                  where co.period >= 100\n" +
                "                    and co.status in (1, 3)\n" +
                "                    and esc.status in (1, 3)\n" +
                "                    and ec.courseType !=2\n" +
                "                    and esc.student = " + studentId + "\n" +
                "                    and co.subject = 'A01') t on fsr.subjectCode = t.subject and (t.grade = fsr.gradeCode or t.id=fsr.id)\n" +
                "where fsr.cateCode > 200";
        if (!org.springframework.util.StringUtils.isEmpty(cateCode)) {
            sql += " and fsr.cateCode=" + cateCode;
        }
        sql += "    ) tt\n" +
                "where tt.isBelongtoClass = 1\n" +
                "order by tt.id ";
        return sql;
    }

    /**
     *
     * @param studentId
     * @param cateCode
     * @return
     */
    private String getNewSqlHz(int studentId,Integer cateCode){
        int id = 263898;
        String sql="select * from ( select fsr.id,\n" +
                "       fsr.bookName,\n" +
                "       fsr.pop,\n" +
                "       isnull((select top 1 TsBJRt.createTime\n" +
                "              from Tk_sendBookJsonRecord_t TsBJRt\n" +
                "              where TsBJRt.bookId = fsr.id\n" +
                "              order by TsBJRt.createTime desc), fsr.sendtime) as             modifyJsonTime,\n" +
                "       fsr.sendtime,\n" +
                "       2                                                                     cateCode,\n" +
                "       fsr.zipSize,\n" +
                "       (case when fsr.status = 2 then 1 else 0 end)                         isReady,\n" +
                "       (case when t.bookId is not null or "+id+" = "+studentId+" then 1 else 0 end) isBelongtoClass,\n" +
                "       fsr.md5Code,\n" +
                "       '"+beseUrl+"/upload/imageTag/zip/76/' + convert(varchar, fsr.id) + '/' +\n" +
                "       replace(replace(fsr.bookName, ' ', '_'), '朗文', 'langwen') + '.zip'   downloadUrl\n" +
                "from im_book fsr\n" +
                "       left join(select col as bookId\n" +
                "                 from SplitIn((select br.bookId\n" +
                "                               from (select distinct co.grade,\n" +
                "                                                     co.cate,\n" +
                "                                                     case\n" +
                "                                                       when SUBSTRING(p.termName, 5, 2) = '暑假' then '秋季'\n" +
                "                                                       when SUBSTRING(p.termName, 5, 2) = '寒假' then '春季'\n" +
                "                                                       else SUBSTRING(p.termName, 5, 2) end as termName\n" +
                "                                     from Edu_Course co\n" +
                "                                            inner join Edu_StudentClass esc on co.id = esc.course\n" +
                "                                            left join Edu_Curriculum cu on co.currId = cu.id\n" +
                "                                            left join Edu_Period p on p.id = co.period\n" +
                "                                     where co.period > 100\n" +
                "                                       and (cu.courseType is null or cu.courseType = 1)\n" +
                "                                       and co.status = 1\n" +
                "                                       and esc.status in (1, 3)\n" +
                "                                       and esc.student = "+studentId+"\n" +
                "                                       and subject = 'A01') t\n" +
                "                                      left join TK_book_rule_t br\n" +
                "                                                on br.displayCate = t.cate and br.displayGrade = t.grade and\n" +
                "                                                   br.displayPeriod = t.termName and br.cityId = 2 and type = 2), ',')) t\n" +
                "                 on fsr.bookName in(t.bookId)\n" +
                "where fsr.id in (97, 98, 99, 100, 101, 109, 110, 111, 112, 113, 7, 8, 9, 10, 11, 19, 20, 21, 22, 23)";
        if (!org.springframework.util.StringUtils.isEmpty(cateCode)) {
            if(cateCode==2){
                sql += " and fsr.cateCode in(" + cateCode+",7"+")";
            }else {
                sql += " and fsr.cateCode=(" + cateCode + ")";
            }
        }
        sql+="union select fsr.id,\n" +
                "       fsr.bookName,\n" +
                "       fsr.pop,\n" +
                "       isnull((select top 1 TsBJRt.createTime\n" +
                "              from Tk_sendBookJsonRecord_t TsBJRt\n" +
                "              where TsBJRt.bookId = fsr.id\n" +
                "              order by TsBJRt.createTime desc), fsr.sendtime) as             modifyJsonTime,\n" +
                "       fsr.sendtime,\n" +
                "       fsr.cateCode                                                                     cateCode,\n" +
                "       fsr.zipSize,\n" +
                "       (case when fsr.status = 2 then 1 else 0 end)                         isReady,\n" +
                "       (case when t.level is not null or "+id+" = "+studentId+" then 1 else 0 end) isBelongtoClass,\n" +
                "       fsr.md5Code,\n" +
                "       '"+beseUrl+"/upload/imageTag/zip/76/' + convert(varchar, fsr.id) + '/' +\n" +
                "       replace(replace(fsr.bookName, ' ', '_'), '朗文', 'langwen') + '.zip'   downloadUrl\n" +
                "from im_book fsr\n" +
                " left join (select col as level\n" +
                "                  from Splitin((select br.level\n" +
                "                                from (select distinct co.grade,\n" +
                "                                                      co.cate,\n" +
                "                                                      case\n" +
                "                                                        when SUBSTRING(p.termName, 5, 2) = '暑假' then '秋季'\n" +
                "                                                        when SUBSTRING(p.termName, 5, 2) = '寒假' then '春季'\n" +
                "                                                        else SUBSTRING(p.termName, 5, 2) end as termName\n" +
                "                                      from Edu_Course co\n" +
                "                                             inner join Edu_StudentClass esc on co.id = esc.course\n" +
                "                                             left join Edu_Curriculum cu on co.currId = cu.id\n" +
                "                                             left join Edu_Period p on p.id = co.period\n" +
                "                                      where co.period > 100\n" +
                "                                        and (cu.courseType is null or cu.courseType = 1)\n" +
                "                                        and co.status = 1\n" +
                "                                        and esc.status in (1, 3)\n" +
                "                                        and esc.student = "+studentId+"\n" +
                "                                        and subject = 'A01') t\n" +
                "                                       left join TK_book_rule_t br on br.displayGrade = t.grade\n" +
                "                                  and br.cityId = 2 and br.type = 0), ','))  t\n" +
                "                 on t.level = fsr.gradeCode\n" +
                "where fsr.cateCode between 100 and 200 ";

        if (!org.springframework.util.StringUtils.isEmpty(cateCode)) {
            sql += " and fsr.cateCode=" + cateCode;
        }
        sql+=" union select fsr.id,\n" +
                "       fsr.bookName,\n" +
                "       fsr.pop,\n" +
                "       isnull((select top 1 TsBJRt.createTime\n" +
                "              from Tk_sendBookJsonRecord_t TsBJRt\n" +
                "              where TsBJRt.bookId = fsr.id\n" +
                "              order by TsBJRt.createTime desc), fsr.sendtime) as             modifyJsonTime,\n" +
                "       fsr.sendtime,\n" +
                "      fsr.cateCode                                                                     cateCode,\n" +
                "       fsr.zipSize,\n" +
                "       (case when fsr.status = 2 then 1 else 0 end)                         isReady,\n" +
                "       (case when t.bookId is not null or "+id+" = "+studentId+" then 1 else 0 end) isBelongtoClass,\n" +
                "       fsr.md5Code,\n" +
                "       '"+beseUrl+"/upload/imageTag/zip/76/' + convert(varchar, fsr.id) + '/' +\n" +
                "       replace(replace(fsr.bookName, ' ', '_'), '朗文', 'langwen') + '.zip'   downloadUrl\n" +
                "from im_book fsr\n" +
                "       left join(select col as bookId\n" +
                "                 from SplitIn((select br.bookId\n" +
                "                               from (select distinct co.grade,\n" +
                "                                                     co.cate,\n" +
                "                                                     case\n" +
                "                                                       when SUBSTRING(p.termName, 5, 2) = '暑假' then '秋季'\n" +
                "                                                       when SUBSTRING(p.termName, 5, 2) = '寒假' then '春季'\n" +
                "                                                       else SUBSTRING(p.termName, 5, 2) end as termName\n" +
                "                                     from Edu_Course co\n" +
                "                                            inner join Edu_StudentClass esc on co.id = esc.course\n" +
                "                                            left join Edu_Curriculum cu on co.currId = cu.id\n" +
                "                                            left join Edu_Period p on p.id = co.period\n" +
                "                                     where co.period > 100\n" +
                "                                       and (cu.courseType is null or cu.courseType = 1)\n" +
                "                                       and co.status = 1\n" +
                "                                       and esc.status in (1, 3)\n" +
                "                                       and esc.student = "+studentId+"\n" +
                "                                       and subject = 'A01') t\n" +
                "                                      left join TK_book_rule_t br\n" +
                "                                                on br.displayCate = t.cate and br.displayGrade = t.grade and\n" +
                "                                                   br.displayPeriod = t.termName and br.cityId = 2 and type = 1), ',')) t\n" +
                "                 on fsr.id in(t.bookId)\n" +
                "where fsr.cateCode between 100 and 200\n";
        if (!org.springframework.util.StringUtils.isEmpty(cateCode)) {
            sql += " and fsr.cateCode=" + cateCode;
        }
        sql += " ) tt where tt.isBelongtoClass=1";
        return sql;

    }

    /**
     *
     *     LEW-1A,LEW-1B,LEW-2A,LEW-2B,LEW-3A,LEW-3B,LEW-4A,LEW-4B,LEW-5A,LEW-5B
     *     LEAP-1A,LEAP-1B,LEAP-2A,LEAP-2B,LEAP-3A,LEAP-3B,LEAP-4A,LEAP-4B,LEAP-5A,LEAP-5B
     * @see ImageTagDaoImpl#getNewSqlHz(int, Integer)
     * @param studentId
     * @param cateCode
     * @return
     */
    @Deprecated
    private String getSqlHz(int studentId,Integer cateCode) {
        int id = 263898;
        String sql = "select *\n" +
                "from (select fsr.id,\n" +
                "             fsr.bookName,\n" +
                "             fsr.pop,\n" +
                "             isnull((select top 1 TsBJRt.createTime from Tk_sendBookJsonRecord_t TsBJRt where TsBJRt.bookId=fsr.id order by TsBJRt.createTime desc),fsr.sendtime) as modifyJsonTime,\n" +
                "             fsr.sendtime,\n" +
                "             fsr.cateCode,\n" +
                "             fsr.zipSize,\n" +
                "             (case when fsr.status = 2 then 1 else 0 end)                       isReady,\n" +
                "             (case when t.grade is not null or " + studentId + "=" + id + "  then 1 else 0 end)                  isBelongtoClass,\n" +
                "             fsr.md5Code,\n" +
                "'"+beseUrl+"/upload/imageTag/zip/76/' + convert(varchar, fsr.id) + '/' +\n" +
                "             replace(replace(fsr.bookName, ' ', '_'), '朗文', 'langwen') + '.zip' downloadUrl\n" +
                "      from im_book fsr\n" +
                "             left join TK_book_status_t bs on bs.bookId = fsr.id and bs.cityId = 2\n" +
                "             left join (select distinct co.grade                               as grade,\n" +
                "                                        co.subject                             as subject,\n" +
                "                                        co.cate                                as cate,\n" +
                "                                        case\n" +
                "                                          when SUBSTRING(p.termName, 5, 2) = '暑假' then '秋季'\n" +
                "                                          when SUBSTRING(p.termName, 5, 2) = '寒假' then '春季'\n" +
                "                                          else SUBSTRING(p.termName, 5, 2) end as termName,\n" +
                "   case\n" +
                "                                          when '263971,263973,265427,272346' like '%"+studentId+"%' then 0\n" +
                "                                          when '220219,230134,241873,250877' like '%"+studentId+"%'  then 0\n" +
                "                                          else 0 end                           as id                  " +
                "     from Edu_Course co\n" +
                "                               inner join Edu_StudentClass esc\n" +
                "                                          on co.id = esc.course\n" +
                "                               left join Edu_Curriculum cu on co.currId = cu.id\n" +
                "                               left join Edu_Period p on p.id = co.period\n" +
                "                        where co.period\n" +
                "                          > 100\n" +
                "                          and (cu.courseType is null or cu.courseType = 1)\n" +
                "                          and co.status = 1\n" +
                "                          and esc.status in (1, 3)\n" +
                "                          and esc.student = " + studentId + "\n" +
                "                          and subject = 'A01' union\n" +
                "(select top 1 '0'                             as grade,\n" +
                "                 'A01'                             as subject,\n" +
                "                 '0'                              as cate,\n" +
                "                '0' as termName,\n" +
                "                 case\n" +
                "                   when '263971,263973,265427,272346,263989,263990,272332' like '%"+studentId+"%' then 161\n" +
                "                   when '220219,230134,241873,250877,221650,264136' like '%"+studentId+"%' then 166\n" +
                "                   else 0 end                           as id\n" +
                " from Edu_Course co)";
        //需求
        if (studentId == 200549) {
            sql += " union  select distinct co.grade                               as grade,\n" +
                    "                                        co.subject                             as subject,\n" +
                    "                                        co.cate                                as cate,\n" +
                    "                                        case\n" +
                    "                                          when SUBSTRING(p.termName, 5, 2) = '暑假' then '秋季'\n" +
                    "                                          when SUBSTRING(p.termName, 5, 2) = '寒假' then '春季'\n" +
                    "                                          else SUBSTRING(p.termName, 5, 2) end as termName, 0 as id\n" +
                    "                        from Edu_Course co\n" +
                    "                               inner join Edu_StudentClass esc\n" +
                    "                                          on co.id = esc.course\n" +
                    "                               left join Edu_Curriculum cu on co.currId = cu.id\n" +
                    "                               left join Edu_Period p on p.id = co.period\n" +
                    "                        where co.period\n" +
                    "                          > 100\n" +
                    "                          and (cu.courseType is null or cu.courseType = 1)\n" +
                    "                          and co.status = 1\n" +
                    "                          and esc.status in (1, 3)\n" +
                    "                          and esc.student = 200550\n" +
                    "                          and subject = 'A01'";
        }
        sql += ") t on fsr.subjectCode = t.subject and (\n" +
                "                                                    charIndex(',' + t.grade + ',', ',' + bs.displayGrade + ',') > 0 and\n" +
                "                                                    charIndex(',' + t.cate + ',', ',' + bs.displayCate + ',') > 0 and\n" +
                "                                                    charIndex(',' + t.termName + ',', ',' + bs.displayPeriod + ',') > 0 or t.id = fsr.id)\n" +
                "      where fsr.showStatus = 1\n" +
                "        and fsr.cateCode between 100 and 200 \n";
        if (!org.springframework.util.StringUtils.isEmpty(cateCode)) {
            sql += "and fsr.cateCode=" + cateCode;
        }
        sql +=  "      union\n" +
                "      select fsr.id,\n" +
                "             fsr.bookName,\n" +
                "             fsr.pop,\n" +
                "             isnull((select top 1 TsBJRt.createTime from Tk_sendBookJsonRecord_t TsBJRt where TsBJRt.bookId=fsr.id order by TsBJRt.createTime desc),fsr.sendtime) as modifyJsonTime,\n" +
                "             fsr.sendtime,\n" +
                "             2                                                                  cateCode,\n" +
                "             fsr.zipSize,\n" +
                "             (case when fsr.status = 2 then 1 else 0 end)                       isReady,\n" +
                "             (case when t.grade is not null or " + studentId + "=" + id + "  then 1 else 0 end)                  isBelongtoClass,\n" +
                "             fsr.md5Code,\n" +
                "'"+beseUrl+"/upload/imageTag/zip/76/' + convert(varchar, fsr.id) + '/' +\n" +
                "             replace(replace(fsr.bookName, ' ', '_'), '朗文', 'langwen') + '.zip' downloadUrl\n" +
                "      from im_book fsr\n" +
                "             left join TK_book_status_t bs on bs.bookId = fsr.id and bs.cityId = 2\n" +
                "             left join (select distinct case\n" +
                "                                          when (select count(*)\n" +
                "                                                from Edu_Course co\n" +
                "                                                       inner join Edu_StudentClass esc on co.id = esc.course\n" +
                "                                                where co.name = 'LEW4B（P5）PFJ1'\n" +
                "                                                  and esc.student = " + studentId + ") > 0 then 'A04'\n" +
                "                                          else co.grade end                     as grade,\n" +
                "                                        co.subject                             as subject,\n" +
                "                                        case\n" +
                "                                          when (select count(*)\n" +
                "                                                from Edu_Course co\n" +
                "                                                       inner join Edu_StudentClass esc on co.id = esc.course\n" +
                "                                                where co.name = 'LEW5 (P5）PFJ1'\n" +
                "                                                  and esc.student = " + studentId + ") > 0 then '3'\n" +
                "                                          else co.cate end                               as cate,\n" +
                "                                        case\n" +
                " when (select count(*)\n" +
                "                                                from Edu_Course co\n" +
                "                                                       inner join Edu_StudentClass esc on co.id = esc.course\n" +
                "                                                where co.name = 'LEW4B（P5）PFJ1'\n" +
                "                                                  and esc.student = " + studentId + ") > 0 then '春季'\n" +
                "                                          when SUBSTRING(p.termName, 5, 2) = '暑假' then '秋季'\n" +
                "                                          when SUBSTRING(p.termName, 5, 2) = '寒假' then '春季'\n" +
                "                                          else SUBSTRING(p.termName, 5, 2) end as termName\n" +
                "                        from Edu_Course co\n" +
                "                               inner join Edu_StudentClass esc on co.id = esc.course\n" +
                "                               left join Edu_Curriculum cu on co.currId = cu.id\n" +
                "                               left join Edu_Period p on p.id = co.period\n" +
                "                        where co.period > 100\n" +
                "                          and (cu.courseType is null or cu.courseType = 1)\n" +
                "                          and co.status = 1\n" +
                "                          and esc.status in (1, 3)\n" +
                "                          and esc.student = " + studentId + "\n" +
                "                          and subject = 'A01'";
        //这是需求
        if (studentId == 200549) {
            sql += " union select distinct case\n" +
                    "                                                                                               when (select count(*)\n" +
                    "                                                                                                     from Edu_Course co\n" +
                    "                                                                                                            inner join Edu_StudentClass esc on co.id = esc.course\n" +
                    "                                                                                                     where co.name = 'LEW4B（P5）PFJ1'\n" +
                    "                                                                                                       and esc.student = 200549) > 0 then 'A04'\n" +
                    "                                                                                               else co.grade end                     as grade,\n" +
                    "                                                                     co.subject                             as subject,\n" +
                    "                                                                     case\n" +
                    "                                                                       when (select count(*)\n" +
                    "                                                                             from Edu_Course co\n" +
                    "                                                                                    inner join Edu_StudentClass esc on co.id = esc.course\n" +
                    "                                                                             where co.name = 'LEW5 (P5）PFJ1'\n" +
                    "                                                                               and esc.student = 200549) > 0 then '3'\n" +
                    "                                                                       else co.cate end                               as cate,\n" +
                    "                                                                     case\n" +
                    "                                                                       when (select count(*)\n" +
                    "                                                                             from Edu_Course co\n" +
                    "                                                                                    inner join Edu_StudentClass esc on co.id = esc.course\n" +
                    "                                                                             where co.name = 'LEW4B（P5）PFJ1'\n" +
                    "                                                                               and esc.student = 200549) > 0 then '春季'\n" +
                    "                                                                       when SUBSTRING(p.termName, 5, 2) = '暑假' then '秋季'\n" +
                    "                                                                       when SUBSTRING(p.termName, 5, 2) = '寒假' then '春季'\n" +
                    "                                                                       else SUBSTRING(p.termName, 5, 2) end as termName\n" +
                    "                        from Edu_Course co\n" +
                    "                               inner join Edu_StudentClass esc on co.id = esc.course\n" +
                    "                               left join Edu_Curriculum cu on co.currId = cu.id\n" +
                    "                               left join Edu_Period p on p.id = co.period\n" +
                    "                        where co.period > 100\n" +
                    "                          and (cu.courseType is null or cu.courseType = 1)\n" +
                    "                          and co.status = 1\n" +
                    "                          and esc.status in (1, 3)\n" +
                    "                          and esc.student = 200550\n" +
                    "                          and subject = 'A01'";
        }
        sql += ") t\n" +
                "                       on fsr.subjectCode = t.subject and\n" +
                "                          charIndex(',' + t.grade + ',', ',' + bs.displayGrade + ',') > 0 and\n" +
                "                          charIndex(',' + t.cate + ',', ',' + bs.displayCate + ',') > 0 and\n" +
                "                          charIndex(',' + t.termName + ',', ',' + bs.displayPeriod + ',') > 0\n" +
                "      where fsr.id in (97, 98, 99, 100, 101, 109, 110, 111, 112,177, 113,7,8,9,10,11,19,20,21,22,23) ";
        if (!org.springframework.util.StringUtils.isEmpty(cateCode)) {
            sql += "and fsr.cateCode=" + cateCode;
        }
        sql += " ) tt where tt.isBelongtoClass=1";
        return sql;
    }
    private String getWjSql(int studentId,Integer cateCode) {
        return "select *\n" +
                "from (\n" +
                "       select fsr.id,\n" +
                "              fsr.bookName,\n" +
                "              fsr.pop,\n" +
                "              isNUll((select top 1 TsBJRt.createTime\n" +
                "               from Tk_sendBookJsonRecord_t TsBJRt\n" +
                "               where TsBJRt.bookId = fsr.id\n" +
                "               order by TsBJRt.createTime desc),1) as                                                 modifyJsonTime,\n" +
                "              fsr.sendtime,\n" +
                "              fsr.cateCode,\n" +
                "              fsr.zipSize,\n" +
                "              (case when fsr.status = 2 then 1 else 0 end)                                         isReady,\n" +
                "              (case when t.grade is not null then 1 else 0 end) isBelongtoClass,\n" +
                "              fsr.md5Code,\n" +
                "              '"+beseUrl+"/upload/imageTag/zip/76/' + convert(varchar, fsr.id) + '/' +\n" +
                "              replace(replace(fsr.bookName, ' ', '_'), '朗文', 'langwen') + '.zip'                   downloadUrl\n" +
                "       from im_book fsr\n" +
                "              left join (select distinct case\n" +
                "                                           when WEG.NewCode = 'K3' and (wec.CourseCate = 'Level1' or wec.CourseCate = 'Level2') then 'A01'\n" +
                "                                           when WEG.NewCode = 'FS' and (wec.CourseCate = 'Level1' or wec.CourseCate = 'Level2') then 'A02'\n" +
                "                                           when WEG.NewCode = 'F1' and (wec.CourseCate = 'Level1' or wec.CourseCate = 'Level2') then 'A03'\n" +
                "                                           when WEG.NewCode = 'F2' and (wec.CourseCate = 'Level1' or wec.CourseCate = 'Level2') then 'A04'\n" +
                "                                           when WEG.NewCode = 'F3' and (wec.CourseCate = 'Level1' or wec.CourseCate = 'Level2') then 'A05'\n" +
                "                                           else '0' end grade\n" +
                "                         from Wj_Edu_Course wec\n" +
                "                                left join Wj_Edu_StudentClass WESC on wec.CourseId = WESC.CourseIdTo\n" +
                "                                 left join  Wj_Edu_Student WES on WES.StudentId=WESC.StudentId \n" +
                "                                left join Wj_Edu_Grade WEG on WEG.GradeCode = WES.GradeCode\n" +
                "                         where wec.Status in (1, 3)\n" +
                "                           and WESC.Status =1 \n" +
                "                           and WESC.StudentId = "+studentId+") t\n" +
                "                        on t.grade = fsr.gradeCode\n" +
                "       where fsr.cateCode > 200\n" +
                "         and fsr.cateCode = "+cateCode+") tt\n" +
                "where tt.isBelongtoClass = 1\n" +
                "order by tt.id";
    }
    @Override
    public List<Map<String, Object>> getNewBookList(int studentId, int cityId,Integer cateCode,int tag) {
        String sql = "";
        if (tag==0) {
            if (cityId == 1) {
                sql = getSqlSz(studentId, cateCode);
            } else {
                sql = getNewSqlHz(studentId, cateCode);
            }
        }else{
            sql = getWjSql(studentId, cateCode);
        }
        return getListUseSql(sql);
    }

    /**
     *
     * @see ImageTagDaoImpl#getNewBookList(int, int, Integer, int)
     * @param studentId
     * @param cityId
     * @param cateCode
     * @return
     */
    @Override
    public List<Map<String, Object>> getBookList(int studentId, int cityId, int cateCode) {
        String sql="";
        if(cityId==1){
            sql+="select distinct fsr.id,\n" +
                    "                fsr.bookName,\n" +
                    "                fsr.pop,\n" +
                    "                fsr.sendtime,\n" +
                    "                fsr.cateCode,\n" +
                    "                fsr.zipSize,\n" +
                    "                (case when fsr.status = 2 then 1 else 0 end)                       isReady,\n" +
                    "                (case when a.type is not null or "+studentId+" = 29674 or "+studentId+"=269901  then 1 else 0 end) isBelongtoClass,\n" +
                    "                fsr.md5Code,\n" +
                    "                'http://alhnb2.skyedu99.com/upload/imageTag/zip/76/' + convert(varchar, fsr.id) + '/' +\n" +
                    "                replace(replace(fsr.bookName, ' ', '_'), '朗文', 'langwen') + '.zip' downloadUrl\n" +
                    "from im_book fsr\n" +
                    "       left join (select\n" +
                    "                    case\n" +
                    "                      --一年级\n" +
                    "                      when co.grade = 'A01' and co.cate = 7 and\n" +
                    "                           (ep.termName like '%暑假' or ep.termName like '%秋季') then 'LEAP-1A'\n" +
                    "                      when co.grade = 'A01' and co.cate = 7 and\n" +
                    "                           (ep.termName like '%寒假' or ep.termName like '%春季') then 'LEAP-1B'\n" +
                    "                      when co.grade = 'A01' and co.cate = 15 and\n" +
                    "                           (ep.termName like '%暑假' or ep.termName like '%秋季') then 'PLE2-1A'\n" +
                    "                      when co.grade = 'A01' and co.cate = 15 and\n" +
                    "                           (ep.termName like '%寒假' or ep.termName like '%春季') then 'PLE2-1B'\n" +
                    "                      --二年级\n" +
                    "                      when co.grade = 'A02' and co.cate = 3 and\n" +
                    "                           (ep.termName like '%暑假' or ep.termName like '%秋季') then 'LEW-2A'\n" +
                    "                      when co.grade = 'A02' and co.cate = 3 and\n" +
                    "                           (ep.termName like '%寒假' or ep.termName like '%春季') then 'LEW-2B'\n" +
                    "                      when co.grade = 'A02' and co.cate = 7 and\n" +
                    "                           (ep.termName like '%暑假' or ep.termName like '%秋季') then 'LEAP-2A'\n" +
                    "                      when co.grade = 'A02' and co.cate = 7 and\n" +
                    "                           (ep.termName like '%寒假' or ep.termName like '%春季') then 'LEAP-2B'\n" +
                    "                      when co.grade = 'A02' and co.cate in (1, 15) and\n" +
                    "                           (ep.termName like '%暑假' or ep.termName like '%秋季') then 'PLE2-2A'\n" +
                    "                      when co.grade = 'A02' and co.cate in (1, 15) and\n" +
                    "                           (ep.termName like '%寒假' or ep.termName like '%春季') then 'PLE2-2B'\n" +
                    "                      --三年级\n" +
                    "                      when co.grade = 'A03' and co.cate = 3 and\n" +
                    "                           (ep.termName like '%暑假' or ep.termName like '%秋季') then 'LEW-3A'\n" +
                    "                      when co.grade = 'A03' and co.cate = 3 and\n" +
                    "                           (ep.termName like '%寒假' or ep.termName like '%春季') then 'LEW-3B'\n" +
                    "                      when co.grade = 'A03' and co.cate = 7 and\n" +
                    "                           (ep.termName like '%暑假' or ep.termName like '%秋季') then 'LEAP-3A'\n" +
                    "                      when co.grade = 'A03' and co.cate = 7 and\n" +
                    "                           (ep.termName like '%寒假' or ep.termName like '%春季') then 'LEAP-3B'\n" +
                    "                      when co.grade = 'A03' and co.cate in (1, 15) and\n" +
                    "                           (ep.termName like '%暑假' or ep.termName like '%秋季') then 'PLE2-3A'\n" +
                    "                      when co.grade = 'A03' and co.cate in (1, 15) and\n" +
                    "                           (ep.termName like '%寒假' or ep.termName like '%春季') then 'PLE2-3B'\n" +
                    "                      -- 四年级\n" +
                    "                      when co.grade = 'A04' and co.cate = 3 and\n" +
                    "                           (ep.termName like '%暑假' or ep.termName like '%秋季') then 'LEW-4A'\n" +
                    "                      when co.grade = 'A04' and co.cate = 3 and\n" +
                    "                           (ep.termName like '%寒假' or ep.termName like '%春季') then 'LEW-4B'\n" +
                    "                      when co.grade = 'A04' and co.cate = 7 and\n" +
                    "                           (ep.termName like '%暑假' or ep.termName like '%秋季') then 'LEAP-4A'\n" +
                    "                      when co.grade = 'A04' and co.cate = 7 and\n" +
                    "                           (ep.termName like '%寒假' or ep.termName like '%春季') then 'LEAP-4B'\n" +
                    "                      when co.grade = 'A04' and co.cate in (1, 15) and\n" +
                    "                           (ep.termName like '%暑假' or ep.termName like '%秋季') then 'PLE2-4A'\n" +
                    "                      when co.grade = 'A04' and co.cate in (1, 15) and\n" +
                    "                           (ep.termName like '%寒假' or ep.termName like '%春季') then 'PLE2-4B'\n" +
                    "                      -- 五年级\n" +
                    "                      when co.grade = 'A05' and co.cate = 3 and\n" +
                    "                           (ep.termName like '%暑假' or ep.termName like '%秋季') then 'LEW-5A'\n" +
                    "                      when co.grade = 'A05' and co.cate = 3 and\n" +
                    "                           (ep.termName like '%寒假' or ep.termName like '%春季') then 'LEW-5B'\n" +
                    "                      when co.grade = 'A05' and co.cate = 7 and\n" +
                    "                           (ep.termName like '%暑假' or ep.termName like '%秋季') then 'LEAP-5A'\n" +
                    "                      when co.grade = 'A05' and co.cate = 7 and\n" +
                    "                           (ep.termName like '%寒假' or ep.termName like '%春季') then 'LEAP-5B'\n" +
                    "                      when co.grade = 'A05' and co.cate in (1, 15) and\n" +
                    "                           (ep.termName like '%暑假' or ep.termName like '%秋季') then 'PLE2-5A'\n" +
                    "                      when co.grade = 'A05' and co.cate in (1, 15) and\n" +
                    "                           (ep.termName like '%寒假' or ep.termName like '%春季') then 'PLE2-5B'\n" +
                    "                      else null end as type\n" +
                    "                  from Edu_Course co\n" +
                    "                         inner join Edu_StudentClass cls on cls.course = co.id\n" +
                    "                         left join Edu_Period ep on co.period = ep.id\n" +
                    "                  where co.status <> -1\n" +
                    "                    and cls.status in (1, 3)\n" +
                    "                    and student = "+studentId+"\n" +
                    "                    and co.subject = 'A01'\n" +
                    "                    and co.cate <> 9) a on fsr.bookName = a.type\n" +
                    "where fsr.cateCode < 100\n" +
                    "  and fsr.id not in (24, 114) and fsr.cateCode="+cateCode+"\n" +
                    "union\n" +
                    "select fsr.id,\n" +
                    "       fsr.bookName,\n" +
                    "       fsr.pop,\n" +
                    "       fsr.sendtime,\n" +
                    "       fsr.cateCode,\n" +
                    "       fsr.zipSize,\n" +
                    "       (case when fsr.status = 2 then 1 else 0 end)                       isReady,\n" +
                    "       (case when t.grade is not null or "+studentId+" = 29674 or "+studentId+"=269901  then 1 else 0 end) isBelongtoClass,\n" +
                    "       fsr.md5Code,\n" +
                    "       'http://alhnb2.skyedu99.com/upload/imageTag/zip/76/' + convert(varchar, fsr.id) + '/' +\n" +
                    "       replace(replace(fsr.bookName, ' ', '_'), '朗文', 'langwen') + '.zip' downloadUrl\n" +
                    "from im_book fsr\n" +
                    "       left join (select distinct co.subject,\n" +
                    "                                  case\n" +
                    "                                    --一年级\n" +
                    "                                    when co.grade = 'A01' and (ep.termName like '%暑假' or ep.termName like '%秋季') and\n" +
                    "                                         (co.cate = 9) then 'A01'\n" +
                    "                                    when co.grade = 'A01' and co.cate = 7 and\n" +
                    "                                         (ep.termName like '%寒假' or ep.termName like '%春季') then 'A01'\n" +
                    "                                    when co.grade = 'A01' and co.cate = 9 and\n" +
                    "                                         (ep.termName like '%寒假' or ep.termName like '%春季') then 'A02'\n" +
                    "                                    when co.grade = 'A01' and co.cate = 7                       then 'A02'\n" +
                    "                                    when co.grade = 'A01' and co.cate = 15 and\n" +
                    "                                         (ep.termName like '%寒假' or ep.termName like '%春季') then 'A04'\n" +
                    "                                    when co.grade = 'A01' and co.cate = 15 and\n" +
                    "                                         (ep.termName like '%暑假' or ep.termName like '%秋季') then 'A03'\n" +
                    "\n" +
                    "                                    --二年级\n" +
                    "                                    when co.grade = 'A02' and co.cate =9 and\n" +
                    "                                         (ep.termName like '%暑假' or ep.termName like '%秋季') then 'A02'\n" +
                    "                                    when co.grade = 'A02' and co.cate in (3, 7) and\n" +
                    "                                         (ep.termName like '%暑假' or ep.termName like '%秋季') then 'A03'\n" +
                    "                                    when co.grade = 'A02' and co.cate in (9, 3, 7) and\n" +
                    "                                         (ep.termName like '%寒假' or ep.termName like '%春季') then 'A03'\n" +
                    "                                    when co.grade = 'A02' and co.cate = 1 then 'A03'\n" +
                    "                                    when co.grade = 'A02' and co.cate = 15 and\n" +
                    "                                         (ep.termName like '%寒假' or ep.termName like '%春季') then 'A03'\n" +
                    "                                    when co.grade = 'A02' and co.cate = 15 and\n" +
                    "                                         (ep.termName like '%暑假' or ep.termName like '%秋季') then 'A04'\n" +
                    "\n" +
                    "\n" +
                    "                                    -- 三年级\n" +
                    "                                    when co.grade = 'A03' and co.cate =9 and\n" +
                    "                                         (ep.termName like '%暑假' or ep.termName like '%秋季') then 'A03'\n" +
                    "                                    when co.grade = 'A03' and co.cate =9  and\n" +
                    "                                         (ep.termName like '%寒假' or ep.termName like '%春季') then 'A04'\n" +
                    "                                    when co.grade = 'A03' and co.cate in (1,7,3)                then 'A04'\n" +
                    "                                    when co.grade = 'A03' and co.cate = 15 and\n" +
                    "                                         (ep.termName like '%暑假' or ep.termName like '%秋季') then 'A04'\n" +
                    "                                    when co.grade = 'A03' and co.cate = 15 and\n" +
                    "                                         (ep.termName like '%寒假' or ep.termName like '%春季') then 'A05'\n" +
                    "\n" +
                    "\n" +
                    "                                    --四年级\n" +
                    "                                    when co.grade = 'A04' and co.cate = 9 and\n" +
                    "                                         (ep.termName like '%暑假' or ep.termName like '%秋季') then 'A03'\n" +
                    "                                    when co.grade = 'A04' and co.cate = 9 and\n" +
                    "                                         (ep.termName like '%寒假' or ep.termName like '%春季') then 'A04'\n" +
                    "                                    when co.grade = 'A04' and co.cate in (1,3,7)                then 'A05'\n" +
                    "                                    when co.grade = 'A04' and co.cate = 15 and\n" +
                    "                                         (ep.termName like '%暑假' or ep.termName like '%秋季') then 'A05'\n" +
                    "                                    when co.grade = 'A04' and co.cate = 15 and\n" +
                    "                                         (ep.termName like '%寒假' or ep.termName like '%春季') then 'A06'\n" +
                    "\n" +
                    "\n" +
                    "                                    --五年级\n" +
                    "                                    when co.grade = 'A05' and co.cate = 9 and\n" +
                    "                                         (ep.termName like '%暑假' or ep.termName like '%秋季') then 'A04'\n" +
                    "                                    when co.grade = 'A05' and co.cate = 9 and\n" +
                    "                                         (ep.termName like '%寒假' or ep.termName like '%春季') then 'A05'\n" +
                    "\n" +
                    "                                    when co.grade = 'A05' and co.cate in (7, 3) and\n" +
                    "                                         (ep.termName like '%暑假' or ep.termName like '%秋季') then 'A05'\n" +
                    "                                    when co.grade = 'A05' and co.cate in (7, 3) and\n" +
                    "                                         (ep.termName like '%寒假' or ep.termName like '%春季') then 'A06'\n" +
                    "                                    when co.grade = 'A05' and co.cate in (1, 15) then 'A06'\n" +
                    "\n" +
                    "                                    else co.grade end grade\n" +
                    "                  from Edu_Course co\n" +
                    "                         inner join Edu_StudentClass esc on co.id = esc.course\n" +
                    "                         left join Edu_Period ep on ep.id = co.period\n" +
                    "                  where co.period >= 100\n" +
                    "                    and co.status =1\n" +
                    "                    and esc.status in (1, 3)\n" +
                    "                    and esc.student = "+studentId+"\n" +
                    "                    and co.subject = 'A01') t on fsr.subjectCode = t.subject and t.grade = fsr.gradeCode\n" +
                    "where fsr.cateCode > 200 and fsr.cateCode="+cateCode+"  \n" +
                    "order by fsr.id";

            //and fsr.id not in (180, 181, 182)
        } else {
            sql+="select *\n" +
                    "from (select fsr.id,\n" +
                    "             fsr.bookName,\n" +
                    "             fsr.pop,\n" +
                    "             fsr.sendtime,\n" +
                    "             fsr.cateCode,\n" +
                    "             fsr.zipSize,\n" +
                    "             (case when fsr.status = 2 then 1 else 0 end)                       isReady,\n" +
                    "             (case when t.grade is not null then 1 else 0 end)                  isBelongtoClass,\n" +
                    "             fsr.md5Code,\n" +
                    "             'http://alhnb2.skyedu99.com/upload/imageTag/zip/76/' + convert(varchar, fsr.id) + '/' +\n" +
                    "             replace(replace(fsr.bookName, ' ', '_'), '朗文', 'langwen') + '.zip' downloadUrl\n" +
                    "      from im_book fsr\n" +
                    "             left join TK_book_status_t bs on bs.bookId = fsr.id and bs.cityId = 2\n" +
                    "             left join (select distinct co.grade                               as grade,\n" +
                    "                                        co.subject                             as subject,\n" +
                    "                                        co.cate                                as cate,\n" +
                    "                                        case\n" +
                    "                                          when SUBSTRING(p.termName, 5, 2) = '暑假' then '秋季'\n" +
                    "                                          when SUBSTRING(p.termName, 5, 2) = '寒假' then '春季'\n" +
                    "                                          else SUBSTRING(p.termName, 5, 2) end as termName\n" +
                    "                        from Edu_Course co\n" +
                    "                               inner join Edu_StudentClass esc\n" +
                    "                                          on co.id = esc.course\n" +
                    "                               left join Edu_Curriculum cu on co.currId = cu.id\n" +
                    "                               left join Edu_Period p on p.id = co.period\n" +
                    "                        where co.period\n" +
                    "                          > 100\n" +
                    "                          and (cu.courseType is null or cu.courseType = 1)\n" +
                    "                          and co.status = 1\n" +
                    "                          and esc.status in (1, 3)\n" +
                    "                          and esc.student = "+studentId+"\n" +
                    "                          and subject = 'A01') t on fsr.subjectCode = t.subject and\n" +
                    "                                                    charIndex(',' + t.grade + ',', ',' + bs.displayGrade + ',') > 0 and\n" +
                    "                                                    charIndex(',' + t.cate + ',', ',' + bs.displayCate + ',') > 0 and\n" +
                    "                                                    charIndex(',' + t.termName + ',', ',' + bs.displayPeriod + ',') > 0\n" +
                    "      where fsr.showStatus = 1\n" +
                    "        and fsr.cateCode between 100 and 200  and fsr.cateCode="+cateCode+"\n" +
                    "      union\n" +
                    "      select fsr.id,\n" +
                    "             fsr.bookName,\n" +
                    "             fsr.pop,\n" +
                    "             fsr.sendtime,\n" +
                    "             2                                                                  cateCode,\n" +
                    "             fsr.zipSize,\n" +
                    "             (case when fsr.status = 2 then 1 else 0 end)                       isReady,\n" +
                    "             (case when t.grade is not null then 1 else 0 end)                  isBelongtoClass,\n" +
                    "             fsr.md5Code,\n" +
                    "             'http://alhnb2.skyedu99.com/upload/imageTag/zip/76/' + convert(varchar, fsr.id) + '/' +\n" +
                    "             replace(replace(fsr.bookName, ' ', '_'), '朗文', 'langwen') + '.zip' downloadUrl\n" +
                    "      from im_book fsr\n" +
                    "             left join TK_book_status_t bs on bs.bookId = fsr.id and bs.cityId = 2\n" +
                    "             left join (select distinct case\n" +
                    "                                          when (select count(*)\n" +
                    "                                                from Edu_Course co\n" +
                    "                                                       inner join Edu_StudentClass esc on co.id = esc.course\n" +
                    "                                                where co.name = 'LEW4B（P5）PFJ1'\n" +
                    "                                                  and esc.student = "+studentId+") > 0 then 'A04'\n" +
                    "                                          else co.grade end                     as grade,\n" +
                    "                                        co.subject                             as subject,\n" +
                    "                                        case\n" +
                    "                                          when (select count(*)\n" +
                    "                                                from Edu_Course co\n" +
                    "                                                       inner join Edu_StudentClass esc on co.id = esc.course\n" +
                    "                                                where co.name = 'LEW5 (P5）PFJ1'\n" +
                    "                                                  and esc.student = "+studentId+") > 0 then '3'\n" +
                    "                                          else co.cate end                               as cate,\n" +
                    "                                        case\n" +
                    " when (select count(*)\n" +
                    "                                                from Edu_Course co\n" +
                    "                                                       inner join Edu_StudentClass esc on co.id = esc.course\n" +
                    "                                                where co.name = 'LEW4B（P5）PFJ1'\n" +
                    "                                                  and esc.student = "+studentId+") > 0 then '春季'\n" +
                    "                                          when SUBSTRING(p.termName, 5, 2) = '暑假' then '秋季'\n" +
                    "                                          when SUBSTRING(p.termName, 5, 2) = '寒假' then '春季'\n" +
                    "                                          else SUBSTRING(p.termName, 5, 2) end as termName\n" +
                    "                        from Edu_Course co\n" +
                    "                               inner join Edu_StudentClass esc on co.id = esc.course\n" +
                    "                               left join Edu_Curriculum cu on co.currId = cu.id\n" +
                    "                               left join Edu_Period p on p.id = co.period\n" +
                    "                        where co.period > 100\n" +
                    "                          and (cu.courseType is null or cu.courseType = 1)\n" +
                    "                          and co.status = 1\n" +
                    "                          and esc.status in (1, 3)\n" +
                    "                          and esc.student = "+studentId+"\n" +
                    "                          and subject = 'A01') t\n" +
                    "                       on fsr.subjectCode = t.subject and\n" +
                    "                          charIndex(',' + t.grade + ',', ',' + bs.displayGrade + ',') > 0 and\n" +
                    "                          charIndex(',' + t.cate + ',', ',' + bs.displayCate + ',') > 0 and\n" +
                    "                          charIndex(',' + t.termName + ',', ',' + bs.displayPeriod + ',') > 0\n" +
                    "      where fsr.id in (97, 98, 99, 100, 101, 109, 110, 111, 112, 113,7,8,9,10,11,19,20,21,22,23) and fsr.cateCode="+cateCode+") tt";
        }

        return getListUseSql(sql);
//        if (cityId == 2) { //杭州地区
//            String sql ="select * from ("+
//                    "        select fsr.id,fsr.bookName,fsr.pop,fsr.sendtime,fsr.cateCode,fsr.zipSize,(case when fsr.status = 2 then 1 else 0 end) isReady, "+
//                    "                (case when t.grade is not null then 1 else 0 end) isBelongtoClass, fsr.md5Code,    "+
//                    "                '"+CommonUtil.APPFILEURL+"/upload/imageTag/zip/76/'+ convert(varchar,fsr.id) + '/'+ replace(replace(fsr.bookName, ' ', '_'), '朗文', 'langwen')+'.zip' downloadUrl "+
//                    "        from im_book fsr left join  (     	"+
//                    "                select distinct case when co.cate = 21 then 'A01' when co.cate = 22 then 'A02' else co.grade end grade, co.subject, "+
//                    "                        cast(case when co.cate = 9 and co.grade = 'A01' and co.period = 106  then 7 else co.cate end as varchar) cate  "+
//                    "                from Edu_Course co inner join Edu_StudentClass esc on co.id = esc.course left join Edu_Curriculum cu on co.currId = cu.id 	"+
//                    "                where co.period > 100 and (cu.courseType is null or cu.courseType = 1) and co.status<>-1 and esc.status in (1,3) and esc.student = "+ studentId +" and subject = 'A01'    "+
//                    "             ) t on fsr.subjectCode = t.subject and charIndex(','+ t.grade +',', ','+ fsr.displayGrade +',') > 0 and charIndex(','+ t.cate +',', ','+ fsr.displayCate +',') > 0  "+
//                    "        where fsr.showStatus = 1 and fsr.cateCode between 100 and 200  and fsr.cateCode="+cateCode+
//                    "     union "+
//                    "        select fsr.id, fsr.bookName,fsr.pop,fsr.sendtime,2 cateCode,fsr.zipSize,(case when fsr.status = 2 then 1 else 0 end) isReady, "+
//                    "                (case when t.grade is not null then 1 else 0 end) isBelongtoClass, fsr.md5Code,  "+
//                    "                '"+CommonUtil.APPFILEURL+"/upload/imageTag/zip/76/'+ convert(varchar,fsr.id) + '/'+ replace(replace(fsr.bookName, ' ', '_'), '朗文', 'langwen')+'.zip' downloadUrl "+
//                    "        from im_book fsr left join  (     "+
//                    "                select distinct co.subject, case when co.cate = 21 then 'A01' when co.cate = 22 then 'A02' else co.grade end grade,"+
//                    "                        case when co.cate in (3, 21, 22) or co.grade = 'A04' and co.cate = 7 then 2 else co.cate end cate    "+
//                    "                from Edu_Course co inner join Edu_StudentClass esc on co.id = esc.course left join Edu_Curriculum cu on co.currId = cu.id"+
//                    "                where co.period > 100 and (cu.courseType is null or cu.courseType = 1) "+
//                    "                        and co.status<>-1 and esc.status in (1,3) and esc.student = "+ studentId +" and subject = 'A01' "+
//                    "                ) t on fsr.subjectCode = t.subject and t.grade=fsr.gradeCode   "+
//                    "        where fsr.id in (97, 98, 99, 100, 109, 110, 111) and fsr.cateCode="+cateCode+
//                    "  ) tt order by tt.id ";
//            return getListUseSql(sql);
//        }
//        String sql =  "select distinct fsr.id,fsr.bookName,fsr.pop,fsr.sendtime,fsr.cateCode,fsr.zipSize,(case when fsr.status = 2 then 1 else 0 end) isReady,"
//                + "		(case when a.grade is not null or "+studentId+" = 29674 or "+studentId+"=269901 then 1 else 0 end) isBelongtoClass, fsr.md5Code, "
//                + "		'"+CommonUtil.APPFILEURL+"/upload/imageTag/zip/76/'+ convert(varchar,fsr.id) + '/'+ replace(replace(fsr.bookName, ' ', '_'), '朗文', 'langwen')+'.zip' downloadUrl "
//                + "from im_book fsr left join ("
//                + " 	select distinct co.grade, co.subject, case when co.cate = 3 or co.cate = 9 then '2,'+co.cate else co.cate end cate, case when p.termName like '%秋季' then 'A' when p.termName like '%春季' then 'B' end type "
//                + " 	from Edu_Course co inner join Edu_StudentClass cls on cls.course=co.id left join Edu_Period p on co.period=p.id "
//                + " 	where co.status<>-1 and cls.status in (1,3) and (p.termName like '%秋季' or p.termName like '%春季') and student = "+ studentId
//                + ") a on a.subject=fsr.subjectCode and a.grade=fsr.gradeCode and charIndex(','+ fsr.cateCode +',', ','+ a.cate +',') > 0 and fsr.bookName like '%'+a.type "
//                + " where fsr.cateCode < 100  and fsr.id not in (24,114) and fsr.cateCode="+cateCode
//                + " union "
//                + "select fsr.id,fsr.bookName,fsr.pop,fsr.sendtime,fsr.cateCode,fsr.zipSize,(case when fsr.status = 2 then 1 else 0 end) isReady, " +
//                "        (case when t.grade is not null or "+studentId+" = 29674 or "+studentId+"=269901 then 1 else 0 end) isBelongtoClass, fsr.md5Code, " +
//                "       '"+CommonUtil.APPFILEURL+"/upload/imageTag/zip/76/'+ convert(varchar,fsr.id) + '/'+ replace(replace(fsr.bookName, ' ', '_'), '朗文', 'langwen')+'.zip' downloadUrl " +
//                "from im_book fsr left join  (" +
//                "    select distinct co.subject, co.cate,"+
//                "	case when co.grade = 'A01' and (co.cate = 7 or (co.cate = 9 and (ep.termName like '%寒假' or ep.termName like '%春季'))) then 'A02' "+
//                "	     when co.grade = 'A02' and co.cate in (15,1) and (ep.termName like '%寒假' or ep.termName like '%春季') then 'A04'"+
//                "	     when co.grade = 'A02' and co.cate in (9,7) and (ep.termName like '%寒假' or ep.termName like '%春季') then 'A03'"+
//                "	     when co.grade = 'A02' and co.cate in (15,1,7) and (ep.termName like '%暑假' or ep.termName like '%秋季') then 'A03'"+
//                "	     when co.grade = 'A03' and co.cate in (15,1) and (ep.termName like '%寒假' or ep.termName like '%春季') then 'A05'"+
//                "	     when co.grade = 'A03' and co.cate in (9,3,7) and (ep.termName like '%寒假' or ep.termName like '%春季') then 'A04'"+
//                "	     when co.grade = 'A03' and co.cate in (15,1,7,3) and (ep.termName like '%暑假' or ep.termName like '%秋季') then 'A04'"+
//                "	     when co.grade = 'A04' and co.cate = 9 and (ep.termName like '%暑假' or ep.termName like '%秋季') then 'A03'"+
//                "	     when co.grade = 'A04' and co.cate in (15,1,7,3) and (ep.termName like '%寒假' or ep.termName like '%春季') then 'A05'"+
//                "	     when co.grade = 'A05' and (ep.termName like '%暑假' or ep.termName like '%秋季') then 'A04' else co.grade end grade"+
//                "    from Edu_Course co inner join Edu_StudentClass esc on co.id = esc.course left join Edu_Period ep on ep.id = co.period"+
//                "    where co.period >= 100 and co.status<>-1 and esc.status in (1,3) and esc.student = "+ studentId +
//                ") t on fsr.subjectCode = t.subject and t.grade = fsr.gradeCode and charIndex(','+ t.cate +',', ','+ fsr.displayCate +',') > 0"+
//                "where fsr.cateCode > 200 and fsr.cateCode="+cateCode+"   and fsr.id not in (180, 181, 182)  order by fsr.id ";
//        return getListUseSql(sql);
    }

    /**
     * @see ImageTagDaoImpl#getNewBookList(int, int, Integer, int)
     * @param studentId
     * @param cityId
     * @param cateCode
     * @return
     */
    @Override
    public List<Map<String, Object>> getBookListByVisitor(int studentId, int cityId, int cateCode) {
        int i=0;
        String sql="";
        if(cityId==1){
            sql+="select distinct fsr.id,\n" +
                    "                fsr.bookName,\n" +
                    "                fsr.pop,\n" +
                    "                fsr.sendtime,\n" +
                    "                fsr.cateCode,\n" +
                    "                fsr.zipSize,\n" +
                    "                (case when fsr.status = 2  then 1 else 0 end)                       isReady,\n" +
                    "                "+i+" isBelongtoClass,\n" +
                    "                fsr.md5Code,\n" +
                    "                'http://alhnb2.skyedu99.com/upload/imageTag/zip/76/' + convert(varchar, fsr.id) + '/' +\n" +
                    "                replace(replace(fsr.bookName, ' ', '_'), '朗文', 'langwen') + '.zip' downloadUrl\n" +
                    "from im_book fsr\n" +
                    "       left join (select\n" +
                    "                    case\n" +
                    "                      --一年级\n" +
                    "                      when co.grade = 'A01' and co.cate = 7 and\n" +
                    "                           (ep.termName like '%暑假' or ep.termName like '%秋季') then 'LEAP-1A'\n" +
                    "                      when co.grade = 'A01' and co.cate = 7 and\n" +
                    "                           (ep.termName like '%寒假' or ep.termName like '%春季') then 'LEAP-1B'\n" +
                    "                      when co.grade = 'A01' and co.cate = 15 and\n" +
                    "                           (ep.termName like '%暑假' or ep.termName like '%秋季') then 'PLE2-1A'\n" +
                    "                      when co.grade = 'A01' and co.cate = 15 and\n" +
                    "                           (ep.termName like '%寒假' or ep.termName like '%春季') then 'PLE2-1B'\n" +
                    "                      --二年级\n" +
                    "                      when co.grade = 'A02' and co.cate = 3 and\n" +
                    "                           (ep.termName like '%暑假' or ep.termName like '%秋季') then 'LEW-2A'\n" +
                    "                      when co.grade = 'A02' and co.cate = 3 and\n" +
                    "                           (ep.termName like '%寒假' or ep.termName like '%春季') then 'LEW-2B'\n" +
                    "                      when co.grade = 'A02' and co.cate = 7 and\n" +
                    "                           (ep.termName like '%暑假' or ep.termName like '%秋季') then 'LEAP-2A'\n" +
                    "                      when co.grade = 'A02' and co.cate = 7 and\n" +
                    "                           (ep.termName like '%寒假' or ep.termName like '%春季') then 'LEAP-2B'\n" +
                    "                      when co.grade = 'A02' and co.cate in (1, 15) and\n" +
                    "                           (ep.termName like '%暑假' or ep.termName like '%秋季') then 'PLE2-2A'\n" +
                    "                      when co.grade = 'A02' and co.cate in (1, 15) and\n" +
                    "                           (ep.termName like '%寒假' or ep.termName like '%春季') then 'PLE2-2B'\n" +
                    "                      --三年级\n" +
                    "                      when co.grade = 'A03' and co.cate = 3 and\n" +
                    "                           (ep.termName like '%暑假' or ep.termName like '%秋季') then 'LEW-3A'\n" +
                    "                      when co.grade = 'A03' and co.cate = 3 and\n" +
                    "                           (ep.termName like '%寒假' or ep.termName like '%春季') then 'LEW-3B'\n" +
                    "                      when co.grade = 'A03' and co.cate = 7 and\n" +
                    "                           (ep.termName like '%暑假' or ep.termName like '%秋季') then 'LEAP-3A'\n" +
                    "                      when co.grade = 'A03' and co.cate = 7 and\n" +
                    "                           (ep.termName like '%寒假' or ep.termName like '%春季') then 'LEAP-3B'\n" +
                    "                      when co.grade = 'A03' and co.cate in (1, 15) and\n" +
                    "                           (ep.termName like '%暑假' or ep.termName like '%秋季') then 'PLE2-3A'\n" +
                    "                      when co.grade = 'A03' and co.cate in (1, 15) and\n" +
                    "                           (ep.termName like '%寒假' or ep.termName like '%春季') then 'PLE2-3B'\n" +
                    "                      -- 四年级\n" +
                    "                      when co.grade = 'A04' and co.cate = 3 and\n" +
                    "                           (ep.termName like '%暑假' or ep.termName like '%秋季') then 'LEW-4A'\n" +
                    "                      when co.grade = 'A04' and co.cate = 3 and\n" +
                    "                           (ep.termName like '%寒假' or ep.termName like '%春季') then 'LEW-4B'\n" +
                    "                      when co.grade = 'A04' and co.cate = 7 and\n" +
                    "                           (ep.termName like '%暑假' or ep.termName like '%秋季') then 'LEAP-4A'\n" +
                    "                      when co.grade = 'A04' and co.cate = 7 and\n" +
                    "                           (ep.termName like '%寒假' or ep.termName like '%春季') then 'LEAP-4B'\n" +
                    "                      when co.grade = 'A04' and co.cate in (1, 15) and\n" +
                    "                           (ep.termName like '%暑假' or ep.termName like '%秋季') then 'PLE2-4A'\n" +
                    "                      when co.grade = 'A04' and co.cate in (1, 15) and\n" +
                    "                           (ep.termName like '%寒假' or ep.termName like '%春季') then 'PLE2-4B'\n" +
                    "                      -- 五年级\n" +
                    "                      when co.grade = 'A05' and co.cate = 3 and\n" +
                    "                           (ep.termName like '%暑假' or ep.termName like '%秋季') then 'LEW-5A'\n" +
                    "                      when co.grade = 'A05' and co.cate = 3 and\n" +
                    "                           (ep.termName like '%寒假' or ep.termName like '%春季') then 'LEW-5B'\n" +
                    "                      when co.grade = 'A05' and co.cate = 7 and\n" +
                    "                           (ep.termName like '%暑假' or ep.termName like '%秋季') then 'LEAP-5A'\n" +
                    "                      when co.grade = 'A05' and co.cate = 7 and\n" +
                    "                           (ep.termName like '%寒假' or ep.termName like '%春季') then 'LEAP-5B'\n" +
                    "                      when co.grade = 'A05' and co.cate in (1, 15) and\n" +
                    "                           (ep.termName like '%暑假' or ep.termName like '%秋季') then 'PLE2-5A'\n" +
                    "                      when co.grade = 'A05' and co.cate in (1, 15) and\n" +
                    "                           (ep.termName like '%寒假' or ep.termName like '%春季') then 'PLE2-5B'\n" +
                    "                      else null end as type\n" +
                    "                  from Edu_Course co\n" +
                    "                         inner join Edu_StudentClass cls on cls.course = co.id\n" +
                    "                         left join Edu_Period ep on co.period = ep.id\n" +
                    "                  where co.status <> -1\n" +
                    "                    and cls.status in (1, 3)\n" +
                    "                    and student = "+studentId+"\n" +
                    "                    and co.subject = 'A01'\n" +
                    "                    and co.cate <> 9) a on fsr.bookName = a.type\n" +
                    "where fsr.cateCode < 100 and fsr.status = 2  \n" +
                    "  and fsr.id not in (24, 114) and fsr.cateCode="+cateCode+
                    " union\n" +
                    "select fsr.id,\n" +
                    "       fsr.bookName,\n" +
                    "       fsr.pop,\n" +
                    "       fsr.sendtime,\n" +
                    "       fsr.cateCode,\n" +
                    "       fsr.zipSize,\n" +
                    "       (case when fsr.status = 2 then 1 else 0 end)                       isReady,\n" +
                    "       "+i+" isBelongtoClass,\n" +
                    "       fsr.md5Code,\n" +
                    "       'http://alhnb2.skyedu99.com/upload/imageTag/zip/76/' + convert(varchar, fsr.id) + '/' +\n" +
                    "       replace(replace(fsr.bookName, ' ', '_'), '朗文', 'langwen') + '.zip' downloadUrl\n" +
                    "from im_book fsr\n" +
                    "       left join (select distinct co.subject,\n" +
                    "                                  case\n" +
                    "                                    --一年级\n" +
                    "                                    when co.grade = 'A01' and (ep.termName like '%暑假' or ep.termName like '%秋季') and\n" +
                    "                                         (co.cate = 7 or co.cate = 9) then 'A01'\n" +
                    "                                    when co.grade = 'A01' and co.cate = 7 and\n" +
                    "                                         (ep.termName like '%寒假' or ep.termName like '%春季') then 'A01'\n" +
                    "                                    when co.grade = 'A01' and co.cate = 9 and\n" +
                    "                                         (ep.termName like '%寒假' or ep.termName like '%春季') then 'A02'\n" +
                    "                                    when co.grade = 'A01' and co.cate = 15 and\n" +
                    "                                         (ep.termName like '%寒假' or ep.termName like '%春季') then 'A03'\n" +
                    "                                    when co.grade = 'A01' and co.cate = 15 and\n" +
                    "                                         (ep.termName like '%暑假' or ep.termName like '%秋季') then 'A02'\n" +
                    "\n" +
                    "                                    --二年级\n" +
                    "                                    when co.grade = 'A02' and co.cate in (9, 3, 7) and\n" +
                    "                                         (ep.termName like '%暑假' or ep.termName like '%秋季') then 'A02'\n" +
                    "                                    when co.grade = 'A02' and co.cate in (9, 3, 7) and\n" +
                    "                                         (ep.termName like '%寒假' or ep.termName like '%春季') then 'A03'\n" +
                    "                                    when co.grade = 'A02' and co.cate in (9, 3, 7) and\n" +
                    "                                         (ep.termName like '%寒假' or ep.termName like '%春季') then 'A03'\n" +
                    "                                    when co.grade = 'A02' and co.cate = 1 then 'A03'\n" +
                    "                                    when co.grade = 'A02' and co.cate = 15 and\n" +
                    "                                         (ep.termName like '%寒假' or ep.termName like '%春季') then 'A03'\n" +
                    "                                    when co.grade = 'A02' and co.cate = 15 and\n" +
                    "                                         (ep.termName like '%暑假' or ep.termName like '%秋季') then 'A04'\n" +
                    "\n" +
                    "\n" +
                    "                                    -- 三年级\n" +
                    "                                    when co.grade = 'A03' and co.cate in (9, 3) and\n" +
                    "                                         (ep.termName like '%暑假' or ep.termName like '%秋季') then 'A03'\n" +
                    "                                    when co.grade = 'A03' and co.cate in (9, 3) and\n" +
                    "                                         (ep.termName like '%寒假' or ep.termName like '%春季') then 'A04'\n" +
                    "                                    when co.grade = 'A03' and co.cate in (1,7) then 'A04'\n" +
                    "                                    when co.grade = 'A03' and co.cate = 15 and\n" +
                    "                                         (ep.termName like '%暑假' or ep.termName like '%秋季') then 'A04'\n" +
                    "                                    when co.grade = 'A03' and co.cate = 15 and\n" +
                    "                                         (ep.termName like '%寒假' or ep.termName like '%春季') then 'A05'\n" +
                    "\n" +
                    "\n" +
                    "                                    --四年级\n" +
                    "                                    when co.grade = 'A04' and co.cate = 9 and\n" +
                    "                                         (ep.termName like '%暑假' or ep.termName like '%秋季') then 'A03'\n" +
                    "                                    when co.grade = 'A04' and co.cate = 9 and\n" +
                    "                                         (ep.termName like '%寒假' or ep.termName like '%春季') then 'A04'\n" +
                    "                                    when co.grade = 'A04' and co.cate in (7, 3) and\n" +
                    "                                         (ep.termName like '%暑假' or ep.termName like '%秋季') then 'A04'\n" +
                    "                                    when co.grade = 'A04' and co.cate in (7, 3) and\n" +
                    "                                         (ep.termName like '%寒假' or ep.termName like '%春季') then 'A05'\n" +
                    "                                    when co.grade = 'A04' and co.cate = 1 then 'A05'\n" +
                    "                                    when co.grade = 'A04' and co.cate = 15 and\n" +
                    "                                         (ep.termName like '%暑假' or ep.termName like '%秋季') then 'A05'\n" +
                    "                                    when co.grade = 'A04' and co.cate = 15 and\n" +
                    "                                         (ep.termName like '%寒假' or ep.termName like '%春季') then 'A06'\n" +
                    "\n" +
                    "\n" +
                    "                                    --五年级\n" +
                    "                                    when co.grade = 'A05' and co.cate = 9 and\n" +
                    "                                         (ep.termName like '%暑假' or ep.termName like '%秋季') then 'A04'\n" +
                    "                                    when co.grade = 'A05' and co.cate = 9 and\n" +
                    "                                         (ep.termName like '%寒假' or ep.termName like '%春季') then 'A05'\n" +
                    "\n" +
                    "                                    when co.grade = 'A05' and co.cate in (7, 3) and\n" +
                    "                                         (ep.termName like '%暑假' or ep.termName like '%秋季') then 'A05'\n" +
                    "                                    when co.grade = 'A05' and co.cate in (7, 3) and\n" +
                    "                                         (ep.termName like '%寒假' or ep.termName like '%春季') then 'A06'\n" +
                    "                                    when co.grade = 'A05' and co.cate in (1, 15) then 'A06'\n" +
                    "\n" +
                    "                                    else co.grade end grade\n" +
                    "                  from Edu_Course co\n" +
                    "                         inner join Edu_StudentClass esc on co.id = esc.course\n" +
                    "                         left join Edu_Period ep on ep.id = co.period\n" +
                    "                  where co.period >= 100\n" +
                    "                    and co.status =1\n" +
                    "                    and esc.status in (1, 3)\n" +
                    "                    and esc.student = "+studentId+"\n" +
                    "                    and co.subject = 'A01') t on fsr.subjectCode = t.subject and t.grade = fsr.gradeCode\n" +
                    "where fsr.cateCode > 200\n and fsr.status = 2  and fsr.cateCode="+cateCode+
                    " order by fsr.id";
        } else {
            sql+="select *\n" +
                    "from (select fsr.id,\n" +
                    "             fsr.bookName,\n" +
                    "             fsr.pop,\n" +
                    "             fsr.sendtime,\n" +
                    "             fsr.cateCode,\n" +
                    "             fsr.zipSize,\n" +
                    "             (case when fsr.status = 2 then 1 else 0 end)                       isReady,\n" +
                    "             "+i+"                  isBelongtoClass,\n" +
                    "             fsr.md5Code,\n" +
                    "             'http://alhnb2.skyedu99.com/upload/imageTag/zip/76/' + convert(varchar, fsr.id) + '/' +\n" +
                    "             replace(replace(fsr.bookName, ' ', '_'), '朗文', 'langwen') + '.zip' downloadUrl\n" +
                    "      from im_book fsr\n" +
                    "             left join TK_book_status_t bs on bs.bookId = fsr.id and bs.cityId = 2\n" +
                    "             left join (select distinct co.grade                               as grade,\n" +
                    "                                        co.subject                             as subject,\n" +
                    "                                        co.cate                                as cate,\n" +
                    "                                        case\n" +
                    "                                          when SUBSTRING(p.termName, 5, 2) = '暑假' then '秋季'\n" +
                    "                                          when SUBSTRING(p.termName, 5, 2) = '寒假' then '春季'\n" +
                    "                                          else SUBSTRING(p.termName, 5, 2) end as termName\n" +
                    "                        from Edu_Course co\n" +
                    "                               inner join Edu_StudentClass esc\n" +
                    "                                          on co.id = esc.course\n" +
                    "                               left join Edu_Curriculum cu on co.currId = cu.id\n" +
                    "                               left join Edu_Period p on p.id = co.period\n" +
                    "                        where co.period\n" +
                    "                          > 100\n" +
                    "                          and (cu.courseType is null or cu.courseType = 1)\n" +
                    "                          and co.status = 1\n" +
                    "                          and esc.status in (1, 3)\n" +
                    "                          and esc.student = "+studentId+"\n" +
                    "                          and subject = 'A01') t on fsr.subjectCode = t.subject and\n" +
                    "                                                    charIndex(',' + t.grade + ',', ',' + bs.displayGrade + ',') > 0 and\n" +
                    "                                                    charIndex(',' + t.cate + ',', ',' + bs.displayCate + ',') > 0 and\n" +
                    "                                                    charIndex(',' + t.termName + ',', ',' + bs.displayPeriod + ',') > 0\n" +
                    "      where fsr.showStatus = 1\n" +
                    "        and fsr.cateCode between 100 and 200 and fsr.status = 2  and fsr.cateCode="+cateCode+
                    "      union\n" +
                    "      select fsr.id,\n" +
                    "             fsr.bookName,\n" +
                    "             fsr.pop,\n" +
                    "             fsr.sendtime,\n" +
                    "             2                                                                  cateCode,\n" +
                    "             fsr.zipSize,\n" +
                    "             (case when fsr.status = 2 then 1 else 0 end)                       isReady,\n" +
                    "            "+i+"                 isBelongtoClass,\n" +
                    "             fsr.md5Code,\n" +
                    "             'http://alhnb2.skyedu99.com/upload/imageTag/zip/76/' + convert(varchar, fsr.id) + '/' +\n" +
                    "             replace(replace(fsr.bookName, ' ', '_'), '朗文', 'langwen') + '.zip' downloadUrl\n" +
                    "      from im_book fsr\n" +
                    "             left join TK_book_status_t bs on bs.bookId = fsr.id and bs.cityId = 2\n" +
                    "             left join (select distinct case\n" +
                    "                                          when (select count(*)\n" +
                    "                                                from Edu_Course co\n" +
                    "                                                       inner join Edu_StudentClass esc on co.id = esc.course\n" +
                    "                                                where co.name = 'LEW4B（P5）PFJ1'\n" +
                    "                                                  and esc.student = "+studentId+") > 0 then 'A04'\n" +
                    "                                          else co.grade end                     as grade,\n" +
                    "                                        co.subject                             as subject,\n" +
                    "                                        case\n" +
                    "                                          when (select count(*)\n" +
                    "                                                from Edu_Course co\n" +
                    "                                                       inner join Edu_StudentClass esc on co.id = esc.course\n" +
                    "                                                where co.name = 'LEW5 (P5）PFJ1'\n" +
                    "                                                  and esc.student = "+studentId+") > 0 then '3'\n" +
                    "                                          else co.cate end                               as cate,\n" +
                    "                                        case\n" +
                    " when (select count(*)\n" +
                    "                                                from Edu_Course co\n" +
                    "                                                       inner join Edu_StudentClass esc on co.id = esc.course\n" +
                    "                                                where co.name = 'LEW4B（P5）PFJ1'\n" +
                    "                                                  and esc.student = "+studentId+") > 0 then '春季'\n" +
                    "                                          when SUBSTRING(p.termName, 5, 2) = '暑假' then '秋季'\n" +
                    "                                          when SUBSTRING(p.termName, 5, 2) = '寒假' then '春季'\n" +
                    "                                          else SUBSTRING(p.termName, 5, 2) end as termName\n" +
                    "                        from Edu_Course co\n" +
                    "                               inner join Edu_StudentClass esc on co.id = esc.course\n" +
                    "                               left join Edu_Curriculum cu on co.currId = cu.id\n" +
                    "                               left join Edu_Period p on p.id = co.period\n" +
                    "                        where co.period > 100\n" +
                    "                          and (cu.courseType is null or cu.courseType = 1)\n" +
                    "                          and co.status = 1\n" +
                    "                          and esc.status in (1, 3)\n" +
                    "                          and esc.student = "+studentId+"\n" +
                    "                          and subject = 'A01') t\n" +
                    "                       on fsr.subjectCode = t.subject and\n" +
                    "                          charIndex(',' + t.grade + ',', ',' + bs.displayGrade + ',') > 0 and\n" +
                    "                          charIndex(',' + t.cate + ',', ',' + bs.displayCate + ',') > 0 and\n" +
                    "                          charIndex(',' + t.termName + ',', ',' + bs.displayPeriod + ',') > 0\n" +
                    "      where fsr.id in (97, 98, 99, 100, 101, 109, 110, 111, 112, 113,7,8,9,10,11,19,20,21,22,23) and fsr.status = 2  and fsr.cateCode="+cateCode+" ) tt";
        }

        return getListUseSql(sql);
    }

    @Override
	public List<Map<String, Object>> getBookList() {
		String sql = "select * from IM_Book where status <> 0";
		return getListUseSql(sql);
	}

    @Override
    public List<Map<String, Object>> getBookListById(String  ids) {
        String sql =  "select distinct fsr.id as iD,fsr.bookName,fsr.pop,fsr.sendtime,fsr.cateCode,fsr.zipSize,1 isReady,"
                + "		1 isBelongtoClass, fsr.md5Code, "
                + "		'"+CommonUtil.APPFILEURL+"/upload/imageTag/zip/76/'+ convert(varchar,fsr.id) + '/'+ replace(replace(fsr.bookName, ' ', '_'), '朗文', 'langwen')+'.zip' downloadUrl "
                + "from im_book fsr left join ("
                + " 	select distinct co.grade, co.subject, case when co.cate = 3 or co.cate = 9 then '2,'+co.cate else co.cate end cate, case when p.termName like '%秋季' then 'A' when p.termName like '%春季' then 'B' end type "
                + " 	from Edu_Course co inner join Edu_StudentClass cls on cls.course=co.id left join Edu_Period p on co.period=p.id "
                + " 	where co.status<>-1 and cls.status in (1,3) and (p.termName like '%秋季' or p.termName like '%春季') and student = "+ 230415
                + ") a on a.subject=fsr.subjectCode and a.grade=fsr.gradeCode and charIndex(','+ fsr.cateCode +',', ','+ a.cate +',') > 0 and fsr.bookName like '%'+a.type "
                + " where fsr.cateCode < 100  and fsr.id not in (24,114)   and fsr.id in ("+ids+") "
                + " union "
                + "select fsr.id as iD,fsr.bookName,fsr.pop,fsr.sendtime,fsr.cateCode,fsr.zipSize,1 isReady, " +
                "       1 isBelongtoClass, fsr.md5Code, " +
                "       '"+CommonUtil.APPFILEURL+"/upload/imageTag/zip/76/'+ convert(varchar,fsr.id) + '/'+ replace(replace(fsr.bookName, ' ', '_'), '朗文', 'langwen')+'.zip' downloadUrl " +
                "from im_book fsr left join  (" +
                "    select distinct co.subject, co.cate,"+
                "	case when co.grade = 'A01' and (co.cate = 7 or (co.cate = 9 and (ep.termName like '%寒假' or ep.termName like '%春季'))) then 'A02' "+
                "	     when co.grade = 'A02' and co.cate in (15,1) and (ep.termName like '%寒假' or ep.termName like '%春季') then 'A04'"+
                "	     when co.grade = 'A02' and co.cate in (9,7) and (ep.termName like '%寒假' or ep.termName like '%春季') then 'A03'"+
                "	     when co.grade = 'A02' and co.cate in (15,1,7) and (ep.termName like '%暑假' or ep.termName like '%秋季') then 'A03'"+
                "	     when co.grade = 'A03' and co.cate in (15,1) and (ep.termName like '%寒假' or ep.termName like '%春季') then 'A05'"+
                "	     when co.grade = 'A03' and co.cate in (9,3,7) and (ep.termName like '%寒假' or ep.termName like '%春季') then 'A04'"+
                "	     when co.grade = 'A03' and co.cate in (15,1,7,3) and (ep.termName like '%暑假' or ep.termName like '%秋季') then 'A04'"+
                "	     when co.grade = 'A04' and co.cate = 9 and (ep.termName like '%暑假' or ep.termName like '%秋季') then 'A03'"+
                "	     when co.grade = 'A04' and co.cate in (15,1,7,3) and (ep.termName like '%寒假' or ep.termName like '%春季') then 'A05'"+
                "	     when co.grade = 'A05' and (ep.termName like '%暑假' or ep.termName like '%秋季') then 'A04' else co.grade end grade"+
                "    from Edu_Course co inner join Edu_StudentClass esc on co.id = esc.course left join Edu_Period ep on ep.id = co.period"+
                "    where co.period >= 100 and co.status<>-1 and esc.status in (1,3) "+
                ") t on fsr.subjectCode = t.subject and t.grade = fsr.gradeCode and charIndex(','+ t.cate +',', ','+ fsr.displayCate +',') > 0"+
                "where fsr.cateCode > 200   and fsr.id in ("+ids+") order by fsr.id ";
        return getListUseSql(sql);
    }

    @Override

    public List<Map<String, Object>> getAllBookList() {
	    String sql="SELECT DISTINCT fsr.id,\n" +
                "                fsr.bookName,\n" +
                "                fsr.pop,\n" +
                "                fsr.cateCode,\n" +
                "                ec.name as cateName\n" +
                "FROM im_book fsr\n" +
                "       left join Edu_Cate ec on ec.code = fsr.cateCode\n" +
                "       LEFT JOIN (\n" +
                "  SELECT DISTINCT co.grade,\n" +
                "                  co.subject,\n" +
                "                  CASE\n" +
                "                    WHEN co.cate = 3\n" +
                "                      OR co.cate = 9 THEN\n" +
                "                      '2,' + co.cate\n" +
                "                    ELSE\n" +
                "                      co.cate\n" +
                "                    END cate,\n" +
                "                  CASE\n" +
                "                    WHEN p.termName LIKE '%秋季' THEN\n" +
                "                      'A'\n" +
                "                    WHEN p.termName LIKE '%春季' THEN\n" +
                "                      'B'\n" +
                "                    END type\n" +
                "  FROM Edu_Course co\n" +
                "         INNER JOIN Edu_StudentClass cls ON cls.course = co.id\n" +
                "         LEFT JOIN Edu_Period p ON co.period = p.id\n" +
                "    WHERE co.status <> - 1\n" +
                "         AND cls.status IN (1, 3)\n" +
                "         AND (\n" +
                "        p.termName LIKE '%秋季'\n" +
                "        OR p.termName LIKE '%春季'\n" +
                "      )\n" +
                ") a ON a.subject = fsr.subjectCode\n" +
                "  AND a.grade = fsr.gradeCode\n" +
                "  AND charIndex(\n" +
                "          ',' + fsr.cateCode + ',',\n" +
                "          ',' + a.cate + ','\n" +
                "        ) > 0\n" +
                "  AND fsr.bookName LIKE '%' + a.type\n" +
                "  WHERE\n" +
                "     fsr.cateCode < 100\n" +
                "       AND fsr.id NOT IN (24, 114)\n" +
                "  UNION\n" +
                "  SELECT\n" +
                "     fsr.id,\n" +
                "     fsr.bookName,\n" +
                "     fsr.pop,\n" +
                "     fsr.cateCode,\n" +
                "     ec.name as cateName\n" +
                "  FROM\n" +
                "     im_book fsr\n" +
                "       left join Edu_Cate ec on ec.code = fsr.cateCode\n" +
                "       LEFT JOIN (\n" +
                "       SELECT DISTINCT co.subject,\n" +
                "                       co.cate,\n" +
                "                       CASE\n" +
                "                         WHEN co.grade = 'A01'\n" +
                "                           AND (\n" +
                "                                  co.cate = 7\n" +
                "                                  OR (\n" +
                "                                      co.cate = 9\n" +
                "                                      AND (\n" +
                "                                          ep.termName LIKE '%寒假'\n" +
                "                                          OR ep.termName LIKE '%春季'\n" +
                "                                        )\n" +
                "                                    )\n" +
                "                                ) THEN\n" +
                "                           'A02'\n" +
                "                         WHEN co.grade = 'A02'\n" +
                "                           AND co.cate IN (15, 1)\n" +
                "                           AND (\n" +
                "                                  ep.termName LIKE '%寒假'\n" +
                "                                  OR ep.termName LIKE '%春季'\n" +
                "                                ) THEN\n" +
                "                           'A04'\n" +
                "                         WHEN co.grade = 'A02'\n" +
                "                           AND co.cate IN (9, 7)\n" +
                "                           AND (\n" +
                "                                  ep.termName LIKE '%寒假'\n" +
                "                                  OR ep.termName LIKE '%春季'\n" +
                "                                ) THEN\n" +
                "                           'A03'\n" +
                "                         WHEN co.grade = 'A02'\n" +
                "                           AND co.cate IN (15, 1, 7)\n" +
                "                           AND (\n" +
                "                                  ep.termName LIKE '%暑假'\n" +
                "                                  OR ep.termName LIKE '%秋季'\n" +
                "                                ) THEN\n" +
                "                           'A03'\n" +
                "                         WHEN co.grade = 'A03'\n" +
                "                           AND co.cate IN (15, 1)\n" +
                "                           AND (\n" +
                "                                  ep.termName LIKE '%寒假'\n" +
                "                                  OR ep.termName LIKE '%春季'\n" +
                "                                ) THEN\n" +
                "                           'A05'\n" +
                "                         WHEN co.grade = 'A03'\n" +
                "                           AND co.cate IN (9, 3, 7)\n" +
                "                           AND (\n" +
                "                                  ep.termName LIKE '%寒假'\n" +
                "                                  OR ep.termName LIKE '%春季'\n" +
                "                                ) THEN\n" +
                "                           'A04'\n" +
                "                         WHEN co.grade = 'A03'\n" +
                "                           AND co.cate IN (15, 1, 7, 3)\n" +
                "                           AND (\n" +
                "                                  ep.termName LIKE '%暑假'\n" +
                "                                  OR ep.termName LIKE '%秋季'\n" +
                "                                ) THEN\n" +
                "                           'A04'\n" +
                "                         WHEN co.grade = 'A04'\n" +
                "                           AND co.cate = 9\n" +
                "                           AND (\n" +
                "                                  ep.termName LIKE '%暑假'\n" +
                "                                  OR ep.termName LIKE '%秋季'\n" +
                "                                ) THEN\n" +
                "                           'A03'\n" +
                "                         WHEN co.grade = 'A04'\n" +
                "                           AND co.cate IN (15, 1, 7, 3)\n" +
                "                           AND (\n" +
                "                                  ep.termName LIKE '%寒假'\n" +
                "                                  OR ep.termName LIKE '%春季'\n" +
                "                                ) THEN\n" +
                "                           'A05'\n" +
                "                         WHEN co.grade = 'A05'\n" +
                "                           AND (\n" +
                "                                  ep.termName LIKE '%暑假'\n" +
                "                                  OR ep.termName LIKE '%秋季'\n" +
                "                                ) THEN\n" +
                "                           'A04'\n" +
                "                         ELSE\n" +
                "                           co.grade\n" +
                "                         END grade\n" +
                "       FROM Edu_Course co\n" +
                "              INNER JOIN Edu_StudentClass esc ON co.id = esc.course\n" +
                "              LEFT JOIN Edu_Period ep ON ep.id = co.period\n" +
                "         WHERE co.period >= 100\n" +
                "              AND co.status <> - 1\n" +
                "              AND esc.status IN (1, 3)\n" +
                "       --         AND esc.student = 1819660\n" +
                "     ) t ON fsr.subjectCode = t.subject\n" +
                "       AND t.grade = fsr.gradeCode\n" +
                "       AND charIndex(\n" +
                "               ',' + t.cate + ',',\n" +
                "               ',' + fsr.displayCate + ','\n" +
                "             ) > 0\n" +
                "  WHERE\n" +
                "     fsr.cateCode > 200\n" +
                "  ORDER BY\n" +
                "     fsr.id";
        return getListUseSql(sql);
    }
}
