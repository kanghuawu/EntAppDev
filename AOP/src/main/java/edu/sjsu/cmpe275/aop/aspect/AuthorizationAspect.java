package edu.sjsu.cmpe275.aop.aspect;

import edu.sjsu.cmpe275.aop.exceptions.AccessDeniedExeption;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.Ordered;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Aspect
public class AuthorizationAspect implements Ordered {
    /***
     * Following is a dummy implementation of this aspect.
     * You are expected to provide an actual implementation based on the requirements, including adding/removing advises as needed.
     */

	private final Map<String, List<String>> sharingRelation = new HashMap<String, List<String>>();

    /**
     * One can only read his own blog or blog which has been shared with him.
     *
     * @param joinPoint
     * @throws AccessDeniedExeption
     */
    @Before("execution(public edu.sjsu.cmpe275.aop.Blog edu.sjsu.cmpe275.aop.BlogService.readBlog(..))")
    public void checkReadBlog(JoinPoint joinPoint) throws AccessDeniedExeption {
        final Object[] args = joinPoint.getArgs();
        final String userId = (String) args[0];
        final String blogUserId = (String) args[1];

        if (!equals(userId, blogUserId) && !hasBeenShared(blogUserId, userId)) {
            throw new AccessDeniedExeption("Error! " + userId + " cannot read " + blogUserId + "'s blog. ");
        }
    }

	/**
	 * One can share his own blog to anyone.
	 * One can share other's blog to anyone only if he has access to the blog.
     * Throw AccessDeniedException
     *
	 * @param joinPoint
	 */
	@Before("execution(public void edu.sjsu.cmpe275.aop.BlogService.shareBlog(..))")
	public void checkShareBlog(JoinPoint joinPoint) throws AccessDeniedExeption {
        final Object[] args = joinPoint.getArgs();
        final String userId = (String) args[0];
        final String blogUserId = (String) args[1];
        final String targetUserId = (String) args[2];

        if (equals(userId, blogUserId) || hasBeenShared(blogUserId, userId)) {
            share(blogUserId, targetUserId);
        } else {
            throw new AccessDeniedExeption("Error! " + userId + " cannot share blog " + blogUserId + " to others. ");
        }
	}

    /**
     * One can only unshare his own blog.
     *
     * @param joinPoint
     */
	@Before("execution(public void edu.sjsu.cmpe275.aop.BlogService.unshareBlog(..))")
	public void checkUnshareBlog(JoinPoint joinPoint) throws AccessDeniedExeption {
        final Object[] args = joinPoint.getArgs();
        final String userId = (String) args[0];
        final String targetUserId = (String) args[1];

        if (equals(userId, targetUserId)) {
            // do nothing
        } else if (hasBeenShared(userId, targetUserId)) {
            unshare(userId, targetUserId);
        } else {
            throw new AccessDeniedExeption("Error! " + userId + "'s blog has not been shared to " + targetUserId + ". ");
        }
	}

	private void share(final String from, final String to) {
		List<String> sharedList = sharingRelation.get(from);
		if (sharedList == null) {
			sharedList = new ArrayList<String>();
		}
		sharedList.add(to);
		sharingRelation.put(from, sharedList);
	}

	private boolean hasBeenShared(final String from, final String to) {
		List<String> sharedList = sharingRelation.get(from);
		if (sharedList != null) {
			return sharedList.contains(to);
		}
		return false;
	}

	private void unshare(final String from, final String to) {
		List<String> sharedList = sharingRelation.get(from);
		if (sharedList != null) {
			sharedList.remove(to);
			sharingRelation.put(from, sharedList);
		}
	}

	private boolean equals(final String a, final String b) {
		if (isEmpty(a) && isEmpty(b)) {
			return true;
		} else {
			return a.equalsIgnoreCase(b);
		}
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
        return 2;
    }

}
