package edu.sjsu.cmpe275.aop;

import edu.sjsu.cmpe275.aop.exceptions.AccessDeniedExeption;
import edu.sjsu.cmpe275.aop.exceptions.NetworkException;

public class NetworkFailedBlogService implements BlogService {

    private static int retry = 0;

    public Blog readBlog(String userId, String blogUserId) throws AccessDeniedExeption, IllegalArgumentException, NetworkException {
        throw new NetworkException("");
    }

    public void commentOnBlog(String userId, String blogUserId, String message) throws AccessDeniedExeption, IllegalArgumentException, NetworkException {
        System.out.println("commentOnBlog, retry: " + retry);
        if (retry < 1) {
            retry++;
            throw new NetworkException("");
        } else {
            return;
        }
    }

    public void shareBlog(String userId, String blogUserId, String targetUserId) throws AccessDeniedExeption, IllegalArgumentException, NetworkException {
    }

    public void unshareBlog(String userId, String targetUserId) throws AccessDeniedExeption, IllegalArgumentException, NetworkException {
    }
}
