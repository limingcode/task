package com.skyedu.sdk.wechat.model;

import java.util.List;

import com.skyedu.sdk.wechat.XStreamCDATA;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamConverters;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamImplicitCollection;
import com.thoughtworks.xstream.annotations.XStreamInclude;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * 被动响应消息-图文消息
 * 
 * @author xj.chen
 * @date 2015-10-16
 */
@XStreamAlias("xml")
public class NewsOutputMessage extends OutputMessage {

	private static final long serialVersionUID = 2028218899332379011L;
	
	/**
	 * 图文消息
	 */
	@XStreamAlias("ArticleCount")
	private int articleCount;
	
	@XStreamAlias("Articles")
	private List<Item> item;
	
	public NewsOutputMessage(String toUserName, String fromUserName,
			List<Item> articles) {
		super.setToUserName(toUserName);
		super.setFromUserName(fromUserName);
		super.setMsgType("news");
		this.item = articles;
		this.articleCount = articles==null? 0:articles.size();
	}

	public int getArticleCount() {
		return articleCount;
	}

	public void setArticleCount(int articleCount) {
		this.articleCount = articleCount;
	}

	public List<Item> getItem() {
		return item;
	}

	public void setItem(List<Item> item) {
		this.item = item;
	}


}