-- phpMyAdmin SQL Dump
-- version 4.1.4
-- http://www.phpmyadmin.net
--
-- Počítač: 127.0.0.1
-- Vytvořeno: Sob 28. led 2017, 18:43
-- Verze serveru: 5.6.15-log
-- Verze PHP: 5.5.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Databáze: `pia`
--
CREATE DATABASE IF NOT EXISTS `pia` DEFAULT CHARACTER SET utf8 COLLATE utf8_czech_ci;
USE `pia`;

-- --------------------------------------------------------

--
-- Struktura tabulky `zelenikr_rbank_address`
--

DROP TABLE IF EXISTS `zelenikr_rbank_address`;
CREATE TABLE IF NOT EXISTS `zelenikr_rbank_address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `city` varchar(255) COLLATE utf8_czech_ci DEFAULT NULL,
  `houseNumber` int(11) DEFAULT NULL,
  `street` varchar(255) COLLATE utf8_czech_ci DEFAULT NULL,
  `zipCode` varchar(255) COLLATE utf8_czech_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci AUTO_INCREMENT=14 ;

--
-- Vypisuji data pro tabulku `zelenikr_rbank_address`
--

INSERT INTO `zelenikr_rbank_address` (`id`, `city`, `houseNumber`, `street`, `zipCode`) VALUES
(1, 'New York City', 11, 'Main street', '10000'),
(2, 'city', 1, 'street', '10000'),
(4, 'city', 1, 'street', '77541'),
(5, 'city', 1, 'street', '30992'),
(6, 'city', 1, 'street', '21439'),
(7, 'city', 1, 'street', '00402'),
(8, 'city', 0, 'street', ''),
(9, 'city', 1, 'street', '08226'),
(10, 'New York City', 5, 'Main street', '20184'),
(11, 'city', 1, 'street', '34651'),
(12, 'city', 1, 'street', '66444'),
(13, 'P?eštice', 10, 'Hlávkova', '33401');

-- --------------------------------------------------------

--
-- Struktura tabulky `zelenikr_rbank_bank_account`
--

DROP TABLE IF EXISTS `zelenikr_rbank_bank_account`;
CREATE TABLE IF NOT EXISTS `zelenikr_rbank_bank_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `accountNumber` varchar(255) COLLATE utf8_czech_ci NOT NULL,
  `sum` decimal(19,2) DEFAULT NULL,
  `creditCard_id` bigint(20) DEFAULT NULL,
  `owner_id` bigint(20) DEFAULT NULL,
  `currency` varchar(255) COLLATE utf8_czech_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_k7632kwdy3dqo83olvesme1r7` (`accountNumber`),
  KEY `FK_1nq7y99b9cac61kakojh202o5` (`creditCard_id`),
  KEY `FK_47fk6ie9jvyshcpdgjb9w5pue` (`owner_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci AUTO_INCREMENT=25 ;

--
-- Vypisuji data pro tabulku `zelenikr_rbank_bank_account`
--

INSERT INTO `zelenikr_rbank_bank_account` (`id`, `accountNumber`, `sum`, `creditCard_id`, `owner_id`, `currency`) VALUES
(1, '728182979', '4010.80', 1, 2, 'CZK'),
(2, '111111111', '783.00', 22, 2, 'USD'),
(3, '222222222', '380.00', 23, 2, 'EUR'),
(6, '154097612', '0.00', 4, 5, 'USD'),
(7, '267942728', '0.00', 5, 6, 'USD'),
(8, '343303272', '0.00', 6, 7, 'USD'),
(9, '476490262', '0.00', 7, 8, 'USD'),
(10, '350529604', '0.00', 8, 9, 'USD'),
(11, '916128473', '0.00', 9, 10, 'USD'),
(12, '894904268', '197.00', 10, 11, 'USD'),
(13, '992116827', '0.00', 11, 12, 'USD'),
(14, '391907671', '0.00', 12, 13, 'USD'),
(18, '967162473', '250.00', 24, 3, 'EUR'),
(19, '979329051', '350.00', 25, 3, 'CZK'),
(23, '763857343', '20.00', 29, 3, 'USD'),
(24, '156212520', '0.00', 31, 14, 'CZK');

-- --------------------------------------------------------

--
-- Struktura tabulky `zelenikr_rbank_client`
--

