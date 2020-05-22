DROP SEQUENCE IF EXISTS codigo_acesso_id_seq;

CREATE SEQUENCE codigo_acesso_id_seq;

DROP TABLE IF EXISTS codigo_acesso;

CREATE TABLE codigo_acesso (
	id INTEGER PRIMARY KEY DEFAULT NEXTVAL('codigo_acesso_id_seq'),
	id_convenio INTEGER NOT NULL,
	id_tomador INTEGER NOT NULL,
	codigo VARCHAR(10) NOT NULL,
	tp_situacao VARCHAR(20) NOT NULL,
	dt_geracao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	dt_expiracao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
