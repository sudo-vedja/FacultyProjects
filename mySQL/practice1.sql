/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

DROP DATABASE IF EXISTS `imtdb_2008213514`;
CREATE DATABASE IF NOT EXISTS `imtdb_2008213514` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;
USE `imtdb_2008213514`;

DROP TABLE IF EXISTS `angazovanje`;
CREATE TABLE IF NOT EXISTS `angazovanje` (
  `angazovanje_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `projekat_id` int(10) unsigned NOT NULL,
  `zaposleni_id` int(10) unsigned NOT NULL,
  `angazovan_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`angazovanje_id`),
  UNIQUE KEY `uq_angazovanje_projekat_id_zaposleni_id` (`projekat_id`,`zaposleni_id`),
  KEY `fk_angazovanje_zaposleni_id` (`zaposleni_id`),
  CONSTRAINT `fk_angazovanje_projekat_id` FOREIGN KEY (`projekat_id`) REFERENCES `projekat` (`projekat_id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_angazovanje_zaposleni_id` FOREIGN KEY (`zaposleni_id`) REFERENCES `zaposleni` (`zaposleni_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


DROP FUNCTION IF EXISTS `getEarnings`;
DELIMITER //
CREATE FUNCTION `getEarnings`(
	`arg_korisicko_ime_zaposlenog` VARCHAR(64),
	`arg_oznaka_projekta` VARCHAR(13)
) RETURNS decimal(10,2)
BEGIN
	DECLARE var_zap_id INT;
	DECLARE var_pro_id INT;
	DECLARE var_pro_cena DECIMAL(10,2);
	
	SET var_zap_id = (
		SELECT zaposleni.zaposleni_id
		FROM zaposleni
		WHERE zaposleni.korisnicko_ime = arg_korisicko_ime_zaposlenog
	);
	
	SET var_pro_id = (
		SELECT projekat.projekat_id
		FROM projekat
		WHERE projekat.oznaka = arg_oznaka_projekta
	);
	
	SET var_pro_cena = (
		SELECT projekat.cena_sata
		FROM projekat
		WHERE projekat.oznaka = arg_oznaka_projekta
	);
	
	RETURN IFNULL(
		var_pro_cena * (
		SELECT
			SUM(TIME_TO_SEC(TIMEDIFF(posao.zavrsen_at, posao.posao_id))) / 3600
		FROM
			posao
		WHERE
			posao.projekat_id = var_pro_id
			AND posao.zaposleni_id = var_zap_id
	), 0);
END//
DELIMITER ;

DROP TABLE IF EXISTS `posao`;
CREATE TABLE IF NOT EXISTS `posao` (
  `posao_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `projekat_id` int(10) unsigned NOT NULL,
  `zaposleni_id` int(10) unsigned NOT NULL,
  `naziv` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `opis` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `poceo_at` datetime NOT NULL,
  `zavrsen_at` datetime NOT NULL,
  PRIMARY KEY (`posao_id`),
  KEY `fk_posao_zaposleni_id` (`zaposleni_id`),
  KEY `fk_posao_projekat_id` (`projekat_id`),
  CONSTRAINT `fk_posao_projekat_id` FOREIGN KEY (`projekat_id`) REFERENCES `projekat` (`projekat_id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_posao_zaposleni_id` FOREIGN KEY (`zaposleni_id`) REFERENCES `zaposleni` (`zaposleni_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


DROP TABLE IF EXISTS `projekat`;
CREATE TABLE IF NOT EXISTS `projekat` (
  `projekat_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `naziv` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `opis` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `oznaka` varchar(13) COLLATE utf8mb4_unicode_ci NOT NULL,
  `cena_sata` decimal(10,2) unsigned NOT NULL,
  `pocinje_at` datetime NOT NULL,
  `zavrsava_se_at` datetime NOT NULL,
  PRIMARY KEY (`projekat_id`),
  UNIQUE KEY `uq_projekat_oznaka` (`oznaka`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


DROP VIEW IF EXISTS `view__detalji_o_aktivnim_projektima`;
CREATE TABLE `view__detalji_o_aktivnim_projektima` (
	`projekat_id` INT(10) UNSIGNED NULL,
	`naziv` VARCHAR(64) NULL COLLATE 'utf8mb4_unicode_ci',
	`opis` TEXT NULL COLLATE 'utf8mb4_unicode_ci',
	`oznaka` VARCHAR(13) NULL COLLATE 'utf8mb4_unicode_ci',
	`cena_sata` DECIMAL(10,2) UNSIGNED NULL,
	`pocinje_at` DATETIME NULL,
	`zavrsava_se_at` DATETIME NULL,
	`broj_zaposlenih` BIGINT(21) NOT NULL,
	`suma_vremena_na_projektu_h` DECIMAL(42,4) NULL
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `zaposleni`;
CREATE TABLE IF NOT EXISTS `zaposleni` (
  `zaposleni_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `korisnicko_ime` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `lozinka` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL,
  `is_aktivan` tinyint(1) unsigned NOT NULL DEFAULT '1',
  PRIMARY KEY (`zaposleni_id`),
  UNIQUE KEY `uq_zaposleni_korisnicko_ime` (`korisnicko_ime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


DROP TRIGGER IF EXISTS `trigger_angazovanje_bi`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `trigger_angazovanje_bi` BEFORE INSERT ON `angazovanje` FOR EACH ROW BEGIN
	DECLARE var_kraj_projekta DATETIME;
	
	SET var_kraj_projekta = (
		SELECT projekat.zavrsava_se_at
		FROM projekat
		WHERE projekat.projekat_id = NEW.projekat_id
	);
	
	IF var_kraj_projekta < NEW.angazovan_at THEN
		SIGNAL SQLSTATE '50003' SET MESSAGE_TEXT = 'Zaposleni ne moze da bude angazovan nakon zavrsetka projekta!';
	END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

DROP TRIGGER IF EXISTS `trigger_posao_bi`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `trigger_posao_bi` BEFORE INSERT ON `posao` FOR EACH ROW BEGIN
	DECLARE var_kraj_projekta DATETIME;
	
	SET var_kraj_projekta = (
		SELECT projekat.zavrsava_se_at
		FROM projekat
		WHERE projekat.projekat_id = NEW.projekat_id
	);
	
	IF var_kraj_projekta < NEW.zavrsen_at THEN
		SIGNAL SQLSTATE '50004' SET MESSAGE_TEXT = 'Posao ne moze da se zavrsi nakon sto je projekat zavrsen!';
	END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

DROP TRIGGER IF EXISTS `trigger_projekat_bi`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `trigger_projekat_bi` BEFORE INSERT ON `projekat` FOR EACH ROW BEGIN
	IF NEW.oznaka NOT RLIKE '^[A-Z]{3}[0-9]{9}[A-Z]{2}$' THEN
		SIGNAL SQLSTATE '50001' SET MESSAGE_TEXT = 'Losa oznaka projekta!';
	END IF;
	
	IF TIME_TO_SEC(TIMEDIFF(NEW.zavrsava_se_at, NEW.pocinje_at)) < 2 * 24 * 60 * 60 THEN
		SIGNAL SQLSTATE '50002' SET MESSAGE_TEXT = 'Projekat ne moze trajati krace od 2 dana!';
	END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

DROP VIEW IF EXISTS `view__detalji_o_aktivnim_projektima`;
DROP TABLE IF EXISTS `view__detalji_o_aktivnim_projektima`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `view__detalji_o_aktivnim_projektima` AS SELECT
	projekat.*,
	IFNULL(COUNT(angazovanje.zaposleni_id), 0) AS broj_zaposlenih,
	IFNULL(SUM(TIME_TO_SEC(TIMEDIFF(posao.zavrsen_at, posao.poceo_at))), 0) / 3600 AS suma_vremena_na_projektu_h
FROM
	projekat
LEFT JOIN angazovanje ON projekat.projekat_id = angazovanje.projekat_id
LEFT JOIN posao ON projekat.projekat_id = posao.projekat_id
WHERE
	NOW() BETWEEN projekat.pocinje_at AND projekat.zavrsava_se_at
ORDER BY
	projekat.zavrsava_se_at ASC ;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
