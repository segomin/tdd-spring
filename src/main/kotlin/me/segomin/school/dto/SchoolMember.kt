package me.segomin.school.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import kotlin.reflect.full.memberProperties

enum class UserType {
    STUDENT,
    TEACHER,
    PRINCIPAL
}

enum class ClassName {
    Alpha,
    Beta,
    Gamma;
}

abstract class SchoolMember(val name: String, val passwd: String, val userType: UserType) {
    @JsonIgnore
    fun getRole(): List<String> {
        return when(userType) {
            UserType.STUDENT -> listOf("ROLE_USER")
            UserType.TEACHER -> listOf("ROLE_TEACHER", "ROLE_USER")
            UserType.PRINCIPAL -> listOf("ROLE_PRINCIPAL", "ROLE_USER")
        }
    }

    override fun toString() = getToString(this)
}

/**
 * !! End Class object 의 parameter name 과 실제 field name 이 다를 경우 @RequestBody 에서 mapping 이 실패할 수 있음 (400 error ?)
 * Controller 에서 Teacher type 으로 선언이 되어 있다 하더라도 userType 은 다른 값이 들어오더라도 에러는 발생하지 않음 !!
 * (mapping 이 reflection 방식에 의해 객체를 할당하면서 parameter name 이 영향을 미침)
 */
class Student(name: String, passwd: String, val className: ClassName) : SchoolMember(name, passwd, UserType.STUDENT)

class Teacher(name: String, passwd: String, val className: ClassName) : SchoolMember(name, passwd, UserType.TEACHER)

class Principal(name: String, passwd: String) : SchoolMember(name, passwd, UserType.PRINCIPAL)

private fun getToString(obj: Any): String {
    val propsString = obj::class.memberProperties
        .joinToString(", ") {
            val value = it.getter.call(obj).toString()
            "${it.name}=${value}"
        }
    return "${obj::class.simpleName} [${propsString}]"
}
