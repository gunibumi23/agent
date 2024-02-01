CREATE TABLE IF NOT EXISTS `users` (
  `user_cd` varchar(100) NOT NULL COMMENT '사용자사번,이메일',
  `user_pw` varchar(128) NOT NULL COMMENT '비밀번호',
  `user_nm` varchar(100) NOT NULL COMMENT '사용자이름',
  `user_sts` varchar(100) NOT NULL COMMENT '사용자이름',
  `team_cd` varchar(100) DEFAULT NULL COMMENT '팀ID',
  `user_ph` varchar(100) DEFAULT NULL COMMENT '사용자전화번호',
  `use_yn` varchar(1) DEFAULT 'Y' COMMENT '사용여부',
  `del_yn`  varchar(1) DEFAULT 'N' COMMENT '삭제여부',
  `created_dt` datetime NOT NULL COMMENT '생성일시',
  `created_cd` varchar(100) NOT NULL COMMENT '생성자',
  `updated_dt` datetime NOT NULL COMMENT '수정일시',
  `updated_cd` varchar(100) NOT NULL COMMENT '수정자',
  PRIMARY KEY (`user_cd`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
COMMENT '사용자';


CREATE TABLE IF NOT EXISTS `products` (
  `prod_cd` varchar(100) NOT NULL COMMENT '상품',
  `prod_nm` varchar(128) NOT NULL COMMENT '상품명',
  `prod_cate` varchar(128) NOT NULL COMMENT '상품',
  `use_yn` varchar(1) DEFAULT 'Y' COMMENT '사용여부',
  `del_yn`  varchar(1) DEFAULT 'N' COMMENT '삭제여부',
  `created_dt` datetime NOT NULL COMMENT '생성일시',
  `created_cd` varchar(100) NOT NULL COMMENT '생성자',
  `updated_dt` datetime NOT NULL COMMENT '수정일시',
  `updated_cd` varchar(100) NOT NULL COMMENT '수정자',
  PRIMARY KEY (`prod_cd`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
COMMENT '상품';



CREATE TABLE IF NOT EXISTS `products_prc` (
  `prod_cd` varchar(100) NOT NULL COMMENT '상품',
  `prod_pr_dt` varchar(128) NOT NULL COMMENT '상품구매일자',
  `prod_pr_prc` varchar(128) NOT NULL COMMENT '상품구매가격',  
  `del_yn`  varchar(1) DEFAULT 'N' COMMENT '삭제여부',
  `created_dt` datetime NOT NULL COMMENT '생성일시',
  `created_cd` varchar(100) NOT NULL COMMENT '생성자',
  `updated_dt` datetime NOT NULL COMMENT '수정일시',
  `updated_cd` varchar(100) NOT NULL COMMENT '수정자',
  PRIMARY KEY (`prod_cd`,`prod_pr_dt`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
COMMENT '상품가격';


