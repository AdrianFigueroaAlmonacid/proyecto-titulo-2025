
    create table category (
        store_id_store integer,
        category_id bigint not null auto_increment,
        name varchar(255) not null,
        primary key (category_id)
    ) engine=InnoDB;

    create table product (
        expiration_date date not null,
        price float(53) not null,
        quantity integer not null,
        sell_price float(53) not null,
        category_category_id bigint,
        id_product bigint not null auto_increment,
        name varchar(255) not null,
        primary key (id_product)
    ) engine=InnoDB;

    create table store (
        id_store integer not null auto_increment,
        name varchar(255) not null,
        primary key (id_store)
    ) engine=InnoDB;

    create table user (
        disabled TINYINT not null,
        id_users integer not null auto_increment,
        locked TINYINT not null,
        register_date date,
        store_id_store integer,
        password varchar(200) not null,
        email varchar(255) not null,
        last_name varchar(255),
        name varchar(255) not null,
        username varchar(255) not null,
        primary key (id_users)
    ) engine=InnoDB;

    create table user_role (
        granted_date DATETIME not null,
        role varchar(20) not null,
        username varchar(20) not null,
        primary key (role, username)
    ) engine=InnoDB;

    alter table category 
       add constraint UK46ccwnsi9409t36lurvtyljak unique (name);

    alter table user 
       add constraint UKob8kqyqqgmefl0aco34akdtpe unique (email);

    alter table user 
       add constraint UKsb8bbouer5wak8vyiiy4pf2bx unique (username);

    alter table category 
       add constraint FKtcvnge5db7x51ilite70k1mbl 
       foreign key (store_id_store) 
       references store (id_store);

    alter table product 
       add constraint FKle1pobdrc8a2uw97gukfmvan4 
       foreign key (category_category_id) 
       references category (category_id);

    alter table user 
       add constraint FKg77w9jm3e5gis0rp2yg3dbc27 
       foreign key (store_id_store) 
       references store (id_store);

    alter table user_role 
       add constraint FKnircs1pyebpo0eucojumm0aed 
       foreign key (username) 
       references user (username);

    create table category (
        store_id_store integer,
        category_id bigint not null auto_increment,
        name varchar(255) not null,
        primary key (category_id)
    ) engine=InnoDB;

    create table product (
        expiration_date date not null,
        price float(53) not null,
        quantity integer not null,
        sell_price float(53) not null,
        category_category_id bigint,
        id_product bigint not null auto_increment,
        name varchar(255) not null,
        primary key (id_product)
    ) engine=InnoDB;

    create table store (
        id_store integer not null auto_increment,
        name varchar(255) not null,
        primary key (id_store)
    ) engine=InnoDB;

    create table user (
        disabled TINYINT not null,
        id_users integer not null auto_increment,
        locked TINYINT not null,
        register_date date,
        store_id_store integer,
        password varchar(200) not null,
        email varchar(255) not null,
        last_name varchar(255),
        name varchar(255) not null,
        username varchar(255) not null,
        primary key (id_users)
    ) engine=InnoDB;

    create table user_role (
        granted_date DATETIME not null,
        role varchar(20) not null,
        username varchar(20) not null,
        primary key (role, username)
    ) engine=InnoDB;

    alter table category 
       add constraint UK46ccwnsi9409t36lurvtyljak unique (name);

    alter table user 
       add constraint UKob8kqyqqgmefl0aco34akdtpe unique (email);

    alter table user 
       add constraint UKsb8bbouer5wak8vyiiy4pf2bx unique (username);

    alter table category 
       add constraint FKtcvnge5db7x51ilite70k1mbl 
       foreign key (store_id_store) 
       references store (id_store);

    alter table product 
       add constraint FKle1pobdrc8a2uw97gukfmvan4 
       foreign key (category_category_id) 
       references category (category_id);

    alter table user 
       add constraint FKg77w9jm3e5gis0rp2yg3dbc27 
       foreign key (store_id_store) 
       references store (id_store);

    alter table user_role 
       add constraint FKnircs1pyebpo0eucojumm0aed 
       foreign key (username) 
       references user (username);

    create table category (
        store_id_store integer,
        category_id bigint not null auto_increment,
        name varchar(255) not null,
        primary key (category_id)
    ) engine=InnoDB;

    create table product (
        expiration_date date not null,
        price float(53) not null,
        quantity integer not null,
        sell_price float(53) not null,
        category_category_id bigint,
        id_product bigint not null auto_increment,
        name varchar(255) not null,
        primary key (id_product)
    ) engine=InnoDB;

    create table store (
        id_store integer not null auto_increment,
        name varchar(255) not null,
        primary key (id_store)
    ) engine=InnoDB;

    create table user (
        disabled TINYINT not null,
        id_users integer not null auto_increment,
        locked TINYINT not null,
        register_date date,
        store_id_store integer,
        password varchar(200) not null,
        email varchar(255) not null,
        last_name varchar(255),
        name varchar(255) not null,
        username varchar(255) not null,
        primary key (id_users)
    ) engine=InnoDB;

    create table user_role (
        granted_date DATETIME not null,
        role varchar(20) not null,
        username varchar(20) not null,
        primary key (role, username)
    ) engine=InnoDB;

    alter table category 
       add constraint UK46ccwnsi9409t36lurvtyljak unique (name);

    alter table user 
       add constraint UKob8kqyqqgmefl0aco34akdtpe unique (email);

    alter table user 
       add constraint UKsb8bbouer5wak8vyiiy4pf2bx unique (username);

    alter table category 
       add constraint FKtcvnge5db7x51ilite70k1mbl 
       foreign key (store_id_store) 
       references store (id_store);

    alter table product 
       add constraint FKle1pobdrc8a2uw97gukfmvan4 
       foreign key (category_category_id) 
       references category (category_id);

    alter table user 
       add constraint FKg77w9jm3e5gis0rp2yg3dbc27 
       foreign key (store_id_store) 
       references store (id_store);

    alter table user_role 
       add constraint FKnircs1pyebpo0eucojumm0aed 
       foreign key (username) 
       references user (username);

    create table category (
        store_id_store integer,
        category_id bigint not null auto_increment,
        name varchar(255) not null,
        primary key (category_id)
    ) engine=InnoDB;

    create table product (
        expiration_date date not null,
        price float(53) not null,
        quantity integer not null,
        sell_price float(53) not null,
        category_category_id bigint,
        id_product bigint not null auto_increment,
        name varchar(255) not null,
        primary key (id_product)
    ) engine=InnoDB;

    create table store (
        id_store integer not null auto_increment,
        name varchar(255) not null,
        primary key (id_store)
    ) engine=InnoDB;

    create table user (
        disabled TINYINT not null,
        id_users integer not null auto_increment,
        locked TINYINT not null,
        register_date date,
        store_id_store integer,
        password varchar(200) not null,
        email varchar(255) not null,
        last_name varchar(255),
        name varchar(255) not null,
        username varchar(255) not null,
        primary key (id_users)
    ) engine=InnoDB;

    create table user_role (
        granted_date DATETIME not null,
        role varchar(20) not null,
        username varchar(20) not null,
        primary key (role, username)
    ) engine=InnoDB;

    alter table user 
       add constraint UKob8kqyqqgmefl0aco34akdtpe unique (email);

    alter table user 
       add constraint UKsb8bbouer5wak8vyiiy4pf2bx unique (username);

    alter table category 
       add constraint FKtcvnge5db7x51ilite70k1mbl 
       foreign key (store_id_store) 
       references store (id_store);

    alter table product 
       add constraint FKle1pobdrc8a2uw97gukfmvan4 
       foreign key (category_category_id) 
       references category (category_id);

    alter table user 
       add constraint FKg77w9jm3e5gis0rp2yg3dbc27 
       foreign key (store_id_store) 
       references store (id_store);

    alter table user_role 
       add constraint FKnircs1pyebpo0eucojumm0aed 
       foreign key (username) 
       references user (username);

    create table category (
        store_id_store integer,
        category_id bigint not null auto_increment,
        name varchar(255) not null,
        primary key (category_id)
    ) engine=InnoDB;

    create table product (
        expiration_date date not null,
        price float(53) not null,
        quantity integer not null,
        sell_price float(53) not null,
        category_category_id bigint,
        id_product bigint not null auto_increment,
        name varchar(255) not null,
        primary key (id_product)
    ) engine=InnoDB;

    create table store (
        id_store integer not null auto_increment,
        name varchar(255) not null,
        primary key (id_store)
    ) engine=InnoDB;

    create table user (
        disabled TINYINT not null,
        id_users integer not null auto_increment,
        locked TINYINT not null,
        register_date date,
        store_id_store integer,
        password varchar(200) not null,
        email varchar(255) not null,
        last_name varchar(255),
        name varchar(255) not null,
        username varchar(255) not null,
        primary key (id_users)
    ) engine=InnoDB;

    create table user_role (
        granted_date DATETIME not null,
        role varchar(20) not null,
        username varchar(20) not null,
        primary key (role, username)
    ) engine=InnoDB;

    alter table user 
       add constraint UKob8kqyqqgmefl0aco34akdtpe unique (email);

    alter table user 
       add constraint UKsb8bbouer5wak8vyiiy4pf2bx unique (username);

    alter table category 
       add constraint FKtcvnge5db7x51ilite70k1mbl 
       foreign key (store_id_store) 
       references store (id_store);

    alter table product 
       add constraint FKle1pobdrc8a2uw97gukfmvan4 
       foreign key (category_category_id) 
       references category (category_id);

    alter table user 
       add constraint FKg77w9jm3e5gis0rp2yg3dbc27 
       foreign key (store_id_store) 
       references store (id_store);

    alter table user_role 
       add constraint FKnircs1pyebpo0eucojumm0aed 
       foreign key (username) 
       references user (username);

    create table category (
        store_id_store integer,
        category_id bigint not null auto_increment,
        name varchar(255) not null,
        primary key (category_id)
    ) engine=InnoDB;

    create table product (
        expiration_date date not null,
        price float(53) not null,
        quantity integer not null,
        sell_price float(53) not null,
        category_category_id bigint,
        id_product bigint not null auto_increment,
        name varchar(255) not null,
        primary key (id_product)
    ) engine=InnoDB;

    create table store (
        id_store integer not null auto_increment,
        name varchar(255) not null,
        primary key (id_store)
    ) engine=InnoDB;

    create table user (
        disabled TINYINT not null,
        id_users integer not null auto_increment,
        locked TINYINT not null,
        register_date date,
        store_id_store integer,
        password varchar(200) not null,
        email varchar(255) not null,
        last_name varchar(255),
        name varchar(255) not null,
        username varchar(255) not null,
        primary key (id_users)
    ) engine=InnoDB;

    create table user_role (
        granted_date DATETIME not null,
        role varchar(20) not null,
        username varchar(20) not null,
        primary key (role, username)
    ) engine=InnoDB;

    alter table user 
       add constraint UKob8kqyqqgmefl0aco34akdtpe unique (email);

    alter table user 
       add constraint UKsb8bbouer5wak8vyiiy4pf2bx unique (username);

    alter table category 
       add constraint FKtcvnge5db7x51ilite70k1mbl 
       foreign key (store_id_store) 
       references store (id_store);

    alter table product 
       add constraint FKle1pobdrc8a2uw97gukfmvan4 
       foreign key (category_category_id) 
       references category (category_id);

    alter table user 
       add constraint FKg77w9jm3e5gis0rp2yg3dbc27 
       foreign key (store_id_store) 
       references store (id_store);

    alter table user_role 
       add constraint FKnircs1pyebpo0eucojumm0aed 
       foreign key (username) 
       references user (username);

    create table category (
        store_id_store integer,
        category_id bigint not null auto_increment,
        name varchar(255) not null,
        primary key (category_id)
    ) engine=InnoDB;

    create table product (
        expiration_date date not null,
        price float(53) not null,
        quantity integer not null,
        sell_price float(53) not null,
        category_category_id bigint,
        id_product bigint not null auto_increment,
        name varchar(255) not null,
        primary key (id_product)
    ) engine=InnoDB;

    create table store (
        id_store integer not null auto_increment,
        name varchar(255) not null,
        primary key (id_store)
    ) engine=InnoDB;

    create table user (
        disabled TINYINT not null,
        id_users integer not null auto_increment,
        locked TINYINT not null,
        register_date date,
        store_id_store integer,
        password varchar(200) not null,
        email varchar(255) not null,
        last_name varchar(255),
        name varchar(255) not null,
        username varchar(255) not null,
        primary key (id_users)
    ) engine=InnoDB;

    create table user_role (
        granted_date DATETIME not null,
        role varchar(20) not null,
        username varchar(20) not null,
        primary key (role, username)
    ) engine=InnoDB;

    alter table user 
       add constraint UKob8kqyqqgmefl0aco34akdtpe unique (email);

    alter table user 
       add constraint UKsb8bbouer5wak8vyiiy4pf2bx unique (username);

    alter table category 
       add constraint FKtcvnge5db7x51ilite70k1mbl 
       foreign key (store_id_store) 
       references store (id_store);

    alter table product 
       add constraint FKle1pobdrc8a2uw97gukfmvan4 
       foreign key (category_category_id) 
       references category (category_id);

    alter table user 
       add constraint FKg77w9jm3e5gis0rp2yg3dbc27 
       foreign key (store_id_store) 
       references store (id_store);

    alter table user_role 
       add constraint FKnircs1pyebpo0eucojumm0aed 
       foreign key (username) 
       references user (username);

    create table category (
        store_id_store integer,
        category_id bigint not null auto_increment,
        name varchar(255) not null,
        primary key (category_id)
    ) engine=InnoDB;

    create table product (
        expiration_date date not null,
        price float(53) not null,
        quantity integer not null,
        sell_price float(53) not null,
        category_category_id bigint,
        id_product bigint not null auto_increment,
        name varchar(255) not null,
        primary key (id_product)
    ) engine=InnoDB;

    create table store (
        id_store integer not null auto_increment,
        name varchar(255) not null,
        primary key (id_store)
    ) engine=InnoDB;

    create table user (
        disabled TINYINT not null,
        id_users integer not null auto_increment,
        locked TINYINT not null,
        register_date date,
        store_id_store integer,
        password varchar(200) not null,
        email varchar(255) not null,
        last_name varchar(255),
        name varchar(255) not null,
        username varchar(255) not null,
        primary key (id_users)
    ) engine=InnoDB;

    create table user_role (
        granted_date DATETIME not null,
        role varchar(20) not null,
        username varchar(20) not null,
        primary key (role, username)
    ) engine=InnoDB;

    alter table user 
       add constraint UKob8kqyqqgmefl0aco34akdtpe unique (email);

    alter table user 
       add constraint UKsb8bbouer5wak8vyiiy4pf2bx unique (username);

    alter table category 
       add constraint FKtcvnge5db7x51ilite70k1mbl 
       foreign key (store_id_store) 
       references store (id_store);

    alter table product 
       add constraint FKle1pobdrc8a2uw97gukfmvan4 
       foreign key (category_category_id) 
       references category (category_id);

    alter table user 
       add constraint FKg77w9jm3e5gis0rp2yg3dbc27 
       foreign key (store_id_store) 
       references store (id_store);

    alter table user_role 
       add constraint FKnircs1pyebpo0eucojumm0aed 
       foreign key (username) 
       references user (username);

    create table category (
        store_id_store integer,
        category_id bigint not null auto_increment,
        name varchar(255) not null,
        primary key (category_id)
    ) engine=InnoDB;

    create table product (
        expiration_date date not null,
        price float(53) not null,
        quantity integer not null,
        sell_price float(53) not null,
        category_category_id bigint,
        id_product bigint not null auto_increment,
        name varchar(255) not null,
        primary key (id_product)
    ) engine=InnoDB;

    create table store (
        id_store integer not null auto_increment,
        name varchar(255) not null,
        primary key (id_store)
    ) engine=InnoDB;

    create table user (
        disabled TINYINT not null,
        id_users integer not null auto_increment,
        locked TINYINT not null,
        register_date date,
        store_id_store integer,
        password varchar(200) not null,
        email varchar(255) not null,
        last_name varchar(255),
        name varchar(255) not null,
        username varchar(255) not null,
        primary key (id_users)
    ) engine=InnoDB;

    create table user_role (
        granted_date DATETIME not null,
        role varchar(20) not null,
        username varchar(20) not null,
        primary key (role, username)
    ) engine=InnoDB;

    alter table user 
       add constraint UKob8kqyqqgmefl0aco34akdtpe unique (email);

    alter table user 
       add constraint UKsb8bbouer5wak8vyiiy4pf2bx unique (username);

    alter table category 
       add constraint FKtcvnge5db7x51ilite70k1mbl 
       foreign key (store_id_store) 
       references store (id_store);

    alter table product 
       add constraint FKle1pobdrc8a2uw97gukfmvan4 
       foreign key (category_category_id) 
       references category (category_id);

    alter table user 
       add constraint FKg77w9jm3e5gis0rp2yg3dbc27 
       foreign key (store_id_store) 
       references store (id_store);

    alter table user_role 
       add constraint FKnircs1pyebpo0eucojumm0aed 
       foreign key (username) 
       references user (username);

    create table category (
        store_id_store integer,
        category_id bigint not null auto_increment,
        name varchar(255) not null,
        primary key (category_id)
    ) engine=InnoDB;

    create table product (
        expiration_date date not null,
        price float(53) not null,
        quantity integer not null,
        sell_price float(53) not null,
        category_category_id bigint,
        id_product bigint not null auto_increment,
        name varchar(255) not null,
        primary key (id_product)
    ) engine=InnoDB;

    create table store (
        id_store integer not null auto_increment,
        name varchar(255) not null,
        primary key (id_store)
    ) engine=InnoDB;

    create table user (
        disabled TINYINT not null,
        id_users integer not null auto_increment,
        locked TINYINT not null,
        register_date date,
        store_id_store integer,
        password varchar(200) not null,
        email varchar(255) not null,
        last_name varchar(255),
        name varchar(255) not null,
        username varchar(255) not null,
        primary key (id_users)
    ) engine=InnoDB;

    create table user_role (
        granted_date DATETIME not null,
        role varchar(20) not null,
        username varchar(20) not null,
        primary key (role, username)
    ) engine=InnoDB;

    alter table user 
       add constraint UKob8kqyqqgmefl0aco34akdtpe unique (email);

    alter table user 
       add constraint UKsb8bbouer5wak8vyiiy4pf2bx unique (username);

    alter table category 
       add constraint FKtcvnge5db7x51ilite70k1mbl 
       foreign key (store_id_store) 
       references store (id_store);

    alter table product 
       add constraint FKle1pobdrc8a2uw97gukfmvan4 
       foreign key (category_category_id) 
       references category (category_id);

    alter table user 
       add constraint FKg77w9jm3e5gis0rp2yg3dbc27 
       foreign key (store_id_store) 
       references store (id_store);

    alter table user_role 
       add constraint FKnircs1pyebpo0eucojumm0aed 
       foreign key (username) 
       references user (username);

    create table category (
        store_id_store integer,
        category_id bigint not null auto_increment,
        name varchar(255) not null,
        primary key (category_id)
    ) engine=InnoDB;

    create table product (
        expiration_date date not null,
        price float(53) not null,
        quantity integer not null,
        sell_price float(53) not null,
        category_category_id bigint,
        id_product bigint not null auto_increment,
        name varchar(255) not null,
        primary key (id_product)
    ) engine=InnoDB;

    create table store (
        id_store integer not null auto_increment,
        name varchar(255) not null,
        primary key (id_store)
    ) engine=InnoDB;

    create table user (
        disabled TINYINT not null,
        id_users integer not null auto_increment,
        locked TINYINT not null,
        register_date date,
        store_id_store integer,
        password varchar(200) not null,
        email varchar(255) not null,
        last_name varchar(255),
        name varchar(255) not null,
        username varchar(255) not null,
        primary key (id_users)
    ) engine=InnoDB;

    create table user_role (
        granted_date DATETIME not null,
        role varchar(20) not null,
        username varchar(20) not null,
        primary key (role, username)
    ) engine=InnoDB;

    alter table user 
       add constraint UKob8kqyqqgmefl0aco34akdtpe unique (email);

    alter table user 
       add constraint UKsb8bbouer5wak8vyiiy4pf2bx unique (username);

    alter table category 
       add constraint FKtcvnge5db7x51ilite70k1mbl 
       foreign key (store_id_store) 
       references store (id_store);

    alter table product 
       add constraint FKle1pobdrc8a2uw97gukfmvan4 
       foreign key (category_category_id) 
       references category (category_id);

    alter table user 
       add constraint FKg77w9jm3e5gis0rp2yg3dbc27 
       foreign key (store_id_store) 
       references store (id_store);

    alter table user_role 
       add constraint FKnircs1pyebpo0eucojumm0aed 
       foreign key (username) 
       references user (username);

    create table category (
        store_id_store integer,
        category_id bigint not null auto_increment,
        name varchar(255) not null,
        primary key (category_id)
    ) engine=InnoDB;

    create table product (
        expiration_date date not null,
        price float(53) not null,
        quantity integer not null,
        sell_price float(53) not null,
        category_category_id bigint,
        id_product bigint not null auto_increment,
        name varchar(255) not null,
        primary key (id_product)
    ) engine=InnoDB;

    create table store (
        id_store integer not null auto_increment,
        name varchar(255) not null,
        primary key (id_store)
    ) engine=InnoDB;

    create table user (
        disabled TINYINT not null,
        id_users integer not null auto_increment,
        locked TINYINT not null,
        register_date date,
        store_id_store integer,
        password varchar(200) not null,
        email varchar(255) not null,
        last_name varchar(255),
        name varchar(255) not null,
        username varchar(255) not null,
        primary key (id_users)
    ) engine=InnoDB;

    create table user_role (
        granted_date DATETIME not null,
        role varchar(20) not null,
        username varchar(20) not null,
        primary key (role, username)
    ) engine=InnoDB;

    alter table user 
       add constraint UKob8kqyqqgmefl0aco34akdtpe unique (email);

    alter table user 
       add constraint UKsb8bbouer5wak8vyiiy4pf2bx unique (username);

    alter table category 
       add constraint FKtcvnge5db7x51ilite70k1mbl 
       foreign key (store_id_store) 
       references store (id_store);

    alter table product 
       add constraint FKle1pobdrc8a2uw97gukfmvan4 
       foreign key (category_category_id) 
       references category (category_id);

    alter table user 
       add constraint FKg77w9jm3e5gis0rp2yg3dbc27 
       foreign key (store_id_store) 
       references store (id_store);

    alter table user_role 
       add constraint FKnircs1pyebpo0eucojumm0aed 
       foreign key (username) 
       references user (username);

    create table category (
        store_id_store integer,
        category_id bigint not null auto_increment,
        name varchar(255) not null,
        primary key (category_id)
    ) engine=InnoDB;

    create table product (
        expiration_date date not null,
        price float(53) not null,
        quantity integer not null,
        sell_price float(53) not null,
        category_category_id bigint,
        id_product bigint not null auto_increment,
        name varchar(255) not null,
        primary key (id_product)
    ) engine=InnoDB;

    create table store (
        id_store integer not null auto_increment,
        name varchar(255) not null,
        primary key (id_store)
    ) engine=InnoDB;

    create table user (
        disabled TINYINT not null,
        id_users integer not null auto_increment,
        locked TINYINT not null,
        register_date date,
        store_id_store integer,
        password varchar(200) not null,
        email varchar(255) not null,
        last_name varchar(255),
        name varchar(255) not null,
        username varchar(255) not null,
        primary key (id_users)
    ) engine=InnoDB;

    create table user_role (
        granted_date DATETIME not null,
        role varchar(20) not null,
        username varchar(20) not null,
        primary key (role, username)
    ) engine=InnoDB;

    alter table user 
       add constraint UKob8kqyqqgmefl0aco34akdtpe unique (email);

    alter table user 
       add constraint UKsb8bbouer5wak8vyiiy4pf2bx unique (username);

    alter table category 
       add constraint FKtcvnge5db7x51ilite70k1mbl 
       foreign key (store_id_store) 
       references store (id_store);

    alter table product 
       add constraint FKle1pobdrc8a2uw97gukfmvan4 
       foreign key (category_category_id) 
       references category (category_id);

    alter table user 
       add constraint FKg77w9jm3e5gis0rp2yg3dbc27 
       foreign key (store_id_store) 
       references store (id_store);

    alter table user_role 
       add constraint FKnircs1pyebpo0eucojumm0aed 
       foreign key (username) 
       references user (username);
