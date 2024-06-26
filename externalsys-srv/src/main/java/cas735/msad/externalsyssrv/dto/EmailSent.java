package cas735.msad.externalsyssrv.dto;

import lombok.Value;

import java.io.Serializable;

@Value
public class EmailSent implements Serializable{
    int smtpStatusCode;
    String smtpServerResponse; 
}
