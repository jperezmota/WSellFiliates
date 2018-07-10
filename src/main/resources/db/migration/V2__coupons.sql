create table asigned_coupons(
	id bigint(20) not null primary key auto_increment,
	coupon varchar(20) not null,
	asigned_to varchar(50) not null,
	enabled boolean default true,
	deleted boolean default false,
	deleted_by varchar(50),
	deleted_date date,
	constraint fk_coupons_users foreign key(asigned_to) references users(username),
	constraint fk_coupons_deletedby_users foreign key(deleted_by) references users(username)
);
create unique index ix_coupon_username on asigned_coupons (asigned_to);

INSERT INTO asigned_coupons (coupon, asigned_to)
VALUES 
('admincoupon', 'admin');