package edu.sjsu.cmpe275.aop.aspect;

import edu.sjsu.cmpe275.aop.exceptions.NetworkException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class RetryAspect {
    /***
     * Following is a dummy implementation of this aspect.
     * You are expected to provide an actual implementation based on the requirements, including adding/removing advices as needed.
     */

    private static final int MAX_RETRY = 2;

	/**
	 * Handles network issue by retry MAX_RETRY times. If still not working, throw NetworkException.
	 *
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("execution(* edu.sjsu.cmpe275.aop.BlogService.*(..))")
	public Object handleNetworkIssue(ProceedingJoinPoint joinPoint) throws Throwable {

		int retryAttempts = 0;
		NetworkException networkException;

		do {
			retryAttempts++;
			try {
				return joinPoint.proceed();
			}
			catch(NetworkException e) {
				networkException = e;
			}
		} while(retryAttempts < MAX_RETRY);

		throw networkException;
	}

}