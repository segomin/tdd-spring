package me.segomin.school.rest

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SchoolController {

    @GetMapping("/about")
    fun getAbout() {
    }
}