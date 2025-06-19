CREATE TABLE `shohinins` (
	`shohin_id` CHAR(4) NOT NULL COLLATE 'utf8mb4_general_ci',
	`shohin_mei` VARCHAR(20) NOT NULL COLLATE 'utf8mb4_general_ci',
	`shohin_bunrui` VARCHAR(20) NOT NULL COLLATE 'utf8mb4_general_ci',
	`hanbai_tanka` INT(11) NULL DEFAULT NULL,
	`shiire_tanka` INT(11) NULL DEFAULT NULL,
	`torokubi` DATE NULL DEFAULT NULL,
	PRIMARY KEY (`shohin_id`) USING BTREE
)
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB
;