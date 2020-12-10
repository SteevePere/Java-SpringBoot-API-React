import React, { useState, useEffect } from 'react';

import AddIssueView from './AddIssueView';

import { notifications } from '../../../Notifications/notifications';

import { request } from 'Lib/request.js';
import { auth } from 'Lib/auth.js';


const AddIssue = (props) =>
{

	const {
		boardId,
		getIssues,
		getMilestones,
	} = props;

	const [issueToCreate, setIssueToCreate] = useState({});
	const [modalVisible, setModalVisible] = useState(false);


	useEffect(() =>
		{
			if (boardId)
			{
				setIssueToCreate({
					...issueToCreate,
					boardId: boardId,
					reporterId: auth.getUserId(),
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

		setIssueToCreate({
			...issueToCreate,
			[key]: value,
		});
	}


	const formIsValid = () => issueToCreate.title && issueToCreate.type &&
		issueToCreate.status && issueToCreate.priority;


	const createIssue = () =>
	{
		if (formIsValid())
		{
			request.post(
	            'issue',
	            issueToCreate,
	            (response) =>
				{
					closeModal();
					getIssues();
					getMilestones();

					notifications.showCreateIssueSuccess();

					if (boardId)
					{
						setIssueToCreate({
							boardId: boardId,
							reporterId: auth.getUserId(),
						});
					}
	            },
	            () => notifications.showCreateIssueError(),
				() => {},
	        );
		}

		else notifications.showInvalidFormError();
	}


	return (

		<AddIssueView
			{...props}
			issue={issueToCreate}
			openModal={openModal}
			closeModal={closeModal}
			modalVisible={modalVisible}
			handleInputChange={handleInputChange}
			saveIssue={createIssue}
		/>
	);
}


export default AddIssue;
