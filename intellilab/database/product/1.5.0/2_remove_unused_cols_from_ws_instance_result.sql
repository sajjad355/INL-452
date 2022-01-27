ALTER TABLE `ws_instance_result` DROP FOREIGN KEY `FKjkvvbs7qcign2b26m67aryb4b`;
ALTER TABLE `ws_instance_result` DROP FOREIGN KEY `FKabq3w07172mtwwn4psm6ep36w`;
ALTER TABLE `ws_instance_result` DROP FOREIGN KEY `FKly0jncpdhuvym5fxow5usvgjg`;
ALTER TABLE `ws_instance_result` DROP FOREIGN KEY `FK7olj00neoapl20vl9d782wr7s`;

ALTER TABLE `ws_instance_result` DROP COLUMN `fk_worksheet_cps_instance_id`;
ALTER TABLE `ws_instance_result` DROP COLUMN `fk_worksheet_mrds_instance_id`;
ALTER TABLE `ws_instance_result` DROP COLUMN `fk_worksheet_qcps_instance_id`;
ALTER TABLE `ws_instance_result` DROP COLUMN `fk_worksheet_sds_instance_id`;