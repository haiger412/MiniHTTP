package mini.testservice;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
 
public class testdaili {
	public static void main(String[] args) {
	    AA a=new A();
		handl h=new handl();
		h.setInstance(a);
		AA aaa=(AA) Proxy.newProxyInstance(a.getClass().getClassLoader(), a.getClass().getInterfaces(),h );
		aaa.test();
	}
}
interface AA{
	public void test();
}
class A implements AA{
	@Override
	public void test(){
		System.out.println(123);
	}
}

class handl implements InvocationHandler{
	private AA proinstance;
	public void setInstance(AA proinstance){
		this.proinstance=proinstance;
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		if("test".equals(method.getName())){
			System.out.println("fuckyou");
			return null;
		}else{
			return method.invoke(proinstance, args);
		}
	}
	
}

