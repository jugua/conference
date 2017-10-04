package ua.rd.cm.services;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ua.rd.cm.domain.ContactType;
import ua.rd.cm.repository.ContactTypeRepository;
import ua.rd.cm.services.businesslogic.ContactTypeService;
import ua.rd.cm.services.businesslogic.impl.ContactTypeServiceImpl;

/**
 * @author Olha_Melnyk
 */
@RunWith(MockitoJUnitRunner.class)
public class ContactTypeServiceTest {

    @Mock
    private ContactTypeRepository contactTypeRepository;

    private ContactTypeService testing;
    private ContactType contactType;

    @Before
    public void setUp() {
        testing = new ContactTypeServiceImpl(contactTypeRepository);
        contactType = new ContactType("VK");
        contactType.setId(1L);
    }

    @Test
    public void testSaveContactType() {
        testing.save(contactType);
        verify(contactTypeRepository).save(contactType);
    }

    @Test
    public void testUpdateContactType() {
        testing.update(contactType);
        verify(contactTypeRepository).save(contactType);
    }

    @Test
    public void testFindByIdContactType() {
        List<ContactType> list = Collections.singletonList(contactType);
        when(contactTypeRepository.findById(anyLong())).thenReturn(list.get(0));
        ContactType contactType = testing.find(1L);
        assertThat(contactType.getId(), is(1L));
    }

    @Test
    public void testFindByNameContactType() {
        when(contactTypeRepository.findFirstByName(anyString())).thenReturn(contactType);
        ContactType actual = testing.findByName("VK");
        assertEquals(contactType, actual);
    }

    @Test
    public void testFindAll() {
        List<ContactType> list = Arrays.asList(contactType, new ContactType("Twitter"));
        when(contactTypeRepository.findAll()).thenReturn(list);
        List<ContactType> contactTypes = testing.findAll();
        assertEquals(contactTypes, list);
    }
}
