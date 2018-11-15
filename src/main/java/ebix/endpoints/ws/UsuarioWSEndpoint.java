package ebix.endpoints.ws;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import ebix.exceptions.UsuarioAlreadyExistsException;
import ebix.exceptions.UsuarioNotFoundException;
import ebix.models.Usuario;
import ebix.services.UsuarioService;
import ebix.ws.domain.CreateUsuarioRequest;
import ebix.ws.domain.CreateUsuarioResponse;
import ebix.ws.domain.DeleteUsuarioRequest;
import ebix.ws.domain.DeleteUsuarioResponse;
import ebix.ws.domain.GetAllUsuariosByEmailRequest;
import ebix.ws.domain.GetAllUsuariosByEmailResponse;
import ebix.ws.domain.GetAllUsuariosByNomeRequest;
import ebix.ws.domain.GetAllUsuariosByNomeResponse;
import ebix.ws.domain.GetAllUsuariosResponse;
import ebix.ws.domain.GetUsuarioByEmailRequest;
import ebix.ws.domain.GetUsuarioByEmailResponse;
import ebix.ws.domain.GetUsuarioByIdRequest;
import ebix.ws.domain.GetUsuarioByIdResponse;
import ebix.ws.domain.ServiceStatus;
import ebix.ws.domain.UpdateUsuarioRequest;
import ebix.ws.domain.UpdateUsuarioResponse;
import ebix.ws.domain.UsuarioInfo;

@Endpoint
public class UsuarioWSEndpoint {

	private static final String NAMESPACE_URI = "http://desafio.ebix/usuario-ws";
	private static final String STATUS_SUCCESS = "SUCCESS";
	private static final String STATUS_NOT_FOUND = "NOT FOUND";
	private static final String STATUS_CONFLICT = "CONFLICT";
	private static final String STATUS_FAIL = "FAIL";

