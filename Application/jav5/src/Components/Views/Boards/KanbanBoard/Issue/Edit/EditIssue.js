import React, { useState, useEffect } from 'react';

import EditIssueView from './EditIssueView';

import { notifications } from '../../../Notifications/notifications';

import { request } from 'Lib/request.js';


const EditIssue = (props) =>
{

	const {
		issue,
		getIssues,
		getMilestones,
		boardId,
	} = props;

	const [issueToUpdate, setIssueToUpdate] = useState({});
	const [modalVisible, setModalVisible] = useState(false);


	useEffect(() =>
		{
			if (boardId)
			{
				setIssueToUpdate({
					...issueToUpdate,
					title: issue.title,
					description: issue.description,
					type: issue.type,
					status: issue.status,
					priority: issue.priority,
					milestoneId: issue.milestone && issue.milestone.id,
					assigneeId: issue.assignee && issue.assignee.id,
				});
			}
		},
		[]  // eslint-disable-line react-hooks/exhaustive-deps
	);


	const openModal = () => setModalVisible(true);


	const closeModal = () => setModalVisible(false);


	const handleInputChange = (event, key) =>
	{
		const value = event.target.value;

		setIssueToUpdate({
			...issueToUpdate,
			[key]: value,
		});
	}


	const formIsValid = () => issueToUpdate.title && issueToUpdate.type &&
		issueToUpdate.status && issueToUpdate.priority;


	const updateIssue = () =>
	{
		if (formIsValid())
		{
			request.patch(
	            'issue/' + issue.id,
	            issueToUpdate,
	            (response) =>
				{
					closeModal();
					getIssues();
					getMilestones();

					notifications.showIssueUpdateSuccess();
	            },
	            () => notifications.showIssueUpdateError(),
				() => {},
	        );
		}

		else notifications.showInvalidFormError();
	}


	return (

		<EditIssueView
			{...props}
			issue={issueToUpdate}
			openModal={openModal}
			closeModal={closeModal}
			modalVisible={modalVisible}
			handleInputChange={handleInputChange}
			saveIssue={updateIssue}
		/>
	);
}


export default EditIssue;
