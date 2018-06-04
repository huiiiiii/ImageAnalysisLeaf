package com.example.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.LeafTypes.LeafTypeName;

@RestController
public class LeafController {

    @RequestMapping("/leaftype")
    public LeafTypeName getLeafType(@RequestParam(value="name", defaultValue="World") String name) {
        return LeafTypeName.Herzfoermig;
    }
    
    @RequestMapping("/leaftypelist")
    public LeafTypeName[] getLeafTypeList(@RequestParam(value="name", defaultValue="World") String name) {
        return LeafTypeName.values();
    }
}