package com.cy.pj.common.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * VO:(View Object/Value Object),在当前项目中我们借助VO封装视图层要呈现数据
 */
@Data
@NoArgsConstructor
public class JsonResult {
	/**消息表示状态码*/
	private Integer state;//1 表示正确数据，0表示异常数据
	/**状态码对应的具体信息*/
	private String message;
	/**数据(基于此属性封装业务层返回的数据)*/
	private Object data;

	public JsonResult(String message){
		this.state=1;
		this.message=message;
		//方法2
		//setState(1);
		//setMessage(message);
	}
	public JsonResult(Object data){
		this.state=1;
		this.data=data;
	}
	//基于此构造方法进行错误信息的初始化
	public JsonResult(Throwable e){//Throwable是所有异常类的父类
		this.state=0;//error
		this.message=e.getMessage();//获取异常信息
	}
	

}
