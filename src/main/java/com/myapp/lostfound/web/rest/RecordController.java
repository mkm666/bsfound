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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class RecordController {


    private final Logger log = LoggerFactory.getLogger(RecordController.class);

    @Autowired
    private RecordRepository RecordRepository;

    @Autowired
    private UserRepository UserRepository;

    @RequestMapping("/toUserLost")
    public String toUserLost(HttpServletRequest request, Model model){
        log.debug("REST to UserLost: {}", request);
        int lostUserId = (int) request.getSession().getAttribute("userId");
        System.out.println(lostUserId);
        ArrayList<Object> lists = new ArrayList<>();
        List<Record> records = new ArrayList<>();
        try {
            records = RecordRepository.findAllByLostUserId(lostUserId);
            for (Record recordObj : records) {
                HashMap<String, Object> map = (HashMap<String, Object>) MapUtil.objectToMap(recordObj);
                User user = UserRepository.findById(recordObj.getLostUserId());
                if(user == null) continue;
                map.put("username",user.getUsername());
                map.put("phone",user.getPhone());
                lists.add(map);
                System.out.println(map.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("Records",lists);
        return "/userLost";
    }

    /**
     * @Description  跳转增加失物招领信息页面
     */
    @RequestMapping("/toAddLost")
    public String toAddLost(){
        log.debug("REST to AddLost");
        return "addLost";
    }

    /**
     * @Description  增加失物招领信息
     */
    @RequestMapping("/addLost")
    public String addLost(Record record, Model model){
        log.debug("REST to addLost: {}", record);
        Record save = RecordRepository.save(record);
        return "redirect:/toUserLost";
    }

    /**
     * @Description 跳转修改失物招领信息页面
     */
    @RequestMapping("/toUpdateLost")
    public String toUpdateLost(int id, Model model){
        Record record = RecordRepository.findById(id);
        model.addAttribute("record",record);
        return "updateLost";
    }

    /**
     * @Description  修改失物招领信息
     */
    @RequestMapping("/updateLost")
    public String updateLost(Record Record){
        Record save = RecordRepository.save(Record);
        return "redirect:/toUserLost";
    }

    /**
     * @Description  删除失物招领信息
     */
    @RequestMapping("/deleteLost")
    public String deleteLost(Record Record){
        RecordRepository.delete(Record);
        return "redirect:/toUserLost";
    }

    /**
     * @Description  完成失物招领
     */
    @RequestMapping("/finish")
    public String finish(Record Record){
        Record record = RecordRepository.findById(Record.getId());
        int status = record.getStatus();
        if (status == 0){
            record.setStatus(1);
        }
        RecordRepository.save(record);
        return "redirect:/toUserLost";
    }



}
