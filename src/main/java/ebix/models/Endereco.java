package ebix.models;

import lombok.Data;

@Data
public class Endereco {
  private String logradouro;
  private String complemento2;
  private String bairro;
  private String cidade;
  private String uf;
  private String cep;
}
