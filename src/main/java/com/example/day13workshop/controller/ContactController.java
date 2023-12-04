package com.example.day13workshop.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.day13workshop.model.Contact;
import com.example.day13workshop.repo.ContactRedis;
import com.example.day13workshop.repo.ContactRepository;

import jakarta.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Controller
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    ContactRedis contactRepository;

    @GetMapping("/addcontact") //url: localhost:8080/contacts/addcontact
    //model acts as a container for any data you want to pass to the view. here it's used to store the Contact object.
    public String addContact(Model model){
        Contact contact = new Contact();
        model.addAttribute("contact", contact);
        return "addcontact";
    }

    @PostMapping("/savecontact") //url: localhost:8080/contacts/savecontact
    public String saveContact(@Valid @ModelAttribute("contact") Contact contactForm, BindingResult result, Model model) throws FileNotFoundException{
        //When a form is submitted, @ModelAttribute binds the incoming form data to a Java object.
        if (result.hasErrors()){
            return "addcontact"; //stay at same page if theres errors
        }  
        //call the repo
        contactRepository.addContact(contactForm);
        contactRepository.save(contactForm);

        model.addAttribute("savedContact", contactForm);
        return "success";
    }

    @GetMapping("") //url: localhost:8080/contacts
    public String listContacts(Model model){
        List<Contact> allContacts = contactRepository.findAll();
        model.addAttribute("allContacts", allContacts);
        // System.out.println("allcontacts: " + allContacts);
        return "contacts";
    }

    @GetMapping("/{id}") //url: localhost:8080/contacts/id
    public String getContact(@PathVariable("id") String id, Model model) throws IOException{
        String content = contactRepository.findOne(id);
        if (content.equals("")){
            model.addAttribute("error", "Contact not found");
            model.addAttribute("content", null);
            System.out.println("content from controller: "+ content);
        } else {
            model.addAttribute("content", content);
            
            System.out.println("content from controller: "+ content);
        }
        return "contactpage";
    }
    
   
}
