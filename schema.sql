-- star_bot
CREATE SCHEMA `star_bot`;
ALTER SCHEMA `star_bot`  DEFAULT CHARACTER SET utf8mb4 ;
USE `star_bot`;
CREATE TABLE `message` (
  `message_no` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '일련번호',
  `service_id` varchar(60) NOT NULL COMMENT '서비스 ID',
  `cid` varchar(60) NOT NULL COMMENT '고객 식별 ID',
  `session_id` varchar(60) NOT NULL COMMENT '세션 ID',
  `scenario_type` varchar(10) NOT NULL COMMENT '시나리오 타입',
  `scenario` varchar(80) NOT NULL COMMENT '시나리오 이름',
  `block_type` varchar(10) NOT NULL COMMENT '블록 타입',
  `block` varchar(80) NOT NULL COMMENT '블록 이름',
  `channel` varchar(10) NOT NULL COMMENT '인입 채널: PC, WEB, MOBILE',
  `channel_id` varchar(10) DEFAULT NULL COMMENT '인입 채널 ID(특번 등)',
  `writer_type` enum('USER','BOT') NOT NULL COMMENT '작성자 유형',
  `message` text NOT NULL COMMENT '메시지',
  `created` datetime NOT NULL COMMENT '생성 시간',
  `updated` datetime DEFAULT NULL COMMENT '마지막 수정 시간',
  PRIMARY KEY (`message_no`)
) ENGINE=InnoDB COMMENT='메시지';

CREATE TABLE `session` (
  `session_no` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '일련번호',
  `service_id` varchar(60) NOT NULL COMMENT '서비스 ID',
  `cid` varchar(60) NOT NULL COMMENT '고객 식별 ID',
  `session_id` varchar(60) NOT NULL COMMENT '세션 ID',
  `channel` varchar(10) DEFAULT NULL COMMENT '인입 채널: PC, WEB, MOBILE',
  `channel_id` varchar(10) DEFAULT NULL COMMENT '인입 채널 ID(특번 등)',
  `status` enum('STARTED','ENDED','EXPIRED') NOT NULL COMMENT '진행 상태',
  `context` text COMMENT '컨텍스트',
  `message_created` datetime DEFAULT NULL COMMENT '대화 시작 시간',
  `message_updated` datetime DEFAULT NULL COMMENT '대화 종료 시간',
  `created` datetime NOT NULL COMMENT '생성 시간',
  `updated` datetime DEFAULT NULL COMMENT '마지막 수정 시간',
  PRIMARY KEY (`session_no`),
  UNIQUE KEY `UKEY1` (`cid`,`service_id`)
) ENGINE=InnoDB COMMENT='세션';

CREATE SCHEMA `star_service`;
ALTER SCHEMA `star_service`  DEFAULT CHARACTER SET utf8mb4 ;
USE `star_service`;
CREATE TABLE `connect_guest` (
  `service_no` bigint unsigned NOT NULL COMMENT '서비스 일련번호',
  `connect_id` varchar(60) NOT NULL COMMENT '연결용 고객 식별 ID',
  `cid` varchar(60) NOT NULL COMMENT '고객 식별 ID',
  `connect_channel` varchar(10) NOT NULL COMMENT '연결 채널: PC, WEB, MOBILE',
  `target_id` varchar(20) NOT NULL COMMENT '대상 서버 ID: CHATBOT',
  `target_connect_id` varchar(60) NOT NULL COMMENT '대상 서버의 연결 ID',
  `status` enum('CONNECTED','CLOSED','EXPIRED') NOT NULL COMMENT '연결 상태',
  `created` datetime NOT NULL COMMENT '연결 시작 시간',
  `updated` datetime DEFAULT NULL COMMENT '연결 종료 시간',
  PRIMARY KEY (`service_no`,`connect_id`)
) ENGINE=InnoDB COMMENT='연결 서버 관리(비회원)';

