package utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.web.client.RestTemplate;

import domain.User;

public class RESTClient {

	/***
	 * 
	 * M�todo que lee un Json de autenticaci�n y devuelve una lista con los
	 * username de todos los usuarios registrados en el sistema para que el
	 * admin del censo pueda a�adir usuarios.
	 * 
	 */
	public static Collection<String> getListUsernamesByJsonAutentication() {
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject("http://auth-egc.azurewebsites.net/api/getUsers", String.class);
		String[] lista = result.split(",");

		Collection<String> usernames = new ArrayList<String>();
		for (String field : lista) {
			if (field.contains("username")) {
				String[] aux_list = field.split(":");
				String username = aux_list[1];
				username = username.replaceAll("\"", "");
				usernames.add(username);
			}
		}

		return usernames;
	}

	public static void main(String[] args) throws IOException {
		Collection<String> usernames = getListUsernamesByJsonAutentication();
		for (String st : usernames) {
			System.out.println(st);
		}
	}

	/***
	 * 
	 * M�todo que lee nos devuelve el Json con la informaci�n de un usuario en
	 * concreto pas�ndole un username, el Json obtenido de autenticaci�n se
	 * leer� para formar un tipo User que ser� lo que se devuelva.
	 * 
	 */
	public static User getCertainUserByJsonAuthentication(String username) {
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject("http://auth-egc.azurewebsites.net/api/getUser?username=" + username,
				String.class);
		System.out.println(result);
		String[] lista = result.split(",");
		User user = new User();
		for (String field : lista) {
			if (field.contains("u_id")) {
				String[] aux_list = field.split(":");
				String u_id = aux_list[1];
				u_id = u_id.replaceAll("\"", "");
				System.out.println(u_id);
				int u_idConverted = Integer.parseInt(u_id);
				user.setU_id(u_idConverted);
			}
			if (field.contains("username")) {
				String[] aux_list = field.split(":");
				String usernameUser = aux_list[1];
				usernameUser = usernameUser.replaceAll("\"", "");
				user.setUsername(usernameUser);
			}
			if (field.contains("email")) {
				String[] aux_list = field.split(":");
				String email = aux_list[1];
				email = email.replaceAll("\"", "");
				user.setEmail(email);
			}
			if (field.contains("genre")) {
				String[] aux_list = field.split(":");
				String genre = aux_list[1];
				genre = genre.replaceAll("\"", "");
				user.setGenre(genre);
			}
			if (field.contains("autonomous_community")) {
				String[] aux_list = field.split(":");
				String autonomous_community = aux_list[1];
				autonomous_community = autonomous_community.replaceAll("\"", "");
				user.setAutonomous_community(autonomous_community);
			}
			if (field.contains("age")) {
				String[] aux_list = field.split(":");
				String age = aux_list[1];
				String[] age_list = age.split("}");
				// age = age.replaceAll("}", "");
				String finalAge = age_list[0];

				int ageConverted = Integer.parseInt(finalAge);
				user.setAge(ageConverted);
			}
		}
		System.out.println(user);
		return user;

	}
}
