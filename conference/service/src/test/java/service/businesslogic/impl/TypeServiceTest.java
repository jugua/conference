package service.businesslogic.impl;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
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

import domain.model.Type;
import service.businesslogic.dto.CreateTypeDto;
import service.businesslogic.dto.TypeDto;
import domain.repository.TypeRepository;
import service.businesslogic.api.TypeService;

@RunWith(MockitoJUnitRunner.class)
public class TypeServiceTest {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();
    @Mock
    private TypeRepository typeRepository;
    @Mock
    private ModelMapper modelMapper;
    private TypeService testing;
    private Type type;
    private CreateTypeDto createTypeDto;

    @Before
    public void setUp() throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        testing = new TypeServiceImpl(typeRepository, modelMapper);
        createTypeDto = new CreateTypeDto("Olena");
        TypeDto typeDto = new TypeDto();
        typeDto.setId(3L);
        typeDto.setName("Olena");
        type = new Type("Olena");
    }

    @Test
    public void testFindByIdType() throws Exception {
        type.setId(3L);
        when(typeRepository.findById(anyLong())).thenReturn(type);
        Type type = testing.getById(3L);
        assertThat(type.getId(), is(3L));
    }

    @Test
    public void testSaveType() {
        testing.save(createTypeDto);
        verify(typeRepository).save(type);
    }

    @Test
    public void testFindAll() {
        List<Type> list = new ArrayList<>();
        list.add(type);
        when(typeRepository.findAll()).thenReturn(list);
        List<TypeDto> types = testing.getAll();
        assertEquals(types.get(0).getName(), list.get(0).getName());
    }

    @Test
    public void testGetByNameContactType() {
        when(typeRepository.findByName(anyString())).thenReturn(type);
        Type types = testing.getByName("Olena");
        assertEquals(type, types);
    }
}

