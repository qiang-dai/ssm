package org.zsl.testmybatis;

import com.cn.hnust.controller.UserController;
import com.cn.hnust.service.IThirdPartyStickerInfoService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


//@RunWith(SpringJUnit4ClassRunner.class)     //表示继承了SpringJUnit4ClassRunner类
//@WebAppConfiguration
//@ContextConfiguration(locations = {"classpath:spring-mvc-servlet.xml"})

public class TestMyBatis {
//    private MockMvc mockMvc;
//
//    @Mock
//    private IThirdPartyStickerInfoService thirdPartyStickerInfoService;
//
//    @InjectMocks
//    UserController userController;
//
//    @Before
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
//    }

//    private static Logger logger = Logger.getLogger(TestMyBatis.class);
//    //  private ApplicationContext ac = null;
//    @Resource
//    private IUserService userService = null;
//
//    @Resource
//    private IThirdPartyStickerInfoService thirdPartyStickerInfoService = null;
//
//    @Before
//    public void before() {
//        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("spring-mybatis.xml");
//        userService = (IUserService) ac.getBean("userService");
//        thirdPartyStickerInfoService = (IThirdPartyStickerInfoService) ac.getBean("thirdPartyStickerInfoService");
//    }
//
//    @Test
//    public void test1() {
//        User user = userService.getUserById(1);
//         System.out.println(user.getUserName());
//         logger.info("值："+user.getUserName());
//        logger.info(JSON.toJSONString(user));
//    }
//
    @Test
    public void test2() {
//        try {
//            mockMvc.perform(post("/sticker/crawl"));
//        } catch (Exception e) {
//
//        }
//        IThirdPartyStickerInfoService thirdPartyStickerInfoService = new ThirdPartyStickerServiceImpl();
//        thirdPartyStickerInfoService.processTask();
    }

}  