package ebix.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ebix.exceptions.UsuarioAlreadyExistsException;
import ebix.exceptions.UsuarioNotFoundException;
import ebix.models.Usuario;
import ebix.repositories.UsuarioRepositorio;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepositorio uRepositorio;

	public Usuario create(Usuario usuario) throws UsuarioAlreadyExistsException {
		//Verifica se o e-mail já existe 
		
		Usuario err;
		try {
			err = this.loadByEmail(usuario.getEmail());
		} catch (UsuarioNotFoundException e) {
			//Não existe, então posso adicionar
			return uRepositorio.create(usuario);
		}
		//Já existe, então retorno um erro
		UsuarioAlreadyExistsException ex = new UsuarioAlreadyExistsException();
		ex.setUsuario(err);
		throw ex;
	}

	public Usuario load(Integer id) throws UsuarioNotFoundException {
		Usuario result = uRepositorio.findUsuarioById(id);
		if (result == null || result.getId() == null) {
			UsuarioNotFoundException ex = new UsuarioNotFoundException();
			ex.setId(id);
			log.error(ex.getLocalizedMessage());
			throw ex;
		}
		return result;
	}
	
	public Usuario loadByEmail(String email) throws UsuarioNotFoundException {
		Usuario result = uRepositorio.findUsuarioByEmail(email);
		if (result == null || result.getId() == null) {
			UsuarioNotFoundException ex = new UsuarioNotFoundException();
			ex.setEmail(email);
			log.error(ex.getLocalizedMessage());
			throw ex;
		}
		return result;
	}
	
	public List<Usuario> loadAll() {
		return uRepositorio.findAll();
	}
	
	public List<Usuario> loadAllByNome(String nome) {
		return uRepositorio.findAllByNome(nome);
	}
	
	public List<Usuario> loadAllByEmail(String email) {
		return uRepositorio.findAllByEmail(email);
	}
	
	public void update(Integer id, Usuario u) throws UsuarioNotFoundException, UsuarioAlreadyExistsException  {
		//Verificando se já existe
		Usuario oldUser = load(id);
		
		//Se chegar aqui é que o id existe
		//Verificar se o email já existe e se é de outro usuario
		try {
			Usuario chkEmail = loadByEmail(u.getEmail());
			//Já possui um usuário com o e-mail, verificando se é o mesmo
			// que estou tentando atualizar
			if (!chkEmail.getId().equals(id)) {
				//O email já existe e é de outro usuaário, então retornar um erro
				UsuarioAlreadyExistsException ex = new UsuarioAlreadyExistsException();
				ex.setUsuario(chkEmail);
				log.error(ex.getLocalizedMessage());
				throw ex;
			}
		} catch (UsuarioNotFoundException e) {
			//O email não existe, mas o usuário existe, deve ser um update de email, não
			//preciso fazer nada aqui
		}
		
		oldUser.setEmail(u.getEmail());
		oldUser.setNome(u.getNome());
		oldUser.setTelefone(u.getTelefone());
		uRepositorio.update(oldUser);
	}
	
	public void delete(Integer id) throws UsuarioNotFoundException {
		//Verificando se o usuário existe
		load(id);
		//Se chegar aqui é porque existe
		uRepositorio.delete(id);
	}
	
	

}