DROP TABLE IF EXISTS `zelenikr_rbank_client`;
CREATE TABLE IF NOT EXISTS `zelenikr_rbank_client` (
  `id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

--
-- Vypisuji data pro tabulku `zelenikr_rbank_client`
--

INSERT INTO `zelenikr_rbank_client` (`id`) VALUES
(2),
(3),
(5),
(6),
(7),
(8),
(9),
(10),
(11),
(12),
(13),
(14);

-- --------------------------------------------------------

--
-- Struktura tabulky `zelenikr_rbank_credit_card`
--

DROP TABLE IF EXISTS `zelenikr_rbank_credit_card`;
CREATE TABLE IF NOT EXISTS `zelenikr_rbank_credit_card` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `creditCardNumber` varchar(255) COLLATE utf8_czech_ci NOT NULL,
  `pin` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_mvvrw9x3hbfthepo9jhy97x11` (`creditCardNumber`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci AUTO_INCREMENT=32 ;

--
-- Vypisuji data pro tabulku `zelenikr_rbank_credit_card`
--

INSERT INTO `zelenikr_rbank_credit_card` (`id`, `creditCardNumber`, `pin`) VALUES
(1, '3422335017849884', 1234),
(4, '4974911237466588', 1234),
(5, '3029710716439918', 1234),
(6, '3381012801269743', 1234),
(7, '1293517593542807', 1234),
(8, '8373051618755791', 1234),
(9, '9589725713914156', 1234),
(10, '0245213269672268', 1234),
(11, '0015219329933563', 1234),
(12, '3366894437568481', 1234),
(22, '1111335017849884', 1111),
(23, '2222335017849884', 2222),
(24, '3537267708963473', 4660),
(25, '6064864216444062', 6493),
(29, '9681306719639778', 5201),
(31, '4804663210315646', 9280);

-- --------------------------------------------------------

--
-- Struktura tabulky `zelenikr_rbank_pattern_payment_order`
--

DROP TABLE IF EXISTS `zelenikr_rbank_pattern_payment_order`;
CREATE TABLE IF NOT EXISTS `zelenikr_rbank_pattern_payment_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `amount` decimal(19,2) DEFAULT NULL,
  `constSymbol` varchar(255) COLLATE utf8_czech_ci DEFAULT NULL,
  `currency` varchar(255) COLLATE utf8_czech_ci DEFAULT NULL,
  `dueDate` date DEFAULT NULL,
  `message` varchar(255) COLLATE utf8_czech_ci DEFAULT NULL,
  `bankCode` varchar(255) COLLATE utf8_czech_ci DEFAULT NULL,
  `offsetAccountNumber` varchar(255) COLLATE utf8_czech_ci DEFAULT NULL,
  `specificSymbol` varchar(255) COLLATE utf8_czech_ci DEFAULT NULL,
  `variableSymbol` varchar(255) COLLATE utf8_czech_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_czech_ci NOT NULL,
  `owner_id` bigint(20) DEFAULT NULL,
  `ownerAccount_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ay1dg7mel82uu9u0lx3x5tabv` (`name`,`owner_id`),
  KEY `FK_9afbqqlvqgpmw9vwe9wn4mgi3` (`owner_id`),
  KEY `FK_q86rljj8ixsth9soriu4ypwso` (`ownerAccount_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci AUTO_INCREMENT=9 ;

--
-- Vypisuji data pro tabulku `zelenikr_rbank_pattern_payment_order`
--

INSERT INTO `zelenikr_rbank_pattern_payment_order` (`id`, `amount`, `constSymbol`, `currency`, `dueDate`, `message`, `bankCode`, `offsetAccountNumber`, `specificSymbol`, `variableSymbol`, `name`, `owner_id`, `ownerAccount_id`) VALUES
(1, '1000.00', '', 'CZK', '2017-02-01', '', '6666', '', '', '', 'prvni vzor', 2, 2),
(4, '50.00', '', 'EUR', NULL, '', '', '', '', '', 'druhy vzor', 2, 3),
(5, '0.00', '', 'CZK', NULL, '', '6666', '', '', '', 'vzor z prikazu', 2, 1),
(6, '0.00', '', 'EUR', '2017-01-25', '', '6666', '222222222', '', '', 'for Kim euro', 3, 18),
(7, '0.00', '', 'EUR', NULL, '', '6666', '967162473', '', '', 'for Jane euro', 2, 3),
(8, '0.00', '', 'USD', '2017-01-25', '', '6666', '763857343', '', '', 'for Jane dolar', 2, 2);

-- --------------------------------------------------------

--
-- Struktura tabulky `zelenikr_rbank_payment_transaction`
--

DROP TABLE IF EXISTS `zelenikr_rbank_payment_transaction`;
CREATE TABLE IF NOT EXISTS `zelenikr_rbank_payment_transaction` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `amount` decimal(19,2) NOT NULL,
  `constSymbol` varchar(255) COLLATE utf8_czech_ci DEFAULT NULL,
  `dueDate` date NOT NULL,
  `message` varchar(255) COLLATE utf8_czech_ci DEFAULT NULL,
  `bankCode` varchar(255) COLLATE utf8_czech_ci DEFAULT NULL,
  `offsetAccountNumber` varchar(255) COLLATE utf8_czech_ci DEFAULT NULL,
  `specificSymbol` varchar(255) COLLATE utf8_czech_ci DEFAULT NULL,
  `variableSymbol` varchar(255) COLLATE utf8_czech_ci DEFAULT NULL,
  `state` varchar(255) COLLATE utf8_czech_ci NOT NULL,
  `type` varchar(255) COLLATE utf8_czech_ci NOT NULL,
  `clientAccount_id` bigint(20) DEFAULT NULL,
  `currency` varchar(255) COLLATE utf8_czech_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_jvjctk5nord9n7oyeeqhuh8um` (`clientAccount_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci AUTO_INCREMENT=32 ;

--
-- Vypisuji data pro tabulku `zelenikr_rbank_payment_transaction`
--

INSERT INTO `zelenikr_rbank_payment_transaction` (`id`, `amount`, `constSymbol`, `dueDate`, `message`, `bankCode`, `offsetAccountNumber`, `specificSymbol`, `variableSymbol`, `state`, `type`, `clientAccount_id`, `currency`) VALUES
(1, '-200.00', '', '2017-01-20', 'testovací platba', '6666', '979329051', '', '', 'SENT', 'ONE_TIME_PAYMENT_ORDER', 1, 'CZK'),
(2, '200.00', '', '2017-01-20', 'testovací platba', '6666', '728182979', '', '', 'RECEIVED', 'INCOMING_PAYMENT', 19, 'CZK'),
(3, '-100.00', '0100', '2017-01-21', 'druha platba', '6666', '979329051', '22', '222222', 'SENT', 'ONE_TIME_PAYMENT_ORDER', 1, 'CZK'),
(4, '100.00', '0100', '2017-01-21', 'druha platba', '6666', '728182979', '22', '222222', 'RECEIVED', 'INCOMING_PAYMENT', 19, 'CZK'),
(5, '-50.00', '', '2017-01-22', 'a give', '0300', '894904268', '', '', 'SENT', 'ONE_TIME_PAYMENT_ORDER', 2, 'USD'),
(6, '50.00', '', '2017-01-22', 'a give', '6666', '111111111', '', '', 'RECEIVED', 'INCOMING_PAYMENT', 12, 'USD'),
(7, '-100.00', '', '2017-01-22', '', '6666', '979329051', '', '', 'SENT', 'ONE_TIME_PAYMENT_ORDER', 1, 'CZK'),
(8, '100.00', '', '2017-01-22', '', '6666', '728182979', '', '', 'RECEIVED', 'INCOMING_PAYMENT', 19, 'CZK'),
(9, '-25.00', '11', '2017-01-21', '', '6666', '894904268', '33333', '222', 'SENT', 'ONE_TIME_PAYMENT_ORDER', 2, 'USD'),
(10, '25.00', '11', '2017-01-21', '', '6666', '111111111', '33333', '222', 'RECEIVED', 'INCOMING_PAYMENT', 12, 'USD'),
(11, '-150.00', '1', '2017-01-22', '', '6666', '728182979', '1', '1', 'SENT', 'ONE_TIME_PAYMENT_ORDER', 19, 'CZK'),
(12, '150.00', '1', '2017-01-22', '', '6666', '979329051', '1', '1', 'RECEIVED', 'INCOMING_PAYMENT', 1, 'CZK'),
(18, '-122.00', '', '2017-01-23', '', '0300', '894904268', '', '00023', 'SENT', 'ONE_TIME_PAYMENT_ORDER', 2, 'USD'),
(19, '122.00', '', '2017-01-23', '', '6666', '111111111', '', '00023', 'RECEIVED', 'INCOMING_PAYMENT', 12, 'USD'),
(20, '-40.00', '', '2017-01-23', '', '6666', '967162473', '', '', 'SENT', 'ONE_TIME_PAYMENT_ORDER', 3, 'EUR'),
(21, '40.00', '', '2017-01-23', '', '6666', '222222222', '', '', 'RECEIVED', 'INCOMING_PAYMENT', 18, 'EUR'),
(23, '120.00', '', '2017-01-23', '', '6666', '862534143', '', '', 'RECEIVED', 'INCOMING_PAYMENT', 18, 'EUR'),
(24, '-80.00', '', '2017-01-25', '', '6666', '967162473', '', '', 'SENT', 'ONE_TIME_PAYMENT_ORDER', 3, 'EUR'),
(25, '80.00', '', '2017-01-25', '', '6666', '222222222', '', '', 'RECEIVED', 'INCOMING_PAYMENT', 18, 'EUR'),
(26, '-270.20', '', '2017-01-25', '', '6666', '967162473', '', '', 'SENT', 'ONE_TIME_PAYMENT_ORDER', 1, 'CZK'),
(27, '10.00', '', '2017-01-25', '', '6666', '728182979', '', '', 'RECEIVED', 'INCOMING_PAYMENT', 18, 'EUR'),
(28, '-170.00', '', '2017-01-25', '', '0300', '9999999999', '', '', 'SENT', 'ONE_TIME_PAYMENT_ORDER', 1, 'CZK'),
(29, '-20.00', '', '2017-01-25', '', '6666', '763857343', '', '', 'SENT', 'ONE_TIME_PAYMENT_ORDER', 2, 'USD'),
(30, '20.00', '', '2017-01-25', '', '6666', '111111111', '', '', 'RECEIVED', 'INCOMING_PAYMENT', 23, 'USD'),
(31, '-199.00', '', '2017-01-26', '', '0100', '2877896541025547', '', '', 'SENT', 'ONE_TIME_PAYMENT_ORDER', 1, 'CZK');

-- --------------------------------------------------------

--
-- Struktura tabulky `zelenikr_rbank_person`
--

DROP TABLE IF EXISTS `zelenikr_rbank_person`;
CREATE TABLE IF NOT EXISTS `zelenikr_rbank_person` (
  `email` varchar(255) COLLATE utf8_czech_ci NOT NULL,
  `name` varchar(255) COLLATE utf8_czech_ci NOT NULL,
  `personalIdNumber` varchar(255) COLLATE utf8_czech_ci NOT NULL,
  `phoneNumber` varchar(255) COLLATE utf8_czech_ci DEFAULT NULL,
  `surname` varchar(255) COLLATE utf8_czech_ci NOT NULL,
  `id` bigint(20) NOT NULL,
  `address_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_d6q5es3sgvxumnq3nequxnb92` (`address_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

--
-- Vypisuji data pro tabulku `zelenikr_rbank_person`
--

INSERT INTO `zelenikr_rbank_person` (`email`, `name`, `personalIdNumber`, `phoneNumber`, `surname`, `id`, `address_id`) VALUES
('zelenikr@students.zcu.cz', 'Kim', '1111111111', '777666777', 'Doo', 2, 1),
('zelenikr@students.zcu.cz', 'Jane', '2222222222', '777222777', 'Doo', 3, 2),
('zelenikr@students.zcu.cz', 'Jim', '2870573249', '916167880', 'Foo', 5, 4),
('zelenikr@students.zcu.cz', 'John', '7645371242', '094535759', 'Doo', 6, 5),
('zelenikr@students.zcu.cz', 'Karl', '4783874758', '793682656', 'Smith', 7, 6),
('zelenikr@students.zcu.cz', 'John', '6565595946', '502977659', 'Doo', 8, 7),
('zelenikr@students.zcu.cz', 'Alex', '8886561597', '722961412', 'Colson', 9, 8),
('zelenikr@students.zcu.cz', 'John', '9258907924', '534000697', 'Smith', 10, 9),
('zelenikr@students.zcu.cz', 'John', '6225419109', '967808611', 'Black', 11, 10),
('zelenikr@students.zcu.cz', 'Neal', '0010630855', '022504463', 'Green', 12, 11),
('zelenikr@students.zcu.cz', 'Jim', '1701532185', '221361310', 'Green', 13, 12),
('zelenikr@students.zcu.cz', 'Jan', '8605092365', '777555666', 'Novák', 14, 13);

-- --------------------------------------------------------

--
-- Struktura tabulky `zelenikr_rbank_role`
--

DROP TABLE IF EXISTS `zelenikr_rbank_role`;
CREATE TABLE IF NOT EXISTS `zelenikr_rbank_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) COLLATE utf8_czech_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_7veom89wc3fchmfkqkk1e3ywy` (`type`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci AUTO_INCREMENT=4 ;

--
-- Vypisuji data pro tabulku `zelenikr_rbank_role`
--

INSERT INTO `zelenikr_rbank_role` (`id`, `type`) VALUES
(1, 'ROLE_ADMIN'),
(3, 'ROLE_CLIENT');

-- --------------------------------------------------------

--
-- Struktura tabulky `zelenikr_rbank_user`
--

DROP TABLE IF EXISTS `zelenikr_rbank_user`;
CREATE TABLE IF NOT EXISTS `zelenikr_rbank_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `password` varchar(255) COLLATE utf8_czech_ci NOT NULL,
  `username` varchar(255) COLLATE utf8_czech_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ncof4ougd8chmk0tpmc8pgpsi` (`username`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci AUTO_INCREMENT=15 ;

--
-- Vypisuji data pro tabulku `zelenikr_rbank_user`
--

INSERT INTO `zelenikr_rbank_user` (`id`, `password`, `username`) VALUES
(1, '1000:d57817096628c5cc4f2cd2ba1fd1d5c58649d16cdfdfc556:c5da037524898cf134dc0dd82f7d0066c1b23efcaec25e11', 'Admin001'),
(2, '1000:4e5b074265de3c48c79c3f83d03dfb93e6d169cc094f7198:56e880f4af2c70a553942e3b87082e9d07421cdb24ae67ba', 'User0001'),
(3, '1000:858be87c7b57dcb9cc0869ee4c2a854b63416fd3f1ed0d58:23fc2358e5be3bb80ad6e0f683335971ca0d5a3f88adc218', 'User0002'),
(5, '1000:36001a2a3ad1e855ba30fa0a08c8d26bddf05e88110a9876:cddfb212579dabf3aaa036a09df172dac7e89da7d460efa7', 'FvBGv1Mk'),
(6, '1000:5cb8bbc87c95e7a69d9c2de06ef138e40731c59fd73fa0ed:3fceb88bd65e7b3a6cdc6e49d880e3b284e4c551a8bb80df', 'c5EdiVl9'),
(7, '1000:51ff8450f753f4f3c35a8ca3b42ddfb441da4d3101116cd1:be5c05c3626586efc96a84cbbc0810b05784ac99962f2f81', 'aI62EU3S'),
(8, '1000:16b010f349417a1066986fa696a7a4e9984040d49caf7a98:70bdd826e8c83129f7097ed30d6a3783e50ddb0d4cca14b6', '8f0kcb2k'),
(9, '1000:2ca7dee0b52861823f94193b8ae6f8054d3de58d77933b5f:ed2b7e4bf7f83c6b1af81152a3483f256f396b145ba3aad4', 'XyLwtXxW'),
(10, '1000:c280b3e24b82b89733e494fb95480744f8a2ba0fc9b308b4:47a8aeef56d7ca828eaf232e2aed082c07d26ba611a5deaf', 'Um8vz6KX'),
(11, '1000:56a674dda8056d8b79d2b57b26a512366d59e4189403916c:65d1b8a30dca52448f928f87876fe6d70ed95224fbec1463', '8x0Cszh8'),
(12, '1000:6703932251d2245660fcfd8a6016f86babd44c93da289789:14ecbee6e17499d571318b97c865732e8a24822bdd8ed0cb', '3HkZBgjB'),
(13, '1000:de8037d3825bd504ad1d564961df5d219d1c0b37743f0534:545e243d4e7984d1450b6d59896cfa486069226a6f06d149', 'uHRsqiAT'),
(14, '1000:978f0e6ca4a58416cbb0a9c14305fba258ab99cdc4f7a525:9ce25d10a31a88060987e65ca8497195926177373543f117', 'LBuQryKV');

-- --------------------------------------------------------

--
-- Struktura tabulky `zelenikr_rbank_user_roles`
--

DROP TABLE IF EXISTS `zelenikr_rbank_user_roles`;
CREATE TABLE IF NOT EXISTS `zelenikr_rbank_user_roles` (
  `user` varchar(255) COLLATE utf8_czech_ci NOT NULL,
  `role` bigint(20) NOT NULL,
  PRIMARY KEY (`user`,`role`),
  KEY `FK_q79utfrpmmofkksli9hd1tt6o` (`role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

--
-- Vypisuji data pro tabulku `zelenikr_rbank_user_roles`
--

INSERT INTO `zelenikr_rbank_user_roles` (`user`, `role`) VALUES
('Admin001', 1),
('8x0Cszh8', 3),
('aI62EU3S', 3),
('c5EdiVl9', 3),
('FvBGv1Mk', 3),
('hL1eK72Z', 3),
('LBuQryKV', 3),
('uHRsqiAT', 3),
('UiHCV4tW', 3),
('Um8vz6KX', 3),
('User0001', 3),
('User0002', 3),
('XyLwtXxW', 3);

--
-- Omezení pro exportované tabulky
--

--
-- Omezení pro tabulku `zelenikr_rbank_bank_account`
--
ALTER TABLE `zelenikr_rbank_bank_account`
  ADD CONSTRAINT `FK_1nq7y99b9cac61kakojh202o5` FOREIGN KEY (`creditCard_id`) REFERENCES `zelenikr_rbank_credit_card` (`id`),
  ADD CONSTRAINT `FK_47fk6ie9jvyshcpdgjb9w5pue` FOREIGN KEY (`owner_id`) REFERENCES `zelenikr_rbank_client` (`id`);

--
-- Omezení pro tabulku `zelenikr_rbank_client`
--
ALTER TABLE `zelenikr_rbank_client`
  ADD CONSTRAINT `FK_nps14y4odsr6o23k6rgo6a6tm` FOREIGN KEY (`id`) REFERENCES `zelenikr_rbank_person` (`id`);

--
-- Omezení pro tabulku `zelenikr_rbank_pattern_payment_order`
--
ALTER TABLE `zelenikr_rbank_pattern_payment_order`
  ADD CONSTRAINT `FK_9afbqqlvqgpmw9vwe9wn4mgi3` FOREIGN KEY (`owner_id`) REFERENCES `zelenikr_rbank_client` (`id`),
  ADD CONSTRAINT `FK_q86rljj8ixsth9soriu4ypwso` FOREIGN KEY (`ownerAccount_id`) REFERENCES `zelenikr_rbank_bank_account` (`id`);

--
-- Omezení pro tabulku `zelenikr_rbank_payment_transaction`
--
ALTER TABLE `zelenikr_rbank_payment_transaction`
  ADD CONSTRAINT `FK_jvjctk5nord9n7oyeeqhuh8um` FOREIGN KEY (`clientAccount_id`) REFERENCES `zelenikr_rbank_bank_account` (`id`);

--
-- Omezení pro tabulku `zelenikr_rbank_person`
--
ALTER TABLE `zelenikr_rbank_person`
  ADD CONSTRAINT `FK_6y5ypfjdhc3kpo50kl652wp5i` FOREIGN KEY (`id`) REFERENCES `zelenikr_rbank_user` (`id`),
  ADD CONSTRAINT `FK_d6q5es3sgvxumnq3nequxnb92` FOREIGN KEY (`address_id`) REFERENCES `zelenikr_rbank_address` (`id`);

--
-- Omezení pro tabulku `zelenikr_rbank_user_roles`
--
ALTER TABLE `zelenikr_rbank_user_roles`
  ADD CONSTRAINT `FK_q79utfrpmmofkksli9hd1tt6o` FOREIGN KEY (`role`) REFERENCES `zelenikr_rbank_role` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
