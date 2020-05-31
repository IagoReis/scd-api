CREATE TABLE codigo_acesso (	
	id_convenio INTEGER NOT NULL,
	id_tomador INTEGER NOT NULL,
	codigo_acesso VARCHAR(10) NOT NULL,
	ip VARCHAR(20) DEFAULT NULL,
	destinatario VARCHAR(50) DEFAULT NULL,
	status VARCHAR(20) NOT NULL,
	dt_geracao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	dt_expiracao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(id_convenio, id_tomador, codigo_acesso)
);
