package com.example.lab.controller;

import com.example.lab.pojo.ResultMessage;
import org.springframework.web.bind.annotation.*;

import static com.example.lab.LabApplication.admin;

@RestController
@RequestMapping("/courseSelection")
public class CourseSelectionController {

    @PostMapping(value = "")
    public ResultMessage changeCourseSelection(@RequestParam("change") Boolean change) {
        try {
            admin.setCourseSelection(change);
            return ResultMessage.SUCCESS;
        }
        catch (Exception exception) {
            return ResultMessage.FAILED;
        }
    }

    @GetMapping(value = "")
    public ResultMessage getCourseSelection() {
        if (admin.getCourseSelection()) {
            return ResultMessage.SUCCESS;
        }
        else {
            return ResultMessage.FAILED;
        }
    }
}
