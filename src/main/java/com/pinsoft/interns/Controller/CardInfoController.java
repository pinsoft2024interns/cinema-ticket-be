package com.pinsoft.interns.Controller;

import com.pinsoft.interns.DTO.CardInfoRequest;
import com.pinsoft.interns.Entity.CardInfo;
import com.pinsoft.interns.Service.CardInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class CardInfoController {

    private final CardInfoService cardInfoService;

    @GetMapping("/card/user/{id}")
    public List<CardInfo> getAllCardByUser(@PathVariable long id ) {
        return cardInfoService.findAllCardsByUser(id);
    }

    @PostMapping("/card/user/{id}")
    public CardInfo createCard(@RequestBody CardInfoRequest cardInfoRequest, @PathVariable long id ) {

            return cardInfoService.addCard(cardInfoRequest, id);
    }

    @GetMapping("/card/{id}")
    public CardInfo getCard(@PathVariable long id ) {
        return cardInfoService.findCard(id);
    }
}
