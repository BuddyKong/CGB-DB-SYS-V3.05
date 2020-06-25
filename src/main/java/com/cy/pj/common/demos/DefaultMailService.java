package com.cy.pj.common.demos;

class LogAspect{
	void before() {
		System.out.println("start:"+System.currentTimeMillis());
	}
	void after() {
		System.out.println("end:"+System.currentTimeMillis());
	}
}
//子类(代理对象->CGLIB代理对象就类似于这种形式)
class LogMailService extends DefaultMailService{
	private LogAspect logAspect;
	public LogMailService(LogAspect logAspect) {
		this.logAspect=logAspect;
	}
	@Override
	public void send(String msg) {
		logAspect.before();
		super.send(msg);
		logAspect.after();
	}
}
//目标对象
public class DefaultMailService implements MailService {
	@Override
	public void send(String msg) {
		//System.out.println("start:"+System.currentTimeMillis());
		System.out.println("send " +msg);
		//System.out.println("end:"+System.currentTimeMillis());
	}

}

