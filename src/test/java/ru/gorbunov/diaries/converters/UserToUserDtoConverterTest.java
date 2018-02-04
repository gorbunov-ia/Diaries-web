package ru.gorbunov.diaries.converters;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import ru.gorbunov.diaries.controller.vm.UserDto;
import ru.gorbunov.diaries.domain.User;

import java.util.Random;

/**
 * Test for UserToUserDtoConverter class.
 *
 * @author Gorbunov.ia
 */
@SpringBootTest
public class UserToUserDtoConverterTest {

    /**
     * Error message.
     */
    private static final String CONVERT_ERR_MSG = "Data lost when converted User to UserDto";

    /**
     * Converter instance.
     */
    private final UserToUserDtoConverter converter = new UserToUserDtoConverter();

    /**
     * Test convert empty User object.
     */
    @Test
    public void testConvertEmptyObject() {
        UserDto userDto = converter.convert(new User());
        Assert.assertNotNull("UserDto object is null, but User not null.", userDto);
    }

    /**
     * Test convert User object with filled field.
     */
    @Test
    public void testConvertFullObject() {
        final Integer userId = new Random().nextInt();
        final User user = getTestUser(userId);
        final UserDto userDto = converter.convert(user);

        Assert.assertEquals(CONVERT_ERR_MSG, userId, userDto.getId());
        Assert.assertEquals(CONVERT_ERR_MSG, "unitTest", userDto.getLogin());
        Assert.assertEquals(CONVERT_ERR_MSG, "unit@test", userDto.getEmail());
        Assert.assertEquals(CONVERT_ERR_MSG, true, userDto.getActive());
    }

    /**
     * Test convert null User object.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConvertNullSource() {
       converter.convert(null);
    }

    /**
     * Help method for creating test user object.
     *
     * @param userId    user id
     * @return          user object
     */
    private User getTestUser(final Integer userId) {
        User user = new User();
        user.setId(userId);
        user.setLogin("unitTest");
        user.setEmail("unit@test");
        user.setIsActive(true);
        return user;
    }
}
