package ebix.exceptions;

import ebix.models.Usuario;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioAlreadyExistsException extends Exception {
	private static final long serialVersionUID = 2171767182508550012L;
	private static final String ERRO_MSG = "Usuário já existe [%s]";

	private Usuario usuario;

	public UsuarioAlreadyExistsException() {
		// TODO Auto-generated constructor stub
	}

	public UsuarioAlreadyExistsException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public UsuarioAlreadyExistsException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public UsuarioAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public UsuarioAlreadyExistsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getMessage() {
		StringBuilder sb = new StringBuilder();
		if (usuario != null)
			return sb.append(String.format(ERRO_MSG, this.usuario.toString())).append("\n").toString();
		
		return super.getMessage();
	}

	@Override
	public String getLocalizedMessage() {
		if (usuario != null)
			return getMessage();
		return super.getLocalizedMessage();
	}

}
