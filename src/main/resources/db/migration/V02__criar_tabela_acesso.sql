CREATE TABLE acesso (
	id_convenio INTEGER NOT NULL,
	id_tomador INTEGER NOT NULL,
	codigo_acesso VARCHAR(10) NOT NULL,
	dt_inicio TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	ip_inicio VARCHAR(20) DEFAULT NULL,
	dt_termino TIMESTAMP DEFAULT NULL,
	ip_termino VARCHAR(20) DEFAULT NULL,
	PRIMARY KEY(id_convenio, id_tomador, codigo_acesso)
);
