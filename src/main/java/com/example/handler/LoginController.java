package com.example.handler;

import com.example.dump.Phones;
import com.example.dump.Users;
import com.example.model.FilterCriteria;
import com.example.model.Phone;
import com.example.model.User;
import com.example.service.db.PhoneService;
import com.example.service.db.UserService;
import com.example.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/")
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    PhoneService phoneService;

    @Autowired
    private Environment environment;

    @Autowired
    AuthenticationTrustResolver trustResolver;

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @GetMapping("/login")
    public String login() {
        log.info("In /login method Get");
        if (!isAnonymous()) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            System.out.println("Is remember-me :"+trustResolver.isRememberMe(authentication));
            return "redirect:/book";
        } else {
            return "login";
        }
    }

    @GetMapping("/registration")
    public String register(@ModelAttribute User user) {
        log.info("In /registration method Get");
        return "registration";
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

    @PreAuthorize("isFullyAuthenticated()")
    @GetMapping("/newfield")
    public String addField(@ModelAttribute Phone phone) {
        log.info("In /newfield method Get");
        return "newfield";
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
            return "redirect:/book";
        }
    }

    @PreAuthorize("isFullyAuthenticated()")
    @GetMapping(value = {"/delete-{id}"})
    public String deleteField(@PathVariable Long id) {
        log.info("In /delete-{id} method Get");
        phoneService.deletePhone(id);
        return "redirect:/book";
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

    @PreAuthorize("isFullyAuthenticated()")
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
        log.info("In /updatefield method Get Phone " + phone);
        if (result.hasErrors()) {
            return "updatefield";
        }
        String login = Utils.getLoginFromContext();
        User user = userService.findUserByLogin(login);
        phone.setUser(user);
        phoneService.updatePhone(phone);
        return "redirect:/book";
    }

    @GetMapping("/book")
    public ModelAndView getAllUserData(Principal user) {
        log.info("In /book method Get");
        String login = user.getName();
        FilterCriteria filterCriteria = new FilterCriteria();
        ModelAndView mav = new ModelAndView();
        mav.addObject("userBook", phoneService.findRowBySearchCriteria(login, ""));
        mav.addObject("filterCriteria", filterCriteria);
        mav.setViewName("book");
        return mav;
    }

    @GetMapping("/dump")
    public String dumpData(Model model) {
        String path = environment.getRequiredProperty("file.pathtodump");
        Utils.dumpData(Users.class, new Users(), path);
        Utils.dumpData(Phones.class, new Phones(), path);
        return "redirect:/book";
    }

    public boolean isAnonymous() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return trustResolver.isAnonymous(authentication);
    }
}
