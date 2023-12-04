package com.example.day13workshop;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.day13workshop.repo.ContactRepository;

@SpringBootApplication
//public class Day13workshopApplication implements ApplicationRunner{
public class Day13workshopApplication{
	//ApplicationRunner run() will get executed just after applicationcontext is created and before spring boot application startup.
	public static void main(String[] args) {
		SpringApplication.run(Day13workshopApplication.class, args); //passing args in
	}
/*
	// args[0] vs --D command
	@Override
	public void run(ApplicationArguments args) throws Exception {
		//check if user passed in dataDir argument
		if (args.containsOption("dataDir")) {
			//1. retreieve directory path first
			final String dataDir = args.getOptionValues("dataDir").get(0);
			//args.getOptionValues("dataDir") returns a list of values associated with the dataDir argument

			//2. then create file path/object
			//convert the directory path (a string) into a File object. 
			//This allows you to interact with the filesystem 
			File fileDir = new File(dataDir);
			if (!fileDir.exists()){ //if it doesnt exist, create the directory;
				fileDir.mkdirs();
				System.out.println("***" + fileDir.getAbsolutePath());
				System.out.println("***" + fileDir.getPath());
				System.out.println("***" + fileDir.getParent());
			} else {
				System.out.println("directory path: " + fileDir.getAbsolutePath());
			}
		} else {
			System.out.println("❌ Error - No directory specified");
		}
	}
	*/
}