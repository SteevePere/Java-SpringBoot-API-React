import React from 'react';

import IssueCard from './IssueCard/IssueCard';

import { notifications } from '../../Notifications/notifications';

import { request } from 'Lib/request.js';


const Issue = (props) =>
{

	const {
		getIssues,
	} = props;


	const deleteIssue = (issueToDelete) =>
	{
		if (issueToDelete)
		{
			request.delete(
	            'issue/' + issueToDelete.id,
	            () =>
				{
					getIssues();
					notifications.showDeleteIssueSuccess();
	            },
	            (error) => notifications.showDeleteIssueError(),
	        );
		}
	}


	return (

		<IssueCard
			{...props}
			deleteIssue={deleteIssue}
		/>
	);
}


export default Issue;
