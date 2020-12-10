import React from 'react';
import { Card, Tooltip, Popconfirm } from 'antd';
import {
	DeleteOutlined,
} from '@ant-design/icons';

import MilestoneCardHeader from './MilestoneCardHeader/MilestoneCardHeader';
import MilestoneCardBody from './MilestoneCardBody/MilestoneCardBody';

import EditMilestone from '../Edit/EditMilestone';

import './MilestoneCard.css';


const MilestoneCard = (props) =>
{

	const {
		isAdmin,
		milestone,
		deleteMilestone,
	} = props;


	const DeleteButton = () =>
	{
		return (
			<Tooltip title='Delete Milestone' placement='bottom'>
				<Popconfirm
					placement='right'
					title='Are you sure you want to delete this Milestone?'
					onConfirm={() => deleteMilestone(milestone)}
					okText='Yes'
					cancelText='No'
				>
					<DeleteOutlined key='delete'/>
				</Popconfirm>
			</Tooltip>
		);
	}


	const EditButton = () =>
	{
		return (
			<EditMilestone
				{...props}
			/>
		);
	}


	const getActions = () =>
	{
		let actions = [
			<EditButton/>,
		];

		if (isAdmin) actions.push(<DeleteButton/>);

		return actions;
	}


	return (

		<Card
			id='milestone_card'
			actions={getActions()}
		>
			<MilestoneCardHeader
				{...props}
			/>
			<MilestoneCardBody
				{...props}
			/>
		</Card>
	);
}


export default MilestoneCard;
