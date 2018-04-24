(ns mission-board.auth
  (:import (java.security.interfaces RSAPrivateKey RSAPublicKey)
           (com.auth0.jwt.algorithms Algorithm)
           (com.auth0.jwt JWT)
           (java.security KeyFactory)
           (java.security.spec PKCS8EncodedKeySpec X509EncodedKeySpec)
           (java.util Base64)))

(def key-factory (KeyFactory/getInstance "RSA"))
(def ^RSAPublicKey public-key  (.generatePublic key-factory (X509EncodedKeySpec. (.decode (Base64/getDecoder) "4tpn3VEn16Lph6XiBl98W2LaWbHVWTJ9"))))
(def ^RSAPrivateKey private-key  (.generatePrivate key-factory (PKCS8EncodedKeySpec. (.getBytes "wlX5ZUxnYP1DgdAhq4KYH-gdFsmFM2JE8MOTayfNG3wLOLectCOBGVzjWplxI_1m"))))
(def ^Algorithm algorithm (Algorithm/RSA256 public-key private-key))
(def ^JWTVerifier verifier (-> (JWT/require algorithm)
                               (.withIssuer "auth0")
                               (.build)))

(defn unsign [token]
  (try
    (.verify verifier token)
    (catch (JWTVerificationException e)
      (prn e))))
