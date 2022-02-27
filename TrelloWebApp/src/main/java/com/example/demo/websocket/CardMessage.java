package com.example.demo.websocket;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CardMessage {

    private String sourceUserGmail;
    private String targetUserGmail;
    private String method;
    private Long cardID;
    private Long boardID;
    private String cardCategory;
    private String cardTitle;

}
