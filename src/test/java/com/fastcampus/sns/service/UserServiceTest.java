package com.fastcampus.sns.service;

import com.fastcampus.sns.exception.ErrorCode;
import com.fastcampus.sns.exception.SnsApplicationException;
import com.fastcampus.sns.fixture.UserEntityFixture;
import com.fastcampus.sns.model.entity.UserEntity;
import com.fastcampus.sns.repository.UserEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest

public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserEntityRepository userEntityRepository;

    @MockBean
    private BCryptPasswordEncoder encoder;

    @Test
    void 회원가입이_정상적으로_동작(){
        String username = "username";
        String password = "password";

        //mocking
        when(userEntityRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(encoder.encode(password)).thenReturn("encrypt_password");
        when(userEntityRepository.save(any())).thenReturn((UserEntityFixture.get(username,password,1)));


        Assertions.assertDoesNotThrow(() -> userService.join(username,password));
    }
    @Test
    void 회원가입이_username으로_가입한유저가_있는경우(){
        String username = "username";
        String password = "password";

        UserEntity fixture = UserEntityFixture.get(username, password,1);

        //mocking
        when(userEntityRepository.findByUsername(username)).thenReturn(Optional.of(fixture));
        when(encoder.encode(password)).thenReturn("encrypt_password");
        when(userEntityRepository.save(any())).thenReturn(Optional.of(fixture));


        SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class, () -> userService.join(username, password));
        Assertions.assertEquals(ErrorCode.DUPLICATED_USER_NAME, e.getErrorCode());

    }

    @Test
    void 로그인이_정상적으로_동작(){
        String username = "username";
        String password = "password";


        UserEntity fixture = UserEntityFixture.get(username, password,1);
        //mocking
        when(userEntityRepository.findByUsername(username)).thenReturn(Optional.of(fixture));
        when(encoder.matches(password, fixture.getPassword())).thenReturn(true);


        Assertions.assertDoesNotThrow(() -> userService.login(username,password));
    }
    @Test
    void 로그인시_username으로_회원가입된_유저가_없는경우(){
        String username = "username";
        String password = "password";


        //mocking
        when(userEntityRepository.findByUsername(username)).thenReturn(Optional.empty());


        SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class, () -> userService.login(username, password));
        Assertions.assertEquals(ErrorCode.USER_NOT_FOUNd, e.getErrorCode());
    }
    @Test
    void 로그인시_username으로_회원가입된_유저가_있지만_password가_틀린경우(){
        String username = "username";
        String password = "password";
        String wrongPassword = "wrongPassword";

        UserEntity fixture = UserEntityFixture.get(username, password,1);
        //mocking
        when(userEntityRepository.findByUsername(username)).thenReturn(Optional.of(fixture));


        SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class, () -> userService.login(username, wrongPassword));
        Assertions.assertEquals(ErrorCode.INVALID_PASSWORD, e.getErrorCode());
    }
}
