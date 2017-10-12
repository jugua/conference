package ua.rd.cm.services;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ua.rd.cm.domain.User;
import ua.rd.cm.domain.VerificationToken;
import ua.rd.cm.repository.VerificationTokenRepository;
import ua.rd.cm.services.businesslogic.impl.VerificationTokenService;


@RunWith(MockitoJUnitRunner.class)
public class VerificationTokenServiceTest {

    private static User user;
    @Mock
    private VerificationTokenRepository tokenRepository;
    private VerificationTokenService tokenService;
    private VerificationToken verificationToken;
    private VerificationToken testedToken;

    @BeforeClass
    public static void init() {
        user = createUser();
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

    @Before
    public void setUp() {
        tokenService = new VerificationTokenService(tokenRepository);
        testedToken = createTokenUsingService();
        verificationToken = new VerificationToken();
        verificationToken.setId(1L);
        verificationToken.setToken("TOKEN");
        verificationToken.setUser(new User());
        verificationToken.setExpiryDate(createExpiredDate(0));
        verificationToken.setStatus(VerificationToken.TokenStatus.VALID);
        verificationToken.setType(VerificationToken.TokenType.CONFIRMATION);
    }

    @Test
    public void testCorrectTokenIsTokenValid() {
        assertTrue(tokenService.isTokenValid(verificationToken, VerificationToken.TokenType.CONFIRMATION));
    }

    @Test
    public void testNullTokenIsTokenValid() {
        assertFalse(tokenService.isTokenValid(null, VerificationToken.TokenType.CONFIRMATION));
    }

    @Test
    public void testUnCorrectTokenTypeIsTokenValid() {
        verificationToken.setType(VerificationToken.TokenType.CHANGING_EMAIL);
        assertFalse(tokenService.isTokenValid(verificationToken, VerificationToken.TokenType.CONFIRMATION));
    }

    @Test
    public void testUnExpiredTokenIsTokenExpired() {
        assertFalse(tokenService.isTokenExpired(verificationToken));
    }

    @Test
    public void testExpiredByDateTokenIsTokenExpired() {
        verificationToken.setExpiryDate(createExpiredDate(61));
        assertTrue(tokenService.isTokenExpired(verificationToken));
    }

    @Test
    public void testExpiredTokenIsTokenExpired() {
        verificationToken.setStatus(VerificationToken.TokenStatus.EXPIRED);
        assertTrue(tokenService.isTokenExpired(verificationToken));
    }

    @Test
    public void testGetTokenForExistingToken() {
        List<VerificationToken> resultedList = new ArrayList<>();
        resultedList.add(verificationToken);
        when(tokenRepository.findByToken(anyString())).thenReturn(resultedList);
        assertEquals(verificationToken, tokenService.getToken("TOKEN"));
    }

    @Test
    public void testGetTokenForUnExistingToken() {
        List<VerificationToken> resultedList = new ArrayList<>();
        when(tokenRepository.findByToken(anyString())).thenReturn(resultedList);
        assertNull(tokenService.getToken("TOKEN"));
    }

    @Test
    public void testCheckUserSettingCreateToken() {
        assertEquals(user, testedToken.getUser());
    }

    @Test
    public void testCheckTypeSettingCreateToken() {
        assertEquals(VerificationToken.TokenType.FORGOT_PASS, testedToken.getType());
    }

    @Test
    public void testCheckExpiredDateSettingCreateToken() {
        LocalDateTime expectedDate = createExpiredDate(0);
        assertEquals(expectedDate.getYear(), testedToken.getExpiryDate().getYear());
        assertEquals(expectedDate.getDayOfYear(), testedToken.getExpiryDate().getDayOfYear());
        assertEquals(expectedDate.getHour(), testedToken.getExpiryDate().getHour());
        assertEquals(expectedDate.getMinute(), testedToken.getExpiryDate().getMinute());
    }

    @Test
    public void testCheckCreatingTokenSettingCreateToken() {
        assertNotNull(testedToken.getToken());
    }

    @Test
    public void testTokensForUniqueValues() {
        VerificationToken testedTokenTwo = createTokenUsingService();
        assertNotEquals(testedToken.getToken(), testedTokenTwo.getToken());
    }

    private VerificationToken createTokenUsingService() {
        return tokenService.createToken(user, VerificationToken.TokenType.FORGOT_PASS);
    }

    private LocalDateTime createExpiredDate(int decreasingTimeInMinutes) {
        LocalDateTime currentTime = LocalDateTime.now();
        return currentTime.plusMinutes(VerificationToken.EXPIRATION_IN_MINUTES - decreasingTimeInMinutes);
    }
}