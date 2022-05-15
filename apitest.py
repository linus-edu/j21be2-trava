#!/usr/bin/env python

import requests

def login(): 
	global jwt, headers
	#jwt = requests.get('http://localhost:8080/get-token').content

	credentials = {'username': 'user', 'password': 'pass'}
	jwt = requests.post('http://localhost:8080/api/accesstoken', json=credentials).content

	print('jwt', jwt)
	headers = {'Authorization': 'Bearer ' + jwt.decode()}
login()

###

def get_admintest():
	c = requests.get('http://localhost:8080/admintest', headers=headers).content
	print(c)

def get_users():
	c = requests.get('http://localhost:8080/api/users', headers=headers).content
	print(c)

get_users()
