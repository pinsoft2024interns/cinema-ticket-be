package com.pinsoft.interns.Controller;

import com.pinsoft.interns.DTO.CardInfoRequest;
import com.pinsoft.interns.DTO.PaymentRequest;
import com.pinsoft.interns.Entity.CardInfo;
import com.pinsoft.interns.Entity.Payment;
import com.pinsoft.interns.Entity.Reservation;
import com.pinsoft.interns.Repository.PaymentRepository;
import com.pinsoft.interns.Repository.ReservationRepository;
import com.pinsoft.interns.Service.CardInfoService;
import com.pinsoft.interns.Service.PaymentService;
import com.pinsoft.interns.Service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/pay")
    public void pay(@RequestBody PaymentRequest paymentRequest) throws Exception {
        paymentService.pay(paymentRequest);
    }

    @GetMapping("/pay/{id}")
    public Payment getPay(@PathVariable long id  ) throws Exception {
       return paymentService.findpay(id);
    }
}
