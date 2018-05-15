package domain.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

public class VerificationTokenTest {

    private VerificationToken testing;

    private User user;

    @Before
    public void setUp() {
        user = createUser();
        testing = createVerificationToken(user);
    }

    @Test
    public void testCorrectTokenIsTokenValid() {
        testing.setType(VerificationToken.TokenType.CONFIRMATION);

        assertFalse(testing.isInvalid(VerificationToken.TokenType.CONFIRMATION));
    }

    @Test
    public void testUnCorrectTokenTypeIsTokenValid() {
        testing.setType(VerificationToken.TokenType.CHANGING_EMAIL);
        assertTrue(testing.isInvalid(VerificationToken.TokenType.CONFIRMATION));
    }

    @Test
    public void testUnExpiredTokenIsTokenExpired() {
        assertFalse(testing.isExpired());
    }

    @Test
    public void testExpiredByDateTokenIsTokenExpired() {
        testing.setExpiryDate(expiredDate());
        assertTrue(testing.isExpired());
    }

    @Test
    public void testExpiredTokenIsTokenExpired() {
        testing.expire();
        assertTrue(testing.isExpired());
    }

    @Test
    public void testCheckCreatingTokenSettingCreateToken() {
        testing = VerificationToken.of(user, VerificationToken.TokenType.FORGOT_PASS);
        assertNotNull(testing.getToken());
    }

    @Test
    public void testCheckUserSettingCreateToken() {
        VerificationToken actual = VerificationToken.of(user, VerificationToken.TokenType.FORGOT_PASS);
        assertEquals(user, actual.getUser());
    }

    @Test
    public void testCheckTypeSettingCreateToken() {
        VerificationToken actual = VerificationToken.of(user, VerificationToken.TokenType.FORGOT_PASS);
        assertEquals(VerificationToken.TokenType.FORGOT_PASS, actual.getType());
    }

    @Test
    public void testCheckExpiredDateSettingCreateToken() {
        testing = VerificationToken.of(user, VerificationToken.TokenType.FORGOT_PASS);

        LocalDateTime expected = VerificationToken.generateDefaultExpiryDate();
        LocalDateTime actual = testing.getExpiryDate();

        assertEquals(expected.getYear(), actual.getYear());
        assertEquals(expected.getDayOfYear(), actual.getDayOfYear());
        assertEquals(expected.getHour(), actual.getHour());
        assertEquals(expected.getMinute(), actual.getMinute());
    }

    @Test
    public void testTokensForUniqueValues() {
        VerificationToken tokenOne = VerificationToken.of(user, VerificationToken.TokenType.FORGOT_PASS);
        VerificationToken tokenTwo = VerificationToken.of(user, VerificationToken.TokenType.FORGOT_PASS);
        assertNotEquals(tokenOne.getToken(), tokenTwo.getToken());
    }

    private static User createUser() {
        User result = new User();
        result.setFirstName("FName");
        result.setLastName("LName");
        result.setEmail("test@gmail.com");
        result.setPassword("password");
        result.setPhoto("testUrl3");
        result.setStatus(User.UserStatus.CONFIRMED);
        return result;
    }

    private VerificationToken createVerificationToken(User user) {
        VerificationToken result = new VerificationToken();
        result.setId(1L);
        result.setToken("TOKEN");
        result.setExpiryDate(VerificationToken.generateDefaultExpiryDate());
        result.setStatus(VerificationToken.TokenStatus.VALID);
        result.setType(VerificationToken.TokenType.CONFIRMATION);
        result.setUser(user);
        return result;
    }

    private LocalDateTime expiredDate() {
        return LocalDateTime.now().minusMinutes(1);
    }

}