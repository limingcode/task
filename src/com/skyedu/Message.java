package com.skyedu;

/**
 * 消息处理
 * @author Jinmingming jin_ming_ming@qq.com
 * @date 2014-7-22
 */
public class Message {
	
	private Type type;
	private String content;
	
	public Message(Type type, String content) {
		this.type = type;
		this.content = content;
	}

	public Message(Type type, String content, Object[] args) {
		this.type = type;
		this.content = SpringUtils.getMessage(content, args);
	}

	/**
	 * 成功
	 * @param content
	 * @param args
	 * @return
	 */
	public static Message success(String content, Object[] args) {
		return new Message(Type.success, content, args);
	}

	/**
	 * 警告
	 * @param content
	 * @param args
	 * @return
	 */
	public static Message warn(String content, Object[] args) {
		return new Message(Type.warn, content, args);
	}

	/**
	 * 错误
	 * @param content 资源文件中的key
	 * @param args 对应资源文件中key值{0}{1}
	 * @return
	 */
	public static Message error(String content, Object[] args) {
		return new Message(Type.error, content, args);
	}

	public Type getType() {
		return this.type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return SpringUtils.getMessage(this.content, new Object[0]);
	}

	/**
	 * 消息类型枚举
	 */
	public enum Type {
		success, warn, error;
	}
}