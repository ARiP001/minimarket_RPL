-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 18, 2024 at 11:10 AM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `kasirrpl`
--

-- --------------------------------------------------------

--
-- Table structure for table `item`
--

CREATE TABLE `item` (
  `item_id` int(11) NOT NULL,
  `nama_item` varchar(255) NOT NULL,
  `harga` int(11) NOT NULL,
  `status` varchar(50) NOT NULL,
  `total` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `item`
--

INSERT INTO `item` (`item_id`, `nama_item`, `harga`, `status`, `total`) VALUES
(1, 'indomie', 5000, 'Tersedia', 8),
(3, 'Nutrilon', 300000, 'Tersedia', 3),
(4, 'milo', 10000, 'Tersedia', 6),
(5, 'sikat gigi', 5000, 'Tersedia', 10),
(6, 'chitato', 9000, 'Tersedia', 5),
(9, 'Rinso', 15000, 'Tersedia', 5),
(11, 'Pantene', 17500, 'Tersedia', 5),
(12, 'Permen', 1000, 'Tersedia', 99),
(13, 'Detol', 5000, 'Tersedia', 5);

-- --------------------------------------------------------

--
-- Table structure for table `transaksi`
--

CREATE TABLE `transaksi` (
  `transaksi_id` int(11) NOT NULL,
  `nama_pelanggan` varchar(255) NOT NULL,
  `item_id` int(11) NOT NULL,
  `tanggal` date NOT NULL,
  `nama_item` varchar(255) NOT NULL,
  `harga` int(11) NOT NULL,
  `jumlah_beli` int(11) NOT NULL,
  `total_bayar` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `nama_user` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transaksi`
--

INSERT INTO `transaksi` (`transaksi_id`, `nama_pelanggan`, `item_id`, `tanggal`, `nama_item`, `harga`, `jumlah_beli`, `total_bayar`, `user_id`, `nama_user`) VALUES
(1, 'Pelanggan', 1, '2024-06-06', 'indomie', 4000, 3, 12000, 1, 'Arif Fathurrahman'),
(6, 'Vero', 4, '2024-06-05', 'milo', 10000, 10, 100000, 1, 'Arif Fathurrahman'),
(10, 'Leon', 5, '2024-06-17', 'sikat gigi', 5000, 10, 50000, 1, 'Arif Fathurrahman'),
(11, 'Dina', 3, '2024-06-17', 'Nutrilon', 300000, 2, 600000, 1, 'Arif Fathurrahman'),
(13, 'Ren', 1, '2024-06-17', 'indomie', 5000, 5, 25000, 1, 'Arif Fathurrahman'),
(14, 'Jessie', 1, '2024-06-17', 'indomie', 5000, 2, 10000, 1, 'Arif Fathurrahman'),
(15, 'Dion', 3, '2024-06-17', 'Nutrilon', 300000, 2, 600000, 1, 'Arif Fathurrahman'),
(16, 'Reno', 11, '2024-06-17', 'Pantene', 17500, 2, 35000, 1, 'Arif Fathurrahman'),
(17, 'Reno', 11, '2024-06-17', 'Pantene', 17500, 3, 52500, 1, 'Arif Fathurrahman'),
(18, 'Arip', 11, '2024-06-17', 'Pantene', 17500, 2, 35000, 1, 'Arif Fathurrahman'),
(19, 'Pelanggan', 1, '2024-06-17', 'indomie', 5000, 5, 25000, 1, 'Arif Fathurrahman'),
(20, 'Pelanggan', 9, '2024-06-17', 'Rinso', 15000, 2, 30000, 1, 'Arif Fathurrahman'),
(21, 'Pelanggan', 1, '2024-06-17', 'indomie', 5000, 3, 15000, 2, 'Nathaleon R D K'),
(22, 'Pelanggan', 1, '2024-06-18', 'indomie', 5000, 2, 10000, 1, 'Arif Fathurrahman'),
(23, 'Pelanggan', 6, '2024-06-18', 'chitato', 9000, 2, 18000, 1, 'Arif Fathurrahman'),
(24, 'Pelanggan', 1, '2024-06-18', 'indomie', 5000, 2, 10000, 1, 'Arif Fathurrahman'),
(25, 'Pelanggan', 4, '2024-06-18', 'milo', 10000, 2, 20000, 1, 'Arif Fathurrahman'),
(30, 'Pelanggan', 1, '2024-06-18', 'indomie', 5000, 2, 10000, 1, 'Arif Fathurrahman'),
(31, 'Pelanggan', 1, '2024-06-18', 'indomie', 5000, 1, 5000, 1, 'Arif Fathurrahman'),
(32, 'Pelanggan', 1, '2024-06-18', 'indomie', 5000, 1, 5000, 2, 'Nathaleon R D K'),
(33, 'Pelanggan', 4, '2024-06-18', 'milo', 10000, 1, 10000, 1, 'Arif Fathurrahman'),
(35, 'Pelanggan', 1, '2024-06-18', 'indomie', 5000, 2, 10000, 1, 'Arif Fathurrahman'),
(36, 'Pelanggan', 6, '2024-06-18', 'chitato', 9000, 2, 18000, 1, 'Arif Fathurrahman'),
(37, 'Pelanggan', 6, '2024-06-18', 'chitato', 9000, 1, 9000, 1, 'Arif Fathurrahman'),
(38, 'Pelanggan', 12, '2024-06-18', 'Permen', 1000, 1, 1000, 1, 'Arif Fathurrahman');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `nama_user` varchar(255) NOT NULL,
  `level_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `username`, `password`, `nama_user`, `level_id`) VALUES
(1, 'admin', 'admin', 'Arif Fathurrahman', 1),
(2, 'kasir', 'kasir', 'Nathaleon R D K', 2),
(3, 'owner', 'owner', 'John Wick', 3),
(4, 'himmel', 'kasir123', 'Hero Himmel', 2),
(7, 'rudeus', 'rudeus', 'Rudeus Greyrat', 3),
(9, 'nathaleon', 'tes321', 'Nathaleon', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `item`
--
ALTER TABLE `item`
  ADD PRIMARY KEY (`item_id`);

--
-- Indexes for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD PRIMARY KEY (`transaksi_id`),
  ADD KEY `item_id` (`item_id`) USING BTREE,
  ADD KEY `user_id` (`user_id`) USING BTREE;

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `item`
--
ALTER TABLE `item`
  MODIFY `item_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `transaksi`
--
ALTER TABLE `transaksi`
  MODIFY `transaksi_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD CONSTRAINT `transaksi_ibfk_1` FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `transaksi_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
