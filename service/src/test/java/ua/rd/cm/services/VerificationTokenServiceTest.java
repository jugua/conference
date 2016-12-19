package ua.rd.cm.services;

import static org.junit.Assert.*;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.rd.cm.domain.User;
import ua.rd.cm.domain.VerificationToken;
import ua.rd.cm.repository.VerificationTokenRepository;
import ua.rd.cm.repository.specification.verificationtoken.VerificationTokenByToken;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;


@RunWith(MockitoJUnitRunner.class)
public class VerificationTokenServiceTest {

    @Mock
    private VerificationTokenRepository tokenRepository;

    private VerificationTokenService tokenService;
    private VerificationToken verificationToken;
    private VerificationToken testedToken;

    private static User user;

    @BeforeClass
    public static void init() {
        user = createUser();
    }

    @Before
    public void setUp() {
        tokenService = new VerificationTokenService(tokenRepository);
        testedToken = createTokenUsingService();
        verificationToken = new VerificationToken(1L, "TOKEN", new User(),
                LocalDateTime.now(ZoneId.systemDefault()), VerificationToken.TokenType.CONFIRMATION, VerificationToken.TokenStatus.VALID);
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
        verificationToken.setExpiryDate(createExpiredDate(1));
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
        when(tokenRepository.findBySpecification(new VerificationTokenByToken(anyString()))).thenReturn(resultedList);
        assertEquals(verificationToken, tokenService.getToken("TOKEN"));
    }

    @Test
    public void testGetTokenForUnExistingToken() {
        List<VerificationToken> resultedList = new ArrayList<>();
        when(tokenRepository.findBySpecification(new VerificationTokenByToken(anyString()))).thenReturn(resultedList);
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

    private static User createUser() {
        return new User(null, "FName", "LName", "test@gmail.com", "password",
                "testUrl3",  User.UserStatus.CONFIRMED,null, null);
    }

    private LocalDateTime createExpiredDate(int increasingTimeInMinutes) {
        LocalDateTime currentTime = LocalDateTime.now(ZoneId.systemDefault());
        return currentTime.plusMinutes(VerificationToken.EXPIRATION_IN_MINUTES +  increasingTimeInMinutes);
    }
}