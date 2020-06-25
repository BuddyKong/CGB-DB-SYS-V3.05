package com.cy.pj.common.bo;
import java.io.Serializable;
import lombok.Data;
/**
 * 借助此对象封装页面上<input type=checkbox>对象需要的数据
 * @author qilei
 *
 */
@Data
public class CheckBox implements Serializable{
	private static final long serialVersionUID = 5938324948577490244L;
	private Integer id;
	private String name;
	  
}
