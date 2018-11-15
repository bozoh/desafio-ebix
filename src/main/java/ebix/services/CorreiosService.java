package ebix.services;

import javax.xml.bind.JAXBIntrospector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import ebix.exceptions.EnderecoNotFoundException;
import ebix.models.Endereco;
import ebix.ws.domain.correios.ConsultaCEP;
import ebix.ws.domain.correios.ConsultaCEPResponse;
import ebix.ws.domain.correios.EnderecoERP;
import ebix.ws.domain.correios.ObjectFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CorreiosService extends WebServiceGatewaySupport {

	@Autowired
	public CorreiosService(Jaxb2Marshaller marshaller, @Value("${correios.sigepweb.uri}") String correiosUri) {
		this.setMarshaller(marshaller);
		this.setUnmarshaller(marshaller);
		this.setDefaultUri(correiosUri);
	}

	public Endereco buscaCep(String cep) throws EnderecoNotFoundException {
		ObjectFactory correiosFactory = new ObjectFactory();
		ConsultaCEP req = correiosFactory.createConsultaCEP();
		req.setCep(cep);
		log.debug("Buscando CEP " + cep);

		try {
			ConsultaCEPResponse response = (ConsultaCEPResponse) JAXBIntrospector
					.getValue(getWebServiceTemplate().marshalSendAndReceive(correiosFactory.createConsultaCEP(req)));
			return this.toEndereco(response.getReturn());
		} catch (org.springframework.ws.soap.client.SoapFaultClientException e) {
			EnderecoNotFoundException ex = new EnderecoNotFoundException();
			ex.setCep(cep);
			log.error(ex.getLocalizedMessage());
			throw ex;
		}

	}

	private Endereco toEndereco(EnderecoERP end) {
		Endereco e = new Endereco();
		e.setLogradouro(end.getEnd());
		e.setComplemento2(end.getComplemento2());
		e.setBairro(end.getBairro());
		e.setCidade(end.getCidade());
		e.setUf(end.getUf());
		e.setCep(end.getCep());
		return e;
	}
}