CREATE TABLE `connect_guest_history` (
  `history_no` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '일련번호',
  `service_no` bigint unsigned NOT NULL COMMENT '서비스 일련번호',
  `cid` varchar(60) NOT NULL COMMENT '고객 식별 ID',
  `target_id` varchar(20) NOT NULL COMMENT '대상 서버 ID: CHATBOT',
  `target_connect_id` varchar(60) NOT NULL COMMENT '대상 서버의 연결 ID',
  `status` enum('CONNECTED','CLOSED','EXPIRED') NOT NULL COMMENT '연결 상태',
  `created` datetime NOT NULL COMMENT '생성 시간',
  PRIMARY KEY (`history_no`)
) ENGINE=InnoDB COMMENT='연결 이력 관리(비회원)';

CREATE TABLE `connect_history` (
  `history_no` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '일련번호',
  `connect_no` bigint unsigned NOT NULL COMMENT '연결 서버 일련번호',
  `channel` varchar(10) NOT NULL COMMENT '인입 채널: PC, WEB, MOBILE',
  `channel_id` varchar(10) DEFAULT NULL COMMENT '인입 채널 ID(특번 등)',
  `target_id` varchar(20) NOT NULL COMMENT '대상 서버 ID: CHATBOT',
  `target_connect_id` varchar(60) NOT NULL COMMENT '대상 서버의 연결 ID',
  `status` enum('CONNECTED','CLOSED','EXPIRED') NOT NULL COMMENT '연결 상태',
  `created` datetime NOT NULL COMMENT '연결 시작시간',
  PRIMARY KEY (`history_no`)
) ENGINE=InnoDB COMMENT='연결 이력 관리';

CREATE TABLE `connect_server` (
  `connect_no` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '일련번호',
  `user_no` bigint unsigned NOT NULL COMMENT '연결 고객 일련번호',
  `channel` varchar(10) NOT NULL COMMENT '인입 채널: PC, WEB, MOBILE',
  `channel_id` varchar(10) DEFAULT NULL COMMENT '인입 채널 ID(특번 등)',
  `target_id` varchar(20) NOT NULL COMMENT '대상 서버 ID: CHATBOT',
  `target_connect_id` varchar(60) NOT NULL COMMENT '대상 서버의 연결 ID',
  `status` enum('CONNECTED','CLOSED','EXPIRED') NOT NULL COMMENT '연결 상태',
  `created` datetime NOT NULL COMMENT '연결 시작 시간',
  `updated` datetime DEFAULT NULL COMMENT '연결 종료 시간',
  PRIMARY KEY (`connect_no`),
  UNIQUE KEY `UKEY1` (`user_no`)
) ENGINE=InnoDB COMMENT='연결 서버 관리';

CREATE TABLE `connect_user` (
  `user_no` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '일련번호',
  `service_no` bigint unsigned NOT NULL COMMENT '서비스 일련번호',
  `connect_id` varchar(60) NOT NULL COMMENT '클라이언트용 고객 식별 ID',
  `uid` varchar(60) NOT NULL COMMENT '고객사의 고객 식별 ID',
  `cid` varchar(60) NOT NULL COMMENT '내부용 고객 식별 ID',
  `created` datetime NOT NULL COMMENT '생성 시간',
  `updated` datetime DEFAULT NULL COMMENT '마지막 수정 시간',
  PRIMARY KEY (`user_no`)
) ENGINE=InnoDB COMMENT='연결 고객 관리';

CREATE TABLE `service_manager` (
  `service_no` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '일련번호',
  `service_id` varchar(60) NOT NULL COMMENT '서비스 ID',
  `name` varchar(100) NOT NULL COMMENT '서비스 이름',
  `description` varchar(2000) DEFAULT NULL COMMENT '설명',
  `updated` datetime DEFAULT NULL COMMENT '수정시간',
  `created` datetime NOT NULL COMMENT '생성 시간',
  PRIMARY KEY (`service_no`),
  UNIQUE KEY `UKEY1` (`service_id`),
  UNIQUE KEY `UKEY2` (`name`)
) ENGINE=InnoDB COMMENT='서비스 관리';

INSERT INTO `service_manager` (`service_no`,`service_id`,`name`,`description`,`updated`,`created`) VALUES (1,'star-chatbot','스타 챗봇','스타가 만드는 챗봇',NULL,'2022-04-20 00:57:56');
