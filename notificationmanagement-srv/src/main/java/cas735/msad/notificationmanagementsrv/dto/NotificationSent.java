package cas735.msad.notificationmanagementsrv.dto;

import lombok.Value;

import java.io.Serializable;

@Value
public class NotificationSent implements Serializable{
    int notificationStatusCode;
    String notificationServiceResponse; 
}
