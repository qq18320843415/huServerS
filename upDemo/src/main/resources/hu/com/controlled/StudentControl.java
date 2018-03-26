package hu.com.controlled;

import android.graphics.pdf.PdfDocument;
import hu.com.entity.Student;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@Scope(value = "singleton") //只实例化一个bean对象（即每次请求都使用同一个bean对象）
@RequestMapping("uusers")
public class StudentControl {
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getUserList() {
        List<Student> list = new ArrayList<Student>();
        Student user1 = new Student();
        user1.setName("Tom");
        user1.setAge("66666");

        Student user2 = new Student();
        user2.setName("Jone");
        user2.setAge("88888");

        list.add(user1);
        list.add(user2);

        Map<String, Object> modelMap = new HashMap<String, Object>(3);
        modelMap.put("大黄", "研发");
        modelMap.put("School","Suzhou");
        modelMap.put("userList",list);
        return modelMap;
    }



    @RequestMapping(value = "/add1.do")
    public String add(HttpServletRequest request) {
        //System.out.println("------ 添加用户信息 ------");
        String name = request.getParameter("username");
        String pass = request.getParameter("password");
        System.out.println("username:" + name + " password: " + pass);
        String str = "username:" + name + " password: " + pass;
        request.setAttribute("user", str);
        return "user_add1";
    }





    @RequestMapping("/jsonfeed")
    public @ResponseBody
    Object getJSON(Model model) {
/*        List<TournamentContent> tournamentList = new ArrayList<TournamentContent>();
        tournamentList.add(TournamentContent.generateContent("FIFA", new Date(), "World Cup", "www.fifa.com/worldcup/"));
        tournamentList.add(TournamentContent.generateContent("FIFA", new Date(), "U-20 World Cup", "www.fifa.com/u20worldcup/"));
        tournamentList.add(TournamentContent.generateContent("FIFA", new Date(), "U-17 World Cup", "www.fifa.com/u17worldcup/"));
        tournamentList.add(TournamentContent.generateContent("中超", new Date(), "中超", "www.fifa.com/confederationscup/"));
        model.addAttribute("items", tournamentList);
        model.addAttribute("status", 0);*/
        return model;
    }


    @RequestMapping(value = "/users")
    public ResponseEntity<List<Student>> listAllUser() {
        List<Student> list = new ArrayList<Student>();

        Student user = new Student();
        user.setName("test1");
        user.setAge("30");

        Student user2 = new Student();
        user.setName("test2");
        user.setAge("40");

        list.add(user);
        list.add(user2);

        return new ResponseEntity<List<Student>>(list, HttpStatus.OK);
    }
}
