package com.cy.pj.common.bo;//business object
import java.io.Serializable;
import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * POJO(VO/BO/DTO/DO)中的BO对象：
 * 
   1)所有的POJO对象属性定义都用对象类型
   2)所有的POJO对象属性默认值无需指定
   3)所有的POJO对象需要提供set/get/ toString 方法
   4)所有的POJO对象都需要提供无参构造函数
   5)所有的POJO对象的构造方法不要写任何业务逻辑。
 
 * 此对象为业务层向外输出的一个BO对象，用于封装业务执行的结果
 * 泛型：类上定义的泛型用于约束类中属性，方法参数，方法返回值类型。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageObject<T> implements Serializable{
	  private static final long serialVersionUID = 5727659641634783999L;
	  /**当前页记录*/
      private List<T> records;
      /**总行数(通过查询获得)*/
      private Integer rowCount;
      /**总页数*/
      private Integer pageCount;
      /**每页要显示多少条记录*/
      private Integer pageSize;
      /**当前页的页码值*/
      private Integer pageCurrent;
	  public PageObject(List<T> records, Integer rowCount,Integer pageSize, Integer pageCurrent) {
		super();
		this.records = records;
		this.rowCount = rowCount;
		this.pageSize = pageSize;
		this.pageCurrent = pageCurrent;
		//计算分页：方案一
//		this.pageCount=rowCount/pageSize;
//		if(rowCount%pageSize!=0) {
//			this.pageCount++;
//		}
		//计算分析：方案二
		this.pageCount=(rowCount-1)/pageSize+1;
	 }
//	 public PageObject(List<T> records, Integer rowCount, Integer pageCount, Integer pageSize, Integer pageCurrent) {
//		super();
//		this.records = records;
//		this.rowCount = rowCount;
//		this.pageCount = pageCount;
//		this.pageSize = pageSize;
//		this.pageCurrent = pageCurrent;
//	 }
	 
	 
}
