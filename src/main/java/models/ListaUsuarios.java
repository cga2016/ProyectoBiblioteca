package models;

import java.util.ArrayList;



public class ListaUsuarios {
	
	
	private static ArrayList<Usuario> usuariosRegistrados = new ArrayList<>();

	/**
	 * metodo para obtener la lista de usuarios registrados
	 * @return
	 */
	public static ArrayList<Usuario> getUsuariosRegistrados() {
		return usuariosRegistrados;
	}

	/**
	 * metodo para establecer la lista de los usuarios
	 * @param usuariosRegistrados
	 */
	public static void setUsuariosRegistrados(ArrayList<Usuario> usuariosRegistrados) {
		ListaUsuarios.usuariosRegistrados = usuariosRegistrados;
	}
	
	/**
	 * @param user
	 */
	public static void addUsuario(Usuario user) {
		usuariosRegistrados.add(user);
		
	}
	
	/**
	 * metodo para buscar un correo por usuario
	 * @param correo
	 * @return
	 */
	public static Usuario searchUsser(String correo) {
        for (Usuario usuario : usuariosRegistrados) {
            if (usuario.getCorreo().equalsIgnoreCase(correo)) { 
                return usuario;
            }
        } 
        return null;
		
	}
	
	/**
	 * metodo para quitar un usuario
	 * @param user
	 */
	public static void removeUsuario(Usuario user) {
		usuariosRegistrados.remove(user);
		
	}
	
	/**
	 * metodo para comprobar si existe un usuario con un usuario
	 * @param user
	 * @return
	 */
	public static boolean checkUsuario(Usuario user) {
		
		for(Usuario usuario : usuariosRegistrados ) {
			if(usuario.equals(user)) {
				return true;
			} 
		}
		return false;
	} 
	
	/**
	 * metodo para comprobar si un usuario existe por el correo
	 * @param correo
	 * @return
	 */
    public static boolean checkUsuarioEmail(String correo) {
        for (Usuario usuario : usuariosRegistrados) {
            if (usuario.getCorreo().equalsIgnoreCase(correo)) { 
                return true;
            }
        }
        return false;
    }
	 
}
