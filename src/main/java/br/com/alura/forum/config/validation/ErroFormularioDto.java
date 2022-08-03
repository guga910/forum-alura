package br.com.alura.forum.config.validation;

public class ErroFormularioDto {// essa classe vai representar o erro, a mensagem de erro personalizada
	
	private String campo; // campo do erro
	private String erro; // qual erro foi gerado
	public ErroFormularioDto(String campo, String erro) {
		super();
		this.campo = campo;
		this.erro = erro;
	}
	public ErroFormularioDto() {
		// TODO Auto-generated constructor stub
	}
	public String getCampo() {
		return campo;
	}
	public String getErro() {
		return erro;
	}
	

}
