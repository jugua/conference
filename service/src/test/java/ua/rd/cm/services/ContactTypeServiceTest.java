package ua.rd.cm.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.rd.cm.domain.ContactType;
import ua.rd.cm.repository.ContactTypeRepository;
import ua.rd.cm.repository.specification.contacttype.ContactTypeById;
import ua.rd.cm.repository.specification.contacttype.ContactTypeByName;
import ua.rd.cm.services.impl.ContactTypeServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * @author Olha_Melnyk
 */
@RunWith(MockitoJUnitRunner.class)
public class ContactTypeServiceTest {

    @Mock
    private ContactTypeRepository contactTypeRepository;

    private ContactTypeService contactTypeService;
    private ContactType contactType;

    @Before
    public void setUp() {
        contactTypeService = new ContactTypeServiceImpl(contactTypeRepository);
        contactType = new ContactType(1L, "VK");
    }

    @Test
    public void testSaveContactType() {
        contactTypeService.save(contactType);
        verify(contactTypeRepository).save(contactType);
    }

    @Test
    public void testUpdateContactType() {
        contactTypeService.update(contactType);
        verify(contactTypeRepository).update(contactType);
    }

    @Test
    public void testFindByIdContactType() {
        List<ContactType> list = new ArrayList<ContactType>() {{
            add(contactType);
        }};
        when(contactTypeRepository.findById(anyLong())).thenReturn(list.get(0));
        ContactType contactType = contactTypeService.find(1L);
        assertEquals(new Long(1L), contactType.getId());
    }

    @Test
    public void testFindByNameContactType() {
        List<ContactType> list = new ArrayList<ContactType>() {{
            add(contactType);
        }};
        when(contactTypeRepository.findByName(anyString())).thenReturn(list);
        List<ContactType> contactTypes = contactTypeService.findByName("VK");
        assertEquals(list, contactTypes);
    }

    @Test
    public void testFindAll() {
        List<ContactType> list = new ArrayList<ContactType>() {{
            add(contactType);
            add(new ContactType(2L, "Twitter"));
        }};
        when(contactTypeRepository.findAll()).thenReturn(list);
        List<ContactType> contactTypes = contactTypeService.findAll();
        assertEquals(contactTypes, list);
    }
}
