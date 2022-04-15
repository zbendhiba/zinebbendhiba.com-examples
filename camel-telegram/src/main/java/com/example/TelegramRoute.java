package com.example;

import java.util.Arrays;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.telegram.model.IncomingMessage;
import org.apache.camel.component.telegram.model.InlineKeyboardButton;
import org.apache.camel.component.telegram.model.OutgoingTextMessage;
import org.apache.camel.component.telegram.model.ReplyKeyboardMarkup;

public class TelegramRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("telegram:bots?authorizationToken={{telegram-token-api}}")
                .process(exchange -> {
                    IncomingMessage incomingMessage = exchange.getMessage().getBody(IncomingMessage.class);

                    if(incomingMessage.getText()!=null && incomingMessage.getText().contains("Option")){
                        OutgoingTextMessage msg = new OutgoingTextMessage();
                        msg.setText("Your answer was accepted!");

                        ReplyKeyboardMarkup replyMarkup = ReplyKeyboardMarkup.builder()
                                .removeKeyboard(true)
                                .build();

                        msg.setReplyMarkup(replyMarkup);


                        exchange.getIn().setBody(msg);
                    }else {

                        OutgoingTextMessage msg = new OutgoingTextMessage();
                        msg.setText("Choose one option!");

                        InlineKeyboardButton buttonOptionOneI = InlineKeyboardButton.builder()
                                .text("Option One - I").build();

                        InlineKeyboardButton buttonOptionOneII = InlineKeyboardButton.builder()
                                .text("Option One - II").build();

                        InlineKeyboardButton buttonOptionTwoI = InlineKeyboardButton.builder()
                                .text("Option Two - I").build();

                        ReplyKeyboardMarkup replyMarkup = ReplyKeyboardMarkup.builder()
                                .keyboard()
                                .addRow(Arrays.asList(buttonOptionOneI, buttonOptionOneII))
                                .addRow(Arrays.asList(buttonOptionTwoI))
                                .close()
                                .oneTimeKeyboard(true)
                                .build();

                        msg.setReplyMarkup(replyMarkup);

                        exchange.getIn().setBody(msg);
                    }
                })
                .to("telegram:bots?authorizationToken={{telegram-token-api}}");
    }
}
