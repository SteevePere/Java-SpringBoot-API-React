import React from 'react';
import { Card, Popconfirm, Tooltip } from 'antd';
import { DeleteOutlined } from '@ant-design/icons';

import IssueCardHeader from './IssueCardHeader/IssueCardHeader';
import IssueCardBody from './IssueCardBody/IssueCardBody';

import EditIssue from '../Edit/EditIssue';
import ViewIssue from '../View/ViewIssue';


const IssueCard = (props) =>
{

	const {
		isAdmin,
		issue,
		provided,
		snapshot,
		deleteIssue,
	} = props;


	const DeleteButton = () =>
	{
		return (
			<Tooltip title='Delete Issue' placement='bottom'>
				<Popconfirm
					placement='right'
					title='Are you sure you want to delete this Issue?'
					okText='Yes'
					cancelText='No'
					onConfirm={() => deleteIssue(issue)}
				>
					<DeleteOutlined key='delete'/>
				</Popconfirm>
			</Tooltip>
		);
	}


	const EditButton = () =>
	{
		return (
			<EditIssue
				{...props}
			/>
		);
	}


	const ViewButton = () =>
	{
		return (
			<ViewIssue
				{...props}
			/>
		);
	}


	const getActions = () =>
	{
		let actions = [
			<ViewButton/>,
			<EditButton/>,
		];

		if (isAdmin) actions.push(<DeleteButton/>);

		return actions;
	}


	return (

			<div
				ref={provided.innerRef}
				{...provided.draggableProps}
				{...provided.dragHandleProps}
				style={{
					userSelect: 'none',
					padding: 16,
					margin: '0 0 8px 0',
					backgroundColor: snapshot.isDragging ?
						'#f0f0f0' : '#fff',
					...provided.draggableProps.style,
					borderRadius: 10,
				}}
			>
				<Card
					hoverable
					style={{
						borderRadius: 10,
					}}
					actions={getActions()}
				>
					<IssueCardHeader
						{...props}
					/>
					<IssueCardBody
						{...props}
					/>
				</Card>
			</div>
	);
}


export default IssueCard;
