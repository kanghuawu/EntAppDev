package edu.sjsu.cmpe275.lab1;

import org.junit.Before;
import org.junit.Test;
import java.util.UUID;

public class SecretServiceTest {
    SecretService secretService;

    @Before
    public void setUp() throws Exception {
        //...
    }

    @Test(expected = UnauthorizedException.class)
    public void testA() {
        System.out.println("testA");
        UUID aliceSecret = secreteService.storeSecrete(“Alice”, new Secret());
        secretService.readSecret("Bob", aliceSecret);
    }

    @Test
    public void testB() {
        System.out.println("testB");
        UUID aliceSecret = secreteService.storeSecrete(“Alice”, new Secret());
        secretService.shareSecret("Alice", aliceSecret, "Bob");
        secretService.readSecret("Bob", aliceSecret);
    }

    //...
}
