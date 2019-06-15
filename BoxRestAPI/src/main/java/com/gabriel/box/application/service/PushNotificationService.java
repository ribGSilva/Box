package com.gabriel.box.application.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Notification;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

@Service
public class PushNotificationService {
	
	private static final String AUTH_KEY_FCM = "AAAAxdzg9dc:APA91bGx-dm4PQZM5RisZerBoXpk13Xe5_0PjrPFmGDcMEgjCKdJ0B0lPCy6AmcVSU9sKGHHJW-m25ix29Q00m3w8SMiWNBwNrjRquvoarpXgDJcQg8YkXtuYlWBexScE7iDzRMniyN0";

	public void sendNoficiation(String title, String body, String userToken, String action, int notifyId) {
		
		Sender sender = new Sender(AUTH_KEY_FCM);
		
		Notification notification = new Notification.Builder("")
				.title(title)
				.body(body)
				.build();
		
	    Message msg = new Message.Builder()
	    		.notification(notification)
	    		.addData("action", action)
	    		.addData("notifyId", String.valueOf(notifyId))
	    		.build();
	    
	    try {
	        Result result = sender.send(msg, "/topics/MAIN", 5);
            if (result.getMessageId() != null) 
                System.out.println("Push Notification Sent Successfully");
             else 
                System.out.println("ErrorCode " + result.getErrorCodeName());
	        
	    } catch (IOException e) {
	        System.out.println("Error " + e.getLocalizedMessage());
	    }
	}
	
}
