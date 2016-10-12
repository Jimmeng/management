package management.controller;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import management.entity.Person;
import management.dao.PersonDao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//import com.fasterxml.jackson.databind.ObjectMapper;

//import java.util.*;

@Controller
@RequestMapping(value="/views")
public class IndexController {
	@RequestMapping(value="/index")
	public String index(){
		return "redirect:/";
	}
	@RequestMapping(value="/register" ,method=GET)
	public String goregister(){
		return "register.html";
	}
	@RequestMapping(value="/register" ,method=POST)
	public @ResponseBody boolean register(@RequestBody Person person){
		person.setId();
		ApplicationContext ctx=null;
		ctx=new ClassPathXmlApplicationContext("spring-mybatis.xml");
		PersonDao personDao=(PersonDao) ctx.getBean("personDao");
		Person data=personDao.ifPersonExistByName(person.getName());
		//return data==null;
		if (data==null){
			personDao.addPerson(person);
			return true;
		}
		else{
			return false;
		}
	}
	@RequestMapping(value="/login" ,method=GET)
	public String go(){
		return "login.html";
	}
	@RequestMapping(value="/login" ,method=POST)
	public @ResponseBody boolean receive(@RequestBody Person person){
		
		ApplicationContext ctx=null;
		ctx=new ClassPathXmlApplicationContext("spring-mybatis.xml");
		PersonDao personDao=(PersonDao) ctx.getBean("personDao");
		Person data=personDao.getPerson(person);
		if(data==null){
			return false;
		}
		else if(data.equals(person))
			return true;
		else
			return false;
	}
	@RequestMapping(value="/work")
	public String work(){
		return "work.html";
	}
}
