-- CREATE USER 'wirebarley'@'%' IDENTIFIED BY '1234';
-- GRANT ALL ON *.* TO 'wirebarley'@'%';

CREATE DATABASE wirebarley;

create table wirebarley.bank_accounts
(
    bank_account_no     bigint               not null
        primary key,
    member_no           bigint               not null,
    bank_no             bigint               not null,
    bank_account_number int                  not null,
    is_deleted          tinyint(1) default 0 not null,
    created_at          datetime             not null,
    updated_at          datetime             null,
    constraint bank_accounts_pk
        unique (bank_no, member_no, bank_account_number)
);


create table wirebarley.banks
(
    bank_no    bigint      not null
        primary key,
    bank_name  varchar(10) not null,
    created_at datetime    not null,
    updated_at datetime    null
);

INSERT INTO wirebarley.banks (bank_no, bank_name, created_at, updated_at) VALUES (140135180074508285, '우리은행', '2025-01-22 13:06:18', '2025-01-22 13:06:18');

create table wirebarley.member_role_mapping
(
    member_role_mapping_no bigint   not null
        primary key,
    member_no              bigint   null,
    role_no                bigint   null,
    response_time          datetime null,
    created_at             datetime null,
    updated_at             datetime null,
    constraint UK_2l8w3e6e6l4pyxs9fh1hnk236
        unique (member_no)
);

create table wirebarley.members
(
    member_no  bigint       not null
        primary key,
    id         varchar(50)  not null,
    name       varchar(50)  null,
    password   varchar(255) not null,
    created_at datetime     not null,
    updated_at datetime     null,
    constraint members_pk
        unique (id)
);

create table wirebarley.resources
(
    resource_no   bigint                                         not null
        primary key,
    resource_name varchar(255)                                   null,
    http_method   enum ('GET', 'POST', 'PUT', 'PATCH', 'DELETE') not null,
    resource_type enum ('URL')                                   not null,
    order_num     int                                            null,
    created_at    datetime                                       null,
    updated_at    datetime                                       null
);

INSERT INTO wirebarley.resources (resource_no, resource_name, http_method, resource_type, order_num, created_at, updated_at) VALUES (140135180074508279, '/banks', 'GET', 'URL', 1, '2025-01-21 21:10:57', '2025-01-21 21:10:57');
INSERT INTO wirebarley.resources (resource_no, resource_name, http_method, resource_type, order_num, created_at, updated_at) VALUES (140135180074508280, '/bank-accounts', 'GET', 'URL', 2, '2025-01-21 21:10:57', '2025-01-21 21:10:57');
INSERT INTO wirebarley.resources (resource_no, resource_name, http_method, resource_type, order_num, created_at, updated_at) VALUES (140135180074508281, '/bank-accounts/banks/{baeldung:^[a-zA-Z0-9-_]+$}', 'POST', 'URL', 3, '2025-01-21 21:10:57', '2025-01-21 21:10:57');
INSERT INTO wirebarley.resources (resource_no, resource_name, http_method, resource_type, order_num, created_at, updated_at) VALUES (140135180074508282, '/bank-accounts/{baeldung:^[a-zA-Z0-9-_]+$}', 'DELETE', 'URL', 4, '2025-01-21 21:10:57', '2025-01-21 21:10:57');
INSERT INTO wirebarley.resources (resource_no, resource_name, http_method, resource_type, order_num, created_at, updated_at) VALUES (140135180074508283, '/wallets/balance', 'GET', 'URL', 5, '2025-01-21 21:10:57', '2025-01-21 21:10:57');
INSERT INTO wirebarley.resources (resource_no, resource_name, http_method, resource_type, order_num, created_at, updated_at) VALUES (140135180074508284, '/wallets/transaction', 'GET', 'URL', 6, '2025-01-21 21:10:57', '2025-01-21 21:10:57');
INSERT INTO wirebarley.resources (resource_no, resource_name, http_method, resource_type, order_num, created_at, updated_at) VALUES (140135180074508285, '/wallets/bank-accounts/{baeldung:^[a-zA-Z0-9-_]+$}/deposit', 'POST', 'URL', 7, '2025-01-21 21:10:57', '2025-01-21 21:10:57');
INSERT INTO wirebarley.resources (resource_no, resource_name, http_method, resource_type, order_num, created_at, updated_at) VALUES (140135180074508286, '/wallets/bank-accounts/{baeldung:^[a-zA-Z0-9-_]+$}/withdraw', 'POST', 'URL', 8, '2025-01-21 21:10:57', '2025-01-21 21:10:57');
INSERT INTO wirebarley.resources (resource_no, resource_name, http_method, resource_type, order_num, created_at, updated_at) VALUES (140135180074508287, '/transfer-transactions', 'GET', 'URL', 9, '2025-01-21 21:10:57', '2025-01-21 21:10:57');
INSERT INTO wirebarley.resources (resource_no, resource_name, http_method, resource_type, order_num, created_at, updated_at) VALUES (140135180074508288, '/transfer-transactions', 'POST', 'URL', 10, '2025-01-21 21:10:57', '2025-01-21 21:10:57');

create table wirebarley.role_hierarchys
(
    role_hierarchy_no bigint      not null
        primary key,
    role_name         varchar(30) null,
    parent_no         bigint      null,
    created_at        datetime    null,
    updated_at        datetime    null
);

