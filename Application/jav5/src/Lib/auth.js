import { request } from './request.js';
import jwt from 'jwt-decode';

import { RolesModel } from 'Models/RolesModel';


export const auth =
{

    isAuthenticated()
	{
		return sessionStorage.getItem('Bearer') !== null &&
			sessionStorage.getItem('Bearer') !== '';
	},


	isAdmin()
	{
		return auth.isAuthenticated() &&
			jwt(sessionStorage.getItem('Bearer')).role === RolesModel.ADMIN;
	},


	getUser()
	{
		return auth.isAuthenticated() ?
			jwt(sessionStorage.getItem('Bearer')) : undefined;
	},


	getUserId()
	{
		return auth.isAuthenticated() ?
			jwt(sessionStorage.getItem('Bearer')).id : undefined;
	},


	getUserName()
	{
		return auth.isAuthenticated() ?
			jwt(sessionStorage.getItem('Bearer')).sub : undefined;
	},


	req_authenticate(
		username,
		password,
		successCallback,
		failureCallback,
		finishCallback
	)
	{
		request.post(
            'authenticate',
            {
				username: username,
				password: password
			},
            (response) =>
			{
				sessionStorage.setItem('Bearer', response.token);
                successCallback();
            },
            (errorContent) =>
			{
				failureCallback();
			},
			() =>
			{
				finishCallback();
			},
        );
    },


    signOut(props)
	{
		auth.removeBearer();
		auth.goToLogin(props.history);
    },


	removeBearer()
	{
		sessionStorage.removeItem('Bearer');
	},


	goToLogin(history)
	{
		history.push('/login');
    },
};
