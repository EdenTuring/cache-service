package com.assignment.cache.controller;

import com.assignment.cache.model.Bond;
import com.assignment.cache.service.BondService;
import org.springframework.web.bind.annotation.*;

import static com.assignment.cache.controller.BondController.BASE_PATH;

@RestController
@RequestMapping(BASE_PATH)
public class BondController {

    static final String BASE_PATH = "/bond";

    private final BondService bondService;

    public BondController(BondService bondService) {
        this.bondService = bondService;
    }

    @PostMapping
    public String uploadBond(@RequestBody Bond bond) {

        bondService.saveNewBond(bond);

        return "Saved";
    }

    @GetMapping("/{bondId}")
    public Bond getById(@PathVariable String bondId) {
        return bondService.getBondById(bondId);
    }
}
