package edu.sjsu.cmpe275.aop;

import edu.sjsu.cmpe275.aop.exceptions.AccessDeniedExeption;
import edu.sjsu.cmpe275.aop.exceptions.NetworkException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BlogServiceTest {

    /***
     * These are dummy test cases. You may add test cases based on your own need.
     */
    private ClassPathXmlApplicationContext ctx;
    private BlogService blogService;
    private BlogService networkFailedBlogService;
    
    private static final String USER_A = "Alice";
    private static final String USER_B = "Bob";
    private static final String USER_C = "Carl";

    @Before
    public void setUp() {
        ctx = new ClassPathXmlApplicationContext("context.xml");
        blogService = (BlogService) ctx.getBean("blogService");
    }

    @After
    public void cleanUp() {
        ctx.close();
    }

    public void getNetworkFaildBlogServiceBean() {
        networkFailedBlogService = (BlogService) ctx.getBean("networkFailedBlogService");
    }


    @Test
    public void testShareBlog_shareOwnBlog() {
        boolean hasException = false;
        try {
            blogService.shareBlog(USER_A, USER_A, USER_C);
        } catch (Exception e) {
            hasException = true;
            e.printStackTrace();
        }
        Assert.assertFalse(hasException);
    }

    @Test
    public void testShareBlog_shareWithoutPrivilege() {
        boolean hasException = false;
        try {
            blogService.shareBlog(USER_A, USER_B, USER_C);
        } catch (Exception e) {
            hasException = true;
            e.printStackTrace();
            Assert.assertEquals(AccessDeniedExeption.class, e.getClass());
            Assert.assertEquals("Error! " + USER_A + " cannot share blog " + USER_B + " to others. ", e.getMessage());
        }
        Assert.assertTrue(hasException);
    }

    @Test
    public void testShareBlog_shareWithPrivilege() {
        boolean hasException = false;
        try {
            blogService.shareBlog(USER_A, USER_A, USER_B);
        } catch (Exception e) {
            hasException = true;
            e.printStackTrace();
        }
        try {
            blogService.shareBlog(USER_B, USER_A, USER_C);
        } catch (Exception e) {
            hasException = true;
            e.printStackTrace();
        }
        Assert.assertFalse(hasException);
    }

    @Test
    public void testUnshareBlog() {
        boolean hasException = false;
        try {
            blogService.shareBlog(USER_A, USER_A, USER_B);
            blogService.unshareBlog(USER_A, USER_B);
        } catch (Exception e) {
            hasException = true;
            e.printStackTrace();
        }
        Assert.assertFalse(hasException);
    }

    @Test
    public void testUnshareBlog_unshareWithoutSharingBefore() {
        boolean hasException = false;
        try {
            blogService.unshareBlog(USER_A, USER_B);
        } catch (Exception e) {
            hasException = true;
            e.printStackTrace();
            Assert.assertEquals(AccessDeniedExeption.class, e.getClass());
            Assert.assertEquals("Error! " + USER_A + "'s blog has not been shared to " + USER_B + ". ", e.getMessage());
        }
        Assert.assertTrue(hasException);
    }

    @Test
    public void testShareBlog_shareToSelf() {
        boolean hasException = false;
        try {
            blogService.shareBlog(USER_A, USER_A, USER_A);
        } catch (Exception e) {
            hasException = true;
            e.printStackTrace();
        }
        Assert.assertFalse(hasException);
    }

    @Test
    public void testUnshareBlog_unshareToSelf() {
        boolean hasException = false;
        try {
            blogService.unshareBlog(USER_A, USER_A);
        } catch (Exception e) {
            hasException = true;
            e.printStackTrace();
        }
        Assert.assertFalse(hasException);
    }

    @Test
    public void testInvalidID_shortThan3() {
        boolean hasException = false;
        try {
            blogService.shareBlog("a", "abc", "def");
        } catch (Exception e) {
            hasException = true;
            e.printStackTrace();
            Assert.assertEquals(IllegalArgumentException.class, e.getClass());
            Assert.assertEquals("Error! Invalid user ID. ", e.getMessage());
        }
        Assert.assertTrue(hasException);
    }

    @Test
    public void testInvalidID_longerThan16() {
        boolean hasException = false;
        try {
            blogService.unshareBlog("abcdefghijklmnopq", "a");
        } catch (Exception e) {
            hasException = true;
            e.printStackTrace();
            Assert.assertEquals(IllegalArgumentException.class, e.getClass());
            Assert.assertEquals("Error! Invalid user ID. ", e.getMessage());
        }
        Assert.assertTrue(hasException);
    }

    @Test
    public void testInvalidMessage_nullOrEmptyString() {
        boolean hasException = false;
        try {
            blogService.commentOnBlog(USER_A, USER_A, null);
        } catch (Exception e) {
            hasException = true;
            e.printStackTrace();
            Assert.assertEquals(IllegalArgumentException.class, e.getClass());
            Assert.assertEquals("Error! Invalid user ID or message. ", e.getMessage());
        }
        Assert.assertTrue(hasException);

        try {
            blogService.commentOnBlog(USER_A, USER_A, "");
        } catch (Exception e) {
            hasException = true;
            e.printStackTrace();
            Assert.assertEquals(IllegalArgumentException.class, e.getClass());
            Assert.assertEquals("Error! Invalid user ID or message. ", e.getMessage());
        }
        Assert.assertTrue(hasException);
    }

    @Test
    public void testInvalidMessage_longerThan100() {
        boolean hasException = false;
        final String longMessage = "01234567890123456789012345678901234567890123456789" +
                "01234567890123456789012345678901234567890123456789a";
        try {
            blogService.commentOnBlog(USER_A, USER_A, longMessage);
        } catch (Exception e) {
            hasException = true;
            e.printStackTrace();
            Assert.assertEquals(IllegalArgumentException.class, e.getClass());
            Assert.assertEquals("Error! Invalid user ID or message. ", e.getMessage());
        }
        Assert.assertTrue(hasException);
    }

    @Test
    public void testRead() {
        boolean hasException = false;
        try {
            blogService.readBlog(USER_A, USER_A);
        } catch (Exception e) {
            hasException = true;
            e.printStackTrace();
        }
        Assert.assertFalse(hasException);
    }

    @Test
    public void testRead_shared() {
        boolean hasException = false;
        try {
            blogService.shareBlog(USER_A, USER_A, USER_B);
            blogService.readBlog(USER_B, USER_A);
        } catch (Exception e) {
            hasException = true;
            e.printStackTrace();
        }
        Assert.assertFalse(hasException);
    }

    @Test
    public void testRead_notShared() {
        boolean hasException = false;
        try {
            blogService.readBlog(USER_B, USER_A);
        } catch (Exception e) {
            hasException = true;
            e.printStackTrace();
            Assert.assertEquals(AccessDeniedExeption.class, e.getClass());
            Assert.assertEquals("Error! " + USER_B + " cannot read " + USER_A + "'s blog. ", e.getMessage());
        }
        Assert.assertTrue(hasException);
    }

    @Test
    public void testRead_beenSharedLaterUnshared() {
        boolean hasException = false;
        try {
            blogService.shareBlog(USER_A, USER_A, USER_B);
            blogService.unshareBlog(USER_A, USER_B);
            blogService.readBlog(USER_B, USER_A);
        } catch (Exception e) {
            hasException = true;
            e.printStackTrace();
            Assert.assertEquals(AccessDeniedExeption.class, e.getClass());
            Assert.assertEquals("Error! " + USER_B + " cannot read " + USER_A + "'s blog. ", e.getMessage());
        }
        Assert.assertTrue(hasException);
    }

    @Test
    public void testRetry_neverSuccess() throws AccessDeniedExeption {
        boolean hasException = false;
        getNetworkFaildBlogServiceBean();

        try {
            networkFailedBlogService.readBlog(USER_A, USER_A);
        } catch (NetworkException e) {
            hasException = true;
            e.printStackTrace();
        }
        Assert.assertTrue(hasException);
    }

    @Test
    public void testRetry_successAfter2Attempts() throws AccessDeniedExeption {
        boolean hasException = false;
        getNetworkFaildBlogServiceBean();

        try {
            networkFailedBlogService.commentOnBlog(USER_A, USER_A, USER_A);
        } catch (NetworkException e) {
            hasException = true;
            e.printStackTrace();
        }
        Assert.assertFalse(hasException);
    }

}
