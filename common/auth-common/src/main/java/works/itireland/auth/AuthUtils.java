package works.itireland.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;

import java.util.function.Function;

public class AuthUtils {
    public static String getUserName(HttpServletRequest request){
        final String authHeader = request.getHeader("Authorization");
        final String token = authHeader.substring(7);
        return extractUsername(token);
    }

    public static String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public static <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private static Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(Constants.SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
