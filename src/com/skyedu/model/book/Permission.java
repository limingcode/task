package com.skyedu.model.book;

import com.image.tag.model.ImBook;
import com.skyedu.model.Category;
import com.skyedu.model.EduStudent;
import com.skyedu.model.Result;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @Description xxx
 * @author: LiMing
 * @date: 2019/10/28  11:32
 * 对应的书籍权限表
 */

@Entity
@Table(name = "IM_Permission")
public class Permission implements Serializable {
    //权限ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //班级ID
    private Integer gradeId;
    //学生ID
    private Integer teacherId;
    //书籍ID
    private Integer bookId;
    //权限对应的级别ID  如培优可以读取XXXXX
    private Integer levelCateId;
    //操作时间
    private Date operationTime;
    //最后操作人
    private String operationPeople;
    /**
     *
     * 學期表:EduPeriod  開始-結束=時段
     *  班級表--course
     * 老師表:EduTeacher
     * 校区表：EduDepa
     * 年級表：EduGrade
     * 層次表：EduCate
     * 權限和書籍表im_permisson im_book
     */
    //权限对应的书籍
    @OneToMany(mappedBy = "permission", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ImBook> imBook;



}
