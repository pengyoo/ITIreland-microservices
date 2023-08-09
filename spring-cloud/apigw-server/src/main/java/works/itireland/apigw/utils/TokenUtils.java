package works.itireland.apigw.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

public class TokenUtils {

    private static final String SECRET_KEY = "cap9oWOVv6er2gx2GtbuTV7Q58nh6Y8HOeL5amzsextjZyaGIhAFmUGPcpxOyDwalAA59D2dPcc2Fnc7MK0HHG//CBOXiNzcUB+e5QZtFtCEUWU2uQApsC9mVL7QXoLREYIuAXmGWvr48ZLSTWNB/DdaQi9hcTO5c2M0QRjjzilScDu/rlR41eAUl533VSbtDTcfAafaqtiiwKyHdRWmYNNyyVEt081OB5MJcKt7+11fJgHP1BMzvefqW6RRc/nXuys3FkSFwzGNGiYLGtFmAUWDD5gbbPk3PA5j7MBuQSNOF904XkK2JxauF1Gpv39l2acwd5R7b4nRKL5+tjnJjqeJDawuBB5uW2PCHdlR/uY=";
    public static boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private static Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public static <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private static Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private static Key getSignInKey() {
        byte[] keBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keBytes) ;
    }

}
