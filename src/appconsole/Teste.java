package appconsole;

import java.time.LocalDateTime;

public class Teste {

	public static void main(String[] args) {
		String agora = LocalDateTime.now().toString()
				.substring(0, 10);
		System.out.println(agora);

	}

}
