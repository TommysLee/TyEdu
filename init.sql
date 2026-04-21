DROP TABLE IF EXISTS t_rs_book;
DROP TABLE IF EXISTS t_rs_book_chapter;
DROP TABLE IF EXISTS t_rs_knowledge;
DROP TABLE IF EXISTS t_rs_que_bank;
DROP TABLE IF EXISTS t_rs_que_ref_chapter;
DROP TABLE IF EXISTS t_rs_que_ref_knowledge;

CREATE TABLE t_rs_book (
    b_id        INTEGER PRIMARY KEY AUTOINCREMENT,
    b_name      TEXT,
    stage       TEXT,
    subject     TEXT,
    remark      TEXT,
    create_time TEXT,
    update_time TEXT
);

CREATE TABLE t_rs_book_chapter (
    chpt_id     INTEGER PRIMARY KEY AUTOINCREMENT,
    b_id        INTEGER,
    parent_id   INTEGER DEFAULT 0,
    chpt_name   TEXT,
    is_leaf     INTEGER DEFAULT 1,
    FOREIGN KEY (b_id) REFERENCES t_rs_book(b_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE t_rs_knowledge (
    k_id        INTEGER PRIMARY KEY AUTOINCREMENT,
    parent_id   INTEGER DEFAULT 0,
    k_name      TEXT,
    is_leaf     INTEGER DEFAULT 1,
    stage       TEXT,
    subject     TEXT
);

CREATE TABLE t_rs_que_bank (
    q_id        INTEGER PRIMARY KEY AUTOINCREMENT,
    subject     TEXT,
    type        TEXT,
    difficulty  INTEGER,
    stem        TEXT,
    answer      TEXT,
    analysis    TEXT,
    create_time TEXT,
    update_time TEXT
);

CREATE TABLE t_rs_que_ref_chapter (
    q_id        INTEGER NOT NULL,
    chpt_id     INTEGER NOT NULL,
    b_id        INTEGER,
    PRIMARY KEY (q_id, chpt_id),
    FOREIGN KEY (q_id) REFERENCES t_rs_que_bank(q_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (chpt_id) REFERENCES t_rs_book_chapter(chpt_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE t_rs_que_ref_knowledge (
    q_id        INTEGER NOT NULL,
    k_id        INTEGER NOT NULL,
    PRIMARY KEY (q_id, k_id),
    FOREIGN KEY (q_id) REFERENCES t_rs_que_bank(q_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (k_id) REFERENCES t_rs_knowledge(k_id) ON DELETE CASCADE ON UPDATE CASCADE
);