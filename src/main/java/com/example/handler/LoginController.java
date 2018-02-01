package com.example.handler;

import com.example.model.FilterCriteria;
import com.example.model.Phone;
import com.example.model.User;
import com.example.repository.db.PhoneRepository;
import com.example.service.db.PhoneService;
import com.example.service.db.UserService;
import com.example.dump.Phones;
import com.example.dump.Users;
import com.example.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.naming.OperationNotSupportedException;
import javax.validation.Valid;

@Controller
@RequestMapping("/phones")
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    PhoneService phoneService;

    @Autowired
    private PhoneRepository repository;

    @Autowired
    private Environment environment;

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @GetMapping("/login")
    public ModelAndView login() {
        log.info("In /login method Get");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }

    @GetMapping("/registration")
    public ModelAndView register(@ModelAttribute User user) {
        log.info("In /registration method Get");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("registration");
        return mav;
    }

    @PostMapping(value = "/registration")
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        log.info("In /registration method Post");
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByLogin(user.getLogin());
        if (userExists != null) {
            bindingResult
                    .rejectValue("login", "error.user",
                            "There is already a user registered with the login provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("success");
        }
        return modelAndView;
    }

    @GetMapping("/newfield")
    public ModelAndView addField(@ModelAttribute Phone phone) {
        log.info("In /newfield method Get");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("newfield");
        return mav;
    }

    @PostMapping(value = "/newfield")
    public String createNewField(@Valid Phone phone, BindingResult result, Model model) {
        log.info("In /newfield method Post");
        if (result.hasErrors()) {
            return "newfield";
        } else {
            String login = Utils.getLoginFromContext();
            User user = userService.findUserByLogin(login);
            phone.setUser(user);
            phoneService.savePhone(phone);
            model.addAttribute("successMessage", "New contact is added successfully");
            return "redirect:/phones/book";
        }
    }

    @GetMapping(value = {"/delete-{id}"})
    public String deleteField(@PathVariable Long id) {
        log.info("In /delete-{id} method Get");
        phoneService.deletePhone(id);
        return "redirect:/phones/book";
    }

    @PostMapping(value = {"/find"})
    public ModelAndView filterBook(FilterCriteria criteria) {
        log.info("In /find method Post");
        ModelAndView mav = new ModelAndView();
        String login = Utils.getLoginFromContext();
        mav.addObject("userBook", phoneService.findRowBySearchCriteria(login, criteria.getSearchParam()));
        mav.addObject("resultMessage", "Result of search");
        mav.setViewName("book");
        return mav;
    }

    @GetMapping(value = {"/edit-{id}"})
    public ModelAndView editField(@PathVariable Long id) {
        log.info("In /edit-{id} method Get");
        ModelAndView mav = new ModelAndView();
        Phone phone = phoneService.getPhoneById(id);
        mav.addObject("phone", phone);
        mav.setViewName("updatefield");
        return mav;
    }

    @PostMapping(value = {"/updatefield"})
    public String updateField(@Valid Phone phone, BindingResult result) {
        log.info("In /updatefield method Get");
        if (result.hasErrors()) {
            return "updatefield";
        }
        phoneService.updatePhone(phone);
        return "redirect:/phones/book";
    }

    @GetMapping("/book")
    public ModelAndView getAllUserData() {
        log.info("In /book method Get");
        String login = Utils.getLoginFromContext();
        FilterCriteria filterCriteria = new FilterCriteria();
        ModelAndView mav = new ModelAndView();
//        mav.addObject("userBook", repository.findByUser_Login(login));
        mav.addObject("userBook", phoneService.findRowBySearchCriteria(login,""));
        mav.addObject("filterCriteria", filterCriteria);
        mav.setViewName("book");
        return mav;
    }
    @GetMapping("/dump")
    public String dumpData(Model model) {
        try {
            String path = environment.getRequiredProperty("file.pathtodump");
            Utils.dumpData(Users.class,userService.getUsers(),path);
            Utils.dumpData(Phones.class,phoneService.getPhones(),path);
        } catch (OperationNotSupportedException e) {
            log.info("For MySQL is not supported");
        }
        return "redirect:/phones/book";
    }
}
