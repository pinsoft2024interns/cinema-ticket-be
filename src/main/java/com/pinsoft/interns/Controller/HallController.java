package com.pinsoft.interns.Controller;

import com.pinsoft.interns.DTO.HallRequest;
import com.pinsoft.interns.DTO.HallUpdateRequest;
import com.pinsoft.interns.Entity.Hall;
import com.pinsoft.interns.Service.HallService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HallController {

    private final HallService hallService;

    @GetMapping("/halls")
    public List<Hall> getHalls() {
        return hallService.getAll();
    }

    @PostMapping("/hall")
    public Hall createHall(@RequestBody HallRequest hallRequest)  {return hallService.addHall(hallRequest);}

    @GetMapping("/hall/{id}")
    public Hall getCinema(@PathVariable long id) {return hallService.findHall(id);}

    @PutMapping("hall/{id}")
    public Hall updateCinema(@PathVariable long id,@RequestBody HallUpdateRequest hallUpdateRequest) {
        return hallService.updateHall(hallUpdateRequest , id);
    }

    @DeleteMapping("hall/{id}")
    public void deleteHall(@PathVariable long id)  {hallService.deleteHall(id);}

    @GetMapping("hall/showing/{id}")
    public Hall getHall(@PathVariable long id) {return hallService.findHallByShowing(id);}

}
