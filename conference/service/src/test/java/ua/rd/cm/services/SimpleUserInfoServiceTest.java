package ua.rd.cm.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import ua.rd.cm.domain.UserInfo;
import ua.rd.cm.repository.ContactTypeRepository;
import ua.rd.cm.repository.UserInfoRepository;
import ua.rd.cm.repository.UserRepository;
import ua.rd.cm.services.businesslogic.ContactTypeService;
import ua.rd.cm.services.businesslogic.UserInfoService;
import ua.rd.cm.services.businesslogic.impl.UserInfoServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class SimpleUserInfoServiceTest {
    @Mock
    private UserInfoRepository userInfoRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ModelMapper mapper;
    @Mock
    private ContactTypeRepository contactTypeRepository;

    private UserInfoService testing;
    private UserInfo userInfo;

    @Before
    public void setUp() {
        testing = new UserInfoServiceImpl(userInfoRepository, userRepository, mapper, contactTypeRepository);
        userInfo = mock(UserInfo.class);
    }

    @Test
    public void testSaveUserInfo() {
        testing.save(userInfo);
        verify(userInfoRepository).save(userInfo);
    }

    @Test
    @Ignore
    public void testUpdateUserInfo() {
//        testing.update("email", userInfo);
//        verify(userInfoRepository).save(userInfo);
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
        UserInfo userInfoT = testing.find(1L);
        assertEquals(userInfoT.getId(), userInfo.getId());
        assertEquals(userInfoT, userInfo);
    }
}
