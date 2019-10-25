package com.skyedu.sdk.wechat;

import java.io.Writer;
import java.lang.reflect.Field;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * 微信处理注解工具
 * @author Jinmingming jin_ming_ming@qq.com
 * @date 2015-10-17
 */
public class WXXStreamHelper {
	
	protected static String PREFIX_CDATA = "<![CDATA[";
	protected static String SUFFIX_CDATA = "]]>";
	
	public static XStream createXstream() {  
        return new XStream(new XppDriver() {  
            @Override  
            public HierarchicalStreamWriter createWriter(Writer out) {  
                return new PrettyPrintWriter(out) {  
                    boolean cdata = false;  
                    Class<?> targetClass = null;  
                    @Override  
                    public void startNode(String name,  
                            @SuppressWarnings("rawtypes") Class clazz) {  
                        super.startNode(name, clazz);  
                        //业务处理，对于用XStreamCDATA标记的Field，需要加上CDATA标签  
                        if(!name.equals("xml")&&!name.equals("item")){//代表当前处理节点是class，用XstreamAlias把class的别名改成xml，下面的代码片段有提到  
                            cdata = needCDATA(targetClass, name);  
                        }else{  
                            targetClass = clazz;  
                        }  
                    }  
  
                    @Override  
                    protected void writeText(QuickWriter writer, String text) {  
                        if (cdata) {  
							writer.write(PREFIX_CDATA + text + SUFFIX_CDATA);  
                        } else {  
                            writer.write(text);  
                        }  
                    }  
                };  
            }  
        });  
    }
	
	private static boolean needCDATA(Class<?> targetClass, String fieldAlias){  
        boolean cdata = false;  
        //first, scan self  
        cdata = existsCDATA(targetClass, fieldAlias);  
        if(cdata) return cdata;  
        //if cdata is false, scan supperClass until java.lang.Object  
        Class<?> superClass = targetClass.getSuperclass();  
        while(!superClass.equals(Object.class)){  
            cdata = existsCDATA(superClass, fieldAlias);  
            if(cdata) return cdata;  
            superClass = superClass.getClass().getSuperclass();  
        }  
        return false;  
    }  
	
	private static boolean existsCDATA(Class<?> clazz, String fieldAlias){  
        //scan fields  
        Field[] fields = clazz.getDeclaredFields();  
        for (Field field : fields) {  
            //1. exists XStreamCDATA  
            if(field.getAnnotation(XStreamCDATA.class) != null ){  
                XStreamAlias xStreamAlias = field.getAnnotation(XStreamAlias.class);  
                //2. exists XStreamAlias  
                if(null != xStreamAlias){  
                    if(fieldAlias.equals(xStreamAlias.value()))//matched  
                        return true;  
                }else{// not exists XStreamAlias  
                    if(fieldAlias.equals(field.getName()))  
                        return true;  
                }  
            }  
        }  
        return false;  
    }  
}
