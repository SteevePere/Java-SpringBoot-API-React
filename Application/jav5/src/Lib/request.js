import axios from 'axios';
import qs from 'qs';

import { config } from 'Config/config';
import { auth } from 'Lib/auth';


const getAccessToken = () =>
{
	const bearer = sessionStorage.getItem('Bearer');
	return bearer ? 'Bearer ' + bearer : bearer;
}


const getHeaders = () =>
{
	return {
		'Authorization': getAccessToken(),
	};
};


const formatParams = (params) =>
{
	return `?${qs.stringify(
		params,
		{ arrayFormat: 'repeat', encode: false }
	)}`;
}


const handleError = (error, errorCallback) =>
{
	if (!error.response || error.response.status === 401)
	{
		auth.removeBearer();
	}

	else
		errorCallback(error.response);
}


export const request =
{
	async get(uri, params, successCallback, errorCallback, finishCallback)
	{
		return axios.get(
			config.apiUrl + uri + formatParams(params),
			{
				headers: getHeaders()
			})
			.then((response) =>
			{
				successCallback(response.data);
			})
			.finally(() =>
			{
				if (finishCallback) finishCallback();
			})
			.catch(error =>
			{
				handleError(error, errorCallback);
			});
	},


	async post(uri, body, successCallback, errorCallback, finishCallback)
	{
		await axios.post(
			config.apiUrl + uri,
			body,
			{
				headers: getHeaders()
			})
			.then((response) =>
			{
				successCallback(response.data);
			})
			.finally(() =>
			{
				finishCallback();
			})
			.catch(error =>
			{
				handleError(error, errorCallback);
			});
	},


	async patch(uri, body, successCallback, errorCallback, finishCallback)
	{
		await axios.patch(
			config.apiUrl + uri,
			body,
			{
				headers: getHeaders()
			})
			.then((response) =>
			{
				successCallback(response.data);
			})
			.finally(() =>
			{
				if (finishCallback) finishCallback();
			})
			.catch(error =>
			{
				handleError(error, errorCallback);
			});
	},


	async delete(uri, successCallback, errorCallback)
	{
		return axios.delete(
			config.apiUrl + uri,
			{
				headers: getHeaders()
			})
			.then((response) =>
			{
				successCallback(response.data);
			})
			.catch(error =>
			{
				handleError(error, errorCallback);
			});
	},


	jsonExport(uri, fileName, successCallback, errorCallback, finishCallback)
	{
		return axios.get(
			config.apiUrl + uri,
			{
				headers: getHeaders(),
				responseType: 'arraybuffer',
			}
		)
		.then((response) =>
		{
			const url = window.URL.createObjectURL(new Blob([response.data]));
			const link = document.createElement('a');

			link.href = url;
			link.setAttribute('download', fileName + '.json');
			document.body.appendChild(link);
			link.click();

			successCallback();
		})
		.finally(() =>
		{
			if (finishCallback) finishCallback();
		})
		.catch(error =>
		{
			handleError(error, errorCallback);
		});
	},
};
