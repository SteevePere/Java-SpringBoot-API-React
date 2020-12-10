import { utils } from 'Lib/utils';


export const notifications =
{

	showInvalidFormError()
	{
		utils.openNotification(
			'error',
			'Form is invalid',
			'Please make sure that all required values have been set.',
			'bottomRight',
			3
		);
	},


	showCreateBoardError()
	{
		utils.openNotification(
			'error',
			'Creation Error',
			'Something went wrong. Please make sure that all values are correct.',
			'bottomRight',
			3
		);
	},


	showCreateBoardSuccess()
	{
		utils.openNotification(
			'success',
			'Board Created',
			'This Board has been successfully created.',
			'bottomRight',
			3
		);
	},


	showSaveUsersError()
	{
		utils.openNotification(
			'error',
			'Could not update Board',
			'An error occurred.',
			'bottomRight',
			3
		);
	},


	showSaveUsersSuccess()
	{
		utils.openNotification(
			'success',
			'Board updated',
			'The selected Users have been assigned to this Board.',
			'bottomRight',
			3
		);
	},


	showSaveBoardSuccess()
	{
		utils.openNotification(
			'success',
			'Board updated',
			'Your changes have been saved.',
			'bottomRight',
			3
		);
	},


	showSaveBoardError()
	{
		utils.openNotification(
			'error',
			'Could not update Board',
			'An error occurred.',
			'bottomRight',
			3
		);
	},


	showDeleteBoardSuccess()
	{
		utils.openNotification(
			'success',
			'Board deleted',
			'This Board has been deleted.',
			'bottomRight',
			3
		);
	},


	showDeleteBoardError()
	{
		utils.openNotification(
			'error',
			'Deletion error',
			'Unable to delete Board. Please make sure that you have sufficient rights.',
			'bottomRight',
			3
		);
	},


	showCreateIssueError()
	{
		utils.openNotification(
			'error',
			'Creation Error',
			'Something went wrong. Please make sure that all values are correct.',
			'bottomRight',
			3
		);
	},


	showCreateIssueSuccess()
	{
		utils.openNotification(
			'success',
			'Issue Created',
			'This Issue has been successfully created.',
			'bottomRight',
			3
		);
	},


	showIssueUpdateError()
	{
		utils.openNotification(
			'error',
			'Could not update Issue',
			'An error occurred.',
			'bottomRight',
			3
		);
	},


	showIssueUpdateSuccess()
	{
		utils.openNotification(
			'success',
			'Issue updated',
			'Your changes have been saved.',
			'bottomRight',
			3
		);
	},


	showDeleteIssueSuccess()
	{
		utils.openNotification(
			'success',
			'Issue deleted',
			'This Issue has been deleted.',
			'bottomRight',
			3
		);
	},


	showDeleteIssueError()
	{
		utils.openNotification(
			'error',
			'Deletion error',
			'Unable to delete Issue. Please make sure that you have sufficient rights.',
			'bottomRight',
			3
		);
	},


	showCreateMilestoneError()
	{
		utils.openNotification(
			'error',
			'Creation Error',
			'Something went wrong. Please make sure that all values are correct.',
			'bottomRight',
			3
		);
	},


	showCreateMilestoneSuccess()
	{
		utils.openNotification(
			'success',
			'Milestone Created',
			'This Milestone has been successfully created.',
			'bottomRight',
			3
		);
	},


	showMilestoneUpdateError()
	{
		utils.openNotification(
			'error',
			'Could not update Milestone',
			'An error occurred.',
			'bottomRight',
			3
		);
	},


	showMilestoneUpdateSuccess()
	{
		utils.openNotification(
			'success',
			'Milestone updated',
			'Your changes have been saved.',
			'bottomRight',
			3
		);
	},


	showDeleteMilestoneSuccess()
	{
		utils.openNotification(
			'success',
			'Milestone deleted',
			'This Milestone has been deleted.',
			'bottomRight',
			3
		);
	},


	showDeleteMilestoneError()
	{
		utils.openNotification(
			'error',
			'Deletion error',
			'Unable to delete Milestone. Please make sure that you have sufficient rights.',
			'bottomRight',
			3
		);
	},
};