create table wirebarley.role_resources_mapping
(
    role_resources_mapping_no bigint   not null
        primary key,
    role_no                   bigint   null,
    resource_no               bigint   null,
    created_at                datetime null,
    updated_at                datetime null
);

INSERT INTO wirebarley.role_resources_mapping (role_resources_mapping_no, role_no, resource_no, created_at, updated_at) VALUES (141147412121595904, 141147433738805251, 140135180074508279, '2025-01-21 22:08:12', '2025-01-21 22:08:12');
INSERT INTO wirebarley.role_resources_mapping (role_resources_mapping_no, role_no, resource_no, created_at, updated_at) VALUES (141147412121595905, 141147433738805251, 140135180074508280, '2025-01-21 22:08:12', '2025-01-21 22:08:12');
INSERT INTO wirebarley.role_resources_mapping (role_resources_mapping_no, role_no, resource_no, created_at, updated_at) VALUES (141147412121595907, 141147433738805251, 140135180074508281, '2025-01-21 22:08:12', '2025-01-21 22:08:12');
INSERT INTO wirebarley.role_resources_mapping (role_resources_mapping_no, role_no, resource_no, created_at, updated_at) VALUES (141147412121595908, 141147433738805251, 140135180074508282, '2025-01-21 22:08:12', '2025-01-21 22:08:12');
INSERT INTO wirebarley.role_resources_mapping (role_resources_mapping_no, role_no, resource_no, created_at, updated_at) VALUES (141147412121595909, 141147433738805251, 140135180074508283, '2025-01-21 22:08:12', '2025-01-21 22:08:12');
INSERT INTO wirebarley.role_resources_mapping (role_resources_mapping_no, role_no, resource_no, created_at, updated_at) VALUES (141147412121595910, 141147433738805251, 140135180074508284, '2025-01-21 22:08:12', '2025-01-21 22:08:12');
INSERT INTO wirebarley.role_resources_mapping (role_resources_mapping_no, role_no, resource_no, created_at, updated_at) VALUES (141147412121595911, 141147433738805251, 140135180074508285, '2025-01-21 22:08:12', '2025-01-21 22:08:12');
INSERT INTO wirebarley.role_resources_mapping (role_resources_mapping_no, role_no, resource_no, created_at, updated_at) VALUES (141147412121595912, 141147433738805251, 140135180074508286, '2025-01-21 22:08:12', '2025-01-21 22:08:12');
INSERT INTO wirebarley.role_resources_mapping (role_resources_mapping_no, role_no, resource_no, created_at, updated_at) VALUES (141147412121595913, 141147433738805251, 140135180074508287, '2025-01-21 22:08:12', '2025-01-21 22:08:12');
INSERT INTO wirebarley.role_resources_mapping (role_resources_mapping_no, role_no, resource_no, created_at, updated_at) VALUES (141147412121595914, 141147433738805251, 140135180074508288, '2025-01-21 22:08:12', '2025-01-21 22:08:12');

create table wirebarley.roles
(
    role_no    bigint      not null
        primary key,
    role_name  varchar(30) null,
    created_at datetime    null,
    updated_at datetime    null
);

INSERT INTO wirebarley.roles (role_no, role_name, created_at, updated_at) VALUES (141147433738805248, 'ROLE_SUPER_ADMIN', '2025-01-21 21:09:42', '2025-01-21 21:09:42');
INSERT INTO wirebarley.roles (role_no, role_name, created_at, updated_at) VALUES (141147433738805249, 'ROLE_ADMIN', '2025-01-21 21:09:42', '2025-01-21 21:09:42');
INSERT INTO wirebarley.roles (role_no, role_name, created_at, updated_at) VALUES (141147433738805250, 'ROLE_CLIENT', '2025-01-21 21:09:42', '2025-01-21 21:09:42');
INSERT INTO wirebarley.roles (role_no, role_name, created_at, updated_at) VALUES (141147433738805251, 'ROLE_USER', '2025-01-21 21:09:42', '2025-01-21 21:09:42');

create table wirebarley.wallet_transactions
(
    wallet_transaction_no bigint                                not null
        primary key,
    wallet_no             bigint                                not null,
    bank_no               bigint                                not null,
    bank_account_number   int                                   not null,
    amount                decimal(38, 2)                        not null,
    type                  enum ('DEPOSIT', 'WITHDRAW') not null,
    created_at            datetime                              not null,
    updated_at            datetime                              null
);

create index wallet_no_type_created_at_amount_index
    on wirebarley.wallet_transactions (wallet_no, type, created_at, amount);

create table wirebarley.wallets
(
    wallet_no  bigint                      not null
        primary key,
    member_no  bigint                      not null,
    balance    decimal(38, 2) default 0.00 not null,
    version    int            default 0    not null,
    created_at datetime                    not null,
    updated_at datetime                    null,
    constraint member_no
        unique (member_no)
);

create table wirebarley.transfer_transactions
(
    transfer_transaction_no bigint   not null
        primary key,
    from_member_no              bigint  not null,
    from_bank_account_no                bigint  not null,
    to_bank_no                bigint not  null,
    to_bank_account_number                int not  null,
    transfer_amount         decimal  not null,
    created_at             datetime not null,
    updated_at             datetime null
);
create index transfer_transactions_index
    on wirebarley.transfer_transactions (from_member_no asc, transfer_transaction_no desc, created_at desc);