package com.myapp.lostfound.web.rest;

import com.myapp.lostfound.domain.Record;
import com.myapp.lostfound.domain.User;
import com.myapp.lostfound.repository.RecordRepository;
import com.myapp.lostfound.repository.UserRepository;
import com.myapp.lostfound.web.rest.util.MapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


@Controller
public class UsersController {

    private final Logger log = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecordRepository recordRepository;

    @RequestMapping("/login")
    public String login(){
        return "index";
    }

    /**
     * @Description 登录
     */
    @RequestMapping("/checkLogin")
    public String check(String username, String password, HttpServletRequest request, Model model){
        log.debug("REST to checkLogin: {}", request);
        System.out.println("成功进入方法");
        System.out.println(username);
        System.out.println(password);
        User user = userRepository.findByUsername(username);
        if(user == null){
            model.addAttribute("message","该账号不存在");
            return "index";
        }
        if(!user.getPassword().equals(password)){
            model.addAttribute("message","密码错误");
            return "index";
        }
        request.getSession().setAttribute("userId",user.getId());
        return "redirect:/toAllLost";
    }

    /**
     * @Description 跳转用户登录失物列表
     */
    @RequestMapping("/toAllLost")
    public String allLost(String search, Model model, HttpServletRequest request) throws Exception {
        log.debug("REST to toAllLost: {}", request);
        log.debug("REST to toAllLost: {}", model);
        ArrayList<Object> lists = new ArrayList<>();
        List<Record> all = new ArrayList<>();
        if (search == null || search.replace(" ","").equals("")) {
            all = recordRepository.findAllByStatus(0);
        } else {
            all = recordRepository.findAllByContentLike(search.replace(" ","").replace("\"",""));
        }
        System.out.println(search);
        for (Record recordObj : all) {
            HashMap<String, Object> map = (HashMap<String, Object>) MapUtil.objectToMap(recordObj);
            User user = userRepository.findById(recordObj.getLostUserId());
            if(user == null) continue;
            map.put("username",user.getUsername());
            map.put("phone",user.getPhone());
            lists.add(map);
            System.out.println(map.toString());
        }
        model.addAttribute("LostRecords",lists);

        System.out.println("之后 " + model.getAttribute("LostRecords"));
        return "/allLost";
    }

    /**
     * @Description  跳转游客方式进入失物列表
     */
    @RequestMapping("/toTouristAllLost")

    public String toTouristAllLost(String search,Model model, HttpServletRequest request) throws Exception {
        log.debug("REST to TouristAllLost: {}", request);
        request.getSession().invalidate();
        if (model.getAttribute("LostRecords") != null) {
            System.out.println("开始 " + model.getAttribute("LostRecords"));
            request.removeAttribute("LostRecords");
        }
        ArrayList<Object> lists = new ArrayList<>();
        List<Record> all = new ArrayList<>();
        if (search == null || search.replace(" ","").equals("")) {
            all = recordRepository.findAllByStatus(0);
        } else {
            all = recordRepository.findAllByContentLike(search.replace(" ",""));
        }
        System.out.println(search);
        for (Record recordObj : all) {
            HashMap<String, Object> map = (HashMap<String, Object>) MapUtil.objectToMap(recordObj);
            User user = userRepository.findById(recordObj.getLostUserId());
            if(user == null) continue;
            map.put("username",user.getUsername());
            map.put("phone",user.getPhone());
            lists.add(map);
            System.out.println(map.toString());
        }
        model.addAttribute("LostRecords",lists);

        System.out.println("之后 " + model.getAttribute("LostRecords"));
        return "/allLost";
    }

    /**
     * @Description 跳转注册页面
     */
    @RequestMapping("/toregister")
    public String toRegister(){
        return "register";
    }

    /**
     * @Description 注册
     */
    @RequestMapping("/register")
    public String Register(User users, Model model, ServletRequest request, ServletResponse response){
        try {
            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(users);
        User byPhone = userRepository.findByPhone(users.getPhone());
        User bySchoolNum = userRepository.findByUsername(users.getUsername());
        if (byPhone == null || bySchoolNum == null){
            User i = userRepository.save(users);
            return "redirect:/login";
        }else {
            model.addAttribute("mess","该手机号或学号已注册");
            return "register";
        }
    }

    /**
     * @Description 退出
     */
    @RequestMapping("/logOut")
    public String logOut(HttpServletRequest request){
        request.getSession().invalidate();
        return "redirect:/login";
    }

    @RequestMapping("/toUser")
    public String toUser(HttpServletRequest request, Model model){
        int id = (int) request.getSession().getAttribute("userId");
        System.out.println(id);
        User user = new User();
        try {
            user = userRepository.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("Users",user);
        System.out.println(user);
        return "/user";
    }

    /**
     * @Description 跳转修改个人信息页面
     */
    @RequestMapping("/toUpdateUser")
    public String toUpdateUser(int id, Model model){
        User user = userRepository.findById(id);
        model.addAttribute("user",user);
        System.out.println(user);
        return "updateUser";
    }

    /**
     * @Description  修改个人信息
     */
    @RequestMapping("/updateUser")
    public String updateUser(User user){
        User save = userRepository.save(user);
        return "redirect:/toUser";
    }

}
