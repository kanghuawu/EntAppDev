package edu.sjsu.cmpe275.aop;

import edu.sjsu.cmpe275.aop.exceptions.AccessDeniedExeption;
import edu.sjsu.cmpe275.aop.exceptions.NetworkException;
import sun.nio.ch.Net;

public class BlogServiceImpl implements BlogService{
	 
	/***
     * Following is a dummy implementation.
     * You can tweak the implementation to suit your need, but this file is NOT part of the submission.
     */

	@Override
	public Blog readBlog(String userId, String blogUserId) throws AccessDeniedExeption, NetworkException {
		// TODO Auto-generated method stub
		System.out.printf("User %s requests to read user %s's blog\n", userId, blogUserId);
		return null;
	}

	@Override
	public void shareBlog(String userId, String blogUserId, String targetUserId)
			throws AccessDeniedExeption, NetworkException {
		System.out.printf("User %s shares user %s's blog with user %s\n", userId, blogUserId, targetUserId);
		// TODO Auto-generated method stub
	}

	@Override
	public void unshareBlog(String userId, String targetUserId) throws AccessDeniedExeption, NetworkException {
		// TODO Auto-generated method stub
		System.out.printf("User %s unshares his/her own blog with user %s\n", userId, targetUserId);
	}

	@Override
	public void commentOnBlog(String userId, String blogUserId, String message)
			throws AccessDeniedExeption, IllegalArgumentException, NetworkException {
		// TODO Auto-generated method stub
		System.out.printf("User %s commented on %s's blog\n", userId, blogUserId);		
	}

}
