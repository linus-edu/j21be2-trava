#!/usr/bin/env python

import requests

def login(): 
	global jwt, headers
	jwt = requests.get('http://localhost:8080/login').content
	print('jwt', jwt)
	headers = {'Authorization': 'Bearer ' + jwt.decode()}

login()

c = requests.get('http://localhost:8080/admintest', headers=headers).content
print(c)