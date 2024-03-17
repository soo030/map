from sqlalchemy import create_engine
from sqlalchemy.orm import scoped_session, sessionmaker
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy import Column, Integer, String

ret = {}

USER = "postgres"
PW = "TCf2fPLXJKspcfN"
URL = "mapdatabase.cgboiw3myfnk.ap-northeast-2.rds.amazonaws.com"
PORT = "5432"
DB = "postgres"

engine = create_engine("postgresql://{}:{}@{}:{}/{}".format(USER, PW, URL,PORT, DB))
db_session = scoped_session(sessionmaker(autocommit=False, autoflush=False, bind=engine))

Base = declarative_base()
Base.query = db_session.query_property()

class User(Base):
    __tablename__ = 'users'
    id = Column(Integer, primary_key=True)
    name = Column(String(50), unique=False)
    passwd = Column(String(120), unique=True)
    grade = Column(String(100), unique=False)

    def __init__(self, name=None, passwd=None, grade=None ):
        self.name = name
        self.passwd = passwd
        self.grade = grade

    def __repr__(self):
        return f'<User {self.passwd!r}>'

class Contact(Base):
    __tablename__ = 'contacts'
    id = Column(Integer, primary_key=True)
    tid = Column(String(50), unique=True)
    phonenum = Column(String(50), unique=True)
    email = Column(String(50), unique=True)

    def __init__(self, tid=None, phonenum=None, email=None):
        self.tid = tid
        self.phonenum = phonenum
        self.email = email

        def __repr__(self):
            return f'<Contact {self.tid!r}>'

# Base.metadata.drop_all(bind=engine)
Base.metadata.create_all(bind=engine)

from flask import Flask
from flask import request
from flask import jsonify
from werkzeug.serving import WSGIRequestHandler
import json

import json
WSGIRequestHandler.protocol_version = "HTTP/1.1"

app = Flask(__name__)

@app.route("/adduser", methods=['POST'])
def add_user():
    content = request.get_json(silent=True)
    name = content["name"]
    passwd = content["passwd"]

    if db_session.query(User).filter_by(passwd=passwd).first() is None:
        u = User(name=name, passwd=passwd, grade="not yet")
        db_session.add(u)
        db_session.commit()
        return jsonify(success=True)
    else:
        return jsonify(success=False)

if __name__ == "__main__":
    app.run(host='localhost', port=8888)

@app.route("/login", methods=['POST'])
def login():
    content = request.get_json(silent=True)
    name = content["name"]
    passwd = content["passwd"]

    check = False
    result = db_session.query(User).all()
    for i in result:
        if i.name == name and i.passwd == passwd:
            check = True
    return jsonify(success=check)

@app.route("/gradeupdate", methods=['POST'])
def gradeupdate():
    content = request.get_json(silent=True)
    passwd = content["passwd"]
    grade = content["grade"]

    if db_session.query(User).filter_by(passwd=passwd).first() is None:
        return jsonify(success=False)

    else:
        gradeup = db_session.query(User).filter_by(passwd=passwd).update(dict(grade=grade))
        db_session.commit()
        return jsonify(success=True)

@app.route("/gradeview", methods=['POST'])
def gradeview():
    content = request.get_json(silent=True)
    passwd = content["passwd"]

    check = "not yet"
    result = db_session.query(User).all()
    for i in result:
        if i.passwd == passwd:
            check = i.grade
    return jsonify(yourgrade=check)

@app.route("/updatecontact", methods=['POST'])
def update_contact():
    content = request.get_json(silent=True)
    tid = content["tid"]
    email = content["email"]
    phonenum = content["phonenum"]

    if db_session.query(Contact).filter_by(tid=tid).first() is None:
        c = Contact(tid=tid, email=email, phonenum=phonenum)
        db_session.add(c)
        db_session.commit()
        return jsonify(success=True)

    else:
        return jsonify(success=False)

@app.route("/updateemail", methods=['POST'])
def update_email():
    content = request.get_json(silent=True)
    tid = content["tid"]
    email = content["email"]

    if db_session.query(Contact).filter_by(tid=tid).first() is None:
        return jsonify(success=False)

    else:
        mailup = db_session.query(Contact).filter_by(tid=tid).update(dict(email=email))
        db_session.commit()
        return jsonify(success=True)

@app.route("/updatenum", methods=['POST'])
def update_num():
    content = request.get_json(silent=True)
    tid = content["tid"]
    phonenum = content["phonenum"]

    if db_session.query(Contact).filter_by(tid=tid).first() is None:
        return jsonify(success=False)

    else:
        numup = db_session.query(Contact).filter_by(tid=tid).update(dict(phonenum=phonenum))
        db_session.commit()

@app.route("/contactview", methods=['POST'])
def view_contact():
    content = request.get_json(silent=True)
    tid = content["tid"]

    p = "none"
    e = "none"

    result = db_session.query(Contact).all()
    for i in result:
        if i.tid == tid:
            p = i.phonenum
            e = i.email
    return jsonify(phonenum=p, email=e)
