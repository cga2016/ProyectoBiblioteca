package models;

import java.util.ArrayList;



public class ListaUsuarios {
	
	
	 private static ArrayList<Usuario> usuariosRegistrados = new ArrayList<>();

	public static ArrayList<Usuario> getUsuariosRegistrados() {
		return usuariosRegistrados;
	}

	public static void setUsuariosRegistrados(ArrayList<Usuario> usuariosRegistrados) {
		ListaUsuarios.usuariosRegistrados = usuariosRegistrados;
	}
	
	public static void addUsuario(Usuario user) {
		usuariosRegistrados.add(user);
		
	}
	
	public static Usuario searchUsser(String correo) {
        for (Usuario usuario : usuariosRegistrados) {
            if (usuario.getCorreo().equalsIgnoreCase(correo)) { 
                return usuario;
            }
        } 
        return null;
		
	}
	
	public static void removeUsuario(Usuario user) {
		usuariosRegistrados.remove(user);
		
	}
	
	public static boolean checkUsuario(Usuario user) {
		
		for(Usuario usuario : usuariosRegistrados ) {
			if(usuario.equals(user)) {
				return true;
			} 
		}
		return false;
	} 
	
    public static boolean checkUsuarioEmail(String correo) {
        for (Usuario usuario : usuariosRegistrados) {
            if (usuario.getCorreo().equalsIgnoreCase(correo)) { 
                return true;
            }
        }
        return false;
    }
	 
}
