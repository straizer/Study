package template;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/** Main class of app. */
@Slf4j
@UtilityClass
public class Main {

	/** App entrypoint. */
	public void main() {
		log.atInfo().addArgument("arg").log("Hello World");
		log.atInfo().addKeyValue("key", "value").log("Hello World");
		log.info("Hello World");
	}
}
