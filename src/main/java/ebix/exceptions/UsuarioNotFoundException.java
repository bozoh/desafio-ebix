package ebix.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioNotFoundException extends Exception {
	private static final long serialVersionUID = 2171767182608550012L;
	private static final String ERRO_MSG = "Usuário não encontrado %s [%s]";

	private Integer id;
	private String email;

	public UsuarioNotFoundException() {
		// TODO Auto-generated constructor stub
	}

	public UsuarioNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public UsuarioNotFoundException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public UsuarioNotFoundException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public UsuarioNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getMessage() {
		StringBuilder sb = new StringBuilder();
		if (id != null)
			return sb.append(String.format(ERRO_MSG, "ID", String.valueOf(this.id))).append("\n").toString();
		if (email != null)
			return sb.append(String.format(ERRO_MSG, "E-MAIL", this.email)).append("\n").toString();
		return super.getMessage();
	}

	@Override
	public String getLocalizedMessage() {
		if (id != null || email != null)
			return getMessage();
		return super.getLocalizedMessage();
	}

}
