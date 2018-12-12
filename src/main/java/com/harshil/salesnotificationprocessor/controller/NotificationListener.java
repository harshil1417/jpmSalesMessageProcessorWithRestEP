package com.harshil.salesnotificationprocessor.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harshil.salesnotificationprocessor.inmemorystorage.InMemoryStorage;
import com.harshil.salesnotificationprocessor.model.Sale;
import com.harshil.salesnotificationprocessor.model.SaleMessage;
import com.harshil.salesnotificationprocessor.model.SalesNotification;
import com.harshil.salesnotificationprocessor.service.MessageProcessor;
import com.harshil.salesnotificationprocessor.service.ReportGenerator;
import com.harshil.salesnotificationprocessor.service.impl.SalesMessageProcessor;
import com.harshil.salesnotificationprocessor.service.impl.SalesReportGeneratorImpl;

@RestController
@RequestMapping("notification")
public class NotificationListener {

	@PostMapping(value = "process")
	public void processMessage(@RequestBody SaleMessage notificataion) {

		System.out.println("Message received by Listener");
		ReportGenerator reportGenerator = new SalesReportGeneratorImpl();
		MessageProcessor messageProcessor = new SalesMessageProcessor(reportGenerator);

		InMemoryStorage.notificationsQueue.add(new SalesNotification(notificataion.getMessageType(),
				new Sale(notificataion.getProductName(), notificataion.getUnitPrice(), notificataion.getTotalUnits())));

		messageProcessor.startProcessing();
	}

}
