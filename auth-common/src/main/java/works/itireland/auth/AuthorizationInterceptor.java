package works.itireland.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;
import java.security.Key;
import java.util.Arrays;
import java.util.List;

import static works.itireland.auth.Constants.SECRET_KEY;

@AllArgsConstructor
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {


//    private final AuthClient authClient;


    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        if(!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        AuthorizedRoles annotation =
                method.getAnnotation(AuthorizedRoles.class);
        if(annotation != null) {
            final String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                throw new IllegalAccessException("You don't have proper authorization!");
            }
            final String token = authHeader.substring(7);
            Claims claims = extractAllClaims(token);
            List<String> roles = (List<String>) claims.get("roles");
            if(!hasCommonElement(roles, annotation.roles())) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                throw new IllegalAccessException("You don't have proper authorization!");
            }
        }
        return true;
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keBytes) ;
    }

    public boolean hasCommonElement(List<String> list, String[] array) {
        return list.stream().anyMatch(element1 -> Arrays.stream(array).anyMatch(element2 -> element2.equals(element1)));
    }
}
