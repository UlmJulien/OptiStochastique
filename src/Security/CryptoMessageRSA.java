package Security; 

import java.math.BigInteger; 
import java.security.KeyPair; 
import java.security.KeyPairGenerator; 
import java.security.SecureRandom; 
import java.security.interfaces.RSAPrivateKey; 
import java.security.interfaces.RSAPublicKey; 

/* 
* Cette classe concerne le cryptage du password 
*/ 

public class CryptoMessageRSA { 
	
	/*--------------------------------------------------*\ 
	|*	 Constructeur	 *| 
	\*--------------------------------------------------*/	
	
	
	public CryptoMessageRSA(){ 
		/* 
		* Le constructeur va permettre de générer les clés privés et publiques 
		*/ 
		try { 
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA" ); 
			keyPairGen.initialize(TAILLE_CLEF, new SecureRandom(new String("toto").getBytes())); 
			pair = keyPairGen.generateKeyPair(); 
		} 
		catch (Exception e) {
			System.out.println(e);
		} 
		// récupération des clefs 
		clefPublique = (RSAPublicKey)pair.getPublic(); 
		clefPrivee = (RSAPrivateKey)pair.getPrivate(); 
	} 
	
	/*--------------------------------------------------*\ 
	|*	 Méthodes publics	 *| 
	\*--------------------------------------------------*/ 
	
	
	public String decrypterMessage(String messageCrypte) { 
		//On repasse le tableau d'octets en BigInteger pour pouvoir le décrypter 
		BigInteger msgADecrypteEnBigInt = new BigInteger(messageCrypte); 
		
		//on décrypte le message grace au RSA et a la clef privée 
		BigInteger msgDecrypteEnBigInt = msgADecrypteEnBigInt.modPow(clefPrivee.getPrivateExponent(), clefPrivee.getModulus()); 
		
		//on repasse le message en octets pour pouvoir retirer l'octet qu'on lui avait ajouté 
		byte[] msgDecrypteEnOctets = msgDecrypteEnBigInt.toByteArray(); 
		
		//on lui retire son octet en plus 
		byte[] resultat = new byte[msgDecrypteEnOctets.length-1]; 
		
		for (int i = 0; i < resultat.length; i++) { 
		resultat[i] = msgDecrypteEnOctets[i+1]; 
		} 
		
		//on retourne une chaine de charactères qui provient du tableau d'octets transformé 
		return new String (resultat); 
	} 
		
	public static String crypterMessage(String message, BigInteger exponent, BigInteger modulus) { 
		//	transformation de la chaine en tableau d'Octets 
		byte[] msgEnOctets = message.getBytes(); 
		
		//ajoute un octet codant le "1" au début du tableau passé en parametres 
		byte[] resultat = new byte[msgEnOctets.length+1]; 
		resultat[0] = 1; 
		for (int i = 0; i < msgEnOctets.length; i++) { 
		resultat[i+1] = msgEnOctets[i]; 
		} 
		
		BigInteger msgEnBigInteger = new BigInteger(resultat); 
		
		//crypte le message par la méthode RSA grace a la clef publique (cela renvoi un BigInteger) 
		BigInteger msgCrypte = msgEnBigInteger.modPow(exponent, modulus); 
		
		//on retourne le message crypté sous la forme d'un tableau d'octets 
		return msgCrypte.toString(); 
	} 
	
	public RSAPrivateKey getClePrive() 
	{ 
		return clefPrivee; 
	} 
	
	public RSAPublicKey getClePublic() 
	{ 
		return clefPublique; 
	} 
	
	/*--------------------------------------------------*\ 
	|*	 Attributs	*| 
	\*--------------------------------------------------*/	
	
	
	private KeyPair pair; 
	private RSAPublicKey clefPublique; 
	private static RSAPrivateKey clefPrivee; 
	private final static int TAILLE_CLEF = 1024; 
}