	@Autowired
	private UsuarioService uService;

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUsuarioByIdRequest")
	@ResponsePayload
	public GetUsuarioByIdResponse getUsuarioPorId(@RequestPayload GetUsuarioByIdRequest request) {
		GetUsuarioByIdResponse resp = new GetUsuarioByIdResponse();
		ServiceStatus serviceStatus = new ServiceStatus();
		try {
			Usuario u = uService.load(request.getUsuarioId());
			serviceStatus.setStatusCode(STATUS_SUCCESS);
			resp.setUsuarioInfo(toUsuarioInfo(u));

		} catch (UsuarioNotFoundException e) {
			serviceStatus.setStatusCode(STATUS_NOT_FOUND);
			serviceStatus.setMessage(e.getLocalizedMessage());
		} catch (Exception e) {
			serviceStatus.setStatusCode(STATUS_FAIL);
			serviceStatus.setMessage(e.getLocalizedMessage());
		}
		resp.setServiceStatus(serviceStatus);
		return resp;

	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUsuarioByEmailRequest")
	@ResponsePayload
	public GetUsuarioByEmailResponse getUsuarioPorEmail(@RequestPayload GetUsuarioByEmailRequest request) {
		GetUsuarioByEmailResponse resp = new GetUsuarioByEmailResponse();
		ServiceStatus serviceStatus = new ServiceStatus();
		try {
			Usuario u = uService.loadByEmail(request.getUsuarioEmail());
			serviceStatus.setStatusCode(STATUS_SUCCESS);
			resp.setUsuarioInfo(toUsuarioInfo(u));
		} catch (UsuarioNotFoundException e) {
			serviceStatus.setStatusCode(STATUS_NOT_FOUND);
			serviceStatus.setMessage(e.getLocalizedMessage());
		} catch (Exception e) {
			serviceStatus.setStatusCode(STATUS_FAIL);
			serviceStatus.setMessage(e.getLocalizedMessage());
		}
		resp.setServiceStatus(serviceStatus);
		return resp;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllUsuariosRequest")
	@ResponsePayload
	public GetAllUsuariosResponse getAllUsuarios() {
		GetAllUsuariosResponse resp = new GetAllUsuariosResponse();
		resp.getUsuarioInfo()
				.addAll(uService.loadAll().stream().map(u -> toUsuarioInfo(u)).collect(Collectors.toList()));
		return resp;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllUsuariosByNomeRequest")
	@ResponsePayload
	public GetAllUsuariosByNomeResponse getAllUsuariosByNome(@RequestPayload GetAllUsuariosByNomeRequest request) {
		GetAllUsuariosByNomeResponse resp = new GetAllUsuariosByNomeResponse();
		resp.getUsuarioInfo().addAll(uService.loadAllByNome(request.getUsuarioNome()).stream()
				.map(u -> toUsuarioInfo(u)).collect(Collectors.toList()));
		return resp;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllUsuariosByEmailRequest")
	@ResponsePayload
	public GetAllUsuariosByEmailResponse getAllUsuariosByEmail(@RequestPayload GetAllUsuariosByEmailRequest request) {
		GetAllUsuariosByEmailResponse resp = new GetAllUsuariosByEmailResponse();
		resp.getUsuarioInfo().addAll(uService.loadAllByEmail(request.getUsuarioEmail()).stream()
				.map(u -> toUsuarioInfo(u)).collect(Collectors.toList()));
		return resp;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "createUsuarioRequest")
	@ResponsePayload
	public CreateUsuarioResponse createUsuario(@RequestPayload CreateUsuarioRequest request) {
		CreateUsuarioResponse resp = new CreateUsuarioResponse();
		ServiceStatus serviceStatus = new ServiceStatus();
		try {
			Usuario u = uService.create(toUsuario(request.getUsuarioInfo()));
			resp.setUsuarioInfo(toUsuarioInfo(u));
			serviceStatus.setStatusCode(STATUS_SUCCESS);
		} catch (UsuarioAlreadyExistsException e) {
			serviceStatus.setStatusCode(STATUS_CONFLICT);
			serviceStatus.setMessage(e.getLocalizedMessage());
		} catch (Exception e) {
			serviceStatus.setStatusCode(STATUS_FAIL);
			serviceStatus.setMessage(e.getLocalizedMessage());
		}
		resp.setServiceStatus(serviceStatus);
		return resp;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateUsuarioRequest")
	@ResponsePayload
	public UpdateUsuarioResponse updateUsuario(@RequestPayload UpdateUsuarioRequest request) {
		UpdateUsuarioResponse resp = new UpdateUsuarioResponse();
		ServiceStatus serviceStatus = new ServiceStatus();
		try {
			uService.update(request.getUsuarioId(), toUsuario(request.getUsuarioInfo()));
			serviceStatus.setStatusCode(STATUS_SUCCESS);
		} catch (UsuarioNotFoundException e) {
			serviceStatus.setStatusCode(STATUS_NOT_FOUND);
			serviceStatus.setMessage(e.getLocalizedMessage());
		} catch (UsuarioAlreadyExistsException e) {
			serviceStatus.setStatusCode(STATUS_CONFLICT);
			serviceStatus.setMessage(e.getLocalizedMessage());
		} catch (Exception e) {
			serviceStatus.setStatusCode(STATUS_FAIL);
			serviceStatus.setMessage(e.getLocalizedMessage());
		}
		
		resp.setServiceStatus(serviceStatus);
		return resp;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteUsuarioRequest")
	@ResponsePayload
	public DeleteUsuarioResponse deleteUsuario(@RequestPayload DeleteUsuarioRequest request) {
		DeleteUsuarioResponse resp = new DeleteUsuarioResponse();
		ServiceStatus serviceStatus = new ServiceStatus();
		try {
			uService.delete(request.getUsuarioId());
			serviceStatus.setStatusCode(STATUS_SUCCESS);
		} catch (UsuarioNotFoundException e) {
			serviceStatus.setStatusCode(STATUS_NOT_FOUND);
			serviceStatus.setMessage(e.getLocalizedMessage());
		} catch (Exception e) {
			serviceStatus.setStatusCode(STATUS_FAIL);
			serviceStatus.setMessage(e.getLocalizedMessage());
		}

		
		resp.setServiceStatus(serviceStatus);
		return resp;
	}

	private UsuarioInfo toUsuarioInfo(Usuario u) {
		UsuarioInfo ui = new UsuarioInfo();
		ui.setId(u.getId());
		ui.setNome(u.getNome());
		ui.setEmail(u.getEmail());
		ui.setTelefone(u.getTelefone());
		return ui;
	}

	private Usuario toUsuario(UsuarioInfo ui) {
		Usuario u = new Usuario();
		u.setId(ui.getId());
		u.setNome(ui.getNome());
		u.setEmail(ui.getEmail());
		u.setTelefone(ui.getTelefone());
		return u;
	}


}
