package service.businesslogic.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    private List<VerificationToken> tokens;
    private VerificationToken verificationToken;

    @Before
    public void setUp() {
        testing = new VerificationTokenService(tokenRepository);

        verificationToken = createVerificationToken();
        tokens = new ArrayList<>();
        tokens.add(verificationToken);
    }

    @Test
    public void testGetTokenForExistingToken() {
        when(tokenRepository.findByToken(anyString())).thenReturn(tokens);
        assertEquals(testing.findTokenBy("TOKEN"), verificationToken);
    }

    @Test
    public void testGetTokenForUnExistingToken() {
        when(tokenRepository.findByToken(anyString())).thenReturn(Collections.emptyList());
        assertNull(testing.findTokenBy("TOKEN"));
    }

    private VerificationToken createVerificationToken() {
        VerificationToken result = new VerificationToken();
        result.setId(1L);
        result.setToken("TOKEN");
        result.setUser(new User());
        result.setExpiryDate(LocalDateTime.now().plusMinutes(VerificationToken.EXPIRATION_IN_MINUTES));
        result.setStatus(VerificationToken.TokenStatus.VALID);
        result.setType(VerificationToken.TokenType.CONFIRMATION);
        return result;
    }

}