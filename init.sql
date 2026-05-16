DROP TABLE IF EXISTS t_rs_book;
DROP TABLE IF EXISTS t_rs_book_chapter;
DROP TABLE IF EXISTS t_rs_knowledge;
DROP TABLE IF EXISTS t_rs_que_bank;
DROP TABLE IF EXISTS t_rs_que_ref_chapter;
DROP TABLE IF EXISTS t_rs_que_ref_knowledge;

CREATE TABLE t_rs_book (
    bid         INTEGER PRIMARY KEY AUTOINCREMENT,
    bname       TEXT,
    stage       TEXT NOT NULL,
    subject     TEXT NOT NULL,
    edition     TEXT NOT NULL,
	edition_title     TEXT NOT NULL,
    remark      TEXT,
    create_time TEXT,
    update_time TEXT
);

CREATE TABLE t_rs_book_chapter (
    chpt_id     INTEGER PRIMARY KEY AUTOINCREMENT,
    bid         INTEGER NOT NULL,
    parent_id   INTEGER DEFAULT 0,
    chpt_name   TEXT,
    is_leaf     INTEGER DEFAULT 1,
	importance	INTEGER DEFAULT 3,
    FOREIGN KEY (bid) REFERENCES t_rs_book(bid) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE t_rs_knowledge (
    kid         INTEGER PRIMARY KEY AUTOINCREMENT,
    parent_id   INTEGER DEFAULT 0,
    kname       TEXT,
    is_leaf     INTEGER DEFAULT 1,
    stage       TEXT,
    subject     TEXT,
	importance	INTEGER DEFAULT 3
);

CREATE TABLE t_rs_que_bank (
    qid         INTEGER PRIMARY KEY AUTOINCREMENT,
	stage       TEXT NOT NULL,
    subject     TEXT NOT NULL,
    type        TEXT,
	type_title  TEXT,
    difficulty  INTEGER,
    stem        TEXT,
    answer      TEXT,
    analysis    TEXT,
	chpt_marked INTEGER DEFAULT 0,
	knowledge_marked INTEGER DEFAULT 0,
    create_time TEXT,
    update_time TEXT
);

CREATE TABLE t_rs_que_ref_chapter (
    qid         INTEGER NOT NULL,
    chpt_id     INTEGER NOT NULL,
    PRIMARY KEY (qid, chpt_id),
    FOREIGN KEY (qid) REFERENCES t_rs_que_bank(qid) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (chpt_id) REFERENCES t_rs_book_chapter(chpt_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE t_rs_que_ref_knowledge (
    qid         INTEGER NOT NULL,
    kid         INTEGER NOT NULL,
    PRIMARY KEY (qid, kid),
    FOREIGN KEY (qid) REFERENCES t_rs_que_bank(qid) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (kid) REFERENCES t_rs_knowledge(kid) ON DELETE CASCADE ON UPDATE CASCADE
);