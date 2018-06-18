package com.example.mycontact.controller;

import com.example.mycontact.Service.ContactService;
import com.example.mycontact.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class ContactController {

    @Autowired
    private ContactService service;
    @RequestMapping(value = {"/", "/contact"})
    public String index(Model model){
        model.addAttribute("contacts", service.findAll());
        return "list";
    }

    @RequestMapping(value = "/contact/create", method = RequestMethod.GET)
    public String create(Model model){
        model.addAttribute("contact", new Contact());
        return "form";
    }

    @RequestMapping(value = "/contact/save", method = RequestMethod.POST)
    public String save(@Valid Contact contact, BindingResult result, RedirectAttributes redirect){
        if(result.hasErrors()){
            return "form";
        }
        service.save(contact);
        redirect.addFlashAttribute("success", "Save contact successfully");
        return "redirect:/contact";
    }

    @RequestMapping(value = "/contact/{id}/edit", method = RequestMethod.GET)
    public String edit(@PathVariable int id, Model model){
        model.addAttribute("contact", service.findOne(id));
        return "form";
    }

    @RequestMapping(value = "/contact/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable int id, RedirectAttributes redirectAttributes){
        service.delete(id);
        redirectAttributes.addFlashAttribute("success", "Delete contact successfully");
        return "redirect:/contact";
    }

    @RequestMapping(value = "/contact/search")
    public String search(@RequestParam("s") String s, Model model){
        if(s == ""){
            return "redirect:/contact";
        }
        model.addAttribute("contacts", service.search(s));
        return "list";
    }
    /*@RequestMapping(value = "/login")
    public String login(){
        return "login";
    }
    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/login";
    }*/
}
