package com.example.day13workshop.repo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import com.example.day13workshop.Utils;
import com.example.day13workshop.model.Contact;

@Repository
public class ContactRedis {
    
    @Autowired @Qualifier("ContactRedis")
    private RedisTemplate<String,Object> template;
    
    private String directoryName = "C:/Users/izt_4/Documents/Visa/Server Side Foundation/codes/day15workshop/vttp-m2-day13/day13workshop/src/main";
    
    private String fileName = "contacts.txt";
    private List<Contact> allContacts;

    // public ContactRedis() throws ParseException, FileNotFoundException{
    //     if (allContacts == null){
    //          allContacts = new ArrayList<>();
    //     }
    //     DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    //     Date dt;
    //     // Contact c1 = new Contact("lee hsien dragon", "lhd@gmail.com", "1234999", dt = df.parse("2000-01-02"), RandomStringUtils.randomAlphanumeric(8));
    //     // allContacts.add(c1);
    //     // save(c1);
    //     // Contact c2 = new Contact("pritam", "ps@gmail.com", "202920222", dt = df.parse("2022-01-02"), RandomStringUtils.randomAlphanumeric(8));
    //     // allContacts.add(c2);
    //     // save(c2);
        
    // }

    //get all contacts
    public List<Contact> findAll(){
        return allContacts;
    }

    //add one contact
    public void addContact(Contact contact){
        if (allContacts == null){
             allContacts = new ArrayList<>();
        }
        allContacts.add(contact);
    }

    //save one contact
    public void save(Contact contact) throws FileNotFoundException{
        String contactFileName = contact.getId();
        File f = new File(directoryName+"/"+contactFileName); //create file object

        //OutputStream is an abstract class in Java; cannot directly create an instance of OutputStream
        //FileOutputStream expects a File or a file path as its argument
        OutputStream os = new FileOutputStream(f);
        PrintWriter pw = new PrintWriter(os);
        pw.write(contact.toString());
        pw.flush();
        pw.close();
        ValueOperations VO = template.opsForValue();
        VO.set(contact.getId(), contact);
        System.out.printf("Key: %s",contact.getId());
    }

    //find one contact
    public String findOne(String id) throws IOException{
        String foundContactFile = id;
        System.out.println("id in repo " + id);
        File f = new File(directoryName+foundContactFile);
        if (f.exists()){
            Path path = Paths.get(f.toString());
            String content = Files.readString(path);
            System.out.println("content from repo " + content);
            return content;
        } else {
            System.out.println("nothing found from repo");
            return "";
        }
    }


}
