package ua.rd.cm.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import ua.rd.cm.domain.Type;
import ua.rd.cm.dto.CreateTypeDto;
import ua.rd.cm.dto.TypeDto;
import ua.rd.cm.repository.TypeRepository;
import ua.rd.cm.services.business_logic.TypeService;
import ua.rd.cm.services.business_logic.impl.TypeServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class TypeServiceTest {
    @Mock
    private TypeService typeService;
    @Mock
    private TypeRepository typeRepository;
    private Type type;
    private CreateTypeDto createTypeDto;

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();


    @Before
    public void setUp() throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        typeService = new TypeServiceImpl(typeRepository, modelMapper);
        createTypeDto = new CreateTypeDto("Olena");
        TypeDto typeDto = new TypeDto();
        typeDto.setId(3L);
        typeDto.setName("Olena");
        type = new Type(3L, "Olena");
    }


    @Test
    public void testFindByIdType() throws Exception {
        when(typeRepository.findById(anyLong())).thenReturn(type);
        Type type = typeService.find(3L);
        assertEquals(new Long(3L), type.getId());
    }

    @Test
    public void testSaveType() {
        typeService.save(createTypeDto);
        verify(typeRepository).save(type);
    }

    @Test
    public void testFindAll() {
        List<Type> list = new ArrayList<Type>();
        list.add(type);
        when(typeRepository.findAll()).thenReturn(list);
        List<TypeDto> types = typeService.findAll();
        assertEquals(types.get(0).getName(), list.get(0).getName());
    }

    @Test
    public void testGetByNameContactType() {
        when(typeRepository.findByName(anyString())).thenReturn(type);
        Type types = typeService.getByName("Olena");
        assertEquals(type, types);
    }
}

