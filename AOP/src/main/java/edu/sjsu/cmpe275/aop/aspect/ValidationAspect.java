package edu.sjsu.cmpe275.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;

@Aspect
public class ValidationAspect implements Ordered {
    /***
     * Following is a dummy implementation of this aspect.
     * You are expected to provide an actual implementation based on the requirements, including adding/removing advices as needed.
     */

	@Pointcut("execution(public edu.sjsu.cmpe275.aop.Blog edu.sjsu.cmpe275.aop.BlogService.readBlog(..))")
	private void pointReadBlog(){}

	@Pointcut("execution(public void edu.sjsu.cmpe275.aop.BlogService.shareBlog(..))")
	private void pointShareBlog(){}

	@Pointcut("execution(public void edu.sjsu.cmpe275.aop.BlogService.unshareBlog(..))")
	private void pointUnshareBlog(){}

	@Pointcut("execution(public void edu.sjsu.cmpe275.aop.BlogService.commentOnBlog(..))")
	private void pointCommentOn(){}

	@Pointcut("pointReadBlog() || pointShareBlog() || pointUnshareBlog()")
	private void pointReadShareAndUnshare(){}

    /**
     * Check ID in methods readBlog, shareBlog, and commentOnBlog.
     *
     * @param joinPoint
     */
	@Before("pointReadShareAndUnshare()")
	public void validateParameterOfReadShareAndUnshare(JoinPoint joinPoint) {
		for(final Object arg : joinPoint.getArgs()) {
			if (!isValidId(arg)) {
				throw new IllegalArgumentException("Error! Invalid user ID. ");
			}
		}
	}

    /**
     * Check ID and message in method commentOnBlog.
     *
     * @param joinPoint
     */
	@Before("pointCommentOn()")
	public void validateParameterOfCommentOn(JoinPoint joinPoint) {
		final Object[] args = joinPoint.getArgs();
		if (!isValidId(args[0]) || !isValidId(args[1]) || !isValidMessage(args[2])) {
			throw new IllegalArgumentException("Error! Invalid user ID or message. ");
		}
	}

	private boolean isValidMessage(Object arg) {
		if (arg instanceof String) {
			final String message = (String) arg;
			if (!isEmpty(arg) && message.length() > 0 && message.length() <= 100 ) {
				return true;
			}
		}
		return false;
	}

	private boolean isValidId(Object arg) {
		if (arg instanceof String) {
			final String id = (String) arg;
			if (!isEmpty(id) && id.length() >= 3 && id.length() <= 16) {
				return true;
			}
		}
		return false;
	}

	private boolean isEmpty(final Object obj) {
		if (obj == null) {
			return true;
		}
		if (obj instanceof String) {
			final String objString = (String) obj;
			if(objString.trim().length() == 0) {
				return true;
			}
		}
		return false;
	}

    public int getOrder() {
        return 1;
    }


}