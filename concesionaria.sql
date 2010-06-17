-- MySQL dump 10.13  Distrib 5.1.37, for debian-linux-gnu (i486)
--
-- Host: localhost    Database: concesionaria
-- ------------------------------------------------------
-- Server version	5.1.37-1ubuntu5.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `coeficientes`
--

DROP TABLE IF EXISTS `coeficientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `coeficientes` (
  `anio` int(4) NOT NULL,
  `coeficiente` double(5,4) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coeficientes`
--

LOCK TABLES `coeficientes` WRITE;
/*!40000 ALTER TABLE `coeficientes` DISABLE KEYS */;
INSERT INTO `coeficientes` VALUES (1965,0.0010),(1966,0.0020),(1967,0.0030),(1968,0.0040),(1969,0.0050),(1970,0.0060),(1971,0.0070),(1972,0.0080),(1973,0.0090),(1974,0.0010),(1975,0.0011),(1976,0.0012),(1977,0.0013),(1978,0.0014),(1979,0.0015),(1980,0.0016),(1981,0.0017),(1982,0.0018),(1983,0.0019),(1984,0.0020),(1985,0.0021),(1986,0.0022),(1987,0.0023),(1988,0.0024),(1989,0.0025),(1990,0.0026),(1991,0.0027),(1992,0.0028),(1993,0.0029),(1994,0.0030),(1995,0.0031),(1996,0.0032),(1997,0.0033),(1998,0.0034),(1999,0.0035),(2000,0.0036),(2001,0.0037),(2002,0.0038),(2003,0.0039),(2004,0.0040),(2005,0.0041),(2006,0.0042),(2007,0.0043),(2008,0.0044),(2009,0.0045),(2010,0.0046),(2011,0.0047),(2012,0.0048),(2013,0.0049),(2014,0.0050),(2015,0.0051),(2016,0.0052),(2017,0.0053),(2018,0.0054),(2019,0.0055),(2020,0.0056),(2021,0.0057),(2022,0.0058),(2023,0.0059),(2024,0.0060),(2025,0.0061),(2026,0.0062),(2027,0.0063);
/*!40000 ALTER TABLE `coeficientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `colores`
--

DROP TABLE IF EXISTS `colores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `colores` (
  `id` int(2) NOT NULL AUTO_INCREMENT,
  `color` varchar(12) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `colores`
--

LOCK TABLES `colores` WRITE;
/*!40000 ALTER TABLE `colores` DISABLE KEYS */;
INSERT INTO `colores` VALUES (1,'azul'),(2,'rojo'),(3,'blanco'),(4,'crema'),(5,'negro');
/*!40000 ALTER TABLE `colores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `combustibles`
--

DROP TABLE IF EXISTS `combustibles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `combustibles` (
  `id` int(1) NOT NULL AUTO_INCREMENT,
  `combustible` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `combustibles`
--

LOCK TABLES `combustibles` WRITE;
/*!40000 ALTER TABLE `combustibles` DISABLE KEYS */;
INSERT INTO `combustibles` VALUES (1,'nafta'),(2,'gasoil');
/*!40000 ALTER TABLE `combustibles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `datos`
--

DROP TABLE IF EXISTS `datos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `datos` (
  `id` int(2) NOT NULL AUTO_INCREMENT,
  `impuestos` double(7,2) NOT NULL,
  `utilidades` double(5,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `datos`
--

LOCK TABLES `datos` WRITE;
/*!40000 ALTER TABLE `datos` DISABLE KEYS */;
INSERT INTO `datos` VALUES (1,3010.30,41.60),(2,728.25,30.44),(3,1900.80,260.50);
/*!40000 ALTER TABLE `datos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `marcas`
--

DROP TABLE IF EXISTS `marcas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `marcas` (
  `id` int(2) NOT NULL AUTO_INCREMENT,
  `marca` varchar(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `marcas`
--

LOCK TABLES `marcas` WRITE;
/*!40000 ALTER TABLE `marcas` DISABLE KEYS */;
INSERT INTO `marcas` VALUES (1,'Volkswagen'),(2,'Fiat'),(3,'Renault'),(4,'Nissan'),(5,'Scania'),(6,'Ford');
/*!40000 ALTER TABLE `marcas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stock`
--

DROP TABLE IF EXISTS `stock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stock` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `tipo_id` int(2) NOT NULL,
  `marca_id` int(2) NOT NULL,
  `color_id` int(2) NOT NULL,
  `anioFabricac` int(4) NOT NULL,
  `combustible_id` int(1) NOT NULL,
  `precFabrica` double(8,2) NOT NULL,
  `cantidadEnStock` int(2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stock`
--

LOCK TABLES `stock` WRITE;
/*!40000 ALTER TABLE `stock` DISABLE KEYS */;
INSERT INTO `stock` VALUES (1,1,1,1,2003,1,50000.40,10),(2,1,2,2,2005,1,45000.00,18),(3,2,3,3,2001,2,62000.00,5),(4,2,4,4,2002,2,68000.00,4),(5,3,5,5,2008,2,200000.00,3),(6,3,6,3,2009,2,180000.00,4),(7,1,2,4,2004,1,40000.00,4);
/*!40000 ALTER TABLE `stock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipos`
--

DROP TABLE IF EXISTS `tipos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipos` (
  `id` int(2) NOT NULL AUTO_INCREMENT,
  `tipo` varchar(15) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipos`
--

LOCK TABLES `tipos` WRITE;
/*!40000 ALTER TABLE `tipos` DISABLE KEYS */;
INSERT INTO `tipos` VALUES (1,'Particular'),(2,'Pasajero'),(3,'Carga');
/*!40000 ALTER TABLE `tipos` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2010-06-17  2:14:05
