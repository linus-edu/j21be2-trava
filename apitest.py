#!/usr/bin/env python

import requests

#BASE_URL = 'http://localhost:8080'
BASE_URL = 'http://localhost:8081'

def login(): 
	global jwt, headers
	#jwt = requests.get('http://localhost:8080/get-token').content

	credentials = {'username': 'user', 'password': 'pass'}
	jwt = requests.post(f'{BASE_URL}/api/accesstoken', json=credentials).content

	print('jwt', jwt)
	#headers = {'Authorization': 'Bearer ' + jwt.decode() + '_wrong'}
	headers = {'Authorization': 'Bearer ' + jwt.decode()}
login()

###

#def get_admintest():
#	c = requests.get(f'{BASE_URL}/admintest', headers=headers).content
#	print(c)

def get_users():
	c = requests.get(f'{BASE_URL}/api/users', headers=headers).content
	print(c)

get_users()
