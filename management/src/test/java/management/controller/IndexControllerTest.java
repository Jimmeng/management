package management.controller;

import org.junit.Before;
import org.junit.Test;




//import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import management.controller.IndexController;

public class IndexControllerTest {
	private MockMvc mockMvc;
	@Before
	public void setup(){
		InternalResourceViewResolver resolver=new InternalResourceViewResolver();
		resolver.setPrefix("/views/");
		resolver.setSuffix(".jsp");
		
		resolver.setExposeContextBeansAsAttributes(true);
		mockMvc = MockMvcBuilders.standaloneSetup(new IndexController()).setViewResolvers(resolver).build();
	}
	@Test
	public void testHomePage() throws Exception {
		//IndexController controller = new IndexController();
	//	MockMvc mockMvc = standaloneSetup(controller).build();
		mockMvc.perform(post("/login"))
			.andExpect(view().name("login"));
		// assertEquals("login",controller.receive());
	}
}
