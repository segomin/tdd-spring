package me.segomin.school.dto

enum class UserType {
    STUDENT,
    TEACHER,
    PRINCIPAL
}

enum class ClassName {
    Alpha,
    Beta,
    Gamma,
}


open class SchoolMember(val name: String, val passwd: String, val userType: UserType) {
    fun getRole(): List<String> {
        return when(userType) {
            UserType.STUDENT -> listOf("ROLE_USER")
            UserType.TEACHER -> listOf("ROLE_TEACHER", "ROLE_USER")
            UserType.PRINCIPAL -> listOf("ROLE_PRINCIPAL", "ROLE_USER")
        }
    }
}

class Student(username: String, password: String, val classNumber: ClassName) : SchoolMember(username, password, UserType.STUDENT)

class Teacher(username: String, password: String, val classNumber: ClassName) : SchoolMember(username, password, UserType.TEACHER)

class Principal(username: String, password: String) : SchoolMember(username, password, UserType.PRINCIPAL)
