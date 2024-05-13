package gods.work.backend.dto;

import lombok.*;

@Setter
@Getter
public class MailDto {

    private String from;
    private String to;
    private String subject;
    private String content;

}
