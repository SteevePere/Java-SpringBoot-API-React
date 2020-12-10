import React, { useState, useEffect } from 'react';
import moment from 'moment';

import AddMilestoneView from './AddMilestoneView';

import { notifications } from '../../../Notifications/notifications';

import { request } from 'Lib/request.js';


const AddMilestone = (props) =>
{

	const {
		boardId,
		getMilestones,
	} = props;

	const [milestoneToCreate, setMilestoneToCreate] = useState({});
	const [modalVisible, setModalVisible] = useState(false);


	useEffect(() =>
		{
			if (boardId)
			{
				setMilestoneToCreate({
					...milestoneToCreate,
					boardId: boardId,
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

		setMilestoneToCreate({
			...milestoneToCreate,
			[key]: value,
		});
	}


	const handleDatePickerChange = (date) =>
	{
		if (date) date = moment(date).format('YYYY-MM-DDTHH:mm:ssZ');

		setMilestoneToCreate({
			...milestoneToCreate,
			dueDate: date,
		});
	}


	const formIsValid = () => milestoneToCreate.title !== undefined &&
		milestoneToCreate.title !== '';


	const createMilestone = () =>
	{
		if (formIsValid())
		{
			request.post(
	            'milestone',
	            milestoneToCreate,
	            (response) =>
				{
					closeModal();
					getMilestones();

					notifications.showCreateMilestoneSuccess();

					if (boardId)
					{
						setMilestoneToCreate({
							boardId: boardId,
						});
					}
	            },
	            () => notifications.showCreateMilestoneError(),
				() => {},
	        );
		}

		else notifications.showInvalidFormError();
	}


	return (

		<AddMilestoneView
			{...props}
			milestone={milestoneToCreate}
			openModal={openModal}
			closeModal={closeModal}
			modalVisible={modalVisible}
			handleInputChange={handleInputChange}
			handleDatePickerChange={handleDatePickerChange}
			saveMilestone={createMilestone}
		/>
	);
}


export default AddMilestone;
