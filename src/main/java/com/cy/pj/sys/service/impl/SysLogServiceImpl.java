package com.cy.pj.sys.service.impl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.cy.pj.common.bo.PageObject;
import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.sys.dao.SysLogDao;
import com.cy.pj.sys.entity.SysLog;
import com.cy.pj.sys.service.SysLogService;

@Service
public class SysLogServiceImpl implements SysLogService {
     
	@Autowired
	private SysLogDao sysLogDao;

	//这里异步写日志操作，同样使用的是AOP
	//@Async描述的方法为切入点
	//这个切入点上执行的异步操作为通知(Advice)
	@Async //由此注解描述的方法，用于告诉spring框架这个方法要运行一个异步线程上(此线程由spring线程池提供)。
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public void saveObject(SysLog entity) {
		//获取线程名称
		String tName=Thread.currentThread().getName();
		System.out.println("SysLogServiceImpl.saveObject.thread-->"+tName);
		//模拟耗时操作
		try{Thread.sleep(5000);}catch(Exception e) {}
		sysLogDao.insertObject(entity);
	}
	
	@Override
	public int deleteObjects(Integer... ids) {
		//1.参数校验
		if(ids==null||ids.length==0)
			throw new IllegalArgumentException("参数值无效");
		//2.执行删除操作
		int rows=sysLogDao.deleteObjects(ids);
		//3.验证删除结果
		if(rows==0)
			throw new ServiceException("记录可能不存在了");
		return rows;
	}
	
	@Override
	public  PageObject<SysLog> findPageObjects(String username, 
			Integer pageCurrent) throws ServiceException{
		//1.参数校验(思考username允许为空吗？允许)
		//请问如下参数校验是否可以颠倒"||"符号两侧的顺序？(不可以)
		if(pageCurrent==null||pageCurrent<1)
			throw new IllegalArgumentException("当前页码值不合法");
		//2.查询总记录数，并进行校验
		//假如在如下行出现空指针，可能问题的原因什么？sysLogDao变量为空
		
		int rowCount=sysLogDao.getRowCount(username);
		if(rowCount==0)
            throw new ServiceException("没有对应记录");//此异常如何定义？
		//3.查询当前页记录
		//定义每页最多要显示的记录数
		int pageSize=5;
		//计算当前页查询的起始位置
		int startIndex=(pageCurrent-1)*pageSize;
		List<SysLog> records=
		sysLogDao.findPageObjects(username, startIndex, pageSize);
		//4.对业务层查询结果进行处理和封装
		//注意:PageObject构造方法传参的顺序由构造方法定义时参数的顺序决定
		return new PageObject<>(records, rowCount,pageSize, pageCurrent);
	}

}




