package com.example.hyperflex.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hyperflex.dto.UsuarioDto;
import com.example.hyperflex.entity.Usuario;
import com.example.hyperflex.repository.IUsuario;



@Service
public class UsuarioService {

	@Autowired
	private IUsuario usuarioRepository;
	
	//Consultar todos los usuarios
	@Transactional(readOnly=true)
	public List<Usuario> findAllUsuarios(){
		return (List<Usuario>) usuarioRepository.findAll();
	}
	
	//Consultar por id
	@Transactional(readOnly=true)
	public Usuario findByIdUsuario(Long id) {
		return (Usuario) usuarioRepository.findById(id).orElse(null);
	}
	
	//Crear Usuarios
	@Transactional
	public Usuario crearUsuario(UsuarioDto userdto) {
		
		Usuario userNuevo = new Usuario();
		userNuevo.setNombre(userdto.getNombre());
		userNuevo.setApellido1(userdto.getApellido1());
		userNuevo.setApellido2(userdto.getApellido2());
		userNuevo.setCurp(userdto.getCurp());
		userNuevo.setDomicilio(userdto.getDomicilio());
		userNuevo.setCelular(userdto.getCelular());
		userNuevo.setEmail(userdto.getEmail());
		
		return usuarioRepository.save(userNuevo);
	}
	
	//Eliminar Usuarios
	@Transactional
	public void deleteUsuario(Long id) {
		usuarioRepository.deleteById(id);
	}
	
	//Editar Usuarios
	@Transactional
	public Usuario editarUsuario(Long id, UsuarioDto userdto) {
		Usuario userEdit = usuarioRepository.findById(id).orElse(null);
		if(userEdit != null) {
			userEdit.setNombre(userdto.getNombre());
			userEdit.setApellido1(userdto.getApellido1());
			userEdit.setApellido2(userdto.getApellido2());
			userEdit.setCurp(userdto.getCurp());
			userEdit.setDomicilio(userdto.getDomicilio());
			userEdit.setCelular(userdto.getCelular());
			userEdit.setEmail(userdto.getEmail());
			
			return usuarioRepository.save(userEdit);
		}
		return null;
	}
}
