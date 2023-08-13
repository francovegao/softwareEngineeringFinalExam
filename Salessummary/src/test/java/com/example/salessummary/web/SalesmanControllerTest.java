package com.example.salessummary.web;

import com.example.salessummary.entities.Salesman;
import com.example.salessummary.repositories.SalesmanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.View;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(value = MockitoExtension.class)
@ComponentScan("./")
@WebAppConfiguration
class SalesmanControllerTest {

    Salesman salesman;

    @Autowired
    private MockMvc mockMvc;
    @Mock
    SalesmanRepository salesmanRepository;
    @Mock
    View mockView;

    @InjectMocks
    SalesmanController salesmanController;

    @BeforeEach
    void setup() throws ParseException{
        salesman = new Salesman();
        salesman.setId(1L);
        salesman.setName("John");

        String sdate1="2012/11/11";
        Date date1=new SimpleDateFormat("yyyy/MM/dd").parse(sdate1);
        salesman.setDot(date1);
        salesman.setItem("Refrigerator");
        salesman.setAmount(3000.0);

        MockitoAnnotations.openMocks(this);

        mockMvc=standaloneSetup(salesmanController).setSingleView(mockView).build();
    }

    @Test
    public void findAll_ListView() throws Exception{
        List<Salesman> list = new ArrayList<Salesman>();
        list.add(salesman);
        list.add(salesman);

        when(salesmanRepository.findAll()).thenReturn(list);
        mockMvc.perform(get("/index"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("listSalesman", list))
                .andExpect(view().name("salesman"))
                .andExpect(model().attribute("listSalesman",hasSize(2)));

        verify(salesmanRepository, times(1)).findAll();
        verifyNoMoreInteractions(salesmanRepository);
    }

    @Test
    void delete(){
        ArgumentCaptor<Long> idCapture=ArgumentCaptor.forClass(Long.class);
        doNothing().when(salesmanRepository).deleteById(idCapture.capture());
        salesmanRepository.deleteById(1L);
        assertEquals(1L,idCapture.getValue());
        verify(salesmanRepository, times(1)).deleteById(1L);
    }

    @Test
    void editSalesman() throws Exception{
        Salesman s2=new Salesman();
        s2.setId(1L);
        s2.setName("John Mast");

        String sdate1="2012/11/11";
        Date date1=new SimpleDateFormat("yyyy/MM/dd").parse(sdate1);
        salesman.setDot(date1);
        salesman.setItem("Refrigerator");
        salesman.setAmount(3000.0);

        Long iid=1L;

        when(salesmanRepository.findById(iid)).thenReturn(Optional.of(s2));

        mockMvc.perform(get("/editSalesman").param("id", String.valueOf(1L)))
                .andExpect(status().isOk())
                .andExpect(model().attribute("salesman",s2))
                .andExpect(view().name("editSalesman"));

        verify(salesmanRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(salesmanRepository);
    }

    @Test
    void save() throws Exception{
        when(salesmanRepository.save(salesman)).thenReturn(salesman);
        salesmanRepository.save(salesman);
        verify(salesmanRepository,times(1)).save(salesman);
    }

}