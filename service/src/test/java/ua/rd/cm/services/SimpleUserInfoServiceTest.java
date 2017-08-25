package ua.rd.cm.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ua.rd.cm.domain.UserInfo;
import ua.rd.cm.repository.UserInfoRepository;
import ua.rd.cm.services.impl.UserInfoServiceImpl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SimpleUserInfoServiceTest {
    @Mock
    private UserInfoRepository userInfoRepository;

    private UserInfoService userInfoService;
    private UserInfo userInfo;

    @Before
    public void setUp() {
        userInfoService = new UserInfoServiceImpl(userInfoRepository);
        userInfo = mock(UserInfo.class);
    }

    @Test
    public void testSaveUserInfo() {
        userInfoService.save(userInfo);
        verify(userInfoRepository).save(userInfo);
    }

    @Test
    public void testUpdateUserInfo() {
        userInfoService.update(userInfo);
        verify(userInfoRepository).save(userInfo);
    }

    @Test
    public void testFindByIdUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(1L);
        userInfo.setShortBio("bio");
        userInfo.setJobTitle("job");
        userInfo.setPastConference("pastConf");
        userInfo.setCompany("company");
        userInfo.setAdditionalInfo("info");

        when(userInfoRepository.findById(anyLong())).thenReturn(userInfo);
        UserInfo userInfoT = userInfoService.find(1L);
        assertEquals(userInfoT.getId(), userInfo.getId());
        assertEquals(userInfoT, userInfo);
    }
}
