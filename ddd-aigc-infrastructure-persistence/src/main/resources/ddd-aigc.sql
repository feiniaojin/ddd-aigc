CREATE DATABASE db_ddd_aigc;

USE db_ddd_aigc;

CREATE TABLE `t_diary` (
	`id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增主键',
	`diary_id` varchar(64) NULL COMMENT '日记id',
	`content` varchar(15000) NULL COMMENT '日记正文',
    `diary_date` DATETIME DEFAULT NULL COMMENT '日记对应的时间',
    `diary_date_str` varchar(32) DEFAULT NULL COMMENT '日记对应的时间，字符串,yyyyMMDD',
    `uid` varchar(64) NULL COMMENT '用户ID',
	`deleted` tinyint NULL DEFAULT 0 COMMENT '逻辑删除标记[0-正常；1-已删除]',
	`created_by` VARCHAR(100) COMMENT '创建人',
	`created_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`modified_by` VARCHAR(100) COMMENT '更新人',
	`modified_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	`version` bigint DEFAULT 1 COMMENT '乐观锁',
	PRIMARY KEY (`id`),
	INDEX `idx_diaryId`(`diary_id`),
	UNIQUE INDEX `u_idx_uid_diaryDateStr` (`uid`,diary_date_str)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE utf8mb4_bin COMMENT '日记表';


CREATE TABLE `t_sticky_note` (
	`id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增主键',
	`sticky_note_id` varchar(64) NOT NULL COMMENT '贴纸id',
	`content` varchar(2000) NULL COMMENT '贴纸正文',
   	`diary_id` varchar(64) NULL COMMENT '日记ID',
    `uid` varchar(64) NULL COMMENT '用户ID',
    `participants` varchar(255) NULL COMMENT '参与者，字符串数组的json,[\'aaa\',\'bbb\']',
	`occurrence_time` DATETIME NULL COMMENT '创建时间',
	`deleted` tinyint NULL DEFAULT 0 COMMENT '逻辑删除标记[0-正常；1-已删除]',
	`created_by` VARCHAR(100) COMMENT '创建人',
	`created_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`modified_by` VARCHAR(100) COMMENT '更新人',
	`modified_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	`version` bigint DEFAULT 1 COMMENT '乐观锁',
	PRIMARY KEY (`id`),
	INDEX `idx_stickyNoteId`(`sticky_note_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE utf8mb4_bin COMMENT '日记贴纸表';


