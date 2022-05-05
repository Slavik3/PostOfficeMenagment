package com.post.menagment.controller;

import com.post.menagment.model.PostOffice;
import com.post.menagment.services.PostOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostOfficeController {

    @Autowired
    private PostOfficeService postOfficeService;


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public HttpStatus addNewPostOffice(@RequestBody PostOffice postOffice) {
        postOfficeService.create(postOffice);
        return HttpStatus.OK;
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public HttpStatus updatePostOffice(@RequestBody PostOffice postOffice) {
        postOfficeService.update(postOffice);
        return HttpStatus.OK;
    }

    @RequestMapping(value = "/isPostOfficeAvailable/{postOfficeId}", method = RequestMethod.GET)
    public boolean isPostOfficeAvailable(@PathVariable long postOfficeId) {
        System.out.println("=======================");
        return postOfficeService.isPostOfficeAvailable(postOfficeId);
    }


}
