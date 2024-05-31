package com.pinsoft.interns.Service;

import com.pinsoft.interns.DTO.CardInfoRequest;
import com.pinsoft.interns.Entity.CardInfo;
import com.pinsoft.interns.Entity.User;
import com.pinsoft.interns.Repository.CardInfoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class CardInfoService {
    private final CardInfoRepository cardInfoRepository;
    private final UserService userService;


    public CardInfo findCard(long id) {
        return cardInfoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Card info not found"));
    }

    public List<CardInfo> findAllCardsByUser(long id) {
        User user = userService.getUserById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        return user.getPayments();
    }


    public CardInfo addCard(CardInfoRequest cardInfoRequest, long id ) {
        if (validateCard(cardInfoRequest)) {
            CardInfo cardInfo = new CardInfo();
            User user = userService.getUserById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));

            cardInfo.setUser(user);
            cardInfo.setCvv(cardInfoRequest.getCvv());
            cardInfo.setCardHolderName(cardInfoRequest.getCardHolderName());
            cardInfo.setExpirationDate(cardInfoRequest.getExpirationDate());
            cardInfo.setCreditCardNumber(cardInfoRequest.getCreditCardNumber());
            return cardInfoRepository.save(cardInfo);
        }
        else {
            throw new IllegalArgumentException("Invalid card info details");
        }
    }

    private boolean validateCard(CardInfoRequest cardInfoRequest) {
        return isValidCreditCardNumber(cardInfoRequest.getCreditCardNumber())
                && isValidExpirationDate(cardInfoRequest.getExpirationDate())
                && isValidCVV(cardInfoRequest.getCvv());
    }

    private boolean isValidCreditCardNumber(String creditCardNumber) {
        int nDigits = creditCardNumber.length();
        int nSum = 0;
        boolean isSecond = false;
        for (int i = nDigits - 1; i >= 0; i--) {
            int d = creditCardNumber.charAt(i) - '0';
            if (isSecond) d = d * 2;
            nSum += d / 10;
            nSum += d % 10;
            isSecond = !isSecond;
        }
        return (nSum % 10 == 0);
    }

    private boolean isValidExpirationDate(String expirationDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
        try {
            YearMonth expDate = YearMonth.parse(expirationDate, formatter);
            return expDate.isAfter(YearMonth.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private boolean isValidCVV(String cvv) {
        return Pattern.matches("\\d{3,4}", cvv);
    }


}
