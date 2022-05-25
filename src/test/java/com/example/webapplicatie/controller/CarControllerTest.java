package com.example.webapplicatie.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.webapplicatie.models.Car;
import com.example.webapplicatie.models.User;
import com.example.webapplicatie.repository.CarRepository;
import com.example.webapplicatie.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lowagie.text.DocumentException;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashSet;

import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {CarController.class})
@ExtendWith(SpringExtension.class)
class CarControllerTest {
    @Autowired
    private CarController carController;

    @MockBean
    private CarRepository carRepository;

    @MockBean
    private UserRepository userRepository;

    /**
     * Method under test: {@link CarController#findByPublished()}
     */
    @Test
    void testFindByPublished() throws Exception {
        when(this.carRepository.findByPublished(anyBoolean())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/cars/published");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.carController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Method under test: {@link CarController#findByPublished()}
     */
    @Test
    void testFindByPublished2() throws Exception {
        User user = new User();
        user.setCars(new ArrayList<>());
        user.setEmail("jane.doe@example.org");
        user.setPassword("iloveyou");
        user.setRoles(new HashSet<>());
        user.setUsername("janedoe");

        Car car = new Car();
        car.setDescription("The characteristics of someone or something");
        car.setPublished(true);
        car.setTitle("Dr");
        car.setUser(user);

        ArrayList<Car> carList = new ArrayList<>();
        carList.add(car);
        when(this.carRepository.findByPublished(anyBoolean())).thenReturn(carList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/cars/published");
        MockMvcBuilders.standaloneSetup(this.carController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"id\":0,\"title\":\"Dr\",\"description\":\"The characteristics of someone or something\",\"published\":true"
                                        + ",\"user\":{\"id\":null,\"username\":\"janedoe\",\"email\":\"jane.doe@example.org\",\"password\":\"iloveyou\",\"roles"
                                        + "\":[]}}]"));
    }

    /**
     * Method under test: {@link CarController#findByPublished()}
     */
    @Test
    void testFindByPublished3() throws Exception {
        when(this.carRepository.findByPublished(anyBoolean())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/cars/published");
        getResult.contentType("https://example.org/example");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.carController).build().perform(getResult);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Method under test: {@link CarController#findByPublished()}
     */
    @Test
    void testFindByPublished4() throws Exception {
        when(this.carRepository.findByPublished(anyBoolean())).thenThrow(new DocumentException("An error occurred"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/cars/published");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.carController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(500));
    }

    /**
     * Method under test: {@link CarController#createCar(Car)}
     */
    @Test
    void testCreateCar() throws Exception {
        User user = new User();
        user.setCars(new ArrayList<>());
        user.setEmail("jane.doe@example.org");
        user.setPassword("iloveyou");
        user.setRoles(new HashSet<>());
        user.setUsername("janedoe");
        when(this.userRepository.getById((Long) any())).thenReturn(user);

        User user1 = new User();
        user1.setCars(new ArrayList<>());
        user1.setEmail("jane.doe@example.org");
        user1.setPassword("iloveyou");
        user1.setRoles(new HashSet<>());
        user1.setUsername("janedoe");

        Car car = new Car();
        car.setDescription("The characteristics of someone or something");
        car.setPublished(true);
        car.setTitle("Dr");
        car.setUser(user1);
        when(this.carRepository.save((Car) any())).thenReturn(car);

        User user2 = new User();
        user2.setCars(new ArrayList<>());
        user2.setEmail("jane.doe@example.org");
        user2.setPassword("iloveyou");
        user2.setRoles(new HashSet<>());
        user2.setUsername("janedoe");

        Car car1 = new Car();
        car1.setDescription("The characteristics of someone or something");
        car1.setPublished(true);
        car1.setTitle("Dr");
        car1.setUser(user2);
        String content = (new ObjectMapper()).writeValueAsString(car1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.carController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":0,\"title\":\"Dr\",\"description\":\"The characteristics of someone or something\",\"published\":true"
                                        + ",\"user\":{\"id\":null,\"username\":\"janedoe\",\"email\":\"jane.doe@example.org\",\"password\":\"iloveyou\",\"roles"
                                        + "\":[]}}"));
    }

    /**
     * Method under test: {@link CarController#exportToPdf(javax.servlet.http.HttpServletResponse)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testExportToPdf() throws DocumentException, IOException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "org.apache.coyote.Response.isCommitted()" because the return value of "org.apache.catalina.connector.Response.getCoyoteResponse()" is null
        //       at org.apache.catalina.connector.Response.isCommitted(Response.java:619)
        //       at org.apache.catalina.connector.Response.setContentType(Response.java:741)
        //       at com.example.webapplicatie.controller.CarController.exportToPdf(CarController.java:149)
        //   In order to prevent exportToPdf(HttpServletResponse)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   exportToPdf(HttpServletResponse).
        //   See https://diff.blue/R013 to resolve this issue.

        CarController carController = new CarController();
        carController.exportToPdf(new Response());
    }

    /**
     * Method under test: {@link CarController#exportToPdf(javax.servlet.http.HttpServletResponse)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testExportToPdf2() throws DocumentException, IOException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.example.webapplicatie.repository.CarRepository.findAll()" because "this.carRepository" is null
        //       at com.example.webapplicatie.controller.CarController.exportToPdf(CarController.java:157)
        //   In order to prevent exportToPdf(HttpServletResponse)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   exportToPdf(HttpServletResponse).
        //   See https://diff.blue/R013 to resolve this issue.

        CarController carController = new CarController();
        carController.exportToPdf(new MockHttpServletResponse());
    }

    /**
     * Method under test: {@link CarController#exportToPdf(javax.servlet.http.HttpServletResponse)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testExportToPdf3() throws DocumentException, IOException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.example.webapplicatie.repository.CarRepository.findAll()" because "this.carRepository" is null
        //       at com.example.webapplicatie.controller.CarController.exportToPdf(CarController.java:157)
        //   In order to prevent exportToPdf(HttpServletResponse)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   exportToPdf(HttpServletResponse).
        //   See https://diff.blue/R013 to resolve this issue.

        CarController carController = new CarController();
        Response response = mock(Response.class);
        doNothing().when(response).setContentType((String) any());
        doNothing().when(response).setHeader((String) any(), (String) any());
        carController.exportToPdf(response);
    }

    /**
     * Method under test: {@link CarController#createCar(Car)}
     */
    @Test
    void testCreateCar2() throws Exception {
        User user = new User();
        user.setCars(new ArrayList<>());
        user.setEmail("jane.doe@example.org");
        user.setPassword("iloveyou");
        user.setRoles(new HashSet<>());
        user.setUsername("janedoe");
        when(this.userRepository.getById((Long) any())).thenReturn(user);
        when(this.carRepository.save((Car) any())).thenThrow(new DocumentException("An error occurred"));

        User user1 = new User();
        user1.setCars(new ArrayList<>());
        user1.setEmail("jane.doe@example.org");
        user1.setPassword("iloveyou");
        user1.setRoles(new HashSet<>());
        user1.setUsername("janedoe");

        Car car = new Car();
        car.setDescription("The characteristics of someone or something");
        car.setPublished(true);
        car.setTitle("Dr");
        car.setUser(user1);
        String content = (new ObjectMapper()).writeValueAsString(car);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.carController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(500));
    }

    /**
     * Method under test: {@link CarController#createCar(Car, long)}
     */
    @Test
    void testCreateCar3() throws Exception {
        User user = new User();
        user.setCars(new ArrayList<>());
        user.setEmail("jane.doe@example.org");
        user.setPassword("iloveyou");
        user.setRoles(new HashSet<>());
        user.setUsername("janedoe");
        when(this.userRepository.getById((Long) any())).thenReturn(user);

        User user1 = new User();
        user1.setCars(new ArrayList<>());
        user1.setEmail("jane.doe@example.org");
        user1.setPassword("iloveyou");
        user1.setRoles(new HashSet<>());
        user1.setUsername("janedoe");

        Car car = new Car();
        car.setDescription("The characteristics of someone or something");
        car.setPublished(true);
        car.setTitle("Dr");
        car.setUser(user1);
        when(this.carRepository.save((Car) any())).thenReturn(car);

        User user2 = new User();
        user2.setCars(new ArrayList<>());
        user2.setEmail("jane.doe@example.org");
        user2.setPassword("iloveyou");
        user2.setRoles(new HashSet<>());
        user2.setUsername("janedoe");

        Car car1 = new Car();
        car1.setDescription("The characteristics of someone or something");
        car1.setPublished(true);
        car1.setTitle("Dr");
        car1.setUser(user2);
        String content = (new ObjectMapper()).writeValueAsString(car1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/cars/user/{id}", 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.carController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":0,\"title\":\"Dr\",\"description\":\"The characteristics of someone or something\",\"published\":true"
                                        + ",\"user\":{\"id\":null,\"username\":\"janedoe\",\"email\":\"jane.doe@example.org\",\"password\":\"iloveyou\",\"roles"
                                        + "\":[]}}"));
    }

    /**
     * Method under test: {@link CarController#createCar(Car, long)}
     */
    @Test
    void testCreateCar4() throws Exception {
        User user = new User();
        user.setCars(new ArrayList<>());
        user.setEmail("jane.doe@example.org");
        user.setPassword("iloveyou");
        user.setRoles(new HashSet<>());
        user.setUsername("janedoe");
        when(this.userRepository.getById((Long) any())).thenReturn(user);
        when(this.carRepository.save((Car) any())).thenThrow(new DocumentException("An error occurred"));

        User user1 = new User();
        user1.setCars(new ArrayList<>());
        user1.setEmail("jane.doe@example.org");
        user1.setPassword("iloveyou");
        user1.setRoles(new HashSet<>());
        user1.setUsername("janedoe");

        Car car = new Car();
        car.setDescription("The characteristics of someone or something");
        car.setPublished(true);
        car.setTitle("Dr");
        car.setUser(user1);
        String content = (new ObjectMapper()).writeValueAsString(car);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/cars/user/{id}", 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.carController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(500));
    }

    /**
     * Method under test: {@link CarController#deleteAllCars()}
     */
    @Test
    void testDeleteAllCars() throws Exception {
        doNothing().when(this.carRepository).deleteAll();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/cars");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.carController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Method under test: {@link CarController#deleteAllCars()}
     */
    @Test
    void testDeleteAllCars2() throws Exception {
        doNothing().when(this.carRepository).deleteAll();
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/api/cars");
        deleteResult.contentType("https://example.org/example");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.carController)
                .build()
                .perform(deleteResult);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Method under test: {@link CarController#deleteAllCars()}
     */
    @Test
    void testDeleteAllCars3() throws Exception {
        doThrow(new DocumentException("An error occurred")).when(this.carRepository).deleteAll();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/cars");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.carController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(500));
    }

    /**
     * Method under test: {@link CarController#deleteCar(long)}
     */
    @Test
    void testDeleteCar() throws Exception {
        doNothing().when(this.carRepository).deleteById((Long) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/cars/{id}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.carController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Method under test: {@link CarController#deleteCar(long)}
     */
    @Test
    void testDeleteCar2() throws Exception {
        doNothing().when(this.carRepository).deleteById((Long) any());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/api/cars/{id}", 123L);
        deleteResult.contentType("https://example.org/example");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.carController)
                .build()
                .perform(deleteResult);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Method under test: {@link CarController#deleteCar(long)}
     */
    @Test
    void testDeleteCar3() throws Exception {
        doThrow(new DocumentException("An error occurred")).when(this.carRepository).deleteById((Long) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/cars/{id}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.carController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(500));
    }

    /**
     * Method under test: {@link CarController#deleteCar(long)}
     */
    @Test
    void testDeleteCar4() throws Exception {
        doNothing().when(this.carRepository).deleteAll();
        doNothing().when(this.carRepository).deleteById((Long) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/cars/{id}", "", "Uri Vars");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.carController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Method under test: {@link CarController#deleteCar(long)}
     */
    @Test
    void testDeleteCar5() throws Exception {
        doNothing().when(this.carRepository).deleteAll();
        doNothing().when(this.carRepository).deleteById((Long) any());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/api/cars/{id}", "", "Uri Vars");
        deleteResult.contentType("https://example.org/example");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.carController)
                .build()
                .perform(deleteResult);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Method under test: {@link CarController#deleteCar(long)}
     */
    @Test
    void testDeleteCar6() throws Exception {
        doThrow(new DocumentException("An error occurred")).when(this.carRepository).deleteAll();
        doNothing().when(this.carRepository).deleteById((Long) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/cars/{id}", "", "Uri Vars");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.carController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(500));
    }

    /**
     * Method under test: {@link CarController#getAllCars(String)}
     */
    @Test
    void testGetAllCars() throws Exception {
        when(this.carRepository.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/cars");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.carController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Method under test: {@link CarController#getAllCars(String)}
     */
    @Test
    void testGetAllCars2() throws Exception {
        User user = new User();
        user.setCars(new ArrayList<>());
        user.setEmail("jane.doe@example.org");
        user.setPassword("iloveyou");
        user.setRoles(new HashSet<>());
        user.setUsername("janedoe");

        Car car = new Car();
        car.setDescription("The characteristics of someone or something");
        car.setPublished(true);
        car.setTitle("Dr");
        car.setUser(user);

        ArrayList<Car> carList = new ArrayList<>();
        carList.add(car);
        when(this.carRepository.findAll()).thenReturn(carList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/cars");
        MockMvcBuilders.standaloneSetup(this.carController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"id\":0,\"title\":\"Dr\",\"description\":\"The characteristics of someone or something\",\"published\":true"
                                        + ",\"user\":{\"id\":null,\"username\":\"janedoe\",\"email\":\"jane.doe@example.org\",\"password\":\"iloveyou\",\"roles"
                                        + "\":[]}}]"));
    }

    /**
     * Method under test: {@link CarController#getAllCars(String)}
     */
    @Test
    void testGetAllCars3() throws Exception {
        when(this.carRepository.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/cars");
        getResult.contentType("https://example.org/example");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.carController).build().perform(getResult);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Method under test: {@link CarController#getAllCars(String)}
     */
    @Test
    void testGetAllCars4() throws Exception {
        when(this.carRepository.findByTitleContaining((String) any())).thenReturn(new ArrayList<>());
        when(this.carRepository.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/cars").param("title", "foo");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.carController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Method under test: {@link CarController#getAllCars(String)}
     */
    @Test
    void testGetAllCars5() throws Exception {
        when(this.carRepository.findByTitleContaining((String) any()))
                .thenThrow(new DocumentException("An error occurred"));
        when(this.carRepository.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/cars").param("title", "foo");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.carController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(500));
    }
}

