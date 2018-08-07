package com.thoughtworks.training.zhangtian.todoservice.repository;

import com.google.common.collect.ImmutableList;
import com.thoughtworks.training.zhangtian.todoservice.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@DataJpaTest
@RunWith(SpringRunner.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldReturnUserByName() {
        User jianpan = new User(1, "jianpan", "12345", false);
        userRepository.save(jianpan);

        Optional<User> user = userRepository.findOneByName("jianpan");
        assertTrue(user.isPresent());
        assertThat(user.get().getName(), is("jianpan"));
    }
}