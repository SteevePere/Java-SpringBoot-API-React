import { utils } from 'Lib/utils';


export const notifications =
{

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


	showDeleteSuccess()
	{
		utils.openNotification(
			'success',
			'User deleted',
			'This User and all related data have been deleted.',
			'bottomRight',
			3
		);
	},


	showDeleteError()
	{
		utils.openNotification(
			'error',
			'Deletion error',
			'Unable to delete User. Please make sure that you have sufficient rights.',
			'bottomRight',
			3
		);
	},


	showEditSuccess(message)
	{
		utils.openNotification(
			'success',
			'User updated',
			message,
			'bottomRight',
			3
		);
	},


	showEditError(message)
	{
		utils.openNotification(
			'error',
			'Update error',
			'Unable to update User. ' +
			message || 'Please make sure that you have sufficient rights.',
			'bottomRight',
			3
		);
	},


	showUserActivated()
	{
		utils.openNotification(
			'success',
			'User Account Activated',
			'This User may now sign in.',
			'bottomRight',
			3
		);
	},


	showUserDeactivated()
	{
		utils.openNotification(
			'success',
			'User Account Deactivated',
			'This User is now blocked.',
			'bottomRight',
			3
		);
	},


	showToggleError()
	{
		utils.openNotification(
			'error',
			'Could not modify Account',
			'An error occurred.',
			'bottomRight',
			3
		);
	},


	showDownloadError()
	{
		utils.openNotification(
			'error',
			'Download Error',
			'An error occurred, could not retrieve file.',
			'bottomRight',
			3
		);
	},
};
