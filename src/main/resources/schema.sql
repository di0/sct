-- Para nosso token de seguranca.

DROP TABLE IF EXISTS TIVIATI_SECURITY;

CREATE TABLE TIVIATI_SECURITY(
  id INT auto_increment NOT NULL,
  akey VARCHAR(250) NOT NULL,
  token VARCHAR(250) NOT NULL,
  ultima_alteracao TIMESTAMP
  -- UNIQUE KEY TTS_APP_KEY_UNIQUE (app_key)
);

INSERT INTO TIVIATI_SECURITY (akey, token, ultima_alteracao) VALUES
  ('X-API-KEY', 'tiviatisct', current_timestamp);
