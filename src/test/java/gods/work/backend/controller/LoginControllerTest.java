//package gods.work.backend.controller;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class LoginControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    public void login() throws Exception {
//        final String url = "/api/login";
//
//        final ResultActions resultActions = mockMvc.perform(get(url));
//
//        resultActions
//                .andDo(print());
//    }
//}
