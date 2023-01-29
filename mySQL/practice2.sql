/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

DROP DATABASE IF EXISTS `ipodb_2008213514`;
CREATE DATABASE IF NOT EXISTS `ipodb_2008213514` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;
USE `ipodb_2008213514`;

DROP TABLE IF EXISTS `agent`;
CREATE TABLE IF NOT EXISTS `agent` (
  `agent_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `departman_id` int(10) unsigned NOT NULL,
  `korisnicko_ime` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `lozinka` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ime` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `prezime` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`agent_id`),
  UNIQUE KEY `uq_agent_korisnicko_ime` (`korisnicko_ime`),
  KEY `fk_agent_departman_id` (`departman_id`),
  CONSTRAINT `fk_agent_departman_id` FOREIGN KEY (`departman_id`) REFERENCES `departman` (`departman_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


DROP TABLE IF EXISTS `departman`;
CREATE TABLE IF NOT EXISTS `departman` (
  `departman_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ime` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `sifra` varchar(6) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`departman_id`),
  UNIQUE KEY `uq_departman_ime` (`ime`),
  UNIQUE KEY `uq_departman_sifra` (`sifra`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


DROP FUNCTION IF EXISTS `getSaleValue`;
DELIMITER //
CREATE FUNCTION `getSaleValue`(
	`arg_agent_id` INT,
	`arg_od_datuma` DATE,
	`arg_do_datuma` DATE
) RETURNS decimal(10,2)
    DETERMINISTIC
BEGIN
	RETURN (
		SELECT
			IFNULL(SUM(prodaja_roba.kolicina * roba.cena), 0)
		FROM agent
		INNER JOIN departman ON agent.departman_id = departman.departman_id
		LEFT JOIN prodaja ON agent.agent_id = prodaja.agent_id
		LEFT JOIN prodaja_roba ON prodaja.prodaja_id = prodaja_roba.prodaja_id
		LEFT JOIN roba ON prodaja_roba.roba_id = roba.roba_id
		WHERE
			prodaja.prodato_at BETWEEN arg_od_datuma AND arg_do_datuma
			AND agent.agent_id = arg_agent_id
	);
END//
DELIMITER ;

DROP TABLE IF EXISTS `prodaja`;
CREATE TABLE IF NOT EXISTS `prodaja` (
  `prodaja_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `agent_id` int(10) unsigned NOT NULL,
  `prodato_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `firma` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`prodaja_id`),
  KEY `fk_prodaja_agent_id` (`agent_id`),
  CONSTRAINT `fk_prodaja_agent_id` FOREIGN KEY (`agent_id`) REFERENCES `agent` (`agent_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


DROP TABLE IF EXISTS `prodaja_roba`;
CREATE TABLE IF NOT EXISTS `prodaja_roba` (
  `prodaja_roba_it` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `prodaja_id` int(10) unsigned NOT NULL,
  `roba_id` int(10) unsigned NOT NULL,
  `kolicina` decimal(10,2) unsigned NOT NULL,
  PRIMARY KEY (`prodaja_roba_it`),
  KEY `fk_prodaja_roba_prodaja_id` (`prodaja_id`),
  KEY `fk_prodaja_roba_roba_id` (`roba_id`),
  CONSTRAINT `fk_prodaja_roba_prodaja_id` FOREIGN KEY (`prodaja_id`) REFERENCES `prodaja` (`prodaja_id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_prodaja_roba_roba_id` FOREIGN KEY (`roba_id`) REFERENCES `roba` (`roba_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


DROP TABLE IF EXISTS `roba`;
CREATE TABLE IF NOT EXISTS `roba` (
  `roba_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `departman_id` int(10) unsigned NOT NULL,
  `naziv` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `opis` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `cena` decimal(10,2) unsigned NOT NULL,
  `sifra` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`roba_id`),
  UNIQUE KEY `uq_roba_sifra` (`sifra`),
  KEY `fk_roba_departman_id` (`departman_id`),
  CONSTRAINT `fk_roba_departman_id` FOREIGN KEY (`departman_id`) REFERENCES `departman` (`departman_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


DROP VIEW IF EXISTS `view__informacije_o_agentima`;
CREATE TABLE `view__informacije_o_agentima` (
	`agent_id` INT(10) UNSIGNED NULL,
	`departman_id` INT(10) UNSIGNED NULL,
	`korisnicko_ime` VARCHAR(64) NULL COLLATE 'utf8mb4_unicode_ci',
	`lozinka` VARCHAR(128) NULL COLLATE 'utf8mb4_unicode_ci',
	`ime` VARCHAR(32) NULL COLLATE 'utf8mb4_unicode_ci',
	`prezime` VARCHAR(32) NULL COLLATE 'utf8mb4_unicode_ci',
	`ime_departmana` VARCHAR(64) NULL COLLATE 'utf8mb4_unicode_ci',
	`broj_prodaja` BIGINT(21) NOT NULL,
	`ukupna_kolicinam` DECIMAL(32,2) NOT NULL,
	`ukupna_zarada` DECIMAL(42,4) NOT NULL
) ENGINE=MyISAM;

DROP TRIGGER IF EXISTS `trigger_departman_bi`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `trigger_departman_bi` BEFORE INSERT ON `departman` FOR EACH ROW BEGIN
	IF LENGTH(NEW.sifra) != 6 THEN
		SIGNAL SQLSTATE '50001' SET MESSAGE_TEXT = 'Sifra departmana mora imati tacno 6 karaktera!';
	END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

DROP TRIGGER IF EXISTS `trigger_prodaja_roba_bi`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `trigger_prodaja_roba_bi` BEFORE INSERT ON `prodaja_roba` FOR EACH ROW BEGIN
	DECLARE var_dep_robe INT;
	DECLARE var_dep_agenta INT;
	
	SET var_dep_robe = (
		SELECT roba.departman_id
		FROM roba
		WHERE roba.roba_id = NEW.roba_id
	);
	
	SET var_dep_agenta = (
		SELECT agent.departman_id
		FROM agent
		WHERE agent_id = (
			SELECT prodaja.agent_id
			FROM prodaja
			WHERE prodaja.prodaja_id = NEW.prodaja_id
		)
	);
	
	IF var_dep_robe != var_dep_agenta THEN
		SIGNAL SQLSTATE '50005' SET MESSAGE_TEXT = 'Agent moze da prodaje samo robu proizvdenu u njegovom deparmanu.';
	END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

DROP TRIGGER IF EXISTS `trigger_roba_bi`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `trigger_roba_bi` BEFORE INSERT ON `roba` FOR EACH ROW BEGIN
	DECLARE var_dep_sifra VARCHAR(6);
	DECLARE var_pro_sifra_first_6 VARCHAR(6);
	
	IF LENGTH(NEW.sifra) != 16 THEN
		SIGNAL SQLSTATE '50001' SET MESSAGE_TEXT = 'Sifra proizvoda mora imati tacno 16 karaktera.';	
	END IF;
	
	SET var_dep_sifra = (
		SELECT departman.sifra
		FROM departman
		WHERE departman.departman_id = NEW.departman_id
	);
	
	SET var_pro_sifra_first_6 = SUBSTR(NEW.sifra, 0, 6);
	
	IF var_dep_sifra != var_pro_sifra_first_6 THEN
		SIGNAL SQLSTATE '50002' SET MESSAGE_TEXT = 'Sifra proizvoda mora da pocinje isto kao sifra departmana kojem pripada.';
	END IF;
	
	IF NEW.sifra NOT RLIKE '[0-9A-Z]{10}$' THEN
		SIGNAL SQLSTATE '50003' SET MESSAGE_TEXT = 'Sifra proizvoda mora da se zavrsava sa 10 cifara ili slova od A do Z.';
	END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

DROP VIEW IF EXISTS `view__informacije_o_agentima`;
DROP TABLE IF EXISTS `view__informacije_o_agentima`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `view__informacije_o_agentima` AS SELECT
	agent.*,
	departman.ime AS ime_departmana,
	IFNULL(COUNT(prodaja.prodaja_id), 0) AS broj_prodaja,
	IFNULL(SUM(prodaja_roba.kolicina), 0) AS ukupna_kolicinam,
	IFNULL(SUM(prodaja_roba.kolicina * roba.cena), 0) AS ukupna_zarada
FROM agent
INNER JOIN departman ON agent.departman_id = departman.departman_id
LEFT JOIN prodaja ON agent.agent_id = prodaja.agent_id
LEFT JOIN prodaja_roba ON prodaja.prodaja_id = prodaja_roba.prodaja_id
LEFT JOIN roba ON prodaja_roba.roba_id = roba.roba_id ;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
