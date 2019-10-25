package com.skyedu.controller.other;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

import com.image.tag.dao.ImageTagDao;
import com.skyedu.dao.impl.OtherUserDaoImpl;
import com.skyedu.model.other.OtherLoginLogModel;
import com.skyedu.model.other.OtherUserModel;
import com.util.DataUtils;
import com.util.HttpClientUtils;
import com.util.StringUtil;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("otherUser")
public class OtherUserController {

    @Resource
    private OtherUserDaoImpl OtherUserDaoImp;

    @Resource
    private ImageTagDao imageTagDao;
    @ResponseBody
    @RequestMapping(value="/ceshi",method = RequestMethod.POST)
    public String getStudent(HttpServletRequest request) {
        imageTagDao.addLesson(request.getParameter("josn"));
        return "ok";
    }
    @RequestMapping("/login")
    @ResponseBody
    public Map<String,Object> login(String username, String password){
        try {
            Map<String, String> objectObjectHashMap = new HashMap<>();
            objectObjectHashMap.put("username", username);
            objectObjectHashMap.put("password", password);
            String result= HttpClientUtils.post("http://i.jieruiedu.com/jr/third/lt/student/login",objectObjectHashMap);
//            String result= HttpClientUtils.post("http://t.jieruiedu.com/jr/third/lt/student/login",objectObjectHashMap);

            OtherUserModel otherUserModel= new Gson().fromJson(result, OtherUserModel.class);
            OtherLoginLogModel otherLoginLogModel = new OtherLoginLogModel();
            otherLoginLogModel.setRequestBody(new Gson().toJson(objectObjectHashMap));
            otherLoginLogModel.setResponseBody(result);
            otherLoginLogModel.setStatus(otherUserModel.getErrcode()==0?1:2);
            OtherUserDaoImp.save(otherLoginLogModel);
            if (otherUserModel.getErrcode()==0) {
//                HashMap<String, Object> stringObjectHashMap = new HashMap<>();
//                stringObjectHashMap.put("bookList",imageTagDao.getBookListById(otherUserModel.getData().getBookList()));
//                System.out.println(JSONObject.toJSONString(stringObjectHashMap));
                String bookList=otherUserModel.getData().getBookList().toString();
                if (!StringUtil.isEmpty(bookList)) {
                    if (bookList.lastIndexOf(",") == bookList.length() - 1) {
                        bookList = bookList.substring(0, bookList.length() - 1);
                    }
                    otherUserModel.getData().setBookList(imageTagDao.getBookListById(bookList));
                }
                return DataUtils.getInstance(100,otherUserModel.getData());
            }else {
                errorModel errorModel= new Gson().fromJson(result, errorModel.class);
                return DataUtils.getInstance(101,errorModel.errmsg);
            }
        }catch (Exception e){
            e.printStackTrace();
            return DataUtils.getInstance(110,"系统异常");
        }
    }
    public static class errorModel implements Serializable {

        private int errcode;
        private String errmsg;

        public int getErrcode() {
            return errcode;
        }

        public void setErrcode(int errcode) {
            this.errcode = errcode;
        }

        public String getErrmsg() {
            return errmsg;
        }

        public void setErrmsg(String errmsg) {
            this.errmsg = errmsg;
        }
    }
}
