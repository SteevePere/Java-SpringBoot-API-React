import React from 'react';

import MilestoneCard from './MilestoneCard/MilestoneCard';

import { notifications } from '../../Notifications/notifications';

import { request } from 'Lib/request.js';


const Milestone = (props) =>
{

	const {
		getMilestones,
	} = props;


	const deleteMilestone = (milestoneToDelete) =>
	{
		if (milestoneToDelete)
		{
			request.delete(
	            'milestone/' + milestoneToDelete.id,
	            (response) =>
				{
					getMilestones();

					if (response.success)
						notifications.showDeleteMilestoneSuccess();

					else
						notifications.showDeleteMilestoneError();
				},
	            (error) => notifications.showDeleteMilestoneError(),
	        );
		}
	}


	return (

		<MilestoneCard
			{...props}
			deleteMilestone={deleteMilestone}
		/>
	);
}


export default Milestone;
