package ebix.endpoints.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import ebix.exceptions.EnderecoNotFoundException;
import ebix.models.Endereco;
import ebix.services.CorreiosService;
import lombok.Data;

@RestController
@RequestMapping("/api")
public class CorreioaRestEndpoint {

	@Autowired
	CorreiosService cService;

	@GetMapping(value = "/cep/{cep}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Endereco buscaCep(@PathVariable String cep) throws EnderecoNotFoundException {
		return cService.buscaCep(cep);
	}

	@ExceptionHandler({ EnderecoNotFoundException.class })
	public ResponseEntity<Object> handleEnderecoNotFoundException(EnderecoNotFoundException ex, WebRequest request) {
		ErroApi erro = new ErroApi();
		erro.setMsg(ex.getLocalizedMessage());
		erro.setStatus(HttpStatus.NOT_FOUND.toString());
		return new ResponseEntity<Object>(erro, new HttpHeaders(), HttpStatus.NOT_FOUND);
	}

}

@Data
class ErroApi {
	private String msg;
	private String status;
}
