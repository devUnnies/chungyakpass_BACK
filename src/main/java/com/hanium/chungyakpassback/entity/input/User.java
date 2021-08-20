package com.hanium.chungyakpassback.entity.input;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanium.chungyakpassback.entity.authority.Authority;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "inp_user")
public class User {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

//    @OneToOne
//    @JoinColumn(name = "세대구성원id")
//    private 세대구성원 세대구성원본인;

    @Column
    private String email;

    @JsonIgnore
    @Column
    private String password;

    @JsonIgnore
    @Column(name = "activated")
    private boolean activated;

    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;
}