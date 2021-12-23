package me.segomin.school.rest

import me.segomin.school.dto.ClassName
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class SchoolController {

    @GetMapping("/about")
    fun getAbout() = "Welcome to TDD School"

    @GetMapping("/greeting")
    fun getGreeting() = "Hello, Sego"

    @GetMapping("/scores/{className}")
    fun getScores(@PathVariable("className") className: String): List<String> {
        return listOf("${ClassName.valueOf(className)} A 100", "${ClassName.valueOf(className)} B 95") // presume return class's students score board
    }
}