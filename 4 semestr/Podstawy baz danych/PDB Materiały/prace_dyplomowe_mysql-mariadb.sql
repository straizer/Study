/*
 Navicat Premium Data Transfer

 Source Server         : root2 mariadb
 Source Server Type    : MariaDB
 Source Server Version : 100505
 Source Host           : localhost:3306
 Source Schema         : nowa baza

 Target Server Type    : MariaDB
 Target Server Version : 100505
 File Encoding         : 65001

 Date: 06/04/2021 22:40:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for kierunki
-- ----------------------------
DROP TABLE IF EXISTS `kierunki`;
CREATE TABLE `kierunki`  (
  `ID_kierunki` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `nazwa_kierunku` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ID_wydzial` int(10) UNSIGNED NULL DEFAULT NULL,
  `ID_stopien_studiow` int(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`ID_kierunki`) USING BTREE,
  INDEX `ID_stopien_studiow`(`ID_stopien_studiow`) USING BTREE,
  UNIQUE INDEX `nazwa_kierunku`(`nazwa_kierunku`, `ID_wydzial`, `ID_stopien_studiow`) USING BTREE,
  INDEX `ID_wydzial`(`ID_wydzial`) USING BTREE,
  CONSTRAINT `kierunki_ibfk_1` FOREIGN KEY (`ID_stopien_studiow`) REFERENCES `stopien_studiow` (`ID_stopien_studiow`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `kierunki_ibfk_2` FOREIGN KEY (`ID_wydzial`) REFERENCES `wydzialy` (`ID_wydzial`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of kierunki
-- ----------------------------

-- ----------------------------
-- Table structure for prace_dyplomowe
-- ----------------------------
DROP TABLE IF EXISTS `prace_dyplomowe`;
CREATE TABLE `prace_dyplomowe`  (
  `ID_prace_dyplomowe` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `tytul_pracy` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ID_promotor` int(10) UNSIGNED NOT NULL,
  `ID_recenzent` int(10) UNSIGNED NULL DEFAULT NULL,
  `ID_student` int(10) UNSIGNED NULL DEFAULT NULL,
  `ID_autor` int(10) UNSIGNED NOT NULL COMMENT 'Zamieścił',
  `ocena` decimal(2, 1) NULL DEFAULT NULL,
  `data_oceny` date NULL DEFAULT NULL,
  `ts_prace_dyplomowe` timestamp NULL DEFAULT current_timestamp() ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID_prace_dyplomowe`) USING BTREE,
  INDEX `ID_promotor`(`ID_promotor`) USING BTREE,
  INDEX `ID_recenzent`(`ID_recenzent`) USING BTREE,
  INDEX `ID_student`(`ID_student`) USING BTREE,
  INDEX `ID_autor`(`ID_autor`) USING BTREE,
  CONSTRAINT `prace_dyplomowe_ibfk_1` FOREIGN KEY (`ID_promotor`) REFERENCES `uzytkownicy` (`ID_uzytkownicy`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `prace_dyplomowe_ibfk_2` FOREIGN KEY (`ID_recenzent`) REFERENCES `uzytkownicy` (`ID_uzytkownicy`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `prace_dyplomowe_ibfk_3` FOREIGN KEY (`ID_student`) REFERENCES `uzytkownicy` (`ID_uzytkownicy`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `prace_dyplomowe_ibfk_4` FOREIGN KEY (`ID_autor`) REFERENCES `uzytkownicy` (`ID_uzytkownicy`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of prace_dyplomowe
-- ----------------------------
INSERT INTO `prace_dyplomowe` VALUES (1, 'Problem redundancji danych w bazach danych', 1, 2, 3, 1, 4.5, '2021-03-02', '2021-04-05 23:08:48');
INSERT INTO `prace_dyplomowe` VALUES (2, 'Replikacja danych w MariaDB', 2, 1, 4, 1, 5.0, '2021-02-02', '2021-04-05 23:16:32');
INSERT INTO `prace_dyplomowe` VALUES (3, 'Optymalizacja w aspekcie partycjonowania wersjonowanych tabel', 1, NULL, NULL, 1, NULL, NULL, '2021-04-05 23:21:42');
INSERT INTO `prace_dyplomowe` VALUES (4, 'Praktyczne zastosowania rekurencji w zapytaniach SQL', 2, 1, NULL, 1, NULL, NULL, '2021-04-05 23:23:44');

-- ----------------------------
-- Table structure for prace_dyplomowe_specjalnosci
-- ----------------------------
DROP TABLE IF EXISTS `prace_dyplomowe_specjalnosci`;
CREATE TABLE `prace_dyplomowe_specjalnosci`  (
  `ID_prace_dyplomowe` int(10) UNSIGNED NOT NULL,
  `ID_specjalnosci` int(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`ID_prace_dyplomowe`, `ID_specjalnosci`) USING BTREE,
  INDEX `ID_specjalnosci`(`ID_specjalnosci`) USING BTREE,
  CONSTRAINT `prace_dyplomowe_specjalnosci_ibfk_1` FOREIGN KEY (`ID_prace_dyplomowe`) REFERENCES `prace_dyplomowe` (`ID_prace_dyplomowe`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `prace_dyplomowe_specjalnosci_ibfk_2` FOREIGN KEY (`ID_specjalnosci`) REFERENCES `specjalnosci` (`ID_specjalnosci`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of prace_dyplomowe_specjalnosci
-- ----------------------------

-- ----------------------------
-- Table structure for specjalnosci
-- ----------------------------
DROP TABLE IF EXISTS `specjalnosci`;
CREATE TABLE `specjalnosci`  (
  `ID_specjalnosci` int(10) UNSIGNED NOT NULL,
  `nazwa_specjalnosci` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ID_kierunki` int(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`ID_specjalnosci`) USING BTREE,
  INDEX `ID_kierunki`(`ID_kierunki`) USING BTREE,
  UNIQUE INDEX `nazwa_specjalnosci`(`nazwa_specjalnosci`, `ID_kierunki`) USING BTREE,
  CONSTRAINT `specjalnosci_ibfk_1` FOREIGN KEY (`ID_kierunki`) REFERENCES `kierunki` (`ID_kierunki`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of specjalnosci
-- ----------------------------

-- ----------------------------
-- Table structure for stopien_studiow
-- ----------------------------
DROP TABLE IF EXISTS `stopien_studiow`;
CREATE TABLE `stopien_studiow`  (
  `ID_stopien_studiow` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `nazwa_stopnia_studiow` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID_stopien_studiow`) USING BTREE,
  UNIQUE INDEX `nazwa_stopnia_studiow`(`nazwa_stopnia_studiow`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stopien_studiow
-- ----------------------------
INSERT INTO `stopien_studiow` VALUES (1, '1st');
INSERT INTO `stopien_studiow` VALUES (2, '2st');
INSERT INTO `stopien_studiow` VALUES (3, '3st');

-- ----------------------------
-- Table structure for studenci_specjalnosci
-- ----------------------------
DROP TABLE IF EXISTS `studenci_specjalnosci`;
CREATE TABLE `studenci_specjalnosci`  (
  `ID_studenci` int(10) UNSIGNED NOT NULL,
  `ID_specjalnosci` int(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`ID_studenci`, `ID_specjalnosci`) USING BTREE,
  INDEX `ID_specjalnosci`(`ID_specjalnosci`) USING BTREE,
  CONSTRAINT `studenci_specjalnosci_ibfk_1` FOREIGN KEY (`ID_studenci`) REFERENCES `uzytkownicy` (`ID_uzytkownicy`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `studenci_specjalnosci_ibfk_2` FOREIGN KEY (`ID_specjalnosci`) REFERENCES `specjalnosci` (`ID_specjalnosci`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of studenci_specjalnosci
-- ----------------------------

-- ----------------------------
-- Table structure for typ_konta
-- ----------------------------
DROP TABLE IF EXISTS `typ_konta`;
CREATE TABLE `typ_konta`  (
  `ID_typ_konta` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `nazwa_typu_konta` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`ID_typ_konta`) USING BTREE,
  UNIQUE INDEX `nazwa_typu_konta`(`nazwa_typu_konta`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of typ_konta
-- ----------------------------
INSERT INTO `typ_konta` VALUES (3, 'administrator');
INSERT INTO `typ_konta` VALUES (1, 'nauczyciel');
INSERT INTO `typ_konta` VALUES (2, 'student');

-- ----------------------------
-- Table structure for uzytkownicy
-- ----------------------------
DROP TABLE IF EXISTS `uzytkownicy`;
CREATE TABLE `uzytkownicy`  (
  `ID_uzytkownicy` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `login` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `haslo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `imie` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `nazwisko` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`ID_uzytkownicy`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of uzytkownicy
-- ----------------------------
INSERT INTO `uzytkownicy` VALUES (1, 'jk', 'x', 'Jan', 'Kowalski');
INSERT INTO `uzytkownicy` VALUES (2, 'an', 'x', 'Anna', 'Nowak');
INSERT INTO `uzytkownicy` VALUES (3, 'mn', 'x', 'Maria', 'Nowakowska');
INSERT INTO `uzytkownicy` VALUES (4, 'zk', 'x', 'Zygmunt', 'Kowalewski');
INSERT INTO `uzytkownicy` VALUES (5, 'gz', 'x', 'Genowefa', 'Zygmunt');

-- ----------------------------
-- Table structure for uzytkownicy_typ_konta
-- ----------------------------
DROP TABLE IF EXISTS `uzytkownicy_typ_konta`;
CREATE TABLE `uzytkownicy_typ_konta`  (
  `ID_uzytkownicy` int(10) UNSIGNED NOT NULL,
  `ID_typ_konta` int(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`ID_uzytkownicy`, `ID_typ_konta`) USING BTREE,
  INDEX `ID_typ_konta`(`ID_typ_konta`) USING BTREE,
  CONSTRAINT `uzytkownicy_typ_konta_ibfk_1` FOREIGN KEY (`ID_uzytkownicy`) REFERENCES `uzytkownicy` (`ID_uzytkownicy`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `uzytkownicy_typ_konta_ibfk_2` FOREIGN KEY (`ID_typ_konta`) REFERENCES `typ_konta` (`ID_typ_konta`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of uzytkownicy_typ_konta
-- ----------------------------
INSERT INTO `uzytkownicy_typ_konta` VALUES (1, 1);
INSERT INTO `uzytkownicy_typ_konta` VALUES (1, 3);
INSERT INTO `uzytkownicy_typ_konta` VALUES (2, 1);
INSERT INTO `uzytkownicy_typ_konta` VALUES (3, 2);
INSERT INTO `uzytkownicy_typ_konta` VALUES (4, 2);
INSERT INTO `uzytkownicy_typ_konta` VALUES (5, 2);

-- ----------------------------
-- Table structure for wydzialy
-- ----------------------------
DROP TABLE IF EXISTS `wydzialy`;
CREATE TABLE `wydzialy`  (
  `ID_wydzial` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `nazwa_wydzialu` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID_wydzial`) USING BTREE,
  UNIQUE INDEX `nazwa_wydzialu`(`nazwa_wydzialu`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wydzialy
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
