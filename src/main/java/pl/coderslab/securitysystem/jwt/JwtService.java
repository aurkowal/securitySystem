package pl.coderslab.securitysystem.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String SECRET_KEY = "IdVrZuBdCVQNzJ5TOSEZIXnLYzqnIbtIm2QXjcqzXYHFdcm+Qa3qVXTd2qJc2C4tGKWWj9lc2JlrqoOPHpUEtg==";
    private static final int EXPIRATION_TIME = 900000;

    //Metoda ta ekstrahuje nazwę użytkownika z tokena JWT. Nazwa użytkownika jest często przechowywana w polu subject tokena.
    public String extractUserName(String token) {
        return getClaim(token, Claims::getSubject);
    }

    //Generuje token JWT dla podanych szczegółów użytkownika. Metoda ta jest przeciążona, aby umożliwić dodawanie dodatkowych roszczeń do tokena.
    public String generateToken(UserDetails userDetails) {
        return generateToken1(new HashMap<>(), userDetails);
    }

    //Sprawdza, czy token jest ważny i nie wygasł, porównując nazwę użytkownika z tokena z nazwą użytkownika z UserDetails.
    public boolean isTokenValidAndNotExpired(String token, UserDetails userDetails) {
        String name = extractUserName(token);
        if (!name.equals(userDetails.getUsername())) {
            return false;
        }
        return !isTokenExpired(token);
    }

    //Sprawdza, czy token wygasł, porównując czas wygaśnięcia tokena z aktualnym czasem.
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    //Ekstrahuje czas wygaśnięcia z tokena JWT.
    private Date extractExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    public String generateToken1(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    //Metoda pomocnicza do ekstrakcji roszczeń z tokena JWT. Używa funkcji claimsResolver do określenia, które roszczenie ma być zwrócone.
    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractClaims(token);
        return claimsResolver.apply(claims);
    }

    //Ekstrahuje wszystkie roszczenia z tokena JWT.
    private Claims extractClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    //Generuje klucz używany do podpisania i weryfikacji tokenów JWT. Klucz jest dekodowany z formatu Base64 do bajtów, a następnie używany do utworzenia klucza HMAC SHA256.
    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
