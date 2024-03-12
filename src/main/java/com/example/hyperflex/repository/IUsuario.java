package com.example.hyperflex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hyperflex.entity.Usuario;

@Repository
public interface IUsuario extends JpaRepository<Usuario, Long>{

}
