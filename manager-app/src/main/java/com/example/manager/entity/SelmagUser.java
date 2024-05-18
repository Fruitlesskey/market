package com.example.manager.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_user", schema = "user_management")
public class SelmagUser {

  @Id
  @GeneratedValue
  private Integer id;

  @Column(name = "c_username")
  private String username;

  @Column(name = "c_password")
  private String password;

  @ManyToMany
  private List<Authority> authorities;

}
