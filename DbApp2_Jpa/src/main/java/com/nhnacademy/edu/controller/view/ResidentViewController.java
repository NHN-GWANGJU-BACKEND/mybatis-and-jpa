package com.nhnacademy.edu.controller.view;

import com.nhnacademy.edu.domain.DTO.ResidentDTO;
import com.nhnacademy.edu.service.ResidentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/residents")
public class ResidentViewController {
    private final ResidentService residentService;

    public ResidentViewController(ResidentService residentService) {
        this.residentService = residentService;
    }

    @GetMapping
    public String mainUserList(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "5") int size,
                               Model model) {
        PageRequest pageable = PageRequest.of(page, size);

        Page<ResidentDTO> residentDTOPage = residentService.findAll(pageable);

        List<ResidentDTO> residents = residentDTOPage.getContent();
        int nowPage = residentDTOPage.getNumber();
        boolean hasPrev = residentDTOPage.hasPrevious();
        boolean hasNext = residentDTOPage.hasNext();

        model.addAttribute("residents", residents);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("hasPrev", hasPrev);
        model.addAttribute("hasNext", hasNext);

        return "residents";
    }

    @PostMapping("{serialNumber}")
    public String removeResident(@PathVariable("serialNumber") int serialNumber) {

        residentService.delete(serialNumber);

        return "redirect:/residents";
    }

}
