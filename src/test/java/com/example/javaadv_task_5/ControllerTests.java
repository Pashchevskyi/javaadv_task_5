package com.example.javaadv_task_5;

import com.example.javaadv_task_5.domain.Employee;
import com.example.javaadv_task_5.domain.Gender;
import com.example.javaadv_task_5.dto.EmployeeDto;
import com.example.javaadv_task_5.dto.EmployeeReadDto;
import com.example.javaadv_task_5.service.EmployeeService;
import com.example.javaadv_task_5.util.config.EmployeeMapper;
import com.example.javaadv_task_5.web.EmployeeController;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = EmployeeController.class)
@DisplayName("Employee Controller Tests")
public class ControllerTests {

    private final EmployeeMapper mapper = EmployeeMapper.INSTANCE;

    @MockBean
    EmployeeService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("POST /api/users")
    @WithMockUser(roles = "ADMIN")
    public void createPassTest() throws Exception {
        EmployeeDto response = new EmployeeDto(1, "Mike", "",
            "mail@mail.com", (new Date()), Gender.M, (new HashSet<>()));
        Employee employee = Employee.builder().id(1).name("Mike").email("mail@mail.com").build();


        when(service.create(any(Employee.class))).thenReturn(employee);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"" + employee.getName() + "\", \"country\": \"" +
                    employee.getCountry() + "\", \"email\": \"" + employee.getEmail() + "\"}");

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id", is(1)))
                .andReturn().getResponse();

        verify(service).create(any());
    }

    @Test
    @DisplayName("Entity POST /api/users")
    @WithMockUser(roles = "ADMIN")
    public void testEntitySave() throws Exception {
        Employee employeeToBeReturn = Employee.builder()
                .id(1)
                .name("Mark")
                .country("France").build();
        doReturn(employeeToBeReturn).when(service).create(any());
        when(this.service.create(any(Employee.class))).thenReturn(employeeToBeReturn);
        // Execute the POST request
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/api/usersS")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": " + employeeToBeReturn.getId() + ", \"name\": " + "\"" +
                    employeeToBeReturn.getName() + "\", \"country\": " + "\"" +
                    employeeToBeReturn.getCountry() + "\"}");
        mockMvc
                .perform(mockRequest)
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        verify(this.service, times(1)).create(any(Employee.class));
        verifyNoMoreInteractions(this.service);
    }

    @Test
    @DisplayName("GET /api/users/{id}")
    @WithMockUser(roles = "USER")
    public void getPassByIdTest() throws Exception {
        EmployeeReadDto response = new EmployeeReadDto("", "", "",
            (new HashSet<>()), Date.from(Instant.now()), Gender.M);
        Employee employee = Employee.builder()
                .id(1)
                .name("Mike")
                .build();

        when(service.getById(1)).thenReturn(employee);

        MockHttpServletRequestBuilder mockRequest = get("/api/users/1");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Mike")));

        verify(service).getById(anyInt());
    }

    @Test
    @DisplayName("PATCH /api/users/fix-countries")
    @WithMockUser(roles = "ADMIN")
    public void fixCountriesNamesTest() throws Exception {
        EmployeeDto response = new EmployeeDto(1, "", "", "",
            (new Date()), Gender.M, (new HashSet<>()));
        Employee employee1 = Employee.builder().id(1).name("Petro").country("ukraine").build();
        Employee employee2 = Employee.builder().id(2).name("Pavlo").country("poland").build();
        List<Employee> list = new ArrayList<>();
        list.add(employee1);
        list.add(employee2);

        when(service.getByCountryStartingWithLowercase()).thenReturn(list);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
            .patch("/api/users/fix-countries")
            .contentType(MediaType.APPLICATION_JSON)
            .content("");

        mockMvc.perform(mockRequest)
            .andExpect(status().isOk());

        verify(service).getByCountryStartingWithLowercase();
    }

    @Test
    @DisplayName("GET /api/users/no-email")
    @WithMockUser(roles = "USER")
    public void getUsersWithoutEmailTest() throws Exception {
        Employee employee1 = Employee.builder().id(1).name("John").country("US").build();
        Employee employee2 = Employee.builder().id(2).name("Jane").country("UK").build();
        Employee employee3 = Employee.builder().id(3).name("Bob").country("US").build();
        List<Employee> list = Arrays.asList(employee1, employee2, employee3);

        when(service.getByEmailNull()).thenReturn(list);

        MvcResult result = mockMvc.perform(get("/api/users/no-email"))
            .andExpect(status().isOk())
            .andReturn();

        verify(service).getByEmailNull();

        String contentType = result.getResponse().getContentType();
        assertNotNull(contentType);
        assertTrue(contentType.contains(MediaType.APPLICATION_JSON_VALUE));
        String responseContent = result.getResponse().getContentAsString();
        assertNotNull(responseContent);
    }

    @Test
    @DisplayName("PATCH /api/users/{id}/name")
    @WithMockUser(roles = "ADMIN")
    public void updatePassByIdTest() throws Exception {
        EmployeeDto response = new EmployeeDto(1, "", "", "", (new Date()),
            Gender.M, (new HashSet<>()));
        Employee employee = Employee.builder().id(1).name("Petro").build();

        when(service.updateNameById(eq(1), eq("Pavlo"))).thenReturn(employee);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .patch("/api/users/1/name")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Pavlo\"}");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));

        verify(service).updateNameById(eq(1), eq("Pavlo"));
    }

    @Test
    @DisplayName("PATCH /api/users/{id}")
    @WithMockUser(roles = "ADMIN")
    public void deletePassTest() throws Exception {

        doNothing().when(service).removeById(1);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .patch("/api/users/1");

        mockMvc.perform(mockRequest)
                .andExpect(status().isNoContent());

        verify(service).removeById(1);
    }

    @Test
    @DisplayName("GET /api/users/p")
    @WithMockUser(roles = "USER")
    public void getUsersPageTest() throws Exception {

        Employee employee1 = Employee.builder().id(1).name("John").country("US").build();
        Employee employee2 = Employee.builder().id(2).name("Jane").country("UK").build();
        Employee employee3 = Employee.builder().id(3).name("Bob").country("US").build();
        List<Employee> list = Arrays.asList(employee1, employee2, employee3);
        Page<Employee> employeesPage = new PageImpl<>(list);
        Pageable pageable = PageRequest.of(0, 5);

        when(service.getAllWithPagination(eq(pageable))).thenReturn(employeesPage);

        MvcResult result = mockMvc.perform(get("/api/users/p")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andReturn();

        verify(service).getAllWithPagination(eq(pageable));

        String contentType = result.getResponse().getContentType();
        assertNotNull(contentType);
        assertTrue(contentType.contains(MediaType.APPLICATION_JSON_VALUE));
        String responseContent = result.getResponse().getContentAsString();
        assertNotNull(responseContent);
    }

}