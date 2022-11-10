package com.adidas.backend.prioritysaleservice;

import com.adidas.backend.prioritysaleservice.controllers.PrioritySaleController;
import com.adidas.backend.prioritysaleservice.services.PrioritySaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Timer;
import java.util.TimerTask;

@SpringBootApplication
public class MembersServiceApplication {

	private static PrioritySaleController controller;

	public static void main(String[] args) {
		SpringApplication.run(MembersServiceApplication.class, args);

		Timer timer = new Timer("Timer");

		long thirtyMinutes = 30 * 60 * 1000;

		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				controller.selectClients();
			}
		}, thirtyMinutes);
	}

}
