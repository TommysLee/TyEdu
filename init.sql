DROP TABLE IF EXISTS t_rs_book;
DROP TABLE IF EXISTS t_rs_book_chapter;
DROP TABLE IF EXISTS t_rs_knowledge;
DROP TABLE IF EXISTS t_rs_que_bank;
DROP TABLE IF EXISTS t_rs_que_ref_chapter;
DROP TABLE IF EXISTS t_rs_que_ref_knowledge;
DROP TABLE IF EXISTS t_exam;
DROP TABLE IF EXISTS t_exam_que;
DROP TABLE IF EXISTS t_exam_que_ref_chapter;
DROP TABLE IF EXISTS t_exam_que_ref_knowledge;
DROP TABLE IF EXISTS t_student;

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

CREATE TABLE t_exam (
    exam_id     INTEGER PRIMARY KEY AUTOINCREMENT,
    title       TEXT NOT NULL,
    exam_type   TEXT NOT NULL,
    exam_time   TEXT NOT NULL,
    stage       TEXT NOT NULL,
    subject     TEXT NOT NULL,
    grade       TEXT NOT NULL,
    max_score   REAL,
    score       REAL,
	grade_rank  INTEGER,
	class_rank  INTEGER,
    published   INTEGER NOT NULL DEFAULT 0,
    reviewed    INTEGER NOT NULL DEFAULT 0,
    remark      TEXT,
    create_time TEXT,
    update_time TEXT
);

CREATE TABLE t_exam_que (
    qid              INTEGER PRIMARY KEY AUTOINCREMENT,
    exam_id          INTEGER NOT NULL,
    stage            TEXT NOT NULL,
    subject          TEXT NOT NULL,
    type             TEXT,
    difficulty       INTEGER,
    stem             TEXT,
    answer           TEXT,
    analysis         TEXT,
    chpt_marked      INTEGER DEFAULT 0,
    knowledge_marked INTEGER DEFAULT 0,
    max_score        REAL,
    score            REAL,
	response		 TEXT,
	seq              INTEGER DEFAULT 0,
    create_time      TEXT,
    update_time      TEXT,
    FOREIGN KEY (exam_id) REFERENCES t_exam(exam_id) ON UPDATE CASCADE
);

CREATE TABLE t_exam_que_ref_chapter (
    qid       INTEGER NOT NULL,
    chpt_id   INTEGER NOT NULL,
    chpt_name TEXT,
    PRIMARY KEY (qid, chpt_id),
    FOREIGN KEY (qid) REFERENCES t_exam_que(qid) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE t_exam_que_ref_knowledge (
    qid   INTEGER NOT NULL,
    kid   INTEGER NOT NULL,
    kname TEXT,
    PRIMARY KEY (qid, kid),
    FOREIGN KEY (qid) REFERENCES t_exam_que(qid) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE t_student (
    sid          INTEGER PRIMARY KEY AUTOINCREMENT,
    school       TEXT,
    name         TEXT NOT NULL,
    stage        TEXT NOT NULL,
    grade        TEXT NOT NULL,
    grade_title  TEXT,
    create_time  TEXT,
    update_time  TEXT
);
