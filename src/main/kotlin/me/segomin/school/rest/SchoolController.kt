package me.segomin.school.rest

import me.segomin.school.dto.ClassName
import org.springframework.web.bind.annotation.*

@RestController
class SchoolController {

    var aboutContent = "Welcome to TDD School"

    @GetMapping("/about")
    fun getAbout() = aboutContent

    @PostMapping("/about")
    fun postAbout(@RequestBody content: String) {
        aboutContent = content
    }

    @GetMapping("/greeting")
    fun getGreeting() = "Hello, Sego"

    @GetMapping("/scores/{className}")
    fun getScores(@PathVariable("className") className: String): List<String> {
        return listOf("${ClassName.valueOf(className)} A 100", "${ClassName.valueOf(className)} B 95") // presume return class's students score board
    }
}