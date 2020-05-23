package com.myapp.lostfound.web.rest;

import com.myapp.lostfound.domain.ThankLetter;
import com.myapp.lostfound.repository.ThankLetterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ThankLetterController {

    private final Logger log = LoggerFactory.getLogger(ThankLetterController.class);

    @Autowired
    private ThankLetterRepository thankLetterRepository;

    @RequestMapping("/toLetter")
    public String toLetter(HttpServletRequest request, Model model){
        List<ThankLetter> letters = new ArrayList<>();
        try {
            letters = thankLetterRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("Letters",letters);
        System.out.println(letters);
        return "/letter";
    }

    /**
     * @Description  跳转增加感谢信信息页面
     */
    @RequestMapping("/toAddLetter")
    public String toAddLetter(){
        return "addLetter";
    }

    /**
     * @Description  增加感谢信信息
     */
    @RequestMapping("/addLetter")
    public String addLetter(ThankLetter letter, Model model){
        ThankLetter save = thankLetterRepository.save(letter);
        return "redirect:/toLetter";
    }

}
