package cn.kgc.springboot_demo;

import cn.kgc.springboot_demo.pojo.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootDemoApplication.class)
@AutoConfigureMockMvc
public class TestController {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test01() throws Exception {
        String url = "/loginPage";
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(url)
                .accept(MediaType.TEXT_HTML);

        ResultActions actions = mockMvc.perform(builder);
        MvcResult mvcResult = actions.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        ModelAndView modelAndView = mvcResult.getModelAndView();

        String viewName = modelAndView.getViewName();
        Assert.assertEquals(viewName,"login");

        int status = response.getStatus();
        Assert.assertEquals(status,200);
    }

    @Test
    public void test02() throws Exception {
        String url = "/login";
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(url)
                .accept(MediaType.TEXT_HTML)
                .param("usercode","admin")
                .param("userpassword","1234567");

        ResultActions actions = mockMvc.perform(builder);
        MvcResult mvcResult = actions.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        ModelAndView modelAndView = mvcResult.getModelAndView();

        String viewName = modelAndView.getViewName();
        Assert.assertEquals(viewName,"redirect:/userList");

        int status = response.getStatus();
        Assert.assertEquals(status,302);
    }

    @Test
    public void test03() throws Exception {
        String url = "/registerPage";
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(url)
                .accept(MediaType.TEXT_HTML);

        ResultActions actions = mockMvc.perform(builder);
        MvcResult mvcResult = actions.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        ModelAndView modelAndView = mvcResult.getModelAndView();

        String viewName = modelAndView.getViewName();
        Assert.assertEquals(viewName,"register");

        int status = response.getStatus();
        Assert.assertEquals(status,200);
    }
    @Test
    public void test04() throws Exception {
        String url = "/register";
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(url)
                .accept(MediaType.TEXT_HTML)
                .param("usercode","zzz")
                .param("userpassword","123");

        ResultActions actions = mockMvc.perform(builder);
        MvcResult mvcResult = actions.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        ModelAndView modelAndView = mvcResult.getModelAndView();

        String viewName = modelAndView.getViewName();
        Assert.assertEquals(viewName,"redirect:/loginPage");

        int status = response.getStatus();
        Assert.assertEquals(status,302);
    }
    @Test
    public void test05() throws Exception {
        String url = "/userList";
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(url);
        builder.accept(MediaType.TEXT_HTML);

        ResultActions actions = mockMvc.perform(builder);
        MvcResult mvcResult = actions.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        ModelAndView modelAndView = mvcResult.getModelAndView();

        String viewName = modelAndView.getViewName();
        Assert.assertEquals(viewName,"userList");

        Map<String, Object> model = modelAndView.getModel();
        List<User> userList = (List<User>) model.get("userList");
        Assert.assertNotNull(userList);

        int status = response.getStatus();
        Assert.assertEquals(status,200);
    }
}
