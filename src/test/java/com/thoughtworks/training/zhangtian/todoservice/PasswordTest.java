package com.thoughtworks.training.zhangtian.todoservice;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PasswordTest {
    @Test
    public void testPassword() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("zhang"));
        System.out.println(encoder.encode("zhang"));
        System.out.println(encoder.encode("zhang"));
        System.out.println(encoder.encode("zhang"));
        System.out.println(encoder.encode("zhang"));
//        assertTrue();
        assertFalse(encoder.matches("zhangt", encoder.encode("zhang")));
    }
}
