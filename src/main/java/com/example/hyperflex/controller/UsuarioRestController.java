package com.example.hyperflex.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.hyperflex.dto.UsuarioDto;
import com.example.hyperflex.entity.Usuario;
import com.example.hyperflex.service.UsuarioService;

//El controlador debe tener los llamados a los métodos del service, es el punto de entrada.
@RestController
@RequestMapping("/api/hyperflex") //Indicar endpoint general, ruta donde se ingresa a estos servicios
public class UsuarioRestController {

	//Inyección de dependencias
	@Autowired
	private UsuarioService userService;
	
	//Este mapeo va a realizar la consulta de todos los usuarios
	@GetMapping("/usuarios")
	@ResponseStatus(HttpStatus.OK)//Mantiene el estado de respuesta
	public List<Usuario> consultaUsuarios(){
		return userService.findAllUsuarios();
	}
	
	//Este mapeo va a realizar la consulta por ID de usuarios
	@GetMapping("/usuarios/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> consultaPorIdUdaurios(@PathVariable Long id){
		Usuario user = null;
		String response = "";
		try{
			user=userService.findByIdUsuario(id);
		} catch(Exception e) {
			response= "Error al realizar la consulta. Detalles";
			response = response.concat(e.getMessage().concat(e.getMessage().toString()));
			return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (user == null) {
			response= "El usuario con el ID: ".concat(id.toString().concat(" no existe en la base de datos"));
			return new ResponseEntity<String>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Usuario>(user, HttpStatus.OK);
	}
	
	//Este mapeo es para crear usuarios 
	@PostMapping("/usuarios")
	public ResponseEntity<?> crearUsuario(@RequestBody UsuarioDto userdto){
		Usuario userNuevo = null;
		Map<String, Object> response = new HashMap<>();
		try {
			userNuevo = this.userService.crearUsuario(userdto);
		}catch(Exception e) {
			response.put("mensaje","Eror al realizar el insert. Detalles: ");
			response.put("error", e.getMessage().concat(e.getCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "Usuario creado con éxito, con el ID: "+userNuevo.getId());
		response.put("Usuairo", userNuevo);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	//Este mapeo es para eliminar un unsuario 
	@DeleteMapping("usuarios/{id}")
	public ResponseEntity<?> eliminarUsuario(@PathVariable Long id){
		Map<String, Object> response = new HashMap<>();
		
		try {
			Usuario userDelete= this.userService.findByIdUsuario(id);
			if(userDelete == null) {
				response.put("mensaje", "Error al eliminar. El usuario no existe en la base de datos. ");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			userService.deleteUsuario(id);
		} catch (Exception e) {
			response.put("mensaje", "Error al eliminar en la base de datos. Detalles: ");
			response.put("error", e.getMessage().concat(e.getCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El usuario fue eliminado con éxito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	//Este mapeo es para editar un usuario
	@PutMapping("/usuarios/{id}")
	public ResponseEntity<?> editarUsuario(@PathVariable Long id, @RequestBody UsuarioDto userdto){
		Usuario userEdit= null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			userEdit = this.userService.editarUsuario(id, userdto);
			if(userEdit == null) {
				response.put("mensaje", "Error al editar el usuario no existe en la base de datos");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			response.put("mensaje", "Error al ectualizar en la base de datos. Detalles: ");
			response.put("error", e.getMessage().concat(e.getCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "Usuario actualizado con éxito, con el ID: "+ id);
		response.put("mensaje", userEdit);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}
