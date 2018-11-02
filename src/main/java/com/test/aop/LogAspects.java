package com.test.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;

/**
 * 日志切面类
 *
 * @author xuan
 * @date 2018/11/1
 */
@Aspect
public class LogAspects {
	/**
	 * 公共的切入点表达式
	 * 1、本类引用
	 * 2、其他的切面引用
	 *
	 * @author xuan
	 * @date 2018/11/1
	 */
	@Pointcut(value = "execution(public int com.test.aop.MathCalculator.*(..))")
	public void pointCut(){}

	/**
	 * 前置通知
	 *
	 * 在目标方法之前切入，切入点表达式（指在哪个方法切入）
	 * joinPoint参数一定要出现在参数列表第一位，放在后面会报错
	 *
	 * @author xuan
	 * @date 2018/11/1
	 */
	@Before(value = "pointCut()")
	public void logStart(JoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().getName();
		Object[] args = joinPoint.getArgs();
		System.out.println(methodName + "运行。。。参数列表是：" + Arrays.toString(args));
	}

	@After(value = "pointCut()")
	public void logEnd() {
		System.out.println("除法结束。。。");
	}

	@AfterReturning(value = "pointCut()", returning = "result")
	public void logReturn(JoinPoint joinPoint, Object result) {
		String methodName = joinPoint.getSignature().getName();
		System.out.println(methodName + "正常返回。。。计算结果：" + result);
	}

	@AfterThrowing(value = "pointCut()", throwing = "exception")
	public void logException(JoinPoint joinPoint, Exception exception){
		String methodName = joinPoint.getSignature().getName();
		System.out.println(methodName + "异常，异常信息：" + exception);
	}

}
