-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 16 Sty 2022, 22:49
-- Wersja serwera: 10.4.11-MariaDB
-- Wersja PHP: 7.2.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `sj4`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `action`
--

CREATE TABLE `action` (
  `id_action` int(11) NOT NULL,
  `name-a` varchar(40) COLLATE utf8_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `action`
--

INSERT INTO `action` (`id_action`, `name-a`) VALUES
(1, 'dodawanie'),
(2, 'modyfikacja'),
(3, 'usuwanie'),
(4, 'cofnięcie zmian'),
(5, 'nowe logowanie'),
(6, 'ponowienie zmiany'),
(7, 'modyfikacja zdjęć'),
(8, 'dodanie zamówienia'),
(9, 'dodanie użytkownika'),
(10, 'odnowienie towaru');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `basket`
--

CREATE TABLE `basket` (
  `id_session-b` text COLLATE utf8_polish_ci DEFAULT NULL,
  `id_user-b` int(11) DEFAULT NULL,
  `id_prod-b` int(11) NOT NULL,
  `quantity-b` int(11) NOT NULL,
  `expires-b` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `basket`
--

INSERT INTO `basket` (`id_session-b`, `id_user-b`, `id_prod-b`, `quantity-b`, `expires-b`) VALUES
(NULL, 1, 5, 2, '2021-11-08 11:12:32'),
(NULL, 21, 3, 1, '2022-01-15 18:04:41'),
(NULL, 2, 36, 3, '2022-01-15 18:04:41'),
(NULL, 2, 16, 1, '2022-01-15 18:04:41'),
(NULL, 2, 8, 3, '2022-03-06 15:52:44'),
(NULL, 2, 9, 21, '2022-01-15 18:04:41'),
('i-u2OGmjzKbWdILO5wsexWxOeMxc9ubudDqhkYwc', NULL, 31, 1, '2022-03-06 15:52:44');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `category`
--

CREATE TABLE `category` (
  `id_cat` int(11) NOT NULL,
  `id_group-c` int(11) NOT NULL,
  `name-c` text COLLATE utf8_polish_ci NOT NULL,
  `description-c` text COLLATE utf8_polish_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `category`
--

INSERT INTO `category` (`id_cat`, `id_group-c`, `name-c`, `description-c`) VALUES
(1, 1, 'Naturalne Minerały', 'Kamienie naturalne to niezwykłe skarby natury, które od lat z powodzeniem wykorzystywane są do wyrobu unikatowej i efektownej biżuterii. Stwarzają niemal nieograniczone możliwości w jubilerstwie i rękodzielnictwie - wspaniale prezentują się zarówno jako komponenty naszyjników, bransoletek oraz kolczyków. Łącząc je z odpowiednimi półfabrykatami przy użyciu różnorodnych technik, otrzymujemy trwałą i kunsztowną biżuterię, będącą oryginalnym dodatkiem zarówno do codziennych stylizacji, jak i kreacji na specjalne okazje'),
(2, 1, 'Minerały Syntetyczne', 'Dostępne w naszym sklepie kamienie występują w wielu wariantach rodzajowych do wyboru. Polecamy kocie oko w różnych opcjach kolorystycznych do wyboru, opalit pięknie podkreślający letnią opaleniznę czy kwarc, oferujący nieograniczoną możliwość tworzenia unikatowych kompozycji biżuteryjnych. Sprzedawane kamienie różnią się średnicą, kolorami i kształtem. Do wyboru są zarówno klasyczne propozycje, po szlifowane kulki, oliwki czy oponki - również w wersjach fasetowanych. Serdecznie zapraszamy do zapoznania się z kategorią kamieni syntetycznych. Ich wysoka wytrzymałość, bogactwo zastosowań oraz niska cena pozwoli popuścić wodze fantazji i stworzyć najbardziej kreatywny projekt  na każdą okazję!'),
(3, 1, 'Perły', 'Wielu twórców uwielbia tworzyć biżuterię z konkretnych materiałów często są to dary mórz i oceanów, a więc perły i muszle. Ich dobór według kryteriów estetyki, rzadkości, ale również barwy i kształtu bywa bardzo skomplikowany. W sklepie Kianit oferujemy naszym klientom, zdolnym rękodzielnikom, wiele różnych rodzajów półfabrykatów. Będą to zarówno rozmaite gatunki muszli, jak również perły sheashell oraz hodowlane w ciekawych barwach i nietypowych kształtach. W ten sposób możemy zaoferować szeroki wybór atrakcyjnych elementów biżuteryjnych, z którego każdy rękodzielnik jest w stanie dobrać te produkty, które faktycznie pasują do jego zamysłów czy prowadzonych projektów. Perły i muszle sprawdzą się jako zawieszki, elementy kolczyków czy części składowe bransoletek i naszyjników. Doskonale wyglądają zarówno jako dodatki, jak również podstawowe elementy, na których budujemy dany projekt. Wybierz już teraz perły i muszle w korzystnych cenach na www.kianit.pl i ciesz się ich pięknymi kształtami i niezwykłymi barwami.'),
(4, 1, 'Koral', NULL),
(5, 1, 'Bursztyn', NULL),
(6, 1, 'Szkło', NULL),
(7, 2, 'Stal Szlachetna', NULL),
(8, 2, 'Srebro', 'Wśród dostępnych w naszym sklepie półfabrykatów ze srebra próby 925 mogą Państwo znaleźć elementy służące do łączenia np. kamieni naturalnych w naszyjnikach, bransoletkach czy kolczykach. Półfabrykaty nierzadko też stanowią dekoracyjne wykończenie. Wykonanie biżuterii bez takich części jak kółka montażowe, bigle, zapięcia, przekładki czy szpilki byłoby trudne i czasochłonne. Mamy nadzieję, że bogactwo i różnorodność półfabrykatów srebrnych w naszym sklepie pozwoli każdemu twórcy biżuterii na realizację własnych autorskich projektów biżuterii.'),
(9, 2, 'Miedź', NULL),
(10, 2, 'Sylikon', NULL),
(11, 2, 'Inne', NULL),
(12, 3, 'Biżuteria', NULL),
(13, 1, 'Kryształki', NULL),
(14, 1, 'Ceramika', NULL),
(15, 4, 'Rzemienie', NULL),
(16, 4, 'Sznurki', NULL),
(17, 4, 'Gumki', NULL),
(18, 4, 'Wstążki', NULL),
(19, 4, 'Linki', NULL),
(20, 4, 'Nici', NULL),
(21, 4, 'Chwosty', NULL),
(22, 4, 'Żyłki', NULL),
(23, 4, 'Frędzle', NULL),
(24, 4, 'Jedwab', NULL),
(25, 4, 'Sutasz', NULL),
(26, 1, 'Drewno', NULL),
(27, 5, 'Opakowania', NULL),
(28, 5, 'Narzędzia', NULL);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `category-tag`
--

CREATE TABLE `category-tag` (
  `id_catag` int(11) NOT NULL,
  `id_cat-ct` int(11) NOT NULL,
  `id_tag-ct` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `category-tag`
--

INSERT INTO `category-tag` (`id_catag`, `id_cat-ct`, `id_tag-ct`) VALUES
(1, 1, 1),
(2, 1, 2),
(3, 1, 3),
(4, 1, 4),
(5, 1, 5),
(6, 1, 6),
(7, 1, 7),
(8, 1, 8),
(9, 1, 9),
(10, 1, 10),
(11, 1, 11),
(12, 1, 12),
(13, 1, 13),
(14, 1, 14),
(15, 1, 15),
(16, 1, 16),
(17, 1, 17),
(18, 1, 18),
(19, 1, 19),
(20, 1, 20),
(21, 1, 21),
(22, 1, 22),
(23, 1, 23),
(24, 1, 24),
(25, 1, 25),
(26, 1, 26),
(27, 1, 27),
(28, 1, 28),
(29, 1, 29),
(30, 1, 30),
(31, 1, 31),
(32, 1, 32),
(33, 1, 33),
(34, 1, 34),
(35, 1, 35),
(36, 1, 36),
(37, 1, 37),
(38, 1, 38),
(39, 1, 39),
(40, 1, 40),
(41, 1, 41),
(42, 1, 42),
(43, 1, 43),
(44, 1, 44),
(45, 1, 45),
(46, 1, 46),
(47, 1, 47),
(48, 1, 48),
(49, 1, 49),
(50, 1, 50),
(51, 1, 51),
(52, 1, 52),
(53, 1, 53),
(54, 1, 54),
(55, 1, 55),
(56, 1, 56),
(57, 1, 57),
(58, 1, 58),
(59, 1, 59),
(60, 1, 60),
(61, 1, 61),
(62, 1, 62),
(63, 1, 63),
(64, 1, 64),
(65, 1, 65),
(66, 1, 66),
(67, 1, 67),
(68, 1, 68),
(69, 1, 69),
(70, 1, 70),
(71, 1, 71),
(72, 1, 72),
(73, 1, 73),
(74, 1, 74),
(75, 1, 75),
(76, 1, 76),
(77, 1, 77),
(78, 1, 78),
(79, 1, 79),
(80, 1, 80),
(81, 1, 81),
(82, 1, 82),
(83, 1, 83),
(84, 1, 84),
(85, 1, 85),
(86, 1, 86),
(87, 1, 87),
(88, 2, 88),
(89, 2, 89),
(90, 2, 90),
(91, 2, 91),
(92, 2, 92),
(93, 2, 93),
(94, 2, 94),
(95, 3, 95),
(96, 3, 96),
(97, 3, 97),
(98, 3, 98),
(99, 3, 99),
(100, 4, 99),
(101, 5, 100),
(102, 5, 101),
(103, 6, 102),
(104, 6, 103),
(105, 7, 104),
(106, 7, 105),
(107, 7, 106),
(108, 7, 107),
(109, 7, 108),
(110, 7, 109),
(111, 7, 110),
(112, 7, 111),
(113, 7, 112),
(114, 7, 113),
(115, 7, 114),
(116, 7, 115),
(117, 7, 117),
(118, 7, 118),
(119, 7, 119),
(120, 7, 120),
(121, 7, 121),
(122, 9, 104),
(123, 8, 105),
(124, 8, 106),
(125, 8, 107),
(126, 8, 108),
(127, 8, 109),
(128, 8, 110),
(129, 8, 111),
(130, 8, 112),
(131, 8, 113),
(132, 8, 114),
(133, 8, 115),
(134, 8, 116),
(135, 8, 117),
(136, 8, 118),
(137, 8, 119),
(138, 8, 120),
(139, 8, 121),
(140, 9, 104),
(141, 9, 105),
(142, 9, 106),
(143, 9, 107),
(144, 9, 108),
(145, 9, 109),
(146, 9, 110),
(147, 9, 111),
(148, 9, 112),
(149, 9, 113),
(150, 9, 114),
(151, 9, 115),
(152, 9, 116),
(153, 9, 117),
(154, 9, 118),
(155, 9, 119),
(156, 9, 120),
(157, 9, 121),
(158, 11, 104),
(159, 11, 105),
(160, 11, 106),
(161, 11, 107),
(162, 11, 108),
(163, 11, 109),
(164, 11, 110),
(165, 11, 111),
(166, 11, 112),
(167, 11, 113),
(168, 11, 114),
(169, 11, 115),
(170, 11, 116),
(171, 11, 117),
(172, 11, 118),
(173, 11, 119),
(174, 11, 120),
(175, 11, 121),
(176, 12, 122),
(177, 12, 123),
(178, 12, 124),
(179, 12, 125),
(180, 13, 126),
(181, 13, 127),
(182, 14, 128),
(183, 14, 129),
(184, 15, 130),
(185, 15, 131),
(186, 15, 132),
(187, 15, 133),
(188, 16, 134),
(189, 16, 135),
(190, 16, 136),
(191, 16, 137),
(192, 18, 138),
(193, 18, 139),
(194, 24, 140),
(195, 24, 141),
(196, 25, 142),
(197, 25, 143),
(198, 26, 144),
(199, 26, 145),
(200, 26, 146),
(201, 26, 147),
(202, 26, 148),
(203, 26, 149),
(204, 27, 150),
(205, 27, 151),
(206, 27, 152),
(207, 27, 153),
(208, 28, 154),
(209, 28, 155),
(210, 28, 156),
(211, 28, 157),
(212, 28, 158),
(267, 4, 168);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `color`
--

CREATE TABLE `color` (
  `id_col` int(11) NOT NULL,
  `name-cl` text COLLATE utf8_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `color`
--

INSERT INTO `color` (`id_col`, `name-cl`) VALUES
(1, 'białyy'),
(2, 'żółty'),
(3, 'pomarańczowy'),
(4, 'czerwony'),
(5, 'różowy'),
(6, 'fioletowy'),
(7, 'niebieski'),
(8, 'granatowy'),
(9, 'turkusowy'),
(10, 'zielony'),
(11, 'brązowy'),
(12, 'beżowy'),
(13, 'szary'),
(14, 'czarny'),
(15, 'srebrny'),
(16, 'złoty'),
(17, 'miedziany'),
(18, 'mosiężny'),
(19, 'wielokolorowy'),
(22, 'oliwkowy'),
(25, 'miętowy');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `delivery`
--

CREATE TABLE `delivery` (
  `id_deliv` int(11) NOT NULL,
  `name-d` text COLLATE utf8_polish_ci NOT NULL,
  `price-d` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `delivery`
--

INSERT INTO `delivery` (`id_deliv`, `name-d`, `price-d`) VALUES
(1, 'Poczta Polska - list polecony', 8.01),
(2, 'InPost - paczkomaty', 9.5),
(3, 'InPost - przesyłka kurierska', 14.9),
(4, 'Kurier DPD - przesyłka kurierska', 14),
(5, 'Kurier DPD - przesyłka za pobraniem', 18),
(6, 'Poczta Polska (zagraniczna)', 26),
(7, 'Kurier DPD (zagraniczna) - Europa środkowa', 40),
(8, 'Kurier DPD (zagraniczna) - Europa Zachodnia', 60),
(9, 'Kurier DPD (zagraniczna) - Afryka', 70),
(10, 'Kurier DPD (zagraniczna)', 120),
(11, 'Odbiór osobisty w siedzibie firmy', 0),
(12, 'FedEx', 30.2);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `discount`
--

CREATE TABLE `discount` (
  `id_disc` int(11) NOT NULL,
  `name-dc` varchar(50) COLLATE utf8_polish_ci NOT NULL,
  `active` tinyint(1) NOT NULL DEFAULT 1,
  `value` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `discount`
--

INSERT INTO `discount` (`id_disc`, `name-dc`, `active`, `value`) VALUES
(1, 'Prezent21', 1, 50),
(2, 'Summer23', 0, 0),
(4, 'Summer22', 1, 70);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `fabric`
--

CREATE TABLE `fabric` (
  `id_fabr` int(11) NOT NULL,
  `name-fb` text COLLATE utf8_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `fabric`
--

INSERT INTO `fabric` (`id_fabr`, `name-fb`) VALUES
(1, 'srebro 875'),
(2, 'srebro 925'),
(3, 'srebro 9999'),
(4, 'stal szlachetna'),
(5, 'miedź'),
(6, 'metal'),
(7, 'sylikonn'),
(8, 'skóra'),
(9, 'nubuk'),
(10, 'zamsz'),
(11, 'welur'),
(12, 'kryształ'),
(13, 'Swarovski'),
(14, 'jedwab'),
(15, 'satyna'),
(16, 'wełna'),
(17, 'nylon'),
(18, 'kamień'),
(19, 'Agat'),
(20, 'Akwamaryn'),
(21, 'Amazonit'),
(22, 'Ametryn '),
(23, 'Ametyst'),
(24, 'Amonit'),
(25, 'Angelit'),
(26, 'Apatyt'),
(27, 'Awenturyn'),
(28, 'Bronzyt'),
(29, 'Chalcedon'),
(30, 'Chalkopiryt'),
(31, 'Chryzokola'),
(32, 'Chryzopraz'),
(33, 'Cyrkon'),
(34, 'Cytryn'),
(35, 'Czaroit'),
(36, 'Diament'),
(37, 'Diopsyd'),
(38, 'Dumortieryt'),
(39, 'Fluoryt'),
(40, 'Granat'),
(41, 'Hematyt'),
(42, 'Herkimeryt'),
(43, 'Hemimorfit'),
(44, 'Howlit'),
(45, 'Iolit'),
(46, 'Jadeit'),
(47, 'Jaspis'),
(48, 'Kamień księżycowy'),
(49, 'Kamień słoneczny'),
(50, 'Karneol'),
(51, 'Kryształ górski'),
(52, 'Krzemień pasiasty'),
(53, 'Kunzyt'),
(54, 'Kwarc barwiony'),
(55, 'Kwarc dymny'),
(56, 'Kwarc kawowy'),
(57, 'Kwarc lemon'),
(58, 'Kwarc lodowy'),
(59, 'Kwarc mistyczny'),
(60, 'Kwarc niebieski'),
(61, 'Kwarc różowy'),
(62, 'Kwarc z rutylem'),
(63, 'Kwarc z turmalinem'),
(64, 'Kwarc tytanowany'),
(65, 'Kwarc szary'),
(66, 'Kyanit'),
(67, 'Labradoryt'),
(68, 'Lapis lazuli'),
(69, 'Larimar'),
(70, 'Larvikit'),
(71, 'Lawa wulkaniczna'),
(72, 'Lepidolit'),
(73, 'Malachit'),
(74, 'Masa perłowa'),
(75, 'Mokait'),
(76, 'Morganit'),
(77, 'Nefryt'),
(78, 'Obsydian'),
(79, 'Oliwin'),
(80, 'Onyks'),
(81, 'Opal'),
(82, 'Peridot'),
(83, 'Piryt'),
(84, 'Prehnit'),
(85, 'Riolit'),
(86, 'Rodochrozyt'),
(87, 'Rodonit'),
(88, 'Rubelit'),
(89, 'Rubin'),
(90, 'Rutyl'),
(91, 'Sardonyks'),
(92, 'Serpentynit'),
(93, 'Serafinit'),
(94, 'Sodalit'),
(95, 'Spinel'),
(96, 'Sugilit'),
(97, 'SzafirSpinel'),
(98, 'Szmaragd'),
(99, 'Tanzanit'),
(100, 'Tektyt'),
(101, 'Topaz'),
(102, 'Turkus'),
(103, 'Turmalin'),
(104, 'Tygrysie oko'),
(105, 'Zoisyt'),
(106, 'Minerał syntetyczny'),
(107, 'moherek'),
(108, 'Piasek pustyni');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `group-g`
--

CREATE TABLE `group-g` (
  `id_group` int(11) NOT NULL,
  `name-g` varchar(30) COLLATE utf8_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `group-g`
--

INSERT INTO `group-g` (`id_group`, `name-g`) VALUES
(1, 'Koraliki'),
(2, 'Półfabrykanty'),
(3, 'Biżuteria'),
(4, 'Pasmanteria'),
(5, 'Inne');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `history`
--

CREATE TABLE `history` (
  `id_his` int(11) NOT NULL,
  `id_user-h` int(11) NOT NULL,
  `id_act-h` int(11) NOT NULL,
  `description-h` text COLLATE utf8_polish_ci NOT NULL,
  `date-h` datetime NOT NULL,
  `query_before-h` text COLLATE utf8_polish_ci NOT NULL,
  `query_after-h` text COLLATE utf8_polish_ci NOT NULL,
  `modify-h` text COLLATE utf8_polish_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `history`
--

INSERT INTO `history` (`id_his`, `id_user-h`, `id_act-h`, `description-h`, `date-h`, `query_before-h`, `query_after-h`, `modify-h`) VALUES
(1, 10, 2, 'Zmaina nazwy koloru', '2021-10-01 17:10:15', 'UPDATE `color` SET `name-cl`= \'żółty\'  WHERE `id_col` = 2', 'UPDATE `color` SET `name-cl`= \'żółtyy\'  WHERE `id_col` = 2', ''),
(2, 10, 2, 'Zmaina nazwy koloru', '2021-10-01 17:18:16', 'UPDATE `color` SET `name-cl`= \'brązowy\'  WHERE `id_col` = 11', 'UPDATE `color` SET `name-cl`= \'brązowyy\'  WHERE `id_col` = 11', ''),
(3, 10, 2, 'Zmaina nazwy koloru', '2021-10-01 17:19:00', 'UPDATE `color` SET `name-cl`= \'brązowyy\'  WHERE `id_col` = 11', 'UPDATE `color` SET `name-cl`= \'brązowy\'  WHERE `id_col` = 11', ''),
(4, 10, 2, 'Zmaina nazwy koloru', '2021-10-01 20:23:01', 'UPDATE `color` SET `name-cl`= \'biały\'  WHERE `id_col` = 1', 'UPDATE `color` SET `name-cl`= \'białydbcjsiejc\'  WHERE `id_col` = 1', ''),
(5, 10, 4, 'Cofnięcie zmiany (id: 5)', '2021-10-01 20:51:58', 'UPDATE `color` SET `name-cl`= \'biały\'  WHERE `id_col` = 1', 'UPDATE `color` SET `name-cl`= \'białydbcjsiejc\'  WHERE `id_col` = 1', ''),
(6, 10, 4, 'Cofnięcie zmiany (id: 5)', '2021-10-01 20:52:18', 'UPDATE `color` SET `name-cl`= \'białydbcjsiejc\'  WHERE `id_col` = 1', 'UPDATE `color` SET `name-cl`= \'biały\'  WHERE `id_col` = 1', ''),
(7, 10, 4, 'Cofnięcie zmiany (id: 5)', '2021-10-01 20:52:37', 'UPDATE `color` SET `name-cl`= \'białydbcjsiejc\'  WHERE `id_col` = 1', 'UPDATE `color` SET `name-cl`= \'biały\'  WHERE `id_col` = 1', ''),
(8, 10, 5, 'Nowe logowanie użytkownika', '2021-10-02 11:57:51', 'UPDATE `login` SET `last_log` = \'2021-10-01 20:51:10.0\' WHERE `id_user-l` = 10', 'UPDATE `login` SET `last_log` = \'2021-10-01 20:51:10.0\' WHERE `id_user-l` = 10', ''),
(9, 10, 2, 'Zmaina nazwy koloru', '2021-10-02 11:58:08', 'UPDATE `color` SET `name-cl`= \'różowy\'  WHERE `id_col` = 5', 'UPDATE `color` SET `name-cl`= \'różowyefr3q4f\'  WHERE `id_col` = 5', ''),
(10, 10, 4, 'Cofnięcie zmiany (id: 10)', '2021-10-02 11:58:19', 'UPDATE `color` SET `name-cl`= \'różowyefr3q4f\'  WHERE `id_col` = 5', 'UPDATE `color` SET `name-cl`= \'różowy\'  WHERE `id_col` = 5', ''),
(11, 10, 5, 'Nowe logowanie użytkownika', '2021-10-02 12:01:08', 'UPDATE `login` SET `last_log` = \'2021-10-02 11:57:51.0\' WHERE `id_user-l` = 10', 'UPDATE `login` SET `last_log` = \'2021-10-02 11:57:51.0\' WHERE `id_user-l` = 10', ''),
(12, 10, 4, 'Cofnięcie zmiany (id: 11)', '2021-10-02 12:01:32', 'UPDATE `color` SET `name-cl`= \'różowy\'  WHERE `id_col` = 5', 'UPDATE `color` SET `name-cl`= \'różowyefr3q4f\'  WHERE `id_col` = 5', ''),
(13, 10, 4, 'Cofnięcie zmiany (id: 10)', '2021-10-02 12:01:53', 'UPDATE `color` SET `name-cl`= \'różowyefr3q4f\'  WHERE `id_col` = 5', 'UPDATE `color` SET `name-cl`= \'różowy\'  WHERE `id_col` = 5', ''),
(14, 11, 1, 'Dodanie nowego użytkownika', '2021-10-02 12:11:22', 'DELETE FROM `user` WHERE `id_user` = 11;DELETE FROM `login` WHERE `id_user-l` = 11;DELETE FROM `user-meta` WHERE `id_user_m` = 75; ', 'INSERT INTO `user` ( `user`, `rank`, `id_rank-u`, `name-u`, `surname-u`, `email`, `telephone`, `newsletter`, `register`) VALUES (true, false, NULL, \'Janina\', \'Wierzchołek\', \'ktostam@mail.com\', \'432-543-543\', true, \'2021-10-02 12:11:22\'); INSERT INTO `login` (`id_user-l`, `login`, `password`, `last_log`) VALUES (11, \'ktostam@mail.com\', \'zse4%RDX\', \'2021-10-02 12:11:22\');INSERT INTO `user-meta` (`logged`, `id_user-m`, `firm`, `name_firm`, `nip_firm`, `firm_email`, `firm_tel`, `adr_str`, `adr_nr`, `adr_town`, `adr_state`, `adr_code`, `adr_post`, `adr_count`) VALUES (true, 11, false, \'null\', \'null\', null, \'null\', \'Warcząca trójka\', \'3/7\', \'Chełm\', \'Mazowieckie\', \'43-235\', \'Chełm\', \'Polska\');', ''),
(15, 11, 5, 'Nowe logowanie użytkownika', '2021-10-02 12:11:45', 'UPDATE `login` SET `last_log` = \'2021-10-02 12:11:22.0\' WHERE `id_user-l` = 11', 'UPDATE `login` SET `last_log` = \'2021-10-02 12:11:22.0\' WHERE `id_user-l` = 11', ''),
(16, 10, 5, 'Nowe logowanie użytkownika', '2021-10-02 12:21:43', 'UPDATE `login` SET `last_log` = \'2021-10-02 12:01:08.0\' WHERE `id_user-l` = 10', 'UPDATE `login` SET `last_log` = \'2021-10-02 12:01:08.0\' WHERE `id_user-l` = 10', ''),
(17, 10, 5, 'Nowe logowanie użytkownika', '2021-10-02 20:24:29', 'UPDATE `login` SET `last_log` = \'2021-10-02 12:21:43.0\' WHERE `id_user-l` = 10', 'UPDATE `login` SET `last_log` = \'2021-10-02 12:21:43.0\' WHERE `id_user-l` = 10', ''),
(18, 10, 2, 'Zmaina nazwy koloru', '2021-10-02 20:24:48', 'UPDATE `color` SET `name-cl`= \'fioletowy\'  WHERE `id_col` = 6', 'UPDATE `color` SET `name-cl`= \'fioletowyy\'  WHERE `id_col` = 6', ''),
(19, 10, 4, 'Cofnięcie zmiany (id: 19)', '2021-10-02 20:25:03', 'UPDATE `color` SET `name-cl`= \'fioletowyy\'  WHERE `id_col` = 6', 'UPDATE `color` SET `name-cl`= \'fioletowy\'  WHERE `id_col` = 6', ''),
(20, 10, 5, 'Nowe logowanie użytkownika', '2021-10-03 20:20:58', 'UPDATE `login` SET `last_log` = \'2021-10-02 20:24:29.0\' WHERE `id_user-l` = 10', 'UPDATE `login` SET `last_log` = \'2021-10-02 20:24:29.0\' WHERE `id_user-l` = 10', ''),
(21, 10, 5, 'Nowe logowanie użytkownika', '2021-10-04 14:07:45', 'UPDATE `login` SET `last_log` = \'2021-10-03 20:20:58.0\' WHERE `id_user-l` = 10', 'UPDATE `login` SET `last_log` = \'2021-10-03 20:20:58.0\' WHERE `id_user-l` = 10', ''),
(22, 10, 5, 'Nowe logowanie użytkownika', '2021-10-04 14:58:15', 'UPDATE `login` SET `last_log` = \'2021-10-04 14:07:45.0\' WHERE `id_user-l` = 10', 'UPDATE `login` SET `last_log` = \'2021-10-04 14:07:45.0\' WHERE `id_user-l` = 10', ''),
(23, 10, 1, 'Dodanie nowego produktu', '2021-10-04 15:01:05', 'DELETE FROM `product` WHERE `id_prod` = 30;DELETE FROM `product-meta` WHERE `id_prod-m` = 30', 'INSERT INTO `product`(`name-p`, `images-p`, `id_catag-p`, `price-p`) VALUES (\'SOWLIT SYNTETYCZNY GRANATOWY KULA GŁADKA OK. 10MM\', 0, 88, 15.0);INSERT INTO `product-meta`(`id_prod-m`, `height`, `width`, `lenght`, `hole`, `weight`, `diameter`, `id_fabr-m`, `id_shap-m`, `id_col-m`, `description`, `quantity-state`, `quantity-m`, ` vat-m`, `discount-m`, `cerate-m`, `restock-m`, `source`) VALUES (30, 0.0, 0.0, 0.0,  0.8, 0.0, 10.0, 106, 10, 8, \'Syntetyczny howlit, kula gładka, odcienie granatu. Sznur wybierany jest losowo. Cena dotyczy jednego sznura o długości ok. 39 cm. Polecamy do tworzenia bransoletek i naszyjników.\', 4214, 1,  0, 70, \'2021-10-04 15:01:05\', \'null\', \'https://kamieniolomy.pl/kamienie-syntetyczne/howlit-syntetyczny-granatowy-kula-gladka-ok.-10mm\')', ''),
(24, 10, 2, 'Modyfikacja produktu', '2021-10-04 15:01:58', 'UPDATE `product-meta`,`product` SET `name-p`=\'SOWLIT SYNTETYCZNY GRANATOWY KULA GŁADKA OK. 10MM\', `images-p`=0, `id_catag-p`=88, `price-p`=15.0, `height`=0.0, `width`=0.0, `lenght`=0.0, `hole`=0.8,`weight`=0.0, `diameter`=10.0, `id_fabr-m`=106, `id_shap-m`=10, `id_col-m`=8,`description`=\'Syntetyczny howlit, kula gładka, odcienie granatu. Sznur wybierany jest losowo. Cena dotyczy jednego sznura o długości ok. 39 cm. Polecamy do tworzenia bransoletek i naszyjników.\', `quantity-state`=4214, `quantity-m`=1, `discount-m`=70, `cerate-m`=\'2021-10-04 15:01:05.0\', `source`=\'https://kamieniolomy.pl/kamienie-syntetyczne/howlit-syntetyczny-granatowy-kula-gladka-ok.-10mm\', `restock-m`=\'null\' WHERE `id_prod`=`id_prod-m` AND `id_prod`=30', 'UPDATE `product-meta`,`product` SET `name-p`=\'HOWLIT SYNTETYCZNY GRANATOWY KULA GŁADKA OK. 10MM\', `images-p`=0, `id_catag-p`=88, `price-p`=15.0, `height`=0.0, `width`=0.0, `lenght`=0.0, `hole`=0.8,`weight`=0.0, `diameter`=10.0, `id_fabr-m`=106, `id_shap-m`=10, `id_col-m`=8,`description`=\'Syntetyczny howlit, kula gładka, odcienie granatu. Sznur wybierany jest losowo. Cena dotyczy jednego sznura o długości ok. 39 cm. Polecamy do tworzenia bransoletek i naszyjników.\', `quantity-state`=4214, `quantity-m`=1, `discount-m`=70, `cerate-m`=\'null\', `source`=\'https://kamieniolomy.pl/kamienie-syntetyczne/howlit-syntetyczny-granatowy-kula-gladka-ok.-10mm\', `restock-m`=\'null\' WHERE `id_prod`=`id_prod-m` AND `id_prod`=0', ''),
(25, 10, 1, 'Dodanie nowego produktu', '2021-10-04 15:04:53', 'DELETE FROM `product` WHERE `id_prod` = 31;DELETE FROM `product-meta` WHERE `id_prod-m` = 31', 'INSERT INTO `product`(`name-p`, `images-p`, `id_catag-p`, `price-p`) VALUES (\'HOWLIT SYNTETYCZNY RÓŻOWY KULA GŁADKA OK. 10MM\', 0, 88, 15.0);INSERT INTO `product-meta`(`id_prod-m`, `height`, `width`, `lenght`, `hole`, `weight`, `diameter`, `id_fabr-m`, `id_shap-m`, `id_col-m`, `description`, `quantity-state`, `quantity-m`, ` vat-m`, `discount-m`, `cerate-m`, `restock-m`, `source`) VALUES (31, 0.0, 0.0, 0.0,  0.8, 0.0, 10.0, 106, 10, 5, \'Syntetyczny howlit, kula gładka, odcienie różu Sznur wybierany jest losowo. Cena dotyczy jednego sznura o długości ok. 39 cm. Polecamy do tworzenia bransoletek i naszyjników.\', 4154, 1,  0, 70, \'2021-10-04 15:04:53\', \'null\', \'https://kamieniolomy.pl/kamienie-syntetyczne/howlit-syntetyczny-rozowy-kula-gladka-ok.-10mm_0\')', ''),
(26, 10, 5, 'Nowe logowanie użytkownika', '2021-10-04 15:16:05', 'UPDATE `login` SET `last_log` = \'2021-10-04 14:58:15.0\' WHERE `id_user-l` = 10', 'UPDATE `login` SET `last_log` = \'2021-10-04 14:58:15.0\' WHERE `id_user-l` = 10', ''),
(27, 10, 2, 'Modyfikacja produktu', '2021-10-04 15:16:58', 'UPDATE `product-meta`,`product` SET `name-p`=\'HOWLIT SYNTETYCZNY RÓŻOWY KULA GŁADKA OK. 10MM\', `images-p`=2, `id_catag-p`=88, `price-p`=15.0, `height`=0.0, `width`=0.0, `lenght`=0.0, `hole`=0.8,`weight`=0.0, `diameter`=10.0, `id_fabr-m`=106, `id_shap-m`=10, `id_col-m`=5,`description`=\'Syntetyczny howlit, kula gładka, odcienie różu Sznur wybierany jest losowo. Cena dotyczy jednego sznura o długości ok. 39 cm. Polecamy do tworzenia bransoletek i naszyjników.\', `quantity-state`=4154, `quantity-m`=1, `discount-m`=70, `cerate-m`=\'2021-10-04 15:04:53.0\', `source`=\'https://kamieniolomy.pl/kamienie-syntetyczne/howlit-syntetyczny-rozowy-kula-gladka-ok.-10mm_0\', `restock-m`=\'null\' WHERE `id_prod`=`id_prod-m` AND `id_prod`=31', 'UPDATE `product-meta`,`product` SET `name-p`=\'HOWLIT SYNTETYCZNY RÓŻOWY KULA GŁADKA OK. 10MM\', `images-p`=2, `id_catag-p`=88, `price-p`=15.0, `height`=0.0, `width`=0.0, `lenght`=0.0, `hole`=0.8,`weight`=0.0, `diameter`=10.0, `id_fabr-m`=106, `id_shap-m`=10, `id_col-m`=5,`description`=\'Syntetyczny howlit, kula gładka, odcienie różu Sznur wybierany jest losowo. Cena dotyczy jednego sznura o długości ok. 39 cm. Polecamy do tworzenia bransoletek i naszyjników.\', `quantity-state`=4154, `quantity-m`=1, `discount-m`=30, `cerate-m`=\'null\', `source`=\'https://kamieniolomy.pl/kamienie-syntetyczne/howlit-syntetyczny-rozowy-kula-gladka-ok.-10mm_0\', `restock-m`=\'null\' WHERE `id_prod`=`id_prod-m` AND `id_prod`=0', ''),
(28, 10, 2, 'Modyfikacja produktu', '2021-10-04 15:17:05', 'UPDATE `product-meta`,`product` SET `name-p`=\'HOWLIT SYNTETYCZNY GRANATOWY KULA GŁADKA OK. 10MM\', `images-p`=2, `id_catag-p`=88, `price-p`=15.0, `height`=0.0, `width`=0.0, `lenght`=0.0, `hole`=0.8,`weight`=0.0, `diameter`=10.0, `id_fabr-m`=106, `id_shap-m`=10, `id_col-m`=8,`description`=\'Syntetyczny howlit, kula gładka, odcienie granatu. Sznur wybierany jest losowo. Cena dotyczy jednego sznura o długości ok. 39 cm. Polecamy do tworzenia bransoletek i naszyjników.\', `quantity-state`=4214, `quantity-m`=1, `discount-m`=70, `cerate-m`=\'2021-10-04 15:01:05.0\', `source`=\'https://kamieniolomy.pl/kamienie-syntetyczne/howlit-syntetyczny-granatowy-kula-gladka-ok.-10mm\', `restock-m`=\'null\' WHERE `id_prod`=`id_prod-m` AND `id_prod`=30', 'UPDATE `product-meta`,`product` SET `name-p`=\'HOWLIT SYNTETYCZNY GRANATOWY KULA GŁADKA OK. 10MM\', `images-p`=2, `id_catag-p`=88, `price-p`=15.0, `height`=0.0, `width`=0.0, `lenght`=0.0, `hole`=0.8,`weight`=0.0, `diameter`=10.0, `id_fabr-m`=106, `id_shap-m`=10, `id_col-m`=8,`description`=\'Syntetyczny howlit, kula gładka, odcienie granatu. Sznur wybierany jest losowo. Cena dotyczy jednego sznura o długości ok. 39 cm. Polecamy do tworzenia bransoletek i naszyjników.\', `quantity-state`=4214, `quantity-m`=1, `discount-m`=30, `cerate-m`=\'null\', `source`=\'https://kamieniolomy.pl/kamienie-syntetyczne/howlit-syntetyczny-granatowy-kula-gladka-ok.-10mm\', `restock-m`=\'null\' WHERE `id_prod`=`id_prod-m` AND `id_prod`=0', ''),
(29, 10, 5, 'Nowe logowanie użytkownika', '2021-10-04 15:47:56', 'UPDATE `login` SET `last_log` = \'2021-10-04 15:16:05.0\' WHERE `id_user-l` = 10', 'UPDATE `login` SET `last_log` = \'2021-10-04 15:16:05.0\' WHERE `id_user-l` = 10', ''),
(30, 10, 5, 'Nowe logowanie użytkownika', '2021-10-04 19:32:31', 'UPDATE `login` SET `last_log` = \'2021-10-04 15:47:56.0\' WHERE `id_user-l` = 10', 'UPDATE `login` SET `last_log` = \'2021-10-04 15:47:56.0\' WHERE `id_user-l` = 10', ''),
(31, 10, 2, 'Modyfikacja produktu', '2021-10-04 19:33:01', 'UPDATE `product-meta`,`product` SET `name-p`=\'AGAT FUKSJA FASETOWANE KULE 6 MM - SZNUR\', `images-p`=2, `id_catag-p`=1, `price-p`=16.8, `height`=0.0, `width`=0.0, `lenght`=0.0, `hole`=0.4,`weight`=0.0, `diameter`=6.0, `id_fabr-m`=19, `id_shap-m`=10, `id_col-m`=6,`description`=\'Naturalny agat. 1 sznur o długości 36 cm = +/- 60 koralików\', `quantity-state`=27, `quantity-m`=1, `discount-m`=0, `cerate-m`=\'2021-08-27 13:05:00.0\', `source`=\'https://www.kianit.pl/agat-fuksja-fasetowane-kule-6-mm-sznur-id-2291\', `restock-m`=\'null\' WHERE `id_prod`=`id_prod-m` AND `id_prod`=2', 'UPDATE `product-meta`,`product` SET `name-p`=\'AGAT FUKSJA FASETOWANE KULE 6 MM - SZNUR\', `images-p`=2, `id_catag-p`=1, `price-p`=16.8, `height`=0.0, `width`=0.0, `lenght`=0.0, `hole`=0.4,`weight`=0.0, `diameter`=6.0, `id_fabr-m`=19, `id_shap-m`=10, `id_col-m`=4,`description`=\'Naturalny agat. 1 sznur o długości 36 cm = +/- 60 koralików\', `quantity-state`=27, `quantity-m`=1, `discount-m`=0, `cerate-m`=\'null\', `source`=\'https://www.kianit.pl/agat-fuksja-fasetowane-kule-6-mm-sznur-id-2291\', `restock-m`=\'null\' WHERE `id_prod`=`id_prod-m` AND `id_prod`=0', ''),
(32, 10, 2, 'Modyfikacja produktu', '2021-10-04 19:33:45', 'UPDATE `product-meta`,`product` SET `name-p`=\'AKWAMARYN FASETOWANE KAMIENIE - SZNUR 19 CM\', `images-p`=1, `id_catag-p`=2, `price-p`=39.5, `height`=0.0, `width`=0.0, `lenght`=0.0, `hole`=1.0,`weight`=0.0, `diameter`=11.0, `id_fabr-m`=20, `id_shap-m`=10, `id_col-m`=7,`description`=\'1 sznur o długości 19 cm\', `quantity-state`=10, `quantity-m`=1, `discount-m`=0, `cerate-m`=\'2021-08-27 14:09:46.0\', `source`=\'https://www.kianit.pl/akwamaryn-fasetowane-kamienie-sznur-19-cm-id-4504\', `restock-m`=\'null\' WHERE `id_prod`=`id_prod-m` AND `id_prod`=5', 'UPDATE `product-meta`,`product` SET `name-p`=\'AKWAMARYN FASETOWANE KAMIENIE - SZNUR 19 CM\', `images-p`=1, `id_catag-p`=2, `price-p`=39.5, `height`=0.0, `width`=0.0, `lenght`=0.0, `hole`=1.0,`weight`=0.0, `diameter`=11.0, `id_fabr-m`=20, `id_shap-m`=16, `id_col-m`=7,`description`=\'1 sznur o długości 19 cm\', `quantity-state`=10, `quantity-m`=1, `discount-m`=0, `cerate-m`=\'null\', `source`=\'https://www.kianit.pl/akwamaryn-fasetowane-kamienie-sznur-19-cm-id-4504\', `restock-m`=\'null\' WHERE `id_prod`=`id_prod-m` AND `id_prod`=0', ''),
(33, 10, 2, 'Modyfikacja produktu', '2021-10-04 19:34:06', 'UPDATE `product-meta`,`product` SET `name-p`=\'AKWAMARYN FASETOWANE KAMIENIE - SZNUR 19 CM\', `images-p`=2, `id_catag-p`=2, `price-p`=39.5, `height`=0.0, `width`=0.0, `lenght`=0.0, `hole`=1.0,`weight`=0.0, `diameter`=11.0, `id_fabr-m`=20, `id_shap-m`=10, `id_col-m`=7,`description`=\'1 sznur o długości 19 cm\', `quantity-state`=10, `quantity-m`=1, `discount-m`=0, `cerate-m`=\'2021-08-27 14:09:46.0\', `source`=\'https://www.kianit.pl/akwamaryn-fasetowane-kamienie-sznur-19-cm-id-4504\', `restock-m`=\'null\' WHERE `id_prod`=`id_prod-m` AND `id_prod`=6', 'UPDATE `product-meta`,`product` SET `name-p`=\'AKWAMARYN FASETOWANE KAMIENIE - SZNUR 19 CM\', `images-p`=2, `id_catag-p`=2, `price-p`=39.5, `height`=0.0, `width`=0.0, `lenght`=0.0, `hole`=1.0,`weight`=0.0, `diameter`=11.0, `id_fabr-m`=20, `id_shap-m`=11, `id_col-m`=7,`description`=\'1 sznur o długości 19 cm\', `quantity-state`=10, `quantity-m`=1, `discount-m`=0, `cerate-m`=\'null\', `source`=\'https://www.kianit.pl/akwamaryn-fasetowane-kamienie-sznur-19-cm-id-4504\', `restock-m`=\'null\' WHERE `id_prod`=`id_prod-m` AND `id_prod`=0', ''),
(34, 10, 2, 'Modyfikacja produktu', '2021-10-04 19:34:52', 'UPDATE `product-meta`,`product` SET `name-p`=\'FLUORYT ZŁOTY - 19,19 MM / 19,19 MM\', `images-p`=2, `id_catag-p`=21, `price-p`=345.0, `height`=15.92, `width`=19.19, `lenght`=19.19, `hole`=0.0,`weight`=7.2, `diameter`=0.0, `id_fabr-m`=39, `id_shap-m`=24, `id_col-m`=16,`description`=\'Piękny Golden Fluorite - Złoty Fluoryt. Powierzchnia niesamowicie ręcznie fasetowana. Idealny do pierścionka lub wisiorka. Prawdziwy okaz.\', `quantity-state`=1, `quantity-m`=1, `discount-m`=70, `cerate-m`=\'2021-08-27 15:14:01.0\', `source`=\'http://www.skarbynatury.pl/pl/p/Fluoryt-Zloty-19%2C19-mm-19%2C19-mm-1-szt/17812\', `restock-m`=\'null\' WHERE `id_prod`=`id_prod-m` AND `id_prod`=17', 'UPDATE `product-meta`,`product` SET `name-p`=\'FLUORYT ZŁOTY - 19,19 MM / 19,19 MM\', `images-p`=2, `id_catag-p`=21, `price-p`=345.0, `height`=15.92, `width`=19.19, `lenght`=19.19, `hole`=0.0,`weight`=7.2, `diameter`=0.0, `id_fabr-m`=39, `id_shap-m`=37, `id_col-m`=16,`description`=\'Piękny Golden Fluorite - Złoty Fluoryt. Powierzchnia niesamowicie ręcznie fasetowana. Idealny do pierścionka lub wisiorka. Prawdziwy okaz.\', `quantity-state`=1, `quantity-m`=1, `discount-m`=70, `cerate-m`=\'null\', `source`=\'http://www.skarbynatury.pl/pl/p/Fluoryt-Zloty-19%2C19-mm-19%2C19-mm-1-szt/17812\', `restock-m`=\'null\' WHERE `id_prod`=`id_prod-m` AND `id_prod`=0', ''),
(35, 10, 2, 'Modyfikacja produktu', '2021-10-04 20:47:07', 'UPDATE `product-meta`,`product` SET `name-p`=\'Krysztal gorski brylki\', `images-p`=0, `id_catag-p`=33, `price-p`=20.55, `height`=0.0, `width`=0.0, `lenght`=0.0, `hole`=0.0,`weight`=0.0, `diameter`=10.0, `id_fabr-m`=1, `id_shap-m`=1, `id_col-m`=1,`description`=\'costam\', `quantity-state`=24, `quantity-m`=1, `discount-m`=0, `cerate-m`=\'2021-08-31 11:37:00.0\', `source`=\'5\', `restock-m`=\'2017-06-01 08:30:00.0\' WHERE `id_prod`=`id_prod-m` AND `id_prod`=21', 'UPDATE `product-meta`,`product` SET `name-p`=\'KRYSZTAŁ GÓRSKI BRYŁKA FASETOWANA OK.8MM\', `images-p`=0, `id_catag-p`=33, `price-p`=44.95, `height`=8.0, `width`=7.0, `lenght`=0.0, `hole`=0.8,`weight`=0.0, `diameter`=0.0, `id_fabr-m`=51, `id_shap-m`=2, `id_col-m`=2,`description`=\'Kryształ górski, baryłka fasetowana. Kamień transparentny. Sznur wybierany jest losowo. Cena dotyczy jednego sznura o długości ok. 38 cm. Polecamy do tworzenia bransoletek i naszyjników\', `quantity-state`=24, `quantity-m`=1, `discount-m`=0, `cerate-m`=\'null\', `source`=\'https://kamieniolomy.pl/pl/p/Krysztal-gorski-barylka-fasetowana-ok.-8x7mm/23066406\', `restock-m`=\'null\' WHERE `id_prod`=`id_prod-m` AND `id_prod`=0', ''),
(36, 10, 2, 'Modyfikacja produktu', '2021-10-04 20:49:51', 'UPDATE `product-meta`,`product` SET `name-p`=\'KRYSZTAŁ GÓRSKI BRYŁKA FASETOWANA OK.8MM\', `images-p`=0, `id_catag-p`=33, `price-p`=44.95, `height`=8.0, `width`=7.0, `lenght`=0.0, `hole`=0.8,`weight`=0.0, `diameter`=0.0, `id_fabr-m`=51, `id_shap-m`=2, `id_col-m`=2,`description`=\'Kryształ górski, baryłka fasetowana. Kamień transparentny. Sznur wybierany jest losowo. Cena dotyczy jednego sznura o długości ok. 38 cm. Polecamy do tworzenia bransoletek i naszyjników\', `quantity-state`=24, `quantity-m`=1, `discount-m`=0, `cerate-m`=\'2021-08-31 11:37:00.0\', `source`=\'https://kamieniolomy.pl/pl/p/Krysztal-gorski-barylka-fasetowana-ok.-8x7mm/23066406\', `restock-m`=\'2017-06-01 08:30:00.0\' WHERE `id_prod`=`id_prod-m` AND `id_prod`=21', 'UPDATE `product-meta`,`product` SET `name-p`=\'KRYSZTAŁ GÓRSKI BRYŁKA FASETOWANA OK.8MM\', `images-p`=0, `id_catag-p`=33, `price-p`=45.0, `height`=8.0, `width`=7.0, `lenght`=0.0, `hole`=0.8,`weight`=0.0, `diameter`=0.0, `id_fabr-m`=51, `id_shap-m`=2, `id_col-m`=2,`description`=\'Kryształ górski, baryłka fasetowana. Kamień transparentny. Sznur wybierany jest losowo. Cena dotyczy jednego sznura o długości ok. 38 cm. Polecamy do tworzenia bransoletek i naszyjników\', `quantity-state`=24, `quantity-m`=1, `discount-m`=0, `cerate-m`=\'null\', `source`=\'https://kamieniolomy.pl/pl/p/Krysztal-gorski-barylka-fasetowana-ok.-8x7mm/23066406\', `restock-m`=\'null\' WHERE `id_prod`=`id_prod-m` AND `id_prod`=0', ''),
(37, 10, 2, 'Modyfikacja produktu', '2021-10-04 20:49:59', 'UPDATE `product-meta`,`product` SET `name-p`=\'KRYSZTAŁ GÓRSKI BRYŁKA FASETOWANA OK.8MM\', `images-p`=0, `id_catag-p`=33, `price-p`=45.0, `height`=8.0, `width`=7.0, `lenght`=0.0, `hole`=0.8,`weight`=0.0, `diameter`=0.0, `id_fabr-m`=51, `id_shap-m`=2, `id_col-m`=2,`description`=\'Kryształ górski, baryłka fasetowana. Kamień transparentny. Sznur wybierany jest losowo. Cena dotyczy jednego sznura o długości ok. 38 cm. Polecamy do tworzenia bransoletek i naszyjników\', `quantity-state`=24, `quantity-m`=1, `discount-m`=0, `cerate-m`=\'2021-08-31 11:37:00.0\', `source`=\'https://kamieniolomy.pl/pl/p/Krysztal-gorski-barylka-fasetowana-ok.-8x7mm/23066406\', `restock-m`=\'2017-06-01 08:30:00.0\' WHERE `id_prod`=`id_prod-m` AND `id_prod`=21', 'UPDATE `product-meta`,`product` SET `name-p`=\'KRYSZTAŁ GÓRSKI BRYŁKA FASETOWANA OK.8x7MM\', `images-p`=0, `id_catag-p`=33, `price-p`=45.0, `height`=8.0, `width`=7.0, `lenght`=0.0, `hole`=0.8,`weight`=0.0, `diameter`=0.0, `id_fabr-m`=51, `id_shap-m`=2, `id_col-m`=2,`description`=\'Kryształ górski, baryłka fasetowana. Kamień transparentny. Sznur wybierany jest losowo. Cena dotyczy jednego sznura o długości ok. 38 cm. Polecamy do tworzenia bransoletek i naszyjników\', `quantity-state`=24, `quantity-m`=1, `discount-m`=0, `cerate-m`=\'null\', `source`=\'https://kamieniolomy.pl/pl/p/Krysztal-gorski-barylka-fasetowana-ok.-8x7mm/23066406\', `restock-m`=\'null\' WHERE `id_prod`=`id_prod-m` AND `id_prod`=0', ''),
(38, 10, 5, 'Nowe logowanie użytkownika', '2021-10-06 22:32:54', 'UPDATE `login` SET `last_log` = \'2021-10-04 19:32:31.0\' WHERE `id_user-l` = 10', 'UPDATE `login` SET `last_log` = \'2021-10-04 19:32:31.0\' WHERE `id_user-l` = 10', ''),
(39, 2, 5, 'Nowe logowanie użytkownika', '2021-10-06 22:35:07', 'UPDATE `login` SET `last_log` = \'2021-09-28 18:48:56.0\' WHERE `id_user-l` = 2', 'UPDATE `login` SET `last_log` = \'2021-09-28 18:48:56.0\' WHERE `id_user-l` = 2', ''),
(40, 3, 5, 'Nowe logowanie użytkownika', '2021-10-06 22:38:41', 'UPDATE `login` SET `last_log` = \'2021-10-01 14:05:22.0\' WHERE `id_user-l` = 3', 'UPDATE `login` SET `last_log` = \'2021-10-01 14:05:22.0\' WHERE `id_user-l` = 3', ''),
(41, 10, 5, 'Nowe logowanie użytkownika', '2021-10-06 22:40:00', 'UPDATE `login` SET `last_log` = \'2021-10-06 22:32:54.0\' WHERE `id_user-l` = 10', 'UPDATE `login` SET `last_log` = \'2021-10-06 22:32:54.0\' WHERE `id_user-l` = 10', ''),
(42, 7, 5, 'Nowe logowanie użytkownika', '2021-10-06 22:42:06', 'UPDATE `login` SET `last_log` = \'2021-10-01 14:02:41.0\' WHERE `id_user-l` = 7', 'UPDATE `login` SET `last_log` = \'2021-10-01 14:02:41.0\' WHERE `id_user-l` = 7', ''),
(43, 1, 5, 'Nowe logowanie użytkownika', '2021-10-19 12:50:03', 'UPDATE `login` SET `last_log` = \'2021-10-19 12:49:31.0\' WHERE `id_user-l` = 1', 'UPDATE `login` SET `last_log` = \'2021-10-19 12:49:31.0\' WHERE `id_user-l` = 1', NULL),
(44, 1, 1, 'Dodanie nowego użytownika z hasłem', '2021-10-19 21:09:22', 'DELETE FROM `product` WHERE `id_prod` = 16', 'INSERT INTO `user` ( `user`, `rank`, `id_rank-u`, `name-u`, `surname-u`, `email`, `telephone`, `newsletter`, `register`) VALUES (true, false, 0, \'Daria\', Sroga, sd@wp.pl, 657-262-576, false, 2021-10-19T21:09)', 'imię: Daria,<br>nazwisko: Sroga,<br>email: sd@wp.pl, <br>telefon: 657-262-576'),
(45, 1, 5, 'Nowe logowanie użytkownika', '2021-10-19 21:09:57', 'UPDATE `login` SET `last_log` = \'2021-10-19 20:53:47.0\' WHERE `id_user-l` = 1', 'UPDATE `login` SET `last_log` = \'2021-10-19 20:53:47.0\' WHERE `id_user-l` = 1', NULL),
(46, 1, 1, 'Dodanie nowej grupy', '2021-10-22 17:32:56', 'DELETE FROM `group-g` WHERE `id_group` = 8', 'INSERT INTO `group-g`(`name-g`) VALUES (\'coś\')', 'nazwa: coś'),
(47, 1, 1, 'Dodanie nowej kategorii do grupy', '2021-10-22 17:32:56', 'DELETE FROM `group-g` WHERE `id_group` = 31', 'INSERT INTO `group-g`(`name-g`) VALUES (\'coś innego\')', NULL),
(48, 1, 5, 'Nowe logowanie użytkownika', '2021-10-22 17:39:20', 'UPDATE `login` SET `last_log` = \'2021-10-22 17:32:16.0\' WHERE `id_user-l` = 1', 'UPDATE `login` SET `last_log` = \'2021-10-22 17:32:16.0\' WHERE `id_user-l` = 1', NULL),
(49, 1, 1, 'Dodanie nowej kategorii', '2021-10-22 17:47:09', 'DELETE FROM `group-g` WHERE `id_group` = 32', 'INSERT INTO `group-g`(`name-g`) VALUES (\'jakaś\')', NULL),
(50, 2, 5, 'Nowe logowanie użytkownika', '2021-10-22 17:56:43', 'UPDATE `login` SET `last_log` = \'2021-10-06 22:35:07.0\' WHERE `id_user-l` = 2', 'UPDATE `login` SET `last_log` = \'2021-10-06 22:35:07.0\' WHERE `id_user-l` = 2', NULL),
(51, 1, 5, 'Nowe logowanie użytkownika', '2021-10-23 14:22:16', 'UPDATE `login` SET `last_log` = \'2021-10-23 14:17:08.0\' WHERE `id_user-l` = 1', 'UPDATE `login` SET `last_log` = \'2021-10-23 14:17:08.0\' WHERE `id_user-l` = 1', NULL);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `login`
--

CREATE TABLE `login` (
  `id_user-l` int(11) NOT NULL,
  `login` varchar(100) COLLATE utf8_polish_ci NOT NULL,
  `password` varchar(25) COLLATE utf8_polish_ci NOT NULL,
  `last_log` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `login`
--

INSERT INTO `login` (`id_user-l`, `login`, `password`, `last_log`) VALUES
(10, 'bleble@mail.com', '3edcvfr4', '2022-01-16 21:58:04'),
(8, 'cokolwiek@jakakolwiek.pl', 'cde3$RFV', '2022-01-15 20:13:29'),
(7, 'cosTam@jakas.com', 'nhy6%TGB', '2022-01-16 21:39:31'),
(21, 'elizabeth.g@wp.pl', 'vfr4%TGB', '2022-01-06 14:19:33'),
(6, 'example@email.com', 'xdr5^TFC', '2022-01-06 14:23:04'),
(2, 'example@example.com', 'HasłoToNieMasło', '2022-01-15 13:00:14'),
(27, 'eya86863@cuoly.com', '^A#2%Mf1]0mguZ0dxPY', '2021-12-28 14:44:34'),
(15, 'habad@gmail.com', '!NyZ4%[7IxHEeL', '2021-10-19 20:24:00'),
(28, 'isq83211@boofx.com', 'nscdoSFEW%3', '2021-12-28 16:22:19'),
(19, 'jakaskolwiek@domena.com', 'xsw2#EDC', '2021-11-14 17:53:08'),
(30, 'jakistam@gmail.com', '425af12a0743502b322e93a01', '0000-00-00 00:00:00'),
(23, 'jdb93193@boofx.com', 'jakieśHasło', '2021-12-28 13:53:18'),
(22, 'jwiesiek@wp.pl', 'L!re#4]G6DmLOF]&j^', '2021-11-16 17:21:45'),
(33, 'kolo@o2.pl', 'w*GlIlmM}U2O0', '2022-01-16 21:00:12'),
(11, 'ktostam@mail.com', 'zse4%RDX', '2021-10-29 19:03:40'),
(1, 'none@none.com', 'HasłoToJednakMasło', '2022-01-16 22:30:46'),
(20, 'olenka@wp.pl', 'HasłoToMasło', '2022-01-16 22:14:34'),
(16, 'sd@wp.pl', 'djYBX}YOC!#reyr', '2021-10-19 21:09:00'),
(17, 'worcia@wp.pl', 'YcAZJ6CiMw', '2021-10-26 19:24:00'),
(3, 'worek@gmail.com', 'HasłoToMasło', '2022-01-16 22:02:26');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `order-o`
--

CREATE TABLE `order-o` (
  `id_order` int(11) NOT NULL,
  `id_pay-o` int(11) NOT NULL,
  `id_user_m-o` int(11) NOT NULL,
  `id_del-o` int(11) NOT NULL,
  `deliv_nr-o` varchar(50) COLLATE utf8_polish_ci DEFAULT NULL,
  `id_stat-o` int(11) NOT NULL,
  `id_disc-o` int(11) DEFAULT NULL,
  `id_worker` int(11) DEFAULT NULL,
  `name-o` varchar(15) COLLATE utf8_polish_ci DEFAULT NULL,
  `surname-o` varchar(30) COLLATE utf8_polish_ci DEFAULT NULL,
  `email-o` varchar(100) COLLATE utf8_polish_ci DEFAULT NULL,
  `telephone-o` varchar(11) COLLATE utf8_polish_ci DEFAULT NULL,
  `vat-o` int(11) NOT NULL,
  `sum-o` float NOT NULL,
  `comments-o` varchar(200) COLLATE utf8_polish_ci DEFAULT NULL,
  `create-o` datetime NOT NULL,
  `update-o` datetime DEFAULT NULL,
  `send-o` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `order-o`
--

INSERT INTO `order-o` (`id_order`, `id_pay-o`, `id_user_m-o`, `id_del-o`, `deliv_nr-o`, `id_stat-o`, `id_disc-o`, `id_worker`, `name-o`, `surname-o`, `email-o`, `telephone-o`, `vat-o`, `sum-o`, `comments-o`, `create-o`, `update-o`, `send-o`) VALUES
(2, 1, 1, 2, NULL, 2, NULL, 3, 'Jadwiga', 'Poręba', 'j.por@wp.pl', '453-675-345', 23, 131, 'coś tam', '2021-09-01 12:27:08', '2021-09-17 01:54:20', NULL),
(4, 3, 1, 4, NULL, 5, 1, 3, 'Monika', 'Wierzchołek', 'w.jad@gmail.com', '231-631-532', 23, 61.4, 'Brak komentarzy', '2021-09-01 13:13:00', '2021-09-18 18:18:35', NULL),
(21, 10, 31, 5, 'DPD: 7283645186245728158', 6, NULL, 3, NULL, NULL, NULL, NULL, 23, 112.74, NULL, '2021-09-10 12:09:03', '2022-01-16 22:14:02', '2022-01-16 22:14:02'),
(22, 10, 31, 5, NULL, 3, NULL, 3, NULL, NULL, NULL, NULL, 23, 177.5, NULL, '2021-09-10 12:10:34', '2021-10-26 17:17:35', NULL),
(23, 10, 36, 5, NULL, 3, NULL, 3, 'Jacek', 'Florianski', 'florjanski@gmail.com', '343-234-254', 23, 15.2, 'Zapakowac razem', '2021-09-11 02:37:49', '2021-12-27 12:57:07', NULL),
(24, 10, 36, 5, NULL, 2, NULL, 11, 'Jacek', 'Florianski', 'florjanski@gmail.com', '343-234-254', 23, 205, 'Zapakowac razem', '2021-09-11 02:43:47', '2021-10-23 18:04:58', NULL),
(25, 10, 36, 5, '', 9, NULL, 3, 'Jacek', 'Florianski', 'florjanski@gmail.com', '343-234-254', 23, 0, 'Zapakowac razem', '2021-09-11 02:49:17', '2021-09-17 02:55:34', NULL),
(26, 10, 36, 5, NULL, 5, NULL, 3, 'Jacek', 'Florianski', 'florjanski@gmail.com', '343-234-254', 0, 35, 'Zapakowac razem', '2021-09-11 03:05:57', '2021-12-27 12:32:36', NULL),
(27, 10, 53, 5, NULL, 4, 1, 3, 'Janina', 'Kowalska', 'example@example.com', NULL, 23, 18.6, 'Cos tam', '2021-09-11 04:39:17', '2021-09-17 02:06:00', NULL),
(39, 11, 55, 5, 'DPD: 72836451862457281582', 7, 1, 3, 'Ludwik', 'Zamożny', 'zamoznyL@wp.pl', '234-354-475', 23, 12.4, 'Proszę bezpiecznie zapakować przesyłkę. Informacja dla kuriera: Zostawić przesyłkę przed domem w razie braku domowników.', '2021-09-13 12:58:15', '2021-12-27 12:42:17', NULL),
(42, 12, 71, 11, '', 9, NULL, 3, 'Dorota', 'Grudka', 'grudka1990@gmail.com', '234-243-452', 23, 63.6, '', '2021-09-16 10:52:22', '2021-09-17 02:55:48', '2021-09-26 14:53:48'),
(45, 10, 71, 2, NULL, 4, NULL, 3, 'Dorota', 'Grudka', 'grudka1990@gmail.com', '532-452-245', 23, 63.6, '', '2021-09-16 11:14:58', '2021-12-27 12:55:28', NULL),
(47, 5, 60, 2, NULL, 2, NULL, 11, 'Jan', 'Kowalski', 'example@emails.com', '213-453-457', 23, 36, 'Informacja dla kuriera: Proszę schować przesyłkę', '2021-09-18 22:16:11', '2021-10-23 18:11:27', NULL),
(48, 2, 74, 4, NULL, 2, NULL, 3, 'Ludmiła', 'Gardziołek', 'cokolwiek1@jgyr.pl', '214-624-437', 23, 67.2, '', '2021-09-18 23:04:21', '2022-01-16 22:11:38', NULL),
(50, 12, 76, 1, '', 9, 1, 11, 'Karolina', 'Wielmożna', 'wkaro@wp.pl', '234-242-452', 23, 58.4, NULL, '2021-10-06 21:03:07', '2021-10-23 17:47:06', NULL),
(52, 4, 77, 2, NULL, 3, NULL, 11, 'Marek', 'Dumny', 'dumny@wp.pl', '234-626-837', 23, 160, '', '2021-10-24 12:18:32', '2021-10-29 19:04:37', NULL),
(53, 5, 79, 2, NULL, 1, 1, NULL, 'Sylwia', 'Pozorny', 'pozornas@wp.pl', '342-433-544', 23, 68.5, 'Proszę o zostawienie paczki przed domem', '2021-10-26 15:00:55', NULL, NULL),
(54, 4, 80, 4, NULL, 3, 1, 3, 'Ludwika', 'Grudka', 'grudka1991@gmail.com', '676-556-576', 23, 87.5, 'Brak komentarza *upuszcza mikrofon*', '2021-10-29 18:43:35', '2022-01-13 20:52:39', NULL),
(55, 6, 61, 2, NULL, 5, NULL, 3, ' Jan', 'Kowalski', 'example@example.com', '213-453-458', 23, 81.3, NULL, '2021-10-31 13:06:50', '2021-12-27 12:28:14', NULL),
(56, 12, 87, 11, NULL, 1, NULL, NULL, 'Jacek', 'Florianski', 'florjanski@gmail.com', '543-574-374', 23, 59.52, NULL, '2021-11-01 20:25:20', NULL, NULL),
(57, 10, 87, 11, '', 9, NULL, 3, 'Jacek', 'Florianski', 'florjanski@gmail.com', '543-574-374', 23, 0, NULL, '2021-11-02 13:07:02', '2022-01-07 16:21:41', NULL),
(58, 10, 87, 11, NULL, 2, NULL, 3, 'Jacek', 'Florianski', 'florjanski@gmail.com', '576-576-468', 23, 68, NULL, '2021-11-02 13:13:50', '2021-12-27 12:53:09', NULL),
(59, 10, 73, 3, NULL, 1, 1, NULL, 'Leonard', 'Radzikowski', 'bleble@mail.com', '435-624-732', 23, 102.25, NULL, '2021-11-02 13:18:16', NULL, NULL),
(60, 10, 87, 2, NULL, 1, NULL, NULL, 'Jacek', 'Florianski', 'florjanski@gmail.com', '453-543-452', 23, 46.8, NULL, '2021-11-02 14:37:18', NULL, NULL),
(61, 12, 105, 1, NULL, 1, 1, NULL, 'Ludwik', 'Grudka', 'lgrudka@gmail.com', '434-513-543', 23, 6.05, NULL, '2021-12-30 14:09:39', NULL, NULL),
(62, 2, 53, 2, NULL, 1, 1, NULL, 'Jan', 'Kowalski', 'example@example.com', '213-453-458', 23, 62.22, 'Nie mam', '2021-12-30 14:46:58', NULL, NULL),
(63, 2, 76, 1, NULL, 1, 1, NULL, 'Karolina', 'Wielmożna', 'wkaro@wp.pl', '234-242-452', 23, 7.05, 'Do kuriera: Proszę zostawić paczkę na ganku. Z góry dziękuję', '2022-01-06 12:24:45', NULL, NULL),
(64, 10, 103, 2, NULL, 1, 1, NULL, 'Elżbieta', 'Oluś', 'olenka@wp.pl', '424-525-661', 23, 14.5, NULL, '2022-01-06 14:26:07', NULL, NULL),
(65, 12, 107, 1, NULL, 1, 1, NULL, 'Bożena', 'Wysocka', 'wysocb@gmail.com', '751-874-568', 23, 7.8, 'Proszę ładnie zapakować', '2022-01-16 19:43:32', NULL, NULL);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `order-product`
--

CREATE TABLE `order-product` (
  `id_order-op` int(11) NOT NULL,
  `id_prod-op` int(11) NOT NULL,
  `quantity-op` int(11) NOT NULL,
  `discount-op` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `order-product`
--

INSERT INTO `order-product` (`id_order-op`, `id_prod-op`, `quantity-op`, `discount-op`) VALUES
(21, 16, 4, 0),
(21, 14, 2, 70),
(21, 10, 1, 70),
(21, 1, 3, 0),
(21, 2, 1, 0),
(22, 16, 4, 0),
(22, 1, 3, 0),
(22, 2, 1, 0),
(22, 17, 1, 70),
(23, 7, 8, 0),
(24, 11, 3, 0),
(24, 15, 2, 0),
(26, 4, 2, 0),
(27, 1, 3, 0),
(39, 9, 2, 0),
(39, 16, 4, 0),
(2, 11, 2, 0),
(2, 13, 1, 0),
(4, 9, 1, 0),
(4, 15, 1, 0),
(45, 2, 2, 0),
(45, 3, 2, 70),
(47, 9, 15, 0),
(48, 2, 4, 0),
(52, 21, 3, 0),
(52, 16, 5, 0),
(53, 11, 1, 0),
(53, 6, 2, 0),
(53, 33, 1, 0),
(54, 4, 5, 0),
(55, 36, 7, 0),
(55, 34, 1, 0),
(56, 2, 2, 0),
(56, 14, 1, 70),
(58, 31, 4, 30),
(58, 8, 5, 0),
(59, 34, 2, 0),
(59, 11, 1, 0),
(59, 6, 1, 0),
(59, 4, 2, 0),
(60, 36, 2, 0),
(60, 34, 1, 0),
(61, 39, 11, 0),
(62, 2, 3, 0),
(62, 35, 2, 0),
(62, 6, 1, 0),
(62, 16, 2, 0),
(63, 40, 1, 0),
(63, 8, 1, 0),
(64, 33, 1, 0),
(65, 8, 3, 0);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `payment`
--

CREATE TABLE `payment` (
  `id_pay` int(11) NOT NULL,
  `name-py` text COLLATE utf8_polish_ci NOT NULL,
  `cat-py` varchar(20) COLLATE utf8_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `payment`
--

INSERT INTO `payment` (`id_pay`, `name-py`, `cat-py`) VALUES
(1, 'Visa', 'Karta płatnicza'),
(2, 'MasterCard', 'Karta płatnicza'),
(3, 'Maestro', 'Karta płatnicza'),
(4, 'Płatności Shopper', 'Platforma płatnicza'),
(5, 'PayPal', 'Platforma płatnicza'),
(6, 'PayU', 'Platforma płatnicza'),
(7, 'Dotpay', 'Platforma płatnicza'),
(8, 'Przelewy24', 'Platforma płatnicza'),
(9, 'BLIK', 'Platforma płatnicza'),
(10, 'Przelew na rachunek bankowy', 'Inne'),
(11, 'Za pobrniem', 'Inne'),
(12, 'Gotówka na miejscu', 'Inne'),
(13, 'Przedpłata na konto', 'Inne');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `product`
--

CREATE TABLE `product` (
  `id_prod` int(11) NOT NULL,
  `name-p` text COLLATE utf8_polish_ci NOT NULL,
  `images-p` int(11) NOT NULL,
  `id_catag-p` int(11) NOT NULL,
  `price-p` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `product`
--

INSERT INTO `product` (`id_prod`, `name-p`, `images-p`, `id_catag-p`, `price-p`) VALUES
(1, 'AGAT FASETOWANE KULE 4 MM - SZNUR (MIODOWY)', 1, 1, 12.4),
(2, 'AGAT FUKSJA FASETOWANE KULE 6 MM - SZNUR', 2, 1, 16.98),
(3, 'AGAT PŁONĄCY - KROPLA 27 MM / 18 MM', 2, 1, 50),
(4, 'AKWAMARYN BRYLKI - SZNUR 20 CM', 2, 2, 35),
(5, 'AKWAMARYN FASETOWANE KAMIENIE - SZNUR 19 CM', 1, 2, 39.5),
(6, 'AKWAMARYN FASETOWANE KAMIENIE - SZNUR 19 CM', 2, 2, 39.5),
(7, 'AMETYST KULE 4 MM - 10 SZT ', 1, 5, 3.8),
(8, 'AMETYST ZIELONY FASETOW. BRIOLETTE MIGDAŁ 10-9 MMM', 4, 5, 5.2),
(9, 'AMETYST FASETOWANE KULE 10 MM', 2, 5, 2.4),
(10, 'AMONIT - 31 MM / 22 MM', 2, 6, 39),
(11, 'Bronzyt kula matowa ok. 4,5mm', 2, 10, 29),
(12, 'Chalcedon niebieski kula 10mm (sznurek)', 2, 11, 200),
(13, 'Chalcedon niebieski kula 4mm (sznurek)', 1, 11, 73),
(14, 'CYRKON - KULA 4 MM LILA', 2, 15, 86.4),
(15, 'Diopsyd bryła nieregularna', 2, 19, 59),
(16, 'Dumortieryt Fasetowany kropla ', 1, 20, 5),
(17, 'FLUORYT ZŁOTY - 19,19 MM / 19,19 MM', 2, 21, 345),
(18, 'Fluoryt Fasetowany kulki 4 mm (sznur)', 1, 21, 36),
(20, 'Koral czerwony łzy nieregularne (sznur)', 2, 100, 45),
(21, 'KRYSZTAŁ GÓRSKI BRYŁKA FASETOWANA OK.8x7MM', 2, 33, 45),
(30, 'HOWLIT SYNTETYCZNY GRANATOWY KULA GŁADKA OK. 10MM', 2, 88, 15),
(31, 'HOWLIT SYNTETYCZNY RÓŻOWY KULA GŁADKA OK. 10MM', 2, 88, 15),
(32, 'CYTRYN - KULE 6 MM - SZNUR', 2, 16, 49.5),
(33, 'GRANAT KULE 6 MM - SZNUR', 1, 22, 29),
(34, 'PIASEK PUSTYNI KOSTKA OK.4-4,5MM', 4, 91, 33),
(35, 'OPALIT FIOLETOWY KULA GŁADKA OK. 6MM', 2, 93, 12),
(36, 'KYANIT FASETOWANE BRIOLETTE 9 - 8 MM ( IN 2)', 2, 48, 6.9),
(37, 'CYTRYN FASETOWANE BRIOLETTE - SZNUR', 4, 16, 129),
(39, 'KWARC SZARY FASETOWANA PASTYLKA 8 MM', 1, 47, 1.1),
(40, 'ONYKS MATOWY KULE 4 MM - SZNUR', 1, 62, 8.9),
(41, 'HEMATYT ZŁOTY DONUT TWISTOWANY - 2 SZT', 1, 23, 1.5),
(42, 'ZAWIESZKA ŁABĘDŹ SREBRO 925 (CH 56)', 1, 139, 6.5);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `product-meta`
--

CREATE TABLE `product-meta` (
  `id_prod-m` int(11) NOT NULL,
  `height` float DEFAULT NULL,
  `width` float DEFAULT NULL,
  `lenght` float DEFAULT NULL,
  `hole` float DEFAULT NULL,
  `weight` float DEFAULT NULL,
  `diameter` float DEFAULT NULL,
  `id_fabr-m` int(11) NOT NULL,
  `id_shap-m` int(11) NOT NULL,
  `id_col-m` int(11) NOT NULL,
  `description` text COLLATE utf8_polish_ci NOT NULL,
  `quantity-state` int(11) NOT NULL,
  `quantity-m` int(11) NOT NULL,
  `discount-m` int(11) DEFAULT NULL,
  `vat-m` int(11) NOT NULL DEFAULT 23,
  `cerate-m` datetime NOT NULL,
  `restock-m` datetime DEFAULT NULL,
  `source` text COLLATE utf8_polish_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `product-meta`
--

INSERT INTO `product-meta` (`id_prod-m`, `height`, `width`, `lenght`, `hole`, `weight`, `diameter`, `id_fabr-m`, `id_shap-m`, `id_col-m`, `description`, `quantity-state`, `quantity-m`, `discount-m`, `vat-m`, `cerate-m`, `restock-m`, `source`) VALUES
(2, 0, 0, 0, 0.4, 0, 6, 19, 10, 4, 'Naturalny agat. 1 sznur o długości 36 cm co równa się około 60 koralików.', 26, 1, 0, 23, '2021-08-27 13:05:00', NULL, 'https://www.kianit.pl/agat-fuksja-fasetowane-kule-6-mm-sznur-id-2291'),
(3, 27, 18, 0, 0.9, NULL, NULL, 19, 8, 3, 'Krople agatu płonącego o zabarwieniu biało pomarańczowym. Niektóre kamienie mają transparentną powierzchnię. Na powierzchni mogą występować naturalne otwory i spękania', 10, 5, 70, 23, '2021-08-27 13:19:40', '2021-10-26 18:15:01', 'http://www.skarbynatury.pl/pl/p/Agat-Plonacy-Kropla-27-mm-18-mm-zestaw-5-szt/14274'),
(4, 10, 10, 12, 1, NULL, NULL, 20, 35, 7, '1 sznur o długości 20 cm', 45, 1, 0, 23, '2021-08-27 13:21:57', NULL, 'https://www.kianit.pl/akwamaryn-brylki-sznur-20-cm-id-3916'),
(5, 0, 0, 0, 1, 0, 11, 20, 16, 7, '1 sznur o długości 19 cm', 10, 1, 0, 23, '2021-08-27 14:09:46', NULL, 'https://www.kianit.pl/akwamaryn-fasetowane-kamienie-sznur-19-cm-id-4504'),
(6, 0, 0, 0, 1, 0, 11, 20, 11, 7, '1 sznur o długości 19 cm', 10, 1, 0, 23, '2021-08-27 14:09:46', '2021-10-25 19:30:42', 'https://www.kianit.pl/akwamaryn-fasetowane-kamienie-sznur-19-cm-id-4504'),
(7, 0, 0, 0, 0.9, NULL, 4, 23, 10, 6, 'Ametystowe naturalne kule.', 4984, 10, 0, 23, '2021-08-27 14:23:10', NULL, 'https://www.kianit.pl/ametyst-kule-4-mm-10-szt-a--id-269'),
(8, 2, 8, 10, 0.5, 0, 0, 23, 14, 10, 'Naturalny zielony ametyst w formie briolette.', 247, 1, 0, 23, '2021-08-27 14:30:04', NULL, 'https://www.kianit.pl/ametyst-zielony-fasetow-briolette-migdal-10-9-mmm-id-4490'),
(9, 0, 0, 0, 1, NULL, 10, 23, 10, 10, 'Naturalny ametyst sprzedawany po jednej sztuce.', 2983, 1, 0, 23, '2021-08-27 14:34:46', NULL, 'https://www.kianit.pl/ametyst-fasetowane-kule-10-mm-id-2172'),
(10, 22, 31, 0, 0, NULL, 10, 24, 37, 2, 'Piękny amonit - bez otworu. Muszla jest doskonale zachowana. Posiadam tylko jeden taki egzemplarz.', 6, 1, 70, 23, '2021-08-27 14:37:54', '2022-01-16 21:50:38', 'http://www.skarbynatury.pl/pl/p/Amonit-31-mm-22-mm-1-szt/19745'),
(11, 0, 0, 0, 0.5, NULL, 4.5, 28, 10, 11, 'Bronzyt, kula matowa odcienie brązu. Sznur wybierany jest losowo. Cena dotyczy jednego sznura o długości ok. 38 cm.  Polecamy do tworzenia bransoletek i naszyjników.', 3997, 1, 0, 23, '2021-08-27 14:41:20', NULL, 'https://kamieniolomy.pl/kamienie-naturalne-sznury/bronzyt-kula-matowa-ok.-45-mm'),
(12, 0, 0, 0, 0.6, NULL, 10, 29, 10, 7, 'Naturalny Chalcedon, kulki. Minerał półszlachetny do biżuterii. Cena za sznurek. Długość: 39,5cm.', 37, 1, 0, 23, '2021-08-27 14:47:08', '2021-10-04 15:57:04', 'https://www.sklep.amberplanet.pl/p27466,chalcedon-niebieski-kula-10mm-sznurek.html'),
(13, 0, 0, 0, 1, NULL, 4, 29, 10, 7, 'Naturalny kamień półszlachetny Chalcedon. Najwyższej jakości minerały do produkcji biżuterii w formie kulek gładkich.', 54, 1, 0, 23, '2021-08-27 14:50:20', '2021-10-25 19:31:50', 'https://www.sklep.amberplanet.pl/p17487,chalcedon-niebieski-kula-4mm-sznurek-jakosc.html'),
(14, 0, 0, 0, 0.8, NULL, 4, 33, 10, 6, 'Powierzchnię pokryto drobną fasetą, która dodatkowo odbija światło.', 1, 96, 70, 23, '2021-08-27 14:52:48', NULL, 'http://www.skarbynatury.pl/pl/p/Cyrkonia-kula-4-mm-lila-zestaw-96-szt/13359'),
(15, 12, 7, 0, 1, NULL, 0, 37, 2, 10, 'Diopsyd, nieregularna bryła gładka. Sznur wybierany jest losowo. Cena dotyczy jednego sznura o długości ok. 40 cm. Polecamy do tworzenia bransoletek i naszyjników.', 0, 1, 0, 23, '2021-08-27 14:54:31', NULL, 'https://kamieniolomy.pl/kamienie-naturalne-sznury/diopsyd-bryla-gladka-ok.-7-12x5-7mm'),
(16, 15, 10, 0, 1.2, NULL, 0, 38, 8, 8, 'Naturalny Dumortieryt fasetowany, kropla. Minerał półszlachetny do robienia biżuterii.', 2021, 1, 0, 23, '2021-08-27 15:03:00', NULL, 'https://www.sklep.amberplanet.pl/p20637,dumortieryt-fasetowany-kropla-15x10mm-sztuka-lub-sznur.html'),
(17, 15.92, 19.19, 19.19, 0, 7.2, 0, 39, 37, 16, 'Piękny Golden Fluorite - Złoty Fluoryt. Powierzchnia niesamowicie ręcznie fasetowana. Idealny do pierścionka lub wisiorka. Prawdziwy okaz.', 0, 1, 70, 23, '2021-08-27 15:14:01', NULL, 'http://www.skarbynatury.pl/pl/p/Fluoryt-Zloty-19%2C19-mm-19%2C19-mm-1-szt/17812'),
(18, 0, 0, 0, 0.4, NULL, 4, 39, 10, 19, 'Naturalny Fluoryt Fasetowany, kulka. Kamień półszlachetny do tworzenia biżuterii.', 20, 1, 0, 23, '2021-08-27 15:27:18', NULL, 'https://www.sklep.amberplanet.pl/p32134,fluoryt-fasetowany-kulki-4-mm-sznur.html'),
(1, 4, 4, 360, 0.6, 0, 0, 19, 10, 11, '1 sznur o długości 36 cm', 10, 1, 0, 23, '2021-08-27 12:03:00', '2022-01-16 21:50:58', 'https://www.kianit.pl/agat-fasetowane-kule-4-mm-sznur-miodowy-id-78'),
(20, 17, 12, 6, 0.8, 0, 0, 2, 37, 4, 'Naturalny Koral czerwony, nieregularne łzy. Kamień jubilerski do biżuterii. Długość: ok. 40 cm.', 23, 1, 0, 23, '2021-08-31 10:49:00', NULL, 'https://www.sklep.amberplanet.pl/p32699,koral-czerwony-lzy-nieregularne-sznur.html'),
(21, 8, 7, 0, 0.8, 0, 0, 51, 2, 2, 'Kryształ górski, baryłka fasetowana. Kamień transparentny. Sznur wybierany jest losowo. Cena dotyczy jednego sznura o długości ok. 38 cm. Polecamy do tworzenia bransoletek i naszyjników', 21, 1, 0, 23, '2021-08-31 11:37:00', '2017-06-01 08:30:00', 'https://kamieniolomy.pl/pl/p/Krysztal-gorski-barylka-fasetowana-ok.-8x7mm/23066406'),
(30, 0, 0, 0, 0.8, 0, 10, 106, 10, 8, 'Syntetyczny howlit, kula gładka, odcienie granatu. Sznur wybierany jest losowo. Cena dotyczy jednego sznura o długości ok. 39 cm. Polecamy do tworzenia bransoletek i naszyjników.', 4214, 1, 30, 0, '2021-10-04 15:01:05', NULL, 'https://kamieniolomy.pl/kamienie-syntetyczne/howlit-syntetyczny-granatowy-kula-gladka-ok.-10mm'),
(31, 0, 0, 0, 0.8, 0, 10, 106, 10, 5, 'Syntetyczny howlit, kula gładka, odcienie różu Sznur wybierany jest losowo. Cena dotyczy jednego sznura o długości ok. 39 cm. Polecamy do tworzenia bransoletek i naszyjników.', 4150, 1, 30, 0, '2021-10-04 15:04:53', NULL, 'https://kamieniolomy.pl/kamienie-syntetyczne/howlit-syntetyczny-rozowy-kula-gladka-ok.-10mm_0'),
(32, 0, 0, 0, 0.9, 0, 6, 34, 10, 2, '1 sznur o długości 39,5 cm', 40, 1, 0, 0, '2021-10-26 12:03:01', NULL, 'https://www.kianit.pl/cytryn-kule-6-mm-sznur-id-3007'),
(33, 0, 0, 0, 1, 0, 6, 40, 10, 4, '1 sznur o długości 38 cm', 34, 1, 0, 0, '2021-10-26 12:07:28', NULL, 'https://www.kianit.pl/granat-kule-6-mm-sznur-id-2704'),
(34, 0, 0, 0, 0.8, 0, 4, 106, 7, 11, 'Piasek pustyni rodzaj syntetycznego szkła o kolorze połyskującego brązu, bardzo, bardzo popularny od wielu lat. Kamień ten mieni się błyszczącymi drobinkami, które zawdzięcza się opiłkom miedzi. Surowiec ten nie występuje w przyrodzie, jest wytwarzany sztucznie. Sznur wybierany jest losowo. Cena dotyczy jednego sznura o długości ok. 40 cm. Kamień syntetyczny idealnie nadający się na bransoletki i naszyjniki.', 19, 1, 0, 0, '2021-10-26 12:43:21', NULL, 'https://kamieniolomy.pl/kamienie-syntetyczne/piasek-pustyni-kostka-4-45mm-sznur-40-cm'),
(35, 0, 0, 0, 0.5, 0, 6, 106, 10, 6, 'Opal syntetyczny, kula gładka, kamień lekko transparentny w odcieniach fioletu Sznur wybierany jest losowo. Cena dotyczy jednego sznura o długości ok. 37 cm. Polecamy do tworzenia bransoletek i naszyjników.', 40, 1, 0, 0, '2021-10-26 12:50:35', NULL, 'https://kamieniolomy.pl/kamienie-syntetyczne/opalit-fioletowy-kula-gladka-ok.-6mm'),
(36, 0, 5, 9, 0.4, 0, 0, 66, 39, 7, 'Długość: 9 mm – 8 mm Szerokość: 5 mm - 4,5 mm', 43, 1, 0, 0, '2021-10-26 18:13:35', NULL, 'https://www.kianit.pl/kyanit-fasetowane-briolette-9-8-mm-in-2-id-4431'),
(37, 5.5, 5, 0, 0.4, 0, 0, 34, 39, 2, 'Ilość: 1 sznur o długości 17 cm, na sznurze jest ok. 55 briolettek.', 20, 1, 0, 0, '2021-12-28 11:58:08', NULL, 'https://www.kianit.pl/cytryn-fasetowane-briolette-sznur-id-4420'),
(39, 0, 3, 0, 1, 0, 8, 65, 29, 13, 'Ilość: 1 sztuka', 30, 1, 0, 0, '2021-12-28 12:24:27', '2021-12-28 13:18:21', 'https://www.kianit.pl/kwarc-szary-fasetowana-pastylka-8-mm-id-37'),
(40, 0, 0, 0, 0.9, 0, 4, 80, 10, 14, 'Ilość: 1 sznur o długości 38 cm. Kamienie mają matowe wykończenie.', 20, 1, 0, 0, '2021-12-28 12:41:42', NULL, 'https://www.kianit.pl/onyks-matowy-kule-4-mm-sznur-id-60'),
(41, 0, 4, 0, 1, 0, 12, 2, 37, 16, 'Ilość: 2 sztuki', 0, 2, 0, 0, '2021-12-28 12:48:51', NULL, 'https://www.kianit.pl/hematyt-zloty-donut-twistowany-2-szt-id-63'),
(42, 10, 11.5, 0, 1.5, 0, 0, 2, 37, 15, 'Nazwa: Zawieszka wykonana ze srebra próby 925; Ilość: 1 sztuka', 20, 1, 0, 0, '2022-01-16 12:18:39', NULL, 'https://www.kianit.pl/zawieszka-labedz-srebro-925-ch-56-id-85');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `rank`
--

CREATE TABLE `rank` (
  `id_rank` int(11) NOT NULL,
  `name-r` text COLLATE utf8_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `rank`
--

INSERT INTO `rank` (`id_rank`, `name-r`) VALUES
(1, 'Administrator'),
(2, 'Pracownik'),
(3, 'Korektor'),
(4, 'Zaopatrzeniowiec');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `review`
--

CREATE TABLE `review` (
  `id_rev` int(11) NOT NULL,
  `id_prod-rw` int(11) NOT NULL,
  `id_order-rw` int(11) NOT NULL,
  `name-rw` varchar(30) COLLATE utf8_polish_ci NOT NULL,
  `stars` int(11) NOT NULL,
  `content` varchar(1000) COLLATE utf8_polish_ci NOT NULL,
  `publication` datetime NOT NULL,
  `reply` text COLLATE utf8_polish_ci DEFAULT NULL,
  `response` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `review`
--

INSERT INTO `review` (`id_rev`, `id_prod-rw`, `id_order-rw`, `name-rw`, `stars`, `content`, `publication`, `reply`, `response`) VALUES
(1, 5, 2, 'Dobry produkt', 5, 'Piękne akwamaryny, jestem zadowolona z zakupu', '2021-09-02 11:17:14', 'Dziękujemy', '2022-01-16 21:47:50'),
(2, 3, 4, 'Piekny agat', 5, 'Niesamowita jakosc, piekne spekania. Polecam', '2021-09-02 14:54:00', 'Dziękujemy bardzo za wystawienie pozytywnej recenzji.', '2021-10-26 17:01:25'),
(4, 16, 22, 'Polecam', 5, 'Piękny kolor', '2021-10-23 18:50:42', 'Pieknie dziękujemy', '2021-10-26 16:55:21'),
(6, 10, 22, 'Niesamowity', 5, 'Nic dodać nic ująć', '2021-10-23 18:50:42', 'Dziękujemy bardzo', '2021-10-29 19:05:52'),
(7, 1, 27, 'Piękna sztuka', 5, 'Niesamowity kolor kamieni i do tego dobra cena. Polecam', '2021-10-23 19:39:23', 'Dziękujemy za pozytywną opinię', '2021-12-27 15:22:25'),
(10, 17, 22, 'Trochę nie ten kolor', 4, 'Może być, ładny', '2021-10-26 15:48:39', NULL, NULL),
(12, 9, 47, 'Piękny', 5, 'Nic dodać nic ująć', '2021-10-26 16:28:34', NULL, NULL),
(13, 36, 60, 'Piękny kyanit', 5, 'Właśnie taki jakiego potrzebowałam do nowego projektu.', '2021-11-13 17:15:01', NULL, NULL),
(14, 34, 60, 'Może być', 3, 'Dobra jakość, ale czegoś brakuje', '2021-11-13 17:17:56', NULL, NULL),
(15, 31, 58, 'Ładny, ale...', 3, 'Kamień syntetyczny, czego można się spodziewać?', '2021-11-13 17:21:34', NULL, NULL),
(16, 8, 58, 'Piękności', 5, 'Niesamowity', '2021-11-13 17:25:38', NULL, NULL),
(17, 2, 22, 'Niesamowity', 5, 'Nic dodać nic ująć. Wszystko w pełni zgodne z opisem', '2021-12-30 15:50:58', NULL, NULL),
(18, 35, 62, 'Ładny', 4, 'Czego się spodziewać po kamieniu syntetycznym', '2021-12-30 15:56:42', NULL, NULL),
(19, 40, 63, 'Piękny kamień', 5, 'Bardzo dobra cena i jakość. Polecam', '2022-01-06 15:03:03', NULL, NULL),
(20, 8, 65, 'Piękny okaz', 5, 'Nic dodać nic ująć. Jakość wyśmienita, kolor delikatny. Taki jak chciałam. Polecam', '2022-01-16 20:39:31', NULL, NULL);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `shape`
--

CREATE TABLE `shape` (
  `id_shap` int(11) NOT NULL,
  `name-sh` text COLLATE utf8_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `shape`
--

INSERT INTO `shape` (`id_shap`, `name-sh`) VALUES
(1, 'beczka'),
(2, 'bryłka'),
(3, 'cebulka'),
(4, 'cygaro'),
(5, 'donut'),
(6, 'grot'),
(7, 'kostka'),
(8, 'kropla'),
(9, 'krzyżyk'),
(10, 'kula'),
(11, 'kwadrat'),
(12, 'kwiat'),
(13, 'listek'),
(14, 'łezka'),
(15, 'markiza'),
(16, 'moneta'),
(17, 'okienko'),
(18, 'oliwka'),
(19, 'oponka'),
(20, 'orzech'),
(21, 'owal'),
(22, 'piórko'),
(23, 'plaster'),
(24, 'prostokąt'),
(25, 'prostopadłościan'),
(26, 'romb'),
(27, 'serce'),
(28, 'słupek'),
(29, 'talarek'),
(30, 'krążek'),
(31, 'dysk'),
(32, 'trójkąt'),
(33, 'ufo'),
(34, 'walec'),
(35, 'wałek'),
(36, 'wrzeciono'),
(37, 'inny kształt'),
(38, 'kule fasetowane'),
(39, 'briolette'),
(41, 'tuba');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `status`
--

CREATE TABLE `status` (
  `id_stat` int(11) NOT NULL,
  `name-s` varchar(30) COLLATE utf8_polish_ci NOT NULL,
  `descr-s` text COLLATE utf8_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `status`
--

INSERT INTO `status` (`id_stat`, `name-s`, `descr-s`) VALUES
(1, 'Poczekalnia', 'Zamówienie czeka na akceptacje'),
(2, 'Akceptacja', 'Zaakceptowano zamówienie. Czeka na zaakceptowanie płatności'),
(3, 'Płatność', 'Płatność zaakceptowana'),
(4, 'Przygotowanie', 'Zamówienie jest w przygotowaniu przez pracownika'),
(5, 'W realizacji', 'Zamówienie czeka na wysyłkę'),
(6, 'Wysłano', 'Zamówienie zostało wysłane i jest już w drodze'),
(7, 'W drodze', 'Zamówienie niedługo przybędzie'),
(8, 'Zakończono', 'Zamówienie jest już u odbiorcy'),
(9, 'Anulowane', 'Zamówienie anulowane w porozumieniu z klientem.');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `tag`
--

CREATE TABLE `tag` (
  `id_tag` int(11) NOT NULL,
  `name-t` text COLLATE utf8_polish_ci NOT NULL,
  `description-t` text COLLATE utf8_polish_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `tag`
--

INSERT INTO `tag` (`id_tag`, `name-t`, `description-t`) VALUES
(1, 'Agat', 'Istnieje wiele kamieni ozdobnych, które można wykorzystywać przy tworzeniu autorskiej biżuterii, jednakże to agat jest obecnie najpopularniejszy. Jego wstęgowa struktura w połączeniu z wieloma odmianami, sprawia, że wśród osób zajmujących się rękodziełem cieszy się on ogromnym powodzeniem. W sklepie Kianit oferujemy m.in. agat mszysty, brazylijski, ognisty, jak i w formie druzy. Agaty zbudowane są z wielu warstw, w naturze najczęściej posiadają blade, szarawe zabarwienie, dlatego często barwi się je w celu pozyskania intensywnych barw. Kamienie dostępne są w różnych kształtach i rozmiarach, dzięki czemu sprawdzą się zarówno do wytworzenia delikatnych bransoletek, subtelnych kolczyków, jak i charakterystycznych kolii czy efektownych naszyjników. Wszystkie kamienie półszlachetne do wyrobu biżuterii mają określone parametry, dzięki czemu jeszcze przed zakupem można je dopasować stosownie do planowego projektu. Agat w różnych kolorach, formach i odmianach świetnie sprawdzi się w projektach nowoczesnych, klasycznych czy w stylu boho. Już teraz sprawdź, które agaty najlepiej sprawdzą się w Twoim pomyśle.'),
(2, 'Akwamaryn', 'Idealnie stworzona biżuteria wymaga przede wszystkim dobrze dobranych kamieni; akwamaryn ze swoją ciekawą formą i nieoczywistym wyglądem świetnie sprawdzi się w każdym autorskim projekcie. Ten niezwykły kamień bywa przejrzysty, niekiedy i mleczny; z niebieskimi lub niebieskozielonymi refleksami, świetny zarówno w przypadku eleganckich kompletów biżuterii, jak też chłodnej, niebieskiej kompozycji'),
(3, 'Amazonit', NULL),
(4, 'Ametryn ', NULL),
(5, 'Ametyst', NULL),
(6, 'Amonit', NULL),
(7, 'Angelit', NULL),
(8, 'Apatyt', NULL),
(9, 'Awenturyn', NULL),
(10, 'Bronzyt', NULL),
(11, 'Chalcedon', NULL),
(12, 'Chalkopiryt', NULL),
(13, 'Chryzokola', NULL),
(14, 'Chryzopraz', NULL),
(15, 'Cyrkon', NULL),
(16, 'Cytryn', 'Półprodukty dostępne są w różnych kształtach i rozmiarach, od klasycznych kul, po fasetowane owale, pastylki, oponki czy bryłki. Dostępna jest również sieczka oraz surowe kamienie o zróżnicowanej wielkości. Wszystkie kamienie mogą być wykorzystane w kompozyjach biżuteryjnych, zwłaszcza lekkich, nowoczesnych i niezwykle subtelnych. Kamienie o większych rozmiarach świetnie sprawdzą się w projektach większych kolekcji. Wybierz cytryn w sklepie Kianit.pl i ciesz się jego energetyzmem oraz naturalnym blaskiem.'),
(17, 'Czaroit', NULL),
(18, 'Diament', NULL),
(19, 'Diopsyd', NULL),
(20, 'Dumortieryt', NULL),
(21, 'Fluoryt', NULL),
(22, 'Granat', NULL),
(23, 'Hematyt', NULL),
(24, 'Herkimeryt', NULL),
(25, 'Hemimorfit', NULL),
(26, 'Howlit', NULL),
(27, 'Iolit', NULL),
(28, 'Jadeit', NULL),
(29, 'Jaspis', NULL),
(30, 'Kamień księżycowy', NULL),
(31, 'Kamień słoneczny', NULL),
(32, 'Karneol', NULL),
(33, 'Kryształ górski', NULL),
(34, 'Krzemień pasiasty', NULL),
(35, 'Kunzyt', NULL),
(36, 'Kwarc barwiony', NULL),
(37, 'Kwarc dymny', NULL),
(38, 'Kwarc kawowy', NULL),
(39, 'Kwarc lemon', NULL),
(40, 'Kwarc lodowy', NULL),
(41, 'Kwarc mistyczny', NULL),
(42, 'Kwarc niebieski', NULL),
(43, 'Kwarc różowy', NULL),
(44, 'Kwarc z rutylem', NULL),
(45, 'Kwarc z turmalinem', NULL),
(46, 'Kwarc tytanowany', NULL),
(47, 'Kwarc szary', NULL),
(48, 'Kyanit', NULL),
(49, 'Labradoryt', NULL),
(50, 'Lapis lazuli', NULL),
(51, 'Larimar', NULL),
(52, 'Larvikit', NULL),
(53, 'Lawa wulkaniczna', NULL),
(54, 'Lepidolit', NULL),
(55, 'Malachit', NULL),
(56, 'Masa perłowa', NULL),
(57, 'Mokait', NULL),
(58, 'Morganit', NULL),
(59, 'Nefryt', NULL),
(60, 'Obsydian', NULL),
(61, 'Oliwin', NULL),
(62, 'Onyks', NULL),
(63, 'Opal', NULL),
(64, 'Peridot', NULL),
(65, 'Piryt', NULL),
(66, 'Prehnit', NULL),
(67, 'Riolit', NULL),
(68, 'Rodochrozyt', NULL),
(69, 'Rodonit', NULL),
(70, 'Rubelit', NULL),
(71, 'Rubin', NULL),
(72, 'Rutyl', NULL),
(73, 'Sardonyks', NULL),
(74, 'Serpentynit', NULL),
(75, 'Serafinit', NULL),
(76, 'Sodalit', NULL),
(77, 'Spinel', NULL),
(78, 'Sugilit', NULL),
(79, 'Szafir', NULL),
(80, 'Szmaragd', NULL),
(81, 'Tanzanit', NULL),
(82, 'Tektyt', NULL),
(83, 'Topaz', NULL),
(84, 'Turkus', NULL),
(85, 'Turmalin', NULL),
(86, 'Tygrysie oko', NULL),
(87, 'Zoisyt', NULL),
(88, 'Howlit syntetyczny', NULL),
(89, 'Kocie oko', NULL),
(90, 'Terahertz', NULL),
(91, 'Piasek pustyni', NULL),
(92, 'Noc Kairu', NULL),
(93, 'Opalit', NULL),
(94, 'Żywica', NULL),
(95, 'Hodowlane', NULL),
(96, 'Naturalne', NULL),
(97, 'Muszle', NULL),
(98, 'Seashell', NULL),
(99, 'Kaboszony', NULL),
(100, 'Kaboszony', NULL),
(101, 'Inkluzje', NULL),
(102, 'Antyczne', NULL),
(103, 'Czeskie', NULL),
(104, 'Bazy', NULL),
(105, 'Bigle', NULL),
(106, 'Druty', NULL),
(107, 'Kolczyki', NULL),
(108, 'Końcówki', NULL),
(109, 'Koraliki', NULL),
(110, 'Kółka', NULL),
(111, 'Krawatki', NULL),
(112, 'Kulki', NULL),
(113, 'Łańcuszki', NULL),
(114, 'Łapaczki', NULL),
(115, 'Przekładki', NULL),
(116, 'Puzderka', NULL),
(117, 'Rurki', NULL),
(118, 'Szpilki', NULL),
(119, 'Sztyfty', NULL),
(120, 'Zapięcia', NULL),
(121, 'Zawieszki', NULL),
(122, 'Bransoletki', NULL),
(123, 'Łańcuszki', NULL),
(124, 'Kolczyki', NULL),
(125, 'Broszki', NULL),
(126, 'Swarovski', NULL),
(127, 'Inne', NULL),
(128, 'Zawieszki', NULL),
(129, 'Przekładki', NULL),
(130, 'Jubilerskie', NULL),
(131, 'Naturalne', NULL),
(132, 'Syntetyczne', NULL),
(133, 'Zamsz', NULL),
(134, 'Jubilerskie', NULL),
(135, 'Nylonowe', NULL),
(136, 'Odzieżowe', NULL),
(137, 'Woskowane', NULL),
(138, 'Tytanowa', NULL),
(139, 'Satynowa', NULL),
(140, 'Sari', NULL),
(141, 'Shibori', NULL),
(142, 'Chiński', NULL),
(143, 'PEGA', NULL),
(144, 'Kokosowe', NULL),
(145, 'Sandałowe', NULL),
(146, 'Różane', NULL),
(147, 'Wenge', NULL),
(148, 'Kamforowe', NULL),
(149, 'Egzotyczne', NULL),
(150, 'Przechowywanie', NULL),
(151, 'Prezentowe', NULL),
(152, 'Pocztowe', NULL),
(153, 'Woreczki', NULL),
(154, 'Igły', NULL),
(155, 'Kleje', NULL),
(156, 'Krosna', NULL),
(157, 'Maty', NULL),
(158, 'Lutowanie', NULL),
(167, 'jakaś', NULL),
(168, 'sznury', NULL),
(169, 'innanazwa', NULL);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `user`
--

CREATE TABLE `user` (
  `id_user` int(11) NOT NULL,
  `user` tinyint(1) DEFAULT NULL,
  `rank` tinyint(1) DEFAULT NULL,
  `id_rank-u` int(11) DEFAULT NULL,
  `name-u` varchar(15) COLLATE utf8_polish_ci NOT NULL,
  `surname-u` varchar(30) COLLATE utf8_polish_ci NOT NULL,
  `email` varchar(100) COLLATE utf8_polish_ci NOT NULL,
  `telephone` varchar(11) COLLATE utf8_polish_ci NOT NULL,
  `newsletter` tinyint(1) DEFAULT NULL,
  `register` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `user`
--

INSERT INTO `user` (`id_user`, `user`, `rank`, `id_rank-u`, `name-u`, `surname-u`, `email`, `telephone`, `newsletter`, `register`) VALUES
(1, 0, 1, 1, 'None', 'None', 'none@none.com', '000-000-000', 1, '1111-11-11 11:11:11'),
(2, 1, 0, NULL, 'Janina', 'Kowalska', 'example@example.com', '213-453-458', 0, '2021-06-02 00:00:00'),
(3, 1, 1, 2, 'Karol', 'Worecki', 'worek@gmail.com', '321-435-643', 0, '2021-09-01 15:16:00'),
(6, 1, 0, NULL, 'Marta', 'Kruścik', 'example@email.com', '345-647-564', 1, '2021-09-13 03:09:10'),
(7, 1, 1, 3, 'Sylwester', 'Xawery', 'cosTam@jakas.com', '324-341-235', 1, '2021-09-13 03:29:11'),
(8, 1, 0, NULL, 'Helena', 'Jadwińska', 'cokolwiek@jakakolwiek.pl', '324-546-435', 1, '2021-09-14 11:11:31'),
(10, 1, 1, 4, 'Leonard', 'Radzikowski', 'bleble@mail.com', '435-624-732', 1, '2021-09-16 11:27:22'),
(11, 1, 1, 2, 'Janina', 'Wierzchołek', 'ktostam@mail.com', '432-543-543', 1, '2021-10-02 12:11:22'),
(15, 0, 1, 2, 'Dawid', 'Habadzibadło', 'habad@gmail.com', '452-563-235', 1, '2021-10-19 20:24:00'),
(16, 1, 0, NULL, 'Daria', 'Sroga', 'sd@wp.pl', '657-262-576', 0, '2021-10-19 21:09:00'),
(17, 0, 1, 2, 'Daria', 'Worecka', 'worcia@wp.pl', '432-453-564', 0, '2021-10-26 19:24:00'),
(19, 1, 0, NULL, 'Jolka', 'Olka', 'jakaskolwiek@domena.com', '235-435-436', 1, '2021-11-14 17:37:52'),
(20, 1, 0, NULL, ' Elżbieta', 'Oluś', 'olenka@wp.pl', '424-525-661', 1, '2021-11-14 18:08:25'),
(21, 1, 0, NULL, 'Elżbieta', 'Gołaz', 'elizabeth.g@wp.pl', '342-432-343', 1, '2021-11-14 18:15:59'),
(22, 1, 0, NULL, 'Wiesław', 'Jastrząb', 'jwiesiek@wp.pl', '876-987-567', 0, '2021-11-16 17:21:45'),
(23, 0, 1, 2, 'Jacek', 'Niewiem', 'jdb93193@boofx.com', '324-532-434', 1, '2021-12-28 13:53:18'),
(27, 0, 1, 2, 'afeawefwa', 'rfefaewf', 'eya86863@cuoly.com', '2342435324', 0, '2021-12-28 14:44:34'),
(28, 0, 1, 3, 'Jowita', 'Jakaś', 'isq83211@boofx.com', '324-534-574', 0, '2021-12-28 16:22:19'),
(30, NULL, 1, 3, 'Marta', 'Kruścik', 'jakistam@gmail.com', '230-043-020', NULL, '2021-12-28 00:00:00'),
(33, 1, 1, 2, 'Jan', 'Kolas', 'kolo@o2.pl', '324-234-243', 0, '2022-01-16 21:00:12');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `user-meta`
--

CREATE TABLE `user-meta` (
  `id_user_m` int(11) NOT NULL,
  `logged` tinyint(1) NOT NULL,
  `id_user-m` int(11) DEFAULT NULL,
  `firm` tinyint(1) NOT NULL DEFAULT 0,
  `name_firm` text COLLATE utf8_polish_ci DEFAULT NULL,
  `nip_firm` varchar(15) COLLATE utf8_polish_ci DEFAULT NULL,
  `firm_email` varchar(100) COLLATE utf8_polish_ci DEFAULT NULL,
  `firm_tel` varchar(11) COLLATE utf8_polish_ci DEFAULT NULL,
  `adr_str` varchar(60) COLLATE utf8_polish_ci NOT NULL,
  `adr_nr` varchar(10) COLLATE utf8_polish_ci NOT NULL,
  `adr_town` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `adr_state` varchar(20) COLLATE utf8_polish_ci NOT NULL,
  `adr_code` varchar(6) COLLATE utf8_polish_ci NOT NULL,
  `adr_post` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `adr_count` varchar(20) COLLATE utf8_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `user-meta`
--

INSERT INTO `user-meta` (`id_user_m`, `logged`, `id_user-m`, `firm`, `name_firm`, `nip_firm`, `firm_email`, `firm_tel`, `adr_str`, `adr_nr`, `adr_town`, `adr_state`, `adr_code`, `adr_post`, `adr_count`) VALUES
(1, 0, NULL, 0, NULL, NULL, NULL, NULL, 'Prosta', '3/5', 'Jelenia Góra', 'Dolnośląskie', '30-579', 'Jelenia Góra', 'Polska'),
(2, 1, NULL, 1, 'Kopytkowe', '7465873645', 'biuro@kopytkowe.pl', '987-098-567', 'Nikczemnej trójki', '6', 'Dziadowo', 'Świętokrzyskie', '34-232', 'Gardłowice', 'Polska'),
(5, 1, 3, 0, NULL, NULL, NULL, NULL, 'Srebrna', '32', 'Stare Babice', 'Olawskie', '43-278', 'Stare Babice', 'Polska'),
(6, 0, NULL, 0, NULL, NULL, NULL, NULL, 'Jakas', '28', 'Czarne Ruminki', 'Podlaskie', '43-654', 'Czarne Rumunki', 'Polska'),
(8, 0, NULL, 0, NULL, NULL, NULL, NULL, 'Armii Krajowej', '4', 'Olesnica', 'Dolnoslaskie', '54-367', 'Białe Błoto', 'Polska'),
(9, 0, NULL, 1, 'Gawory', '4345678765', 'biuro@gawory.pl', '765-098-675', 'Armii Krajowej', '4', 'Olesnica', 'Dolnoslaskie', '54-367', 'Biale Bloto', 'Polska'),
(31, 1, 2, 1, 'Gawory', '62536473820', 'biuro@gawory.pl', '543-557-489', 'Armii Krajowej', '8', 'Oleśnica', 'Dolnośląskie', '54-369', 'Białe Błoto', 'Polska'),
(36, 0, NULL, 0, NULL, NULL, NULL, NULL, 'Armii Krajowej', '4', 'Olesnica', 'Dolnoslaskie', '54-367', 'Biale Bloto', 'Polska'),
(48, 0, NULL, 0, NULL, NULL, NULL, NULL, 'Armii Krajowej', '4', 'Olesnica', 'Dolnoslaskie', '54-367', 'Biale Bloto', 'Polska'),
(49, 0, NULL, 0, NULL, NULL, NULL, NULL, 'Armii Krajowej', '4', 'Olesnica', 'Dolnoslaskie', '54-367', 'Biale Bloto', 'Polska'),
(50, 0, NULL, 0, NULL, NULL, NULL, NULL, 'Armii Krajowej', '4', 'Olesnica', 'Dolnoslaskie', '54-367', 'Białe Bloto', 'Polska'),
(51, 0, NULL, 0, NULL, NULL, NULL, NULL, 'Armii Krajowej', '5', 'Olesnica', 'Dolnoslaskie', '54-367', 'Białe Bloto', 'Polska'),
(52, 0, NULL, 0, NULL, NULL, NULL, NULL, 'Armii Krajowej', '5', 'Olesnica', 'Dolnoslaskie', '54-367', 'Biale Bloto', 'Polska'),
(53, 1, 2, 0, NULL, NULL, NULL, NULL, 'Armii Krajowej', '5', 'Oleśnica', 'Dolnoslaskie', '54-360', 'Białe Błoto', 'Polska'),
(55, 0, NULL, 0, NULL, NULL, NULL, NULL, 'Tadeusza Kościuszki', '4', 'Wrocław', 'Dolnośląskie', '20-466', 'Wrocław', 'Polska'),
(56, 0, NULL, 0, NULL, NULL, NULL, NULL, 'Tadeusza Kościuszki', '4', 'Oleśnica', 'Dolnośląskie', '56-400', 'Białe Błoto', 'Polska'),
(57, 1, 6, 0, NULL, NULL, NULL, NULL, 'Głazowa', '4/3', 'Domiszcza', 'Podlaskie', '32-473', 'Małe Domki', 'Polska'),
(58, 1, 7, 0, NULL, NULL, NULL, NULL, 'Nikczemnej trójki', '209', 'Koło', 'Świętokrzyskie', '34-213', 'Koło', 'Polska'),
(59, 1, 8, 0, NULL, NULL, NULL, NULL, 'Katolickich Świętych', '98', 'Dziadów', 'Opolskie', '34-241', 'Dziadów', 'Polska'),
(60, 1, 2, 1, 'Kopytkowe', '7465873645', 'biuro@kopytkowe.pl', '987-098-567', 'Nikczemnej trójki', '6', 'Dziadów', 'Świętokrzyskie', '34-218', 'Gardłowice', 'Polska'),
(61, 1, 2, 0, NULL, NULL, NULL, NULL, 'Szczęśliwa', '5', 'Wrocław', 'Dolnośląskie', '34-213', 'Wrocław', 'Polska'),
(67, 0, NULL, 0, NULL, NULL, NULL, NULL, 'Jana Matejki', '21', 'Modre', 'Podlaskie', '31-343', 'Górskie Szlaki', 'Polska'),
(71, 0, NULL, 0, NULL, NULL, NULL, NULL, 'Jagiellońska', '7', 'Trzebanica', 'Dolnośląskie', '52-414', 'Trzebnica', 'Polska'),
(73, 1, 10, 0, NULL, NULL, NULL, NULL, 'Horacego Wiary', '6', 'Zośki', 'Świętokrzyskie', '34-678', 'Kamieniec', 'Polska'),
(74, 0, NULL, 1, 'Wywrotowcy', '4121434235', 'Ludnila@wywrot.pl', '432-523-523', 'Katolickich Świętych', '209', 'Oleśnica', 'Dolnośląskie', '56-400', 'Oleśnica', 'Polska'),
(75, 1, 11, 0, NULL, NULL, NULL, NULL, 'Warcząca trójka', '3/7', 'Chełm', 'Mazowieckie', '43-235', 'Chełm', 'Polska'),
(76, 0, NULL, 1, 'Holubki', '3424353563', 'biuro@hilubki.pl', '341-351-453', 'Grudzińska', '32', 'Jaremno', 'Opolskie', '32-545', 'Jaremno', 'Polska'),
(77, 0, NULL, 0, NULL, NULL, NULL, NULL, 'Wątła', '6', 'Konin', 'Pomorskie', '23-452', 'Karczochy', 'Polska'),
(79, 0, NULL, 1, 'Porcelanki', '4532457753', 'biuro@porcelanki.pl', '343-543-534', 'Armii Krajowej', '6', 'Oleśnica', 'Dolnośląskie', '56-400', 'Oleśnica', 'Polska'),
(80, 0, NULL, 1, 'Historycy', '5674829045', 'hist@historycy.pl', '456-245-642', 'Katolickich Świętych', '209', 'Trzebanica', 'Podlaskie', '34-213', 'Wrocław', 'Polska'),
(84, 0, NULL, 0, NULL, NULL, NULL, NULL, 'Armii Krajowej', '4', 'Olesnica', 'Dolnośląskie', '54-367', 'Białe Bloto', 'Polska'),
(85, 0, NULL, 0, NULL, NULL, NULL, NULL, 'Armii Krajowej', '4', 'Olesnica', 'Dolnoslaskie', '54-367', 'Białe Bloto', 'Polska'),
(86, 0, NULL, 0, NULL, NULL, NULL, NULL, 'Nikczemnej trójki', '98', 'Olesnica', 'Pomorskie', '54-367', 'Biale Bloto', 'Polska'),
(87, 0, NULL, 0, NULL, NULL, NULL, NULL, 'Jagiellońska', '4', 'Olesnica', 'Dolnoslaskie', '54-367', 'Białe Bloto', 'Polska'),
(102, 1, 19, 0, NULL, NULL, NULL, NULL, 'Tadeusza Kościuszki', '5', 'Oleśnica', 'Opolskie', '20-466', 'Biale Bloto', 'Polska'),
(103, 1, 20, 1, 'Kościelni', '4352638473', 'koscielni@kosc.pl', '454-534-532', 'Katolickich Świętych', '5', 'Wrocław', 'Podlaskie', '34-213', 'Wrocław', 'Polska'),
(104, 1, 21, 0, NULL, NULL, NULL, NULL, 'Szczęśliwa', '209', 'Trzebanica', 'Opolskie', '52-414', 'Gardłowice', 'Polska'),
(105, 0, NULL, 0, NULL, NULL, NULL, NULL, 'Floriańska', '5', 'Kamienna góra', 'Podlaskie', '41-241', 'Górki', 'Polska'),
(107, 0, NULL, 1, 'Onyx', '4376578965', 'biuro@onyx.eu', '726-825-853', 'Oćwieka', '31', 'Oćwieka', 'Kujawsko-Pomorskie', '88-419', 'Gąsawa', 'Polska'),
(108, 1, 20, 0, NULL, NULL, NULL, NULL, 'Hiacyntowa', '452', 'Jelenów', 'Podlaskie', '23-123', 'Koziołki', 'Polska');

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `action`
--
ALTER TABLE `action`
  ADD PRIMARY KEY (`id_action`);

--
-- Indeksy dla tabeli `basket`
--
ALTER TABLE `basket`
  ADD KEY `id_prod-b` (`id_prod-b`),
  ADD KEY `id_user-b` (`id_user-b`);

--
-- Indeksy dla tabeli `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`id_cat`),
  ADD KEY `id_group` (`id_group-c`);

--
-- Indeksy dla tabeli `category-tag`
--
ALTER TABLE `category-tag`
  ADD PRIMARY KEY (`id_catag`),
  ADD KEY `id_cat-ct` (`id_cat-ct`),
  ADD KEY `id_tag-ct` (`id_tag-ct`);

--
-- Indeksy dla tabeli `color`
--
ALTER TABLE `color`
  ADD PRIMARY KEY (`id_col`);

--
-- Indeksy dla tabeli `delivery`
--
ALTER TABLE `delivery`
  ADD PRIMARY KEY (`id_deliv`);

--
-- Indeksy dla tabeli `discount`
--
ALTER TABLE `discount`
  ADD PRIMARY KEY (`id_disc`);

--
-- Indeksy dla tabeli `fabric`
--
ALTER TABLE `fabric`
  ADD PRIMARY KEY (`id_fabr`);

--
-- Indeksy dla tabeli `group-g`
--
ALTER TABLE `group-g`
  ADD PRIMARY KEY (`id_group`);

--
-- Indeksy dla tabeli `history`
--
ALTER TABLE `history`
  ADD PRIMARY KEY (`id_his`),
  ADD KEY `id_user-h` (`id_user-h`),
  ADD KEY `id_act-h` (`id_act-h`);

--
-- Indeksy dla tabeli `login`
--
ALTER TABLE `login`
  ADD UNIQUE KEY `email-l` (`login`),
  ADD KEY `id_user-l` (`id_user-l`);

--
-- Indeksy dla tabeli `order-o`
--
ALTER TABLE `order-o`
  ADD PRIMARY KEY (`id_order`),
  ADD KEY `id_stat-o` (`id_stat-o`),
  ADD KEY `id_del-o` (`id_del-o`),
  ADD KEY `id_pay-o` (`id_pay-o`),
  ADD KEY `id_user_m-o` (`id_user_m-o`) USING BTREE,
  ADD KEY `id_disc-o` (`id_disc-o`),
  ADD KEY `id_worker` (`id_worker`);

--
-- Indeksy dla tabeli `order-product`
--
ALTER TABLE `order-product`
  ADD KEY `id_prod-op` (`id_prod-op`),
  ADD KEY `id_order-op` (`id_order-op`);

--
-- Indeksy dla tabeli `payment`
--
ALTER TABLE `payment`
  ADD PRIMARY KEY (`id_pay`);

--
-- Indeksy dla tabeli `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id_prod`),
  ADD KEY `id_catag-p` (`id_catag-p`);

--
-- Indeksy dla tabeli `product-meta`
--
ALTER TABLE `product-meta`
  ADD KEY `id_col-m` (`id_col-m`) USING BTREE,
  ADD KEY `id_shap-m` (`id_shap-m`) USING BTREE,
  ADD KEY `id_fabr-m` (`id_fabr-m`) USING BTREE,
  ADD KEY `id_prod-m` (`id_prod-m`) USING BTREE;

--
-- Indeksy dla tabeli `rank`
--
ALTER TABLE `rank`
  ADD PRIMARY KEY (`id_rank`);

--
-- Indeksy dla tabeli `review`
--
ALTER TABLE `review`
  ADD PRIMARY KEY (`id_rev`),
  ADD KEY `id_order-rw` (`id_order-rw`) USING BTREE,
  ADD KEY `id_prod-rw` (`id_prod-rw`) USING BTREE;

--
-- Indeksy dla tabeli `shape`
--
ALTER TABLE `shape`
  ADD PRIMARY KEY (`id_shap`);

--
-- Indeksy dla tabeli `status`
--
ALTER TABLE `status`
  ADD PRIMARY KEY (`id_stat`);

--
-- Indeksy dla tabeli `tag`
--
ALTER TABLE `tag`
  ADD PRIMARY KEY (`id_tag`);

--
-- Indeksy dla tabeli `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_user`),
  ADD UNIQUE KEY `email-u` (`email`),
  ADD KEY `id_rank-u` (`id_rank-u`);

--
-- Indeksy dla tabeli `user-meta`
--
ALTER TABLE `user-meta`
  ADD PRIMARY KEY (`id_user_m`),
  ADD KEY `id_user-m` (`id_user-m`);

--
-- AUTO_INCREMENT dla tabel zrzutów
--

--
-- AUTO_INCREMENT dla tabeli `action`
--
ALTER TABLE `action`
  MODIFY `id_action` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT dla tabeli `category`
--
ALTER TABLE `category`
  MODIFY `id_cat` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=42;

--
-- AUTO_INCREMENT dla tabeli `category-tag`
--
ALTER TABLE `category-tag`
  MODIFY `id_catag` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=272;

--
-- AUTO_INCREMENT dla tabeli `color`
--
ALTER TABLE `color`
  MODIFY `id_col` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT dla tabeli `delivery`
--
ALTER TABLE `delivery`
  MODIFY `id_deliv` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT dla tabeli `discount`
--
ALTER TABLE `discount`
  MODIFY `id_disc` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT dla tabeli `fabric`
--
ALTER TABLE `fabric`
  MODIFY `id_fabr` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=109;

--
-- AUTO_INCREMENT dla tabeli `group-g`
--
ALTER TABLE `group-g`
  MODIFY `id_group` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT dla tabeli `history`
--
ALTER TABLE `history`
  MODIFY `id_his` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=52;

--
-- AUTO_INCREMENT dla tabeli `order-o`
--
ALTER TABLE `order-o`
  MODIFY `id_order` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=66;

--
-- AUTO_INCREMENT dla tabeli `payment`
--
ALTER TABLE `payment`
  MODIFY `id_pay` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT dla tabeli `product`
--
ALTER TABLE `product`
  MODIFY `id_prod` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=43;

--
-- AUTO_INCREMENT dla tabeli `rank`
--
ALTER TABLE `rank`
  MODIFY `id_rank` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT dla tabeli `review`
--
ALTER TABLE `review`
  MODIFY `id_rev` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT dla tabeli `shape`
--
ALTER TABLE `shape`
  MODIFY `id_shap` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=42;

--
-- AUTO_INCREMENT dla tabeli `status`
--
ALTER TABLE `status`
  MODIFY `id_stat` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT dla tabeli `tag`
--
ALTER TABLE `tag`
  MODIFY `id_tag` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=170;

--
-- AUTO_INCREMENT dla tabeli `user`
--
ALTER TABLE `user`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT dla tabeli `user-meta`
--
ALTER TABLE `user-meta`
  MODIFY `id_user_m` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=109;

--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `basket`
--
ALTER TABLE `basket`
  ADD CONSTRAINT `basket_ibfk_3` FOREIGN KEY (`id_prod-b`) REFERENCES `product` (`id_prod`),
  ADD CONSTRAINT `basket_ibfk_4` FOREIGN KEY (`id_user-b`) REFERENCES `user` (`id_user`);

--
-- Ograniczenia dla tabeli `category`
--
ALTER TABLE `category`
  ADD CONSTRAINT `category_ibfk_1` FOREIGN KEY (`id_group-c`) REFERENCES `group-g` (`id_group`);

--
-- Ograniczenia dla tabeli `category-tag`
--
ALTER TABLE `category-tag`
  ADD CONSTRAINT `category-tag_ibfk_2` FOREIGN KEY (`id_tag-ct`) REFERENCES `tag` (`id_tag`),
  ADD CONSTRAINT `category-tag_ibfk_3` FOREIGN KEY (`id_cat-ct`) REFERENCES `category` (`id_cat`);

--
-- Ograniczenia dla tabeli `history`
--
ALTER TABLE `history`
  ADD CONSTRAINT `history_ibfk_1` FOREIGN KEY (`id_act-h`) REFERENCES `action` (`id_action`),
  ADD CONSTRAINT `history_ibfk_2` FOREIGN KEY (`id_user-h`) REFERENCES `user` (`id_user`);

--
-- Ograniczenia dla tabeli `login`
--
ALTER TABLE `login`
  ADD CONSTRAINT `login_ibfk_1` FOREIGN KEY (`id_user-l`) REFERENCES `user` (`id_user`);

--
-- Ograniczenia dla tabeli `order-o`
--
ALTER TABLE `order-o`
  ADD CONSTRAINT `order-o_ibfk_10` FOREIGN KEY (`id_disc-o`) REFERENCES `discount` (`id_disc`),
  ADD CONSTRAINT `order-o_ibfk_11` FOREIGN KEY (`id_worker`) REFERENCES `user` (`id_user`),
  ADD CONSTRAINT `order-o_ibfk_12` FOREIGN KEY (`id_user_m-o`) REFERENCES `user-meta` (`id_user_m`),
  ADD CONSTRAINT `order-o_ibfk_2` FOREIGN KEY (`id_pay-o`) REFERENCES `payment` (`id_pay`),
  ADD CONSTRAINT `order-o_ibfk_5` FOREIGN KEY (`id_del-o`) REFERENCES `delivery` (`id_deliv`),
  ADD CONSTRAINT `order-o_ibfk_9` FOREIGN KEY (`id_stat-o`) REFERENCES `status` (`id_stat`);

--
-- Ograniczenia dla tabeli `order-product`
--
ALTER TABLE `order-product`
  ADD CONSTRAINT `order-product_ibfk_2` FOREIGN KEY (`id_order-op`) REFERENCES `order-o` (`id_order`),
  ADD CONSTRAINT `order-product_ibfk_3` FOREIGN KEY (`id_prod-op`) REFERENCES `product` (`id_prod`);

--
-- Ograniczenia dla tabeli `product`
--
ALTER TABLE `product`
  ADD CONSTRAINT `product_ibfk_1` FOREIGN KEY (`id_catag-p`) REFERENCES `category-tag` (`id_catag`);

--
-- Ograniczenia dla tabeli `product-meta`
--
ALTER TABLE `product-meta`
  ADD CONSTRAINT `product-meta_ibfk_1` FOREIGN KEY (`id_fabr-m`) REFERENCES `fabric` (`id_fabr`),
  ADD CONSTRAINT `product-meta_ibfk_2` FOREIGN KEY (`id_shap-m`) REFERENCES `shape` (`id_shap`),
  ADD CONSTRAINT `product-meta_ibfk_3` FOREIGN KEY (`id_col-m`) REFERENCES `color` (`id_col`),
  ADD CONSTRAINT `product-meta_ibfk_4` FOREIGN KEY (`id_prod-m`) REFERENCES `product` (`id_prod`);

--
-- Ograniczenia dla tabeli `review`
--
ALTER TABLE `review`
  ADD CONSTRAINT `review_ibfk_2` FOREIGN KEY (`id_order-rw`) REFERENCES `order-o` (`id_order`),
  ADD CONSTRAINT `review_ibfk_3` FOREIGN KEY (`id_prod-rw`) REFERENCES `product` (`id_prod`);

--
-- Ograniczenia dla tabeli `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `user_ibfk_1` FOREIGN KEY (`id_rank-u`) REFERENCES `rank` (`id_rank`);

--
-- Ograniczenia dla tabeli `user-meta`
--
ALTER TABLE `user-meta`
  ADD CONSTRAINT `user-meta_ibfk_1` FOREIGN KEY (`id_user-m`) REFERENCES `user` (`id_user`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
