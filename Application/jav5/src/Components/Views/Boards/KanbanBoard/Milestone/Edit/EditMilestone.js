import React, { useState, useEffect } from 'react';
import moment from 'moment';

import EditMilestoneView from './EditMilestoneView';

import { notifications } from '../../../Notifications/notifications';

import { request } from 'Lib/request.js';


const EditMilestone = (props) =>
{

	const {
		milestone,
		getMilestones,
		boardId,
	} = props;

	const [milestoneToUpdate, setMilestoneToUpdate] = useState({});
	const [modalVisible, setModalVisible] = useState(false);


	useEffect(() =>
		{
			if (boardId)
			{
				setMilestoneToUpdate({
					...milestoneToUpdate,
					title: milestone.title,
					description: milestone.description,
					dueDate: milestone.dueDate,
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

		setMilestoneToUpdate({
			...milestoneToUpdate,
			[key]: value,
		});
	}


	const handleDatePickerChange = (date) =>
	{
		if (date) date = moment(date).format('YYYY-MM-DDTHH:mm:ssZ');

		setMilestoneToUpdate({
			...milestoneToUpdate,
			dueDate: date,
		});
	}


	const formIsValid = () => milestoneToUpdate.title !== undefined &&
		milestoneToUpdate.title !== '';


	const updateMilestone = () =>
	{
		if (formIsValid())
		{
			request.patch(
	            'milestone/' + milestone.id,
	            milestoneToUpdate,
	            (response) =>
				{
					closeModal();
					getMilestones();

					notifications.showMilestoneUpdateSuccess();
	            },
	            () => notifications.showMilestoneUpdateError(),
				() => {},
	        );
		}

		else notifications.showInvalidFormError();
	}


	return (

		<EditMilestoneView
			{...props}
			milestone={milestoneToUpdate}
			openModal={openModal}
			closeModal={closeModal}
			modalVisible={modalVisible}
			handleInputChange={handleInputChange}
			handleDatePickerChange={handleDatePickerChange}
			saveMilestone={updateMilestone}
		/>
	);
}


export default EditMilestone;
