package ebix.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoNotFoundException extends Exception {
	private static final long serialVersionUID = 2171767182608550012L;
	private static final String ERRO_MSG = "Endereço não encontrado CEP [%s]";

	
	private String cep;

	public EnderecoNotFoundException() {
		// TODO Auto-generated constructor stub
	}

	public EnderecoNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public EnderecoNotFoundException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public EnderecoNotFoundException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public EnderecoNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getMessage() {
		StringBuilder sb = new StringBuilder();
		if (cep != null)
			return sb.append(String.format(ERRO_MSG, this.cep)).append("\n").toString();
		
		return super.getMessage();
	}

	@Override
	public String getLocalizedMessage() {
		if (cep != null )
			return getMessage();
		return super.getLocalizedMessage();
	}

}
