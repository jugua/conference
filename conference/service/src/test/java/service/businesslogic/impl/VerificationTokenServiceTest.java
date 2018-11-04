package service.businesslogic.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import domain.model.User;
import domain.model.VerificationToken;
import domain.repository.VerificationTokenRepository;

@RunWith(MockitoJUnitRunner.class)
public class VerificationTokenServiceTest {

    @Mock
    private VerificationTokenRepository tokenRepository;

    private VerificationTokenService testing;

    private VerificationToken verificationToken;

    @Before
    public void setUp() {
        testing = new VerificationTokenService(tokenRepository);

        verificationToken = createVerificationToken();
    }

    @Test
    public void testGetTokenForExistingToken() {
        when(tokenRepository.findFirstByToken(anyString())).thenReturn(verificationToken);
        assertEquals(testing.findTokenBy("TOKEN"), verificationToken);
    }

    @Test
    public void getTokenForUnexistingTokenReturnsNull() {
        when(tokenRepository.findFirstByToken(anyString())).thenReturn(null);
        assertNull(testing.findTokenBy("TOKEN"));
    }

    private VerificationToken createVerificationToken() {
        VerificationToken result = new VerificationToken();
        result.setId(1L);
        result.setToken("TOKEN");
        result.setUser(new User());
        result.setExpiryDate(LocalDateTime.now().plusMinutes(VerificationToken.DEFAULT_EXPIRATION_TIME_IN_MINUTES));
        result.setStatus(VerificationToken.TokenStatus.VALID);
        result.setType(VerificationToken.TokenType.CONFIRMATION);
        return result;
    }

}