package ebix.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Usuario {
	
	private Integer id;
	private String nome;
	private String email;
	private String telefone;

}
