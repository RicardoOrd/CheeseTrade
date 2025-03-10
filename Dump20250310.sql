-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: bd_cheesetrade
-- ------------------------------------------------------
-- Server version	8.0.41

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tbladministrador`
--

DROP TABLE IF EXISTS `tbladministrador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbladministrador` (
  `id_admin` int NOT NULL AUTO_INCREMENT,
  `id_empleado` int NOT NULL,
  `adm_privilegios` text NOT NULL,
  PRIMARY KEY (`id_admin`),
  UNIQUE KEY `id_empleado` (`id_empleado`),
  CONSTRAINT `tbladministrador_ibfk_1` FOREIGN KEY (`id_empleado`) REFERENCES `tblempleado` (`id_empleado`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbladministrador`
--

LOCK TABLES `tbladministrador` WRITE;
/*!40000 ALTER TABLE `tbladministrador` DISABLE KEYS */;
INSERT INTO `tbladministrador` VALUES (1,2,'gestionar usuarios, modificar precios, agregar productos');
/*!40000 ALTER TABLE `tbladministrador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblauditoria`
--

DROP TABLE IF EXISTS `tblauditoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblauditoria` (
  `id_auditoria` int NOT NULL AUTO_INCREMENT,
  `id_empleado` int NOT NULL,
  `aud_accion` varchar(255) NOT NULL,
  `aud_tabla_afectada` varchar(50) NOT NULL,
  `id_registro_afectado` int DEFAULT NULL,
  `fecha` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_auditoria`),
  KEY `id_empleado` (`id_empleado`),
  CONSTRAINT `tblauditoria_ibfk_1` FOREIGN KEY (`id_empleado`) REFERENCES `tblempleado` (`id_empleado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblauditoria`
--

LOCK TABLES `tblauditoria` WRITE;
/*!40000 ALTER TABLE `tblauditoria` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblauditoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblcliente`
--

DROP TABLE IF EXISTS `tblcliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblcliente` (
  `id_cliente` int NOT NULL AUTO_INCREMENT,
  `cli_nombre` varchar(100) NOT NULL,
  `cli_apellido` varchar(100) NOT NULL,
  `cli_telefono` varchar(20) NOT NULL,
  `cli_correo` varchar(255) DEFAULT NULL,
  `cli_direccion` varchar(100) NOT NULL,
  `cli_preferencias` varchar(50) NOT NULL,
  PRIMARY KEY (`id_cliente`),
  UNIQUE KEY `cli_telefono` (`cli_telefono`),
  UNIQUE KEY `cli_correo` (`cli_correo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblcliente`
--

LOCK TABLES `tblcliente` WRITE;
/*!40000 ALTER TABLE `tblcliente` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblcliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbldetalle_compra`
--

DROP TABLE IF EXISTS `tbldetalle_compra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbldetalle_compra` (
  `id_detalle` int NOT NULL AUTO_INCREMENT,
  `id_compra` int NOT NULL,
  `id_producto` int NOT NULL,
  `det_cantidad` int NOT NULL,
  `det_precio_unitario` decimal(10,2) NOT NULL,
  `det_subtotal` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id_detalle`),
  KEY `id_compra` (`id_compra`),
  KEY `id_producto` (`id_producto`),
  CONSTRAINT `tbldetalle_compra_ibfk_1` FOREIGN KEY (`id_compra`) REFERENCES `tblhistorial_compras` (`id_compra`),
  CONSTRAINT `tbldetalle_compra_ibfk_2` FOREIGN KEY (`id_producto`) REFERENCES `tblproductos` (`id_producto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbldetalle_compra`
--

LOCK TABLES `tbldetalle_compra` WRITE;
/*!40000 ALTER TABLE `tbldetalle_compra` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbldetalle_compra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbldevoluciones`
--

DROP TABLE IF EXISTS `tbldevoluciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbldevoluciones` (
  `id_devolucion` int NOT NULL AUTO_INCREMENT,
  `id_compra` int NOT NULL,
  `id_producto` int NOT NULL,
  `dev_cantidad` int NOT NULL,
  `dev_motivo` text NOT NULL,
  `dev_fecha_devolucion` datetime DEFAULT CURRENT_TIMESTAMP,
  `dev_estado` enum('pendiente','aceptada','rechazada') NOT NULL,
  PRIMARY KEY (`id_devolucion`),
  KEY `id_compra` (`id_compra`),
  KEY `id_producto` (`id_producto`),
  CONSTRAINT `tbldevoluciones_ibfk_1` FOREIGN KEY (`id_compra`) REFERENCES `tblhistorial_compras` (`id_compra`),
  CONSTRAINT `tbldevoluciones_ibfk_2` FOREIGN KEY (`id_producto`) REFERENCES `tblproductos` (`id_producto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbldevoluciones`
--

LOCK TABLES `tbldevoluciones` WRITE;
/*!40000 ALTER TABLE `tbldevoluciones` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbldevoluciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblempleado`
--

DROP TABLE IF EXISTS `tblempleado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblempleado` (
  `id_empleado` int NOT NULL AUTO_INCREMENT,
  `emp_nombre` varchar(100) NOT NULL,
  `emp_apellido` varchar(100) NOT NULL,
  `emp_correo` varchar(255) NOT NULL,
  `emp_telefono` varchar(20) NOT NULL,
  `emp_rol` enum('vendedor','cajero','administrador') NOT NULL,
  `emp_fecha_contratacion` date NOT NULL,
  `emp_salario` decimal(10,2) NOT NULL,
  `emp_estado` tinyint(1) DEFAULT '1',
  `emp_contraseña` varchar(255) NOT NULL,
  PRIMARY KEY (`id_empleado`),
  UNIQUE KEY `emp_correo` (`emp_correo`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblempleado`
--

LOCK TABLES `tblempleado` WRITE;
/*!40000 ALTER TABLE `tblempleado` DISABLE KEYS */;
INSERT INTO `tblempleado` VALUES (1,'Empleado','prueba','empleado@gmail.com','123456789','vendedor','2025-03-04',5000.00,1,'123'),(2,'EmpleadoADM','prueba','empleadoadm@gmail.com','123456789','administrador','2025-03-04',5000.00,1,'admin');
/*!40000 ALTER TABLE `tblempleado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblenvio`
--

DROP TABLE IF EXISTS `tblenvio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblenvio` (
  `id_envio` int NOT NULL AUTO_INCREMENT,
  `id_reserva` int NOT NULL,
  `env_direccion_envio` text NOT NULL,
  `env_costo_envio` decimal(10,2) NOT NULL,
  `env_fecha_envio` datetime DEFAULT NULL,
  `env_estado` enum('pendiente','en camino','entregado','cancelado') NOT NULL,
  `env_empresa_transportista` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id_envio`),
  KEY `id_reserva` (`id_reserva`),
  CONSTRAINT `tblenvio_ibfk_1` FOREIGN KEY (`id_reserva`) REFERENCES `tblreserva_productos` (`id_reserva`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblenvio`
--

LOCK TABLES `tblenvio` WRITE;
/*!40000 ALTER TABLE `tblenvio` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblenvio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblhistorial_compras`
--

DROP TABLE IF EXISTS `tblhistorial_compras`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblhistorial_compras` (
  `id_compra` int NOT NULL AUTO_INCREMENT,
  `id_cliente` int NOT NULL,
  `hist_fecha_compra` datetime DEFAULT CURRENT_TIMESTAMP,
  `hist_total` decimal(10,2) NOT NULL,
  `hist_metodo_pago` enum('efectivo','transferencia') NOT NULL,
  `hist_estado` enum('pagado','pendiente','cancelado') NOT NULL,
  PRIMARY KEY (`id_compra`),
  KEY `id_cliente` (`id_cliente`),
  CONSTRAINT `tblhistorial_compras_ibfk_1` FOREIGN KEY (`id_cliente`) REFERENCES `tblcliente` (`id_cliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblhistorial_compras`
--

LOCK TABLES `tblhistorial_compras` WRITE;
/*!40000 ALTER TABLE `tblhistorial_compras` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblhistorial_compras` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblorden_recoger`
--

DROP TABLE IF EXISTS `tblorden_recoger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblorden_recoger` (
  `id_orden` int NOT NULL AUTO_INCREMENT,
  `id_reserva` int NOT NULL,
  `ord_fecha_recogida` datetime NOT NULL,
  `ord_estado` enum('pendiente','recogido','cancelado') NOT NULL,
  PRIMARY KEY (`id_orden`),
  KEY `id_reserva` (`id_reserva`),
  CONSTRAINT `tblorden_recoger_ibfk_1` FOREIGN KEY (`id_reserva`) REFERENCES `tblreserva_productos` (`id_reserva`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblorden_recoger`
--

LOCK TABLES `tblorden_recoger` WRITE;
/*!40000 ALTER TABLE `tblorden_recoger` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblorden_recoger` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblproductos`
--

DROP TABLE IF EXISTS `tblproductos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblproductos` (
  `id_producto` int NOT NULL AUTO_INCREMENT,
  `prod_nombre` varchar(100) NOT NULL,
  `prod_descripcion` text NOT NULL,
  `prod_precio` decimal(10,2) NOT NULL,
  `prod_stock` int NOT NULL,
  `prod_peso_unitario` decimal(10,2) NOT NULL,
  `prod_tipo` enum('suave','maduro','fresco') NOT NULL,
  `prod_fecha_caducidad` date NOT NULL,
  `prod_imagen_url` text,
  PRIMARY KEY (`id_producto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblproductos`
--

LOCK TABLES `tblproductos` WRITE;
/*!40000 ALTER TABLE `tblproductos` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblproductos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblreserva_productos`
--

DROP TABLE IF EXISTS `tblreserva_productos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblreserva_productos` (
  `id_reserva` int NOT NULL AUTO_INCREMENT,
  `id_cliente` int NOT NULL,
  `id_producto` int NOT NULL,
  `res_cantidad` int NOT NULL,
  `res_fecha_reserva` datetime DEFAULT CURRENT_TIMESTAMP,
  `res_estado` enum('pendiente','confirmado','cancelado') NOT NULL,
  PRIMARY KEY (`id_reserva`),
  KEY `id_cliente` (`id_cliente`),
  KEY `id_producto` (`id_producto`),
  CONSTRAINT `tblreserva_productos_ibfk_1` FOREIGN KEY (`id_cliente`) REFERENCES `tblcliente` (`id_cliente`),
  CONSTRAINT `tblreserva_productos_ibfk_2` FOREIGN KEY (`id_producto`) REFERENCES `tblproductos` (`id_producto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblreserva_productos`
--

LOCK TABLES `tblreserva_productos` WRITE;
/*!40000 ALTER TABLE `tblreserva_productos` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblreserva_productos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblusuarios`
--

DROP TABLE IF EXISTS `tblusuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblusuarios` (
  `id` int NOT NULL AUTO_INCREMENT,
  `usuario` varchar(50) NOT NULL,
  `contraseña` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `usuario` (`usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblusuarios`
--

LOCK TABLES `tblusuarios` WRITE;
/*!40000 ALTER TABLE `tblusuarios` DISABLE KEYS */;
INSERT INTO `tblusuarios` VALUES (1,'admin','1234');
/*!40000 ALTER TABLE `tblusuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-03-10 10:21:43
