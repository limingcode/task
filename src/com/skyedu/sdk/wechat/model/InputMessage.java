package com.skyedu.sdk.wechat.model;

/**
 * POST的XML数据包转换为消息接受对象
 * 
 * <p>由于POST的是XML数据包，所以不确定为哪种接受消息，<br/>
 * 所以直接将所有字段都进行转换，最后根据<tt>MsgType</tt>字段来判断取何种数据</p>
 * 
 * @author Jinmingming jin_ming_ming@qq.com
 * @date 2015-10-16
 */
public class InputMessage {

	// 开发者微信号
    private String ToUserName;
    // 发送方帐号openid
    private String FromUserName;
    // 消息创建时间
    private Long CreateTime;
    private String MsgType = "text";
    private Long MsgId;
    // 文本消息
    private String Content;
    // 图片消息
    private String PicUrl;
    // 位置消息
    private String LocationX;
    private String LocationY;
    private Long Scale;
    private String Label;
    // 链接消息
    private String Title;
    private String Description;
    private String Url;
    // 语音信息
    private String MediaId;
    private String Format;
    private String Recognition;
    // 事件
    private String Event;
    // 事件KEY值
    private String EventKey;
    // 二维码的ticket
    private String Ticket;
    //点击的菜单id
    private String MenuId;
    
    // 模板消息内容
    private Long MsgID;
    private String Status;

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public Long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Long createTime) {
        CreateTime = createTime;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    public Long getMsgId() {
        return MsgId;
    }

    public void setMsgId(Long msgId) {
        MsgId = msgId;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

    public String getLocationX() {
        return LocationX;
    }

    public void setLocationX(String locationX) {
        LocationX = locationX;
    }

    public String getLocationY() {
        return LocationY;
    }

    public void setLocationY(String locationY) {
        LocationY = locationY;
    }

    public Long getScale() {
        return Scale;
    }

    public void setScale(Long scale) {
        Scale = scale;
    }

    public String getLabel() {
        return Label;
    }

    public void setLabel(String label) {
        Label = label;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getEvent() {
        return Event;
    }

    public void setEvent(String event) {
        Event = event;
    }

    public String getEventKey() {
        return EventKey;
    }

    public void setEventKey(String eventKey) {
        EventKey = eventKey;
    }

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    public String getFormat() {
        return Format;
    }

    public void setFormat(String format) {
        Format = format;
    }

    public String getRecognition() {
        return Recognition;
    }

    public void setRecognition(String recognition) {
        Recognition = recognition;
    }

    public String getTicket() {
        return Ticket;
    }

    public void setTicket(String ticket) {
        Ticket = ticket;
    }

	public String getMenuId() {
		return MenuId;
	}

	public void setMenuId(String menuId) {
		MenuId = menuId;
	}
}