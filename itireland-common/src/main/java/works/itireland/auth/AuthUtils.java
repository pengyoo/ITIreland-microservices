package works.itireland.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.List;
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

    public static Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(Constants.SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static boolean hasCommonElement(List<String> list, String[] array) {
        return list.stream().anyMatch(element1 -> Arrays.stream(array).anyMatch(element2 -> element2.equals(element1)));
    }
}
