import { utils } from 'Lib/utils';


export const notifications =
{

	showRegistrationError(message)
	{
		utils.openNotification(
			'error',
			'Registration Error',
			message || 'Please contact your Administrator.',
			'bottomRight',
			5
		);
	},


	showRegistrationSuccess()
	{
		utils.openNotification(
			'success',
			'Registration Success !',
			'Your account has been created. It will need to be validated by an Administrator before your can sign in.',
			'bottomRight',
			5
		);
	},


	showInvalidFormError()
	{
		utils.openNotification(
			'error',
			'Form is invalid',
			'Please make sure that all required values have been set ' +
				'and that passwords match.',
			'bottomRight',
			3
		);
	},


	showAuthenticationError()
	{
		utils.openNotification(
			'error',
			'Authentication error',
			'Please make sure that your credentials are correct and your account has been activated.',
			'bottomRight',
			5
		);
	},
};
