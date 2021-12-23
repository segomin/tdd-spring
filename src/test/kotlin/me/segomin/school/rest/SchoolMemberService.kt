package me.segomin.school.rest

import me.segomin.school.dto.SchoolMember
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService

class MemberAuthority(private val role: String): GrantedAuthority {
    override fun getAuthority(): String {
        return role
    }
}

class SchoolMemberService: UserDetailsService {
    class SchoolMemberDetails(val name: String, val passwd: String, private val roles: List<String>): UserDetails {

        constructor(member: SchoolMember) : this(member.name, member.passwd, member.getRole())

        override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
            return roles.map(::MemberAuthority).toMutableList()
        }

        override fun getPassword() = passwd

        override fun getUsername() = name

        override fun isAccountNonExpired(): Boolean {
            TODO("Not yet implemented")
        }

        override fun isAccountNonLocked(): Boolean {
            TODO("Not yet implemented")
        }

        override fun isCredentialsNonExpired(): Boolean {
            TODO("Not yet implemented")
        }

        override fun isEnabled(): Boolean {
            TODO("Not yet implemented")
        }
    }

    override fun loadUserByUsername(username: String): UserDetails {
        TODO("Not yet implemented")
    }

}
