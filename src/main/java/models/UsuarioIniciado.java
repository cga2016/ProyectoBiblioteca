package models;

public class UsuarioIniciado {
	private static Usuario usuario;

	public static Usuario getUsuario() {
		return usuario;
	}

	public static void setUsuario(Usuario usuario) {
		UsuarioIniciado.usuario = usuario;
	}
	
	
}
