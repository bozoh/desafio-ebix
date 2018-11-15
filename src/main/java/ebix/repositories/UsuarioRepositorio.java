package ebix.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ebix.exceptions.UsuarioNotFoundException;
import ebix.models.Usuario;

@Repository
public class UsuarioRepositorio {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional(readOnly = true)
	public Usuario findUsuarioById(int id) {
		try {
			return jdbcTemplate.queryForObject("SELECT * FROM usuarios WHERE id=?", new Object[] { id },
					new UsuarioRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Transactional(readOnly = true)
	public Usuario findUsuarioByEmail(String email) {
		try {
			return jdbcTemplate.queryForObject("SELECT * FROM usuarios WHERE upper(email)=upper(?)",
					new Object[] { email }, new UsuarioRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}

	@Transactional(readOnly = true)
	public List<Usuario> findAll() {
		return jdbcTemplate.query("SELECT * FROM usuarios", new UsuarioRowMapper());
	}

	@Transactional(readOnly = true)
	public List<Usuario> findAllByNome(String nome) {
		final String sql = "SELECT * FROM usuarios WHERE upper(nome) like ?";
		Object[] params = new Object[] {
				String.format("%%%s%%", nome.toUpperCase())
		};
		return jdbcTemplate.query(sql, new UsuarioRowMapper(), params);

	}

	@Transactional(readOnly = true)
	public List<Usuario> findAllByEmail(String email) {
		final String sql = "SELECT * FROM usuarios WHERE upper(email) like ?";
		
		Object[] params = new Object[] {
				String.format("%%%s%%", email.toUpperCase())
		};
		
		return jdbcTemplate.query(sql, new UsuarioRowMapper(), params);

	}

	public Usuario create(final Usuario usuario) {
		final String sql = "INSERT INTO usuarios (nome, email, telefone) VALUES (?, ?, ?)";
		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, usuario.getNome());
				ps.setString(2, usuario.getEmail());
				ps.setString(3, usuario.getTelefone());
				return ps;
			}
		}, holder);

		int newUsuarioId = holder.getKey().intValue();
		usuario.setId(newUsuarioId);
		return usuario;
	}

	public void update(final Usuario u) {
		jdbcTemplate.update("UPDATE usuarios SET nome=?, email=?, telefone=? WHERE id=?",
				new Object[] { u.getNome(), u.getEmail(), u.getTelefone(), u.getId() });
	}

	public void delete(final Integer id) throws UsuarioNotFoundException {
		// Verifica se o usuário já existe
		this.findUsuarioById(id);
		// Se chegar até aqui é porque o usuario existe
		jdbcTemplate.update("DELETE FROM usuarios WHERE id=?", new Object[] { id });
	}

}

class UsuarioRowMapper implements RowMapper<Usuario> {

	@Override
	public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Usuario(rs.getInt("id"), rs.getString("nome"), rs.getString("email"), rs.getString("telefone"));
	}

}
