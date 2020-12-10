import React from 'react';
import { NavLink } from 'react-router-dom';
import { Card, Tooltip, Popconfirm } from 'antd';
import {
	EditOutlined,
	CheckOutlined,
	DeleteOutlined,
	UsergroupAddOutlined,
} from '@ant-design/icons';

import BoardCardHeader from './BoardCardHeader/BoardCardHeader';
import BoardCardBody from './BoardCardBody/BoardCardBody';
import BoardCardFooter from './BoardCardFooter/BoardCardFooter';

import './BoardCard.css';


const BoardCard = (props) =>
{

	const {
		isAdmin,
		board,
		boardToEdit,
		saveBoard,
		setBoardToEdit,
		deleteBoard,
		openManageUsersModal,
	} = props;


	const editingBoard = boardToEdit && boardToEdit.id === board.id;


	const DeleteButton = () =>
	{
		return (
			<Tooltip title='Delete Board' placement='bottom'>
				<Popconfirm
					placement='right'
					title='Are you sure you want to delete this Board?'
					onConfirm={() => deleteBoard(board)}
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
			<Tooltip
				title={editingBoard ? 'Save Board' : 'Edit Board'}
				placement='bottom'
			>
				{
					editingBoard ?
					<CheckOutlined
						onClick={saveBoard}
					/> :
					<EditOutlined
						onClick={() => setBoardToEdit(board)}
					/>
				}
			</Tooltip>
		);
	}


	const AddUsersButton = () =>
	{
		return (
			<Tooltip title='Manage Board Users' placement='bottom'>
				<UsergroupAddOutlined
					onClick={() => openManageUsersModal(board)}
				/>
			</Tooltip>
		);
	}


	return (

		<Card
			id='board_card'
			actions={isAdmin ? [
				<EditButton/>,
				<AddUsersButton/>,
				<DeleteButton/>,
			] : []}
		>
			<BoardCardHeader
				{...props}
			/>
			<NavLink
				to={'/boards/' + board.id}
			>
				<BoardCardBody
					{...props}
				/>
				<BoardCardFooter
					{...props}
				/>
			</NavLink>
		</Card>
	);
}


export default BoardCard;
