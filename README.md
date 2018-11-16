**Spring Annotation Meanings and Uses.**

`@RequestMapping`

**Meaning:**

**Use:**


`@PathVariable`

**Meaning:** Whatever variable this is assigned to, is the variable Spring
should be expecting in the URL path.

**Use:** `@RequestMapping(value = "/login/{email}")
      	public String returnLoginDetails(@PathVariable("email"))`


`@RestController`

**Meaning:**

**Use:**


`@Component`

**Meaning:** This annotation marks a java class as a bean so the component-scanning mechanism of spring can pick it up and
 pull it into the application context

**Use** `@Component
public class LoginService {}`


`@Service`

**Meaning:** Use @Service over @Component in service-layer classes because it specifies intent better. Doesn't add
additional functionality as of now.

**Use:** `@Service
         public class LoginService {}`


