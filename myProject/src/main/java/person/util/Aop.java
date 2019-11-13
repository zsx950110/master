package person.util;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.util.RedisInputStream;


@Aspect
@Component("myAop")
public class Aop {
	private Logger logger = LoggerFactory.getLogger(Aop.class);
	private Jedis jedis = null;
	/*	切点就是作用在哪个业务方法上，第一个*位置是返回值类型;然后是要拦截的包的位置，两个..表示选定包及其子包;
    然后是某个类，*表示包中所有类；这个*(..)表示所有方法以及任意返回值*/
	@Pointcut("execution( * person..*.*(..))")
	public void myPoint() {
		logger.info("AOP开启日志》》》》》");
	}
	@Before("myPoint()")
	public void beforeService(JoinPoint joinPoint){
		logger.info("*********前置增强处理被执行**************");
		logger.info("》》》》》连接点对象名："+joinPoint.getTarget().getClass().getSimpleName());
		logger.info("连接点方法:"+joinPoint.getSignature());
		StringBuffer args = new StringBuffer();
		if(joinPoint.getArgs().length!=0) {
			for(int i = 0; i < joinPoint.getArgs().length; i++){
				args.append(joinPoint.getArgs()[i]) ;
				if(i<joinPoint.getArgs().length){
					args.append(",");
				}

			}
			logger.info("》》》》》》》连接点方法参数值："+args);
		}
	}


	@After("myPoint()")
	public void after(){
		logger.info("*******后置通知被执行**********");
	}


	@AfterThrowing(throwing = "ex",pointcut = "myPoint()")
	public void afterThrowing(JoinPoint joinPoint,Exception ex){

		logger.info("*****方法异常时执行**********");
		logger.info("异常信息："+ex.getMessage(),ex);
	}
	@AfterReturning(returning = "re",pointcut = "myPoint()")
	public void afterReturning(Object re){
		logger.info("****方法返回后通知被执行****");
		//re是一个数组
		logger.info("返回值：{}",re);
	}
/*
	@Around("myPoint()")
	public void around(){
		logger.info("arround增强执行》》》》》》》》》》》》》");
	}
*/

}
